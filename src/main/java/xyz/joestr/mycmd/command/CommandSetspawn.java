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

public class CommandSetspawn implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandSetspawn(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.setspawn")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if (args.length == 0) {
				
				this.plugin.config.getMap().put("spawn", player.getLocation());
				this.plugin.config.Save();
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawn-Punkt wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.setspawn")) {
				
				this.plugin.usageMessage(player, "/setspawn", "run_command", "/setspawn", "/setspawn");
				return true;
			}
		}
		
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
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawn wurde auf die Position " + ChatColor.GRAY + 
					location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
		}
		
		this.plugin.usageMessage(commandSender, "/setspawn <Welt> <X> <Y> <Z>");
		return true;
	}
}
