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
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.tell")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.tell"));
				return true;
			}
			
			if(arg.length > 1) {
				
				if(!player.hasPermission("mycmd.command.tell")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage());
					return true;
				}
				
				if(!player.getName().equals(arg[0])) {
					
					if(arg[0].equals("KONSOLE")) {
						
						String msg = "";
						
						for(int i = 1; i < arg.length; i++) {
							
							msg = msg + arg[i] + " ";
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
					
					if(Bukkit.getOfflinePlayer(arg[0]).isOnline()) {
						
						String msg = "";
						
						for (int i = 1; i < arg.length; i++) {
							
							msg = msg + arg[i] + " ";
						}
						
						this.plugin.whisper.put(player.getName(), arg[0]);
						this.plugin.whisper.put(arg[0], player.getName());
						player.sendMessage(
								this.plugin.pluginPrefix +
								this.plugin.toColorcode(
										"&",
										((String)this.plugin.config.getMap().get("whisper_sender")).toString()
										.replace("%target_player_displayname%", Bukkit.getPlayer(arg[0]).getDisplayName())
										.replace("%target_player%", Bukkit.getPlayer(arg[0]).getName())
										.replace("%message%", msg)
								)
						);
						Bukkit.getPlayer(arg[0]).sendMessage(
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
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist offline.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst nicht mit dir selbst schreiben. :(");
				return true;
			}
			
			if (player.hasPermission("mycmd.command.rank")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/<tell|t|whisper|w|msg> <Spieler> <Nachricht ...>", "suggest_command", "/t ", "/<tell|t|whisper|w|msg> <Spieler> <Nachricht ...>"));
				return true;
			}
		}
		
		//Console
		if(arg.length > 1) {
			
			if(!arg[0].equals("KONSOLE")) {
				
				if(Bukkit.getOfflinePlayer(arg[0]).isOnline()) {
					
					String msg = "";
					
					for (int i = 1; i < arg.length; i++) {
						
						msg = msg + arg[i] + " ";
					}
					
					this.plugin.whisper.put("KONSOLE", arg[0]);
					this.plugin.whisper.put(arg[0], "KONSOLE");
					Bukkit.getConsoleSender().sendMessage(
							this.plugin.pluginPrefix +
							this.plugin.toColorcode(
									"&",
									((String)this.plugin.config.getMap().get("whisper_sender")).toString()
									.replace("%target_player_displayname%", Bukkit.getPlayer(arg[0]).getDisplayName())
									.replace("%target_player%", Bukkit.getPlayer(arg[0]).getName())
									.replace("%message%", msg)
							)
					);
					Bukkit.getPlayer(arg[0]).sendMessage(
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
				
				sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist offline.");
				return true;
			}
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst nicht mit dir selbst schreiben. :(");
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/<tell|t|whisper|w|msg> <Spieler> <Nachricht ...>"));
		return true;
	}
}
