package xyz.joestr.mycmd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class Room implements Cloneable, ConfigurationSerializable {
	
	private String name = "";
	private String password = "";
	private List<OfflinePlayer> members = new ArrayList<>();
	private List<OfflinePlayer> operators = new ArrayList<>();
	
	public Room(String name) {
		this.name = name;
	}
	
	public Room(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<OfflinePlayer> getMembers() {
		return this.members;
	}
	
	public List<OfflinePlayer> getOperators() {
		return this.operators;
	}
	
	public void writeMessage(Player sender, String message) {
		
		List<OfflinePlayer> lop = new ArrayList<>();
		
		lop.addAll(this.operators);
		lop.addAll(this.members);
		
		lop.forEach(p -> { if(p.isOnline()) p.getPlayer().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + this.name + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + sender.getName() + ChatColor.RESET + ": " + message); } );
	}
	
	public void roomMessage(String message) {
		
		List<OfflinePlayer> lop = new ArrayList<>();
		
		lop.addAll(this.operators);
		lop.addAll(this.members);
		
		lop.forEach(p -> { if(p.isOnline()) p.getPlayer().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + this.name + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + message); } );
	}
	
	@Override
	public Map<String, Object> serialize() {
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("name", this.name);
		result.put("password", this.password);
		result.put("operators", this.operators);
		result.put("members", this.members);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static Room deserialize(Map<String, Object> map) {
		
		Room result = new Room(map.get("name").toString(), map.get("password").toString());
		
		result.getOperators().addAll((List<Player>) map.get("operators"));
		result.getMembers().addAll((List<Player>) map.get("members"));
		
		return result;
	}
}
