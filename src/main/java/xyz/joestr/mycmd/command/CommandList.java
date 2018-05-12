package xyz.joestr.mycmd.command;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandList implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandList(MyCmd mycmd) { this.plugin = mycmd; }
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.list")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if (!player.hasPermission("mycmd.command.list")) {
					
					player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage("mycmd.command.list"));
					return true;
				}
				
				_list_(sender);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.list")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/list", "run_command", "/list", "/list"));
				return true;
			}
		}
		
		if(arg.length == 0) {
			
			_list_(sender);
			return true;
		}
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/list"));
		return true;
	}
	
	public void _list_(CommandSender sender) {
		
		String str = "";
		Collection<? extends Player> a = Bukkit.getOnlinePlayers();
		int i = 0;
		
		for (Player p : a) {
			
			i++;
			if(i < a.toArray().length) {
				
				str = str + p.getDisplayName() + ChatColor.GREEN + ", ";
			} else {
				
				str = str + p.getDisplayName();
			}
		}
		
		if(a.toArray().length == 0) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es sind zurzeit " + ChatColor.GRAY + "keine" + ChatColor.GREEN + " Spieler online.");
		}
		
		if(a.toArray().length == 1) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es ist zurzeit " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler online:");
			sender.sendMessage(this.plugin.pluginPrefix + str);
		}
		
		if(a.toArray().length >= 2) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Es sind zurzeit " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler online:");
			sender.sendMessage(this.plugin.pluginPrefix + str);
		}
	}
}
