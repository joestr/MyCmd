package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandBroadcast implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandBroadcast(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.broadcast")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.broadcast");
				return true;
			}
			
			if(args.length > 0) {
				
				if(!player.hasPermission("mycmd.command.tell")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.broadcast");
					return true;
				}
				
				String msg = "";
				
				for (int i = 0; i < args.length; i++) {
					
					msg = msg + args[i] + " ";
				}
				
				Bukkit.getServer().broadcastMessage(
						this.plugin.toColorcode(
								"&",
								this.plugin.config.getMap().get("broadcast").toString() + msg
						)
				);
				
				return true;
			}
			
			if (player.hasPermission("mycmd.command.broadcast")) {
				
				this.plugin.usageMessage(player, "/<broadcast|bc> <Nachricht ...>", "suggest_command", "/bc ", "/<broadcast|bc> <Nachricht ...>");
				return true;
			}
		}
		
		//Console
		if(args.length > 0) {
			
			String msg = "";
			
			for (int i = 0; i < args.length; i++) {
				
				msg = msg + args[i] + " ";
			}
			
			Bukkit.getServer().broadcastMessage(
					this.plugin.toColorcode(
							"&",
							this.plugin.config.getMap().get("broadcast").toString() + msg
					)
			);
			
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/<broadcast|bc> <Nachricht ...>");
		return true;
	}
}
