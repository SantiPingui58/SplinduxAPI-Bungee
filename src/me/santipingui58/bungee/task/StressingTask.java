package me.santipingui58.bungee.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.data.DataManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StressingTask {
	

	public StressingTask() {
		 Main.get().getProxy().getScheduler().schedule(Main.get(), new Runnable() {
			 @Override
			public void run() {
					if  (DataManager.getManager().getSet("ingame-players").size()==0) return;
		for (ProxiedPlayer pp : BungeeCord.getInstance().getPlayers())  {
			 int random = ThreadLocalRandom.current().nextInt(0, 100 + 1);
			if (pp.hasPermission("splindux.staff")) continue;
			if (random>=70) {
				List<String> players = new ArrayList<String>(DataManager.getManager().getSet("ingame-players"));
			
				Collections.sort(players);
				ProxiedPlayer spect = BungeeCord.getInstance().getPlayer(UUID.fromString(players.get(0)));
				if (spect!=null)
				ProxyServer.getInstance().getPluginManager().dispatchCommand(pp, "/spectate " + spect.getName());
			}
		}
			 }
		 }, 1, 5, TimeUnit.SECONDS);
	}
}
