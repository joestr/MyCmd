package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import xyz.joestr.mycmd.MyCmd;

public class TabCompleteRank
	implements TabCompleter
{
	public TabCompleteRank(MyCmd myCmd) {
		
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg)
	{
		List<String> list = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(arg.length <= 1) {
				
				if(player.hasPermission("mycmd.command.rank")) {
					
					list.add("on");list.add("off");list.add("reload");list.add("list");list.add("add");list.add("remove");
					
					if(arg.length == 1) {
						
						for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } }
						list = l;
					}
					
					return list;
				}
			}
			
			if(arg.length <= 2) {
				
				if(!player.hasPermission("mycmd.command.rank")) { return list; }
				
				if(arg[0].equalsIgnoreCase("remove")) {
					
					for(Team t : Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeams()) { list.add(t.getName()); }
					
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
		Player player = (Player)sender;
		
		if(arg.length <= 1) {
			
			list.add("on");list.add("off");list.add("reload");list.add("list");list.add("add");list.add("remove");
			
			if(arg.length == 1) {
				
				for(String key : list) { if(key.startsWith(arg[0])) { l.add(key); } }
				list = l;
			}
			
			return list;
		}
		
		if(arg.length <= 2) {
			
			if(!player.hasPermission("mycmd.command.rank")) { return list; }
			
			for(Team t : Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeams()) { list.add(t.getName()); }
			
			if(arg.length == 2) {
				
				for(String key : list) { if(key.startsWith(arg[1])) { l.add(key); } }
				list = l;
			}
				
			return list;
		}
		
		return list;
		//End Console;
	}
}
