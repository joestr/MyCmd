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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.ping") && !player.hasPermission("mycmd.command.ping.other")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.ping")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.ping"));
					return true;
				}
				
				player.sendMessage(ChatColor.GREEN + "Deine Ping-Zeit beträgt " + ChatColor.GRAY + getPing(player) + "ms" + ChatColor.GREEN + ".");
				return true;
			}
			
			if (arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.ping.other")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.ping.other"));
					return true;
				}
				
				_ping_other_(sender, arg[0]);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ping.other") && player.hasPermission("mycmd.command.ping")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/ping [<Spieler>]", "suggest_command", "/ping ", "/ping [<Spieler>]"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ping.other")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/ping <Spieler>", "suggest_command", "/ping ", "/ping <Spieler>"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ping")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/ping", "run_command", "/ping", "/ping"));
				return true;
			} else {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.ping"));
				return true;
			}
		}
		//End Player
		
		//Console
		if (arg.length == 1) {
			
			_ping_other_(sender, arg[0]);
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/ping <Spieler>"));
		return true;
		//End Console
	}
	
	@SuppressWarnings("deprecation")
	public void _ping_other_(CommandSender sender, String string) {
		
		if(!Bukkit.getOfflinePlayer(string).isOnline()) {
			
			sender.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + string + ChatColor.RED + " ist offline.");
			return;
		}
		
		sender.sendMessage(ChatColor.GREEN + "Die Ping-Zeit vom Spieler " + Bukkit.getServer().getPlayer(string).getDisplayName() + ChatColor.GREEN + " beträgt " + ChatColor.GRAY + getPing(Bukkit.getPlayer(string)) + "ms" + ChatColor.GREEN + ".");
		return;
	}
}
