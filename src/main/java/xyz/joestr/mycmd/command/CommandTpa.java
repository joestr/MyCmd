package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandTpa implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandTpa(MyCmd myCmd) {
		
		this.plugin = myCmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, final String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.tpa")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.tpa"));
				return true;
			}
			
			if(arg.length == 1) {
				
				if(this.plugin.tpa.containsKey(player.getName())) {
					
					player.sendMessage(ChatColor.RED + "Du kannst nur alle 60 Sekunden eine TP-Anfrage versenden.");
					return true;
				}
				
				if(player.getName().equals(arg[0])) {
					
					player.sendMessage(ChatColor.RED + "Du kannst dir selbst keine TP-Anfrage schicken.");
					return true;
				}
				
				if(Bukkit.getOfflinePlayer(arg[0]).isOnline()) {
					
					if((Bukkit.getPlayer(arg[0]).hasPermission("mycmd.command.tpdeny")) && (Bukkit.getPlayer(arg[0]).hasPermission("mycmd.command.tpaccept"))) {
						
						if(this.plugin.tpaSwitched.containsKey(arg[0])) {
							
							player.sendMessage(ChatColor.RED + "Spieler " + Bukkit.getServer().getPlayer(arg[0]).getDisplayName() + ChatColor.RED + " hat bereits eine TP-Anfrage erhalten.");
							return true;
						}
						
						this.plugin.tpa.put(player.getName(), Bukkit.getPlayer(arg[0]).getName());
						this.plugin.tpaSwitched.put(Bukkit.getPlayer(arg[0]).getName(), player.getName());
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
							
							public void run() {
									
								CommandTpa.this.plugin.tpa.remove(player.getName());
								CommandTpa.this.plugin.tpaSwitched.remove(arg[0]);
							}
						}, 600L);
							
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
							"tellraw " + arg[0] + " " + 
							"[\"\"," + 
							"{" + 
							"\"text\":\"TP-Anfrage: \",\"color\":\"green\"" + 
							"}," + 
							"{" + 
							"\"text\":\"" + player.getDisplayName() + "\"" + 
							"}," + 
							"{" + 
							"\"text\":\" will zu dir. (30 Sekunden)\n\",\"color\":\"green\"" + 
							"}," + 
							"{" + 
							"\"text\":\"Annehmen: \",\"color\":\"green\"" + 
							"}," + 
							"{" + 
							"\"text\":\"/tpaccept\",\"color\":\"gray\"," + 
							"\"clickEvent\":" + 
							"{" + 
							"\"action\":\"run_command\",\"value\":\"/tpaccept\"" + 
							"}," + 
							"\"hoverEvent\":" + 
							"{" + 
							"\"action\":\"show_text\",\"value\":" + 
							"{" + 
							"\"text\":\"\",\"extra\":" + 
							"[" + 
							"{" + 
							"\"text\":\"/tpaccept\",\"color\":\"gray\"" + 
							"}" + 
							"]" + 
							"}" + 
							"}" + 
							"}," + 
							"{" + 
							"\"text\":\" - \",\"color\":\"green\"" + 
							"}," + 
							"{" + 
							"\"text\":\"Ablehnen: \",\"color\":\"red\"" + 
							"}," + 
							"{" + 
							"\"text\":\"/tpdeny\",\"color\":\"gray\"," + 
							"\"clickEvent\":" + 
							"{" + 
							"\"action\":\"run_command\",\"value\":\"/tpdeny\"" + 
							"}," + 
							"\"hoverEvent\":" + 
							"{" + 
							"\"action\":\"show_text\",\"value\":" + 
							"{" + 
							"\"text\":\"\",\"extra\":" + 
							"[" + 
							"{" + 
							"\"text\":\"/tpdeny\",\"color\":\"gray\"" + 
							"}" + 
							"]" + 
							"}" + 
							"}" + 
							"}" + 
							"]"
						);
						
						player.sendMessage(ChatColor.GREEN + "Du hast eine TP-Anfrage an " + Bukkit.getPlayer(arg[0]).getDisplayName() + ChatColor.GREEN + " gesendet.");
						return true;
					}
					
					player.sendMessage(ChatColor.RED + "Spieler " + Bukkit.getPlayer(arg[0]).getDisplayName() + ChatColor.RED + " hat keine Berechtigung.");
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Spieler " + ChatColor.GRAY + arg[0] + ChatColor.RED +" ist nicht online.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.tpa")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/tpa <Spieler>", "suggest_command", "/tpa ", "/tpa <Spieler>"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 0) {
			
			sender.sendMessage(ChatColor.RED + "Der Befehl /tpa ist in der Konsole nicht verfügbar.");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/tpa"));
		return true;
	}
}
