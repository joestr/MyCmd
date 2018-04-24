package xyz.joestr.mycmd;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.joestr.mycmd.command.CommandBan;
import xyz.joestr.mycmd.command.CommandBan_ip;
import xyz.joestr.mycmd.command.CommandBanlist;
import xyz.joestr.mycmd.command.CommandDelhome;
import xyz.joestr.mycmd.command.CommandDelhome2;
import xyz.joestr.mycmd.command.CommandDelspawn;
import xyz.joestr.mycmd.command.CommandDelwarp;
//import xyz.joestr.mycmd.command.CommandHelp;
import xyz.joestr.mycmd.command.CommandHome;
import xyz.joestr.mycmd.command.CommandHome2;
import xyz.joestr.mycmd.command.CommandKick;
import xyz.joestr.mycmd.command.CommandList;
import xyz.joestr.mycmd.command.CommandMap;
import xyz.joestr.mycmd.command.CommandMotd;
import xyz.joestr.mycmd.command.CommandRam;
import xyz.joestr.mycmd.command.CommandMycmd;
import xyz.joestr.mycmd.command.CommandNavi;
import xyz.joestr.mycmd.command.CommandPardon;
import xyz.joestr.mycmd.command.CommandPardon_ip;
import xyz.joestr.mycmd.command.CommandPing;
import xyz.joestr.mycmd.command.CommandPvp;
import xyz.joestr.mycmd.command.CommandRank;
import xyz.joestr.mycmd.command.CommandReply;
import xyz.joestr.mycmd.command.CommandSeen;
import xyz.joestr.mycmd.command.CommandSethome;
import xyz.joestr.mycmd.command.CommandSethome2;
import xyz.joestr.mycmd.command.CommandSetspawn;
import xyz.joestr.mycmd.command.CommandSetwarp;
import xyz.joestr.mycmd.command.CommandSpawn;
import xyz.joestr.mycmd.command.CommandTell;
import xyz.joestr.mycmd.command.CommandTpa;
import xyz.joestr.mycmd.command.CommandTpaccept;
import xyz.joestr.mycmd.command.CommandTpahere;
import xyz.joestr.mycmd.command.CommandTpdeny;
import xyz.joestr.mycmd.command.CommandWarp;
import xyz.joestr.mycmd.command.CommandWarps;
import xyz.joestr.mycmd.command.CommandWhitelist;
import xyz.joestr.mycmd.command.CommandWiki;
import xyz.joestr.mycmd.command.CommandWorldspawn;
import xyz.joestr.mycmd.delegates.YMLDelegate;
import xyz.joestr.mycmd.event.EventAsyncChat;
import xyz.joestr.mycmd.event.EventEntityDamageByEntity;
import xyz.joestr.mycmd.event.EventJoin;
import xyz.joestr.mycmd.event.EventKick;
import xyz.joestr.mycmd.event.EventListPing;
import xyz.joestr.mycmd.event.EventLogin;
import xyz.joestr.mycmd.event.EventQuit;
import xyz.joestr.mycmd.event.EventTeleport;
import xyz.joestr.mycmd.tabcomplete.TabCompleteBan;
import xyz.joestr.mycmd.tabcomplete.TabCompleteBan_ip;
import xyz.joestr.mycmd.tabcomplete.TabCompleteBanlist;
import xyz.joestr.mycmd.tabcomplete.TabCompleteDelhome;
import xyz.joestr.mycmd.tabcomplete.TabCompleteDelhome2;
import xyz.joestr.mycmd.tabcomplete.TabCompleteDelspawn;
import xyz.joestr.mycmd.tabcomplete.TabCompleteDelwarp;
//import xyz.joestr.mycmd.tabcomplete.TabCompleteHelp;
import xyz.joestr.mycmd.tabcomplete.TabCompleteHome;
import xyz.joestr.mycmd.tabcomplete.TabCompleteHome2;
import xyz.joestr.mycmd.tabcomplete.TabCompleteKick;
import xyz.joestr.mycmd.tabcomplete.TabCompleteList;
import xyz.joestr.mycmd.tabcomplete.TabCompleteMap;
import xyz.joestr.mycmd.tabcomplete.TabCompleteMotd;
import xyz.joestr.mycmd.tabcomplete.TabCompleteRam;
import xyz.joestr.mycmd.tabcomplete.TabCompleteMycmd;
import xyz.joestr.mycmd.tabcomplete.TabCompleteNavi;
import xyz.joestr.mycmd.tabcomplete.TabCompletePardon;
import xyz.joestr.mycmd.tabcomplete.TabCompletePardon_ip;
import xyz.joestr.mycmd.tabcomplete.TabCompletePing;
import xyz.joestr.mycmd.tabcomplete.TabCompletePvp;
import xyz.joestr.mycmd.tabcomplete.TabCompleteRank;
import xyz.joestr.mycmd.tabcomplete.TabCompleteReply;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSeen;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSethome;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSethome2;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSetspawn;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSetwarp;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSpawn;
import xyz.joestr.mycmd.tabcomplete.TabCompleteTell;
import xyz.joestr.mycmd.tabcomplete.TabCompleteTpa;
import xyz.joestr.mycmd.tabcomplete.TabCompleteTpaccept;
import xyz.joestr.mycmd.tabcomplete.TabCompleteTpahere;
import xyz.joestr.mycmd.tabcomplete.TabCompleteTpdeny;
import xyz.joestr.mycmd.tabcomplete.TabCompleteWarp;
import xyz.joestr.mycmd.tabcomplete.TabCompleteWarps;
import xyz.joestr.mycmd.tabcomplete.TabCompleteWhitelist;
import xyz.joestr.mycmd.tabcomplete.TabCompleteWiki;
import xyz.joestr.mycmd.tabcomplete.TabCompleteWorldspawn;
import xyz.joestr.mycmd.util.Reflection;

