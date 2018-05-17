package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandTpdeny implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandTpdeny(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.tpdeny")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.tpdeny");
				return true;
			}
			
			if(args.length == 0) {
				
				if (player.hasPermission("mycmd.command.tpdeny")) {
					
					if(this.plugin.tpa.containsKey(this.plugin.tpaSwitched.get(player.getName()))) {
						
						if(Bukkit.getOfflinePlayer((String)this.plugin.tpaSwitched.get(player.getName())).isOnline()) {
							
							player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast die TP-Anfrage von " + Bukkit.getPlayer((String)this.plugin.tpaSwitched.get(player.getName())).getDisplayName() + ChatColor.RED + " abgelehnt.");
							Bukkit.getPlayer((String)this.plugin.tpaSwitched.get(player.getName())).sendMessage(this.plugin.pluginPrefix + player.getDisplayName() + ChatColor.RED + " hat deine TP-Anfrage abgelehnt.");
							this.plugin.tpa.remove(this.plugin.tpaSwitched.get(player.getName()));
							this.plugin.tpaSwitched.remove(player.getName());
							return true;
						}
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast die TP-Anfrage von " + ChatColor.GRAY + (String)this.plugin.tpaSwitched.get(player.getName()) + ChatColor.RED + " abgelehnt.");
						return true;
					}
					
					if(this.plugin.tpahere.containsKey(this.plugin.tpahereSwitched.get(player.getName()))) {
						
						if(Bukkit.getOfflinePlayer((String)this.plugin.tpahereSwitched.get(player.getName())).isOnline()) {
							
							player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast die TP-Anfrage von " + Bukkit.getPlayer((String)this.plugin.tpahereSwitched.get(player.getName())).getDisplayName() + ChatColor.RED + " abgelehnt.");
							Bukkit.getPlayer((String)this.plugin.tpahereSwitched.get(player.getName())).sendMessage(this.plugin.pluginPrefix + player.getDisplayName() + ChatColor.RED + " hat deine TP-Anfrage abgelehnt.");
							this.plugin.tpahere.remove(this.plugin.tpahereSwitched.get(player.getName()));
							this.plugin.tpahereSwitched.remove(player.getName());
							return true;
						}
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast die TP-Anfrage von " + ChatColor.GRAY + (String)this.plugin.tpahereSwitched.get(player.getName()) + ChatColor.RED + " abgelehnt.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast eine TP-Anfrage erhalten, aber niemand hat dir eine gesendet!?");
					this.plugin.tpaSwitched.remove(player.getName());
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast keine TP-Anfrage erhalten.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.tpdeny")) {
				
				this.plugin.usageMessage(player, "/tpdeny", "run_command", "/tpdeny", "/tpdeny");
				return true;
			}
		}
		
		//Console
		
		if(args.length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Befehl /tpdeny ist in der Konsole nicht verfügbar.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/tpdeny");
		return true;
	}
}
