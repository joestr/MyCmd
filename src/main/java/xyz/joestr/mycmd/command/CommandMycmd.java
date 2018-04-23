package xyz.joestr.mycmd.command;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;
import xyz.joestr.mycmd.delegates.YMLDelegate;

public class CommandMycmd implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandMycmd(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.mycmd")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.mycmd")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.mycmd"));
					return true;
				}
				
				_mycmd_(sender);
				return true;
			}
			
			if (player.hasPermission("mycmd.command.mycmd")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/mycmd", "run_command", "/mycmd", "/mycmd"));
			}
			return true;
		}
		//End Player
		
		//Console
		if(arg.length == 0) {
			
			_mycmd_(sender);
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/mycmd"));
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
			
			sender.sendMessage(ChatColor.RED + "Fehler in der Datei " + ChatColor.GRAY + this.plugin.config.getFileName() + ChatColor.RED + " von" + ChatColor.GRAY + " MyCmd " + ChatColor.RED + ". MyCmd deaktiviert.");
			Bukkit.getLogger().log(Level.WARNING, "Error in the " + this.plugin.config.getFileName() + " file of MyCmd. MyCmd deactivated.");
			Bukkit.getPluginManager().disablePlugin(this.plugin);
			return;
		}
		
		sender.sendMessage(ChatColor.GREEN + "Die Dateien von " + ChatColor.GRAY + "MyCmd" + ChatColor.GREEN + " wurden neu geladen.");
		return;
	}
}
