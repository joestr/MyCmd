package xyz.joestr.mycmd.command;

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
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.warp")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.warp")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.warp");
					return true;
				}
				
				if (this.plugin.warps.getMap().containsKey(args[0])) {
					
					player.teleport((Location)this.plugin.warps.getMap().get(args[0]));
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du wurdest zum Warp-Punkt " + ChatColor.GRAY + args[0] + ChatColor.GREEN + " teleportiert.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Warp-Punkt " + ChatColor.GRAY + args[0] + ChatColor.RED + " wurde noch nicht gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.warp")) {
				
				this.plugin.usageMessage(player, "/warp <Warp-Punkt>", "suggest_command", "/warp ", "/warp <Warp-Punkt>");
				return true;
			}
		}
		
		//Console
		if(args.length == 1) {
			
			if (this.plugin.warps.getMap().containsKey(args[0])) {
				
				Location location = (Location)this.plugin.warps.getMap().get(args[0]);
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Warp-Punkt " + ChatColor.GRAY + args[0] + ChatColor.GREEN + " befindet sich bei " + 
						ChatColor.GRAY + location.getWorld().getName() + "/" + ChatColor.GRAY + location.getX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ChatColor.GREEN + ".");
				return true;
			}
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Warp-Punkt " + ChatColor.GRAY + args[0] + ChatColor.RED + " wurde noch nicht gesetzt.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/warp <Warp-Punkt>");
		return true;
	}
}
