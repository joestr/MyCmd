package xyz.joestr.mycmd.tabcomplete;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import xyz.joestr.mycmd.MyCmd;

public class TabCompleteSpawn
	implements TabCompleter
{
	public TabCompleteSpawn(MyCmd myCmd) {}
	
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3)
	{
		List<String> list = new ArrayList<String>();
		return list;
	}
}
