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
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.delhome") && !player.hasPermission("mycmd.command.delhome.other")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.delhome")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.delhome");
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes.getMap().containsKey(player.getUniqueId().toString())) {
						
						this.plugin.homes.getMap().remove(player.getUniqueId().toString());
						this.plugin.homes.Save();
						player.sendMessage(ChatColor.GREEN + "Du hast deinen Home-Punkt gelöscht.");
						return true;
					}
					
					player.sendMessage(ChatColor.RED + "Du hast noch keinen Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes.getMap().containsKey(player.getName())) {
					
					this.plugin.homes.getMap().remove(player.getName());
					this.plugin.homes.Save();
					player.sendMessage(ChatColor.GREEN + "Du hast deinen Home-Punkt gelöscht.");
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Du hast noch keinen Home-Punkt gesetzt.");
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.delhome.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.delhome.other");
					return true;
				}
				
				_delhome_other_(sender, arg[0]);
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
		if(arg.length == 1) {
			
			_delhome_other_(sender, arg[0]);
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/delhome <Spieler>"));
		return true;
		//End Console
	}
	
	/**
	 * 
	 * @param sender Specifies the sender of the command.
	 * @param string Specifies a player's name.
	 */
	@SuppressWarnings("deprecation")
	public void _delhome_other_(CommandSender sender, String string) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.homes.getMap().containsKey(Bukkit.getOfflinePlayer(string).getUniqueId().toString())) {
				
				this.plugin.homes.getMap().remove(Bukkit.getOfflinePlayer(string).getUniqueId().toString());
				this.plugin.homes.Save();
				sender.sendMessage(ChatColor.GREEN + "Du hast den Home-Punkt vom Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " gelöscht.");
				return;
			}
			
			sender.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + string + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
			return;
		}
		
		if(this.plugin.homes.getMap().containsKey(string)) {
			
			this.plugin.homes.getMap().remove(string);
			this.plugin.homes.Save();
			sender.sendMessage(ChatColor.GREEN + "Du hast den Home-Punkt vom Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " gelöscht.");
			return;
		}
		
		sender.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + string + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
		return;
	}
}
