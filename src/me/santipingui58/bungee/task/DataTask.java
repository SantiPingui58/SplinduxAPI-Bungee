package me.santipingui58.bungee.task;

import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.bungee.data.DataManager;

public class DataTask {
	

	public DataTask() {
		 Main.get().getProxy().getScheduler().schedule(Main.get(), new Runnable() {
			 @Override
			public void run() {
		DataManager.getManager().saveData();
			 }
		 }, 1, 10, TimeUnit.MINUTES);
	}
}
