package me.santipingui58.bungee.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.SplinduxDataAPI;
import me.santipingui58.data.integration.IntegrationBukkitType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class SpectateCommand extends Command implements TabExecutor {

	public SpectateCommand() {
		super("spectate");
	}
		
	@Override
	public void execute(CommandSender sender, String[] args) {
	
		ProxiedPlayer p  =  (ProxiedPlayer) sender;
		String server = p.getServer().getInfo().getName();
		if (server.equalsIgnoreCase("limbo") || server.equalsIgnoreCase("login")) {
			return;
		}
		if (args.length==0) {
			sender.sendMessage(TextComponent.fromLegacyText("§aUso of command: /spectate <Player>"));
		} else {
			if (DataManager.getManager().getSet("ingame-players").contains(p.getUniqueId().toString())) {
				sender.sendMessage(TextComponent.fromLegacyText("§cYou can't spectate while being in game. Do /spawn first."));
				return;
			}
			
			ProxiedPlayer spect = new ArrayList<ProxiedPlayer>(BungeeCord.getInstance().matchPlayer(args[0])).get(0);
			if (spect!=null &&  spect.isConnected()) {
				if (DataManager.getManager().getSet("ingame-players").contains(spect.getUniqueId().toString())) {	
					DataManager.getManager().set(p.getUniqueId().toString()+"-spectating", spect.getUniqueId().toString());
					DataManager.getManager().addToSet("spectator-players", p.getUniqueId().toString());
							SplinduxDataAPI.getAPI().createIntegrationBukkit(spect.getServer().getInfo().getName(), IntegrationBukkitType.SPECTATE, p.getUniqueId().toString());
							
							if (p.getServer().getInfo().getName().equalsIgnoreCase(spect.getServer().getInfo().getName())) return;
						 BungeeCord.getInstance().getScheduler().schedule(Main.get(), new Runnable() {
								public void run() {
										p.connect(spect.getServer().getInfo());
								}
					        }, 200, 0, TimeUnit.MILLISECONDS);
				} else {
					sender.sendMessage(TextComponent.fromLegacyText("§cThe player §b" + args[0] + "§c is not in game."));
				}
			} else {
				sender.sendMessage(TextComponent.fromLegacyText("§cThe player §b" + args[0] + "§c does not exist or is not online."));
			}
		} 
}

	@Override
	public Iterable<String> onTabComplete(CommandSender arg0, String[] arg1) {
		Set<String> set = new HashSet<String>();
		for (String s : DataManager.getManager().getSet("online-players")) {
			if (BungeeCord.getInstance().getPlayer(UUID.fromString(s))!=null) set.add(BungeeCord.getInstance().getPlayer(UUID.fromString(s)).getName());
		}	
		return set;
	}
	
	
	
}


