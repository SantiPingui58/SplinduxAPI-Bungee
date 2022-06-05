package me.santipingui58.bungee.task;

import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.bungee.bungee.BungeeManager;
import me.santipingui58.bungee.game.RankedManager;
import me.santipingui58.data.DataManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;

public class OnlineServerTask {
	

	public OnlineServerTask() {
		 Main.get().getProxy().getScheduler().schedule(Main.get(), new Runnable() {
			 @Override
			public void run() {
				 
				 RankedManager.getManager().executeQueues();
				 DataManager.getManager().delete("loaded-servers");
				 
				 for (String s : BungeeCord.getInstance().getServers().keySet()) {
					   BungeeCord.getInstance().getServerInfo(s).ping(new Callback<ServerPing>() {

						@Override
						public void done(ServerPing result, Throwable error) {
							if (error==null) BungeeManager.getManager().getOnlineServers().add(s);
							
						}
						   
					   });
				 
				 }
				 
				 for (String s : BungeeManager.getManager().getOnlineServers()) {
					 DataManager.getManager().addToSet("loaded-servers", s);
					 DataManager.getManager().set("server."+s+".players", String.valueOf(BungeeCord.getInstance().getServerInfo(s).getPlayers().size()));
				 }
				 
			 }
		 }, 1, 10, TimeUnit.SECONDS);
	}
}
