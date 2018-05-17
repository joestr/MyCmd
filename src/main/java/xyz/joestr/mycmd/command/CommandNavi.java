package xyz.joestr.mycmd.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandNavi implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandNavi(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//Player
		if(commandSender instanceof Player) {
			
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.navi")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			@SuppressWarnings("unchecked")
			List<String> nw = (List<String>)this.plugin.config.getMap().get("navi-worlds");
			//List<String> mw = (List<String>)this.plugin.config.getMap().get("map-worlds");
			
			if(args.length == 0) {
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Achtung: Dieser Befehl wird überarbeitet.");
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Navi:");
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "/navi r - Zum Spawn deiner Welt");
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "/navi <Spieler> - Zu einem Spieler");
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "/navi <X> <Z> - Zu bestimmten Koordinaten");
				
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.navi")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.navi");
					return true;
				}
				
				if(!nw.contains(player.getWorld().getName())) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für diese Welt ist der Kompass nicht verfügbar.");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("r")) {
					
					player.setCompassTarget(player.getWorld().getSpawnLocation());
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Dein Kompass zeigt nun zum " + ChatColor.GRAY + "Spawn deiner Welt" + ChatColor.BLUE + ".");
					return true;
				} else {
					
					if(player.getName().equals(args[0])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst nicht zu dir selbst navigieren. :(");
						return true;
					}
					
					if(Bukkit.getOfflinePlayer(args[0]).isOnline()) {
						
						if(!player.getWorld().equals(Bukkit.getPlayer(args[0]).getWorld())) {
							
							player.sendMessage(this.plugin.pluginPrefix + Bukkit.getPlayer(args[0]).getDisplayName() + ChatColor.RED + " befindet sich auf einer anderen Welt.");
						}
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Dein Kompass zeigt nun auf " + Bukkit.getPlayer(args[0]).getDisplayName() + ChatColor.BLUE + ".");
						
						Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
							
							public void run() {
								
								if(Bukkit.getOfflinePlayer(player.getName()).isOnline() && Bukkit.getOfflinePlayer(args[0]).isOnline()) {
									
									player.setCompassTarget(Bukkit.getPlayer(args[0]).getLocation());
								} else {
									
									return;
								}
							}
						}, 0L, 20L);
						
						return true;
					} else {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED + " ist offline.");
						return true;
					}
				}
			}
			
			if(args.length == 2) {
				
				if(!player.hasPermission("mycmd.command.navi")) {
					
					 this.plugin.noPermissionMessage(player, "mycmd.command.navi");
					return true;
				}
				
				if(!nw.contains(player.getWorld().getName())) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für diese Welt ist der Kompass nicht verfügbar.");
					return true;
				}
				
				double x = 0;
				double z = 0;
				
				try {
					
					x = Integer.parseInt(args[0]);
					z = Integer.parseInt(args[1]);
				} catch(Exception e) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für X und Z müssen Ganzzahlen angegeben werden.");
				}
				
				Location location = new Location(player.getWorld(), x < 0 ? -0.5D : 0.5D, 127, z < 0 ? -0.5D : 0.5D);
				
				player.setCompassTarget(location);
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.BLUE + "Dein Kompass zeigt nun auf " + ChatColor.GRAY + x + "/127/" + z + ChatColor.BLUE + ".");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.navi")) {
				
				this.plugin.usageMessage(player, "/navi [<r|Spieler|X>] [<Z>]", "suggest_command", "/navi ", "/navi <r|Spieler|X> [<Z>]");
				return true;
			}
		}
		//End Player
		
		//Console
		if(args.length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Befehl " + ChatColor.GRAY + "/navi" + ChatColor.RED + " ist in der Konsole nicht verfügbar.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/navi");
		return true;
		//End Console
	}
}
