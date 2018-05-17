package xyz.joestr.mycmd.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandDelspawn implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandDelspawn(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//Player
		if(commandSender instanceof Player) {
			
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.delspawn")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(!player.hasPermission("mycmd.command.delspawn")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.delspawn");
					return true;
				}
				
				_delspawn_(commandSender);
				return true;
			}
			
			if(player.hasPermission("mycmd.command.spawn")){
				
				this.plugin.usageMessage(player, "/delspawn", "run_command", "/delspawn", "/delspawn");
				return true;
			}
		}
		//End Player
		
		//Console
		if(args.length == 0) {
			
			_delspawn_(commandSender);
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/delspawn");
		return true;
		//End Console
	}
	
	/**
	 * 
	 * @param sender Specifies the sender of the command.
	 */
	public void _delspawn_(CommandSender sender) {
		
		if(this.plugin.config.getMap().get("spawn") == null) {
			
			sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Der Spawn-Punkt wurde noch nicht gesetzt.");
			return;
		}
		
		this.plugin.config.Reset();
		this.plugin.config.Save();
		sender.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Der Spawn-Punkt wurde gelöscht.");
		return;
	}
}
