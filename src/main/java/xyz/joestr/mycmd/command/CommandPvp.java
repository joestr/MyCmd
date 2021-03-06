package xyz.joestr.mycmd.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandPvp implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandPvp(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	// TODO: GREEN -> Blue
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.pvp") && !player.hasPermission("mycmd.command.pvp.other")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(player.hasPermission("mycmd.command.pvp") && player.hasPermission("mycmd.command.pvp.other")) {
					
					this.plugin.commandOverview(player, "PvP",
							new String[] {"Dein PvP aktivieren", "Dein PvP deaktivieren", "Deinen PvP-Status anzeigen", "Liste aller dezeitigen PvP-Teilnehmer", "PvP eines Spielers aktivieren", "PvP eines Spielers deaktivieren", "PvP-Status eines Spielers anzeigen"},
							new String[] {"run_command", "run_command", "run_command", "run_command", "suggest_command", "suggest_command", "suggest_command"},
							new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list", "/pvp on ", "/pvp off ", "/pvp status "},
							new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list", "/pvp on <Spieler>", "/pvp off <Spieler>", "/pvp status <Spieler>"});
					
					return true;
				}
				
				if(player.hasPermission("mycmd.command.pvp.other")) {
					
					this.plugin.commandOverview(player, "PvP",
							new String[] {"PvP eines Spielers aktivieren", "PvP eines Spielers deaktivieren", "PvP-Status eines Spielers anzeigen"},
							new String[] {"suggest_command", "suggest_command", "suggest_command"},
							new String[] {"/pvp on ", "/pvp off ", "pvp status "},
							new String[] {"/pvp on <Spieler>", "/pvp off <Spieler>", "/pvp status <Spieler>"});
					return true;
				}
				
				if(player.hasPermission("mycmd.command.pvp")) {
					
					this.plugin.commandOverview(player, "PvP",
							new String[] {"Dein PvP aktivieren", "Dein PvP deaktivieren", "Deinen PvP-Status anzeigen", "Liste aller dezeitigen PvP-Teilnehmer"},
							new String[] {"run_command", "run_command", "run_command", "run_command"},
							new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list"},
							new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list"}
							);
					
					return true;
				}
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.pvp")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.pvp");
					return true;
				}
				
      if(args[0].equalsIgnoreCase("status")) {
					
					if(Bukkit.getOnlineMode()) {
						
						if(this.plugin.pvp.getMap().containsKey(player.getUniqueId().toString())) {
							
							String onoff = "";
							if((Boolean)this.plugin.pvp.getMap().get(player.getUniqueId().toString())) { onoff = "aktiviert"; } else { onoff = "deaktiviert"; }
							player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein PvP ist zurzeit " + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
							return true;
						}
						
						this.plugin.pvp.getMap().put(player.getUniqueId().toString(), false);
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein PvP ist zurzeit " + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
						return true;
					}
					
					if(this.plugin.pvp.getMap().containsKey(player.getName())) {
						
						String onoff = "";
						if((Boolean)this.plugin.pvp.getMap().get(player.getName())) { onoff = "aktiviert"; } else { onoff = "deaktiviert"; }
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein PvP ist zurzeit " + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
						return true;
					}
						
					this.plugin.pvp.getMap().put(player.getName(), false);
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Dein PvP ist zurzeit " + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("list")) {
					
					_pvp_list_(commandSender);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("on")) {
					
					if(Bukkit.getOnlineMode()) {
						
						if(this.plugin.pvp.getMap().containsKey(player.getUniqueId().toString())) {
							
							if(!(Boolean)this.plugin.pvp.getMap().get(player.getUniqueId().toString())) {
								
								this.plugin.pvp.getMap().put(player.getUniqueId().toString(), true);
								this.plugin.pvp.Save();
								commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
								return true;
							} else {
								
								commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Dein PvP ist bereits "  + ChatColor.GRAY + "aktiviert" + ChatColor.RED + ".");
								return true;
							}
						}
						
						this.plugin.pvp.getMap().put(player.getUniqueId().toString(), true);
						this.plugin.pvp.Save();
						commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
						return true;
					}
					
					if(this.plugin.pvp.getMap().containsKey(player.getName())) {
						
						if(!(Boolean)this.plugin.pvp.getMap().get(player.getName())) {
							
							this.plugin.pvp.getMap().put(player.getName(), true);
							this.plugin.pvp.Save();
							commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
							return true;
						} else {
							
							commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Dein PvP ist bereits "  + ChatColor.GRAY + "aktiviert" + ChatColor.RED + ".");
							return true;
						}
					}
					
					this.plugin.pvp.getMap().put(player.getName(), true);
					this.plugin.pvp.Save();
					commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("off")) {
					
					if(this.plugin.pvpList.containsKey(player.getName())) { player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "PvP-Schutz ist noch aktiv."); return true; }
					
					if(Bukkit.getOnlineMode()) {
						
						if(this.plugin.pvp.getMap().containsKey(player.getUniqueId().toString())) {
							
							if((Boolean)this.plugin.pvp.getMap().get(player.getUniqueId().toString())) {
								
								this.plugin.pvp.getMap().put(player.getUniqueId().toString(), false);
								this.plugin.pvp.Save();
								commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
								return true;
							} else {
								
								commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Dein PvP ist bereits "  + ChatColor.GRAY + "deaktiviert" + ChatColor.RED + ".");
								return true;
							}
						}
						
						this.plugin.pvp.getMap().put(player.getUniqueId().toString(), false);
						this.plugin.pvp.Save();
						commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
						return true;
					}
					
					if(this.plugin.pvp.getMap().containsKey(player.getName())) {
						
						if((Boolean)this.plugin.pvp.getMap().get(player.getName())) {
							
							this.plugin.pvp.getMap().put(player.getName(), false);
							this.plugin.pvp.Save();
							commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
							return true;
						} else {
							
							commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Dein PvP ist bereits "  + ChatColor.GRAY + "deaktiviert" + ChatColor.RED + ".");
							return true;
						}
					}
					
					this.plugin.pvp.getMap().put(player.getName(), false);
					this.plugin.pvp.Save();
					commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
					return true;
				}
			}
			
			if(args.length == 2) {
				
				if(!player.hasPermission("mycmd.command.pvp.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.pvp.other");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("status")) {
					
					_pvp_status_(commandSender, args);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("on")) {
					
					_pvp_on_(commandSender, args);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("off")) {
					
					_pvp_off_(commandSender, args);
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.pvp") && player.hasPermission("mycmd.command.pvp.other")) {
				
				this.plugin.usageMessage(player, "/pvp [<list|status|on|off>] [<Spieler>]", "suggest_command", "/pvp ", "/pvp <list|status|on|off> [<Spieler>]");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.pvp.other")) {
				
				this.plugin.usageMessage(player, "/pvp [<status|on|off>] [<Spieler>]", "suggest_command", "/pvp ", "/pvp <status|on|off> <Spieler>");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.pvp")) {
				
				this.plugin.usageMessage(player, "/pvp [<list|status|on|off>]", "suggest_command", "/pvp ", "/pvp <list|status|on|off>");
				return true;
			}
		}
		
		//Console
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("list")) {
				
				_pvp_list_(commandSender);
				return true;
			}
		}
		
		if(args.length == 2) {
			
			if(args[0].equalsIgnoreCase("status")) {
				
				_pvp_status_(commandSender, args);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("on")) {
				
				_pvp_on_(commandSender, args);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("off")) {
				
				_pvp_off_(commandSender, args);
				return true;
			}
		}
		
		this.plugin.usageMessage(commandSender, "/pvp <list|status|on|off> [<Spieler>]");
		return true;
	}
	
	public void _pvp_list_(CommandSender sender) {
		
		String str = "";
		List<String> a = new ArrayList<String>();
		int i = 0;
		
		for(Entry<String, Object> e : this.plugin.pvp.getMap().entrySet()) {
			
			Boolean onoff = false;
			
			try { onoff = (Boolean)e.getValue(); } catch (Exception ex) { ex.printStackTrace(); e.setValue(false); }
			
			if(onoff) {
				
				a.add(e.getKey());
			}
		}
		
		for (String s : a) {
			
			i++;
			String temps = "";
			if(Bukkit.getServer().getOnlineMode()) { temps = Bukkit.getServer().getOfflinePlayer(UUID.fromString(s)).getName(); } else { temps = s; }
			if(i < a.toArray().length) { str = str + ChatColor.GRAY + temps + ChatColor.GREEN + ", "; } else { str = str + ChatColor.GRAY + temps; }
		}
		
		if(a.toArray().length == 0) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es haben zurzeit " + ChatColor.GRAY + "keine" + ChatColor.GREEN + " Spieler ihr PvP aktiviert.");
		}
		
		if(a.toArray().length == 1) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es hat zurzeit " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler sein PvP aktiviert:");
			sender.sendMessage(this.plugin.pluginPrefix + str);
		}
		
		if(a.toArray().length >= 2) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es haben zurzeit " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler ihr PvP aktiviert:");
			sender.sendMessage(this.plugin.pluginPrefix + str);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void _pvp_status_(CommandSender sender, String[] arg) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.pvp.getMap().containsKey(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
				
				String onoff = "";
				if((Boolean)this.plugin.pvp.getMap().get(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) { onoff = "aktiviert"; } else { onoff = "deaktiviert"; }
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " hat sein PvP zurzeit "  + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
				return;
			}
			
			this.plugin.pvp.getMap().put(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString(), false);
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " hat sein PvP zurzeit "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
			return;
		}
		
		if(this.plugin.pvp.getMap().containsKey(arg[1])) {
			
			String onoff = "";
			if((Boolean)this.plugin.pvp.getMap().get(arg[1])) { onoff = "aktiviert"; } else { onoff = "deaktiviert"; }
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " hat sein PvP zurzeit "  + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
			return;
		}
			
		this.plugin.pvp.getMap().put(arg[1], false);
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " hat sein PvP zurzeit "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
		return;
	}
	
	@SuppressWarnings("deprecation")
	public void _pvp_on_(CommandSender sender, String[] arg) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.pvp.getMap().containsKey(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
				
				if(!(Boolean)this.plugin.pvp.getMap().get(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
					
					this.plugin.pvp.getMap().put(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString(), true);
					this.plugin.pvp.Save();
					sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
					return;
				} else {
					
					sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits "  + ChatColor.GRAY + "aktiviert" + ChatColor.RED + ".");
					return;
				}
			}
		}
		
		if(this.plugin.pvp.getMap().containsKey(arg[1])) {
			
			if(!(Boolean)this.plugin.pvp.getMap().get(arg[1])) {
				
				this.plugin.pvp.getMap().put(arg[1], true);
				this.plugin.pvp.Save();
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
				return;
			} else {
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits "  + ChatColor.GRAY + "aktiviert" + ChatColor.RED + ".");
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void _pvp_off_(CommandSender sender, String[] arg) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.pvp.getMap().containsKey(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
				
				if((Boolean)this.plugin.pvp.getMap().get(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
					
					this.plugin.pvp.getMap().put(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString(), false);
					this.plugin.pvp.Save();
					sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
					return;
				} else {
					
					sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits "  + ChatColor.GRAY + "deaktiviert" + ChatColor.RED + ".");
					return;
				}
			}
		}
		
		if(this.plugin.pvp.getMap().containsKey(arg[1])) {
			
			if((Boolean)this.plugin.pvp.getMap().get(arg[1])) {
				
				this.plugin.pvp.getMap().put(arg[1], false);
				this.plugin.pvp.Save();
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
				return;
			} else {
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits "  + ChatColor.GRAY + "deaktiviert" + ChatColor.RED + ".");
				return;
			}
		}
	}
}
