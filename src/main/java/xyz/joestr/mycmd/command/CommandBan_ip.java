package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.BanList.Type;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;
import xyz.joestr.mycmd.command.blockcommand.BlockCommandBan_ip;
import xyz.joestr.mycmd.command.consolecommand.ConsoleCommandBan_ip;
import xyz.joestr.mycmd.command.playercommand.PlayerCommandBan_ip;
import xyz.joestr.mycmd.command.proxiedcommand.ProxiedCommandBan_ip;
import xyz.joestr.mycmd.command.remoteconsolecommand.RemoteConsoleCommandBan_ip;

public class CommandBan_ip implements CommandExecutor {
	
	MyCmd plugin;
	
	PlayerCommandBan_ip playerCommandBan_ip = null;
	BlockCommandBan_ip blockCommandBan_ip = null;
	ConsoleCommandBan_ip consoleCommandBan_ip = null;
	ProxiedCommandBan_ip proxiedCommandBan_ip = null;
	RemoteConsoleCommandBan_ip remoteConsoleCommandBan_ip = null;
	
	public CommandBan_ip(MyCmd mycmd) {
		
		this.plugin = mycmd;
		
		playerCommandBan_ip = new PlayerCommandBan_ip(this.plugin);
		blockCommandBan_ip = new BlockCommandBan_ip(this.plugin);
		consoleCommandBan_ip = new ConsoleCommandBan_ip(this.plugin);
		proxiedCommandBan_ip = new ProxiedCommandBan_ip(this.plugin);
		remoteConsoleCommandBan_ip = new RemoteConsoleCommandBan_ip(this.plugin);
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		// From a player
		if(commandSender instanceof Player) {
			
			// Cast command sender to a player
			Player player = (Player) commandSender;
			
			return this.playerCommandBan_ip.process(player, args);
		}
		
		// From a block
		if(commandSender instanceof BlockCommandSender) {
			
			// Cast command sender to a block command sender
			BlockCommandSender blockCommandSender = (BlockCommandSender) commandSender;
			
			return this.blockCommandBan_ip.process(blockCommandSender, args);
		}
		
		// From a console
		if(commandSender instanceof ConsoleCommandSender) {
			
			// Cast command sender to a console command sender
			ConsoleCommandSender consoleCommandSender = (ConsoleCommandSender) commandSender;

			return this.consoleCommandBan_ip.process(consoleCommandSender, args);
		}
		
		// Proxied command
		if(commandSender instanceof ProxiedCommandSender) {
			
			// Cast command sender to a proxied command sender
			ProxiedCommandSender proxiedCommandSender = (ProxiedCommandSender) commandSender;
			
			return this.proxiedCommandBan_ip.process(proxiedCommandSender, args);			
		}
		
		// Remote console
		if(commandSender instanceof RemoteConsoleCommandSender) {
			
			// Cast command sender to a remote console command sender
			RemoteConsoleCommandSender remoteConsoleCommandSender = (RemoteConsoleCommandSender) commandSender;
			
			return this.remoteConsoleCommandBan_ip.process(remoteConsoleCommandSender, args);
		}
		
		// Hier sollte man eigentlich nicht hinkommen.
		return true;
	}
	
	// Für später (mit Zeitabfrage
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
		
		Bukkit.getServer().broadcastMessage(
				this.plugin.toColorcode("&", ((String)this.plugin.config.getMap().get("ban-ip-temp")))
				.replace("%ip%", arg[0])
				.replace("%reason%", message)
		);
		
		Bukkit.getServer().getBanList(Type.IP).addBan(arg[0], message, date, "MyCmd");
	}
}
