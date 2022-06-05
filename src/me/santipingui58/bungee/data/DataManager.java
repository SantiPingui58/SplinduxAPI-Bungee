package me.santipingui58.bungee.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import me.santipingui58.data.spleef.SpleefType;
import me.santipingui58.hikari.HikariAPI;
import net.md_5.bungee.BungeeCord;

public class DataManager {

		private static DataManager manager;	
		 public static DataManager getManager() {
		        if (manager == null)
		        	manager = new DataManager();
		        return manager;
		    }
		
 	
		 //Called everyday at 00:00 of the Server time. Gives the mutation tokens to all players who are able to get daily mutations. Runs in a Asynchronous Task for better perfomance
	 
	public void giveMutationTokens() {
		HikariAPI.getManager().giveMutations();
	}
	   
	
	public void eloDecay() {
		HikariAPI.getManager().eloDecay(SpleefType.SPLEEF);
		HikariAPI.getManager().eloDecay(SpleefType.SPLEGG);
		HikariAPI.getManager().eloDecay(SpleefType.TNTRUN);
	}
	
	
	public void resetELO() {
	HikariAPI.getManager().resetELO();
	}
	
	
	public void saveData() {
		Set<String> toRemove = new HashSet<String>(); 
		for (String s : me.santipingui58.data.DataManager.getManager().getSet("loaded-players")) 	{
			if (BungeeCord.getInstance().getPlayer(UUID.fromString(s))==null) toRemove.add(s); 
			HikariAPI.getManager().saveData(UUID.fromString(s));	
		}
		
		//for (String s : toRemove) me.santipingui58.data.DataManager.getManager().removeToSet("loaded-players", s);
		
	}
	
	
	
	
}
