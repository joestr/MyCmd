package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandRank implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandRank(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.rank")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.rank"));
				return true;
			}
			
			if(arg.length == 0) {
				
				/*
				this.plugin.commandOverview(player, "PvP",
						new String[] {"Ränge aktivieren", "Ränge deaktivieren", "Ränge neu laden", "Alle Ränge auflisten", "Einen Rang anzeigen", "Einen Rang hinzufügen", "Einen Rang entfernen"},
						new String[] {"run_command", "run_command", "run_command", "run_command", "suggest_command", "suggest_command", "suggest_command"},
						new String[] {"/rank on", "/rank off", "/rank reload", "/rank list", "/rank show ", "/rank add ", "/rank remove "},
						new String[] {"/rank on", "/rank off", "/rank reload", "/rank list", "/rank show <Rang>", "/rank add <Rang> <Präfix> <Suffix>", "/rank remove <Rang>"});
				return true;
				 */
				
				if(!player.hasPermission("mycmd.command.rank")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.rank"));
					return true;
				}
				
				_rank_(sender);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.rank")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/rank", "run_command", "/home", "/home"));
			}
			return true;
		}
		
		//Console
		if(arg.length == 0) {
			
			_rank_(sender);
		}
		
		sender.sendMessage(this.plugin.usageMessage("/rank"));
		return true;
	}
	
	public void _rank_(CommandSender sender) {
		
		for(String string : this.plugin.ranks.getMap().keySet()) {
			
			if(this.plugin.scoreboard.getTeam(string) == null) {
				
				this.plugin.scoreboard.registerNewTeam(string);
				this.plugin.scoreboard.getTeam(string).setPrefix(this.plugin.toColorcode("&", (String)this.plugin.ranks.getMap().get(string)));
				this.plugin.scoreboard.getTeam(string).setSuffix(this.plugin.toColorcode("&", "&r"));
			} else {
				
				this.plugin.scoreboard.getTeam(string).setPrefix(this.plugin.toColorcode("&", (String)this.plugin.ranks.getMap().get(string)));
				this.plugin.scoreboard.getTeam(string).setSuffix(this.plugin.toColorcode("&", "&r"));
			}
		}
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			
			for(String str : this.plugin.ranks.getMap().keySet()) {
				
				if(p.hasPermission("mycmd.rank." + str) && (this.plugin.scoreboard.getTeam(str) != null)) {
					
					this.plugin.scoreboard.getTeam(str).addEntry(p.getName());
					p.setDisplayName(this.plugin.scoreboard.getTeam(str).getPrefix() + p.getName() + this.plugin.scoreboard.getTeam(str).getSuffix());
				} else { p.setDisplayName(p.getName()); }
			}
			
			p.setScoreboard(this.plugin.scoreboard);
		}
		
		sender.sendMessage(ChatColor.GREEN + "Die Ränge wurden neu gesetzt.");
		return;
	}
}
