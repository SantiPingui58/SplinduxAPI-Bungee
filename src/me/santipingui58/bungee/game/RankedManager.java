package me.santipingui58.bungee.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.Set;

import me.santipingui58.bungee.Main;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.SplinduxDataAPI;
import me.santipingui58.data.integration.IntegrationBungeeType;
import me.santipingui58.data.player.DataPlayer;
import me.santipingui58.data.spleef.SpleefType;

public class RankedManager {

	private static RankedManager manager;	
	 public static RankedManager getManager() {
	        if (manager == null)
	        	manager = new RankedManager();
	        return manager;
	    }
	
	
	public void executeQueues() {
		for (SpleefType spleefType : SpleefType.values()) {
			if (spleefType.equals(SpleefType.POTSPLEEF) || spleefType.equals(SpleefType.BOWSPLEEF)) continue;
			for (int i=1;i<=3;i++) {
				sendDuels("queue."+spleefType.toString().toLowerCase()+"."+i);
			}
		}
		
	}
	
	
	//queue.spleef.1
	 private void sendDuels(String queue) {
		 SpleefType spleefType = SpleefType.valueOf(queue.toUpperCase().split("\\.")[1]);
		 int teamSize = Integer.parseInt(queue.split("\\.")[2]);
		 List<String> list = DataManager.getManager().getList(queue);
		 Set<UUID> lastJoiners = new HashSet<UUID>();
			if (list.size()< teamSize*2) return;
			 while (list.size()%(teamSize*2)!=0) {
				 String last = list.get(list.size()-1);
				 lastJoiners.add(UUID.fromString(last));
				 list.remove(last);
			 }
			 
			 LinkedHashMap<UUID, Integer> elo = new LinkedHashMap<UUID,Integer>();
			 for (String u : list) {
				 if (DataManager.getManager().getSet("online-players").contains(u.toString())) {
					 UUID uuid = UUID.fromString(u);
					 elo.put(uuid, DataPlayer.getPlayer().getELO(uuid, spleefType));
				 }
			 
			 }
			 elo = sort(elo);
			 List<UUID> lista = new ArrayList<UUID>(elo.keySet());
			 for (int i =0; i<lista.size()/teamSize/2;i++) {
				 boolean b = true;
				 List<UUID> team1 = new ArrayList<UUID>();
				 List<UUID> team2 = new ArrayList<UUID>();
				 int inicio = 2*i*teamSize;
				 int fin = 2*teamSize -1 + 2*teamSize*i;

				 for (int j = inicio; j<=fin;j++) {
					 if (b) {
						 b= false;
						 Main.get().getLogger().info(lista.get(j).toString());
						 team1.add(lista.get(j));
					 } else {
						 b = true;
						 team2.add(lista.get(j));
					 }
				 }
				 
				 
					 String t1 = team1.get(0).toString();
					 team1.remove(team1.get(0));
					 for (UUID u : team1)  t1 = t1+"_"+u.toString();
					 
					 String t2 = team2.get(0).toString();
					 team2.remove(team2.get(0));
					 for (UUID u : team2)  t2 = t2+"_"+u.toString();
					 
					 String integration = t1+"_"+t2;
					  integration = integration +",null,"+spleefType.toString()+","+teamSize+",false,-1";
					  SplinduxDataAPI.getAPI().createIntegrationBungee(IntegrationBungeeType.CREATE_GAME, integration);
			 }
	 
			 DataManager.getManager().delete(queue);
			if (!lastJoiners.isEmpty()) for (UUID u : lastJoiners) DataManager.getManager().addToList(queue, u.toString());
			 
			 

	 }
	 
		public  LinkedHashMap<UUID,Integer> sort(LinkedHashMap<UUID,Integer> arrayToSort) {
		    int n = arrayToSort.size();
		    
		    int iterations = 0;
		    List<UUID> uuids = new ArrayList<UUID>();
		    uuids.addAll(arrayToSort.keySet());
		    for (int gap = n / 2; gap > 0 && iterations<=1; gap /= 2) {
		        for (int i = gap; i < n; i++) {
		        	
		            UUID key = uuids.get(i);
		            int j = i;
		            while (j >= gap && arrayToSort.get(uuids.get(j-gap)) > arrayToSort.get(key)) {
		                uuids.set(j, uuids.get(j - gap));
		                j -= gap;
		            }
		            uuids.set(j, key);
		        }
		        iterations++;
	}
		    
		    LinkedHashMap<UUID,Integer> map = new LinkedHashMap<UUID,Integer>();
		    
		    for (UUID u : uuids) {
		    	map.put(u, arrayToSort.get(u));
		    }
		    

	    return map;
		}
	
	
}
