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
	public boolean onCommand(CommandSender sender, Command command, String string, final String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.tpahere")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.tpahere"));
				return true;
			}
			
			if(arg.length == 1) {
				
				if(this.plugin.tpahere.containsKey(player.getName())) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst nur alle 60 Sekunden eine TP-Anfrage versenden.");
					return true;
				}
				
				if(player.getName().equals(arg[0])) {
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du kannst dir selbst keine TP-Anfrage schicken.");
					return true;
				}
				
				if(Bukkit.getOfflinePlayer(arg[0]).isOnline()) {
					
					if((Bukkit.getPlayer(arg[0]).hasPermission("mycmd.command.tpdeny")) && (Bukkit.getPlayer(arg[0]).hasPermission("mycmd.command.tpaccept"))) {
						
						if(this.plugin.tpahereSwitched.containsKey(arg[0])) {
							
							player.sendMessage(this.plugin.pluginPrefix + Bukkit.getServer().getPlayer(arg[0]).getDisplayName() + ChatColor.RED + " hat bereits eine TP-Anfrage erhalten.");
							return true;
						}
						
						this.plugin.tpahere.put(player.getName(), Bukkit.getPlayer(arg[0]).getName());
						this.plugin.tpahereSwitched.put(Bukkit.getPlayer(arg[0]).getName(), player.getName());
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
							
							public void run() {
									
								CommandTpahere.this.plugin.tpahere.remove(player.getName());
								CommandTpahere.this.plugin.tpahereSwitched.remove(arg[0]);
							}
						}, 600L);
							
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
							"tellraw " + arg[0] + " " + 
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
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Du hast eine TP-Anfrage an " + Bukkit.getPlayer(arg[0]).getDisplayName() + ChatColor.GREEN + " gesendet.");
						return true;
					}
					
					player.sendMessage(this.plugin.pluginPrefix + Bukkit.getPlayer(arg[0]).getDisplayName() + ChatColor.RED + " hat keine Berechtigung.");
					return true;
				}
				
				player.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + arg[0] + ChatColor.RED +" ist nicht online.");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.tpahere")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/tpahere <Spieler>", "suggest_command", "/tpahere ", "/tpahere <Spieler>"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 0) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Befehl /tpahere ist in der Konsole nicht verfügbar.");
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/tpahere"));
		return true;
	}
}
