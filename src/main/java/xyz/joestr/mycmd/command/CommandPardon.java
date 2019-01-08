package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandPardon implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandPardon(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.pardon")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.pardon");
				return true;
			}
			
			if(args.length >= 2) {
				
				if(!Bukkit.getServer().getBanList(Type.NAME).isBanned(args[0])) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED + " ist nicht gebannt.");
					return true;
				}
				
				String message = "";
				
				for(int i = 1; i < args.length; i++) { 
					
					if(i + 1 == args.length) { message += args[i]; continue; }
					message += args[i] + " ";
				}
				
				Bukkit.getServer().getBanList(Type.NAME).pardon(args[0]);
				Bukkit.getServer().broadcastMessage(
						this.plugin.toColorcode(
								"&",
								((String)this.plugin.config.getMap().get("pardon"))
								.replace("%player%", args[0])
								.replace("%reason%", message)
						)
				);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.pardon")) {
				
				this.plugin.usageMessage(player, "/pardon <Spieler> <Grund ...>", "suggest_command", "/pardon ", "/pardon <Spieler> <Grund ...>");
			}
			
			return true;
		}
		
		//Console
		if(args.length >= 2) {
			
			if(!Bukkit.getServer().getBanList(Type.NAME).isBanned(args[0])) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED + " ist nicht gebannt.");
				return true;
			}
			
			String message = "";
			
			for(int i = 1; i < args.length; i++) { 
				
				if(i + 1 == args.length) { message += args[i]; continue; }
				message += args[i] + " ";
			}
			
			Bukkit.getServer().getBanList(Type.NAME).pardon(args[0]);
			Bukkit.getServer().broadcastMessage(
					this.plugin.toColorcode(
							"&",
							((String)this.plugin.config.getMap().get("pardon"))
							.replace("%player%", args[0])
							.replace("%reason%", message)
					)
			);
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/pardon <Spieler> <Grund ...>");
		return true;
	}
}
