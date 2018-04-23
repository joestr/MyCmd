package xyz.joestr.mycmd.command;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandDelhome2 implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandDelhome2(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.delhome2") && !player.hasPermission("mycmd.command.delhome2.other")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.delhome2")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.delhome2");
					return true;
				}
				
				if(Bukkit.getOnlineMode()) {
					
					if(this.plugin.homes2.getMap().containsKey(player.getUniqueId().toString())) {
						
						this.plugin.homes2.getMap().remove(player.getUniqueId().toString());
						this.plugin.homes2.Save();
						player.sendMessage(ChatColor.GREEN + "Du hast deinen zweiten Home-Punkt gelöscht.");
						return true;
					}
					
					player.sendMessage(ChatColor.RED + "Du hast noch keinen zweiten Home-Punkt gesetzt.");
					return true;
				}
				
				if(this.plugin.homes2.getMap().containsKey(player.getName())) {
					
					this.plugin.homes2.getMap().remove(player.getName());
					this.plugin.homes2.Save();
					player.sendMessage(ChatColor.GREEN + "Du hast deinen zweiten Home-Punkt gelöscht.");
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Du hast noch keinen zweiten Home-Punkt gesetzt.");
				return true;
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.delhome2.other")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.delhome2.other");
					return true;
				}
				
				_delhome2_other_(sender, arg[0]);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.delhome2.other") && player.hasPermission("mycmd.command.delhome2")) {
				
				this.plugin.usageMessage(player, "/delhome2 [<Spieler>]", "suggest_command", "/delhome2W ", "/delhome2 [<Spieler>]");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.delhome2.other")) {
				
				this.plugin.usageMessage(player, "/delhome2 <Spieler>", "suggest_command", "/delhome2 ", "/delhome2 <Spieler>");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.delhome2")) {
				
				this.plugin.usageMessage(player, "/delhome2", "run_command", "/delhome", "/delhome2");
				return true;
			}
		}
		//End Player
		
		//Console
		if(arg.length == 1) {
			
			_delhome2_other_(sender, arg[0]);
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/delhome2 <Spieler>"));
		return true;
		//End Console
	}
	
	/**
	 * 
	 * @param sender Specifies the sender of the command.
	 * @param string Specifies a player's name.
	 */
	@SuppressWarnings("deprecation")
	public void _delhome2_other_(CommandSender sender, String string) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.homes2.getMap().containsKey(Bukkit.getOfflinePlayer(string).getUniqueId().toString())) {
				
				this.plugin.homes2.getMap().remove(Bukkit.getOfflinePlayer(string).getUniqueId().toString());
				this.plugin.homes2.Save();
				sender.sendMessage(ChatColor.GREEN + "Du hast den zweiten Home-Punkt vom Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " gelöscht.");
				return;
			}
			
			sender.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + string + ChatColor.RED + " hat noch keinen zweiten Home-Punkt gesetzt.");
			return;
		}
		
		if(this.plugin.homes2.getMap().containsKey(string)) {
			
			this.plugin.homes2.getMap().remove(string);
			this.plugin.homes2.Save();
			sender.sendMessage(ChatColor.GREEN + "Du hast den zweiten Home-Punkt vom Spieler " + ChatColor.GRAY + string + ChatColor.GREEN + " gelöscht.");
			return;
		}
		
		sender.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + string + ChatColor.RED + " hat noch keinen Home-Punkt gesetzt.");
		return;
	}
}
