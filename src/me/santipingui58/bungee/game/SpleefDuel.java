package me.santipingui58.bungee.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.santipingui58.bungee.messages.MessagesManager;
import me.santipingui58.bungee.utils.Utils;
import me.santipingui58.data.SplinduxDataAPI;
import me.santipingui58.data.integration.IntegrationBukkitType;
import me.santipingui58.data.spleef.SpleefType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SpleefDuel {

	private String arena;
	private UUID challenger;
	private List<UUID> dueledPlayers;
	private List<UUID> acceptedPlayers;
	private SpleefType type;
	private UUID uuid;
	MessagesManager mm = MessagesManager.getManager();
	
	public SpleefDuel(UUID challenger, List<UUID> sp2, String arena,SpleefType type) {
		uuid = UUID.randomUUID();
		this.challenger = challenger;
		this.dueledPlayers = sp2;
		this.arena = arena;
		this.type = type;
		this.acceptedPlayers = new ArrayList<UUID>();
		sendMessage();
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	public UUID getChallenger() {
		return this.challenger;
	}
	
	public List<UUID> getDueledPlayers() {
		return this.dueledPlayers;
	}
	public List<UUID> getAcceptedPlayers() {
		return this.acceptedPlayers;
	}
	
	public List<UUID> getAllPlayers() {
		List<UUID> list = new ArrayList<UUID>();
		list.addAll(dueledPlayers);
		list.add(challenger);
		return list;
	}
	
	public String getArena() {
		return this.arena;
	}
	
	public SpleefType getType() {
		return this.type;
	}

	
	private void sendMessage() {
		BungeeCord bg = BungeeCord.getInstance();
		ProxiedPlayer challenger = bg.getPlayer(this.challenger);
		if (this.dueledPlayers.size()==1) {
			ProxiedPlayer pp = bg.getPlayer(this.dueledPlayers.get(0));
			
			mm.sendMessage(challenger, "sent_duel_1v1", pp.getName(),type.toString(),
					getArena()==null ? mm.getMessage(challenger.getUniqueId(), "random_map") : getArena());
			
		mm.sendMessage(pp, "recieved_duel", challenger.getName(),type.toString(),getArena()==null ? mm.getMessage(pp.getUniqueId(), "random_map") : getArena());
		pp.sendMessage(getInvitation(pp.getUniqueId(),challenger.getName()));	
		
		//new AcceptDuelMenu(sp2.get(0), duel).o(sp2.get(0).getPlayer());	
		
		String server = pp.getServer().getInfo().getName();
		//UUID player,SpleefType type,String mapa,UUID challenger,UUID duelUUID,List<UUID> players,
		String jugadores = "";
		for (UUID u : this.dueledPlayers) {
			if (jugadores=="") {
				jugadores = u.toString();
			} else {
				jugadores = jugadores + "," + u.toString();
			}
		}
		
		String mapa = getArena()==null ? "null" : getArena();
		String te = pp.getUniqueId().toString()+","+type.toString()+","+mapa+","+this.challenger.toString()+","+this.uuid.toString()+jugadores;
		SplinduxDataAPI.getAPI().createIntegrationBukkit(server.toUpperCase(), IntegrationBukkitType.OPEN_DUEL_ACCEPT_MENU,te );
		} else {
			int size = (this.dueledPlayers.size()+1)/2; 
			String mode = size+"VS"+size;
			
			
			mm.sendMessage(challenger, "sent_duel_team", mode,getArena()==null ? mm.getMessage(challenger.getUniqueId(), "random_map") : getArena());
			for (UUID s2 : this.dueledPlayers) {
				ProxiedPlayer sp_2 = bg.getPlayer(s2);
				mm.sendMessage(sp_2, "recieved_duel", challenger.getName(),type.toString(),getArena()==null ? mm.getMessage(sp_2.getUniqueId(), "random_map") : getArena());
				sp_2.sendMessage(getInvitation(s2,challenger.getName()));	
				//new AcceptDuelMenu(sp_2, duel).o(sp_2.getPlayer());	
				String server = sp_2.getServer().toString();
				
				String mapa = getArena()==null ? "null" : getArena();
				String jugadores = "";
				for (UUID u : this.dueledPlayers) {
					if (jugadores=="") {
						jugadores = u.toString();
					} else {
						jugadores = jugadores + "," + u.toString();
					}
				}
				
				String te = s2.toString()+","+type.toString()+","+mapa+","+this.challenger.toString()+","+this.uuid.toString()+jugadores;
				SplinduxDataAPI.getAPI().createIntegrationBukkit(server.toUpperCase(), IntegrationBukkitType.OPEN_DUEL_ACCEPT_MENU, te);
			}
		}
		
		TextComponent msg1 = new TextComponent("["+ mm.getMessage(challenger.getUniqueId(), "cancel")+"]");
		msg1.setColor(net.md_5.bungee.api.ChatColor.RED );
		msg1.setBold( true );
		msg1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hover duelcancel " + getUUID().toString()));	
		msg1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(mm.getMessage(challenger.getUniqueId(), "cancel_duel")).create()));
		challenger.sendMessage(msg1);
	}
	
	private BaseComponent[] getInvitation(UUID player,String dueler) {
		TextComponent msg1 = new TextComponent("["+ mm.getMessage(player, "accept") +"]");
		msg1.setColor(net.md_5.bungee.api.ChatColor.GREEN );
		msg1.setBold( true );
		msg1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hover duelaccept " + dueler));	
		msg1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(mm.getMessage(player, "accept_duel")).create()));
		TextComponent msg2 = new TextComponent("["+mm.getMessage(player, "deny")+"]");
		msg2.setColor( net.md_5.bungee.api.ChatColor.RED );
		msg2.setBold( true );
		msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hover dueldeny "  + dueler));
		msg2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(mm.getMessage(player, "deny_duel")).create()));
		ComponentBuilder cb = new ComponentBuilder(msg1);
		cb.append(" ");
		cb.append(msg2);
		return cb.create();
	}
	
	
	
	public void acceptDuel(UUID sp) {
		ProxiedPlayer p = BungeeCord.getInstance().getPlayer(sp);
		if (getAcceptedPlayers().contains(sp)) {
			mm.sendMessage(p, "already_accepted_duel");
			return;
		}
		getAcceptedPlayers().add(sp);
		if (getAcceptedPlayers().size()>=getDueledPlayers().size()) {
		//GameManager.getManager().duelGame(challenger, getDueledPlayers(), getArena(),getType(),getAllPlayers().size()/2,false,false,-1);
			//SPLEEF,SantiPingui58-Luna_45,NexByte-exin12,-1,false,map		
			
			ServerInfo target = ProxyServer.getInstance().getServerInfo(this.type.toString().toLowerCase());			
			
       	String players = this.dueledPlayers.get(0).toString();
			for (UUID u : this.dueledPlayers) {
				ProxiedPlayer p2 = BungeeCord.getInstance().getPlayer(u);
				p2.connect(target);
				if (u.compareTo(this.dueledPlayers.get(0))==0) continue;	
				players = players + ","+ u.toString();
				}
			
			ProxiedPlayer chall = BungeeCord.getInstance().getPlayer(this.challenger);
			chall.connect(target);
			
			String message = this.type.toString()+","+this.challenger+","+players+",-1,false,"+this.arena;
			
			SplinduxDataAPI.getAPI().createIntegrationBukkit(this.type.toString(), IntegrationBukkitType.START_GAME, message);
		} else {
			List<UUID> list = new ArrayList<UUID>();		
			List<UUID> leftToAccept = new ArrayList<UUID>();		
			list.add(getChallenger());
			list.addAll(getDueledPlayers());
			
			for (UUID splayer : getDueledPlayers()) {
				if (!getAcceptedPlayers().contains(splayer)) {
					leftToAccept.add(splayer);
				}
			}
			TextComponent msg1 = new TextComponent(mm.getMessage(p.getUniqueId(), "accept"));
			msg1.setColor(net.md_5.bungee.api.ChatColor.GREEN );
			msg1.setBold( true );
			msg1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hover duelaccept " + BungeeCord.getInstance().getPlayer(challenger).getName()));		
			msg1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(mm.getMessage(p.getUniqueId(), "accept_duel")).create()));
			TextComponent msg2 = new TextComponent(mm.getMessage(p.getUniqueId(), "deny"));
			msg2.setColor( net.md_5.bungee.api.ChatColor.RED );
			msg2.setBold( true );
			msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hover dueldeny "  + BungeeCord.getInstance().getPlayer(challenger).getName()));
			msg2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(mm.getMessage(p.getUniqueId(), "deny_duel")).create()));
			
			ComponentBuilder cb = new ComponentBuilder(msg1);
			cb.append(" ");
			cb.append(msg2);
			BaseComponent[] bc =  cb.create();		
			for (UUID dueled : list) {		
				mm.sendMessage(dueled, "has_accepted_duel", BungeeCord.getInstance().getPlayer(sp).getName(),Utils.getUtils().getPlayerNamesFromList(leftToAccept));
				if (dueled!=getChallenger()) {
					if (!getAcceptedPlayers().contains(dueled))
						BungeeCord.getInstance().getPlayer(dueled).sendMessage(bc);
			}
			}
		}
}
}
