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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.motd")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.motd"));
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("1")) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1")));
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("2")) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 2" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd2")));
					return true;
				}
			}
			
			if(arg.length >= 2) {
				
				if(!player.hasPermission("mycmd.command.motd.edit")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.motd.edit"));
					return true;
				}
				
				String message = "";
				
				for(int i = 1; i < arg.length; i++) { 
					
					if(i + 1 == arg.length) { message += arg[i]; continue; }
					message += arg[i] + " ";
				}
				
				if(arg[0].equalsIgnoreCase("1")) {
					
					if(cIC(message)) { player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Folgende Zeichen sind nicht erlaubt: \" \\"); return true; }
					this.plugin.config.getMap().put("motd1", this.plugin.toAlternativeColorcode("§", message));
					this.plugin.config.Save();
					_refreshtab_();
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " wurde aktualisiert.");
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("2")) {
					
					if(cIC(message)) { player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Folgende Zeichen sind nicht erlaubt: \" \\"); return true; }
					this.plugin.config.getMap().put("motd2", this.plugin.toAlternativeColorcode("§", message));
					this.plugin.config.Save();
					_refreshtab_();
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 2" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " wurde aktualisiert.");
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.motd") && player.hasPermission("mycmd.command.motd.edit")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/motd <1|2> [<Text ...>]", "suggest_command", "/motd ", "/motd <1|2> [<Text ...>]"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.motd.edit")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/motd <1|2> <Text ...>", "suggest_command", "/motd ", "/motd <1|2> <Text ...>"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.motd")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/motd <1|2>", "suggest_command", "/motd ", "/motd <1|2>"));
				return true;
			}
		}
		
		if(arg.length == 1) {
			
			if(arg[0].equalsIgnoreCase("1")) {
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1")));
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("2")) {
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " lautet: " + ChatColor.RESET + this.plugin.toColorcode("&", (String)this.plugin.config.getMap().get("motd1")));
				return true;
			}
		}
		
		if(arg.length >= 2) {
			
			String message = "";
			
			for(int i = 1; i < arg.length; i++) { 
				
				if(i + 1 == arg.length) { message += arg[i]; continue; }
				message += arg[i] + " ";
			}
			
			if(arg[0].equalsIgnoreCase("1")) {
				
				if(cIC(message)) { sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Folgende Zeichen sind nicht erlaubt: \" \\"); return true; }
				this.plugin.config.getMap().put("motd1", this.plugin.toAlternativeColorcode("§", message));
				this.plugin.config.Save();
				_refreshtab_();
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 1" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " wurde aktualisiert.");
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("2")) {
				
				if(cIC(message)) { sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Folgende Zeichen sind nicht erlaubt: \" \\"); return true; }
				this.plugin.config.getMap().put("motd2", this.plugin.toAlternativeColorcode("§", message));
				this.plugin.config.Save();
				_refreshtab_();
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "Zeile 2" + ChatColor.GREEN + " der " + ChatColor.GRAY + "MOTD" + ChatColor.GREEN + " wurde aktualisiert.");
				return true;
			}
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/motd <1|2> [<Text ...>]"));
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
	
	public boolean cIC(String m) {
		
		boolean b = false;
		if(m.contains("\"")) { b = true; }
		if(m.contains("\\")) { b = true; }
		return b;
	}
}
