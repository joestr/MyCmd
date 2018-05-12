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
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.navi")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage());
				return true;
			}
			
			@SuppressWarnings("unchecked")
			List<String> nw = (List<String>)this.plugin.config.getMap().get("navi-worlds");
			//List<String> mw = (List<String>)this.plugin.config.getMap().get("map-worlds");
			
			if(arg.length == 0) {
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Achtung: Dieser Befehl wird überarbeitet.");
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Navi:");
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "/navi r - Zum Spawn deiner Welt");
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "/navi <Spieler> - Zu einem Spieler");
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "/navi <X> <Z> - Zu bestimmten Koordinaten");
				
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.navi")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.navi"));
					return true;
				}
				
				if(!nw.contains(player.getWorld().getName())) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für diese Welt ist der Kompass nicht verfügbar.");
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("r")) {
					
					player.setCompassTarget(player.getWorld().getSpawnLocation());
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein Kompass zeigt nun zum " + ChatColor.GRAY + "Spawn deiner Welt" + ChatColor.GREEN + ".");
					return true;
				} else {
					
					if(player.getName().equals(arg[0])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst nicht zu dir selbst navigieren. :(");
						return true;
					}
					
					if(Bukkit.getOfflinePlayer(arg[0]).isOnline()) {
						
						if(!player.getWorld().equals(Bukkit.getPlayer(arg[0]).getWorld())) {
							
							player.sendMessage(this.plugin.pluginPrefix + Bukkit.getPlayer(arg[0]).getDisplayName() + ChatColor.RED + " befindet sich auf einer anderen Welt.");
						}
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein Kompass zeigt nun auf " + Bukkit.getPlayer(arg[0]).getDisplayName() + ".");
						
						Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
							
							public void run() {
								
								if(Bukkit.getOfflinePlayer(player.getName()).isOnline() && Bukkit.getOfflinePlayer(arg[0]).isOnline()) {
									
									player.setCompassTarget(Bukkit.getPlayer(arg[0]).getLocation());
								} else {
									
									return;
								}
							}
						}, 0L, 20L);
						
						return true;
					} else {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist offline.");
						return true;
					}
				}
			}
			
			if(arg.length == 2) {
				
				if(!player.hasPermission("mycmd.command.navi")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.navi"));
					return true;
				}
				
				if(!nw.contains(player.getWorld().getName())) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für diese Welt ist der Kompass nicht verfügbar.");
					return true;
				}
				
				double x = 0;
				double z = 0;
				
				try {
					
					x = Integer.parseInt(arg[0]);
					z = Integer.parseInt(arg[1]);
				} catch(Exception e) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für X und Z müssen Ganzzahlen angegeben werden.");
				}
				
				Location location = new Location(player.getWorld(), x < 0 ? -0.5D : 0.5D, 127, z < 0 ? -0.5D : 0.5D);
				
				player.setCompassTarget(location);
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein Kompass zeigt nun auf " + ChatColor.GRAY + x + "/127/" + z + ChatColor.GREEN + ".");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.navi")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/navi [<r|Spieler|X>] [<Z>]", "suggest_command", "/navi ", "/navi <r|Spieler|X> [<Z>]"));
				return true;
			}
		}
		//End Player
		
		//Console
		if(arg.length == 0) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Befehl " + ChatColor.GRAY + "/navi" + ChatColor.RED + " ist in der Konsole nicht verfügbar.");
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/navi"));
		return true;
		//End Console
	}
}
