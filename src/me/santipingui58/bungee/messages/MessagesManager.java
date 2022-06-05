package me.santipingui58.bungee.messages;

import java.util.UUID;

import me.santipingui58.bungee.Main;
import me.santipingui58.data.DataManager;
import me.santipingui58.data.Language;
import me.santipingui58.data.player.DataPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessagesManager {
	private static MessagesManager manager;	
	 public static MessagesManager getManager() {
	        if (manager == null)
	        	manager = new MessagesManager();
	        return manager;
	    }
	
	 DataPlayer dp = DataPlayer.getPlayer();
	 
	 public void sendMessage(UUID u,String path,String... args) {
	
		 BungeeCord.getInstance().getPlayer(u).sendMessage(TextComponent.fromLegacyText(getMessage(path,args,dp.getLanguage(u))));
	 }
	 
	 
	 public void sendMessage(ProxiedPlayer pp,String path,String... args) {
		sendMessage(pp.getUniqueId(),path,args);
	 }
	 
	 public String getMessage(UUID u, String path, String... args) {
		 return getMessage(path,args,dp.getLanguage(u));
	 }
	 
	 
 private String getMessage(String path, String[] args,Language language) {
	 
	 //BungeeCord.getInstance().getLogger().info("messages."+path+"."+language.toString().toLowerCase());
	 	String message = DataManager.getManager().get("messages."+path+"."+language.toString().toLowerCase());
	 	
	 	if (message==null || message.equalsIgnoreCase("")) message =  DataManager.getManager().get("messages."+path+"."+Language.ENGLISH.toString().toLowerCase()); 	
	 	
	 	
		 message = ChatColor.translateAlternateColorCodes('&', message);
		 
		 int i = 0;
		 if (args!=null && args.length>0) { 
		 for (String s : args) {
			 String replacing = "%arg"+i+"%";
			message = message.replaceAll(replacing, s);
			 i++;
		 }
		 }
		 return message;
	 }

	
	 public void load() {
		 int eng = 0;
		 int esp = 0;
		 int rus = 0;
		for (String s : Main.messages.getKeys()) {
			String en = Main.messages.getString(s+".en");
			String es = Main.messages.getString(s+".es");
			String ru = Main.messages.getString(s+".ru");
			if (en!=null && !en.equalsIgnoreCase("")) {
				DataManager.getManager().set("messages."+s+".english", en);
				eng++;
			}
			if (es!=null && !es.equalsIgnoreCase("")) {
				DataManager.getManager().set("messages."+s+".spanish", es);
				esp++;
			}
			if (ru!=null && !ru.equalsIgnoreCase("")) {
				DataManager.getManager().set("messages."+s+".russian", ru);
				rus++;
			}
		
		}
		
		BungeeCord.getInstance().getLogger().info("LOADED " + eng + " ENGLISH MESSAGES");
		BungeeCord.getInstance().getLogger().info("LOADED " + esp + " SPANISH MESSAGES");
		BungeeCord.getInstance().getLogger().info("LOADED " + rus + " RUSSIAN MESSAGES");
		 
	}

}
