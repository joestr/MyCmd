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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.setspawn")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if (arg.length == 0) {
				
				this.plugin.config.getMap().put("spawn", player.getLocation());
				this.plugin.config.Save();
				player.sendMessage(ChatColor.GREEN + "Der Spawn-Punkt wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.setspawn")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/setspawn", "run_command", "/setspawn", "/setspawn"));
				return true;
			}
		}
		
		if(arg.length == 4) {
			
			World w = null; int x; int y; int z;
			
			try {
				
				w = Bukkit.getWorld(arg[0]);
			} catch(Exception e) {
				
				sender.sendMessage(ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			try {
				
				x = Integer.parseInt(arg[1]);
				y = Integer.parseInt(arg[2]);
				z = Integer.parseInt(arg[3]);
			} catch(Exception e) {
				
				sender.sendMessage(ChatColor.RED + "Für X, Y und Z müssen Ganzzahlen angegeben werden.");
				return true;
			}
			
			Location location = new Location(w, x, y, z);
			
			sender.sendMessage(ChatColor.GREEN + "Der Spawn wurde auf die Position " + ChatColor.GRAY + 
					location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
		}
		
		sender.sendMessage(this.plugin.usageMessage("/setspawn <Welt> <X> <Y> <Z>"));
		return true;
	}
}
