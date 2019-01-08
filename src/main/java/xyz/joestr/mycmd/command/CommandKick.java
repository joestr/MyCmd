package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandKick implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandKick(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.kick")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.kick");
				return true;
			}
			
			if(args.length >= 2) {
				
				if(!player.hasPermission("mycmd.command.kick")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.kick");
					return true;
				}
				
				String message = "";
				
				for(int i = 1; i < args.length; i++) { 
					
					if(i + 1 == args.length) { message += args[i]; continue; }
					message += args[i] + " ";
				}
				
				_kick_(commandSender, args[0], message);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.kick")) {
				
				this.plugin.usageMessage(player, "/kick <Spieler> <Grund ...>", "suggest_command", "/kick ", "/kick <Spieler> <Grund ...>");
				return true;
			}
			
			return true;
		}
		
		//Console
		if(args.length >= 2) {
			
			String message = "";
			
			for(int i = 1; i < args.length; i++) { 
				
				if(i + 1 == args.length) { message += args[i]; continue; }
				message += args[i] + " ";
			}
			
			_kick_(commandSender, args[0], message);
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/kick <Spieler> <Grund ...>");
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void _kick_(CommandSender sender, String string, String string2) {
		
		if(Bukkit.getOfflinePlayer(string).isOnline()) {
			
			Bukkit.getPlayer(string).kickPlayer(
					this.plugin.toColorcode("&", string2)
			);
			return;
		}
		
		/*
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
		
		if(ms > 1000L) {
			
			text.append(ChatColor.GRAY).append(ms / SECOND).append("s");
			ms %= 1000L;
		}
		*/
		
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + string + ChatColor.RED + " ist offline.");
	}
}
