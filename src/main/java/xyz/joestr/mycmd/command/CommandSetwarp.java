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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.setworldspawn")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.setworldspawn")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.setworldspawn"));
					return true;
				}
				
				player.getLocation().getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawn-Punkt der Welt wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.setworldspawn"))
			{
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/setworldspawn", "run_command", "/setworldspawn", "/setworldspawn"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 4) {
			
			World w = null; int x; int y; int z;
			
			try {
				
				w = Bukkit.getWorld(arg[0]);
			} catch(Exception e) {
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			try {
				
				x = Integer.parseInt(arg[1]);
				y = Integer.parseInt(arg[2]);
				z = Integer.parseInt(arg[3]);
			} catch(Exception e) {
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für X, Y und Z müssen Ganzzahlen angegeben werden.");
				return true;
			}
			
			Location location = new Location(w, x, y, z);
			
			w.setSpawnLocation(x, y, z);
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawn-Punlt der Welt " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " wurde auf die Position" +
					location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/setworldspawn <Welt> <X> <Y> <Z>"));
		return true;
	}
}
