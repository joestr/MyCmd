package xyz.joestr.mycmd.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.ChatColor;

import xyz.joestr.mycmd.MyCmd;

public class EventTeleport implements Listener {
	
	MyCmd plugin;
	
	public EventTeleport(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnTeleport(PlayerTeleportEvent event) {
		
		if(this.plugin.pvpList.containsKey(event.getPlayer().getName())) {
			
			if(event.getCause() == TeleportCause.COMMAND || event.getCause() == TeleportCause.PLUGIN || event.getCause() == TeleportCause.UNKNOWN) {
				
				event.getPlayer().sendMessage(ChatColor.RED + "Du befindest dich in einem PvP-Kampf.");
				event.setCancelled(true);
				return;
			}
		}
		
		event.getTo().getWorld().loadChunk(event.getTo().getChunk());
	}
}
