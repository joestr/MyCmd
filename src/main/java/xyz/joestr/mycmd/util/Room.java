package xyz.joestr.mycmd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

/**
 * Raum-System-Klasse
 * @author Joel
 *
 */
public class Room implements ConfigurationSerializable {
	
	private String name = "";
	private String password = "";
	private String format = "";
	private String infoFormat = "";
	private List<OfflinePlayer> members = new ArrayList<>();
	private List<OfflinePlayer> operators = new ArrayList<>();
	
	/**
	 * Raum-Konstruktor
	 * @param name {@linkplain String} Name des Raumes
	 * @param format {@linkplain String} Anzeigeformat des Raumes
	 * @param infoFormat {@linkplain String} Anzeigeformat des Raumes 
	 */
	public Room(String name, String format, String infoFormat) {
		this.name = name;
		this.format = format;
		this.infoFormat = infoFormat;
	}
	
	/**
	 * Raum-Konstruktor
	 * @param name {@linkplain String} Name des Raumes
	 * @param password {@linkplain String} Passwort des Raumes
	 * @param format {@linkplain String} Anzeigeformat des Raumes
	 * @param infoFormat {@linkplain String} Anzeigeformat des Raumes
	 */
	public Room(String name, String password, String format, String infoFormat) {
		this.name = name;
		this.password = password;
		this.format = format;
		this.infoFormat = infoFormat;
	}
	
	/**
	 * Name des Raumes
	 * @return {@linkplain String} Name des Raumes
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Passwort des Raumes
	 * @return {@linkplain String} Passwort des Raumes
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Setzt das Passwort des Raumes
	 * @param password {@linkplain String} Passwort des Raumes
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Anzeigeformat des Raumes
	 * @return
	 */
	public String getFormat() {
		return this.format;
	}
	/**
	 * Mitglieder im Raum
	 * @return {@linkplain List}<{@linkplain OfflinePlayer}> Liste der Mitglieder im Raum
	 */
	public List<OfflinePlayer> getMembers() {
		return this.members;
	}
	
	/**
	 * Operatoren im Raum
	 * @return {@linkplain List}<{@linkplain OfflinePlayer}> Liste der Operatoren im Raum
	 */
	public List<OfflinePlayer> getOperators() {
		return this.operators;
	}
	
	/**
	 * Schreibt eine Nachricht im Raum
	 * @param sender {@linkplain Player} Spieler, weclher die Nachricht sendet
	 * @param message {@linkplain String} Nachricht
	 */
	public void writeMessage(Player sender, String message) {
		
		List<OfflinePlayer> lop = new ArrayList<>();
		
		lop.addAll(this.operators);
		lop.addAll(this.members);
		
		lop.forEach(
				p -> {
					if(p.isOnline()) p.getPlayer().sendMessage(
							
							ChatColor.translateAlternateColorCodes(
									'&',
									format
									.replace("%room_name%", this.name)
									.replace("%player%", sender.getName())
									.replace("%player_displayname%", sender.getDisplayName())
							) + message
					);
				}
		);
	}
	
	/**
	 * Schreibt eine generelle Nachricht im Raum
	 * @param message {@linkplain String} Nachricht
	 */
	public void roomMessage(String message) {
		
		List<OfflinePlayer> lop = new ArrayList<>();
		
		lop.addAll(this.operators);
		lop.addAll(this.members);
		
		lop.forEach(
				p -> {
					if(p.isOnline()) p.getPlayer().sendMessage(
							
							ChatColor.translateAlternateColorCodes(
									'&',
									infoFormat
									.replace("%room_name%", this.name)
							) + message
					);
				}
		);
	}
	
	/**
	 * Serialisierung
	 * @return {@linkplain Map}<{@linkplain String}, {@linkplain Object}> Map mit serialisierten Inhalten
	 */
	@Override
	public Map<String, Object> serialize() {
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("name", this.name);
		result.put("password", this.password);
		result.put("format", this.format);
		result.put("infoFormat", this.infoFormat);
		result.put("operators", this.operators);
		result.put("members", this.members);
		
		return result;
	}
	
	/**
	 * Deserialisierung
	 * @param map {@linkplain Map}<{@linkplain String}, {@linkplain Object}> Map mit serialisierten Inhalten
	 * @return {@linkplain Room} Raum
	 */
	@SuppressWarnings("unchecked")
	public static Room deserialize(Map<String, Object> map) {
		
		Room result = new Room(
				map.get("name").toString(),
				map.get("password").toString(),
				map.get("format").toString(),
				map.get("infoFormat").toString()
		);
		
		result.getOperators().addAll((List<Player>) map.get("operators"));
		result.getMembers().addAll((List<Player>) map.get("members"));
		
		return result;
	}
}
