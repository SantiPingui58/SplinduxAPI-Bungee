package me.santipingui58.bungee.listener;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.github.games647.craftapi.UUIDAdapter;

import me.santipingui58.bungee.Main;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.Language;
import me.santipingui58.data.SplinduxDataAPI;
import me.santipingui58.data.player.DataPlayer;
import me.santipingui58.hikari.HikariAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.util.Tristate;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerLoginListener implements Listener {

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onJoin(PostLoginEvent e) {
	

		Main.get().getLogger().info("POSTLOGIN UUID:"+ e.getPlayer().getUniqueId().toString());
		 DataManager.getManager().addToSet("online-players", e.getPlayer().getUniqueId().toString());
                    	//DataUser localDataUser = AuthPlugin.INSTANCE.getUserRepository().getUser(p);
                      //  UUID uuid = localDataUser.getUniqueId();
                      //  Main.get().getLogger().info("UUID of "+ p.getName() + ": "+uuid.toString());
                        //DataManager.getManager().set(p.getUniqueId()+"-name",p.getName());
                        //DataManager.getManager().addToSet("online-players", uuid.toString());
                 		//HikariAPI.getManager().loadData(uuid);	

	}
	
	
	
	

	private static final String UUID_FIELD_NAME = "uniqueId";
	private static final MethodHandle uniqueIdSetter;

	static {
		MethodHandle setHandle = null;
		try {
			Lookup lookup = MethodHandles.lookup();
	
			Field uuidField = InitialHandler.class.getDeclaredField(UUID_FIELD_NAME);
			uuidField.setAccessible(true);
			setHandle = lookup.unreflectSetter(uuidField);
		} catch (ReflectiveOperationException reflectiveOperationException) {
			reflectiveOperationException.printStackTrace();
		}

		uniqueIdSetter = setHandle;
	}

	
	public User giveMeADamnUser(UUID u) {
		LuckPerms api = LuckPermsProvider.get();
	    UserManager userManager = api.getUserManager();
	    CompletableFuture<User> userFuture = userManager.loadUser(u);

	    return userFuture.join(); // ouch! (block until the User is loaded)
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onLogin(LoginEvent event) {
		
		String whitelist = DataManager.getManager().get("whitelist");
		if (whitelist !=null && whitelist.equalsIgnoreCase("true")) {
		
			User user =giveMeADamnUser(event.getConnection().getUniqueId());
			if (user==null) {
				event.setCancelled(true);
				event.setCancelReason("Server is currently whitelisted.");
			}
			CachedPermissionData permissionData = user.getCachedData().getPermissionData();
			Tristate checkResult = permissionData.checkPermission("splindux.staff");
			// the same as what Player#hasPermission would return
			boolean checkResultAsBoolean = checkResult.asBoolean();
			if (!checkResultAsBoolean) {
				event.setCancelled(true);
				event.setCancelReason("Server is currently whitelisted.");
			}
		}
		
		UUID uuid = null;
		if (!event.getConnection().isOnlineMode()) {
		
		PendingConnection connection = event.getConnection();
		String username = connection.getName();
		
		try {
			UUID onlineUUID = connection.getUniqueId();
			UUID offlineUUID = UUIDAdapter.generateOfflineId(username);
			
			Main.get().getLogger().info("Online UUID:"+ onlineUUID.toString());
			Main.get().getLogger().info("Offline UUID:"+ offlineUUID.toString());
			
			uniqueIdSetter.invokeExact((InitialHandler) connection, offlineUUID);

			Main.get().getLogger().fine("New offline UUID" + offlineUUID + " set for player " + username + " instead of premium UUID " + onlineUUID);	
			uuid = offlineUUID;
			
		} catch (Exception exception) {
			exception.printStackTrace();
			Main.get().getLogger().warning("Failed to set offline uuid of " + username);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	} else {
		uuid = event.getConnection().getUniqueId();
	}
	
		 DataManager.getManager().set(uuid.toString()+"-name",event.getConnection().getName()); 
 		HikariAPI.getManager().createData(uuid);	
 		
 		UUID u = uuid;
		BungeeCord.getInstance().getScheduler().schedule(Main.get(), new Runnable() {
            @Override
            public void run() {
            	if (DataPlayer.getPlayer().getIP(u)==null || DataPlayer.getPlayer().getIP(u).equalsIgnoreCase("null")) {
            		Main.get().getLogger().info("a");
            		String ip = event.getConnection().getAddress().getAddress().toString();
            		ip = ip.replaceAll("/", "");
            		DataPlayer.getPlayer().setIP(u, ip);
            		String country = SplinduxDataAPI.getAPI().getCountry(ip);
            		String language =SplinduxDataAPI.getAPI().languageFromCountry(country);
            		DataPlayer.getPlayer().setCountry(u, country);
            		DataPlayer.getPlayer().setLanguage(u, Language.valueOf(language));
            		SplinduxDataAPI.getAPI().saveData(u);
            		} else {
            			Main.get().getLogger().info("b");
            		}
            	}
            }, 1, 0, TimeUnit.SECONDS);
	}

	
	@EventHandler
	public void onLeave(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		DataManager.getManager().removeToSet("ingame-players", p.getUniqueId().toString());
		DataManager.getManager().removeToSet("spectator-players", p.getUniqueId().toString());
	}
	
}
