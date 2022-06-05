package me.santipingui58.bungee.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.santipingui58.bungee.Main;
import me.santipingui58.bungee.bungee.BungeeManager;
import me.santipingui58.bungee.utils.Utils;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.SplinduxDataAPI;
import me.santipingui58.data.integration.IntegrationBukkitType;
import me.santipingui58.data.integration.IntegrationBungeeType;
import me.santipingui58.data.spleef.SpleefType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BungeeAdminCommand extends Command implements TabExecutor {

	public BungeeAdminCommand() {
		super("bungeeadmin","splindux.admin","badmin");
	}
		
	@Override
	public void execute(CommandSender sender, String[] args) {
	if (args[0].equalsIgnoreCase("execute")) {

	       File file = new File(Main.get().getDataFolder(),"execute.txt");
	       BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	       String st = null;
	       try {
			while ((st = br.readLine()) != null)BungeeCord.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(), st);
			   
				sender.sendMessage(TextComponent.fromLegacyText("§aCommands executed"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	} else if (args[0].equalsIgnoreCase("title")) {
		//badmin title <server> title <title> subtitle <subtitle>
		if (args[2].equalsIgnoreCase("title") || args[2].equalsIgnoreCase("subtitle")) {
			StringBuilder builder = new StringBuilder();
			
			int left = 0;
		    for (int i = 3; i < args.length; i++)
		    {
		    	if (!args[i].equalsIgnoreCase("subtitle")) {
		      builder.append(args[i]).append(" ");
		      left = i;
		    	} else {
		    		break;
		    	}
		    }
		    
		  String title = builder.toString();
		  
			StringBuilder builder2 = new StringBuilder();
		  for (int i = left+2; i < args.length; i++)
		    {
		      builder2.append(args[i]).append(" ");
		    }
		  
		  String subtitle = builder2.toString();
		  if (title.contains(",") || subtitle.contains(",")) {
				sender.sendMessage(TextComponent.fromLegacyText("§The title and subtitle can't contain ',' character"));
			  return;
		  }
		  String message = title+"," + subtitle;
		  if (args[1].matches(".*\\d.*")) {
		  SplinduxDataAPI.getAPI().createIntegrationBukkit(args[1], IntegrationBukkitType.TITLE, message);
		} else if (args[1].equalsIgnoreCase("all")) {
			for (String s : BungeeManager.getManager().getOnlineServers()) {
				  SplinduxDataAPI.getAPI().createIntegrationBukkit(s, IntegrationBukkitType.TITLE, message);
			}
		} else {
			Set<String> servers = new HashSet<String>();
			for (String s : BungeeManager.getManager().getOnlineServers()) {
			if (s.toLowerCase().contains(args[1].toLowerCase())) servers.add(s);	
			}
			
			for (String s : servers)   SplinduxDataAPI.getAPI().createIntegrationBukkit(s, IntegrationBukkitType.TITLE, message);
		}
		}
		
		
	}  else if (args[0].equalsIgnoreCase("duel")) {
			
			SpleefType type = SpleefType.valueOf(args[1].toUpperCase());
			boolean tie = false;
			try {
			tie = Boolean.parseBoolean(args[3]);
			} catch (Exception ex) {
				sender.sendMessage(TextComponent.fromLegacyText("§cThe value of tie can only be true or false."));
				return;
			}
			
			int time = 0;
			try {
			time = Integer.valueOf(args[2]);
			}catch (Exception ex) {
				sender.sendMessage(TextComponent.fromLegacyText("§cThe value of time can only be numbers."));
				return;
			}
			if (args.length<5) {
				sender.sendMessage(TextComponent.fromLegacyText("§aUse of command: /staff duel <type> <time in minutes> <can tie?> <players>"));
				return;
			}
			
			StringBuilder builder = new StringBuilder();
		    for (int i = 4; i < args.length; i++)
		    {
		      builder.append(args[i]).append(" ");
		    }
		    
		  String message = builder.toString();
		  List<String> list = new ArrayList<String>(Arrays.asList(message.split(" ")));
			List<UUID> sp2 = new ArrayList<UUID>();
			if ((list.size())%2!=0) {
				sender.sendMessage(TextComponent.fromLegacyText("§cYou can only duel an odd amount of players (1,3,5,7,etc.)"));
				return;
			}
			
			
			List<String> players = new ArrayList<String>();
			

			if (Utils.getUtils().hasDuplicate(list)) {
				sender.sendMessage(TextComponent.fromLegacyText("§cYou can only duel the same player once..."));
				return;
			}
			
			
		  for (String s : list) {
			  ProxiedPlayer op = BungeeCord.getInstance().getPlayer(s);
			  if (op!=null && op.isConnected()) {
					  players.add(op.getName());
						 UUID dueled = op.getUniqueId();
						  if (!DataManager.getManager().getSet("ingame-players").contains(dueled.toString())) {
							  sp2.add(dueled);
					  } else {
							sender.sendMessage(TextComponent.fromLegacyText("§cThis player §b"+ op.getName() +" is already in game."));
							return;
						}
				  
				 
			  } else {
				  sender.sendMessage(TextComponent.fromLegacyText("§cThe player §b" + s + "§c does not exist or is not online."));
				  return;
			  }
		  }
		  
		  if (Utils.getUtils().hasDuplicate(players)) {
				sender.sendMessage(TextComponent.fromLegacyText("§cYou can only duel the same player once..."));
				return;
			}
		  
		  int size = sp2.size()/2;
		  
		  UUID challenger = sp2.get(0);
		  sp2.remove(challenger);
		  int ti = time;

				  //SantiPingui58_hikarilof,arena,spleef,4,canTie,maxTime
				  String integration = challenger.toString();
				  for (UUID sp : sp2)integration = integration+ "_"+ sp.toString();
				  integration = integration + ",null,"+type.toString()+","+size+","+tie+","+ti;
				  SplinduxDataAPI.getAPI().createIntegrationBungee(IntegrationBungeeType.CREATE_GAME, integration);
		  
		} else if (args[0].equalsIgnoreCase("whitelist")) {
			boolean whitelist = Boolean.parseBoolean(DataManager.getManager().get("whitelist"));
			if (whitelist) {
				sender.sendMessage(TextComponent.fromLegacyText("Whitelist turned off"));
				DataManager.getManager().set("whitelist", "false");
			} else {
				sender.sendMessage(TextComponent.fromLegacyText("Whitelist turned on"));
				DataManager.getManager().set("whitelist", "true");
			}
		}
		
}

	@Override
	public Iterable<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return DataManager.getManager().getSet("ingame-players");
	}
	
	
	
}


