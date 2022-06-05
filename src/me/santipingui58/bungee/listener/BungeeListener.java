package me.santipingui58.bungee.listener;

import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.data.SplinduxDataAPI;
import me.santipingui58.data.integration.IntegrationBungeeType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeListener implements Listener {

	@EventHandler
    public void onKick(final ServerKickEvent event) {
        final ProxiedPlayer p = event.getPlayer();
        if (event.getPlayer().getServer() != null) {
        String serverName=	event.getPlayer().getServer().getInfo().getName();
            if (serverName.startsWith("spleef")  || serverName.startsWith("splegg") || serverName.startsWith("tntrun") ) {
                event.setCancelled(true);
                Main.get().getProxy().getScheduler().schedule(Main.get(), new Runnable() {
                    @Override
                    public void run() {
                    	if (p!=null && p.isConnected()) {
                        SplinduxDataAPI.getAPI().createIntegrationBungee(IntegrationBungeeType.SEND_TO_LOBBY, p.getUniqueId().toString());
                        String msg = new TextComponent(event.getKickReasonComponent()).getText();
                        p.sendMessage(new ComponentBuilder(ChatColor.RED + "Disconnected: " + ChatColor.RESET + msg).create());
                    	
                    	}
                    }
                }, 1l, TimeUnit.MICROSECONDS);
            } else if (serverName.startsWith("login")) {
            	     Main.get().getProxy().getScheduler().schedule(Main.get(), new Runnable() {
                         @Override
                         public void run() {
                             p.connect(Main.get().getProxy().getServerInfo("limbo"));
                         }
                     }, 1l, TimeUnit.MICROSECONDS);
            } 
        }
    }
	
	
	
	
	
}
