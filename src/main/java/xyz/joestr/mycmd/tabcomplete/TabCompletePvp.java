package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class TabCompletePvp implements TabCompleter {
	
	MyCmd plugin;
	public TabCompletePvp(MyCmd myCmd) { this.plugin = myCmd; }
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg) {
		
		List<String> list = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(arg.length <= 1) {
				
				if(player.hasPermission("mycmd.command.pvp")) {
					
					list.add("on");list.add("off");list.add("list");list.add("status");
					
					if(arg.length == 1) {
						
						for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
			}
			
			if(arg.length <= 2) {
				
				if(!player.hasPermission("mycmd.command.pvp.other")) { return list; }
				
				if(arg[0].equalsIgnoreCase("status")) {
					
					for(OfflinePlayer pl : Bukkit.getServer().getOfflinePlayers()) { list.add(pl.getName()); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("on")) {
					
					for(OfflinePlayer pl : Bukkit.getServer().getOfflinePlayers()) { list.add(pl.getName()); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("off")) {
					
					for(OfflinePlayer pl : Bukkit.getServer().getOfflinePlayers()) { list.add(pl.getName()); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
			}
		}
		//End Player
		
		//Console
		if(arg.length <= 1) {
			
			list.add("on");list.add("off");list.add("list");list.add("status");
			
			if(arg.length == 1) {
				
				for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } }
				list = l;
			}
			
			return list;
		}
		
		if(arg.length <= 2) {
			
			if(arg[0].equalsIgnoreCase("status")) {
				
				for(OfflinePlayer pl : Bukkit.getServer().getOfflinePlayers()) { list.add(pl.getName()); }
				
				if(arg.length == 2) {
					
					for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
					list = l;
				}
				
				return list;
			}
			
			if(arg[0].equalsIgnoreCase("on")) {
				
				for(OfflinePlayer pl : Bukkit.getServer().getOfflinePlayers()) { list.add(pl.getName()); }
				
				if(arg.length == 2) {
					
					for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
					list = l;
				}
				
				return list;
			}
			
			if(arg[0].equalsIgnoreCase("off")) {
				
				for(OfflinePlayer pl : Bukkit.getServer().getOfflinePlayers()) { list.add(pl.getName()); }
				
				if(arg.length == 2) {
					
					for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
					list = l;
				}
				
				return list;
			}
		}
		
		return list;
		//End Console
	}
}
