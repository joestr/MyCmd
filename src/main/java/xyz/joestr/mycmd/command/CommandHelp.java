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
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.help")) {
				
				player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.help"));
				return true;
			}
			
			if(arg.length == 0) {
				
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
			
			if(arg.length == 1) {
				
				ArrayList<String> al = new ArrayList<String>();
				String[] texts = new String[7]; String[] actions = new String[7]; String[] commands = new String[7]; String[] hovers = new String[7];
				
				for(Command com : this.plugin.commandList) { if(com.getPermission() != null) { if(player.hasPermission(com.getPermission())) { al.add(com.getName()); } } else { al.add(com.getName()); } }
				
				int page = 0;
				
				try { page = Integer.parseInt(arg[0]); } catch(Exception exception) {
					
					this.plugin.usageMessage(player, "/help [<Seite>]", "suggest_command", "/help ", "/help [<Seite>]"); return true;
				}
				
				if(page < 1 || page > Math.ceil(al.size() / 7)) { player.sendMessage("Verfügbare Seiten: 1 bis " + Math.ceil(al.size() / 7)); }
				
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
			}
			return true;
		}
		
		//Console
		if(arg.length == 0) {
			
			sender.sendMessage(ChatColor.GRAY + "--- " + ChatColor.GREEN + "Hilfe" + ChatColor.GRAY + " --- " + ChatColor.GREEN + "(Seite " + ChatColor.GRAY + "1" + ChatColor.GREEN + " von " + ChatColor.GRAY + Math.ceil(this.plugin.commandList.size() / 7) + ChatColor.GREEN + ")");
			
			for(int i = 0; i < 7; i++) {
				
				if(this.plugin.commandList.isEmpty() || i >= this.plugin.commandList.size()) { break; }
				sender.sendMessage(ChatColor.GRAY + this.plugin.commandList.get(i).getName() + " (?)");
			}
			
			return true;
		}
		
		if(arg.length == 1) {
			
			int page = 0;
			
			try { page = Integer.parseInt(arg[0]); } catch(Exception exception) {
				
				sender.sendMessage(this.plugin.usageMessage("/help [<Seite>]")); return true;
			}
			
			if(page < 1 || page > Math.ceil(this.plugin.commandList.size() / 7)) { sender.sendMessage("Verfügbare Seiten: 1 bis " + Math.ceil(this.plugin.commandList.size() / 7)); return true; }
			
			sender.sendMessage(ChatColor.GRAY + "--- " + ChatColor.GREEN + "Hilfe" + ChatColor.GRAY + " --- " + ChatColor.GREEN + "(Seite " + ChatColor.GRAY + page + ChatColor.GREEN + " von " + ChatColor.GRAY + Math.ceil(this.plugin.commandList.size() / 7) + ChatColor.GREEN + ")");
			
			for(int i = ((page - 1) * 7); i < (page * 7) ; i++) {
				
				if(this.plugin.commandList.isEmpty() || i >= this.plugin.commandList.size()) { break; }
				sender.sendMessage(ChatColor.GRAY + this.plugin.commandList.get(i).getName() + " (?)");
			}
			
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/help [<Seite>]"));
		return true;
	}

}
