package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class TabCompleteTpahere
	implements TabCompleter
{
	public TabCompleteTpahere(MyCmd myCmd) {}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg)
	{
		List<String> list = new ArrayList<String>();
		if ((sender instanceof Player))
		{
			Player player = (Player)sender;
			if (arg.length < 1)
			{
				if (player.hasPermission("mycmd.command.tpahere"))
				{
					for (Player pl : Bukkit.getOnlinePlayers()) {
						list.add(pl.getName());
					}
					return list;
				}
				return list;
			}
			return list;
		}
		return list;
	}
}
