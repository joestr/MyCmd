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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		// Wenn der Befehl von einem Spieler gesendet wurde
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.rank")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.rank"));
				return true;
			}
			
			// /rank 
			if(arg.length == 0) {
				
				// Gleicher Check wie Zeile 30 bsi 34
				
				this.plugin.commandOverview(player, "Rang",
						new String[] {"Ränge aktivieren", "Ränge deaktivieren", "Ränge neu laden", "Alle Ränge auflisten", "Einen Rang hinzufügen", "Einen Rang entfernen"},
						new String[] {"run_command", "run_command", "run_command", "run_command", "suggest_command", "suggest_command"},
						new String[] {"/rank on", "/rank off", "/rank reload", "/rank list", "/rank add ", "/rank remove "},
						new String[] {"/rank on", "/rank off", "/rank reload", "/rank list", "/rank add <Rang> <Präfix> <Suffix> <Farbe>", "/rank remove <Rang>"});
				return true;
			}
			
			// /rank {on,off,reload,list}
			if(arg.length == 1) {
				
				// Gleicher Check wie Zeile 30 bsi 34
				
				if(arg[0].equalsIgnoreCase("on")) {
					
					_rank_on_(sender);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("off")) {
					
					_rank_off_(sender);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("reload")) {
					
					_rank_reload_(sender);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("list")) {
					
					_rank_list_(sender);
					return true;
				}
			}
			
			if(arg.length == 2) {
				
				if(arg[0].equalsIgnoreCase("remove")) {
					
					_rank_remove_(sender, arg[1]);
					return true;
				}
			}
			
			if(arg.length == 5) {
				
				if(arg[0].equalsIgnoreCase("off")) {
					
					_rank_add_(sender, arg[1], arg[2], arg[3], arg[4]);
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.rank")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/rank", "run_command", "/rank", "/rank"));
			}
			return true;
		}
		
		//Console
		if(arg.length == 1) {
			
			// Gleicher Check wie Zeile 30 bsi 34
			
			if(arg[0].equalsIgnoreCase("on")) {
				
				_rank_on_(sender);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("off")) {
				
				_rank_off_(sender);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("reload")) {
				
				_rank_reload_(sender);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("list")) {
				
				_rank_list_(sender);
				return true;
			}
		}
		
		if(arg.length == 2) {
			
			if(arg[0].equalsIgnoreCase("remove")) {
				
				_rank_remove_(sender, arg[1]);
				return true;
			}
		}
		
		if(arg.length == 5) {
			
			if(arg[0].equalsIgnoreCase("off")) {
				
				_rank_add_(sender, arg[1], arg[2], arg[3], arg[4]);
				return true;
			}
		}
		
		sender.sendMessage(this.plugin.usageMessage("/rank <on|off|reload|list|show|add|remove> [Rang] [Präfix] [Suffix] [Farbe]"));
		return true;
	}
	
	private void _rank_add_(CommandSender sender, String string, String string2, String string3, String string4) {
		
		if(this.plugin.ranks.getMap().containsKey(string)) {
			
			this.plugin.ranks.getMap().put(string, string2 + ";" + string3 + ";" + string4);
			this.plugin.ranks.Save();
			sender.sendMessage(ChatColor.GREEN + "Rang " + ChatColor.GRAY + string + ChatColor.GREEN + " hinzugefügt.");
		} else {
			
			sender.sendMessage(ChatColor.RED + "Rang " + ChatColor.GRAY + string + ChatColor.RED + " existiert bereits.");
		}
	}

	private void _rank_remove_(CommandSender sender, String string) {
		
		if(this.plugin.scoreboard.getEntryTeam(string) != null) {
			
			this.plugin.scoreboard.getTeams().remove(this.plugin.scoreboard.getEntryTeam(string));
			sender.sendMessage(ChatColor.GREEN + "Rang " + ChatColor.GRAY + string + ChatColor.GREEN + " entfernt.");
		} else {
			
			sender.sendMessage(ChatColor.RED + "Rang " + ChatColor.GRAY + string + ChatColor.RED + " existiert nicht.");
		}
	}

	private void _rank_list_(CommandSender sender) {
		
		sender.sendMessage(ChatColor.GREEN + "Ränge:");
		
		for(Team t : this.plugin.scoreboard.getTeams()) {
			
			sender.sendMessage(ChatColor.GRAY + t.getName() + ChatColor.GREEN + ": "
					+ ChatColor.GRAY + t.getPrefix() + ChatColor.GREEN + ", "
					+ ChatColor.GRAY + t.getSuffix() + ChatColor.GREEN + ", "
					+ ChatColor.GRAY + t.getColor()
			);
		}
		
	}

	private void _rank_off_(CommandSender sender) {
		
		sender.sendMessage(ChatColor.RED + "Noch nicht implementiert.");
	}

	private void _rank_on_(CommandSender sender) {
		
		sender.sendMessage(ChatColor.RED + "Noch nicht implementiert.");
	}
	
	public void _rank_reload_(CommandSender sender) {
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			
			for(String str : this.plugin.ranks.getMap().keySet()) {
				
				if(p.hasPermission("mycmd.rank." + str) && (this.plugin.scoreboard.getTeam(str) != null)) {
					
					this.plugin.scoreboard.getTeam(str).addEntry(p.getName());
					// Tab-Liste
					p.setPlayerListName(this.plugin.scoreboard.getTeam(str).getPrefix() + p.getName() + this.plugin.scoreboard.getTeam(str).getSuffix());
					// Anzeigename
					p.setDisplayName(this.plugin.scoreboard.getTeam(str).getColor() + p.getName());
				} else {
					
					p.setDisplayName(p.getName());
				}
			}
			
			p.setScoreboard(this.plugin.scoreboard);
		}
		
		sender.sendMessage(ChatColor.GREEN + "Die Ränge wurden neu gesetzt.");
		return;
	}
}