/**
 * MyCmd-Klasse
 * @author joestr
 * @version 1
 * @since 1
 */
public class MyCmd extends JavaPlugin {
	
	/* TODO
	 *  /pvp [<status|list|on|off>] implementieren // Konsole: /pvp [<status|list|on|off>] [<Spieler>] -done
	 *  EntityDamageEvent: PvP-Status implementieren -done
	 *  /ban <Spieler> <Grund ...> -done.
	 *  /ban-name <Name> <Grund ...>
	 *  /ban-ip <Adresse> <Grund ...> -done.
	 *  /ban-chat <Spieler> <Grund ...>
	 *  /pardon <Spieler> <Grund ...> -done.
	 *  /pardon-name <Name> <Grund ...>
	 *  /pardon-ip <Adresse> <Grund ...> -done.
	 *  /pardon-chat <Spieler> <Grund ...> 
	 *  /banlist <ip|player|name|chat> -half done.
	 *  /rank [<on|off|reload|add|remove>] [Rang] [Prefix] implementieren
	 */
	
	public YMLDelegate config = new YMLDelegate(this, "config", "config.yml");
	public YMLDelegate warps = new YMLDelegate(this, "warps", "warps.yml");
	public YMLDelegate homes = new YMLDelegate(this, "homes", "homes.yml");
	public YMLDelegate ranks = new YMLDelegate(this, "ranks", "ranks.yml");
	public YMLDelegate homes2 = new YMLDelegate(this, "homes2", "homes2.yml");
	public YMLDelegate pvp = new YMLDelegate(this, "pvp", "pvp.yml");
	public ArrayList<YMLDelegate> delegates = new ArrayList<YMLDelegate>();
	public Map<String, String> tpa = new HashMap<String, String>();
	public Map<String, String> tpaSwitched = new HashMap<String, String>();
	public Map<String, String> Tpahere = new HashMap<String, String>();
	public Map<String, String> tpahereSwitched = new HashMap<String, String>();
	public Map<String, String> whisper = new HashMap<String, String>();
	public Map<String, String> navi = new HashMap<String, String>();
	public Map<String, String> naviSwitched = new HashMap<String, String>();
	public Map<String, Date> pvpList = new HashMap<String, Date>();
	public ArrayList<String> KickEventList = new ArrayList<String>(); 
	public ArrayList<Command> commandList = new ArrayList<Command>();
	public Scoreboard scoreboard;
	
