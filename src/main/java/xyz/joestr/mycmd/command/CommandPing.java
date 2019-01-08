package xyz.joestr.mycmd.command;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandPing implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandPing(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	//https://board.nitrado.net/community-area/programmierung/hilfe/67728/spieler-ping-herrausbekommen/
	public int getPing(Player player) {
		
		Object nms_player = null;
		Field fieldPing = null;
		int ping = 0;
		
		try {
			
			nms_player = player.getClass().getMethod("getHandle").invoke(player);
			fieldPing = nms_player.getClass().getDeclaredField("ping");
			fieldPing.setAccessible(true);
			ping = fieldPing.getInt(nms_player);
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return ping;
	}
	//End https://board.nitrado.net/community-area/programmierung/hilfe/67728/spieler-ping-herrausbekommen/
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.ping") && !player.hasPermission("mycmd.command.ping.other")) {
				
				 this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.ping")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.ping");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Deine Ping-Zeit beträgt " + ChatColor.GRAY + getPing(player) + "ms" + ChatColor.BLUE + ".");
				return true;
			}
			
			if (arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.ping.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.ping.other");
					return true;
				}
				
				_ping_other_(sender, arg[0]);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ping.other") && player.hasPermission("mycmd.command.ping")) {
				
				this.plugin.usageMessage(player, "/ping [<Spieler>]", "suggest_command", "/ping ", "/ping [<Spieler>]");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ping.other")) {
				
				this.plugin.usageMessage(player, "/ping <Spieler>", "suggest_command", "/ping ", "/ping <Spieler>");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ping")) {
				
				this.plugin.usageMessage(player, "/ping", "run_command", "/ping", "/ping");
				return true;
			}
			
			this.plugin.noPermissionMessage(player, "mycmd.command.ping");
			return true;
		}
		//End Player
		
		//Console
		if (arg.length == 1) {
			
			_ping_other_(sender, arg[0]);
			return true;
		}
		
		this.plugin.usageMessage(sender, "/ping <Spieler>");
		return true;
		//End Console
	}
	
	@SuppressWarnings("deprecation")
	public void _ping_other_(CommandSender sender, String string) {
		
		if(!Bukkit.getOfflinePlayer(string).isOnline()) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Spieler " + ChatColor.GRAY + string + ChatColor.RED + " ist offline.");
			return;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Die Ping-Zeit vom Spieler " + Bukkit.getServer().getPlayer(string).getDisplayName() + ChatColor.BLUE + " beträgt " + ChatColor.GRAY + getPing(Bukkit.getPlayer(string)) + "ms" + ChatColor.BLUE + ".");
		return;
	}
}
