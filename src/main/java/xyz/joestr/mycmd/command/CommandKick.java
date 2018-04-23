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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.kick")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.kick"));
				return true;
			}
			
			if(arg.length >= 2) {
				
				if(!player.hasPermission("mycmd.command.kick")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.kick"));
					return true;
				}
				
				String message = "";
				
				for(int i = 1; i < arg.length; i++) { 
					
					if(i + 1 == arg.length) { message += arg[i]; continue; }
					message += arg[i] + " ";
				}
				
				_kick_(sender, arg[0], message);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.kick")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/kick <Spieler> <Grund ...>", "suggest_command", "/kick ", "/kick <Spieler> <Grund ...>"));
			}
			
			return true;
		}
		
		//Console
		if(arg.length >= 2) {
			
			String message = "";
			
			for(int i = 1; i < arg.length; i++) { 
				
				if(i + 1 == arg.length) { message += arg[i]; continue; }
				message += arg[i] + " ";
			}
			
			_kick_(sender, arg[0], message);
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/kick <Spieler> <Grund ...>"));
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void _kick_(CommandSender sender, String string, String string2) {
		
		if(Bukkit.getOfflinePlayer(string).isOnline()) {
			
			Bukkit.getPlayer(string).kickPlayer(string2);
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
		
		sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " ist offline.");
	}
}
