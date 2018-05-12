package xyz.joestr.mycmd.command.playercommand;

import java.util.regex.Pattern;

import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.BanList.Type;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class PlayerCommandBan_ip {

	MyCmd plugin = null;
	
	public PlayerCommandBan_ip(MyCmd myCmd) {
		
		this.plugin = myCmd;
	}
	
	public boolean process(Player player, String[] args) {
		
		if(!player.hasPermission("mycmd.command.ban-ip")) {
			
			this.plugin.noPermissionMessage(player, "mycmd.command.ban-ip");
			return true;
		}
		
		if(args.length >= 2) {
			
			if(!player.hasPermission("mycmd.command.ban-ip")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.ban-ip");
				return true;
			}
			
			Pattern p = Pattern.compile("(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))");
			if(!args[0].matches(p.toString())) {
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Es muss eine g�ltige IP-Adresse angegeben werden."); 
				return true;
			}
			
			String message = "";
			
			for(int i = 1; i < args.length; i++) { 
				
				if(i + 1 == args.length) { message += args[i]; continue; }
				message += args[i] + " ";
			}
			
			Bukkit.getServer().getBanList(Type.IP).addBan(args[0], message, null, player.getName());
			
			BanEntry banEntry = Bukkit.getServer().getBanList(Type.IP).getBanEntry(args[0]);
			
			String kickString =
					this.plugin.config.getMap().get("ban-screen").toString()
					.replace("%type%", Type.IP.toString())
					.replace("%target%", banEntry.getTarget())
					.replace("%reason%", banEntry.getReason())
					.replace("%source%", banEntry.getSource())
					.replace("%created%", banEntry.getCreated().toInstant().toString())
					.replace("%expires%", banEntry.getExpiration() == null ? "-" : banEntry.getExpiration().toInstant().toString());
			
			for(Player pl : Bukkit.getOnlinePlayers()) {
				
				if(pl.getAddress().toString().contains(args[0])) {
					
					pl.kickPlayer(
							this.plugin.toColorcode("&", kickString)
					);
				}
			}
			
			Bukkit.getServer().broadcastMessage(
					this.plugin.toColorcode(
							"&",
							((String)this.plugin.config.getMap().get("ban-ip"))
							.replace("%ip%", args[0])
							.replace("%reason%", message)
					)
			);
			
			return true;
		}
		
		if(player.hasPermission("mycmd.command.ban-ip")) {
			
			this.plugin.usageMessage(player, "/ban-ip <Adresse> <Grund ...>", "suggest_command", "/ban-ip ", "/ban-ip <IP-Adresse> <Grund ...>");
			return true;
		}
		
		return true;
	}
}
