package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandTpaccept implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandTpaccept(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.tpaccept")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.tpaccept");
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.tpaccept")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.tpaccept");
					return true;
				}
				
				if(this.plugin.tpaSwitched.containsKey(player.getName())) {
					
					if(this.plugin.tpa.containsKey(this.plugin.tpaSwitched.get(player.getName()))) {
						
						if(Bukkit.getOfflinePlayer((String)this.plugin.tpaSwitched.get(player.getName())).isOnline()) {
							
							Bukkit.getPlayer(
									(String) this.plugin.tpaSwitched.get(player.getName())
							).teleport(player);
							
							player.sendMessage(this.plugin.pluginPrefix + 
									Bukkit.getPlayer(
											(String)this.plugin.tpaSwitched.get(
													player.getName()
											)
									).getDisplayName() +
									ChatColor.GREEN +
									" wurde zu dir teleportiert."
							);
							
							Bukkit.getPlayer(
									(String) this.plugin.tpaSwitched.get(
											player.getName()
									)
							).sendMessage(this.plugin.pluginPrefix + 
									ChatColor.GREEN + "Du wurdest zu " +
									player.getDisplayName() +
									ChatColor.GREEN +
									" teleportiert."
							);
							
							this.plugin.tpa.remove(this.plugin.tpaSwitched.get(player.getName()));
							this.plugin.tpaSwitched.remove(player.getName());
							return true;
						}
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Spieler " + ChatColor.GRAY + (String)this.plugin.tpaSwitched.get(player.getName()) + ChatColor.GRAY + " ist gerade nicht online.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast eine TP-Anfrage erhalten, aber niemand hat dir eine gesendet!?");
					this.plugin.tpaSwitched.remove(player.getName());
					return true;
				}
				
				if(this.plugin.tpahereSwitched.containsKey(player.getName())) {
					
					if(this.plugin.tpahere.containsKey(this.plugin.tpahereSwitched.get(player.getName()))) {
						
						if(Bukkit.getOfflinePlayer((String)this.plugin.tpahereSwitched.get(player.getName())).isOnline()) {
							
							player.teleport(
									Bukkit.getPlayer(
											(String) this.plugin.tpahereSwitched.get(
													player.getName()
											)
									)
							);
							
							player.sendMessage(this.plugin.pluginPrefix + 
									ChatColor.GREEN +
									"Du wurdest zu " +
									Bukkit.getPlayer(
											(String) this.plugin.tpahereSwitched.get(
													player.getName()
											)
									).getDisplayName() +
									ChatColor.GREEN +
									" teleportiert."
							);
							
							Bukkit.getPlayer(
									(String) this.plugin.tpahereSwitched.get(
											player.getName()
									)
							).sendMessage(this.plugin.pluginPrefix + 
									player.getDisplayName() + 
									ChatColor.GREEN + 
									" wurde zu dir teleportiert."
							);
							
							this.plugin.tpahere.remove(this.plugin.tpahereSwitched.get(player.getName()));
							this.plugin.tpahereSwitched.remove(player.getName());
							return true;
						}
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + (String)this.plugin.tpahereSwitched.get(player.getName()) + ChatColor.GRAY + " ist gerade nicht online.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast eine TP-Anfrage erhalten, aber niemand hat dir eine gesendet!?");
					this.plugin.tpahereSwitched.remove(player.getName());
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast keine TP-Anfrage erhalten.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.tpaccept")) {
				
				this.plugin.usageMessage(player, "/tpaccept", "run_command", "/tpaccept", "/tpaccept");
				return true;
			}
		}
		
		//Console
		if(args.length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Befehl /tpaccept ist in der Konsole nicht verfügbar.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/tpaccept");
		return true;
	}
}
