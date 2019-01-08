package xyz.joestr.mycmd.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandRam implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandRam(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//Player
		if(commandSender instanceof Player) {
			
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.ram")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if (args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.ram")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.ram");
					return true;
				}
				
				_ram_(commandSender);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ram")) {
				
				this.plugin.usageMessage(player, "/ram", "run_command", "/ram", "/ram");
			}
			return true;
		}
		//End Player
		
		//Console
		if (args.length == 0) {
			
			_ram_(commandSender);
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/ram");
		return true;
		//End Console
	}
	
	private void _ram_(CommandSender sender) {
		
		Runtime runtime = Runtime.getRuntime();
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Maximaler RAM: " + ChatColor.GRAY + (int)(runtime.maxMemory() / 1000000L) + "MB");
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Reservierter RAM: " + ChatColor.GRAY + (int)(runtime.totalMemory() / 1000000L) + "MB");
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Freier RAM: " + ChatColor.GRAY + (int)(runtime.freeMemory() / 1000000L) + "MB");
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Belegter RAM: " + ChatColor.GRAY + ((int)(runtime.totalMemory() / 1000000L) - (int)(runtime.freeMemory() / 1000000L)) + "MB");
	}
}
