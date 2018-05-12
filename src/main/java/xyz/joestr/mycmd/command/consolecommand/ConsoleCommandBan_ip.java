package xyz.joestr.mycmd.command.consolecommand;

import java.util.regex.Pattern;

import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.BanList.Type;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class ConsoleCommandBan_ip {
	
	MyCmd plugin = null;
	
	public ConsoleCommandBan_ip(MyCmd myCmd) {
		
		this.plugin = myCmd;
	}
	
	public boolean process(ConsoleCommandSender consoleCommandSender, String[] args) {
		
		if(args.length >= 2) {
			
			Pattern p = Pattern.compile("(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))");
			if(!args[0].matches(p.toString())) {
				
				consoleCommandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Es muss eine gültige IP-Adresse angegeben werden.");
				return true;
			}
			
			String message = "";
			
			for(int i = 1; i < args.length; i++) { 
				
				if(i + 1 == args.length) { message += args[i]; continue; }
				message += args[i] + " ";
			}
			
			Bukkit.getServer().getBanList(Type.IP).addBan(args[0], message, null, "KONSOLE");

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
		
		consoleCommandSender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/ban-ip <IP-Adresse> <Grund ...>"));
		return true;
	}
}
