package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandSetwarp implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandSetwarp(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.setworldspawn")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.setworldspawn")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.setworldspawn");
					return true;
				}
				
				player.getLocation().getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawn-Punkt der Welt wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.setworldspawn"))
			{
				
				this.plugin.usageMessage(player, "/setworldspawn", "run_command", "/setworldspawn", "/setworldspawn");
				return true;
			}
		}
		
		//Console
		if(args.length == 4) {
			
			World w = null; int x; int y; int z;
			
			try {
				
				w = Bukkit.getWorld(args[0]);
			} catch(Exception e) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			try {
				
				x = Integer.parseInt(args[1]);
				y = Integer.parseInt(args[2]);
				z = Integer.parseInt(args[3]);
			} catch(Exception e) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für X, Y und Z müssen Ganzzahlen angegeben werden.");
				return true;
			}
			
			Location location = new Location(w, x, y, z);
			
			w.setSpawnLocation(x, y, z);
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawn-Punlt der Welt " + ChatColor.GRAY + args[0] + ChatColor.GREEN + " wurde auf die Position" +
					location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/setworldspawn <Welt> <X> <Y> <Z>");
		return true;
	}
}
