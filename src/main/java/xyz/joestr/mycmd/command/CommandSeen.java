package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandSeen implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandSeen(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.seen")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.seen");
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.seen")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.seen");
					return true;
				}
				
				_seen_(commandSender, args[0]);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.seen")) {
				
				this.plugin.usageMessage(player, "/seen <Spieler>", "suggest_command", "/seen ", "/seen <Spieler>");
			}
			return true;
		}
		
		//Console
		if(args.length == 1) {
			
			_seen_(commandSender, args[0]);
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/seen <Spieler>");
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void _seen_(CommandSender sender, String string) {
		
		if (Bukkit.getOfflinePlayer(string).isOnline()) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + Bukkit.getPlayer(string).getName() + ChatColor.GREEN + " ist online.");
			return;
		}
		
		if (Bukkit.getOfflinePlayer(string).getLastPlayed() == 0L) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + string + ChatColor.GREEN + " war nie zuvor auf diesem Server.");
			return;
		}
		
		int SECOND = 1000; int MINUTE = 60000; int HOUR = 3600000; int DAY = 86400000;
		long ms = System.currentTimeMillis() - Bukkit.getOfflinePlayer(string).getLastPlayed();
		StringBuffer text = new StringBuffer("");
		
		if(ms > 86400000L) {
			
			text.append(ChatColor.GRAY).append(ms / DAY).append("d" + ChatColor.GREEN + ", ");
			ms %= 86400000L;
		}
		
		if(ms > 3600000L) {
			
			text.append(ChatColor.GRAY).append(ms / HOUR).append("h" + ChatColor.GREEN + ", ");
			ms %= 3600000L;
		}
		
		if(ms > 60000L) {
			
			text.append(ChatColor.GRAY).append(ms / MINUTE).append("m" + ChatColor.GREEN + ", ");
			ms %= 60000L;
		}
		
		//if(ms > 1000L) {
			
			text.append(ChatColor.GRAY).append(ms / SECOND).append("s");
			ms %= 1000L;
		//}
		
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + string + ChatColor.GREEN + " ist seit " + text + ChatColor.GREEN + " offline.");
	}
}
