package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandWiki implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandWiki(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.wiki")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 1)
			{
				if(!player.hasPermission("mycmd.command.wiki")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.wiki"));
					return true;
				}
				
				player.sendMessage(ChatColor.GREEN + "Wiki:");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
					"tellraw " + player.getName() + " " + 
					"[\"\"," + 
					"{" + 
					"\"text\":\">> " + arg[0] + "\",\"color\":\"gray\"," + 
					"\"clickEvent\":" + 
					"{" + 
					"\"action\":\"open_url\",\"value\":\"" + (String)this.plugin.config.getMap().get("wiki") + arg[0] + "\"" + 
					"}," + 
					"\"hoverEvent\":" + 
					"{" + 
					"\"action\":\"show_text\",\"value\":" + 
					"{" + 
					"\"text\":\"\",\"extra\":" + 
					"[" + 
					"{" + 
					"\"text\":\"" + (String)this.plugin.config.getMap().get("wiki") + arg[0] + "\",\"color\":\"gray\"" + 
					"}" + 
					"]" + 
					"}" + 
					"}" + 
					"}" + 
					"]"
				);
				
				return true;
			}
			
			if(player.hasPermission("mycmd.command.wiki")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/wiki <Stichwort>", "suggest_command", "/wiki ", "/wiki <Stichwort>"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 0) {
			
			sender.sendMessage(ChatColor.GREEN + "Wiki: " + ChatColor.GRAY + (String)this.plugin.config.getMap().get("wiki"));
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/wiki"));
		return true;
	}
}
