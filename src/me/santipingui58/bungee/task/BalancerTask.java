package me.santipingui58.bungee.task;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.bungee.bungee.BungeeManager;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.SplinduxDataAPI;
import me.santipingui58.data.integration.IntegrationBukkitType;
import me.santipingui58.data.player.DataPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;

public class BalancerTask {
	
	//SERVER;Type;Message
	
	 DataPlayer sp =  DataPlayer.getPlayer();
	
	public BalancerTask() {
		
		BungeeCord.getInstance().getScheduler().schedule(Main.get(), new Runnable() {
            @Override
            public void run() {
				Set<String> list = new HashSet<String>();
				list.addAll(DataManager.getManager().getSet("bungee-balancer"));
				
				for (String s : list) {
					
					String[] mgs = s.split(";");				
					String server= mgs[0];
					IntegrationBukkitType type = IntegrationBukkitType.valueOf(mgs[1]);
						String message = mgs[2];
						try {
						ServerInfo balancedServer=	BungeeManager.getManager().getBalancedServer(server);
						Main.get().getLogger().info(message);
						SplinduxDataAPI.getAPI().createIntegrationBukkit(balancedServer.getName(), type, message);
					}   catch(Exception ex) {
						DataManager.getManager().removeToSet("bungee-balancer", s);
						BungeeCord bg = BungeeCord.getInstance();		
						bg.getLogger().info("<BALANCER ERROR>");
						bg.getLogger().info("Type: " + type.toString());
						bg.getLogger().info("Message: " + message);
						ex.printStackTrace();
					}
						
						DataManager.getManager().removeToSet("bungee-balancer", s);
					
					
				}
				
            }
        }, 1, 50, TimeUnit.MILLISECONDS);
	}
}
