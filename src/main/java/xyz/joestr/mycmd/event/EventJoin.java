package xyz.joestr.mycmd.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


import xyz.joestr.mycmd.MyCmd;

public class EventJoin implements Listener {
	
	MyCmd plugin;
	
	public EventJoin(MyCmd mycmd) { this.plugin = mycmd; }
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnJoin(PlayerJoinEvent event) {
		
		event.getPlayer().setScoreboard(this.plugin.scoreboard);
		this.plugin.sendPlayerlistHeaderFooter(event.getPlayer(), this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1")), this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd2")));
		event.getPlayer().sendMessage(this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1") + "\n" + ChatColor.RESET + (String)this.plugin.config.getMap().get("motd2")));
		event.setJoinMessage(this.plugin.toColorcode("&", ((String)this.plugin.config.getMap().get("join")).replace("%player_displayname%", event.getPlayer().getDisplayName()).replace("%player%", event.getPlayer().getName())));
		return;
	}
}
