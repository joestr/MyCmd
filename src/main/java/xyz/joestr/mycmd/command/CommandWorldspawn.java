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
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.worldspawn")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.worldspawn")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.worldspawn");
					return true;
				}
				
				if(player.teleport(player.getWorld().getSpawnLocation())) {
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du wurdest zum " + ChatColor.GRAY + "Spawn-Punkt deiner Welt" + ChatColor.GREEN + " teleportiert.");
				}
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.worldspawn")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.worldspawn.other");
					return true;
				}
				
				World w = null;
				
				try {
					
					w = Bukkit.getWorld(args[1]);
				} catch(Exception e) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
					return true;
				}
				
				if(player.teleport(player.getWorld().getSpawnLocation())) {
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du wurdest zum " + ChatColor.GRAY + "Spawn-Punkt der Welt "+ w.getName() + ChatColor.GREEN + " teleportiert.");
				}
				return true;
			}
			
			if(args.length == 2) {
				
				if(!player.hasPermission("mycmd.command.worldspawn.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.worldspawn.other");
					return true;
				}
				
				World w = null;
				
				try {
					
					w = Bukkit.getWorld(args[0]);
				} catch(Exception e) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
					return true;
				}
				
				if(!Bukkit.getOfflinePlayer(args[1]).isOnline()) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED + " ist offline.");
					return true;
				}
				
				if(Bukkit.getPlayer(args[0]).teleport(w.getSpawnLocation())) {
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.GREEN + "wurde zum " + ChatColor.GRAY + "Spawn-Punkt der Welt " + args[0] + ChatColor.GREEN + " teleportiert.");
				}
				return true;
			}
			
			if(player.hasPermission("mycmd.command.worldspawn") && player.hasPermission("mycmd.command.worldspawn.other")) {
				
				this.plugin.usageMessage(player, "/worldspawn [<Welt>] [<Spieler>]", "suggest_command", "/worldspawn ", "/worldspawn [<Welt>] [<Spieler>]");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.worldspawn.other")) {
				
				this.plugin.usageMessage(player, "/worldspawn <Welt> <Spieler>", "suggest_command", "/worldspawn ", "/worldspawn <Welt> <Spieler>");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.worldspawn")) {
				
				this.plugin.usageMessage(player, "/worldspawn [<Welt>]", "suggest_command", "/worldspawn ", "/worldspawn [<Welt>]");
				return true;
			}
		}
		
		//Console
		if(args.length == 1) {
			
			World w = null;
			
			try {
				
				w = Bukkit.getWorld(args[0]);
			} catch(Exception e) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			Location location = w.getSpawnLocation();
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der " + ChatColor.GRAY + "Spawn-Punkt der Welt " + args[0] + ChatColor.GREEN + " befindet sich bei " +
					ChatColor.GRAY + "/" + location.getX() + "/" + location.getBlockY() + "/" + location.getZ() + ChatColor.GREEN + ".");
			return true;
		}
		
		if(args.length == 2) {
			
			World w = null;
			
			try {
				
				w = Bukkit.getWorld(args[0]);
			} catch(Exception e) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Welt muss eine Zeichenkette angegeben werden.");
				return true;
			}
			
			if(!Bukkit.getOfflinePlayer(args[1]).isOnline()) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED + " ist offline.");
				return true;
			}
			
			Bukkit.getPlayer(args[1]).teleport(w.getSpawnLocation());
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.GREEN + "wurde zum " + ChatColor.GRAY + "Spawn-Punkt der Welt " + args[0] + ChatColor.GREEN + " teleportiert.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/worldspawn <Welt> [<Spieler>]");
		return true;
	}
}
