package xyz.joestr.mycmd.command.noplayercommand;

import org.bukkit.command.CommandSender;

import xyz.joestr.mycmd.MyCmd;

public class EmptyNoPlayerCommand {
	
	MyCmd plugin = null;
	
	public EmptyNoPlayerCommand(MyCmd myCmd) {
		
		this.plugin = myCmd;
	}
	
	public boolean process(CommandSender commandSender, String[] args) {
		
		if(args.length >= 1) {
			
			// Prozedere
			
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/<COMMAND> <MESSAGE>");
		return true;
	}
}
