package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import xyz.joestr.mycmd.MyCmd;

public class CommandRank implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandRank(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		// Wenn der Befehl von einem Spieler gesendet wurde
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.rank")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.rank");
				return true;
			}
			
			// /rank 
			if(args.length == 0) {
				
				// Gleicher Check wie Zeile 30 bsi 34
				
				this.plugin.commandOverview(player, "Rang",
						new String[] {"Ränge aktivieren", "Ränge deaktivieren", "Ränge neu laden", "Alle Ränge auflisten", "Einen Rang hinzufügen", "Einen Rang entfernen"},
						new String[] {"run_command", "run_command", "run_command", "run_command", "suggest_command", "suggest_command"},
						new String[] {"/rank on", "/rank off", "/rank reload", "/rank list", "/rank add ", "/rank remove "},
						new String[] {"/rank on", "/rank off", "/rank reload", "/rank list", "/rank add <Rang> <Präfix> <Suffix> <Anzeigename-Präfix>", "/rank remove <Rang>"});
				return true;
			}
			
			// /rank {on,off,reload,list}
			if(args.length == 1) {
				
				// Gleicher Check wie Zeile 30 bsi 34
				
				if(args[0].equalsIgnoreCase("on")) {
					
					_rank_on_(commandSender);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("off")) {
					
					_rank_off_(commandSender);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("reload")) {
					
					_rank_reload_(commandSender);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("list")) {
					
					_rank_list_(commandSender);
					return true;
				}
			}
			
			if(args.length == 2) {
				
				if(args[0].equalsIgnoreCase("remove")) {
					
					_rank_remove_(commandSender, args[1]);
					return true;
				}
			}
			
			if(args.length == 5) {
				
				if(args[0].equalsIgnoreCase("add")) {
					
					_rank_add_(
							commandSender,
							args[1],
							this.plugin.replaceSpecialWhitespaceChar(args[2]),
							this.plugin.replaceSpecialWhitespaceChar(args[3]),
							this.plugin.replaceSpecialWhitespaceChar(args[4])
					);
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.rank")) {
				
				this.plugin.usageMessage(player, "/rank <on|off|reload|list|add|remove> [Rang] [Präfix] [Suffix] [Anzeigename-Präfix]", "suggest_command", "/rank ", "/rank <on|off|reload|list|add|remove> [Rang] [Präfix] [Suffix] [Anzeigename-Präfix]");
			}
			return true;
		}
		
		//Console
		if(args.length == 1) {
			
			// Gleicher Check wie Zeile 30 bsi 34
			
			if(args[0].equalsIgnoreCase("on")) {
				
				_rank_on_(commandSender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("off")) {
				
				_rank_off_(commandSender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("reload")) {
				
				_rank_reload_(commandSender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("list")) {
				
				_rank_list_(commandSender);
				return true;
			}
		}
		
		if(args.length == 2) {
			
			if(args[0].equalsIgnoreCase("remove")) {
				
				_rank_remove_(commandSender, args[1]);
				return true;
			}
		}
		
		if(args.length == 5) {
			
			if(args[0].equalsIgnoreCase("add")) {
				
				_rank_add_(
						commandSender,
						args[1],
						this.plugin.replaceSpecialWhitespaceChar(args[2]),
						this.plugin.replaceSpecialWhitespaceChar(args[3]),
						this.plugin.replaceSpecialWhitespaceChar(args[4])
				);
				return true;
			}
		}
		
		this.plugin.usageMessage(commandSender, "/rank <on|off|reload|list|add|remove> [Rang] [Präfix] [Suffix] [Anzeigename-Präfix]");
		return true;
	}
	
	private void _rank_add_(CommandSender sender, String string, String string2, String string3, String string4) {
		
		if(string2.length() > 16) {
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Präfix ist zu lang.");
			return;
		}
		
		if(string2.length() > 16) {
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Suffix ist zu lang.");
			return;
		}
		
		if(string2.length() > 16) {
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Anzeigename-Präfix ist zu lang.");
			return;
		}
		
		if(!this.plugin.ranks.getMap().containsKey(string)) {
			
			this.plugin.ranks.getMap().put(string, string2 + ";" + string3 + ";" + string4);
			this.plugin.ranks.Save();
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Rang " + ChatColor.GRAY + string + ChatColor.GREEN + " hinzugefügt.");
			_rank_reload_(sender);
		} else {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Rang " + ChatColor.GRAY + string + ChatColor.RED + " existiert bereits.");
		}
	}

	private void _rank_remove_(CommandSender sender, String string) {
		
		if(this.plugin.ranks.getMap().containsKey(string)) {
			
			this.plugin.ranks.getMap().remove(string);
			this.plugin.ranks.Save();
			this.plugin.scoreboard.getTeam(string).unregister();
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Rang " + ChatColor.GRAY + string + ChatColor.GREEN + " entfernt.");
			_rank_reload_(sender);
		} else {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Rang " + ChatColor.GRAY + string + ChatColor.RED + " existiert nicht.");
		}
	}

	private void _rank_list_(CommandSender sender) {
		
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Ränge:");
		
		for(Team t : this.plugin.scoreboard.getTeams()) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + t.getName() + ChatColor.GREEN + ": '"
					+ ChatColor.GRAY + this.plugin.toAlternativeColorcode("§", t.getPrefix()) + ChatColor.GREEN + "', '"
					+ ChatColor.GRAY + this.plugin.toAlternativeColorcode("§", t.getSuffix()) + ChatColor.GREEN + "', '"
					+ ChatColor.GRAY + this.plugin.toAlternativeColorcode("§", t.getDisplayName()) + ChatColor.GREEN + "'"
			);
		}
		
	}

	private void _rank_off_(CommandSender sender) {
		
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Noch nicht implementiert.");
	}

	private void _rank_on_(CommandSender sender) {
		
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Noch nicht implementiert.");
	}
	
	public void _rank_reload_(CommandSender sender) {
				
		for(String string : this.plugin.ranks.getMap().keySet()) {
			
			String tString = (String)this.plugin.ranks.getMap().get(string);
			String[] tStrings = null;
			
			if(tString.contains(";")) {
				tStrings = tString.split(";");
			}
			
			if(tStrings.length < 3 || tStrings.length > 4) {
				continue;
			}
			
			if(this.plugin.scoreboard.getTeam(string) == null) {
				
				this.plugin.scoreboard.registerNewTeam(string);
			}
			
			try {
				this.plugin.scoreboard.getTeam(string).setPrefix(this.plugin.toColorcode("&", tStrings[0]));
			} catch(IllegalStateException | IllegalArgumentException e) {
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Fehler beim setzen des Präfixes vom Rang " + ChatColor.GRAY + string + ChatColor.RED + ".");
			}
			
			try {
				this.plugin.scoreboard.getTeam(string).setSuffix(this.plugin.toColorcode("&", tStrings[1]));
			} catch(IllegalStateException | IllegalArgumentException e) {
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Fehler beim setzen des Suffixes vom Rang " + ChatColor.GRAY + string + ChatColor.RED + ".");
			}
			
			try {
				this.plugin.scoreboard.getTeam(string).setDisplayName(this.plugin.toColorcode("&", tStrings[2]));
			} catch(IllegalStateException | IllegalArgumentException e) {
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Fehler beim setzen des Anzeigenamen-Präfixes vom Rang " + ChatColor.GRAY + string + ChatColor.RED + ".");
			}
		}
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			
			for(String str : this.plugin.ranks.getMap().keySet()) {
				
				if(this.plugin.scoreboard.getTeam(str) == null) {
					continue;
				}
				
				Team t = this.plugin.scoreboard.getTeam(str);
				
				if(p.hasPermission("mycmd.rank." + t.getName())) {
					
					if(!t.hasEntry(p.getName())) {
						t.addEntry(p.getName());
						p.setDisplayName(t.getDisplayName() + p.getName());
					} else {
						p.setDisplayName(t.getDisplayName() + p.getName());
					}
				} else {
					
					if(t.hasEntry(p.getName())) {
						t.removeEntry(p.getName());
						p.setDisplayName(p.getName());
					}
				}
			}
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Die Ränge wurden neu gesetzt.");
		return;
	}
}
