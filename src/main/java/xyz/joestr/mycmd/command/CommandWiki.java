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
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.wiki")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 1)
			{
				if(!player.hasPermission("mycmd.command.wiki")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.wiki");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Wiki:");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
					"tellraw " + player.getName() + " " + 
					"[\"\"," + 
					"{" + 
					"\"text\":\">> " + args[0] + "\",\"color\":\"gray\"," + 
					"\"clickEvent\":" + 
					"{" + 
					"\"action\":\"open_url\",\"value\":\"" + (String)this.plugin.config.getMap().get("wiki") + args[0] + "\"" + 
					"}," + 
					"\"hoverEvent\":" + 
					"{" + 
					"\"action\":\"show_text\",\"value\":" + 
					"{" + 
					"\"text\":\"\",\"extra\":" + 
					"[" + 
					"{" + 
					"\"text\":\"" + (String)this.plugin.config.getMap().get("wiki") + args[0] + "\",\"color\":\"gray\"" + 
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
				
				this.plugin.usageMessage(player, "/wiki <Stichwort>", "suggest_command", "/wiki ", "/wiki <Stichwort>");
				return true;
			}
		}
		
		//Console
		if(args.length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Wiki: " + ChatColor.GRAY + (String)this.plugin.config.getMap().get("wiki"));
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/wiki");
		return true;
	}
}
