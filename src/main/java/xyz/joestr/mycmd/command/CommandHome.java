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
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//Player
		if (commandSender instanceof Player) {
			
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.home") && !player.hasPermission("mycmd.command.home.other")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.home")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.home");
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes.getMap().containsKey(player.getUniqueId().toString())) {
						
						Location location = (Location)this.plugin.homes.getMap().get(player.getUniqueId().toString());
						player.teleport(location);
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Du wurdest zu deinem Home-Punkt teleportiert.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinen Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes.getMap().containsKey(player.getName())) {
					
					Location location = (Location)this.plugin.homes.getMap().get(player.getName());
					player.teleport(location);
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Du wurdest zu deinem Home-Punkt teleportiert.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinen Home-Punkt gesetzt.");
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.home.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.home.other");
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes.getMap().containsKey(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString())) {
						
						Location location = (Location)this.plugin.homes.getMap().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());
						player.teleport(location);
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Du wurdest zu Home-Punkt von " + ChatColor.GRAY + args[0] + ChatColor.BLUE + " teleportiert.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Spieler " + ChatColor.GRAY + args[0] + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes.getMap().containsKey(args[0])) {
					
					Location location = (Location)this.plugin.homes.getMap().get(args[0]);
					player.teleport(location);
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Du wurdest zu Home-Punkt von " + ChatColor.GRAY + args[0] + ChatColor.BLUE + " teleportiert.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Spieler " + ChatColor.GRAY + args[0] + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home.other") && player.hasPermission("mycmd.command.home")) {
				
				this.plugin.usageMessage(player, "/home [<Spieler>]", "suggest_command", "/home ", "/home [<Spieler>]");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home.other")) {
				
				this.plugin.usageMessage(player, "/home <Spieler>", "suggest_command", "/home ", "/home <Spieler>");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home")) {
				
				this.plugin.usageMessage(player, "/home", "run_command", "/home", "/home");
				return true;
			}
		}
		//End Player
		
		//Console
		if(args.length == 1) {
			
			if(Bukkit.getOnlineMode()) {
				
				if(this.plugin.homes.getMap().containsKey(Bukkit.getOfflinePlayer(label).getUniqueId().toString())) {
					
					Location location = (Location)this.plugin.homes.getMap().get(Bukkit.getOfflinePlayer(label).getUniqueId().toString());
					commandSender.sendMessage(
							this.plugin.pluginPrefix +
							ChatColor.BLUE + "Der Home-Punkt von " +
							ChatColor.GRAY + label +
							ChatColor.BLUE + " befindet sich bei " +
							ChatColor.GRAY + location.getWorld().getName() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() +
							ChatColor.BLUE + "."
					);
					return true;
				}
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + label + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
				return true;
			}
			
			if(this.plugin.homes.getMap().containsKey(label)) {
				
				Location location = (Location)this.plugin.homes.getMap().get(label);
				commandSender.sendMessage(
						this.plugin.pluginPrefix +
						ChatColor.BLUE + "Der Home-Punkt von " +
						ChatColor.GRAY + label +
						ChatColor.BLUE + " befindet sich bei " + 
						ChatColor.GRAY + location.getWorld().getName() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() +
						ChatColor.BLUE + "."
				);
				return true;
			}
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + label + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/home <Spieler>");
		return true;
		//End Console
	}
}
