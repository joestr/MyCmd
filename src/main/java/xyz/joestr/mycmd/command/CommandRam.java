package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.ram")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage());
				return true;
			}
			
			if (arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.ram")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.ram"));
					return true;
				}
				
				_ram_(sender);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ram")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/ram", "run_command", "/ram", "/ram"));
			}
			return true;
		}
		//End Player
		
		//Console
		if (arg.length == 0) {
			
			_ram_(sender);
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/ram"));
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
