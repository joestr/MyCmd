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

public class CommandSetworldspawn implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandSetworldspawn(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.setwarp")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.setwarp")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.setwarp"));
					return true;
				}
				
				this.plugin.warps.getMap().put(arg[0], player.getLocation());
				this.plugin.warps.Save();
				player.sendMessage(ChatColor.GREEN + "Der Warp-Punkt " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.setwarp"))
			{
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/setwarp <Warp-Punkt>", "suggest_command", "/setwarp ", "/setwarp <Warp-Punkt>"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 5) {
			
			World w = null; int x; int y; int z;
			
			try {
				
				w = Bukkit.getWorld(arg[1]);
			} catch(Exception e) {
				
				sender.sendMessage(ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			try {
				
				x = Integer.parseInt(arg[2]);
				y = Integer.parseInt(arg[3]);
				z = Integer.parseInt(arg[4]);
			} catch(Exception e) {
				
				sender.sendMessage(ChatColor.RED + "Für X, Y und Z müssen Ganzzahlen angegeben werden.");
				return true;
			}
			
			Location location = new Location(w, x, y, z);
			
			this.plugin.warps.getMap().put(arg[0], location);
			this.plugin.warps.Save();
			sender.sendMessage(ChatColor.GREEN + "Der Warp-Punkt " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + " wurde auf die Position" +
					location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/setwarp <Warp-Punkt> <Welt> <X> <Y> <Z>"));
		return true;
	}
}
