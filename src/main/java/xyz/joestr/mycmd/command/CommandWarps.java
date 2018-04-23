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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.warps")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.warps")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.warps"));
					return true;
				}
				
				player.sendMessage(ChatColor.GREEN + "Warp-Punkte:");
				
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
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/warps", "run_command", "/warps", "/warps"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 0) {
			
			sender.sendMessage(ChatColor.GREEN + "Warp-Punkte:");
			
			if(!this.plugin.warps.getMap().isEmpty()) {
				
				for (String str : this.plugin.warps.getMap().keySet()) {
					
					sender.sendMessage(ChatColor.GRAY + str);
				}
			}
			
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/warps"));
		return true;
	}
}
