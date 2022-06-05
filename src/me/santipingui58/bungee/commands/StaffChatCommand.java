package me.santipingui58.bungee.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.santipingui58.data.Language;
import me.santipingui58.data.player.DataPlayer;
import me.santipingui58.translate.TranslateAPI;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

	public StaffChatCommand() {
		super("staffchat","splindux.staff","sc");
	}
	DataPlayer dp = DataPlayer.getPlayer();
		
	@Override
	public void execute(CommandSender sender, String[] args) {
		List<UUID> list = new ArrayList<UUID>();
		for (ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
			if (online.hasPermission("splindux.staff")) list.add(online.getUniqueId());
				
		}
		
		ProxiedPlayer p = (ProxiedPlayer) sender;
		StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < args.length; i++)
	    {
	      builder.append(args[i]).append(" ");
	    }
	  String message = builder.toString();
	  
	  
	  List<UUID> english = new ArrayList<UUID>();
	  List<UUID> spanish = new ArrayList<UUID>();
	  List<UUID> russian = new ArrayList<UUID>();
	  List<UUID> withoutTranslate = new ArrayList<UUID>();
	  
	  for (UUID players : list) {
		  if (!dp.getLanguage(players).equals(dp.getLanguage(p.getUniqueId())) && dp.hasTranslate(players)) {
			 
			  switch(dp.getLanguage(players)) {
			case ENGLISH:
				english.add(players);
				break;
			case RUSSIAN:
				russian.add(players);
				break;
			case SPANISH:
				spanish.add(players);
				break;
			  }
			  
		  } else {
			  withoutTranslate.add(players);
		  }
	  }
	  String server = p.getServer().getInfo().getName();
	  server = server.substring(0, 1).toUpperCase() + server.substring(1);
	  String prefix = !p.hasPermission("splindux.staff") ? "§c[Staff] §f["+server+ "] §7[User] §f" : "§c[Staff]  §f["+server+ "] §f";
	  
	  for (UUID player : withoutTranslate) BungeeCord.getInstance().getPlayer(player).sendMessage(TextComponent.fromLegacyText(prefix + p.getName() + "§8: §e" + message));
	  
	  
		try {
			TranslateAPI.getAPI().translate(dp.getLanguage(p.getUniqueId()), Language.ENGLISH, message)
			  .thenAccept(text -> { 
				  for (UUID pl : english) {
					  BungeeCord.getInstance().getPlayer(pl).sendMessage(TextComponent.fromLegacyText(prefix + p.getName() + "§8: §e" + text));
				  }
			  });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			TranslateAPI.getAPI().translate(dp.getLanguage(p.getUniqueId()), Language.SPANISH, message)
			  .thenAccept(text -> { 
				  for (UUID pl : spanish) {
					  BungeeCord.getInstance().getPlayer(pl).sendMessage(TextComponent.fromLegacyText(prefix + p.getName() + "§8: §e" + text));
				  }
			  });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			TranslateAPI.getAPI().translate(dp.getLanguage(p.getUniqueId()), Language.RUSSIAN, message)
			  .thenAccept(text -> { 
				  for (UUID pl : russian) {
					  BungeeCord.getInstance().getPlayer(pl).sendMessage(TextComponent.fromLegacyText(prefix + p.getName() + "§8: §e" + text));
				  }
			  });
		} catch (IOException e) {
			e.printStackTrace();
		}
	 

		
}

	
}


