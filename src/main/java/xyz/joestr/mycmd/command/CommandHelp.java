package xyz.joestr.mycmd.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import xyz.joestr.mycmd.MyCmd;

public class CommandHelp implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandHelp(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.help")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.help");
				return true;
			}
			
			if(args.length == 0) {
				
				ArrayList<String> al = new ArrayList<String>();
				String[] texts = new String[7]; String[] actions = new String[7]; String[] commands = new String[7]; String[] hovers = new String[7];
				
				for(Command com : this.plugin.commandList) { if(com.getPermission() != null) { if(player.hasPermission(com.getPermission())) { al.add(com.getName()); } } else { al.add(com.getName()); } }
				
				for(int i = 0; i < 7; i++) {
					
					if(al.isEmpty() || i >= al.size()) { break; }
					texts[i] = "/" + al.get(i) + " (?)"; actions[i] = "suggest_command"; commands[i] = "/" + al.get(i) + " "; hovers[i] = "/" + al.get(i) + " (?)";
				}
				
				this.plugin.commandOverview(player,
					ChatColor.GRAY + "--- " + ChatColor.GREEN + "Hilfe" + ChatColor.GRAY + " --- " + ChatColor.GREEN + "(Seite " + ChatColor.GRAY + "1" + ChatColor.GREEN + " von " + ChatColor.GRAY + Math.ceil(al.size() / 7) + ChatColor.GREEN + ")",
					texts, actions, commands, hovers
				);
			}
			
			if(args.length == 1) {
				
				ArrayList<String> al = new ArrayList<String>();
				String[] texts = new String[7]; String[] actions = new String[7]; String[] commands = new String[7]; String[] hovers = new String[7];
				
				for(Command com : this.plugin.commandList) { if(com.getPermission() != null) { if(player.hasPermission(com.getPermission())) { al.add(com.getName()); } } else { al.add(com.getName()); } }
				
				int page = 0;
				
				try { page = Integer.parseInt(args[0]); } catch(Exception exception) {
					
					this.plugin.usageMessage(player, "/help [<Seite>]", "suggest_command", "/help ", "/help [<Seite>]"); return true;
				}
				
				if(page < 1 || page > Math.ceil(al.size() / 7)) { player.sendMessage(this.plugin.pluginPrefix + "Verfügbare Seiten: 1 bis " + Math.ceil(al.size() / 7)); }
				
				for(int i = ((page - 1) * 7); i < (page * 7) ; i++) {
					
					if(al.isEmpty() || i >= al.size()) { break; }
					texts[i - ((page - 1) * 7)] = "/" + al.get(i) + " (?)"; actions[i - ((page - 1) * 7)] = "suggest_command"; commands[i - ((page - 1) * 7)] = "/" + al.get(i) + " "; hovers[i - ((page - 1) * 7)] = "/" + al.get(i) + " (?)";
				}
				
				this.plugin.commandOverview(player,
					ChatColor.GRAY + "--- " + ChatColor.GREEN + "Hilfe" + ChatColor.GRAY + " --- " + ChatColor.GREEN + "(Seite " + ChatColor.GRAY + page + ChatColor.GREEN + " von " + ChatColor.GRAY + Math.ceil(al.size() / 7) + ChatColor.GREEN + ")",
					texts, actions, commands, hovers
				);
				
				return true;
			}
			
			if(player.hasPermission("mycmd.command.help")) {
				
				this.plugin.usageMessage(player, "/help [<Seite>]", "suggest_command", "/help ", "/help [<Seite>]");
				return true;
			}
		}
		
		//Console
		if(args.length == 0) {
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "--- " + ChatColor.GREEN + "Hilfe" + ChatColor.GRAY + " --- " + ChatColor.GREEN + "(Seite " + ChatColor.GRAY + "1" + ChatColor.GREEN + " von " + ChatColor.GRAY + Math.ceil(this.plugin.commandList.size() / 7) + ChatColor.GREEN + ")");
			
			for(int i = 0; i < 7; i++) {
				
				if(this.plugin.commandList.isEmpty() || i >= this.plugin.commandList.size()) { break; }
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + this.plugin.commandList.get(i).getName() + " (?)");
			}
			
			return true;
		}
		
		if(args.length == 1) {
			
			int page = 0;
			
			try { page = Integer.parseInt(args[0]); } catch(Exception exception) {
				
				this.plugin.usageMessage(commandSender, "/help [<Seite>]"); return true;
			}
			
			if(page < 1 || page > Math.ceil(this.plugin.commandList.size() / 7)) { commandSender.sendMessage(this.plugin.pluginPrefix + "Verfügbare Seiten: 1 bis " + Math.ceil(this.plugin.commandList.size() / 7)); return true; }
			
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + "--- " + ChatColor.GREEN + "Hilfe" + ChatColor.GRAY + " --- " + ChatColor.GREEN + "(Seite " + ChatColor.GRAY + page + ChatColor.GREEN + " von " + ChatColor.GRAY + Math.ceil(this.plugin.commandList.size() / 7) + ChatColor.GREEN + ")");
			
			for(int i = ((page - 1) * 7); i < (page * 7) ; i++) {
				
				if(this.plugin.commandList.isEmpty() || i >= this.plugin.commandList.size()) { break; }
				commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.GRAY + this.plugin.commandList.get(i).getName() + " (?)");
			}
			
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/help [<Seite>]");
		return true;
	}

}
