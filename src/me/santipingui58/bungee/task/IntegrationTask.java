package me.santipingui58.bungee.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.santipingui58.bungee.Main;
import me.santipingui58.bungee.bungee.BungeeManager;
import me.santipingui58.bungee.game.GameManager;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.SplinduxDataAPI;
import me.santipingui58.data.integration.IntegrationBukkitType;
import me.santipingui58.data.integration.IntegrationBungeeType;
import me.santipingui58.data.player.DataPlayer;
import me.santipingui58.data.spleef.SpleefType;
import me.santipingui58.hikari.HikariAPI;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;





public class IntegrationTask {
	
	//SERVER;Type;Message
	
	 DataPlayer sp =  DataPlayer.getPlayer();
	
	public IntegrationTask() {
		
		BungeeCord.getInstance().getScheduler().schedule(Main.get(), new Runnable() {
            @Override
            public void run() {
				Set<String> list = new HashSet<String>();
				list.addAll(DataManager.getManager().getSet("bungee-integration"));
				for (String s : list) {
					String[] mgs = s.split(";");				
					IntegrationBungeeType type = IntegrationBungeeType.valueOf(mgs[0]);
						String message = mgs[1];
						String[] data = message.split(",");
						try {
						switch(type) {
						
						//server,players_players_players
						case MOVE_PLAYER:
						String server = data[0];
						String[] players = data[1].split("_");
						for (String sss : players) {
						BungeeCord.getInstance().getPlayer(UUID.fromString(sss)).connect(BungeeCord.getInstance().getServerInfo(server));
						}
						break;
						/*
						//map,SPLEEF,sender,santi,patata,triff
						case SPLEEF_DUEL_CREATED:
							
							String map = data[0];
							SpleefType spleefType = SpleefType.valueOf(data[1]);
							UUID sender = UUID.fromString(data[2]);
							List<UUID> dueled = new ArrayList<UUID>();
							for (int i = 3; i<data.length; i++) dueled.add(UUID.fromString(data[i]));
							SpleefDuel duel = new SpleefDuel(sender, dueled, map, spleefType);
							GameManager.getManager().addSpleefDuel(sender, duel);
							break;
							
					
							 * 
							 */
						//player,message
						case SEND_MESSAGE:
							
							UUID uuid = UUID.fromString(data[0]);
							String texto = data[1];
							BungeeCord.getInstance().getPlayer(uuid).sendMessage(TextComponent.fromLegacyText(texto));
							break;
							/*
							//duelUUID,playerUUID,challengerUUID
						case ACCEPT_DUEL:	
							UUID duelUUID = UUID.fromString(data[0]);
							UUID player = UUID.fromString(data[1]);
							 sender = UUID.fromString(data[2]);
						 duel = GameManager.getManager().getDuel(sender,duelUUID);
						 if (duel!=null) {
							 duel.acceptDuel(player);
						 } else {
							 BungeeCord.getInstance().getPlayer(player).sendMessage(new ComponentBuilder("§cThis duel has expired.").create());
						 }
						 break;
						 */
							//msg
						case BROADCAST:	
							BungeeCord.getInstance().broadcast(data[0]);
						 break;
						 
						 
							//text,clickevent,hoverevent
						case BROADCAST_COMPONENT:	
							BaseComponent[] asd = TextComponent.fromLegacyText(data[0]);
							TextComponent msg2 = new TextComponent(asd);
							if (data[1]!=null) msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,data[1]));
							if (data[2]!=null)msg2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(data[2]).create()));
							BungeeCord.getInstance().broadcast(msg2);
						 break;
							
						 //uuid
						case LOAD_DATA:	
							 uuid = UUID.fromString(data[0]);
							HikariAPI.getManager().loadData(uuid);
						 break;
						 
						 
						 //uuid
						case LOAD_FRIENDS:	
							 uuid = UUID.fromString(data[0]);
							HikariAPI.getManager().loadFriends(uuid);
						 break;
						 
						 //uuid
						case SAVE_DATA:	
							 uuid = UUID.fromString(data[0]);
							HikariAPI.getManager().saveData(uuid);
						 break;
						 
						//uuid,uui2
						case SEND_TO_LOBBY:			
						ServerInfo serverToConnect = BungeeManager.getManager().getBalancedServer("lobby");
						if (serverToConnect!=null) {
							for (String u : data) {
								ProxiedPlayer p  = BungeeCord.getInstance().getPlayer(UUID.fromString(u));
								
								if (p!=null && p.isConnected()) {
								p.connect(serverToConnect);
								}
						}
						}
						 break;
						//uuid,uui2
						case SAVE_FRIENDS:	
							 uuid = UUID.fromString(data[0]);
							UUID uuid1 = UUID.fromString(data[1]);
							HikariAPI.getManager().saveFriend(uuid, uuid1);
						 break;
						 
						//uuid,uui2
						case DELETE_FRIENDS:	
							 uuid = UUID.fromString(data[0]);
							 uuid1 = UUID.fromString(data[1]);
							HikariAPI.getManager().deleteFriend(uuid, uuid1);
						 break;
						 
						  //SantiPingui58_hikarilof,arena,spleef,4,canTie,maxTime,duelUUID
						case CREATE_GAME:	
						 players =  data[0].split("_");
						String arena = data[1];

						 SpleefType spleefType = SpleefType.valueOf(data[2]);
						int teamSize = Integer.parseInt(data[3]);
						boolean cantie = Boolean.parseBoolean(data[4]); 
						int maxTime = Integer.parseInt(data[5]);
						String duel = data.length>6 ? data[6] : "";
						Set<UUID> uuids = new HashSet<UUID>();
						for (String ss : players) uuids.add(UUID.fromString(ss));
						HashMap<String,String> arenaAndServer = GameManager.getManager().getArenaAndServer(arena,uuids, spleefType, teamSize, cantie, maxTime);	
						if (arenaAndServer==null) {
							for (UUID u : uuids) BungeeCord.getInstance().getPlayer(u).sendMessage(TextComponent.fromLegacyText("§cCouldn't find an arena! Duel cancelled."));
						if (duel!=null)SplinduxDataAPI.getAPI().createIntegrationBukkit(BungeeCord.getInstance().getPlayer(players[0]).getServer().getInfo().getName(), IntegrationBukkitType.CANCEL_DUEL,duel+","+players[0]);
						} 
						
						for (Entry<String, String> it : arenaAndServer.entrySet()) {
							 String ser= it.getValue();
								//for (UUID u : uuids) BungeeCord.getInstance().getPlayer(u).connect(BungeeCord.getInstance().getServerInfo(ser));
							 message = message + ","+ it.getKey();
							SplinduxDataAPI.getAPI().createIntegrationBukkit(ser, IntegrationBukkitType.START_GAME,message);
						}
					
						 break;
						 
						 /*
						//duelUUID,playerUUID,challengerUUID
						case DENY_DUEL:
							 duelUUID = UUID.fromString(data[0]);
							 player = UUID.fromString(data[1]);
							 sender = UUID.fromString(data[2]);
							 duel = GameManager.getManager().getDuel(sender,duelUUID);
							 if (duel!=null) {
								   
								 BungeeCord.getInstance().getPlayer(player).sendMessage(new ComponentBuilder("§cYou have denied the duel request from §b" +  BungeeCord.getInstance().getPlayer(sender) + "§c!").create());
									for (UUID u : duel.getAllPlayers()) {
										if (u.compareTo(player)!=0)
											 BungeeCord.getInstance().getPlayer(u).sendMessage(new ComponentBuilder(
													 "§cThe player §b" +  BungeeCord.getInstance().getPlayer(player) + "§c has denied the request! Duel cancelled.").create());
									}	
									
									GameManager.getManager().deleteSpleefDuel(sender, duel);
							 } else {
								 BungeeCord.getInstance().getPlayer(player).sendMessage(new ComponentBuilder("§cThis duel has expired.").create());
							 }
							 */
						default:
							break;	
						}
					}   catch(Exception ex) {
						DataManager.getManager().removeToSet("bungee-integration", s);
						BungeeCord bg = BungeeCord.getInstance();		
						bg.getLogger().info("<INTEGRATION ERROR>");
						bg.getLogger().info("Type: " + type.toString());
						bg.getLogger().info("Message: " + message);
						ex.printStackTrace();
					}
						
						DataManager.getManager().removeToSet("bungee-integration", s);
					
					
				}
				
            }
        }, 1, 50, TimeUnit.MILLISECONDS);
	}
}