	/**
	 * Plugin-Start
	 * @author joestr
	 * @version 1
	 * @since 1
	 */
	public void onEnable() {
		
		Bukkit.getServer().getScheduler().runTask(this, new Runnable() {

			@Override
			public void run() { removeFallbackAliases(); }
		});
		
		delegates.add(config);
		delegates.add(warps);
		delegates.add(homes);
		delegates.add(ranks);
		delegates.add(homes2);
		delegates.add(pvp);
		
		this.scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		
		getCommand("warps").setExecutor(new CommandWarps(this));
		getCommand("warps").setTabCompleter(new TabCompleteWarps(this));
		getCommand("setwarp").setExecutor(new CommandSetwarp(this));
		getCommand("setwarp").setTabCompleter(new TabCompleteSetwarp(this));
		getCommand("delwarp").setExecutor(new CommandDelwarp(this));
		getCommand("delwarp").setTabCompleter(new TabCompleteDelwarp(this));
		getCommand("warp").setExecutor(new CommandWarp(this));
		getCommand("warp").setTabCompleter(new TabCompleteWarp(this));
		getCommand("home").setExecutor(new CommandHome(this));
		getCommand("home").setTabCompleter(new TabCompleteHome(this));
		getCommand("home2").setExecutor(new CommandHome2(this));
		getCommand("home2").setTabCompleter(new TabCompleteHome2(this));
		getCommand("sethome").setExecutor(new CommandSethome(this));
		getCommand("sethome").setTabCompleter(new TabCompleteSethome(this));
		getCommand("sethome2").setExecutor(new CommandSethome2(this));
		getCommand("sethome2").setTabCompleter(new TabCompleteSethome2(this));
		getCommand("delhome").setExecutor(new CommandDelhome(this));
		getCommand("delhome").setTabCompleter(new TabCompleteDelhome(this));
		getCommand("delhome2").setExecutor(new CommandDelhome2(this));
		getCommand("delhome2").setTabCompleter(new TabCompleteDelhome2(this));
		getCommand("tpa").setExecutor(new CommandTpa(this));
		getCommand("tpa").setTabCompleter(new TabCompleteTpa(this));
		getCommand("tpahere").setExecutor(new CommandTpahere(this));
		getCommand("tpahere").setTabCompleter(new TabCompleteTpahere(this));
		getCommand("tpaccept").setExecutor(new CommandTpaccept(this));
		getCommand("tpaccept").setTabCompleter(new TabCompleteTpaccept(this));
		getCommand("tpdeny").setExecutor(new CommandTpdeny(this));
		getCommand("tpdeny").setTabCompleter(new TabCompleteTpdeny(this));
		getCommand("setspawn").setExecutor(new CommandSetspawn(this));
		getCommand("setspawn").setTabCompleter(new TabCompleteSetspawn(this));
		getCommand("spawn").setExecutor(new CommandSpawn(this));
		getCommand("spawn").setTabCompleter(new TabCompleteSpawn(this));
		getCommand("delspawn").setExecutor(new CommandDelspawn(this));
		getCommand("delspawn").setTabCompleter(new TabCompleteDelspawn(this));
		getCommand("ram").setExecutor(new CommandRam(this));
		getCommand("ram").setTabCompleter(new TabCompleteRam(this));
		getCommand("ping").setExecutor(new CommandPing(this));
		getCommand("ping").setTabCompleter(new TabCompletePing(this));
		getCommand("seen").setExecutor(new CommandSeen(this));
		getCommand("seen").setTabCompleter(new TabCompleteSeen(this));
		getCommand("rank").setExecutor(new CommandRank(this));
		getCommand("rank").setTabCompleter(new TabCompleteRank(this));
		getCommand("tell").setExecutor(new CommandTell(this));
		getCommand("tell").setTabCompleter(new TabCompleteTell(this));
		getCommand("reply").setExecutor(new CommandReply(this));
		getCommand("reply").setTabCompleter(new TabCompleteReply(this));
		getCommand("mycmd").setExecutor(new CommandMycmd(this));
		getCommand("mycmd").setTabCompleter(new TabCompleteMycmd(this));
		getCommand("wiki").setExecutor(new CommandWiki(this));
		getCommand("wiki").setTabCompleter(new TabCompleteWiki(this));
		getCommand("navi").setExecutor(new CommandNavi(this));
		getCommand("navi").setTabCompleter(new TabCompleteNavi(this));
		getCommand("list").setExecutor(new CommandList(this));
		getCommand("list").setTabCompleter(new TabCompleteList(this));
		getCommand("map").setExecutor(new CommandMap(this));
		getCommand("map").setTabCompleter(new TabCompleteMap(this));
		getCommand("worldspawn").setExecutor(new CommandWorldspawn(this));
		getCommand("worldspawn").setTabCompleter(new TabCompleteWorldspawn(this));
		getCommand("kick").setExecutor(new CommandKick(this));
		getCommand("kick").setTabCompleter(new TabCompleteKick(this));
		getCommand("ban").setExecutor(new CommandBan(this));
		getCommand("ban").setTabCompleter(new TabCompleteBan(this));
		getCommand("ban-ip").setExecutor(new CommandBan_ip(this));
		getCommand("ban-ip").setTabCompleter(new TabCompleteBan_ip(this));
		getCommand("banlist").setExecutor(new CommandBanlist(this));
		getCommand("banlist").setTabCompleter(new TabCompleteBanlist(this));
		getCommand("pardon").setExecutor(new CommandPardon(this));
		getCommand("pardon").setTabCompleter(new TabCompletePardon(this));
		getCommand("pardon-ip").setExecutor(new CommandPardon_ip(this));
		getCommand("pardon-ip").setTabCompleter(new TabCompletePardon_ip(this));
		getCommand("whitelist").setExecutor(new CommandWhitelist(this));
		getCommand("whitelist").setTabCompleter(new TabCompleteWhitelist(this));
		getCommand("motd").setExecutor(new CommandMotd(this));
		getCommand("motd").setTabCompleter(new TabCompleteMotd(this));
		getCommand("pvp").setExecutor(new CommandPvp(this));
		getCommand("pvp").setTabCompleter(new TabCompletePvp(this));
		//getCommand("help").setExecutor(new CommandHelp(this));
		//getCommand("help").setTabCompleter(new TabCompleteHelp(this));
		
		Bukkit.getPluginManager().registerEvents(new EventEntityDamageByEntity(this), this);
		Bukkit.getPluginManager().registerEvents(new EventTeleport(this), this);
		Bukkit.getPluginManager().registerEvents(new EventLogin(this), this);
		Bukkit.getPluginManager().registerEvents(new EventJoin(this), this);
		Bukkit.getPluginManager().registerEvents(new EventQuit(this), this);
		Bukkit.getPluginManager().registerEvents(new EventAsyncChat(this), this);
		Bukkit.getPluginManager().registerEvents(new EventKick(this), this);
		Bukkit.getPluginManager().registerEvents(new EventListPing(this), this);
		
		for(YMLDelegate delegate : delegates) {
			
			if(!delegate.Exist()) { delegate.Create(); }
			delegate.Load();
		}
		
		if(!config.Check()) {
			
			Bukkit.getLogger().log(Level.WARNING, "Error in the " + config.getFileName() + " file.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		for(String string : this.ranks.getMap().keySet()) {
			
			String tString = (String)this.ranks.getMap().get(string);
			String[] tStrings = null;
			
			if(tString.contains(";")) {
				tStrings = tString.split(";");
			}
			
			if(tStrings.length < 3 || tStrings.length > 4) {
				continue;
			}
			
			if(this.scoreboard.getTeam(string) == null) {
				
				this.scoreboard.registerNewTeam(string);
			}
				
			this.scoreboard.getTeam(string).setPrefix(this.toColorcode("&", tStrings[0]));
			this.scoreboard.getTeam(string).setSuffix(this.toColorcode("&", tStrings[1]));
			this.scoreboard.getTeam(string).setColor(ChatColor.getByChar(this.toColorcode("&", tStrings[0])));
		}
	}
	
	/**
	 * Plugin-Ende
	 * @author joestr
	 * @version 1
	 * @since 1
	 */
	public void onDisable() {
		
		
	}
	
	public void sendActionBarToPlayer(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
	
	/**
	 * Wandelt eine  Zeichenkette mit alternativen Farb-Codes in eine Zeichenkette mit gültigen Farb-Codes um.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param alternativeColorcode Alternativer String, welcher einen Farb-Code bezeichnet.
	 * @param target Zeichenkette, welche behandelt werden sollte.
	 * @return Behandelte Zeichenkette.
	 */
	public String toColorcode(String alternativeColorcode, String target) { return target.replace(alternativeColorcode, "§"); }
	
	/**
	 * Wandelt eine Zeichenkette mit gültigen Farb-Codes in eine Zeichenkette mit alternativen Farb-Codes um.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param alternativeColorcode Alternativer String, welcher einen Farb-Code bezeichnet.
	 * @param target Zeichenkette, welche behandelt werden sollte.
	 * @return Behandelte Zeichenkette.
	 */
	public String toAlternativeColorcode(String alternativeColorcode, String target) { return target.replace("§", alternativeColorcode); }
	
	/**
	 * Gibt einen String zur richtigen Benutzung eines Befehls zurück.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param command Befehl als Zeichenkette.
	 * @return Formatierte Zeichekette.
	 */
	public String usageMessage(String command) {
		
		return ChatColor.RED + "Benutze: " + ChatColor.GRAY + command;
	}
	
	/**
	 * Gibt einen String zur richtigen Benutzung eines Befehls zurück.
	 * @deprecated
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param pln Spielername
	 * @param cmd Befehl
	 * @param act Aktion
	 * @param actv Wert der Aktion
	 * @param stv Wert der Aktion "show_text"
	 * @return tellraw-Befehlszeichenkette
	 */
	public String usageMessage(String pln, String cmd, String act, String actv, String stv) {
		
		return "tellraw " + pln + " " + 
			"[\"\"," + 
			"{" + 
			"\"text\":\"Benutze: \",\"color\":\"red\"" + 
			"}," + 
			"{" + 
			"\"text\":\"" + cmd + "\",\"color\":\"gray\"," + 
			"\"clickEvent\":" + 
			"{" + 
			"\"action\":\"" + act + "\",\"value\":\"" + actv + "\"" + 
			"}," + 
			"\"hoverEvent\":" + 
			"{" + 
			"\"action\":\"show_text\",\"value\":" + 
			"{" + 
			"\"text\":\"\",\"extra\":" + 
			"[" + 
			"{" + 
			"\"text\":\"" + stv + "\",\"color\":\"gray\"" + 
			"}" + 
			"]" + 
			"}" + 
			"}" + 
			"}" + 
			"]"
		;
	}
	
	/**
	 * Sendet einem Spieler eine Nachricht zur richtigen Benutzung eines Befehls.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player Spieler
	 * @param command Befehl als Zeichenkette
	 * @param action Aktion
	 * @param actionValue Wert der Aktion
	 * @param show_textValue Wert der Aktion "show_text"
	 */
	public void usageMessage(Player player, String command, String action, String actionValue, String show_textValue) {
		
		player.spigot().sendMessage(TextComponent.fromLegacyText(
		"[\"\"," + 
			"{" + 
			"\"text\":\"Benutze: \",\"color\":\"red\"" + 
			"}," + 
			"{" + 
			"\"text\":\"" + command + "\",\"color\":\"gray\"," + 
			"\"clickEvent\":" + 
			"{" + 
			"\"action\":\"" + action + "\",\"value\":\"" + actionValue + "\"" + 
			"}," + 
			"\"hoverEvent\":" + 
			"{" + 
			"\"action\":\"show_text\",\"value\":" + 
			"{" + 
			"\"text\":\"\",\"extra\":" + 
			"[" + 
			"{" + 
			"\"text\":\"" + show_textValue + "\",\"color\":\"gray\"" + 
			"}" + 
			"]" + 
			"}" + 
			"}" + 
			"}" + 
		"]"));
	}
	
	/**
	 * Gibt eine Nachricht die auf fehlende Rechte hinweist zurück.
	 * @deprecated
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @return Formatierte Zeichenkette
	 */
	public String noPermissionMessage() {
		
		return ChatColor.RED + "Berechtigung fehlt.";
	}
	
	/**
	 * Sendet einem Spieler eine Nachricht die auf fehlende Rechte hinweist.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player Spieler
	 */
	public void noPermissionMessage(Player player) { player.sendMessage(ChatColor.RED + "Berechtigung fehlt."); }
	
	/**
	 * Gibt eine Nachricht die auf fehlende Rechte hinweist zurück.
	 * @deprecated
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param perm Berechtigung als Zeichenkette
	 * @return Formatierte Zeichenkette mit Berechtigungszeichenkette
	 */
	public String noPermissionMessage(String perm) {
		
		return ChatColor.RED + "Berechtigung " + ChatColor.GRAY + perm + ChatColor.RED + " fehlt.";
	}
	
	/**
	 * Sendet einem Spieler eine Nachricht die auf fehlende Rechte hinweist.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player Spieler
	 * @param perm Berechtigung als Zeichenkette
	 */
	public void noPermissionMessage(Player player, String perm) { player.sendMessage(ChatColor.RED + "Berechtigung " + ChatColor.GRAY + perm + ChatColor.RED + " fehlt."); }
	
	/**
	 * Spielt ein Spielerlisten-Paket an alle Spieler aus, welche online sind.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player Spieler
	 * @param header Spielerlisten-Header
	 * @param footer Spielerlisten-Footer
	 */
	public void sendPlayerlistHeaderFooter(Player player, String header, String footer) {
		
        if(header == null) header = "";
        if(footer == null) footer = "";
        
        header = header.replaceAll("&", "§");
        footer = footer.replaceAll("&", "§");
        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());
        
        Method method = null;
		try {
			
			method = Reflection.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);
		} catch(Exception exception) { exception.printStackTrace(); }
        
        Object tabTitle = null;
		try {
			tabTitle = method.invoke(null, "{\"text\": \"" + header + "\"}");
		} catch(Exception exception) { exception.printStackTrace(); }
		
		Object tabFoot = null;
		try {
			
			tabFoot = method.invoke(null, "{\"text\": \"" + footer + "\"}");
		} catch(Exception exception) { exception.printStackTrace(); }
        
        Object packet = null;
		try { packet = Reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter").newInstance(); } catch (Exception exception) { exception.printStackTrace(); }
 
        try {
        	
            Field field = packet.getClass().getDeclaredField("a");
            field.setAccessible(true);
            Field field2 = packet.getClass().getDeclaredField("b");
            field2.setAccessible(true);
            field.set(packet, tabTitle);
            field2.set(packet, tabFoot);
        } catch(Exception exception) { exception.printStackTrace(); } finally { 
        	
        	Method sendPacket = null;
			try { sendPacket = Reflection.getNMSClass("PlayerConnection").getMethod("sendPacket", Reflection.getNMSClass("Packet")); } catch (Exception exception) { exception.printStackTrace(); }
	        try { sendPacket.invoke(Reflection.getConnection(player), packet); } catch (Exception exception) { exception.printStackTrace(); }
        }
    }
	
	/**
	 * Sendet einem Spieler eine Kommandoübersicht.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player Spieler
	 * @param title Titel
	 * @param texts Feld für Texte
	 * @param actions Feld für Aktionen
	 * @param commands Feld für Befehle als Zeichenketten
	 * @param hovers Feld für Hover-Texte
	 */
	public void commandOverview(Player player, String title, String[] texts,  String[] actions, String[] commands, String[] hovers) {
		
		player.sendMessage(ChatColor.GREEN + title + ":");
		
		if(texts.length != commands.length || texts.length != actions.length || texts.length != hovers.length) { player.sendMessage(ChatColor.RED + "Hier ist ein Fehler. Bitte einem Admin melden. Code: FQ-1"); }
		
		int i = 0;
		for(String s : texts) {
			
			player.spigot().sendMessage(TextComponent.fromLegacyText(
			"[\"\"," +
				"{" + 
				"\"text\":\">> " + s + "\",\"color\":\"gray\"," + 
				"\"clickEvent\":" + 
				"{" + 
				"\"action\":\""+ actions[i] + "\",\"value\":\"" + commands[i] + "\"" + 
				"}," + 
				"\"hoverEvent\":" + 
				"{" + 
				"\"action\":\"show_text\",\"value\":" + 
				"{" + 
				"\"text\":\"\",\"extra\":" + 
				"[" + 
				"{" + 
				"\"text\":\"" + hovers[i] + "\",\"color\":\"gray\"" + 
				"}" + 
				"]" + 
				"}" + 
				"}" + 
				"}" + 
			"]"));
			
			i++;
		}
	}
	
	/**
	 * Füllt die Kommando-Liste
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param kC Map
	 */
	public void fillCommandList(HashMap<String, Command> kC) {
		
		ArrayList<String> al = new ArrayList<String>();
		
		for(String s : kC.keySet()) { if(!al.contains(s)) { al.add(s); }; }
		
		Collections.sort(al, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
		});
		
		for(String s : al) { commandList.add(kC.get(s)); }
	}
	
