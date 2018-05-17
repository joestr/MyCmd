package xyz.joestr.mycmd.command;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;
import xyz.joestr.mycmd.util.YMLDelegate;

public class CommandMycmd implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandMycmd(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//Player
		if(commandSender instanceof Player) {
			
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.mycmd")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.mycmd")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.mycmd");
					return true;
				}
				
				_mycmd_(commandSender);
				return true;
			}
			
			if (player.hasPermission("mycmd.command.mycmd")) {
				
				this.plugin.usageMessage(player, "/mycmd", "run_command", "/mycmd", "/mycmd");
			}
			return true;
		}
		//End Player
		
		//Console
		if(args.length == 0) {
			
			_mycmd_(commandSender);
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/mycmd");
		return true;
		//End Console
	}
	
	public void _mycmd_(CommandSender sender) {
		
		for(YMLDelegate delegate : this.plugin.delegates) {
			
			if(!delegate.Exist()) {
				
				delegate.Create();
			}
			
			delegate.Load();
		}
		
		if(!this.plugin.config.Check()) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Fehler in der Datei " + ChatColor.GRAY + this.plugin.config.getFileName() + ChatColor.RED + " von" + ChatColor.GRAY + " MyCmd " + ChatColor.RED + ". MyCmd deaktiviert.");
			Bukkit.getLogger().log(Level.WARNING, "Error in the " + this.plugin.config.getFileName() + " file of MyCmd. MyCmd deactivated.");
			Bukkit.getPluginManager().disablePlugin(this.plugin);
			return;
		}
		
		this.plugin.pluginPrefix = this.plugin.toColorcode("&", (String) this.plugin.config.getMap().get("plugin-prefix"));
		
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Die Dateien von " + ChatColor.GRAY + "MyCmd" + ChatColor.GREEN + " wurden neu geladen.");
		return;
	}
}
