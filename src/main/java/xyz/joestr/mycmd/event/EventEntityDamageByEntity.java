package xyz.joestr.mycmd.event;

import java.util.Date;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SplashPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import xyz.joestr.mycmd.MyCmd;

public class EventEntityDamageByEntity implements Listener {
	
	MyCmd plugin;
	
	public EventEntityDamageByEntity(MyCmd mycmd)
	{
		this.plugin = mycmd;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void OnEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			
			Player o = (Player) event.getEntity();
			Player a = (Player) event.getDamager();
			desideAction(o, a, new Date(), event);
			return;
		}
		
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
			
			Player o = (Player) event.getEntity();
			Arrow arrow = (Arrow) event.getDamager();
			
			if(arrow.getShooter() instanceof Player) {
				
				Player a = (Player) arrow.getShooter();
				desideAction(o, a, new Date(), event);
				return;
			}
		}
		
		if(event.getEntity() instanceof Player && event.getDamager() instanceof SplashPotion) {
			
			Player o = (Player) event.getEntity();
			SplashPotion sp = (SplashPotion) event.getDamager();
			
			if(sp.getShooter() instanceof Player) {
				
				Player a = (Player) sp.getShooter();
				desideAction(o, a, new Date(), event);
				return;
			}
		}
	}
	
	public void desideAction(final Player o, final Player a, final Date d, final EntityDamageByEntityEvent e) {
		
		if(Bukkit.getServer().getOnlineMode()) {
			
			if(this.plugin.pvp.getMap().containsKey(a.getUniqueId().toString())) {
				
				if(!(Boolean)this.plugin.pvp.getMap().get(a.getUniqueId().toString())) {
					
					e.setCancelled(true);
					plugin.sendActionBarToPlayer(a, ChatColor.RED + "Dein PvP ist zurzeit deaktiviert.");
					return;
				}
			} else {
				
				e.setCancelled(true);
				plugin.sendActionBarToPlayer(a, ChatColor.RED + "Dein PvP ist zurzeit deaktiviert.");
				return;
			}
			
			if(this.plugin.pvp.getMap().containsKey(o.getUniqueId().toString())) {
				
				if(!(Boolean)this.plugin.pvp.getMap().get(o.getUniqueId().toString())) {
					
					e.setCancelled(true);
					plugin.sendActionBarToPlayer(a, ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + o.getName() + ChatColor.RED + " ist zurzeit deaktiviert.");
					return;
				}
			} else {
				
				e.setCancelled(true);
				plugin.sendActionBarToPlayer(a, ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + o.getName() + ChatColor.RED + " ist zurzeit deaktiviert.");
				return;
			}
		} else {
		
			if(this.plugin.pvp.getMap().containsKey(a.getName())) {
				
				if(!(Boolean)this.plugin.pvp.getMap().get(a.getName())) {
					
					e.setCancelled(true);
					plugin.sendActionBarToPlayer(a, ChatColor.RED + "Dein PvP ist zurzeit deaktiviert.");
					return;
				}
			} else {
				
				e.setCancelled(true);
				plugin.sendActionBarToPlayer(a, ChatColor.RED + "Dein PvP ist zurzeit deaktiviert.");
				return;
			}
			
			if(this.plugin.pvp.getMap().containsKey(o.getName())) {
				
				if(!(Boolean)this.plugin.pvp.getMap().get(o.getName())) {
					
					e.setCancelled(true);
					plugin.sendActionBarToPlayer(a, ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + o.getName() + ChatColor.RED + " ist zurzeit deaktiviert.");
					return;
				}
			} else {
				
				e.setCancelled(true);
				plugin.sendActionBarToPlayer(a, ChatColor.RED + "PvP vom Spieler " + ChatColor.GRAY + o.getName() + ChatColor.RED + " ist zurzeit deaktiviert.");
				return;
			}
		}
		
		if(!this.plugin.pvpList.containsKey(o.getName())) {
			
			this.plugin.pvpList.put(o.getName(), d);
			plugin.sendActionBarToPlayer(o, ChatColor.GREEN + "PvP-Schutz ist nun " + ChatColor.GRAY + "aktiv" + ChatColor.GREEN + ".");
			
			inPvP(o);
		} else {
			
			this.plugin.pvpList.put(o.getName(), d);
		}
		
		if(!this.plugin.pvpList.containsKey(a.getName())) {
			
			this.plugin.pvpList.put(a.getName(), d);
			plugin.sendActionBarToPlayer(a, ChatColor.GREEN + "PvP-Schutz ist nun " + ChatColor.GRAY + "aktiv" + ChatColor.GREEN + ".");
			
			inPvP(a);
		} else {
			
			this.plugin.pvpList.put(a.getName(), d);
		}
	}
	
	public void countdown(final Player player){ //A method
		
		new BukkitRunnable(){ //BukkitRunnable, not Runnable
		
			int countdown = 10; //Instance variable in our anonymous class to easily hold the countdown value
			
			@Override
			public void run() {
				
				this.cancel();
				
				if(countdown <= 0 || !player.isOnline()){ //countdown is over or player left the server, just two example reasons to exit
					
					this.cancel(); //cancel the repeating task
					return; //exit the method
				}
				
				player.sendMessage(ChatColor.RED + "There are " + countdown + " seconds until timtower gets moderator powers."); //Example usage
				countdown--; //decrement
			}
		}.runTaskTimer(this.plugin, 0, 20); //Repeating task with 0 ticks initial delay, run once per 20 ticks (one second). Make sure you pass a valid instance of your plugin.
	}
	
	public void inPvP(final Player player) {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(EventEntityDamageByEntity.this.plugin.pvpList.get(player.getName()).getTime() + 10000 > new Date().getTime()) {
					
					return;
				}
				
				this.cancel();
				EventEntityDamageByEntity.this.plugin.pvpList.remove(player.getName());
				
				if(player.isOnline()) {
					
					plugin.sendActionBarToPlayer(player, ChatColor.GREEN + "PvP-Schutz ist nun " + ChatColor.GRAY + "inaktiv" + ChatColor.GREEN + ".");
				}
			}
		}.runTaskTimer(this.plugin, 0, 15);
	}
}
