package me.santipingui58.bungee.commands;

import java.util.UUID;

import me.santipingui58.bungee.game.GameManager;
import me.santipingui58.bungee.game.SpleefDuel;
import me.santipingui58.bungee.messages.MessagesManager;
import me.santipingui58.data.player.DataPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HoverCommand extends Command {

	private MessagesManager mm = MessagesManager.getManager();
	
	public HoverCommand() {
		super("hover");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {

			ProxiedPlayer sp = (ProxiedPlayer) sender;	 
			 switch(args[0]) {
			 case "duelaccept": duelAccept(sp,args);
			 return;
			 case "dueldeny": duelDeny(sp,args);
			 return;
			 case "duelcancel": duelCancel(sp,args);
			 return;
			 /*
			 case "crumblecancel": crumbleCancel(sp,args);
			 return;
			 case "crumbledeny": crumbleDeny(sp,args);
			 return;
			 case "crumbleaccept": crumbleAccept(sp,args);
			 return;
			 case "playtocancel": playtoCancel(sp,args);
			 return;
			 case "playtodeny": playtoDeny(sp,args);
			 return;
			 case "playtoaccept": playtoAccept(sp,args);
			 return;
			 case "mutationaccept": mutationAccept(sp,args);
			 return;
			 case "frienddeny": friendDeny(sp,args);
			 return;
			 case "friendaccept": friendAccept(sp,args);
			 return;
			 case "joinguildplayeraccept": joinGuildPlayerAccept(sp,args);
			 return;
			 case "joinguildmemberaccept": joinGuildMemberAccept(sp,args);
			 return;
			 case "renegociateguildaccept": renegociateGuildAccept(sp,args);
			 return;
			 case "joinguildplayerdeny": joinGuildPlayerDeny(sp,args);
			 return;
			 case "joinguildmemberdeny": joinGuildMemberDeny(sp,args);
			 return;
			 case "renegociateguilddeny": renegociateGuildDeny(sp,args);
			 return;
			 case "guildduelaccept": duelGuildAccept(sp,args);
			 return;
			 case "guilddueldeny": duelGuildDeny(sp,args);
			 return;
			 case "buyplayeraccept": buyPlayerAccept(sp,args);
			 return;
			 case "buyplayerdeny": buyPlayerDeny(sp,args);
			 return;
			 case "gift": gift(sp,args);
			 return;
			 */
			 }		
			
		}
	

	

/*
	private void gift(ProxiedPlayer sp, String[] args) {
		UUID uuid2 = UUID.fromString(args[1]);
		if (TimeLimitManager.getManager().hasActiveTimeLimitBy(sp, TimeLimitType.GIFT)) {
			List<TimeLimit> timelimits = TimeLimitManager.getManager().getTimeLimitBy(sp, TimeLimitType.GIFT);
			for (TimeLimit tl : timelimits) {
			UUID uuid1 = UUID.fromString(tl.getArgs()[0]);
		
			if (uuid1.compareTo(uuid2)==0) {
				sp.sendMessage("§cYou already sent a gift to this player. You need to wait: §e" +  tl.getLeftTime());
				return;
			}
			}
		}
		
		String[] argss = {uuid2.toString()};
		TimeLimitManager.getManager().addTimeLimit(sp, 1440, TimeLimitType.GIFT, argss);

		OfflinePlayer pa = Bukkit.getOfflinePlayer(uuid2);
		sp.sendMessage("§aYou sent a gift to §b" +  pa.getName());
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer splayer = ProxiedPlayer.getProxiedPlayer(pa);
					splayer.addCoins(25);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
	}




	private void buyPlayerDeny(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.BUY_PLAYER,sp2.getUUID(), sp.getUUID());
					List<UUID> list = new ArrayList<UUID>();
					list.addAll(request.getReceptor());
					list.addAll(request.getSender());
					for (UUID u : list) {
						OfflinePlayer off = Bukkit.getOfflinePlayer(u);
						if (off.isOnline()) off.getPlayer().sendMessage("§b"+sp.getOfflinePlayer() + "§c has denied the request.");
					}
					
					GuildsManager.getManager().getBuyPlayerRequests().remove(request);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
	}




	private void buyPlayerAccept(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.BUY_PLAYER,sp2.getUUID(), sp.getUUID());
					if (request!=null) {
					request.acceptRequest(null);
					} else {
						sp.sendMessage("§This request has expired.");
					}
					GuildsManager.getManager().getBuyPlayerRequests().remove(request);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
	
		
		
		
	}




	private void duelGuildDeny(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.GUILD_DUEL,sp2.getUUID(), sp.getUUID());
					GuildsManager.getManager().getDuelRequests().remove(request);
					List<UUID> list = new ArrayList<UUID>();
					list.addAll(request.getReceptor());
					list.addAll(request.getSender());
					for (UUID u : list) {
						OfflinePlayer off = Bukkit.getOfflinePlayer(u);
						if (off.isOnline()) off.getPlayer().sendMessage("§b"+sp.getOfflinePlayer().getName() + "§c has denied the request.");
					}
					
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
	}



	private void duelGuildAccept(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.GUILD_DUEL,sp2.getUUID(), sp.getUUID());
					if (request!=null) {
					Player player = Bukkit.getPlayer(args[1]);
					String[] argss = {sp.getUUID().toString(), player.getUniqueId().toString()};
					request.acceptRequest(argss);
					} else {
						sp.getPlayer().sendMessage("§cThis request has expired.");
					}
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
	}




	private void renegociateGuildDeny(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.RENEGOCIATE_GUILD,sp2.getUUID(), sp.getUUID());
					List<UUID> list = new ArrayList<UUID>();
					list.addAll(request.getReceptor());
					list.addAll(request.getSender());
					for (UUID u : list) {
						OfflinePlayer off = Bukkit.getOfflinePlayer(u);
						if (off.isOnline()) off.getPlayer().sendMessage("§b"+sp.getOfflinePlayer() + "§c has denied the request.");
					}
					GuildsManager.getManager().getRenegociateRequests().remove(request);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
		
	}



	private void joinGuildMemberDeny(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.JOIN_GUILD_AS_MEMBER,sp2.getUUID(), sp.getUUID());
					List<UUID> list = new ArrayList<UUID>();
					list.addAll(request.getReceptor());
					list.addAll(request.getSender());
					for (UUID u : list) {
						OfflinePlayer off = Bukkit.getOfflinePlayer(u);
						if (off.isOnline()) off.getPlayer().sendMessage("§b"+sp.getOfflinePlayer() + "§c has denied the request.");
					}
					GuildsManager.getManager().getJoinGuildMembersRequests().remove(request);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
		
		
	}



	private void joinGuildPlayerDeny(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.JOIN_GUILD_AS_PLAYER,sp2.getUUID(), sp.getUUID());
					String[] args = {pa.getName(),pa.getUniqueId().toString()};
					request.acceptRequest(args);
					GuildsManager.getManager().getJoinGuildPlayersRequests().remove(request);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
		
		
	}



	private void renegociateGuildAccept(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.RENEGOCIATE_GUILD,sp2.getUUID(), sp.getUUID());
					request.acceptRequest(null);
					GuildsManager.getManager().getJoinGuildPlayersRequests().remove(request);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
	
		
		
		
	}



	private void joinGuildMemberAccept(ProxiedPlayer sp, String[] args) {

		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.JOIN_GUILD_AS_MEMBER,sp2.getUUID(), sp.getUUID());
					request.acceptRequest(null);
					GuildsManager.getManager().getJoinGuildPlayersRequests().remove(request);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
	
		
		
	}



	private void joinGuildPlayerAccept(ProxiedPlayer sp, String[] args) {

		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = GuildsManager.getManager().getRequest(RelationshipRequestType.JOIN_GUILD_AS_PLAYER,sp2.getUUID(), sp.getUUID());
					request.acceptRequest(null);
					GuildsManager.getManager().getJoinGuildPlayersRequests().remove(request);
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
	
		
		
	}



	private void friendAccept(ProxiedPlayer sp, String[] args) {	

		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		ProxiedPlayer temp = ProxiedPlayer.getProxiedPlayer(pa);
		if (temp==null) {
			 new ProxiedPlayer(pa.getUniqueId());
			HikariAPI.getManager().loadData(pa.getUniqueId());
		}
		
			new BukkitRunnable() {
				public void run() {		
					ProxiedPlayer sp2 = ProxiedPlayer.getProxiedPlayer(pa);
					RelationshipRequest request = FriendsManager.getManager().getFriendRequest(sp2.getUUID(), sp.getUUID());
					if (request!=null) {
					request.acceptRequest(null);
					FriendsManager.getManager().getFriendRequests().remove(request);
					if (pa.isOnline()) pa.getPlayer().sendMessage("§b" + sp.getOfflinePlayer().getName() +  " §ahas accepted your friend request!");
					if (sp.getOfflinePlayer().isOnline()) sp.getPlayer().sendMessage("§aYou have added §b" + sp2.getOfflinePlayer().getName() +  " §ato your friends list!");
					} else {
						if (sp.getOfflinePlayer().isOnline()) sp.getPlayer().sendMessage("§cThis request has expired.");
					}
				
		}
				}.runTaskLaterAsynchronously(Main.get(), 5L);
		
	
	}
	
	
	
	private void friendDeny(ProxiedPlayer sp, String[] args) {
		@SuppressWarnings("deprecation")
		OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
		if (pa.isOnline()) pa.getPlayer().sendMessage("§b" + sp.getOfflinePlayer().getName() +  " §chas denied your friend request.");
		if (sp.getOfflinePlayer().isOnline()) sp.getPlayer().sendMessage("§aYou have denied §b" + args[1] +  " §cfriend request.");
		RelationshipRequest request = FriendsManager.getManager().getFriendRequest(pa.getUniqueId(), sp.getUUID());
		FriendsManager.getManager().getFriendRequests().remove(request);

	}




	private void mutationAccept(ProxiedPlayer sp, String[] args) {
		Player p = sp.getPlayer();
		if (sp.isInGame() || sp.isInQueue()) {
			UUID uuid = UUID.fromString(args[1]);
			GameMutation mutation = sp.getArena().getMutationBy(uuid);
			if (!mutation.getOwner().equals(sp)) {
			if (mutation!=null && mutation.getState().equals(MutationState.VOTING)) {
				mutation.voteMutation(sp);
			} else {
				p.sendMessage("§cThis mutation voting has finished or you are not in a game.");
			}
			}
	} else {
		p.sendMessage("§cYou need to be in a FFA game to execute this command.");	
	}
		
	}



	private void playtoAccept(ProxiedPlayer sp, String[] args) {
		Player p = sp.getPlayer();
		if (sp.isInGame()) {
			Arena arena = sp.getArena();
			if (arena.getPlayToRequest()!=null) {
				ArenaRequest request = arena.getPlayToRequest();
				if (!request.getAcceptedPlayers().contains(sp)) {
					request.playtoAccept(sp);
				} else {
					p.sendMessage("§cYou already accepted this request.");
				}
			}else {
				p.sendMessage("§cThis crumble request has expired.");
			}
	
		} else {
			p.sendMessage("§cThis crumble request has expired.");
		}
		
	}



	private void playtoDeny(ProxiedPlayer sp, String[] args) {
		Player p = sp.getPlayer();
		if (sp.isInGame()) {
			Arena arena = sp.getArena();
			if (arena.getPlayToRequest()!=null) {
			for (ProxiedPlayer dueled : arena.getPlayers()) {
				dueled.getPlayer().sendMessage("§cThe player §b" + sp.getName() + "§c has denied the request! Playto cancelled.");
			}
			
			arena.setPlayToRequest(null);
			
			} else {
				p.sendMessage("§cThis crumble request has expired.");
			}
		} else {
			p.sendMessage("§cThis crumble request has expired.");
		}
		
	}



	private void playtoCancel(ProxiedPlayer sp, String[] args) {
		Player p = sp.getPlayer();
		if (sp.isInGame()) {
			Arena arena = sp.getArena();
			ArenaRequest request = arena.getPlayToRequest();		
		if (request!=null) {
			if (request.getChallenger().equals(sp)) {
		for (ProxiedPlayer dueled : arena.getViewers()) {
			dueled.getPlayer().sendMessage("§b" + sp.getName() + "§c has cancelled the  playto request.");
		}
		arena.setPlayToRequest(null);
			} else {
				p.sendMessage("§cThis duel request has expired.");
			}
		} else {
			p.sendMessage("§cThis duel request has expired.");
		}
	} else {
		p.sendMessage("§cThis crumble request has expired.");
	}
		
	}



	private void crumbleAccept(ProxiedPlayer sp, String[] args) {
		Player p = sp.getPlayer();
		if (sp.isInGame()) {
			Arena arena = sp.getArena();
			if (!arena.getDeadPlayers1().contains(sp) && !arena.getDeadPlayers2().contains(sp)) {
			if (arena.getCrumbleRequest()!=null) {
				ArenaRequest request = arena.getCrumbleRequest();
				if (!request.getAcceptedPlayers().contains(sp)) {
					request.crumbleAccept(sp);
				} else {
					p.sendMessage("§cYou already accepted this request.");
				}
			}else {
				p.sendMessage("§cThis crumble request has expired.");
			}
	} else {
		p.sendMessage("§cOnly alive players can execute this command.");
	}
		} else {
			p.sendMessage("§cThis crumble request has expired.");
		}
		
	}



	private void crumbleDeny(ProxiedPlayer sp, String[] args) {
		Player p = sp.getPlayer();
		if (sp.isInGame()) {
			Arena arena = sp.getArena();
			if (arena.getCrumbleRequest()!=null) {
			for (ProxiedPlayer dueled : arena.getPlayers()) {
				dueled.getPlayer().sendMessage("§cThe player §b" + sp.getName() + "§c has denied the request! Crumble cancelled.");
			}
			
			arena.setCrumbleRequest(null);
			
			} else {
				p.sendMessage("§cThis crumble request has expired.");
			}
		} else {
			p.sendMessage("§cThis crumble request has expired.");
		}		
	}



	private void crumbleCancel(ProxiedPlayer sp, String[] args) {
		Player p = sp.getPlayer();
		if (sp.isInGame()) {
			Arena arena = sp.getArena();
			ArenaRequest request = arena.getCrumbleRequest();		
		if (request!=null) {
			if (request.getChallenger().equals(sp)) {
		for (ProxiedPlayer dueled : arena.getViewers()) {
			dueled.getPlayer().sendMessage("§b" + sp.getName() + "§c has cancelled the  crumble request.");
		}
		
			arena.setCrumbleRequest(null);
			} else {
				p.sendMessage("§cThis duel request has expired.");
			}
		} else {
			p.sendMessage("§cThis duel request has expired.");
		}
	} else {
		p.sendMessage("§cThis crumble request has expired.");
	}	
	}



	private void record(ProxiedPlayer sp, String[] args,CommandSender sender) {
		if (sp.isInGame()) {
			Arena arena = sp.getArena();
				if (arena.getRecordingRequest()) {
					arena.record();
					arena.cancelRecordingRequest();
					List<Player> list = new ArrayList<Player>();
					List<ProxiedPlayer> playerss = new ArrayList<ProxiedPlayer>();
							playerss.addAll(arena.getDuelPlayers1());
					playerss.addAll(arena.getDuelPlayers2());
					for (ProxiedPlayer spp : playerss) {
						list.add(spp.getPlayer());
					}							
					 //Player[] myArray = new Player[list.size()];
					// GameReplay replay = ReplayManager.getManager().createReplay(ReplayManager.getManager().getDuelName(arena));
					//ReplayAPI.getInstance().recordReplay(replay.getName(), sender,  list.toArray(myArray));
					sp.getPlayer().sendMessage("§aYou are now recording this game!");
				}
		}	
	}
*/


	private void duelCancel(ProxiedPlayer sp, String[] args) {
		
		SpleefDuel duel = GameManager.getManager().getDuel(sp.getUniqueId(), UUID.fromString(args[1]));
		if (duel!=null) {
			for (UUID u : duel.getDueledPlayers()) {
				ProxiedPlayer pp = BungeeCord.getInstance().getPlayer(u);
				mm.sendMessage(pp, "duel_cancelled",sp.getName());
			}
			mm.sendMessage(sp, "you_have_duel_cancelled");
			GameManager.getManager().deleteSpleefDuel(sp.getUniqueId(), duel);
			} else {
				mm.sendMessage(sp, "duel_expired");
			}
		
	}



	private void duelAccept(ProxiedPlayer sp,String[] args) {	
		ProxiedPlayer p2 = BungeeCord.getInstance().getPlayer(args[1]);
		if (p2!=null && p2.isConnected()) {
			SpleefDuel duel = GameManager.getManager().getDuelByDueled(p2.getUniqueId(), sp.getUniqueId());
			if (duel!=null) {
				DataPlayer dp = DataPlayer.getPlayer();
				if (!dp.isInGame(sp.getUniqueId())) {
					if (!dp.isInGame(p2.getUniqueId())) {
						duel.acceptDuel(sp.getUniqueId());
						GameManager.getManager().deleteSpleefDuel(p2.getUniqueId(), duel);
						} else {
							mm.sendMessage(sp, "player_already_ingame", p2.getName());
					}
				} else {
					mm.sendMessage(sp,"cannot_execute_here");
				}
			} else {
				mm.sendMessage(sp, "duel_expired");
			}
		} else {
			mm.sendMessage(sp, "duel_ended");
		}
		
	}
	
	
	private void duelDeny(ProxiedPlayer sp, String[] args) {
		ProxiedPlayer p2 = BungeeCord.getInstance().getPlayer(args[1]);
		if (p2!=null && p2.isConnected()) {
			SpleefDuel duel = GameManager.getManager().getDuelByDueled(p2.getUniqueId(), sp.getUniqueId());
			if (duel!=null) {
				mm.sendMessage(sp, "you_have_denied_dueled",p2.getName());
				
				for (UUID u : duel.getAllPlayers()) {
					ProxiedPlayer pp = BungeeCord.getInstance().getPlayer(u);
					mm.sendMessage(pp, "duel_denied",sp.getName());
				}
				GameManager.getManager().deleteSpleefDuel(p2.getUniqueId(), duel);
			} else {
				mm.sendMessage(sp, "duel_expired");
			}
			} else {
				mm.sendMessage(sp, "duel_ended");
			}
		
	}

	

	
}


