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
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if (sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.home2") && !player.hasPermission("mycmd.command.home2.other")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.home")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.home"));
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes2.getMap().containsKey(player.getUniqueId().toString())) {
						
						Location location = (Location)this.plugin.homes2.getMap().get(player.getUniqueId().toString());
						player.teleport(location);
						player.sendMessage(ChatColor.GREEN + "Du wurdest zu deinem zweiten Home-Punkt teleportiert.");
						return true;
					}
					
					player.sendMessage(ChatColor.RED + "Du hast noch keinen zweiten Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes2.getMap().containsKey(player.getName())) {
					
					Location location = (Location)this.plugin.homes2.getMap().get(player.getName());
					player.teleport(location);
					player.sendMessage(ChatColor.GREEN + "Du wurdest zu deinem zweiten Home-Punkt teleportiert.");
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Du hast noch keinen zweiten Home-Punkt gesetzt.");
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.home.other")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.home.other"));
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes2.getMap().containsKey(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString())) {
						
						Location location = (Location)this.plugin.homes2.getMap().get(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString());
						player.teleport(location);
						player.sendMessage(ChatColor.GREEN + "Du wurdest zum zweiten Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " teleportiert.");
						return true;
					}
					
					player.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " hat noch keinen zweiten Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes2.getMap().containsKey(arg[0])) {
					
					Location location = (Location)this.plugin.homes2.getMap().get(arg[0]);
					player.teleport(location);
					player.sendMessage(ChatColor.GREEN + "Du wurdest zum zweiten Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " teleportiert.");
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " hat noch keinen zweiten Home-Punkt gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home2.other") && player.hasPermission("mycmd.command.home2")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/home2 [<Spieler>]", "suggest_command", "/home2 ", "/home2 [<Spieler>]"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home2.other")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/home2 <Spieler>", "suggest_command", "/home2 ", "/home2 <Spieler>"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.home2")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/home2", "run_command", "/home2", "/home2"));
				return true;
			}
		}
		//End Player
		
		//Console
		if(arg.length == 1) {
			
			if(Bukkit.getOnlineMode()) {
				
				if(this.plugin.homes2.getMap().containsKey(Bukkit.getOfflinePlayer(string).getUniqueId().toString())) {
					
					Location location = (Location)this.plugin.homes2.getMap().get(Bukkit.getOfflinePlayer(string).getUniqueId().toString());
					sender.sendMessage(ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " befindet sich bei " + 
							ChatColor.GRAY + location.getWorld().getName() + "/" + ChatColor.GRAY + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ChatColor.GREEN + ".");
					return true;
				}
				
				sender.sendMessage(ChatColor.GRAY + string + ChatColor.GREEN + " hat noch keinen zweiten Home-Punkt gesetzt.");
				return true;
			}
			
			if(this.plugin.homes2.getMap().containsKey(string)) {
				
				Location location = (Location)this.plugin.homes2.getMap().get(string);
				sender.sendMessage(ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " befindet sich bei " + 
						ChatColor.GRAY + location.getWorld().getName() + "/" + ChatColor.GRAY + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ChatColor.GREEN + ".");
				return true;
			}
			
			sender.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " hat noch keinen zweiten Home-Punkt gesetzt.");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/home2 <Spieler>"));
		return true;
		//End Console
	}
}
