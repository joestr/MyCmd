package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandHome2 implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandHome2(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//Player
		if (commandSender instanceof Player) {
			
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.home2") && !player.hasPermission("mycmd.command.home2.other")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.home")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.home");
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes2.getMap().containsKey(player.getUniqueId().toString())) {
						
						Location location = (Location)this.plugin.homes2.getMap().get(player.getUniqueId().toString());
						player.teleport(location);
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Du wurdest zu deinem zweiten Home-Punkt teleportiert.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinen zweiten Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes2.getMap().containsKey(player.getName())) {
					
					Location location = (Location)this.plugin.homes2.getMap().get(player.getName());
					player.teleport(location);
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Du wurdest zu deinem zweiten Home-Punkt teleportiert.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinen zweiten Home-Punkt gesetzt.");
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.home.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.home.other");
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes2.getMap().containsKey(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString())) {
						
						Location location = (Location)this.plugin.homes2.getMap().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());
						player.teleport(location);
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Du wurdest zum zweiten Home-Punkt von " + ChatColor.GRAY + args[0] + ChatColor.BLUE + " teleportiert.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED + " hat noch keinen zweiten Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes2.getMap().containsKey(args[0])) {
					
					Location location = (Location)this.plugin.homes2.getMap().get(args[0]);
					player.teleport(location);
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Du wurdest zum zweiten Home-Punkt von " + ChatColor.GRAY + args[0] + ChatColor.BLUE + " teleportiert.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED + " hat noch keinen zweiten Home-Punkt gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home2.other") && player.hasPermission("mycmd.command.home2")) {
				
				this.plugin.usageMessage(player, "/home2 [<Spieler>]", "suggest_command", "/home2 ", "/home2 [<Spieler>]");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home2.other")) {
				
				this.plugin.usageMessage(player, "/home2 <Spieler>", "suggest_command", "/home2 ", "/home2 <Spieler>");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home2")) {
				
				this.plugin.usageMessage(player, "/home2", "run_command", "/home2", "/home2");
				return true;
			}
		}
		//End Player
		
		//Console
		if(args.length == 1) {
			
			if(Bukkit.getOnlineMode()) {
				
				if(this.plugin.homes2.getMap().containsKey(Bukkit.getOfflinePlayer(label).getUniqueId().toString())) {
					
					Location location = (Location)this.plugin.homes2.getMap().get(Bukkit.getOfflinePlayer(label).getUniqueId().toString());
					commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + label + ChatColor.BLUE + " befindet sich bei " + 
							ChatColor.GRAY + location.getWorld().getName() + "/" + ChatColor.GRAY + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ChatColor.BLUE + ".");
					return true;
				}
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + label + ChatColor.GREEN + " hat noch keinen zweiten Home-Punkt gesetzt.");
				return true;
			}
			
			if(this.plugin.homes2.getMap().containsKey(label)) {
				
				Location location = (Location)this.plugin.homes2.getMap().get(label);
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + label + ChatColor.BLUE + " befindet sich bei " + 
						ChatColor.GRAY + location.getWorld().getName() + "/" + ChatColor.GRAY + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ChatColor.BLUE + ".");
				return true;
			}
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Spieler " + ChatColor.GRAY + label + ChatColor.GREEN + " hat noch keinen zweiten Home-Punkt gesetzt.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/home2 <Spieler>");
		return true;
		//End Console
	}
}
