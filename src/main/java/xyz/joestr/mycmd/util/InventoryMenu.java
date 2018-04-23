package xyz.joestr.mycmd.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InventoryMenu implements Listener {

	Plugin plugin = null;
	Player player = null;
	int slots = 0;
	String name = "";
	ItemStack[] itemstacks = null;
	String[] commands = null;
	String[] commands2 = null;
	Inventory inventory = null;
	
	public InventoryMenu(Plugin plugin, Player player, int slots, String name, ItemStack[] itemstacks, String[] commands, String[] commands2) {
		
		this.plugin = plugin;
		this.player = player;
		this.slots = slots;
		this.name = name;
		this.itemstacks = itemstacks;
		this.commands = commands;
		this.commands2 = commands2;
	}
	
	public void createMenu() {
		
		if(!(this.slots >= 9 && this.slots <= 54 && (this.slots % 9) == 0)) {
			
			this.player.sendMessage(ChatColor.RED + "FQ2: Slot-Anzahl muss größer als 8, kleiner als 55 und durch 9 ohne Rest teilbar sein.");
			return;
		}
		
		if(!((this.itemstacks.length == this.slots) && (this.commands.length == this.slots))) {
			
			this.player.sendMessage(ChatColor.RED + "FQ3: Die Listen bezüglich Items und Befehle sind nich gleich lang wie die Slot-Anzahl.");
			return;
		}
		
		this.inventory = Bukkit.getServer().createInventory(this.player, this.slots, this.name);
		
		for(int a = 0; a < this.slots; a++) {
			
			this.inventory.setItem(a, this.itemstacks[a]);
		}
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	public void showToPlayer() {
		
		if(this.player.getOpenInventory() != null) { this.player.getOpenInventory().close(); }
		this.player.openInventory(this.inventory);
	}
	
	@EventHandler
	public void invetoryClick(InventoryClickEvent e) {
		
		if(e.getInventory().getHolder().equals(this.player)) {
			
			if(e.getInventory().equals(this.inventory)) {
				
				if(!(this.commands[e.getRawSlot()] == "")) { Bukkit.getServer().dispatchCommand(this.player, this.commands[e.getRawSlot()]); }
				if(!(this.commands2[e.getRawSlot()] == "")) { Bukkit.getServer().dispatchCommand(this.player, this.commands2[e.getRawSlot()]); }
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void inventoryInteract(InventoryInteractEvent e) {
		
		if(e.getInventory().getHolder().equals(this.player)) {
			
			if(e.getInventory().equals(this.inventory)) {
				
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void invetoryDrag(InventoryDragEvent e) {
		
		if(e.getInventory().getHolder().equals(this.player)) {
			
			if(e.getInventory().equals(this.inventory)) {
				
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void inventoryClose(InventoryCloseEvent e) {
		
		if(e.getInventory().getHolder().equals(this.player)) {
			
			if(e.getInventory().equals(this.inventory)) {
				
				try { this.finalize(); } catch (Throwable e1) { /* this.player.sendMessage(ChatColor.RED + "FQ4: Konnte Objekt nicht finalisieren."); */ e1.printStackTrace(); }
			}
		}
	}
	
}
