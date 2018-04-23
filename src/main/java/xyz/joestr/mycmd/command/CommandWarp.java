package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandWarp implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandWarp(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.warp")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.warp")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.warp"));
					return true;
				}
				
				if (this.plugin.warps.getMap().containsKey(arg[0])) {
					
					player.teleport((Location)this.plugin.warps.getMap().get(arg[0]));
					player.sendMessage(ChatColor.GREEN + "Du wurdest zum Warp-Punkt " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " teleportiert.");
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Der Warp-Punkt " + ChatColor.GRAY + arg[0] + ChatColor.RED + " wurde noch nicht gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.warp")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/warp <Warp-Punkt>", "suggest_command", "/warp ", "/warp <Warp-Punkt>"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 1) {
			
			if (this.plugin.warps.getMap().containsKey(arg[0])) {
				
				Location location = (Location)this.plugin.warps.getMap().get(arg[0]);
				sender.sendMessage(ChatColor.GREEN + "Der Warp-Punkt " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " befindet sich bei " + 
						ChatColor.GRAY + location.getWorld().getName() + "/" + ChatColor.GRAY + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ChatColor.GREEN + ".");
				return true;
			}
			
			sender.sendMessage(ChatColor.RED + "Der Warp-Punkt " + ChatColor.GRAY + arg[0] + ChatColor.RED + " wurde noch nicht gesetzt.");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/warp <Warp-Punkt>"));
		return true;
	}
}
