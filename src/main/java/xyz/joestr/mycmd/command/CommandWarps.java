package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandWarps implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandWarps(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.warps")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.warps")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.warps");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Warp-Punkte:");
				
				if(!this.plugin.warps.getMap().isEmpty()) {
					
					for (String str : this.plugin.warps.getMap().keySet()) {
						
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
							"tellraw " + player.getName() + " " + 
							"[\"\"," + 
							"{" + 
							"\"text\":\">> " + str + "\",\"color\":\"gray\"," + 
							"\"clickEvent\":" + 
							"{" + 
							"\"action\":\"run_command\",\"value\":\"/warp " + str + "\"" + 
							"}," + 
							"\"hoverEvent\":" + 
							"{" + 
							"\"action\":\"show_text\",\"value\":" + 
							"{" + 
							"\"text\":\"\",\"extra\":" + 
							"[" + 
							"{" + 
							"\"text\":\"/warp " + str + "\",\"color\":\"gray\"" + 
							"}" + 
							"]" + 
							"}" + 
							"}" + 
							"}" + 
							"]"
						);
					}
				}
				
				return true;
			}
			
			if (player.hasPermission("mycmd.command.warps")) {
				
				this.plugin.usageMessage(player, "/warps", "run_command", "/warps", "/warps");
				return true;
			}
		}
		
		//Console
		if(args.length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Warp-Punkte:");
			
			if(!this.plugin.warps.getMap().isEmpty()) {
				
				for (String str : this.plugin.warps.getMap().keySet()) {
					
					commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + str);
				}
			}
			
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/warps");
		return true;
	}
}
