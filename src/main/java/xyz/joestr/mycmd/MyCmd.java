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
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
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
import xyz.joestr.mycmd.command.CommandBroadcast;
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
import xyz.joestr.mycmd.command.CommandRoom;
import xyz.joestr.mycmd.command.CommandSeen;
import xyz.joestr.mycmd.command.CommandSethome;
import xyz.joestr.mycmd.command.CommandSethome2;
import xyz.joestr.mycmd.command.CommandSetspawn;
import xyz.joestr.mycmd.command.CommandSetwarp;
import xyz.joestr.mycmd.command.CommandSetworldspawn;
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
import xyz.joestr.mycmd.tabcomplete.TabCompleteRoom;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSeen;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSethome;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSethome2;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSetspawn;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSetwarp;
import xyz.joestr.mycmd.tabcomplete.TabCompleteSetworldspawn;
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
import xyz.joestr.mycmd.util.Room;
import xyz.joestr.mycmd.util.YMLDelegate;

/**
 * MyCmd-Klasse
 * @author joestr
 * @version 1
 * @since 1
 */
public class MyCmd extends JavaPlugin {
	
	/*
	 * &9 ChatColor.BLUE - Statusmeldungen
	 * &c ChatColor.RED - Fehlermeldungen
	 * &a ChatColor.GREEN - Erfolgsmeldungen
	 * &7 ChatColor.GRAY - Hervorheben von Werten in Status-, Fehler-, und Erfolgsmeldungen
	 */
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
	 *  /rank [<on|off|reload|list|add|remove>] [Rang] [Prefix] [Suffix] [Displaynameprefix] implementieren - done
	 */
	
	// Beeinhaltet die Einstellungen aus der config.yml
	public YMLDelegate config = new YMLDelegate(this, "config", "config.yml");
	
	// Beeinhaltet die Warps aus der warps.yml
	public YMLDelegate warps = new YMLDelegate(this, "warps", "warps.yml");
	
	// Beeinhaltet die Homes aus der homes.yml
	public YMLDelegate homes = new YMLDelegate(this, "homes", "homes.yml");
	
	// Beeinhaltet die zweiten Homes aus der home2.yml
	public YMLDelegate homes2 = new YMLDelegate(this, "homes2", "homes2.yml");
	
	// Beeinhaltet die Ränge aus der ranks.yml
	public YMLDelegate ranks = new YMLDelegate(this, "ranks", "ranks.yml");
	
	// Beeinhaltet die PvP-Einstellungen aus der pvp.yml
	public YMLDelegate pvp = new YMLDelegate(this, "pvp", "pvp.yml");
	
	// Beeinhaltet die Räume
	public YMLDelegate rooms = new YMLDelegate(this, "rooms", "rooms.yml");
	
	// Alle oben definierten YMLDelegates werden in dieser Liste gespeichert
	public ArrayList<YMLDelegate> delegates = new ArrayList<YMLDelegate>();
	
	// Maps für /tpa
	public Map<String, String> tpa = new HashMap<String, String>();
	public Map<String, String> tpaSwitched = new HashMap<String, String>();
	
	// Maps für /tpahere
	public Map<String, String> tpahere = new HashMap<String, String>();
	public Map<String, String> tpahereSwitched = new HashMap<String, String>();
	
	// Hier sind die Whisper-Partner
	public Map<String, String> whisper = new HashMap<String, String>();
	
	// Maps für /navi
	public Map<String, String> navi = new HashMap<String, String>();
	public Map<String, String> naviSwitched = new HashMap<String, String>();
	
	// Hier sind PvP-Sachen drin
	public Map<String, Date> pvpList = new HashMap<String, Date>();
	
	// Liste für gekickte Spieler
	public ArrayList<String> kickEventList = new ArrayList<String>();
	
	// Kommandoliste
	public ArrayList<Command> commandList = new ArrayList<Command>();
	
	// Zugriff auf das Scoreboard
	public Scoreboard scoreboard;
	
	// Plugin-Prefix
	public String pluginPrefix = "";
	
