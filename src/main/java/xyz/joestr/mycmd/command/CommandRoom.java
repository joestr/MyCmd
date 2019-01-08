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
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		if(commandSender instanceof Player) {
			
			//Player
			Player player = (Player)commandSender;
			
			if(!player.hasPermission("mycmd.command.room") && !player.hasPermission("mycmd.command.room.bypass")) {
				
				this.plugin.noPermissionMessage(player);
				return true;
			}
			
			if(args.length == 0) {
				
				if(player.hasPermission("mycmd.command.room")) {
					
					this.plugin.commandOverview(player, "Raum-System",
							new String[] {
									"Einen Raum erstellen",
									"Einem Raum beitreten",
									"In Einem Raum schreiben",
									"Einen Raum verlassen",
									"Einen Raumoperator ernennen",
									"Einen Raumoperator zurücksetzen",
									"Ein Raumteilnehmer entfernen",
									"Das Passwort für den Raum setzen",
									"Einen Raum löschen",
									"Alle Räume auflisten",
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
									"/pvp info <Name>"
							}
					);
					
					return true;
				}
			}
			
			// /room [0]
			if(args.length == 1) {
				
				if(!player.hasPermission("mycmd.command.room")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.room");
					return true;
				}
				
				// /room list
				if(args[0].equalsIgnoreCase("list")) {
					
					player.sendMessage(this.plugin.pluginPrefix + 
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
					player.sendMessage(this.plugin.pluginPrefix + rooms);
					
					return true;
				}
			}
			
			// /room [0] [1]
			if(args.length == 2) {
				
				if(!player.hasPermission("mycmd.command.room")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.room");
					return true;
				}
				
				// /room create <Name>
				if(args[0].equalsIgnoreCase("create")) {
					
					if(this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert bereits.");
						return true;
					}
					
					Room r = new Room(
							args[1],
							this.plugin.config.getMap().get("room-chat").toString(),
							this.plugin.config.getMap().get("room-info").toString()
					);
					
					r.getOperators().add(player);
					
					r.roomMessage(
							ChatColor.GREEN +
							"Raum wurde von " +
							ChatColor.GRAY + player.getName() + ChatColor.GREEN + 
							" erstellt."
					);
					
					this.plugin.rooms.getMap().put(args[1], r);
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room join <Name>
				if(args[0].equalsIgnoreCase("join")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					if(r.getPassword() != "") {
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " hat ein Passwort.");
						return true;
					}
					
					if(r.getOperators().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " Operator.");
						return true;
					}
					
					if(r.getMembers().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " bereits Mitglied.");
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
				if(args[0].equalsIgnoreCase("leave")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					if(r.getOperators().contains(player) && r.getOperators().size() < 2) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " einziger Operator.");
						return true;
					}
					
					if(!r.getMembers().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " nicht Mitglied.");
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
				if(args[0].equalsIgnoreCase("info")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Raum: " + ChatColor.GRAY + args[1]);
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Passwort: " + (!r.getPassword().equals("") ? ChatColor.GREEN + "Ja" : ChatColor.RED + "Nein"));
					
					String operators = "";
					for(OfflinePlayer p : r.getOperators()) {
						
						operators += ChatColor.DARK_RED + p.getName() + ChatColor.GREEN + ", ";
					}
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Operatoren: " + operators);
					
					String members = "";
					for(OfflinePlayer p : r.getMembers()) {
						
						members += ChatColor.DARK_GREEN + p.getName() + ChatColor.GREEN + ", ";
					}
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Mitglieder: " + members);
					
					return true;
				}
				
				// /room delete <Name>
				if(args[0].equalsIgnoreCase("delete")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.roomMessage(
							ChatColor.GRAY + player.getName() + ChatColor.GREEN + 
							" hat den Raum gelöscht."
					);
					
					this.plugin.rooms.getMap().remove(args[1]);
					this.plugin.rooms.Save();
					
					return true;
				}
			}
			
			// /room [0] [1] [2]
			if(args.length == 3) {
				
				if(!player.hasPermission("mycmd.command.room")) {
					
					this.plugin.noPermissionMessage(player, "mycmd.command.room");
					return true;
				}
				
				// /room create <Name> [<Password>]
				if(args[0].equalsIgnoreCase("create")) {
					
					if(this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert bereits.");
						return true;
					}
					
					Room r = new Room(
							args[1],
							args[2],
							this.plugin.config.getMap().get("room-chat").toString(),
							this.plugin.config.getMap().get("room-info").toString()
					);
					
					r.getOperators().add(player);
					
					player.sendMessage(this.plugin.pluginPrefix + ChatColor.GREEN + "Raum " + ChatColor.GRAY + args[1] + ChatColor.GREEN + " erfolgreich erstellt.");
					
					this.plugin.rooms.getMap().put(args[1], r);
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room join <Name> [<Password>]
				if(args[0].equalsIgnoreCase("join")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					if(!r.getPassword().equals(args[2])) {
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Passwort für den Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " falsch.");
						return true;
					}
					
					if(r.getOperators().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " Operator.");
						return true;
					}
					
					if(r.getMembers().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " bereits Mitglied.");
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
				
				// /room op <Name> <Spieler>
				if(args[0].equalsIgnoreCase("op")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.getMembers().remove(Bukkit.getServer().getOfflinePlayer(args[2]));
					r.getOperators().add(Bukkit.getServer().getOfflinePlayer(args[2]));
					
					r.roomMessage(
							ChatColor.GRAY + args[2] + ChatColor.GREEN + 
							" ist im Raum nun Operator."
					);
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room deop <Name> <Spieler>
				if(args[0].equalsIgnoreCase("deop")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.getOperators().remove(Bukkit.getServer().getOfflinePlayer(args[2]));
					r.getMembers().add(Bukkit.getServer().getOfflinePlayer(args[2]));
					
					r.roomMessage(
							ChatColor.GRAY + args[2] + ChatColor.RED + 
							" ist im Raum kein Operator mehr."
					);
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room kick <Name> <Spieler>
				if(args[0].equalsIgnoreCase("kick")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					if(!r.getOperators().contains(Bukkit.getServer().getOfflinePlayer(args[2])) && !r.getMembers().contains(Bukkit.getServer().getOfflinePlayer(args[2]))) {
						
						player.sendMessage(
								this.plugin.pluginPrefix +
								ChatColor.RED + "Im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED +
								" gibt es keinen Teilnehmer mit dem Namen " +
								ChatColor.GRAY + args[2] + ChatColor.RED + "."
						);
						return true;
					}
					
					r.roomMessage(
							ChatColor.GRAY + args[2] + ChatColor.RED + 
							" wurde aus dem Raum geworfen."
					);
					
					r.getOperators().remove(Bukkit.getServer().getOfflinePlayer(args[2]));
					r.getMembers().remove(Bukkit.getServer().getOfflinePlayer(args[2]));
					
					this.plugin.rooms.Save();
					
					return true;
				}
				
				// /room password <Name> <Passwort>
				if(args[0].equalsIgnoreCase("password")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					if(!r.getOperators().contains(player)) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Du Bist im Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " nicht Operator.");
						return true;
					}
					
					r.setPassword(args[2]);
					
					r.roomMessage(
							ChatColor.GREEN +
							"Das Passwort vom Raum wurde gesetzt."
					);
					
					this.plugin.rooms.Save();
					
					return true;
				}
			}
				
			
			// /room [0] [1] [2] [...]
			if(args.length >= 3) {
				
				// /room write <Name> <Nachricht ...>
				if(args[0].equalsIgnoreCase("write")) {
					
					if(!this.plugin.rooms.getMap().keySet().contains(args[1])) {
						
						player.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Raum " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
						return true;
					}
					
					String message = "";
					for(int i = 2; i < args.length; i++) {
						message += args[i];
					}
					
					Room r = (Room) this.plugin.rooms.getMap().get(args[1]);
					
					r.writeMessage(player, message);
					
					this.plugin.rooms.getMap().put(args[1], r);
					this.plugin.rooms.Save();
					
					return true;
				}
			}
			
			if(player.hasPermission("mycmd.command.room")) {
				
				this.plugin.commandOverview(player, "Raum-System",
						new String[] {
								"Einen Raum erstellen",
								"Einem Raum beitreten",
								"In Einem Raum schreiben",
								"Einen Raum verlassen",
								"Einen Raumoperator ernennen",
								"Einen Raumoperator zurücksetzen",
								"Ein Raumteilnehmer entfernen",
								"Das Passwort für den Raum setzen",
								"Einen Raum löschen",
								"Alle Räume auflisten",
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
								"/pvp info <Name>"
						}
				);
				
				return true;
			}
		}
		
		//Console
		if(args.length == 0) {
			commandSender.sendMessage(this.plugin.pluginPrefix + ChatColor.RED + "Nur Spieler können das Raum-System nutzen.");
		}
		
		this.plugin.usageMessage(commandSender, "/room");
		return true;
	}
}
