package me.santipingui58.bungee.task;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.data.DataManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MovePlayerToServerTask {
	

	public MovePlayerToServerTask() {
		 Main.get().getProxy().getScheduler().schedule(Main.get(), new Runnable() {
			 @Override
			public void run() {
				Set<String> list = DataManager.getManager().getSet("pending-move-players");
				//uuid,server
				
				for (String game : list) {
					
					String[] data = game.split(",");
					ProxiedPlayer player = BungeeCord.getInstance().getPlayer(UUID.fromString(data[0]));
					if (player!=null) {
					ServerInfo target = ProxyServer.getInstance().getServerInfo(data[1]);
					player.connect(target);
					}
					DataManager.getManager().removeToSet("pending-move-players", game);
			}
			}
		 }, 1, 100, TimeUnit.MILLISECONDS);
	}
}
