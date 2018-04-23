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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.seen")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.seen"));
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.seen")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.seen"));
					return true;
				}
				
				_seen_(sender, arg[0]);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.seen")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/seen <Spieler>", "suggest_command", "/seen ", "/seen <Spieler>"));
			}
			return true;
		}
		
		//Console
		if(arg.length == 1) {
			
			_seen_(sender, arg[0]);
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/seen <Spieler>"));
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void _seen_(CommandSender sender, String string) {
		
		if (Bukkit.getOfflinePlayer(string).isOnline()) {
			
			sender.sendMessage(ChatColor.GREEN + "Spieler " + Bukkit.getPlayer(string).getDisplayName() + ChatColor.GREEN + " ist online.");
			return;
		}
		
		if (Bukkit.getOfflinePlayer(string).getLastPlayed() == 0L) {
			
			sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " war nie zuvor auf diesem Server.");
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
		
		sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " ist seit " + text + ChatColor.GREEN + " offline.");
	}
}
