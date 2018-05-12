package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandHome implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandHome(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if (sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.home") && !player.hasPermission("mycmd.command.home.other")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.home")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.home"));
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes.getMap().containsKey(player.getUniqueId().toString())) {
						
						Location location = (Location)this.plugin.homes.getMap().get(player.getUniqueId().toString());
						player.teleport(location);
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du wurdest zu deinem Home-Punkt teleportiert.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinen Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes.getMap().containsKey(player.getName())) {
					
					Location location = (Location)this.plugin.homes.getMap().get(player.getName());
					player.teleport(location);
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du wurdest zu deinem Home-Punkt teleportiert.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinen Home-Punkt gesetzt.");
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.home.other")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.home.other"));
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes.getMap().containsKey(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString())) {
						
						Location location = (Location)this.plugin.homes.getMap().get(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString());
						player.teleport(location);
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du wurdest zu Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " teleportiert.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes.getMap().containsKey(arg[0])) {
					
					Location location = (Location)this.plugin.homes.getMap().get(arg[0]);
					player.teleport(location);
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du wurdest zu Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " teleportiert.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home.other") && player.hasPermission("mycmd.command.home")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/home [<Spieler>]", "suggest_command", "/home ", "/home [<Spieler>]"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home.other")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/home <Spieler>", "suggest_command", "/home ", "/home <Spieler>"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/home", "run_command", "/home", "/home"));
				return true;
			}
		}
		//End Player
		
		//Console
		if(arg.length == 1) {
			
			if(Bukkit.getOnlineMode()) {
				
				if(this.plugin.homes.getMap().containsKey(Bukkit.getOfflinePlayer(string).getUniqueId().toString())) {
					
					Location location = (Location)this.plugin.homes.getMap().get(Bukkit.getOfflinePlayer(string).getUniqueId().toString());
					sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Home-Punkt von " + ChatColor.GRAY + string + ChatColor.GREEN + " befindet sich bei " +
							ChatColor.GRAY + location.getWorld().getName() + "/" + ChatColor.GRAY + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ChatColor.GREEN + ".");
					return true;
				}
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + string + ChatColor.GREEN + " hat noch keinen Home-Punkt gesetzt.");
				return true;
			}
			
			if(this.plugin.homes.getMap().containsKey(string)) {
				
				Location location = (Location)this.plugin.homes.getMap().get(string);
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Home-Punkt vom Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " befindet sich bei " + 
						ChatColor.GRAY + location.getWorld().getName() + "/" + ChatColor.GRAY + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ChatColor.GREEN + ".");
				return true;
			}
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " hat noch keinen Home-Punkt gesetzt.");
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/home <Spieler>"));
		return true;
		//End Console
	}
}
