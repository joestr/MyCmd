package xyz.joestr.mycmd.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandDelwarp implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandDelwarp(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if (sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.delwarp")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.delwarp")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.delwarp");
					return true;
				}
				
				_delwarp_(sender, arg[0]);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.delwarp")) {
				
				this.plugin.usageMessage(player, "/delwarp <Warp-Punkt>", "suggest_command", "/delwarp ", "/delwarp <Warp-Punkt>");
				return true;
			}
		}
		//End Player
		
		//Console
		if(arg.length == 1) {
			
			_delwarp_(sender, arg[0]);
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/delwarp <Warp-Punkt>"));
		return true;
		//End Console
	}
	
	/**
	 * 
	 * @param sender Specifies the sender of the command.
	 * @param string Specifies the name  of a warp point.
	 */
	public void _delwarp_(CommandSender sender, String string) {
		
		if (!this.plugin.warps.getMap().isEmpty()) {
			
			if (this.plugin.warps.getMap().containsKey(string)) {
				
				this.plugin.warps.getMap().remove(string);
				this.plugin.warps.Save();
				sender.sendMessage(ChatColor.GREEN + "Der Warp-Punkt " + ChatColor.GRAY + string + ChatColor.GREEN + " wurde gelöscht.");
				return;
			}
				
			sender.sendMessage(ChatColor.RED + "Der Warp-Punkt " + ChatColor.GRAY + string + ChatColor.RED + " existiert nicht.");
			return;
		}
		
		sender.sendMessage(ChatColor.RED + "Der Warp-Punkt " + ChatColor.GRAY + string + ChatColor.RED + " existiert nicht.");
		return;
	}
}
