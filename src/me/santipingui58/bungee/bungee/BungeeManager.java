package me.santipingui58.bungee.bungee;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;


public class BungeeManager {
	private static BungeeManager manager;	
	 public static BungeeManager getManager() {
	        if (manager == null)
	        	manager = new BungeeManager();
	        return manager;
	    }
	
private Set<String> onlineServers = new HashSet<String>();
	
	public Set<String> getOnlineServers() {
		return this.onlineServers;
	}
	
	
	
	public ServerInfo getBalancedServer(String name) {
		ServerInfo serverToConnect = null;

		for (String bs : onlineServers) {
			if (bs.contains(name)) {
				if (serverToConnect==null) {
					serverToConnect= BungeeCord.getInstance().getServers().get(bs);
				} else {
					if (serverToConnect.getPlayers().size()>BungeeCord.getInstance().getServers().get(bs).getPlayers().size()) {
						serverToConnect= BungeeCord.getInstance().getServers().get(bs);
					}
				}
			}
		}
		return serverToConnect;
	}
	
	
	public LinkedHashMap<ServerInfo,Integer> getServersByPlayersOnline(String name) {
		LinkedHashMap<ServerInfo,Integer> hashmap = new LinkedHashMap<ServerInfo,Integer>();
		
		for (String bs : onlineServers) {
			if (bs.contains(name)) {
				ServerInfo si = BungeeCord.getInstance().getServers().get(bs);
				hashmap.put(si, si.getPlayers().size());
			}
		}
		hashmap = sortByValue(hashmap);
		return hashmap;
	}
	
	
	  
	  private <T> LinkedHashMap<T, Integer> sortByValue(Map<T,Integer> hm) { 
	        // Create a list from elements of HashMap 
	        List<Map.Entry<T, Integer> > list = 
	               new LinkedList<Map.Entry<T, Integer> >(hm.entrySet()); 
	  
	        // Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<T, Integer> >() { 
	            public int compare(Map.Entry<T, Integer> o2,  
	                               Map.Entry<T, Integer> o1) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        }); 
	
	        LinkedHashMap<T, Integer> temp = new LinkedHashMap<T, Integer>(); 
	        for (Map.Entry<T, Integer> aa : list) { 
	            temp.put(aa.getKey(), aa.getValue()); 
	        } 
	        return temp; 
}
}

