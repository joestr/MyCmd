package xyz.joestr.mycmd.event;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import net.md_5.bungee.api.ChatColor;
import xyz.joestr.mycmd.MyCmd;

public class EventLogin
	implements Listener
{
	MyCmd plugin;
	
	public EventLogin(MyCmd mycmd)
	{
		this.plugin = mycmd;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnLogin(PlayerLoginEvent event)
	{
		if(!event.getPlayer().getName().matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_]{1,16}")) {
			
			event.disallow(null, ChatColor.RED + "Dein Spielername beinhaltet ungültige Zeichen, ist zu kurz oder zu lang. Erlaubte Zeichen: a-z A-Z 0-9 _ Länge: 1 bis 16");
			return;
		}
		
		if(Bukkit.getServer().hasWhitelist()) {
			
			if(!event.getPlayer().isWhitelisted()) {
				
				event.disallow(null, this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("whitelist-message")));
				return;
			}
		}
		
		if(Bukkit.getServer().getBanList(Type.NAME).isBanned(event.getPlayer().getName())) {
			
			event.disallow(null, Bukkit.getServer().getBanList(Type.NAME).getBanEntry(event.getPlayer().getName()).getReason());
			return;
		}
		
		String ip = event.getRealAddress().toString().split(":")[0].replace("/", "");
		
		if(Bukkit.getServer().getBanList(Type.IP).isBanned(ip)) {
			
			event.disallow(null, Bukkit.getServer().getBanList(Type.IP).getBanEntry(ip).getReason());
			return;
		}
		
		for (String str : this.plugin.ranks.getMap().keySet())
		{
			if (event.getPlayer().hasPermission("mycmd.rank." + str))
			{
				this.plugin.scoreboard.getTeam(str).addEntry(event.getPlayer().getName());
				event.getPlayer().setDisplayName(this.plugin.scoreboard.getTeam(str).getDisplayName() + event.getPlayer().getName());
				//event.getPlayer().setPlayerListName(this.plugin.scoreboard.getTeam(str).getPrefix() + event.getPlayer().getName() + this.plugin.scoreboard.getTeam(str).getSuffix());
			}
		}
	}
}
