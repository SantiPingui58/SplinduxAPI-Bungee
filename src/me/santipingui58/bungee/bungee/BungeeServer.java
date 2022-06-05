package me.santipingui58.bungee.bungee;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;

public class BungeeServer {

	
	private String name;
	private ServerState state;
	
	public BungeeServer(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public ServerInfo getServerInfo() {
		return BungeeCord.getInstance().getServerInfo(name);
	}
	
	public ServerState getState() {
		return this.state;
	}
	
	public void setState(ServerState  ss) {
		this.state = ss;
	}
	
}
