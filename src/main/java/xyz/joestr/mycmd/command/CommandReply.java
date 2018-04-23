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
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.reply")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.reply"));
				return true;
			}
			
			if(arg.length > 0) {
				
				if(!player.hasPermission("mycmd.command.reply")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.reply"));
					return true;
				}
				
				if(this.plugin.whisper.containsKey(player.getName())) {
					
					if(((String)this.plugin.whisper.get(player.getName())).equals("KONSOLE")) {
						
						String msg = "";
						
						for(int i = 0; i < arg.length; i++) {
							
							msg = msg + arg[i] + " ";
						}
						
						this.plugin.whisper.put(player.getName(), "KONSOLE");
						this.plugin.whisper.put("Server", player.getName());
						player.sendMessage(this.plugin.toColorcode("&", this.plugin.config.getMap().get("whisper_sender").toString().replace("%target_player_displayname%", ChatColor.WHITE + "KONSOLE").replace("%message%", msg)));
						Bukkit.getConsoleSender().sendMessage(this.plugin.toColorcode("&", this.plugin.config.getMap().get("whisper_target").toString().replace("%sender_player_displayname%", player.getDisplayName()).replace("%message%", msg)));
						return true;
					}
					
					if(Bukkit.getOfflinePlayer((String)this.plugin.whisper.get(player.getName())).isOnline()) {
						
						String msg = "";
						
						for (int i = 0; i < arg.length; i++) {
							
							msg = msg + arg[i] + " ";
						}
						
						this.plugin.whisper.put(player.getName(), (String)this.plugin.whisper.get(player.getName()));
						this.plugin.whisper.put((String)this.plugin.whisper.get(player.getName()), player.getName());
						player.sendMessage(this.plugin.toColorcode("&", this.plugin.config.getMap().get("whisper_sender").toString().replace("%target_player_displayname%", Bukkit.getPlayer((String)this.plugin.whisper.get(player.getName())).getDisplayName()).replace("%message%", msg)));
						Bukkit.getPlayer((String)this.plugin.whisper.get(player.getName())).sendMessage(this.plugin.toColorcode("&", this.plugin.config.getMap().get("whisper_target").toString().replace("%sender_player_displayname%", player.getDisplayName()).replace("%message%", msg)));
						return true;
					}
					
					player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist offline.");
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Du hast noch keinem gewhispert oder niemand hat dir gewhispert.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.reply")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/<reply|r|ans> <Nachricht ...>", "suggest_command", "/r ", "/<reply|r|ans> <Nachricht ...>"));
				return true;
			}
			return true;
		}
		
		//Console
		if(arg.length > 0) {
						
			if(this.plugin.whisper.containsKey("Server")) {
				
				if(Bukkit.getOfflinePlayer((String)this.plugin.whisper.get("Server")).isOnline()) {
					
					String msg = "";
					
					for (int i = 0; i < arg.length; i++) {
						
						msg = msg + arg[i] + " ";
					}
					
					this.plugin.whisper.put("Server", (String)this.plugin.whisper.get("Server"));
					this.plugin.whisper.put((String)this.plugin.whisper.get("Server"), "Server");
					sender.sendMessage(this.plugin.toColorcode("&", this.plugin.config.getMap().get("whisper_sender").toString().replace("%target_player_displayname%", Bukkit.getPlayer((String)this.plugin.whisper.get("Server")).getDisplayName()).replace("%message%", msg)));
					Bukkit.getPlayer((String)this.plugin.whisper.get("Server")).sendMessage(this.plugin.toColorcode("&", this.plugin.config.getMap().get("whisper_target").toString().replace("%sender_player_displayname%", ChatColor.WHITE + "Server").replace("%message%", msg)));
					return true;
				}
				
				sender.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED + " ist offline.");
				return true;
			}
			
			sender.sendMessage(ChatColor.RED + "Du hast noch keinem gewhispert oder niemand hat dir gewhispert.");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/<reply|r|ans> <Nachricht ...>"));
		return true;
	}
}
