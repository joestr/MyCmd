package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class TabCompleteRoom implements TabCompleter {
	
	MyCmd plugin;
	public TabCompleteRoom(MyCmd myCmd) { this.plugin = myCmd; }
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg) {
		
		List<String> list = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(arg.length <= 1) {
				
				if(player.hasPermission("mycmd.command.room")) {
					
					list.add("create");
					list.add("join");
					list.add("write");
					list.add("leave");
					list.add("op");
					list.add("deop");
					list.add("kick");
					list.add("password");
					list.add("delete");
					list.add("list");
					list.add("info");
					
					if(arg.length == 1) {
						
						for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
			}
			
			if(arg.length <= 2) {
				
				if(!player.hasPermission("mycmd.command.room")) { return list; }
				
				if(arg[0].equalsIgnoreCase("join")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("leave")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("info")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("deop")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("op")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("kick")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("password")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("write")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
					if(arg.length == 2) {
						
						for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				if(arg[0].equalsIgnoreCase("delete")) {
					
					for(String pl : this.plugin.rooms.getMap().keySet()) { list.add(pl); }
					
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
		
		return list;
		//End Console
	}
}
