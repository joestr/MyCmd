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

public class CommandSethome2
	implements CommandExecutor
{
	private MyCmd plugin;
	
	public CommandSethome2(MyCmd mycmd)
	{
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.sethome2") && !player.hasPermission("mycmd.command.sethome2")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.sethome2")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.sethome2"));
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					this.plugin.homes2.getMap().put(player.getUniqueId().toString(), player.getLocation());
					this.plugin.homes2.Save();
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein zweiter Home-Punkt wurde auf die aktuelle Position gesetzt.");
					return true;
				}
				
				this.plugin.homes2.getMap().put(player.getName(), player.getLocation());
				this.plugin.homes2.Save();
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein zweiter Home-Punkt wurde auf die aktuelle Position gesetzt.");
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.sethome2.other")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.sethome2.other"));
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					this.plugin.homes2.getMap().put(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString(), player.getLocation());
					this.plugin.homes2.Save();
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf deine aktuelle Position gesetzt.");
					return true;
				}
				
				this.plugin.homes2.getMap().put(arg[0], player.getLocation());
				this.plugin.homes2.Save();
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.sethome2") && player.hasPermission("mycmd.command.sethome2")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/sethome2 [<Spieler>]", "suggest_command", "/sethome2 ", "/sethome2 [<Spieler>]"));
				return true;
			}
			
			if (player.hasPermission("mycmd.command.sethome2.other"))
			{
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/sethome2 <Spieler>", "suggest_command", "/sethome2 ", "/sethome2 <Spieler>"));
				return true;
			}
			
			if (player.hasPermission("mycmd.command.sethome2"))
			{
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/sethome2", "run_command", "/sethome2", "/sethome2"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 5) {
			
			World w = null; int x; int y; int z;
			
			try {
				
				w = Bukkit.getWorld(arg[1]);
			} catch(Exception e) {
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			try {
				
				x = Integer.parseInt(arg[2]);
				y = Integer.parseInt(arg[3]);
				z = Integer.parseInt(arg[4]);
			} catch(Exception e) {
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für X, Y und Z müssen Ganzzahlen angegeben werden.");
				return true;
			}
			
			Location location = new Location(w, x, y, z);
			
			if(Bukkit.getOnlineMode()) {
				
				this.plugin.homes2.getMap().put(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString(), location);
				this.plugin.homes2.Save();
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf die Position " + ChatColor.GRAY + 
						location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
				return true;
			}
			
			this.plugin.homes2.getMap().put(arg[0], location);
			this.plugin.homes2.Save();
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf die Position " + ChatColor.GRAY + 
					location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/sethome2 <Spieler> <Welt> <X> <Y> <Z>"));
		return true;
	}
}
