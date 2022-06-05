package me.santipingui58.bungee.task;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.hikari.HikariAPI;


public class MinuteTask {
	
	
	public MinuteTask() {
		 Main.get().getProxy().getScheduler().schedule(Main.get(), new Runnable() {
			 @SuppressWarnings("deprecation")
			@Override
			public void run() {
					Date date = new Date();
			        if (date.getHours() == 6 && date.getMinutes() == 0) {
			        	if (date.getDay()==0) {
			        		
			        		HikariAPI.getManager().resetWeekly();
			        		//Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "discord broadcast #admin **Weekly reset done**");
			        		}
			        	
			            if (date.getDate()==1) {
			            	//DataManager.getManager().resetMonthlyStats();    
			            	HikariAPI.getManager().resetMonthly();
			            	//Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "discord broadcast #admin **Monthly reset done**");
			            }
			                            
			        }
				 
			 }
		 }, 1, 10, TimeUnit.MINUTES);
	}
	

}
