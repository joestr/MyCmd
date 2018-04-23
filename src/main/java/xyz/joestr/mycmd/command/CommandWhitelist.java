package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.Collection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandWhitelist implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandWhitelist(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.whitelist")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.whitelist"));
				return true;
			}
			
			if(arg.length == 0) {
				
				if(player.hasPermission("mycmd.command.whitelist")) {
					
					this.plugin.commandOverview(player, "Whitelist",
							new String[] {"Whitelist aktivieren", "Whitelist deaktivieren", "Whitelist Status anzeigen", "Spieler auf der Whitelist anzeigen", "Einen Spieler auf die Whitelist setzen", "Einen Spieler von der Whitelist entfernen", "Whitelist-Nachricht ändern"},
							new String[] {"run_command", "run_command", "run_command", "run_command", "suggest_command", "suggest_command", "suggest_command"},
							new String[] {"/whitelist on", "/whitelist off", "/whitelist status", "/whitelist list", "/whitelist add ", "/whitelist remove ", "/whitelist message "},
							new String[] {"/whitelist on", "/whitelist off", "/whitelist status", "/whitelist list", "/whitelist add <Spieler>", "/whitelist remove <Spieler>", "/whitelist message <Nachricht ...>"});
					return true;
				}
			}
			
			if(arg.length == 1) {
				
				if(arg[0].equalsIgnoreCase("on")) {
					
					_whitelist_on_(sender);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("off")) {
					
					_whitelist_off_(sender);
					return true;
				}

				if(arg[0].equalsIgnoreCase("reload")) {
					
					_whitelist_reload_(sender);
					return true;
				}

				if(arg[0].equalsIgnoreCase("list")) {
					
					_whitelist_list_(sender);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("status")) {
					
					_whitelist_status_(sender);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("message")) {
					
					_whitelist_message_(sender);
					return true;
				}
			}
			
			if(arg.length >= 2) {
				
				if(arg.length == 2) {
					
					if(arg[0].equalsIgnoreCase("add")) {
						
						_whitelist_add_(sender, arg);
						return true;
					}
					
					if(arg[0].equalsIgnoreCase("remove")) {
						
						_whitelist_remove_(sender, arg);
						return true;
					}
				}
				
				if(arg[0].equalsIgnoreCase("message")) {
					
					_whitelist_message_edit_(sender, arg);
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.whitelist")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/whitelist [<on|off|list|reload|status|add|remove|message>] [<Spieler>|<Nachricht ...>]", "suggest_command", "/whitelist ", "/whitelist <on|off|list|reload|status|add|remove|message> [<Spieler>|<Nachricht ...>]"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 1) {
			
			if(arg[0].equalsIgnoreCase("on")) {
				
				_whitelist_on_(sender);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("off")) {
				
				_whitelist_off_(sender);
				return true;
			}

			if(arg[0].equalsIgnoreCase("reload")) {
				
				_whitelist_reload_(sender);
				return true;
			}

			if(arg[0].equalsIgnoreCase("list")) {
				
				_whitelist_list_(sender);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("status")) {
				
				_whitelist_status_(sender);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("message")) {
				
				_whitelist_message_(sender);
				return true;
			}
		}
		
		if(arg.length >= 2) {
			
			if(arg.length == 2) {
				
				if(arg[0].equalsIgnoreCase("add")) {
					
					_whitelist_add_(sender, arg);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("remove")) {
					
					_whitelist_remove_(sender, arg);
					return true;
				}
			}
			
			if(arg[0].equalsIgnoreCase("message")) {
				
				_whitelist_message_edit_(sender, arg);
				return true;
			}
		}
		
		sender.sendMessage(this.plugin.usageMessage("/whitelist <on|off|list|reload|status|add|remove|message> [<Spieler>|<Nachricht ...>]"));
		return true;
	}
	
	public void _whitelist_on_(CommandSender sender) {
		
		if(Bukkit.getServer().hasWhitelist()) {
			
			sender.sendMessage(ChatColor.RED + "Die Whitelist ist bereits aktiviert.");
			return;
		}
			
		Bukkit.getServer().setWhitelist(true);
		sender.sendMessage(ChatColor.GREEN + "Die Whitelist wurde aktiviert.");
		
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			
			if(!player.isWhitelisted()) { player.kickPlayer(this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("whitelist-message"))); }
		}
		
		return;
	}
	
	public void _whitelist_off_(CommandSender sender) {
		
		if(!Bukkit.getServer().hasWhitelist()) {
			
			sender.sendMessage(ChatColor.RED + "Die Whitelist ist bereits deaktiviert.");
			return;
		}
		
		Bukkit.getServer().setWhitelist(false);
		sender.sendMessage(ChatColor.GREEN + "Die Whitelist wurde deaktiviert.");
		return;
	}
	
	public void _whitelist_reload_(CommandSender sender) {
		
		Bukkit.getServer().reloadWhitelist();
		sender.sendMessage(ChatColor.GREEN + "Die Whitelist wurde neu geladen.");
		return;
	}
	
	public void _whitelist_status_(CommandSender sender) {
		
		String onoff = "";
		if(Bukkit.getServer().hasWhitelist()) { onoff = "aktiviert"; } else { onoff = "deaktiviert"; }
		sender.sendMessage(ChatColor.GREEN + "Die Whitelist ist " + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
		return;
	}
	
	
	public void _whitelist_message_(CommandSender sender) {
		
		sender.sendMessage(ChatColor.GREEN + "Die Whitelist-Message lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("whitelist-message")));
		return;
	}
	
	public void _whitelist_message_edit_(CommandSender sender, String[] arg) {
		
		String message = "";
		
		for(int i = 1; i < arg.length; i++) {
			
			if(i + 1 == arg.length) { message += arg[i]; continue; }
			message += arg[i] + " ";
		}
		
		this.plugin.config.getMap().put("whitelist-message", message);
		this.plugin.config.Save();
		sender.sendMessage(ChatColor.GREEN + "Die Whitelist-Message wurde aktualisiert.");
		return;
	}
	
	public void _whitelist_list_(CommandSender sender) {
		
		String str = "";
		Collection<? extends OfflinePlayer> a = Bukkit.getServer().getWhitelistedPlayers();
		int i = 0;
		
		for(OfflinePlayer p : a) {
			
			i++;
			if(i < a.toArray().length) {
				
				str = str + ChatColor.GRAY + p.getName() + ChatColor.GREEN + ", ";
			} else {
				
				str = str + ChatColor.GRAY + p.getName();
			}
		}
		
		if(a.toArray().length == 0) {
			
			sender.sendMessage(ChatColor.GREEN + "Es sind " + ChatColor.GRAY + "keine" + ChatColor.GREEN + " Spieler auf der Whitelist.");
		}
		
		if(a.toArray().length == 1) {
			
			sender.sendMessage(ChatColor.GREEN + "Es ist " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler auf der Whitelist:");
			sender.sendMessage(str);
		}
		
		if(a.toArray().length >= 2) {
			
			sender.sendMessage(ChatColor.GREEN + "Es sind " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler auf der Whitelist:");
			sender.sendMessage(str);
		}
	}
	
	@SuppressWarnings({ "deprecation", "unlikely-arg-type" })
	public void _whitelist_add_(CommandSender sender, String[] arg) {
				
		if(Bukkit.getServer().getWhitelistedPlayers().contains(arg[1])) { sender.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits auf der Whitelist."); return; }
		
		Bukkit.getServer().getOfflinePlayer(arg[1]).setWhitelisted(true);
		sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde auf die Whitelist gesetzt.");
		return;
		
	}
	
	@SuppressWarnings("deprecation")
	public void _whitelist_remove_(CommandSender sender, String[] arg) {
				
		if(!Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getServer().getOfflinePlayer(arg[1]))) { sender.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " befindet sich nicht auf der Whitelist."); return; }
		
		Bukkit.getServer().getOfflinePlayer(arg[1]).setWhitelisted(true);
		sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde von der Whitelist entfernt.");
		return;
		
	}
}
