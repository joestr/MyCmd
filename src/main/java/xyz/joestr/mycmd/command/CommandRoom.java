package xyz.joestr.mycmd.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.joestr.mycmd.MyCmd;
import xyz.joestr.mycmd.util.Room;

public class CommandRoom implements CommandExecutor {
	
	private MyCmd plugin;
	
	public CommandRoom(MyCmd mycmd) {
		
		this.plugin = mycmd;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		
		if(sender instanceof Player) {
			
			//Player
			Player player = (Player)sender;
			
			if(!player.hasPermission("mycmd.command.pvp") && !player.hasPermission("mycmd.command.pvp.other")) {
				
				player.sendMessage(this.plugin.pluginPrefix + this.plugin.noPermissionMessage());
				return true;
			}
			
			if(arg.length == 0) {
				
				if(player.hasPermission("mycmd.command.room")) {
					
					this.plugin.commandOverview(player, "Raum-System",
							new String[] {
									"Einen Raum erstellen",
									"Einem Raum beitreten",
									"In Einem Raum schreiben",
									"Einen Raum verlassen",
									"Einen Raumoperator ernennen",
									"Einen Raumoperator zurücksetzen",
									"Ein Raummitglied entfernen",
									"Das Passwort für den Raum setzen",
									"Einen Raum löschen",
									"Räume auflisten",
									"Informationen über einen Raum"
							},
							new String[] {
									"suggest_command",
									"suggest_command",
									"suggest_command",
									"suggest_command",
									"suggest_command",
									"suggest_command",
									"suggest_command",
									"suggest_command",
									"suggest_command",
									"run_command",
									"suggest_command"
							},
							new String[] {
									"/room create ",
									"/room join ",
									"/room write ",
									"/room leave ",
									"/room op ",
									"/room deop ",
									"/room kick ",
									"/room password ",
									"/room delete",
									"/room list",
									"/pvp info "
							},
							new String[] {
									"/room create <Name> [<Passwort>]",
									"/room join <Name> [<Passwort>]",
									"/room write <Name> <Nachricht ...>",
									"/room leave <Name>",
									"/room op <Name> <Spieler>",
									"/room deop <Name> <Spieler>",
									"/room kick <Name> <Spieler>",
									"/room password <Name> <Spieler>",
									"/room delete <Name>",
									"/room list",
									"/pvp info <Name> [<Passwort>]"
							}
					);
					
					return true;
				}
			}
			
			// /room [0]
			if(arg.length == 1) {
				
				if(!player.hasPermission("mycmd.command.room")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.room");
					return true;
				}
				
				// /room list
				if(arg[0].equalsIgnoreCase("list")) {
					
					player.sendMessage(
							ChatColor.GREEN +
							"Räume: (" + ChatColor.DARK_RED + "Operator" + ChatColor.GREEN + ", " +
							ChatColor.DARK_GREEN + "Mitglied" + ChatColor.GREEN + "):"
					);
					
					String rooms = "";
					
					for(String s : this.plugin.rooms.getMap().keySet()) {
						
						Room r = (Room) this.plugin.rooms.getMap().get(s);
						
						if(r.getOperators().contains(player)) {
							rooms += ChatColor.DARK_RED + s + ChatColor.WHITE;
						} else if(r.getMembers().contains(player)) {
							rooms += ChatColor.DARK_GREEN + s + ChatColor.WHITE;
						} else {
							rooms += ChatColor.GREEN + s;
						}
						
						rooms += ChatColor.GREEN + ", ";
					}
					
					player.sendMessage(rooms);
					return true;
				}
			}
			
			if(arg.length == 2) {
				
				if(!player.hasPermission("mycmd.command.room")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.room");
					return true;
				}
				
				// /room create <Name>
				if(arg[0].equalsIgnoreCase("create")) {
					
					if(this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert bereits.");
						return true;
					}
					
					Room r = new Room(arg[1]);
					
					r.getOperators().add(player);
					
					r.roomMessage(
							ChatColor.GREEN +
							"Raum " + ChatColor.GRAY + arg[1] + ChatColor.GREEN +
							" wurde von " + ChatColor.GRAY + player.getName() + ChatColor.GREEN + 
							" erfolgreich erstellt."
					);
					
					this.plugin.rooms.getMap().put(arg[1], r);
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room join <Name>
				if(arg[0].equalsIgnoreCase("join")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					if(r.getPassword() != "") {
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " hat ein Passwort.");
						return true;
					}
					
					if(r.getOperators().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " Operator.");
						return true;
					}
					
					if(r.getMembers().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " bereits Mitglied.");
						return true;
					}
					
					r.getMembers().add(player);
					
					r.roomMessage(
							ChatColor.GRAY + player.getName() + ChatColor.GREEN + 
							" ist dem Raum beigetreten."
					);
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room leave <Name>
				if(arg[0].equalsIgnoreCase("leave")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					if(r.getOperators().contains(player) && r.getOperators().size() < 2) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " einziger Operator.");
						return true;
					}
					
					if(!r.getMembers().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " nicht Mitglied.");
						return true;
					}
					
					r.roomMessage(
							ChatColor.GRAY + player.getName() + ChatColor.GREEN + 
							" hat den Raum verlassen."
					);
					
