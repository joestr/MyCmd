package xyz.joestr.mycmd.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.joestr.mycmd.MyCmd;

public class CommandMap implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandMap(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		//Player
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.map")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(!player.hasPermission("mycmd.command.map")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.map"));
					return true;
				}
				
				@SuppressWarnings("unchecked")
				List<String> mw = (List<String>)this.plugin.config.getMap().get("map-worlds");
				
				if(mw.contains(player.getWorld().getName())) {
					
					//http://zeus.gstd.eu/?worldname=world&mapname=flat&zoom=3&x=469&y=64&z=-12173
					player.sendMessage(ChatColor.GREEN + "Map:");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
						"tellraw " + player.getName() + " " + 
						"[\"\"," + 
						"{" + 
						"\"text\":\">> Link\",\"color\":\"gray\"," + 
						"\"clickEvent\":" + 
						"{" + 
						"\"action\":\"open_url\",\"value\":\"" + (String)this.plugin.config.getMap().get("map") + "?worldname=" + player.getWorld().getName() + "&mapname=" + (String)this.plugin.config.getMap().get("map-name") + "&zoom=" + this.plugin.config.getMap().get("map-zoom").toString() + "&x=" + player.getLocation().getBlockX() + "&y=" + this.plugin.config.getMap().get("map-y").toString() + "&z=" + player.getLocation().getBlockZ() + "\"" + 
						"}," + 
						"\"hoverEvent\":" + 
						"{" + 
						"\"action\":\"show_text\",\"value\":" + 
						"{" + 
						"\"text\":\"\",\"extra\":" + 
						"[" + 
						"{" + 
						"\"text\":\"" + (String)this.plugin.config.getMap().get("map") + "?worldname=" + player.getWorld().getName() + "&mapname=" + (String)this.plugin.config.getMap().get("map-name") + "&zoom=" + this.plugin.config.getMap().get("map-zoom").toString() + "&x=" + player.getLocation().getBlockX() + "&y=" + this.plugin.config.getMap().get("map-y").toString() + "&z=" + player.getLocation().getBlockZ() + "\",\"color\":\"gray\"" + 
						"}" + 
						"]" + 
						"}" + 
						"}" + 
						"}" + 
						"]"
					);
					return true;
				}
				
				player.sendMessage(ChatColor.RED + "Für diese Welt gibt es keine Karte.");
				return true;
			}
			
			if(player.hasPermission("mycmd.comammd.map")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/map", "run_command", "/map", "/map"));
				return true;
			}
		}
		//End Player
		
		//Console
		if(arg.length == 0) {
			
			sender.sendMessage(ChatColor.GREEN + "Map: " + ChatColor.GRAY + (String)this.plugin.config.getMap().get("map"));
		}
		
		sender.sendMessage(this.plugin.usageMessage("/map"));
		return true;
		//End Console
	}
}