	/**
	 * Plugin-Start
	 * @author joestr
	 * @version 1
	 * @since 1
	 */
	public void onEnable() {
		
		ConfigurationSerialization.registerClass(Room.class);
		
		// Die Delegates zur Liste hinzufügen
		this.delegates.add(this.config);
		this.delegates.add(this.warps);
		this.delegates.add(this.homes);
		this.delegates.add(this.ranks);
		this.delegates.add(this.homes2);
		this.delegates.add(this.pvp);
		this.delegates.add(this.rooms);
		
		// Delegates prüfen
		for(YMLDelegate delegate : delegates) {
			if(!delegate.Exist()) {
				delegate.Create();
			}
			
			try {
				delegate.Load();
			} catch (Exception e) {
				
				delegate.Create();
			} finally {
				
				
			}
		}
				
		// Überprüfung des Config-Delegates
		// Es muss nur das Config-Delegate gepüft werden,
		// da die anderen Delegates Variable Einträge haben.
		if(this.config.EntryCheck()) {
			this.config.Save();
		}
		
		Bukkit.getServer().getScheduler().runTask(
				this,
				new Runnable() {
					@Override 
					public void run() {
						removeFallbackAliases();
					}
				}
		);
		
		// Scoreboard setzten (Verwendung des Hauptscoreboards)
		this.scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		
		// Plugin-Präfix setzen
		this.pluginPrefix = this.toColorcode("&", (String) this.config.getMap().get("plugin-prefix"));
		
		// Kommandos registrieren
		this.getCommand("warps").setExecutor(new CommandWarps(this));
		this.getCommand("warps").setTabCompleter(new TabCompleteWarps(this));
		this.getCommand("setwarp").setExecutor(new CommandSetwarp(this));
		this.getCommand("setwarp").setTabCompleter(new TabCompleteSetwarp(this));
		this.getCommand("delwarp").setExecutor(new CommandDelwarp(this));
		this.getCommand("delwarp").setTabCompleter(new TabCompleteDelwarp(this));
		this.getCommand("warp").setExecutor(new CommandWarp(this));
		this.getCommand("warp").setTabCompleter(new TabCompleteWarp(this));
		this.getCommand("home").setExecutor(new CommandHome(this));
		this.getCommand("home").setTabCompleter(new TabCompleteHome(this));
		this.getCommand("home2").setExecutor(new CommandHome2(this));
		this.getCommand("home2").setTabCompleter(new TabCompleteHome2(this));
		this.getCommand("sethome").setExecutor(new CommandSethome(this));
		this.getCommand("sethome").setTabCompleter(new TabCompleteSethome(this));
		this.getCommand("sethome2").setExecutor(new CommandSethome2(this));
		this.getCommand("sethome2").setTabCompleter(new TabCompleteSethome2(this));
		this.getCommand("delhome").setExecutor(new CommandDelhome(this));
		this.getCommand("delhome").setTabCompleter(new TabCompleteDelhome(this));
		this.getCommand("delhome2").setExecutor(new CommandDelhome2(this));
		this.getCommand("delhome2").setTabCompleter(new TabCompleteDelhome2(this));
		this.getCommand("tpa").setExecutor(new CommandTpa(this));
		this.getCommand("tpa").setTabCompleter(new TabCompleteTpa(this));
		this.getCommand("tpahere").setExecutor(new CommandTpahere(this));
		this.getCommand("tpahere").setTabCompleter(new TabCompleteTpahere(this));
		this.getCommand("tpaccept").setExecutor(new CommandTpaccept(this));
		this.getCommand("tpaccept").setTabCompleter(new TabCompleteTpaccept(this));
		this.getCommand("tpdeny").setExecutor(new CommandTpdeny(this));
		this.getCommand("tpdeny").setTabCompleter(new TabCompleteTpdeny(this));
		this.getCommand("setspawn").setExecutor(new CommandSetspawn(this));
		this.getCommand("setspawn").setTabCompleter(new TabCompleteSetspawn(this));
		this.getCommand("spawn").setExecutor(new CommandSpawn(this));
		this.getCommand("spawn").setTabCompleter(new TabCompleteSpawn(this));
		this.getCommand("delspawn").setExecutor(new CommandDelspawn(this));
		this.getCommand("delspawn").setTabCompleter(new TabCompleteDelspawn(this));
		this.getCommand("ram").setExecutor(new CommandRam(this));
		this.getCommand("ram").setTabCompleter(new TabCompleteRam(this));
		this.getCommand("ping").setExecutor(new CommandPing(this));
		this.getCommand("ping").setTabCompleter(new TabCompletePing(this));
		this.getCommand("seen").setExecutor(new CommandSeen(this));
		this.getCommand("seen").setTabCompleter(new TabCompleteSeen(this));
		this.getCommand("rank").setExecutor(new CommandRank(this));
		this.getCommand("rank").setTabCompleter(new TabCompleteRank(this));
		this.getCommand("tell").setExecutor(new CommandTell(this));
		this.getCommand("tell").setTabCompleter(new TabCompleteTell(this));
		this.getCommand("reply").setExecutor(new CommandReply(this));
		this.getCommand("reply").setTabCompleter(new TabCompleteReply(this));
		this.getCommand("mycmd").setExecutor(new CommandMycmd(this));
		this.getCommand("mycmd").setTabCompleter(new TabCompleteMycmd(this));
		this.getCommand("wiki").setExecutor(new CommandWiki(this));
		this.getCommand("wiki").setTabCompleter(new TabCompleteWiki(this));
		this.getCommand("navi").setExecutor(new CommandNavi(this));
		this.getCommand("navi").setTabCompleter(new TabCompleteNavi(this));
		this.getCommand("list").setExecutor(new CommandList(this));
		this.getCommand("list").setTabCompleter(new TabCompleteList(this));
		this.getCommand("map").setExecutor(new CommandMap(this));
		this.getCommand("map").setTabCompleter(new TabCompleteMap(this));
		this.getCommand("worldspawn").setExecutor(new CommandWorldspawn(this));
		this.getCommand("worldspawn").setTabCompleter(new TabCompleteWorldspawn(this));
		this.getCommand("setworldspawn").setExecutor(new CommandSetworldspawn(this));
		this.getCommand("setworldspawn").setTabCompleter(new TabCompleteSetworldspawn(this));
		this.getCommand("kick").setExecutor(new CommandKick(this));
		this.getCommand("kick").setTabCompleter(new TabCompleteKick(this));
		this.getCommand("ban").setExecutor(new CommandBan(this));
		this.getCommand("ban").setTabCompleter(new TabCompleteBan(this));
		this.getCommand("ban-ip").setExecutor(new CommandBan_ip(this));
		this.getCommand("ban-ip").setTabCompleter(new TabCompleteBan_ip(this));
		this.getCommand("banlist").setExecutor(new CommandBanlist(this));
		this.getCommand("banlist").setTabCompleter(new TabCompleteBanlist(this));
		this.getCommand("pardon").setExecutor(new CommandPardon(this));
		this.getCommand("pardon").setTabCompleter(new TabCompletePardon(this));
		this.getCommand("pardon-ip").setExecutor(new CommandPardon_ip(this));
		this.getCommand("pardon-ip").setTabCompleter(new TabCompletePardon_ip(this));
		this.getCommand("whitelist").setExecutor(new CommandWhitelist(this));
		this.getCommand("whitelist").setTabCompleter(new TabCompleteWhitelist(this));
		this.getCommand("motd").setExecutor(new CommandMotd(this));
		this.getCommand("motd").setTabCompleter(new TabCompleteMotd(this));
		this.getCommand("pvp").setExecutor(new CommandPvp(this));
		this.getCommand("pvp").setTabCompleter(new TabCompletePvp(this));
		//this.getCommand("help").setExecutor(new CommandHelp(this));
		//this.getCommand("help").setTabCompleter(new TabCompleteHelp(this));
		this.getCommand("room").setExecutor(new CommandRoom(this));
		this.getCommand("room").setTabCompleter(new TabCompleteRoom(this));
		this.getCommand("broadcast").setExecutor(new CommandBroadcast(this));
		
		Bukkit.getServer().getPluginManager().registerEvents(new EventEntityDamageByEntity(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EventTeleport(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EventLogin(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EventJoin(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EventQuit(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EventAsyncChat(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EventKick(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EventListPing(this), this);
		
		// Ränge aus der ranks.yml bearbeiten
		for(String string : this.ranks.getMap().keySet()) {
			
			// Temporäre Variablen
			String tString = (String) this.ranks.getMap().get(string);
			String[] tStrings = null;
			
			// Sind ;-te im String?
			if(tString.contains(";")) {
				tStrings = tString.split(";");
			}
			
			// 3 oder 4 elemente
			// 3: text;text;text
			// 4: text;text;text;
			// Wichtig ist eigentlich nur das es mind. 3 Elemnte gibt.
			if(tStrings.length < 3 || tStrings.length > 4) {
				continue;
			}
			
			// Wenn es das Team noch nicht gibt ...
			if(this.scoreboard.getTeam(string) == null) {
				// ... neues Team registrieren.
				this.scoreboard.registerNewTeam(string);
			}
			
			// Dem Team Präfix, Suffix und Anzeigename-Präfix setzen.
			try {
				this.scoreboard.getTeam(string).setPrefix(this.toColorcode("&", tStrings[0]));
			} catch(IllegalStateException | IllegalArgumentException e) {
				Bukkit.getServer().getLogger().log(Level.SEVERE, "Fehler beim setzen des Präfixes vom Rang " + string + ".");
			}
			
			try {
				this.scoreboard.getTeam(string).setSuffix(this.toColorcode("&", tStrings[1]));
			} catch(IllegalStateException | IllegalArgumentException e) {
				Bukkit.getServer().getLogger().log(Level.SEVERE, "Fehler beim setzen des Suffixes vom Rang " + string + ".");
			}
			
			// Anzeigename-Präfix hier faul über den Team-Anzeigename gesetzt ;D
			try {
				this.scoreboard.getTeam(string).setDisplayName(this.toColorcode("&", tStrings[2]));
			} catch(IllegalStateException | IllegalArgumentException e) {
				Bukkit.getServer().getLogger().log(Level.SEVERE, "Fehler beim setzen des Anzeigenamen-Präfixes vom Rang " + string + ".");
			}
		}
	}
	
	/**
	 * Plugin-Ende
	 * @author joestr
	 * @version 1
	 * @since 1
	 */
	public void onDisable() {
		
		// Scoreboard sauber machen (Teams aus dem Scoreboard entfernen, welche im Delegate drin sind)
		for(String string : this.ranks.getMap().keySet()) {
			if(this.scoreboard.getTeam(string) != null) {
				this.scoreboard.getTeam(string).unregister();
			}
		}
	}
	
	/**
	 * Sendet eine Actionbar-Nachricht an einen Spieler.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player {@link Player} Ziel-Spieler
	 * @param message {@link String} Nachricht
	 */
	public void sendActionBarToPlayer(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
	
	/**
	 * Wandelt eine  Zeichenkette mit alternativen Farb-Codes in eine Zeichenkette mit gültigen Farb-Codes um.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param alternativeColorcode {@link String} Alternativer String welcher einen Farb-Code bezeichnet.
	 * @param target {@link String} Zeichenkette welche behandelt werden sollte.
	 * @return {@link String} Behandelte Zeichenkette.
	 */
	public String toColorcode(String alternativeColorcode, String target) {
		
		if(alternativeColorcode.length() > 0) {
			return ChatColor.translateAlternateColorCodes(alternativeColorcode.charAt(0), target);
		} else {
			return target;
		}
	}
	
	/**
	 * Wandelt eine Zeichenkette mit gültigen Farb-Codes in eine Zeichenkette mit alternativen Farb-Codes um.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param alternativeColorcode {@link String} Alternativer String welcher einen Farb-Code bezeichnet.
	 * @param target {@link String} Zeichenkette welche behandelt werden sollte.
	 * @return {@link String} Behandelte Zeichenkette.
	 */
	public String toAlternativeColorcode(String alternativeColorcode, String target) {
		
		if(alternativeColorcode.length() > 0) {
			return target.replace(ChatColor.COLOR_CHAR, alternativeColorcode.charAt(0));
		} else {
			return target;
		}
	}
	
	/**
	 * Sendet einem Befehlssender eine Nachricht zur richtigen Benutzung eines Befehls
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param commandSender {@linkplain CommandSender} Befehlssender
	 * @param command {@link String} Befehl als Zeichenkette.
	 */
	public void usageMessage(CommandSender commandSender, String command) {
		commandSender.sendMessage(this.pluginPrefix + ChatColor.RED + "Benutze: " + ChatColor.GRAY + command);
	}
	
	/**
	 * Sendet einem Spieler eine Nachricht zur richtigen Benutzung eines Befehls.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player {@link Player} Spieler
	 * @param command {@link String} Befehl als Zeichenkette
	 * @param action {@link String} Aktion
	 * @param actionValue {@link String} Wert der Aktion
	 * @param show_textValue {@link String} Wert der Aktion "show_text"
	 */
	public void usageMessage(Player player, String command, String action, String actionValue, String show_textValue) {
		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), 
		"tellraw " + player.getName() + " " +
		"[\"\"," + 
			"{" +
			"\"text\":\"" + this.pluginPrefix + "\"" +
			"}," +
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
		"]");
	}
	
	/**
	 * Sendet einem Spieler eine Nachricht die auf fehlende Rechte hinweist.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player Spieler
	 */
	public void noPermissionMessage(Player player) {
		player.sendMessage(this.pluginPrefix + ChatColor.RED + "Berechtigung fehlt.");
	}
	
	/**
	 * Sendet einem Spieler eine Nachricht die auf fehlende Rechte hinweist.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player {@link Player} Spieler
	 * @param perm {@link String} Berechtigung als Zeichenkette
	 */
	public void noPermissionMessage(Player player, String perm) {
		player.sendMessage(this.pluginPrefix + ChatColor.RED + "Berechtigung " + ChatColor.GRAY + perm + ChatColor.RED + " fehlt.");
	}
	
	/**
	 * Spielt ein Spielerlisten-Paket an alle Spieler aus, welche online sind.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param player {@link Player} Spieler
	 * @param header {@link String} Spielerlisten-Header
	 * @param footer {@link String} Spielerlisten-Footer
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
	 * @param player {@link Player} Spieler
	 * @param title {@link String}[] Titel
	 * @param texts {@link String}[] Feld für Texte
	 * @param actions {@link String}[] Feld für Aktionen
	 * @param commands {@link String}[] Feld für Befehle als Zeichenketten
	 * @param hovers {@link String}[] Feld für Hover-Texte
	 */
	public void commandOverview(Player player, String title, String[] texts,  String[] actions, String[] commands, String[] hovers) {
		
		player.sendMessage(ChatColor.GREEN + title + ":");
		
		if(texts.length != commands.length || texts.length != actions.length || texts.length != hovers.length) { player.sendMessage(ChatColor.RED + "Hier ist ein Fehler. Bitte einem Admin melden. Code: FQ-1"); }
		
		int i = 0;
		for(String s : texts) {
			
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), 
			"tellraw " + player.getName() + " " +
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
			"]");
			
			i++;
		}
	}
	
	/**
	 * Füllt die Kommando-Liste
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param kC {@link Map} Map
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
	 * @param obj {@link Object} Objekt
	 * @param fieldName {@link String} Feldname
	 * @return {@link Object} Objekt
	 * @throws {@link NoSuchFieldException} Feld nicht gefunden
	 * @throws {@link IllesgalAccessException} Verbotener Zugriff
	 */
	private Object customGet(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		
		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}
	
	/**
	 * Einen Spielerkopf bekommen.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param skullOwner {@link String} Name des Kopf-Besitzers
	 * @param displayName {@link String} Anzeigename
	 * @param quantity {@link Integer} Menge
	 * @return {@link ItemStack} ItemStack
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
	
	/**
	 * Behandelt einen String und ersetzt das spezielle Zeichen für ein Leerzeichen durch ein richtiges Leerzeichen.
	 * @author joestr
	 * @version 1
	 * @since 1
	 * @param string {@link String} Zu behandelnde Zeichenkette.
	 * @return {@link String} Behandelte Zeichnkette
	 */
	public String replaceSpecialWhitespaceChar(String string) {
		return string.replaceAll("%s", " ");
	}
}
