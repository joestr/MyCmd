package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class CommandBan_ip implements CommandExecutor {
	
	MyCmd plugin;
	
	public CommandBan_ip(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.ban-ip")) {
				
				this.plugin.noPermissionMessage(player, "mycmd.command.ban-ip");
				return true;
			}
			
			if(arg.length >= 2) {
				
				if(!player.hasPermission("mycmd.command.ban-ip")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.ban-ip");
					return true;
				}
				
				Pattern p = Pattern.compile("(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))");
				if(!arg[0].matches(p.toString())) { sender.sendMessage(ChatColor.RED + "Es muss eine gültige IP-Adresse angegeben werden."); return true; }
				
				String message = "";
				
				for(int i = 1; i < arg.length; i++) { 
					
					if(i + 1 == arg.length) { message += arg[i]; continue; }
					message += arg[i] + " ";
				}
				
				Bukkit.getServer().getBanList(Type.IP).addBan(arg[0], message, null, player.getName());
				for(Player pl : Bukkit.getOnlinePlayers()) { if(pl.getAddress().toString().contains(arg[0])) { pl.kickPlayer(message); } }
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "IP " + ChatColor.GRAY + arg[0] + ChatColor.YELLOW +" wurde gebannt. (" + ChatColor.GRAY + message + ChatColor.YELLOW + ")");
				return true;
			}
			
			if(player.hasPermission("mycmd.command.ban-ip")) {
				
				this.plugin.usageMessage(player, "/ban-ip <Adresse> <Grund ...>", "suggest_command", "/ban-ip ", "/ban-ip <Adresse> <Grund ...>");
				return true;
			}
		}
		
		//Console
		if(arg.length >= 2) {
			
			Pattern p = Pattern.compile("(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))");
			if(!arg[0].matches(p.toString())) { sender.sendMessage(ChatColor.RED + "Es muss eine gültige IP-Adresse angegeben werden."); return true; }
			
			String message = "";
			
			for(int i = 1; i < arg.length; i++) { 
				
				if(i + 1 == arg.length) { message += arg[i]; continue; }
				message += arg[i] + " ";
			}
			
			Bukkit.getServer().getBanList(Type.IP).addBan(arg[0], message, null, "KONSOLE");
			for(Player pl : Bukkit.getOnlinePlayers()) { if(pl.getAddress().toString().contains(arg[0])) { pl.kickPlayer(message); } }
			Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "IP " + ChatColor.GRAY + arg[0] + ChatColor.YELLOW +" wurde gebannt. (" + ChatColor.GRAY + message + ChatColor.YELLOW + ")");
			return true;
		}
		
		sender.sendMessage(this.plugin.usageMessage("/ban-ip <Adresse> <Grund ...>"));
		return true;
	}
	
	public void _ban_ip_(CommandSender sender, String[] arg) {
		
		Pattern p = Pattern.compile("(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))");
		if(!arg[0].matches(p.toString())) { sender.sendMessage(ChatColor.RED + "Es muss eine gültige IP-Adresse angegeben werden."); return; }
		
		String message = "";
		
		for(int i = 1; i < arg.length; i++) { 
			
			if(i + 1 == arg.length) { message += arg[i]; continue; }
			message += arg[i] + " ";
		}
		
		Pattern pattern = Pattern.compile("(([0-9]*)d)*(([0-9]|0[0-9]|1[0-9]|2[0-3])h)*(([0-5][0-9])m)*([0-5][0-9])s");
		if(!arg[2].matches(pattern.toString())) { sender.sendMessage(ChatColor.RED + "Für Zeit muss folgender String angegeben werden: [<Int>d<Int>h<Int>m]<Int>s"); return; }
		
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
		
		Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + arg[0] + " wurde temporär vom Server gebannt. (" + message + ")");
		Bukkit.getServer().getBanList(Type.IP).addBan(arg[0], message, date, "MyCmd");
	}
}
