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
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.tpaccept")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.tpaccept");
				return true;
			}
			
			if(arg.length == 0) {
				
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
							
							player.sendMessage(
									player.getDisplayName() +
									ChatColor.GREEN +
									" wurde zu dir teleportiert."
							);
							
							Bukkit.getPlayer(
									(String) this.plugin.tpaSwitched.get(
											player.getName()
									)
							).sendMessage(
									ChatColor.GREEN + "Du wurdest zu " +
											Bukkit.getPlayer(
													(String)this.plugin.tpaSwitched.get(
															player.getName()
													)
											).getDisplayName() +
									ChatColor.GREEN +
									" teleportiert."
							);
							
							this.plugin.tpa.remove(this.plugin.tpaSwitched.get(player.getName()));
							this.plugin.tpaSwitched.remove(player.getName());
							return true;
						}
						
						player.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + (String)this.plugin.tpaSwitched.get(player.getName()) + ChatColor.GRAY + " ist gerade nicht online.");
						return true;
					}
					
					player.sendMessage(ChatColor.RED + "Du hast eine TP-Anfrage erhalten, aber niemand hat dir eine gesendet!?");
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
							
							player.sendMessage(
									ChatColor.GREEN +
									"Du wurdest zu " +
									player.getDisplayName() +
									ChatColor.GREEN +
									" teleportiert."
							);
							
							Bukkit.getPlayer(
									(String) this.plugin.tpahereSwitched.get(
											player.getName()
									)
							).sendMessage(
									Bukkit.getPlayer(
											(String) this.plugin.tpahereSwitched.get(
													player.getName()
											)
									).getDisplayName() + 
									ChatColor.GREEN + 
									" wurde zu dir teleportiert."
							);
							
							this.plugin.tpahere.remove(this.plugin.tpahereSwitched.get(player.getName()));
							this.plugin.tpahereSwitched.remove(player.getName());
							return true;
						}
						
						player.sendMessage(ChatColor.GRAY + (String)this.plugin.tpahereSwitched.get(player.getName()) + ChatColor.GRAY + " ist gerade nicht online.");
						return true;
					}
					
					player.sendMessage(ChatColor.RED + "Du hast eine TP-Anfrage erhalten, aber niemand hat dir eine gesendet!?");
					this.plugin.tpahereSwitched.remove(player.getName());
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Du hast keine TP-Anfrage erhalten.");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.tpaccept")) {
				
				this.plugin.usageMessage(player, "/tpaccept", "run_command", "/tpaccept", "/tpaccept");
				return true;
			}
		}
		
		//Console
		if(arg.length == 0) {
			
			sender.sendMessage(ChatColor.RED + "Der Befehl /tpaccept ist in der Konsole nicht verfügbar.");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/tpaccept"));
		return true;
	}
}
