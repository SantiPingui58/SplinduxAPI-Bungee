package me.santipingui58.bungee.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import me.santipingui58.bungee.game.GameManager;
import me.santipingui58.bungee.messages.MessagesManager;
import me.santipingui58.bungee.utils.Utils;
import me.santipingui58.data.player.DataPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class DuelCommand extends Command implements TabExecutor{

	public DuelCommand() {
		super("duel");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
        	final ProxiedPlayer p = (ProxiedPlayer) sender;
        	MessagesManager mm = MessagesManager.getManager();
        	if (GameManager.getManager().areQueuesClosed() && ! sender.hasPermission("splindux.staff")) {
        		mm.sendMessage(p, "queues_are_closed");
        		return;
        	}
        
        	 	DataPlayer dp = DataPlayer.getPlayer();
        		if (dp.isInGame(p.getUniqueId())) {
        			mm.sendMessage(p, "cant_use_command_ingame");
        			return;
        		}
        		
        	if (args.length==0) {
        		mm.sendMessage(p, "use_of_command", "/duel <Players>");
        	} else {

        		StringBuilder builder = new StringBuilder();
        	    for (int i = 0; i < args.length; i++)
        	    {
        	      builder.append(args[i]).append(" ");
        	    }
        	    
        	  String message = builder.toString();
        	  List<String> list = new ArrayList<String>(Arrays.asList(message.split(" ")));
        		List<UUID> sp2 = new ArrayList<UUID>();
        		if ((list.size()+1)%2!=0) {
        			mm.sendMessage(p, "only_odd_duel");
        			return;
        		}
        		
        		
        		
        		if (list.size()+1>=6 && !p.hasPermission("splindux.vip")) {
        			String[] argument = {"3"};
        			mm.sendMessage(p, "no_team_spleef_permission",argument);
        			String[] argument2 = {"&a&l[VIP]"};
                   mm.sendMessage(p, "no_rank_permission", argument2);
        			return;	
        		}
        		if (list.size()+1>=8 && !p.hasPermission("splindux.epic")) {
        			String[] argument = {"4"};
        			mm.sendMessage(p, "no_team_spleef_permission",argument);
        			String[] argument2 = {"&9&l[Epic]"};
                   mm.sendMessage(p, "no_rank_permission", argument2);return;
        			
        		}
        		if (list.size()+1>=10 && !p.hasPermission("splindux.extreme")) {
        			String[] argument = {"5"};
        			mm.sendMessage(p, "no_team_spleef_permission",argument);
        			String[] argument2 = {"&5&l[Extreme]"};
                   mm.sendMessage(p, "no_rank_permission", argument2);return;
        			
        		}
        		
        		List<String> players = new ArrayList<String>();
        		

        		if (Utils.getUtils().hasDuplicate(list)) {
        			mm.sendMessage(p, "only_duel_once");
        			return;
        		}
        		
        		
        	  for (String s : list) {
        		  ProxiedPlayer op = BungeeCord.getInstance().getPlayer(s);
        		  if (op!=null) {
        			  if (!op.equals(p)) {
        				  players.add(op.getName());
     				  
        				  if (GameManager.getManager().getDuelByDueled(p.getUniqueId(),op.getUniqueId())==null) {
        					  if (!dp.isInGame(op.getUniqueId())) {
        						  sp2.add(op.getUniqueId());
        				  } else {
        						String[] argu = {op.getName()};
        						mm.sendMessage(p, "player_already_ingame",argu);
        						return;
        					}
        			  } else {
        					mm.sendMessage(p, "already_sent_duel");
        					  return;
        				}
        			  } else {
        				  mm.sendMessage(p, "cant_duel_yourself");
        				  return;
        			  }
        			 
        		  } else {
        			  mm.sendMessage(p, "player_not_online",s);
        			  return;
        		  }
        	  }
        	  
        	  if (Utils.getUtils().hasDuplicate(players)) {
        		  mm.sendMessage(p, "only_duel_once");
        			return;
        		}
        	  
        	  //new DuelMenu(sp,sp2,null).o(p);
        	  //String server = p.getServer().getInfo().getName();
        	  
        	  String msg = p.getUniqueId().toString();
        	  for (UUID u : sp2) {
        		  msg = msg +","+u.toString();
        	  }
        	 
        	  //IntegrationManager.getManager().createIntegrationBukkit(IntegrationServerType.valueOf(server.toUpperCase()), IntegrationType.OPEN_DUEL_MENU, msg);
        	  
        	}

        }
	

}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
			
		Set<String> names = new HashSet<String>();
		
		for (ProxiedPlayer pp : BungeeCord.getInstance().getPlayers()) names.add(pp.getName());
		
		  String arg = args.length > 0 ? args[args.length -1] : "";
	        return names.stream()
	                .filter(s -> (arg.isEmpty() || s.toLowerCase().startsWith(arg.toLowerCase())))
	                .collect(Collectors.toList());
		
	}
	
}


