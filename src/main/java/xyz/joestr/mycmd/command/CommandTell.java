package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandTell implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandTell(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.tell")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.tell");
				return true;
			}
			
			if(args.length > 1) {
				
				if(!player.hasPermission("mycmd.command.tell")) {
					
					this.plugin.noPermissionMessage(player);
					return true;
				}
				
				if(!player.getName().equals(args[0])) {
					
					if(args[0].equals("KONSOLE")) {
						
						String msg = "";
						
						for(int i = 1; i < args.length; i++) {
							
							msg = msg + args[i] + " ";
						}
						
						this.plugin.whisper.put(player.getName(), "KONSOLE");
						this.plugin.whisper.put("KONSOLE", player.getName());
						player.sendMessage(
								this.plugin.pluginPrefix +
								this.plugin.toColorcode(
										"&",
										this.plugin.config.getMap().get("whisper_sender").toString()
										.replace("%target_player_displayname%", ChatColor.WHITE + "KONSOLE")
										.replace("%target_player%", ChatColor.WHITE + "KONSOLE")
										.replace("%message%", msg)
								)
						);
						Bukkit.getConsoleSender().sendMessage(
								this.plugin.pluginPrefix +
								this.plugin.toColorcode(
										"&",
										this.plugin.config.getMap().get("whisper_target").toString()
										.replace("%sender_player_displayname%", player.getDisplayName())
										.replace("%sender_player%", player.getName())
										.replace("%message%", msg)
								)
						);
						return true;
					}
					
					if(Bukkit.getOfflinePlayer(args[0]).isOnline()) {
						
						String msg = "";
						
						for (int i = 1; i < args.length; i++) {
							
							msg = msg + args[i] + " ";
						}
						
						this.plugin.whisper.put(player.getName(), args[0]);
						this.plugin.whisper.put(args[0], player.getName());
						player.sendMessage(
								this.plugin.pluginPrefix +
								this.plugin.toColorcode(
										"&",
										((String)this.plugin.config.getMap().get("whisper_sender")).toString()
										.replace("%target_player_displayname%", Bukkit.getPlayer(args[0]).getDisplayName())
										.replace("%target_player%", Bukkit.getPlayer(args[0]).getName())
										.replace("%message%", msg)
								)
						);
						Bukkit.getPlayer(args[0]).sendMessage(
								this.plugin.pluginPrefix +
								this.plugin.toColorcode(
										"&",
										((String)this.plugin.config.getMap().get("whisper_target")).toString()
										.replace("%sender_player_displayname%", player.getDisplayName())
										.replace("%sender_player%", player.getName())
										.replace("%message%", msg)
								)
						);
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.RED + " ist offline.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst nicht mit dir selbst schreiben. :(");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.tell")) {
				
				this.plugin.usageMessage(player, "/<tell|t|whisper|w|msg> <Spieler> <Nachricht ...>", "suggest_command", "/t ", "/<tell|t|whisper|w|msg> <Spieler> <Nachricht ...>");
				return true;
			}
		}
		
		//Console
		if(args.length > 1) {
			
			if(!args[0].equals("KONSOLE")) {
				
				if(Bukkit.getOfflinePlayer(args[0]).isOnline()) {
					
					String msg = "";
					
					for (int i = 1; i < args.length; i++) {
						
						msg = msg + args[i] + " ";
					}
					
					this.plugin.whisper.put("KONSOLE", args[0]);
					this.plugin.whisper.put(args[0], "KONSOLE");
					Bukkit.getConsoleSender().sendMessage(
							this.plugin.pluginPrefix +
							this.plugin.toColorcode(
									"&",
									((String)this.plugin.config.getMap().get("whisper_sender")).toString()
									.replace("%target_player_displayname%", Bukkit.getPlayer(args[0]).getDisplayName())
									.replace("%target_player%", Bukkit.getPlayer(args[0]).getName())
									.replace("%message%", msg)
							)
					);
					Bukkit.getPlayer(args[0]).sendMessage(
							this.plugin.pluginPrefix +
							this.plugin.toColorcode(
									"&",
									((String)this.plugin.config.getMap().get("whisper_target")).toString()
									.replace("%sender_player_displayname%", ChatColor.WHITE + "KONSOLE")
									.replace("%sender_player%", ChatColor.WHITE + "KONSOLE")
									.replace("%message%", msg)
							)
					);
					return true;
				}
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED + " ist offline.");
				return true;
			}
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst nicht mit dir selbst schreiben. :(");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/<tell|t|whisper|w|msg> <Spieler> <Nachricht ...>");
		return true;
	}
}
