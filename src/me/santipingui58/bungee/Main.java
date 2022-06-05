package me.santipingui58.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import me.santipingui58.bungee.commands.BungeeAdminCommand;
import me.santipingui58.bungee.commands.SpectateCommand;
import me.santipingui58.bungee.listener.BungeeListener;
import me.santipingui58.bungee.listener.PlayerLoginListener;
import me.santipingui58.bungee.messages.MessagesManager;
import me.santipingui58.bungee.task.BalancerTask;
import me.santipingui58.bungee.task.DataTask;
import me.santipingui58.bungee.task.IntegrationTask;
import me.santipingui58.bungee.task.OnlineServerTask;
import me.santipingui58.bungee.task.StressingTask;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.spleef.SpleefType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {
	
	public static Plugin plugin;
	public static Main main;

	public static Configuration config,messages,execute;
	
	public static boolean stressing = false;
	
	public static Plugin get() {
		return plugin;
	}
	
	
	public static Main getInstance() {
		return main;
	}
	
	
    @Override
    public void onEnable() {
    	main =  this;
    	DataManager.getManager().delete("online-players");
    	DataManager.getManager().delete("loaded-players");
       	DataManager.getManager().delete("loaded-servers");
    	DataManager.getManager().delete("ingame-players");
    	DataManager.getManager().delete("spectator-players");
    	DataManager.getManager().set("queue", "true");
    	for (SpleefType spleefType : SpleefType.values()) {
			if (spleefType.equals(SpleefType.POTSPLEEF) || spleefType.equals(SpleefType.BOWSPLEEF)) continue;
			for (int i=1;i<=3;i++) {
				DataManager.getManager().delete("queue."+spleefType.toString().toLowerCase()+"."+i);
			}
		}
    	plugin = this;
    	listeners();
    	 commands();
    	 tasks();
    	 configs();
    	
 		 
 		 MessagesManager.getManager().load();
    	
    }
    
    private void commands() {
    	PluginManager p = ProxyServer.getInstance().getPluginManager();
   	 //p.registerCommand(this, new DuelCommand());
   	 //p.registerCommand(this, new HoverCommand());
    	p.registerCommand(this, new BungeeAdminCommand());
    	p.registerCommand(this, new SpectateCommand());
    	
    }
     
    
    private void tasks() {
    	new IntegrationTask();
    	new BalancerTask();
    	new OnlineServerTask();
    	new DataTask();
    	if (stressing) new StressingTask();
    }
    
    private void configs() {
    	config = config("config");
    	messages =config("messages");
    }

    public Configuration config(String name) {
    	 if (!getDataFolder().exists()) getDataFolder().mkdir();

         File configFile = new File(getDataFolder(), name+".yml");
         
         if (!configFile.exists()) {
             try (InputStream in = getResourceAsStream(name+".yml")) {
                 Files.copy(in, configFile.toPath());
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
 		 try {
 			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), name+".yml"));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		return null;
     	
    }
    
    
	
	
	private void listeners() {
		getProxy().getPluginManager().registerListener(this, new PlayerLoginListener());
		getProxy().getPluginManager().registerListener(this, new BungeeListener());
	}
	
	
    @Override
    public void onDisable() {
    	getLogger().info("Saving players data...");
    	me.santipingui58.bungee.data.DataManager.getManager().saveData();
    }

	
	

}