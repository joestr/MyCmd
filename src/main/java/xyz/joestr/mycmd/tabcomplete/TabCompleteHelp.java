package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class TabCompleteHelp implements TabCompleter {
	
	MyCmd plugin;
	public TabCompleteHelp(MyCmd myCmd) { this.plugin = myCmd; }
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg) {
		
		List<String> list = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(arg.length <= 1) {
				
				if(player.hasPermission("mycmd.command.help")) {
					
					ArrayList<String> al = new ArrayList<String>();
					for(Command com : this.plugin.commandList) { if(com.getPermission() != null) { if(player.hasPermission(com.getPermission())) { al.add(com.getName()); } } else { al.add(com.getName()); } }
					
					for(int i = 1; i <= Math.ceil(al.size() / 7); i++) { list.add("" + i); }
					
					if(arg.length == 1) {
						
						for(String key : list) { if(key.startsWith(arg[0])) {l.add(key); } }
						list = l;
					}
					
					return list;
				}
				
				return list;
			}
			
			return list;
		}
		
		if(arg.length <= 1) {
			
			for(int i = 1; i <= Math.ceil(this.plugin.commandList.size() / 7); i++) { list.add("" + i); }
			
			if(arg.length == 1) {
				
				for(String key : list) {
					
					if(key.startsWith(arg[0])) { l.add(key); }
				}
				
				list = l;
			}
			
			return list;
		}
		
		return list;
	}
}
