package xyz.joestr.mycmd.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import xyz.joestr.mycmd.MyCmd;

public class EventAsyncChat implements Listener {
	
	MyCmd plugin;
	
	public EventAsyncChat(MyCmd mycmd)
	{
		this.plugin = mycmd;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnAsyncChat(AsyncPlayerChatEvent event)
	{
		if(event.getPlayer().hasPermission("mycmd.chatcolor")) {
			event.setMessage(
					this.plugin.toColorcode("&", event.getMessage())
			);
		}
		
		event.setFormat(
			this.plugin.toColorcode(
					"&",
					((String)this.plugin.config.getMap().get("chat"))
					.replace("%player_displayname%", event.getPlayer().getDisplayName())
					.replace("%player%", event.getPlayer().getName())
			)
			+ event.getMessage()
		);
	}
}
