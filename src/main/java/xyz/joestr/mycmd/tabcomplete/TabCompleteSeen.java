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

public class TabCompleteSeen
	implements TabCompleter
{
	public TabCompleteSeen(MyCmd myCmd) {}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg)
	{
		List<String> list = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		if ((sender instanceof Player))
		{
			Player player = (Player)sender;
			if (arg.length <= 1)
			{
				if (player.hasPermission("mycmd.command.seen"))
				{
					for (OfflinePlayer pl : Bukkit.getOfflinePlayers()) {
						list.add(pl.getName());
					}
					
					if(arg.length == 1)
					{
						for(String key : list)
						{
							if(key.startsWith(arg[0]))
							{
								l.add(key);
							}
						}
						list = l;
					}
					return list;
				}
				return list;
			}
			return list;
		}
		
		if(arg.length <= 1) {
			
			for(OfflinePlayer pl : Bukkit.getOfflinePlayers()) { list.add(pl.getName()); }
			
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
