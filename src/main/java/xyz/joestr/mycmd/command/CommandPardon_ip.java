package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandPardon_ip implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandPardon_ip(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.pardon")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.pardon"));
				return true;
			}
			
			if(arg.length >= 2) {
				
				if(!Bukkit.getServer().getBanList(Type.IP).isBanned(arg[0])) {
					
					player.sendMessage(ChatColor.RED + "IP " + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist nicht gebannt.");
					return true;
				}
				
				String message = "";
				
				for(int i = 1; i < arg.length; i++) { 
					
					if(i + 1 == arg.length) { message += arg[i]; continue; }
					message += arg[i] + " ";
				}
				
				Bukkit.getServer().getBanList(Type.IP).pardon(arg[0]);
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "IP " + ChatColor.GRAY + arg[0] + ChatColor.YELLOW +" wurde entbannt. (" + ChatColor.GRAY + message + ChatColor.YELLOW + ")");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.pardon")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/pardon-ip <IP> <Grund ...>", "suggest_command", "/pardon-ip ", "/pardon-ip <IP> <Grund ...>"));
			}
			
			return true;
		}
		
		//Console
		if(arg.length >= 2) {
			
			if(!Bukkit.getServer().getBanList(Type.IP).isBanned(arg[0])) {
				
				sender.sendMessage(ChatColor.RED + "IP " + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist nicht gebannt.");
				return true;
			}
			
			String message = "";
			
			for(int i = 1; i < arg.length; i++) { 
				
				if(i + 1 == arg.length) { message += arg[i]; continue; }
				message += arg[i] + " ";
			}
			
			Bukkit.getServer().getBanList(Type.NAME).pardon(arg[0]);
			Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "IP " + ChatColor.GRAY + arg[0] + ChatColor.YELLOW +" wurde entbannt. (" + ChatColor.GRAY + message + ChatColor.YELLOW + ")");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/pardon-ip <IP> <Grund ...>"));
		return true;
	}
}
