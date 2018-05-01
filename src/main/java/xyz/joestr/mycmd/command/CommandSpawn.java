package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.spawn")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.spawn")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.spawn"));
					return true;
				}
				
				if (this.plugin.config.getMap().get("spawn") == null) {
					
					player.sendMessage(ChatColor.RED + "Der Spawn-Punkt wurde noch nicht gesetzt.");
					return true;
				}
				
				Location location = (Location) this.plugin.config.getMap().get("spawn");
				if(player.teleport(location)) {
					player.sendMessage(ChatColor.GREEN + "Du wurdest zum Spawn-Punkt teleportiert.");
				}
				return true;
			}
			
			if (player.hasPermission("mycmd.command.spawn")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/spawn", "run_command", "/spawn", "/spawn"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 0) {
			
			if (this.plugin.config.getMap().get("spawn") == null) {
				
				sender.sendMessage(ChatColor.RED + "Der Spawn-Punkt wurde noch nicht gesetzt.");
				return true;
			}
			
			Location location = (Location) this.plugin.config.getMap().get("spawn");
			sender.sendMessage(ChatColor.GREEN + "Der Spawn-Punkt befindet sich bei " +
					ChatColor.GRAY + location.getWorld().getName() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + ".");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/spawn"));
		return true;
	}
}
