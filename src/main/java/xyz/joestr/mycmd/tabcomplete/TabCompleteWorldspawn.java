package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class TabCompleteWorldspawn implements TabCompleter {
	
	MyCmd plugin;
	
	public TabCompleteWorldspawn(MyCmd myCmd) { this.plugin = myCmd; }
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg) {
		
		List<String> list = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(arg.length <= 1) {
				
				if(player.hasPermission("mycmd.command.worldspawn")) {
					
					for(World w : Bukkit.getWorlds()) { list.add(w.getName()); }
					
					if(arg.length == 1) { for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } } list = l; }
					
					return list;
				}
				
				return list;
			}
			
			if(arg.length <= 2) {
				
				if(player.hasPermission("mycmd.command.worldspawn.other")) {
					
					for(Player p : Bukkit.getOnlinePlayers()) { list.add(p.getName()); }
					list.remove(player.getName());
					
					if(arg.length == 1) { for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } } list = l; }
					
					return list;
				}
				
				return list;
			}
			
			return list;
		}
		
		//Console
		if(arg.length <= 1) {
				
			for(World w : Bukkit.getWorlds()) { list.add(w.getName()); }
			
			if(arg.length == 1) { for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } } list = l; }
			
			return list;
		}
		
		if(arg.length <= 2) {
			
			for(Player p : Bukkit.getOnlinePlayers()) { list.add(p.getName()); }
			
			if(arg.length == 1) { for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } } list = l; }
			
			return list;
		}
		
		return list;
	}
}
