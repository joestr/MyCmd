package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import xyz.joestr.mycmd.MyCmd;

public class TabCompleteList
	implements TabCompleter
{
	public TabCompleteList(MyCmd mycmd) {}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String string, String[] arg)
	{
		List<String> list = new ArrayList<String>();
		return list;
	}
}