					r.getOperators().remove(player);
					r.getMembers().remove(player);
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room info <Name>
				if(arg[0].equalsIgnoreCase("info")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					player.sendMessage(ChatColor.GREEN + "Raum: " + ChatColor.GRAY + arg[1]);
					player.sendMessage(ChatColor.GREEN + "Passwort: " + (!r.getPassword().equals("") ? ChatColor.GREEN + "Ja" : ChatColor.RED + "Nein"));
					
					String operators = "";
					for(OfflinePlayer p : r.getOperators()) {
						
						operators += ChatColor.DARK_RED + p.getName() + ChatColor.GREEN + ", ";
					}
					player.sendMessage(ChatColor.GREEN + "Operatoren: " + operators);
					
					String members = "";
					for(OfflinePlayer p : r.getMembers()) {
						
						members += ChatColor.DARK_GREEN + p.getName() + ChatColor.GREEN + ", ";
					}
					player.sendMessage(ChatColor.GREEN + "Mitglieder: " + members);
					
					return true;
				}
				
				// /room delete <Name>
				if(arg[0].equalsIgnoreCase("delete")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.roomMessage(
							ChatColor.GRAY + player.getName() + ChatColor.GREEN + 
							" hat den Raum gelöscht."
					);
					
					this.plugin.rooms.getMap().remove(arg[1]);
					this.plugin.rooms.Save();
					
					return true;
				}
			}
			// /room 1 2
			
			if(arg.length == 3) {
				
				if(!player.hasPermission("mycmd.command.room")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.room");
					return true;
				}
				
				// /room create <Name> [<Password>]
				if(arg[0].equalsIgnoreCase("create")) {
					
					if(this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert bereits.");
						return true;
					}
					
					Room r = new Room(arg[1], arg[2]);
					
					r.getOperators().add(player);
					
					player.sendMessage(ChatColor.GREEN + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.GREEN + " erfolgreich erstellt.");
					
					this.plugin.rooms.getMap().put(arg[1], r);
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room join <Name> [<Password>]
				if(arg[0].equalsIgnoreCase("join")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					if(!r.getPassword().equals(arg[2])) {
						player.sendMessage(ChatColor.RED + "Passwort für den Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " falsch.");
						return true;
					}
					
					if(r.getOperators().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " Operator.");
						return true;
					}
					
					if(r.getMembers().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " bereits Mitglied.");
						return true;
					}
					
					r.getMembers().add(player);
					
					r.roomMessage(
							ChatColor.GRAY + player.getName() + ChatColor.GREEN + 
							" ist dem Raum beigetreten."
					);
					
					this.plugin.rooms.Save();
					
					return true;
				}
			}
				
			
			if(arg.length >= 3) {
				
				if(!player.hasPermission("mycmd.command.room")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.room");
					return true;
				}
				
				// /room op <Name> <Spieler>
				if(arg[0].equalsIgnoreCase("op")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.getMembers().remove(Bukkit.getServer().getOfflinePlayer(arg[2]));
					r.getOperators().add(Bukkit.getServer().getOfflinePlayer(arg[2]));
					
					r.roomMessage(
							ChatColor.GRAY + arg[2] + ChatColor.GREEN + 
							" ist im Raum " +
							ChatColor.GRAY + arg[1] + ChatColor.GREEN +
							" nun Operator."
					);
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room deop <Name> <Spieler>
				if(arg[0].equalsIgnoreCase("deop")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.getOperators().remove(Bukkit.getServer().getOfflinePlayer(arg[2]));
					r.getMembers().add(Bukkit.getServer().getOfflinePlayer(arg[2]));
					
					r.roomMessage(
							ChatColor.GRAY + arg[2] + ChatColor.RED + 
							" ist im Raum " +
							ChatColor.GRAY + arg[1] + ChatColor.RED +
							" kein Operator mehr."
					);
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room kick <Name> <Spieler>
				if(arg[0].equalsIgnoreCase("kick")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.roomMessage(
							ChatColor.GRAY + arg[2] + ChatColor.RED + 
							" wurde aus dem raum geworfen."
					);
					
					r.getMembers().remove(Bukkit.getServer().getOfflinePlayer(arg[2]));
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room password <Name> <Passwort>
				if(arg[0].equalsIgnoreCase("password")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.setPassword(arg[2]);
					
					r.roomMessage(
							ChatColor.GREEN +
							"Das Passwort vom Raum " +
							ChatColor.GRAY + arg[1] + ChatColor.GREEN +
							"wurde aktualisiert."
					);
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room write <Name> <Nachricht ...>
				if(arg[0].equalsIgnoreCase("write")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(arg[1])) {
						
						player.sendMessage(ChatColor.RED + "Raum " + ChatColor.GRAY + arg[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					String message = "";
					for(int i = 2; i < arg.length; i++) {
						message += arg[i];
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(arg[1]);
					
					r.writeMessage(player, message);
					
					this.plugin.rooms.getMap().put(arg[1], r);
					this.plugin.rooms.Save();
					
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.room")) {
				
				this.plugin.usageMessage(
						player,
						"/room [<create|join|write|leave|delete|list|info|op|deop|kick>] [<Name>] [<Passwort|Spieler|Nachricht ...>]",
						"suggest_command",
						"/room ",
						"/room [<create|join|write|leave|delete|list|info|op|deop|kick>] [<Name>] [<Passwort|Spieler|Nachricht ...>]"
				);
				return true;
			}
		}
		
		//Console
		sender.sendMessage("Nix da!");
		
		sender.sendMessage(this.plugin.pluginPrefix + this.plugin.usageMessage("/room"));
		return true;
	}
}
