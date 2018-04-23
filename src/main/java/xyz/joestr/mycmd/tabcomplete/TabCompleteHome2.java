package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class TabCompleteHome2 implements TabCompleter {
	
MyCmd plugin;
	
	public TabCompleteHome2(MyCmd myCmd) {
		
		this.plugin = myCmd;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg) {
		
		List<String> list = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		
		if((sender instanceof Player)) {
			
			Player player = (Player)sender;
			
			if(arg.length <= 1) {
				
				if(player.hasPermission("mycmd.command.home2.other")) {
					
					list.addAll(this.plugin.homes2.getMap().keySet());
					
					if(arg.length == 1) { for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } } list = l; }
					
					return list;
				}
				
				return list;
			}
			
			return list;
		}
		
		if(arg.length <= 1) {
			
			list.addAll(this.plugin.homes2.getMap().keySet());
			
			if(arg.length == 1) { for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } } list = l; }
			
			return list;
		}
		
		return list;
	}
}