	/**
	 * Entfernt Fallback-Aliase
	 * @author joestr
	 * @version 1
	 * @since 1
	 */
	@SuppressWarnings("unchecked")
	public void removeFallbackAliases() {
		
	    long start = System.nanoTime();
	    
	    try {
	    	
	    	String regex = "(?iu)(spigot|bukkit|minecraft|worldguard";
	    	int removed = 0;
	    	
	    	for(Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
	    		
	    		regex += '|' + pl.getName() + '|' + pl.getName().toLowerCase() + '|' + pl.getName().toUpperCase();
	    	}
	    	
	    	regex += "):.*";
	    	
	    	HashMap<String, Command> knownCommands = (HashMap<String, Command>)this.customGet(this.customGet(this.getServer().getPluginManager(), "commandMap"), "knownCommands");
	    	HashMap<String, Command> knownCommandsClone = (HashMap<String, Command>)knownCommands.clone();
	    	
	    	for(String key : knownCommandsClone.keySet()) { if(key.matches(regex)) { knownCommands.remove(key); removed++; } }
	    	
	    	fillCommandList(knownCommands);
	    	
	    	Bukkit.getServer().getLogger().info(String.format("[MyCmd] %s Fallback Aliase wurden in %.3f Sekunden entfernt. ", new Object[] { Integer.valueOf(removed), Double.valueOf((System.nanoTime() - start) / 1.0E9D) }));
	    } catch(Exception ex) {
	    	
	    	Bukkit.getServer().getLogger().warning("[MyCmd] Fallback Aliase wurden nicht gefiltert.");
	    	ex.printStackTrace();
	    }
	}
	
	/**
	 * Verwendung für die Methode "removeFallbackAliases".
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param obj Objekt
	 * @param fieldName Feldname
	 * @return Objekt
	 * @throws NoSuchFieldException Feld nicht gefunden
	 * @throws IllesgalAccessException Verbotener Zugriff
	 */
	private Object customGet(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		
		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}
	
	/**
	 * Einen Spielerkopf bekommen.
	 * @param skullOwner Name des Kopf-Besitzers
	 * @param displayName Anzeigename
	 * @param quantity Menge
	 * @return ItemStack
	 */
	@SuppressWarnings("deprecation")
	public ItemStack getSkull(String skullOwner, String displayName, int quantity) {
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, quantity, (byte) SkullType.PLAYER.ordinal());
		SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		skullMeta.setOwner(skullOwner);
		if(displayName != null) { skullMeta.setDisplayName(ChatColor.RESET + displayName); }
		skull.setItemMeta(skullMeta);
		return skull;
	}
}
