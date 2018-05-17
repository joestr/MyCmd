package xyz.joestr.mycmd.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandSpawn implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandSpawn(MyCmd mycmd) { this.plugin = mycmd; }
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.spawn")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.spawn")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.spawn");
					return true;
				}
				
				if (this.plugin.config.getMap().get("spawn") == null) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Spawn-Punkt wurde noch nicht gesetzt.");
					return true;
				}
				
				Location location = (Location) this.plugin.config.getMap().get("spawn");
				if(player.teleport(location)) {
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du wurdest zum Spawn-Punkt teleportiert.");
				}
				return true;
			}
			
			if (player.hasPermission("mycmd.command.spawn")) {
				
				this.plugin.usageMessage(player, "/spawn", "run_command", "/spawn", "/spawn");
				return true;
			}
		}
		
		//Console
		if(args.length == 0) {
			
			if (this.plugin.config.getMap().get("spawn") == null) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Spawn-Punkt wurde noch nicht gesetzt.");
				return true;
			}
			
			Location location = (Location) this.plugin.config.getMap().get("spawn");
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawn-Punkt befindet sich bei " +
					ChatColor.GRAY + location.getWorld().getName() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + ".");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/spawn");
		return true;
	}
}
