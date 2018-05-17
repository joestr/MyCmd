package xyz.joestr.mycmd.command;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandDelhome implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandDelhome(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//Player
		if(commandSender instanceof Player) {
			
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.delhome") && !player.hasPermission("mycmd.command.delhome.other")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.delhome")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.delhome");
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes.getMap().containsKey(player.getUniqueId().toString())) {
						
						this.plugin.homes.getMap().remove(player.getUniqueId().toString());
						this.plugin.homes.Save();
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast deinen Home-Punkt gelöscht.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinen Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes.getMap().containsKey(player.getName())) {
					
					this.plugin.homes.getMap().remove(player.getName());
					this.plugin.homes.Save();
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast deinen Home-Punkt gelöscht.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinen Home-Punkt gesetzt.");
				return true;
			}
			
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.delhome.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.delhome.other");
					return true;
				}
				
				_delhome_other_(commandSender, args[0]);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.delhome.other") && player.hasPermission("mycmd.command.delhome")) {
				
				this.plugin.usageMessage(player, "/delhome [<Spieler>]", "suggest_command", "/delhome ", "/delhome [<Spieler>]");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.delhome.other")) {
				
				this.plugin.usageMessage(player, "/delhome <Spieler>", "suggest_command", "/delhome ", "/delhome <Spieler>");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.delhome")) {
				
				this.plugin.usageMessage(player, "/delhome", "run_command", "/delhome", "/delhome");
				return true;
			}
		}
		//End Player
		
		//Console
		if(args.length == 1) {
			
			_delhome_other_(commandSender, args[0]);
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/delhome <Spieler>");
		return true;
		//End Console
	}
	
	/**
	 * 
	 * @param commandSender Specifies the sender of the command.
	 * @param string Specifies a player's name.
	 */
	@SuppressWarnings("deprecation")
	public void _delhome_other_(CommandSender commandSender, String string) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.homes.getMap().containsKey(Bukkit.getOfflinePlayer(string).getUniqueId().toString())) {
				
				this.plugin.homes.getMap().remove(Bukkit.getOfflinePlayer(string).getUniqueId().toString());
				this.plugin.homes.Save();
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast den Home-Punkt von " + ChatColor.GRAY + string + ChatColor.GREEN + " gelöscht.");
				return;
			}
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + string + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
			return;
		}
		
		if(this.plugin.homes.getMap().containsKey(string)) {
			
			this.plugin.homes.getMap().remove(string);
			this.plugin.homes.Save();
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast den Home-Punkt von " + ChatColor.GRAY + string + ChatColor.GREEN + " gelöscht.");
			return;
		}
		
		commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + string + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
		return;
	}
}
