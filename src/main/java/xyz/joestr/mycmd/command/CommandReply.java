package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandReply implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandReply(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.reply")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.reply");
				return true;
			}
			
			if(args.length > 0) {
				
				if(!player.hasPermission("mycmd.command.reply")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.reply");
					return true;
				}
				
				if(this.plugin.whisper.containsKey(player.getName())) {
					
					if(((String)this.plugin.whisper.get(player.getName())).equals("KONSOLE")) {
						
						String msg = "";
						
						for(int i = 0; i < args.length; i++) {
							
							msg = msg + args[i] + " ";
						}
						
						this.plugin.whisper.put(player.getName(), "KONSOLE");
						this.plugin.whisper.put("Server", player.getName());
						player.sendMessage(
								this.plugin.pluginPrefix +
								this.plugin.toColorcode(
										"&",
										this.plugin.config.getMap().get("whisper_sender").toString()
										.replace("%target_player_listname%", ChatColor.WHITE + "KONSOLE")
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
										.replace("%sender_player_listname%", player.getPlayerListName())
										.replace("%sender_player_displayname%", player.getDisplayName())
										.replace("%sender_player%", player.getName())
										.replace("%message%", msg)
								)
						);
						return true;
					}
					
					if(Bukkit.getOfflinePlayer((String)this.plugin.whisper.get(player.getName())).isOnline()) {
						
						String msg = "";
						
						for (int i = 0; i < args.length; i++) {
							
							msg = msg + args[i] + " ";
						}
						
						this.plugin.whisper.put(player.getName(), (String)this.plugin.whisper.get(player.getName()));
						this.plugin.whisper.put((String)this.plugin.whisper.get(player.getName()), player.getName());
						player.sendMessage(
								this.plugin.pluginPrefix +
								this.plugin.toColorcode(
										"&",
										this.plugin.config.getMap().get("whisper_sender").toString()
										.replace("%target_player_listname%", Bukkit.getPlayer((String)this.plugin.whisper.get(player.getName())).getPlayerListName())
										.replace("%target_player_displayname%", Bukkit.getPlayer((String)this.plugin.whisper.get(player.getName())).getDisplayName())
										.replace("%target_player%", Bukkit.getPlayer((String)this.plugin.whisper.get(player.getName())).getName())
										.replace("%message%", msg)
								)
						);
						Bukkit.getPlayer((String)this.plugin.whisper.get(player.getName())).sendMessage(
								this.plugin.pluginPrefix +
								this.plugin.toColorcode(
										"&",
										this.plugin.config.getMap().get("whisper_target").toString()
										.replace("%sender_player_listname%", player.getPlayerListName())
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
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinem gewhispert oder niemand hat dir gewhispert.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.reply")) {
				
				this.plugin.usageMessage(player, "/<reply|r|ans> <Nachricht ...>", "suggest_command", "/r ", "/<reply|r|ans> <Nachricht ...>");
				return true;
			}
			return true;
		}
		
		//Console
		if(args.length > 0) {
						
			if(this.plugin.whisper.containsKey("Server")) {
				
				if(Bukkit.getOfflinePlayer((String)this.plugin.whisper.get("Server")).isOnline()) {
					
					String msg = "";
					
					for (int i = 0; i < args.length; i++) {
						
						msg = msg + args[i] + " ";
					}
					
					this.plugin.whisper.put("Server", (String)this.plugin.whisper.get("Server"));
					this.plugin.whisper.put((String)this.plugin.whisper.get("Server"), "Server");
					commandSender.sendMessage(
							this.plugin.pluginPrefix +
							this.plugin.toColorcode(
									"&",
									this.plugin.config.getMap().get("whisper_sender").toString()
									.replace("%target_player_listname%", Bukkit.getPlayer((String)this.plugin.whisper.get("KONSOLE")).getPlayerListName())
									.replace("%target_player_displayname%", Bukkit.getPlayer((String)this.plugin.whisper.get("KONSOLE")).getDisplayName())
									.replace("%target_player%", Bukkit.getPlayer((String)this.plugin.whisper.get("KONSOLE")).getName())
									.replace("%message%", msg)
							)
					);
					Bukkit.getPlayer((String)this.plugin.whisper.get("Server")).sendMessage(
							this.plugin.pluginPrefix +
							this.plugin.toColorcode(
									"&",
									this.plugin.config.getMap().get("whisper_target").toString()
									.replace("%sender_player_listname%", ChatColor.WHITE + "KONSOLE")
									.replace("%sender_player_displayname%", ChatColor.WHITE + "KONSOLE")
									.replace("%sender_player%", ChatColor.WHITE + "KONSOLE")
									.replace("%message%", msg)
							)
					);
					return true;
				}
				
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.RED + " ist offline.");
				return true;
			}
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du hast noch keinem gewhispert oder niemand hat dir gewhispert.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/<reply|r|ans> <Nachricht ...>");
		return true;
	}
}
