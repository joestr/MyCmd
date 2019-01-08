package xyz.joestr.mycmd.event;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import xyz.joestr.mycmd.MyCmd;

public class EventKick implements Listener {
	
	MyCmd plugin;
	
	public EventKick(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnKick(PlayerKickEvent event) {
		
		if(Bukkit.getServer().getBanList(Type.NAME).isBanned(event.getPlayer().getName())) {
			
			event.setLeaveMessage("");
			this.plugin.kickEventList.add(event.getPlayer().getName());
			return;
		}
		
		if(Bukkit.getServer().getBanList(Type.NAME).isBanned(event.getPlayer().getAddress().toString().split(":")[0].replace("/", ""))) {
			
			event.setLeaveMessage("");
			this.plugin.kickEventList.add(event.getPlayer().getName());
			return;
		}
		
		event.setLeaveMessage(
				
			this.plugin.toColorcode("&", ((String)this.plugin.config.getMap().get("kick")))
			.replace("%player_displayname%", event.getPlayer().getDisplayName()).replace("%player%", event.getPlayer().getName())
			.replace("%reason%", event.getReason())
		);
		
		this.plugin.kickEventList.add(event.getPlayer().getName());
		Bukkit.broadcastMessage(event.getLeaveMessage());
	}
}
