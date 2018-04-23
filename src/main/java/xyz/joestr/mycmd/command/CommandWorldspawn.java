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

public class CommandWorldspawn implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandWorldspawn(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.worldspawn")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.worldspawn")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.worldspawn"));
					return true;
				}
				
				player.teleport(player.getWorld().getSpawnLocation());
				player.sendMessage(ChatColor.GREEN + "Du wurdest zum " + ChatColor.GRAY + "Spawn-Punkt deiner Welt" + ChatColor.GREEN + " teleportiert.");
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.worldspawn")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.worldspawn.other"));
					return true;
				}
				
				World w = null;
				
				try {
					
					w = Bukkit.getWorld(arg[1]);
				} catch(Exception e) {
					
					player.sendMessage(ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
					return true;
				}
				
				player.teleport(player.getWorld().getSpawnLocation());
				player.sendMessage(ChatColor.GREEN + "Du wurdest zum " + ChatColor.GRAY + "Spawn-Punkt der Welt "+ w.getName() + ChatColor.GREEN + " teleportiert.");
				return true;
			}
			
			if(arg.length == 2) {
				
				if(!player.hasPermission("mycmd.command.worldspawn.other")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.worldspawn.other"));
					return true;
				}
				
				World w = null;
				
				try {
					
					w = Bukkit.getWorld(arg[0]);
				} catch(Exception e) {
					
					player.sendMessage(ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
					return true;
				}
				
				if(!Bukkit.getOfflinePlayer(arg[1]).isOnline()) {
					
					player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist offline.");
					return true;
				}
				
				Bukkit.getPlayer(arg[0]).teleport(w.getSpawnLocation());
				player.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde zum " + ChatColor.GRAY + "Spawn-Punkt der Welt " + arg[0] + ChatColor.GREEN + " teleportiert.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.worldspawn") && player.hasPermission("mycmd.command.worldspawn.other")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/worldspawn [<Welt>] [<Spieler>]", "suggest_command", "/worldspawn ", "/worldspawn [<Welt>] [<Spieler>]"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.worldspawn.other")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/worldspawn <Welt> <Spieler>", "suggest_command", "/worldspawn ", "/worldspawn <Welt> <Spieler>"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.worldspawn")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/worldspawn [<Welt>]", "suggest_command", "/worldspawn ", "/worldspawn [<Welt>]"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 1) {
			
			World w = null;
			
			try {
				
				w = Bukkit.getWorld(arg[0]);
			} catch(Exception e) {
				
				sender.sendMessage(ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			Location location = w.getSpawnLocation();
			sender.sendMessage(ChatColor.GREEN + "Der " + ChatColor.GRAY + "Spawn-Punkt der Welt " + arg[0] + ChatColor.GREEN + " befindet sich bei " +
					ChatColor.GRAY + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + ".");
			return true;
		}
		
		if(arg.length == 2) {
			
			World w = null;
			
			try {
				
				w = Bukkit.getWorld(arg[0]);
			} catch(Exception e) {
				
				sender.sendMessage(ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			if(!Bukkit.getOfflinePlayer(arg[1]).isOnline()) {
				
				sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist offline.");
				return true;
			}
			
			Bukkit.getPlayer(arg[1]).teleport(w.getSpawnLocation());
			sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ChatColor.GRAY + arg[0] + ChatColor.GREEN + "wurde zum " + ChatColor.GRAY + "Spawn-Punkt der Welt " + arg[0] + ChatColor.GREEN + " teleportiert.");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/worldspawn <Welt> [<Spieler>]"));
		return true;
	}
}
