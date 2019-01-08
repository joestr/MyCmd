package xyz.joestr.mycmd.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.joestr.mycmd.MyCmd;

public class EventQuit implements Listener {
	
	MyCmd plugin;
	
	public EventQuit(MyCmd mycmd) { this.plugin = mycmd; }
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnQuit(PlayerQuitEvent event) {
		
		if((this.plugin.whisper.containsKey(event.getPlayer().getName())) && (this.plugin.whisper.containsKey(this.plugin.whisper.get(event.getPlayer().getName())))) {
			
			if(((String)this.plugin.whisper.get(event.getPlayer().getName())).equals(Boolean.valueOf(this.plugin.whisper.containsKey(this.plugin.whisper.get(event.getPlayer().getName()))))) {
				
				this.plugin.whisper.remove(this.plugin.whisper.get(event.getPlayer().getName()));
				this.plugin.whisper.remove(event.getPlayer().getName());
			}
		}
		
		if(this.plugin.kickEventList.contains(event.getPlayer().getName())) {
			
			this.plugin.kickEventList.remove(event.getPlayer().getName());
			event.setQuitMessage("");
		} else {
		
			event.setQuitMessage(
					
				this.plugin.toColorcode("&",
					
					((String)this.plugin.config.getMap().get("quit"))
					.replace("%player_displayname%", event.getPlayer().getDisplayName())
					.replace("%player%", event.getPlayer().getName())
				)
			);
		}
	}
}
