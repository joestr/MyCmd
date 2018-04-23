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

public class CommandSethome implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandSethome(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.sethome") && !player.hasPermission("mycmd.command.sethome")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.sethome")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.sethome"));
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					this.plugin.homes.getMap().put(player.getUniqueId().toString(), player.getLocation());
					this.plugin.homes.Save();
					player.sendMessage(ChatColor.GREEN + "Dein Home-Punkt wurde auf die aktuelle Position gesetzt.");
					return true;
				}
				
				this.plugin.homes.getMap().put(player.getName(), player.getLocation());
				this.plugin.homes.Save();
				player.sendMessage(ChatColor.GREEN + "Dein Home-Punkt wurde auf die aktuelle Position gesetzt.");
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.sethome.other")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.sethome.other"));
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					this.plugin.homes.getMap().put(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString(), player.getLocation());
					this.plugin.homes.Save();
					player.sendMessage(ChatColor.GREEN + "Der Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf deine aktuelle Position gesetzt.");
					return true;
				}
				
				this.plugin.homes.getMap().put(arg[0], player.getLocation());
				this.plugin.homes.Save();
				player.sendMessage(ChatColor.GREEN + "Der Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf deine aktuelle Position gesetzt.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.sethome") && player.hasPermission("mycmd.command.sethome")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/sethome [<Spieler>]", "suggest_command", "/sethome ", "/sethome [<Spieler>]"));
				return true;
			}
			
			if (player.hasPermission("mycmd.command.sethome.other"))
			{
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/sethome <Spieler>", "suggest_command", "/sethome ", "/sethome <Spieler>"));
				return true;
			}
			
			if (player.hasPermission("mycmd.command.sethome"))
			{
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/sethome", "run_command", "/sethome", "/sethome"));
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
			
			if(Bukkit.getOnlineMode()) {
				
				if (this.plugin.homes.getMap().containsKey(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString())) {
					
					this.plugin.homes.getMap().remove(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString());
					this.plugin.homes.getMap().put(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString(), location);
					this.plugin.homes.Save();
					sender.sendMessage(ChatColor.GREEN + "Der Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf die Position " + ChatColor.GRAY + 
							location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + ".");
					return true;
				}
				
				this.plugin.homes.getMap().put(Bukkit.getOfflinePlayer(arg[0]).getUniqueId().toString(), location);
				this.plugin.homes.Save();
				sender.sendMessage(ChatColor.GREEN + "Der Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf die Position " + ChatColor.GRAY + 
						location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + ".");
				return true;
			}
			
			if (this.plugin.homes.getMap().containsKey(arg[0])) {
				
				this.plugin.homes.getMap().remove(arg[0]);
				this.plugin.homes.getMap().put(arg[0], location);
				this.plugin.homes.Save();
				sender.sendMessage(ChatColor.GREEN + "Der Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf die Position " + ChatColor.GRAY + 
						location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + ".");
				return true;
			}
			
			this.plugin.homes.getMap().put(arg[0], location);
			this.plugin.homes.Save();
			sender.sendMessage(ChatColor.GREEN + "Der Home-Punkt vom Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde auf die Position " + ChatColor.GRAY + 
					location.getWorld().toString() + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + ".");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/sethome <Spieler> <Welt> <X> <Y> <Z>"));
		return true;
	}
}
