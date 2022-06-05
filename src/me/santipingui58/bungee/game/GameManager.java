package me.santipingui58.bungee.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.bungee.bungee.BungeeManager;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.spleef.SpleefType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;

public class GameManager {
	private static GameManager manager;	
	 public static GameManager getManager() {
	        if (manager == null)
	        	manager = new GameManager();
	        return manager;
	    }
	
	 
	 private  HashMap<UUID,Set<SpleefDuel>> spleefduels = new HashMap<UUID,Set<SpleefDuel>>();
	 private boolean queues;
	 
	 public void addSpleefDuel(UUID uuid, SpleefDuel duel) {
		 if (!spleefduels.containsKey(uuid)) spleefduels.put(uuid, new HashSet<SpleefDuel>());
			 spleefduels.get(uuid).add(duel);
			 DataManager.getManager().addToSet(uuid+".duels", duel.getUUID().toString());
				//Eliminar luego de 1 minuto si o si
				BungeeCord.getInstance().getScheduler().schedule(Main.get(), new Runnable() {
		            @Override
		            public void run() {
		            	spleefduels.get(uuid).remove(duel);
		            	 DataManager.getManager().removeToSet(uuid+".duels", duel.getUUID().toString());
		            }
		        }, 1, TimeUnit.MINUTES);
}
	 
	 
	 
	 public void deleteSpleefDuel(UUID uuid,SpleefDuel duel) {
		 spleefduels.get(uuid).remove(duel);
    	 DataManager.getManager().removeToSet(uuid+".duels", duel.getUUID().toString());
	 } 
	 
	 
	 public boolean areQueuesClosed() {
		 return this.queues;
	 }
	 
	 
	 public void  closeQueues(boolean b) {
		 this.queues = b;
	 }
	 

	 
	 public SpleefDuel getDuelByDueled(UUID sp, UUID player) {
		 if (spleefduels.containsKey(sp)) {
			 for (SpleefDuel duels : spleefduels.get(sp)) {
				 if (duels.getDueledPlayers().contains(player)) return duels;
			 }
		 }
			 return null;
		 }

	public SpleefDuel getDuel(UUID sender,UUID duelUUID) {
		for (SpleefDuel duel : this.spleefduels.get(sender)) {
			if (duel.getUUID().compareTo(duelUUID)==0) return duel;
		}
		return null;
	}
	 
	
	public HashMap<String,String> getArenaAndServer(String arena,Set<UUID> players, SpleefType type, int teamSize, boolean cantie, int maxtime) {
		if (arena==null || arena.equalsIgnoreCase("") || arena.equalsIgnoreCase("null")) 
			return getAnyArenaAndServer(players, type, teamSize, cantie, maxtime);
			return getSpecificArenaAndServer(arena, players, type, teamSize, cantie, maxtime);
		
	}
	
	public HashMap<String,String> getAnyArenaAndServer(Set<UUID> players, SpleefType type, int teamSize, boolean cantie, int maxtime) {
		HashMap<String,String> arenaAndServer = new HashMap<String,String>();
		
		List<String> list = new ArrayList<String>(DataManager.getManager().getSet("loaded-arenas"));
		Collections.shuffle(list);
		for (String s : list) {
		
			int minSize = Integer.parseInt(DataManager.getManager().get("arena."+s+".min"));
			int maxSize = Integer.parseInt(DataManager.getManager().get("arena."+s+".max"));	
			SpleefType spleefType = SpleefType.valueOf(DataManager.getManager().get("arena."+s+".type"));
			boolean isInGame = Boolean.parseBoolean(DataManager.getManager().get("arena."+s+",ingame"));
				if (spleefType.equals(type) && minSize<=teamSize && maxSize>=teamSize) {
				if (!isInGame) {
					String[] args = s.split("\\.");
					arenaAndServer.put(args[0], args[1]);
					return arenaAndServer;
				}
				} 
			}

		return null;
		
	}
	
	
	public HashMap<String,String> getSpecificArenaAndServer(String arena,Set<UUID> players, SpleefType type, int teamSize, boolean cantie, int maxtime) {
		HashMap<String,String> arenaAndServer = new HashMap<String,String>();
		String server = type.toString().toLowerCase();
		server = server.replaceAll("\\d", "");
		List<ServerInfo> servers = new ArrayList<>(BungeeManager.getManager().getServersByPlayersOnline(server).keySet());

		
		Collections.reverse(servers);
		
		for (ServerInfo si : servers) {
			Set<String> loadedArenas = DataManager.getManager().getSet("loaded-arenas");
		for (String loadedArena : loadedArenas) {	
			if (loadedArena.contains(arena.toLowerCase()) && loadedArena.contains(si.getName().toLowerCase())) {
				int minSize = Integer.parseInt(DataManager.getManager().get("arena."+loadedArena+".min"));
				int maxSize = Integer.parseInt(DataManager.getManager().get("arena."+loadedArena+".max"));	
				SpleefType spleefType = SpleefType.valueOf(DataManager.getManager().get("arena."+loadedArena+".type"));
				boolean isInGame = Boolean.parseBoolean(DataManager.getManager().get("arena."+loadedArena+",ingame"));
					if (spleefType.equals(type) && minSize<=teamSize && maxSize>=teamSize) {
					if (!isInGame) {
						String[] a = loadedArena.split("\\.");
						arenaAndServer.put(a[0], si.getName());
						return arenaAndServer;
					}
					} 
			}
			}
		}
		return null;
		
	}
}
