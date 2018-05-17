package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandMotd implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandMotd(MyCmd mycmd) { this.plugin = mycmd; }
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.motd")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.motd");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("1")) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.BLUE + " der " + ChatColor.GRAY + "MOTD" + ChatColor.BLUE + " lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1")));
					return true;
				}
				
				if(args[0].equalsIgnoreCase("2")) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 2" + ChatColor.BLUE + " der " + ChatColor.GRAY + "MOTD" + ChatColor.BLUE + " lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd2")));
					return true;
				}
			}
			
			if(args.length >= 2) {
				
				if(!player.hasPermission("mycmd.command.motd.edit")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.motd.edit");
					return true;
				}
				
				String message = "";
				
				for(int i = 1; i < args.length; i++) { 
					
					if(i + 1 == args.length) { message += args[i]; continue; }
					message += args[i] + " ";
				}
				
				if(args[0].equalsIgnoreCase("1")) {
					
					if(containsInvalidCharacters(message)) { player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Folgende Zeichen sind nicht erlaubt: \" \\"); return true; }
					this.plugin.config.getMap().put("motd1", this.plugin.toAlternativeColorcode("§", message));
					this.plugin.config.Save();
					_refreshtab_();
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " wurde aktualisiert.");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("2")) {
					
					if(containsInvalidCharacters(message)) { player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Folgende Zeichen sind nicht erlaubt: \" \\"); return true; }
					this.plugin.config.getMap().put("motd2", this.plugin.toAlternativeColorcode("§", message));
					this.plugin.config.Save();
					_refreshtab_();
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 2" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " wurde aktualisiert.");
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.motd") && player.hasPermission("mycmd.command.motd.edit")) {
				
				this.plugin.usageMessage(player, "/motd <1|2> [<Text ...>]", "suggest_command", "/motd ", "/motd <1|2> [<Text ...>]");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.motd.edit")) {
				
				this.plugin.usageMessage(player, "/motd <1|2> <Text ...>", "suggest_command", "/motd ", "/motd <1|2> <Text ...>");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.motd")) {
				
				this.plugin.usageMessage(player, "/motd <1|2>", "suggest_command", "/motd ", "/motd <1|2>");
				return true;
			}
		}
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("1")) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.BLUE + " der " + ChatColor.GRAY + "MOTD" + ChatColor.BLUE + " lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1")));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("2")) {
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.BLUE + " der " + ChatColor.GRAY + "MOTD" + ChatColor.BLUE + " lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1")));
				return true;
			}
		}
		
		if(args.length >= 2) {
			
			String message = "";
			
			for(int i = 1; i < args.length; i++) { 
				
				if(i + 1 == args.length) { message += args[i]; continue; }
				message += args[i] + " ";
			}
			
			if(args[0].equalsIgnoreCase("1")) {
				
				if(containsInvalidCharacters(message)) { commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Folgende Zeichen sind nicht erlaubt: \" \\"); return true; }
				this.plugin.config.getMap().put("motd1", this.plugin.toAlternativeColorcode("§", message));
				this.plugin.config.Save();
				_refreshtab_();
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " wurde aktualisiert.");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("2")) {
				
				if(containsInvalidCharacters(message)) { commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Folgende Zeichen sind nicht erlaubt: \" \\"); return true; }
				this.plugin.config.getMap().put("motd2", this.plugin.toAlternativeColorcode("§", message));
				this.plugin.config.Save();
				_refreshtab_();
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 2" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " wurde aktualisiert.");
				return true;
			}
		}
		
		this.plugin.usageMessage(commandSender, "/motd <1|2> [<Text ...>]");
		return true;
	}
	
	public void _refreshtab_() {
		
		String header = this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1"));
		String footer = this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd2"));
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			
			this.plugin.sendPlayerlistHeaderFooter(p, header, footer);
		}
		
		return;
	}
	
	public boolean containsInvalidCharacters(String m) {
		
		boolean b = false;
		if(m.contains("\"")) { b = true; }
		if(m.contains("\\")) { b = true; }
		return b;
	}
}
