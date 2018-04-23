package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class TabCompleteWarp implements TabCompleter {
	
	private MyCmd plugin;
	
	public TabCompleteWarp(MyCmd mycmd) { this.plugin = mycmd; }
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg) {
		
		List<String> list = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(arg.length <= 1) {
				
				if(player.hasPermission("mycmd.command.warp")) {
					
					for(String key : this.plugin.warps.getMap().keySet()) { list.add(key); }
					
					if(arg.length == 1) {
						
						for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				return list;
			}
			
			return list;
		}
		
		//Console
		if(arg.length <= 1) {
			
			for(String key : this.plugin.warps.getMap().keySet()) { list.add(key); }
			
			if(arg.length == 1) {
				
				for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } }
				list = l;
			}
			
			return list;
		}
		
		return list;
	}
}
