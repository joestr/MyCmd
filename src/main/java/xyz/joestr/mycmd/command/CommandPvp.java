package xyz.joestr.mycmd.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.cloutteam.samjakob.gui.ItemBuilder;
import com.cloutteam.samjakob.gui.buttons.GUIButton;
import com.cloutteam.samjakob.gui.types.PaginatedGUI;

import xyz.joestr.mycmd.MyCmd;
import xyz.joestr.mycmd.util.InventoryMenu;

public class CommandPvp implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandPvp(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.pvp") && !player.hasPermission("mycmd.command.pvp.other")) {
				
				player.sendMessage(this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(player.hasPermission("mycmd.command.pvp") && player.hasPermission("mycmd.command.pvp.other")) {
					
					// Instantiate the GUI
					//PaginatedGUI menu = new PaginatedGUI("PvP-Menü");
					//
					// Instantiate the GUIButton
					// This takes an ItemStack as an argument, however I have created an
					// ItemBuilder class to create custom ItemStacks very easily.
					//GUIButton button = new GUIButton(
					//    ItemBuilder.start(Material.BOOK).name("&eView the rules")
					//        .lore(Arrays.asList("&eMy first button!")).build()
					//);
					//
					// Yep - that's right. Lambda support!
					//button.setListener(event -> {
					//    event.setCancelled(true);
					//    event.getWhoClicked().sendMessage("1. Don't cheat!");
					//});
					//
					//menu.addButton(button);
					//
					// Open the inventory on the player's screen
					//player.openInventory(menu.getInventory());
					
					PaginatedGUI menu = new PaginatedGUI("PvP-Menü");
					
					GUIButton pvp_status = null;
					
					if(this.plugin.pvp.getMap().containsKey(player.getName()) && (Boolean)this.plugin.pvp.getMap().get(player.getName())) {
						pvp_status = new GUIButton(
								ItemBuilder.start(Material.GREEN_SHULKER_BOX).name(ChatColor.GREEN + "Dein PvP ist momentan aktiviert.")
									.lore(Arrays.asList("Zum Ändern klicken.")).build()
							);
						pvp_status.setListener(event -> {
						    event.setCancelled(true);
						    Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "pvp off");
						});
					} else {
						this.plugin.pvp.getMap().put(player.getName(), false);
						this.plugin.pvp.Save();
						pvp_status = new GUIButton(
								ItemBuilder.start(Material.RED_SHULKER_BOX).name(ChatColor.RED + "Dein PvP ist momentan deaktiviert.")
									.lore(Arrays.asList("Zum Ändern klicken.")).build()
							);
						pvp_status.setListener(event -> {
						    event.setCancelled(true);
						    Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "pvp on");
						});
					}
					
					menu.addButton(pvp_status);
					
					player.openInventory(menu.getInventory());
					
					/*
					this.plugin.commandOverview(player, "PvP",
							new String[] {"Dein PvP aktivieren", "Dein PvP deaktivieren", "Deinen PvP-Status anzeigen", "Liste aller dezeitigen PvP-Teilnehmer", "PvP eines Spielers aktivieren", "PvP eines Spielers deaktivieren", "PvP-Status eines Spielers anzeigen"},
							new String[] {"run_command", "run_command", "run_command", "run_command", "suggest_command", "suggest_command", "suggest_command"},
							new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list", "/pvp on ", "/pvp off ", "/pvp status "},
							new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list", "/pvp on <Spieler>", "/pvp off <Spieler>", "/pvp status <Spieler>"});
					*/
					return true;
				}
				
				if(player.hasPermission("mycmd.command.pvp.other")) {
					
					ItemStack is = new ItemStack(Material.CONCRETE);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName("PVP-Eintrag");
					im.addEnchant(Enchantment.MENDING, 1, true);
					is.setItemMeta(im);
					
					InventoryMenu imenu = new InventoryMenu(this.plugin, player, 9, "PVP-Menü",
						new ItemStack[] { is, is, is, is, is, is, is, is, is},
						new String[] { "/t", "/t", "/t", "/t", "/t", "/t", "/t", "/t", "/t" },
						new String[] { "/t", "/t", "/t", "/t", "/t", "/t", "/t", "/t", "/t" }
					);
					
					imenu.createMenu();
					imenu.showToPlayer();
					
					/*
					this.plugin.commandOverview(player, "PvP",
							new String[] {"PvP eines Spielers aktivieren", "PvP eines Spielers deaktivieren", "PvP-Status eines Spielers anzeigen"},
							new String[] {"suggest_command", "suggest_command", "suggest_command"},
							new String[] {"/pvp on ", "/pvp off ", "pvp status "},
							new String[] {"/pvp on <Spieler>", "/pvp off <Spieler>", "/pvp status <Spieler>"});
					*/
					return true;
				}
				
				if(player.hasPermission("mycmd.command.pvp")) {
					
					ItemStack e = new ItemStack(Material.GRAY_SHULKER_BOX);
					ItemMeta em = e.getItemMeta();
					em.setDisplayName("");
					e.setItemMeta(em);
					
					ItemStack status = new ItemStack(Material.AIR);
					ItemMeta statusm = status.getItemMeta();
					
					if(Bukkit.getOnlineMode()) {
						
						if(this.plugin.pvp.getMap().containsKey(player.getUniqueId().toString())) {
							
							status = new ItemStack(Material.LIME_SHULKER_BOX);
							statusm = status.getItemMeta();
							statusm.setDisplayName(ChatColor.GRAY + "Dein PvP ist zurzeit " + ChatColor.GREEN + "aktiviert" + ChatColor.GRAY + ".");
						} else {
						
							this.plugin.pvp.getMap().put(player.getUniqueId().toString(), false);
							status = new ItemStack(Material.RED_SHULKER_BOX);
							statusm = status.getItemMeta();
							statusm.setDisplayName(ChatColor.GRAY + "Dein PvP ist zurzeit " + ChatColor.RED + "deaktiviert" + ChatColor.GRAY + ".");
						}
					} else {
					
						if(this.plugin.pvp.getMap().containsKey(player.getName())) {
							
							status = new ItemStack(Material.LIME_SHULKER_BOX);
							statusm = status.getItemMeta();
							statusm.setDisplayName(ChatColor.GRAY + "Dein PvP ist zurzeit " + ChatColor.GREEN + "aktiviert" + ChatColor.GRAY + ".");
						} else {
							
							this.plugin.pvp.getMap().put(player.getUniqueId().toString(), false);
							status = new ItemStack(Material.RED_SHULKER_BOX);
							statusm = status.getItemMeta();
							statusm.setDisplayName(ChatColor.GRAY + "Dein PvP ist zurzeit " + ChatColor.RED + "deaktiviert" + ChatColor.GRAY + ".");
						}
					}
					
					status.setItemMeta(statusm);
					
					ItemStack on = new ItemStack(Material.LIME_SHULKER_BOX);
					ItemMeta onm = on.getItemMeta();
					onm.setDisplayName(ChatColor.GRAY + "Dein PvP " + ChatColor.GREEN + "aktivieren" + ChatColor.GRAY + ".");
					on.setItemMeta(onm);
					
					ItemStack off = new ItemStack(Material.RED_SHULKER_BOX);
					ItemMeta offm = off.getItemMeta();
					offm.setDisplayName(ChatColor.GRAY + "Dein PvP " + ChatColor.RED + "deaktivieren" + ChatColor.GRAY + ".");
					off.setItemMeta(offm);
					
					InventoryMenu imenu = new InventoryMenu(this.plugin, player, 9, "PVP-Menü",
						new ItemStack[] { this.plugin.getSkull(player.getUniqueId().toString(), player.getDisplayName(), 1), status, e, e, e, e, e, on, off },
						new String[] { "", "pvp status", "", "", "", "", "", "pvp on", "pvp off" },
						new String[] { "", "pvp", "", "", "", "", "", "pvp", "pvp" }
					);
					
					imenu.createMenu();
					imenu.showToPlayer();
					
					return true;
				}
			}
			
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.pvp")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.pvp"));
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("info")) {
					
					if(player.hasPermission("mycmd.command.pvp") && player.hasPermission("mycmd.command.pvp.other")) {
						
						this.plugin.commandOverview(player, "PvP",
								new String[] {"Dein PvP aktivieren", "Dein PvP deaktivieren", "Deinen PvP-Status anzeigen", "Liste aller dezeitigen PvP-Teilnehmer", "PvP eines Spielers aktivieren", "PvP eines Spielers deaktivieren", "PvP-Status eines Spielers anzeigen"},
								new String[] {"run_command", "run_command", "run_command", "run_command", "suggest_command", "suggest_command", "suggest_command"},
								new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list", "/pvp on ", "/pvp off ", "/pvp status "},
								new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list", "/pvp on <Spieler>", "/pvp off <Spieler>", "/pvp status <Spieler>"});
						return true;
					}
					
					if(player.hasPermission("mycmd.command.pvp.other")) {
						
						this.plugin.commandOverview(player, "PvP",
								new String[] {"PvP eines Spielers aktivieren", "PvP eines Spielers deaktivieren", "PvP-Status eines Spielers anzeigen"},
								new String[] {"suggest_command", "suggest_command", "suggest_command"},
								new String[] {"/pvp on ", "/pvp off ", "pvp status "},
								new String[] {"/pvp on <Spieler>", "/pvp off <Spieler>", "/pvp status <Spieler>"});
						return true;
					}
					
					if(player.hasPermission("mycmd.command.pvp")) {
						
						this.plugin.commandOverview(player, "PvP",
								new String[] {"Dein PvP aktivieren", "Dein PvP deaktivieren", "Deinen PvP-Status anzeigen", "Liste aller dezeitigen PvP-Teilnehmer"},
								new String[] {"run_command", "run_command", "run_command", "run_command"},
								new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list"},
								new String[] {"/pvp on", "/pvp off", "/pvp status", "/pvp list"});
						return true;
					}
				}
				
				if(arg[0].equalsIgnoreCase("status")) {
					
					if(Bukkit.getOnlineMode()) {
						
						if(this.plugin.pvp.getMap().containsKey(player.getUniqueId().toString())) {
							
							String onoff = "";
							if((Boolean)this.plugin.pvp.getMap().get(player.getUniqueId().toString())) { onoff = "aktiviert"; } else { onoff = "deaktiviert."; }
							player.sendMessage(ChatColor.GREEN + "Dein PvP ist zurzeit " + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
							return true;
						}
						
						this.plugin.pvp.getMap().put(player.getUniqueId().toString(), false);
						player.sendMessage(ChatColor.GREEN + "Dein PvP ist zurzeit " + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
						return true;
					}
					
					if(this.plugin.pvp.getMap().containsKey(player.getName())) {
						
						String onoff = "";
						if((Boolean)this.plugin.pvp.getMap().get(player.getName())) { onoff = "aktiviert"; } else { onoff = "deaktiviert."; }
						player.sendMessage(ChatColor.GREEN + "Dein PvP ist zurzeit " + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
						return true;
					}
						
					this.plugin.pvp.getMap().put(player.getName(), false);
					player.sendMessage(ChatColor.GREEN + "Dein PvP ist zurzeit " + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("list")) {
					
					_pvp_list_(sender);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("on")) {
					
					if(Bukkit.getOnlineMode()) {
						
						if(this.plugin.pvp.getMap().containsKey(player.getUniqueId().toString())) {
							
							if(!(Boolean)this.plugin.pvp.getMap().get(player.getUniqueId().toString())) {
								
								this.plugin.pvp.getMap().put(player.getUniqueId().toString(), true);
								this.plugin.pvp.Save();
								sender.sendMessage(ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
								return true;
							} else {
								
								sender.sendMessage(ChatColor.RED + "Dein PvP ist bereits "  + ChatColor.GRAY + "aktiviert" + ChatColor.RED + ".");
								return true;
							}
						}
						
						this.plugin.pvp.getMap().put(player.getUniqueId().toString(), true);
						this.plugin.pvp.Save();
						sender.sendMessage(ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
						return true;
					}
					
					if(this.plugin.pvp.getMap().containsKey(player.getName())) {
						
						if(!(Boolean)this.plugin.pvp.getMap().get(player.getName())) {
							
							this.plugin.pvp.getMap().put(player.getName(), true);
							this.plugin.pvp.Save();
							sender.sendMessage(ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
							return true;
						} else {
							
							sender.sendMessage(ChatColor.RED + "Dein PvP ist bereits "  + ChatColor.GRAY + "aktiviert" + ChatColor.RED + ".");
							return true;
						}
					}
					
					this.plugin.pvp.getMap().put(player.getName(), true);
					this.plugin.pvp.Save();
					sender.sendMessage(ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("off")) {
					
					if(this.plugin.pvpList.containsKey(player.getName())) { player.sendMessage(ChatColor.RED + "PvP-Schutz ist noch aktiv."); return true; }
					
					if(Bukkit.getOnlineMode()) {
						
						if(this.plugin.pvp.getMap().containsKey(player.getUniqueId().toString())) {
							
							if((Boolean)this.plugin.pvp.getMap().get(player.getUniqueId().toString())) {
								
								this.plugin.pvp.getMap().put(player.getUniqueId().toString(), false);
								this.plugin.pvp.Save();
								sender.sendMessage(ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
								return true;
							} else {
								
								sender.sendMessage(ChatColor.RED + "Dein PvP ist bereits "  + ChatColor.GRAY + "deaktiviert" + ChatColor.RED + ".");
								return true;
							}
						}
						
						this.plugin.pvp.getMap().put(player.getUniqueId().toString(), false);
						this.plugin.pvp.Save();
						sender.sendMessage(ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
						return true;
					}
					
					if(this.plugin.pvp.getMap().containsKey(player.getName())) {
						
						if((Boolean)this.plugin.pvp.getMap().get(player.getName())) {
							
							this.plugin.pvp.getMap().put(player.getName(), false);
							this.plugin.pvp.Save();
							sender.sendMessage(ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
							return true;
						} else {
							
							sender.sendMessage(ChatColor.RED + "Dein PvP ist bereits "  + ChatColor.GRAY + "deaktiviert" + ChatColor.RED + ".");
							return true;
						}
					}
					
					this.plugin.pvp.getMap().put(player.getName(), false);
					this.plugin.pvp.Save();
					sender.sendMessage(ChatColor.GREEN + "Du hast dein PvP "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
					return true;
				}
			}
			
			if(arg.length == 2) {
				
				if(!player.hasPermission("mycmd.command.pvp.other")) {
					
					player.sendMessage(this.plugin.noPermissionMessage("mycmd.command.pvp.other"));
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("status")) {
					
					_pvp_status_(sender, arg);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("on")) {
					
					_pvp_on_(sender, arg);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("off")) {
					
					_pvp_off_(sender, arg);
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.pvp") && player.hasPermission("mycmd.command.pvp.other")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/pvp [<list|status|on|off>] [<Spieler>]", "suggest_command", "/pvp ", "/pvp <list|status|on|off> [<Spieler>]"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.pvp.other")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/pvp [<status|on|off>] [<Spieler>]", "suggest_command", "/pvp ", "/pvp <status|on|off> <Spieler>"));
				return true;
			}
			
			if(player.hasPermission("mycmd.command.pvp")) {
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.usageMessage(player.getName(), "/pvp [<list|status|on|off>]", "suggest_command", "/pvp ", "/pvp <list|status|on|off>"));
				return true;
			}
		}
		
		//Console
		if(arg.length == 1) {
			
			if(arg[0].equalsIgnoreCase("list")) {
				
				_pvp_list_(sender);
				return true;
			}
		}
		
		if(arg.length == 2) {
			
			if(arg[0].equalsIgnoreCase("status")) {
				
				_pvp_status_(sender, arg);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("on")) {
				
				_pvp_on_(sender, arg);
				return true;
			}
			
			if(arg[0].equalsIgnoreCase("off")) {
				
				_pvp_off_(sender, arg);
				return true;
			}
		}
		
		sender.sendMessage(this.plugin.usageMessage("/pvp <list|status|on|off> [<Spieler>]"));
		return true;
	}
	
	public void _pvp_list_(CommandSender sender) {
		
		String str = "";
		List<String> a = new ArrayList<String>();
		int i = 0;
		
		for(Entry<String, Object> e : this.plugin.pvp.getMap().entrySet()) {
			
			Boolean onoff = false;
			
			try { onoff = (Boolean)e.getValue(); } catch (Exception ex) { ex.printStackTrace(); e.setValue(false); }
			
			if(onoff) {
				
				a.add(e.getKey());
			}
		}
		
		for (String s : a) {
			
			i++;
			String temps = "";
			if(Bukkit.getServer().getOnlineMode()) { temps = Bukkit.getServer().getOfflinePlayer(UUID.fromString(s)).getName(); } else { temps = s; }
			if(i < a.toArray().length) { str = str + ChatColor.GRAY + temps + ChatColor.GREEN + ", "; } else { str = str + ChatColor.GRAY + temps; }
		}
		
		if(a.toArray().length == 0) {
			
			sender.sendMessage(ChatColor.GREEN + "Es haben zurzeit " + ChatColor.GRAY + "keine" + ChatColor.GREEN + " Spieler ihr PvP aktiviert.");
		}
		
		if(a.toArray().length == 1) {
			
			sender.sendMessage(ChatColor.GREEN + "Es hat zurzeit " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler sein PvP aktiviert:");
			sender.sendMessage(str);
		}
		
		if(a.toArray().length >= 2) {
			
			sender.sendMessage(ChatColor.GREEN + "Es haben zurzeit " + ChatColor.GRAY + a.toArray().length + ChatColor.GREEN + " Spieler ihr PvP aktiviert:");
			sender.sendMessage(str);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void _pvp_status_(CommandSender sender, String[] arg) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.pvp.getMap().containsKey(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
				
				String onoff = "";
				if((Boolean)this.plugin.pvp.getMap().get(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) { onoff = "aktiviert"; } else { onoff = "deaktiviert"; }
				sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " hat sein PvP zurzeit "  + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
				return;
			}
			
			this.plugin.pvp.getMap().put(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString(), false);
			sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " hat sein PvP zurzeit "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
			return;
		}
		
		if(this.plugin.pvp.getMap().containsKey(arg[1])) {
			
			String onoff = "";
			if((Boolean)this.plugin.pvp.getMap().get(arg[1])) { onoff = "aktiviert"; } else { onoff = "deaktiviert"; }
			sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " hat sein PvP zurzeit "  + ChatColor.GRAY + onoff + ChatColor.GREEN + ".");
			return;
		}
			
		this.plugin.pvp.getMap().put(arg[1], false);
		sender.sendMessage(ChatColor.GREEN + "Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " hat sein PvP zurzeit "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
		return;
	}
	
	@SuppressWarnings("deprecation")
	public void _pvp_on_(CommandSender sender, String[] arg) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.pvp.getMap().containsKey(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
				
				if(!(Boolean)this.plugin.pvp.getMap().get(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
					
					this.plugin.pvp.getMap().put(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString(), true);
					this.plugin.pvp.Save();
					sender.sendMessage(ChatColor.GREEN + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
					return;
				} else {
					
					sender.sendMessage(ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits "  + ChatColor.GRAY + "aktiviert" + ChatColor.RED + ".");
					return;
				}
			}
		}
		
		if(this.plugin.pvp.getMap().containsKey(arg[1])) {
			
			if(!(Boolean)this.plugin.pvp.getMap().get(arg[1])) {
				
				this.plugin.pvp.getMap().put(arg[1], true);
				this.plugin.pvp.Save();
				sender.sendMessage(ChatColor.GREEN + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde "  + ChatColor.GRAY + "aktiviert" + ChatColor.GREEN + ".");
				return;
			} else {
				
				sender.sendMessage(ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits "  + ChatColor.GRAY + "aktiviert" + ChatColor.RED + ".");
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void _pvp_off_(CommandSender sender, String[] arg) {
		
		if(Bukkit.getOnlineMode()) {
			
			if(this.plugin.pvp.getMap().containsKey(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
				
				if((Boolean)this.plugin.pvp.getMap().get(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString())) {
					
					this.plugin.pvp.getMap().put(Bukkit.getServer().getOfflinePlayer(arg[1]).getUniqueId().toString(), false);
					this.plugin.pvp.Save();
					sender.sendMessage(ChatColor.GREEN + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
					return;
				} else {
					
					sender.sendMessage(ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits "  + ChatColor.GRAY + "deaktiviert" + ChatColor.RED + ".");
					return;
				}
			}
		}
		
		if(this.plugin.pvp.getMap().containsKey(arg[1])) {
			
			if((Boolean)this.plugin.pvp.getMap().get(arg[1])) {
				
				this.plugin.pvp.getMap().put(arg[1], false);
				this.plugin.pvp.Save();
				sender.sendMessage(ChatColor.GREEN + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " wurde "  + ChatColor.GRAY + "deaktiviert" + ChatColor.GREEN + ".");
				return;
			} else {
				
				sender.sendMessage(ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + arg[1] + ChatColor.RED + " ist bereits "  + ChatColor.GRAY + "deaktiviert" + ChatColor.RED + ".");
				return;
			}
		}
	}
}
