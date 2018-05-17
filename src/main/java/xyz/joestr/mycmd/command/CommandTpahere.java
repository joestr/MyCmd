package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandTpahere implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandTpahere(MyCmd myCmd) {
		
		this.plugin = myCmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String label, final String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.tpahere")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.tpahere");
				return true;
			}
			
			if(args.length == 1) {
				
				if(this.plugin.tpahere.containsKey(player.getName())) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst nur alle 60 Sekunden eine TP-Anfrage versenden.");
					return true;
				}
				
				if(player.getName().equals(args[0])) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst dir selbst keine TP-Anfrage schicken.");
					return true;
				}
				
				if(Bukkit.getOfflinePlayer(args[0]).isOnline()) {
					
					if((Bukkit.getPlayer(args[0]).hasPermission("mycmd.command.tpdeny")) && (Bukkit.getPlayer(args[0]).hasPermission("mycmd.command.tpaccept"))) {
						
						if(this.plugin.tpahereSwitched.containsKey(args[0])) {
							
							player.sendMessage(this.plugin.pluginPrefix + Bukkit.getServer().getPlayer(args[0]).getDisplayName() + ChatColor.RED + " hat bereits eine TP-Anfrage erhalten.");
							return true;
						}
						
						this.plugin.tpahere.put(player.getName(), Bukkit.getPlayer(args[0]).getName());
						this.plugin.tpahereSwitched.put(Bukkit.getPlayer(args[0]).getName(), player.getName());
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
							
							public void run() {
									
								CommandTpahere.this.plugin.tpahere.remove(player.getName());
								CommandTpahere.this.plugin.tpahereSwitched.remove(args[0]);
							}
						}, 600L);
							
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
							"tellraw " + args[0] + " " + 
							"[\"\"," + 
							"{" + 
							"\"text\":\"TP-Anfrage: \",\"color\":\"green\"" + 
							"}," + 
							"{" + 
							"\"text\":\"Willst du zu \",\"color\":\"green\"" + 
							"}," +
							"{" + 
							"\"text\":\"" + player.getDisplayName() + "\"" + 
							"}," +  
							"{" + 
							"\"text\":\"? (30 Sekunden)\n\",\"color\":\"green\"" + 
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
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast eine TP-Anfrage an " + Bukkit.getPlayer(args[0]).getDisplayName() + ChatColor.GREEN + " gesendet.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + Bukkit.getPlayer(args[0]).getDisplayName() + ChatColor.RED + " hat keine Berechtigung.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + args[0] + ChatColor.RED +" ist nicht online.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.tpahere")) {
				
				this.plugin.usageMessage(player, "/tpahere <Spieler>", "suggest_command", "/tpahere ", "/tpahere <Spieler>");
				return true;
			}
		}
		
		//Console
		if(args.length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Befehl /tpahere ist in der Konsole nicht verfügbar.");
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/tpahere");
		return true;
	}
}
