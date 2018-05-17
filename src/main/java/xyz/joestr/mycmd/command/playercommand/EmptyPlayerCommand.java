package xyz.joestr.mycmd.command.playercommand;

import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class EmptyPlayerCommand {

	MyCmd plugin = null;
	
	public EmptyPlayerCommand(MyCmd myCmd) {
		
		this.plugin = myCmd;
	}
	
	public boolean process(Player player, String[] args) {
		
		if(!player.hasPermission("<PERMISSION>")) {
			
			this.plugin.noPermissionMessage(player, "<PERMISSION>");
			return true;
		}
		
		if(args.length >= 1) {
			
			if(!player.hasPermission("<PERMISSION>")) {
				
				this.plugin.noPermissionMessage(player, "<PERMISSION>");
				return true;
			}
			
			// Prozedere hier
			
			return true;
		}
		
		if(player.hasPermission("<PERMISSION>")) {
			
			this.plugin.usageMessage(player, "/<COMMAND> <MESSAGE ...>", "suggest_command", "/<COMMAND> ", "/<COMMAND> <MESSAGE ...>");
			return true;
		}
		
		return true;
	}
}
