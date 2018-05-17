package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandBan implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandBan(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.ban")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.ban");
				return true;
			}
			
			if(args.length >= 2) {
				
				if(!player.hasPermission("mycmd.command.ban")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.ban");
					return true;
				}
				
				String message = "";
				
				for(int i = 1; i < args.length; i++) { 
					
					if(i + 1 == args.length) { message += args[i]; continue; }
					message += args[i] + " ";
				}
				
				Bukkit.getServer().getBanList(Type.NAME).addBan(args[0], message, null, player.getName());
				
				BanEntry banEntry = Bukkit.getServer().getBanList(Type.NAME).getBanEntry(args[0]);
				
				String kickMessage = 
						this.plugin.config.getMap().get("ban-screen").toString()
						.replace("%type%", Type.NAME.toString())
						.replace("%target%", banEntry.getTarget())
						.replace("%reason%", banEntry.getReason())
						.replace("%source%", banEntry.getSource())
						.replace("%created%", banEntry.getCreated().toInstant().toString())
						.replace("%expires%", banEntry.getExpiration() == null ? "-" : banEntry.getExpiration().toInstant().toString());
				
				if(Bukkit.getServer().getOfflinePlayer(args[0]).isOnline()) {
					Bukkit.getServer().getPlayer(args[0]).kickPlayer(
							this.plugin.toColorcode("&", kickMessage)
					);
				}
				
				Bukkit.getServer().broadcastMessage(
						this.plugin.toColorcode(
								"&",
								this.plugin.config.getMap().get("ban").toString()
								.replace("%player%", args[0])
								.replace("%reason%", message)
						)
				);
				
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ban")) {
				
				this.plugin.usageMessage(player, "/ban <Spieler> <Grund ...>", "suggest_command", "/ban ", "/ban <Spieler> <Grund ...>");
				return true;
			}
		}
		
		//Console
		if(args.length >= 2) {
			
			String message = "";
			
			for(int i = 1; i < args.length; i++) { 
				
				if(i + 1 == args.length) { message += args[i]; continue; }
				message += args[i] + " ";
			}
			
			Bukkit.getServer().getBanList(Type.NAME).addBan(args[0], message, null, "KONSOLE/BLOCK/PROXY/REMOTECONSOLE");
			BanEntry banEntry = Bukkit.getServer().getBanList(Type.NAME).getBanEntry(args[0]);
			
			String kickMessage = 
					this.plugin.config.getMap().get("ban-screen").toString()
					.replace("%type%", Type.NAME.toString())
					.replace("%target%", banEntry.getTarget())
					.replace("%reason%", banEntry.getReason())
					.replace("%source%", banEntry.getSource())
					.replace("%created%", banEntry.getCreated().toInstant().toString())
					.replace("%expires%", banEntry.getExpiration() == null ? "-" : banEntry.getExpiration().toInstant().toString());
			
			if(Bukkit.getServer().getOfflinePlayer(args[0]).isOnline()) {
				Bukkit.getServer().getPlayer(args[0]).kickPlayer(
						this.plugin.toColorcode("&", kickMessage)
				);
			}
			
			Bukkit.getServer().broadcastMessage(
					this.plugin.toColorcode(
							"&",
							this.plugin.config.getMap().get("ban").toString()
							.replace("%player%", args[0])
							.replace("%reason%", message)
					)
			);
			
			return true;
		}
		
		this.plugin.usageMessage(commandSender, "/ban <Spieler> <Grund ...>");
		return true;
	}
	
	// Mit Zeitabfrage (für später)
	public void _ban_(CommandSender sender, String[] arg) {
		
		String message = "";
		
		for(int i = 1; i < arg.length; i++) { 
			
			if(i + 1 == arg.length) { message += arg[i]; continue; }
			message += arg[i] + " ";
		}
		
		Pattern pattern = Pattern.compile("(([0-9]*)d)*(([0-9]|0[0-9]|1[0-9]|2[0-3])h)*(([0-5][0-9])m)*([0-5][0-9])s");
		if(!arg[2].matches(pattern.toString())) { sender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Für Zeit muss folgender String angegeben werden: [<Int>d<Int>h<Int>m]<Int>s"); return; }
		
		ArrayList<Integer> al = new ArrayList<Integer>();
		Pattern pattern2 = Pattern.compile("-?\\d+");
		Matcher matcher = pattern2.matcher(arg[2]);
		while(matcher.find()) { al.add(Integer.parseInt(matcher.group())); }
		
		long tdiff = 0;
		ArrayList<Integer> tdiffal = new ArrayList<Integer>();
		tdiffal.add(1000);tdiffal.add(60000);tdiffal.add(3600000);tdiffal.add(86400000);
		
		int i = 0;int j = 0;
		for(j = al.size(); j >= 0; j--) { tdiff += al.get(j) * tdiffal.get(i); i++; }
		
		Date date = new Date();
		date.setTime(date.getTime() + tdiff);
		
		Bukkit.getServer().broadcastMessage(
				this.plugin.toColorcode("&", ((String)this.plugin.config.getMap().get("ban-temp")))
				.replace("%player%", Bukkit.getServer().getPlayer(arg[0]).getName())
				.replace("%reason%", message)
		);
		
		Bukkit.getServer().getBanList(Type.NAME).addBan(arg[0], message, date, "MyCmd");
	}
}
