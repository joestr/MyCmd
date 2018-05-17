package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Collection;

import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandBanlist implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandBanlist(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.banlist")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.banlist");
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.banlist")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.banlist");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("ip")) {
					
					_banlist_ip_(commandSender);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("player")) {
					
					_banlist_player_(commandSender);
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.banlist")) {
				
				this.plugin.usageMessage(player, "/banlist <ip|player>", "suggest_command", "/banlist ", "/banlist <ip|player>");
				return true;
			}
		}
		
		//Console
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("ip")) {
				
				_banlist_ip_(commandSender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("player")) {
				
				_banlist_player_(commandSender);
				return true;
			}
		}
		
		this.plugin.usageMessage(commandSender, "/banlist <ip|player>");
		return true;
	}
	
	public void _banlist_ip_(CommandSender commandSender) {
		
		String str = "";
		Collection<BanEntry> a = Bukkit.getServer().getBanList(Type.IP).getBanEntries();
		int i = 0;
		
		for (BanEntry be : a) {
			
			i++;
			if(i < a.toArray().length) {
				
				str = str + ChatColor.GRAY + be.getTarget() + ChatColor.BLUE + ", ";
			} else {
				
				str = str + ChatColor.GRAY + be.getTarget();
			}
		}
		
		if(a.toArray().length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Es sind " + ChatColor.GRAY + "keine" + ChatColor.BLUE + " IP-Adressen gebannt.");
		}
		
		if(a.toArray().length == 1) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Es ist " + ChatColor.GRAY + a.toArray().length + ChatColor.BLUE + " IP-Adresse gebannt:");
			commandSender.sendMessage(this.plugin.pluginPrefix + str);
		}
		
		if(a.toArray().length >= 2) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Es sind " + ChatColor.GRAY + a.toArray().length + ChatColor.BLUE + " IP-Adressen gebannt:");
			commandSender.sendMessage(this.plugin.pluginPrefix + str);
		}
	}
	
	public void _banlist_player_(CommandSender commandSender) {
		
		String str = "";
		Collection<BanEntry> a = Bukkit.getServer().getBanList(Type.NAME).getBanEntries();
		int i = 0;
		
		for (BanEntry be : a) {
			
			i++;
			if(i < a.toArray().length) {
				
				str = str + ChatColor.GRAY + be.getTarget() + ChatColor.BLUE + ", ";
			} else {
				
				str = str + ChatColor.GRAY + be.getTarget();
			}
		}
		
		if(a.toArray().length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Es sind " + ChatColor.GRAY + "keine" + ChatColor.BLUE + " Spieler gebannt.");
		}
		
		if(a.toArray().length == 1) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Es ist " + ChatColor.GRAY + a.toArray().length + ChatColor.BLUE + " Spieler gebannt:");
			commandSender.sendMessage(this.plugin.pluginPrefix + str);
		}
		
		if(a.toArray().length >= 2) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Es sind " + ChatColor.GRAY + a.toArray().length + ChatColor.BLUE + " Spieler gebannt:");
			commandSender.sendMessage(this.plugin.pluginPrefix + str);
		}
	}
}
