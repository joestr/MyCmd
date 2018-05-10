package xyz.joestr.mycmd.command.playercommand;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.BanList.Type;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;

public class PlayerCommandBan_ip {

	MyCmd plugin = null;
	
	public PlayerCommandBan_ip(MyCmd myCmd) {
		
		this.plugin = myCmd;
	}
	
	public void process(Player player, String[] args) {
		Pattern p = Pattern.compile("(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))");
		if(!args[0].matches(p.toString())) {
			player.sendMessage(ChatColor.RED + "Es muss eine g�ltige IP-Adresse angegeben werden."); 
			return;
		}
		
		String message = "";
		
		for(int i = 1; i < args.length; i++) { 
			
			if(i + 1 == args.length) { message += args[i]; continue; }
			message += args[i] + " ";
		}
		
		Bukkit.getServer().broadcastMessage(
				this.plugin.toColorcode("&", ((String)this.plugin.config.getMap().get("ban-ip")))
				.replace("%ip%", args[0])
				.replace("%reason%", message)
		);
		
		Bukkit.getServer().getBanList(Type.IP).addBan(args[0], message, null, player.getName());
		for(Player pl : Bukkit.getOnlinePlayers()) { if(pl.getAddress().toString().contains(args[0])) { pl.kickPlayer(message); } }
		
		return;
	}
}
