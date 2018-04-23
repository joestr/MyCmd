package xyz.joestr.mycmd.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import xyz.joestr.mycmd.MyCmd;

public class EventListPing implements Listener {
	
	MyCmd plugin;
	
	public EventListPing(MyCmd mycmd)
	{
		this.plugin = mycmd;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnListPing(ServerListPingEvent event) {
		
		event.setMotd(this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1")) + "\n" + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd2")));
		return;
	}
	
}
