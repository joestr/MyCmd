package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandSetworldspawn implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandSetworldspawn(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.setworldspawn")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.setworldspawn")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.setworldspawn");
					return true;
				}
				
				this.plugin.warps.getMap().put(arg[0], player.getLocation());
				this.plugin.warps.Save();
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawnpunkt deiner Welt wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.setworldspawn"))
			{
				
				this.plugin.usageMessage(player, "/setworldspawn", "run_command", "/setworldspawn", "/setworldspawn");
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
			
			w.setSpawnLocation(x, y, z);
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawnpunkt der Welt " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " wurde auf die Position" +
					x + "/" + x + "/" + z + ChatColor.GREEN + " gesetzt.");
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/setworldspawn <Welt> <X> <Y> <Z>"));
		return true;
	}
}
