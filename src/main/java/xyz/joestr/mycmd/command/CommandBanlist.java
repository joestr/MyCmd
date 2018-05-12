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
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.banlist")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.banlist");
				return true;
			}
			
			if(arg.length == 1) {
				
				if(arg[0].equalsIgnoreCase("ip")) {
					
					_banlist_ip_(sender);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("player")) {
					
					_banlist_player_(sender);
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.banlist")) {
				
				this.plugin.usageMessage(player, "/banlist <ip|player>", "suggest_command", "/banlist ", "/banlist <ip|player>");
				return true;
			}
		}
		
		//Console
		if(arg.length == 1) {
			
			if(arg[0].equalsIgnoreCase("ip")) {
				
				_banlist_ip_(sender);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("player")) {
				
				_banlist_player_(sender);
				return true;
			}
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/banlist <ip|player>"));
		return true;
	}
	
	public void _banlist_ip_(CommandSender sender) {
		
		String str = "";
		Collection<BanEntry> a = Bukkit.getServer().getBanList(Type.IP).getBanEntries();
		int i = 0;
		
		for (BanEntry be : a) {
			
			i++;
			if(i < a.toArray().length) {
				
				str = str + ChatColor.GRAY + be.getTarget() + ChatColor.GREEN + ", ";
			} else {
				
				str = str + ChatColor.GRAY + be.getTarget();
			}
		}
		
		if(a.toArray().length == 0) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es sind " + ChatColor.GRAY + "keine" + ChatColor.GREEN + " IP-Adressen gebannt.");
		}
		
		if(a.toArray().length == 1) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es ist " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " IP-Adresse gebannt:");
			sender.sendMessage(this.plugin.pluginPrefix + str);
		}
		
		if(a.toArray().length >= 2) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es sind " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " IP-Adressen gebannt:");
			sender.sendMessage(this.plugin.pluginPrefix + str);
		}
	}
	
	public void _banlist_player_(CommandSender sender) {
		
		String str = "";
		Collection<BanEntry> a = Bukkit.getServer().getBanList(Type.NAME).getBanEntries();
		int i = 0;
		
		for (BanEntry be : a) {
			
			i++;
			if(i < a.toArray().length) {
				
				str = str + ChatColor.GRAY + be.getTarget() + ChatColor.GREEN + ", ";
			} else {
				
				str = str + ChatColor.GRAY + be.getTarget();
			}
		}
		
		if(a.toArray().length == 0) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es sind " + ChatColor.GRAY + "keine" + ChatColor.GREEN + " Spieler gebannt.");
		}
		
		if(a.toArray().length == 1) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es ist " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler gebannt:");
			sender.sendMessage(this.plugin.pluginPrefix + str);
		}
		
		if(a.toArray().length >= 2) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es sind " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler gebannt:");
			sender.sendMessage(this.plugin.pluginPrefix + str);
		}
	}
}
