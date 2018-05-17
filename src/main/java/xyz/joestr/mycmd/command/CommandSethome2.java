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
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.sethome2") && !player.hasPermission("mycmd.command.sethome2")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.sethome2")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.sethome2");
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
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.sethome2.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.sethome2.other");
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					this.plugin.homes2.getMap().put(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString(), player.getLocation());
					this.plugin.homes2.Save();
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + args[0] + ChatColor.GREEN + "wurde auf deine aktuelle Position gesetzt.");
					return true;
				}
				
				this.plugin.homes2.getMap().put(args[0], player.getLocation());
				this.plugin.homes2.Save();
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + args[0] + ChatColor.GREEN + "wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.sethome2") && player.hasPermission("mycmd.command.sethome2")) {
				
				this.plugin.usageMessage(player, "/sethome2 [<Spieler>]", "suggest_command", "/sethome2 ", "/sethome2 [<Spieler>]");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.sethome2.other"))
			{
				
				this.plugin.usageMessage(player, "/sethome2 <Spieler>", "suggest_command", "/sethome2 ", "/sethome2 <Spieler>");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.sethome2"))
			{
				
				this.plugin.usageMessage(player, "/sethome2", "run_command", "/sethome2", "/sethome2");
				return true;
			}
		}
		
		//Console
		if(args.length == 5) {
			
			World w = null; int x; int y; int z;
			
			try {
				
				w = Bukkit.getWorld(args[1]);
			} catch(Exception e) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			try {
				
				x = Integer.parseInt(args[2]);
				y = Integer.parseInt(args[3]);
				z = Integer.parseInt(args[4]);
			} catch(Exception e) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für X, Y und Z müssen Ganzzahlen angegeben werden.");
				return true;
			}
			
			Location location = new Location(w, x, y, z);
			
			if(Bukkit.getOnlineMode()) {
				
				this.plugin.homes2.getMap().put(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString(), location);
				this.plugin.homes2.Save();
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + args[0] + ChatColor.GREEN + "wurde auf die Position " + ChatColor.GRAY + 
						location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
				return true;
			}
			
			this.plugin.homes2.getMap().put(args[0], location);
			this.plugin.homes2.Save();
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der zweite Home-Punkt vom Spieler " + ChatColor.GRAY + args[0] + ChatColor.GREEN + "wurde auf die Position " + ChatColor.GRAY + 
					location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + " gesetzt.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/sethome2 <Spieler> <Welt> <X> <Y> <Z>");
		return true;
	}
}
