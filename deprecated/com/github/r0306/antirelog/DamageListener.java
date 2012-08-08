package com.github.r0306.antirelog;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.WeakHashMap;

import net.citizensnpcs.api.CitizensManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;


public class DamageListener implements Listener {
	private antirelog plugin;		
	public DamageListener (antirelog plugin) {
		this.plugin = plugin;
	}
		boolean playeradd = false;
		public static boolean isDamaged = false;
		public static boolean timerTask = true;
		static Set<Player> Alreadydmg = Collections.newSetFromMap(new WeakHashMap<Player,Boolean>());
		//static Set<Player> Damagelist = Collections.newSetFromMap(new WeakHashMap<Player,Boolean>());
		public static HashMap<Player, Long> Damagelist = new HashMap<Player, Long>();
		public static Set<Integer> tasks= Collections.newSetFromMap(new WeakHashMap<Integer, Boolean>());
		public static HashMap<Player, Integer> timer = new HashMap<Player, Integer>();

		

@EventHandler (priority = EventPriority.HIGHEST)
public void damage (EntityDamageEvent event) {
	Player realplayer1 = null;
	Player realplayer2 = null;
	if (event.isCancelled() == true && event.getCause() != DamageCause.CUSTOM || event.getDamage() < 0 && event.getCause() != DamageCause.CUSTOM) {
	 event.setCancelled(true);
	 return;
	}
	else if (antirelog.Citizens = null != null) { 
		antirelog.Citizens = false;
	}
	else if (antirelog.Citizens == true ) {
		if (CitizensManager.isNPC(event.getEntity()) == true) {
			event.setCancelled(true);
		}
	} else {
	Entity victim = event.getEntity();
	if (event instanceof EntityDamageByEntityEvent) {
		EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) event;
        if(edbeEvent.getDamager() instanceof Player && victim instanceof Player || edbeEvent.getDamager() instanceof Arrow && victim instanceof Player) {
    		EntityDamageByEntityEvent edbeEvent1 = (EntityDamageByEntityEvent) event;
        	Entity attacker = edbeEvent1.getDamager();
            Player player = (Player) victim;
            realplayer1 = player;
            final Player p1 = realplayer1;
            Player player2 = null;
        	if (attacker instanceof Arrow) {
        		Arrow arrow = (Arrow) edbeEvent1.getDamager();
        		if (arrow.getShooter() instanceof Player) {
        			player2 = (Player) arrow.getShooter();
        		} else {
        			return;
        		}
        	} else {
        		player2 = (Player) edbeEvent1.getDamager();
        	}
            realplayer2 = player2;
            final Player p2 = realplayer2;
            if (!(Damagelist.containsKey(realplayer1))) {
        	    if (!realplayer1.hasPermission("antirelog.pvpbypass") || !realplayer1.isOp()) {
                Calendar c = Calendar.getInstance();
                long start = c.getTimeInMillis() / 1000;
                long endTime = start + plugin.getConfig().getInt("StunDuration");
                Damagelist.put(realplayer1, endTime);
                realplayer1.sendMessage(ChatColor.RED + "[AntiRelog] "  + plugin.getConfig().getString("Tag-Message"));
                int id = 0;
                id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                	   public void run() {
                	       p1.sendMessage(ChatColor.GREEN + "[AntiRelog] " + plugin.getConfig().getString("UnTag-Message"));
                	       timer.remove(p1);
                	       Damagelist.remove(p1);
                	   }
                	}, plugin.getConfig().getInt("StunDuration") * 20L);
                timer.put(p1, id);
        	    }
            }
            else if (Damagelist.containsKey(realplayer1)) {
        	    if (!realplayer1.hasPermission("antirelog.pvpbypass") || !realplayer1.isOp()) {
                Calendar c = Calendar.getInstance();
                long start = c.getTimeInMillis() / 1000;
                long endTime = start + plugin.getConfig().getInt("StunDuration");
                Damagelist.put(realplayer1, endTime);
                plugin.getServer().getScheduler().cancelTask(timer.get(realplayer1));
                int id = 0;
                id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                	   public void run() {
                	       p1.sendMessage(ChatColor.GREEN + "[AntiRelog] " + plugin.getConfig().getString("UnTag-Message"));
                	       timer.remove(p1);
                	       Damagelist.remove(p1);
                	   }
                	}, plugin.getConfig().getInt("StunDuration") * 20L);
                timer.put(p1, id);
        	    }
            }
            if (!(Damagelist.containsKey(realplayer2))) {
        	    if (!realplayer2.hasPermission("antirelog.pvpbypass") || !realplayer2.isOp()) {
                Calendar c = Calendar.getInstance();
                long start = c.getTimeInMillis() / 1000;
                long endTime = start + plugin.getConfig().getInt("StunDuration");
                Damagelist.put(realplayer2, endTime);
                realplayer2.sendMessage(ChatColor.RED + "[AntiRelog] "  + plugin.getConfig().getString("Tag-Message"));
                int id = 0;
                id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                	   public void run() {
                	       p2.sendMessage(ChatColor.GREEN + "[AntiRelog] " + plugin.getConfig().getString("UnTag-Message"));
                	       timer.remove(p2);
                	       Damagelist.remove(p2);
                	   }
                	}, plugin.getConfig().getInt("StunDuration") * 20L);
                timer.put(p2, id);
        	    }
            }
            else if (Damagelist.containsKey(realplayer2)) {
        	    if (!realplayer2.hasPermission("antirelog.pvpbypass") || !realplayer2.isOp()) {
                Calendar c = Calendar.getInstance();
                long start = c.getTimeInMillis() / 1000;
                long endTime = start + plugin.getConfig().getInt("StunDuration");
                Damagelist.put(realplayer2, endTime);
                plugin.getServer().getScheduler().cancelTask(timer.get(realplayer2));
                int id = 0;
                id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                	   public void run() {
                	       p2.sendMessage(ChatColor.GREEN + "[AntiRelog] " + plugin.getConfig().getString("UnTag-Message"));
                	       timer.remove(p2);
                	       Damagelist.remove(p2);
                	   }
                	}, plugin.getConfig().getInt("StunDuration") * 20L);
                timer.put(p2, id);
        	    }
            }
           
        }
        else {
        	if (plugin.getConfig().getString("MobLogger").equalsIgnoreCase("on") || plugin.getConfig().getString("MobLogger").equalsIgnoreCase("true")) {
        		if ((plugin.getConfig().getString("PassiveMobLogger").equalsIgnoreCase("off") || plugin.getConfig().getString("PassiveMobLogger").equalsIgnoreCase("off")) && (victim instanceof Chicken || victim instanceof Cow || victim instanceof Ocelot || victim instanceof Squid || victim instanceof Sheep || victim instanceof Villager || victim instanceof Pig || victim instanceof MushroomCow)) {
        			return;
        		}

        		if (victim instanceof Player || edbeEvent.getDamager() instanceof Player || (edbeEvent.getDamager() instanceof Arrow && victim instanceof Player)) {
        			if (victim instanceof Player) {
        				Player victim2 = (Player) edbeEvent.getEntity();
        				realplayer1 = victim2;
        			}
        			else if (edbeEvent.getDamager() instanceof Player) {
        				Player damager = (Player) edbeEvent.getDamager();
        				realplayer1 = damager;
        			}
        			else if (edbeEvent.getDamager() instanceof Arrow) {
                		Arrow arrow = (Arrow) edbeEvent.getDamager();
                		if (arrow.getShooter() instanceof Player || arrow.getShooter() instanceof Skeleton && victim instanceof Player) {
                			if (arrow.getShooter() instanceof Player) {
                				realplayer1 = (Player) arrow.getShooter();
                			} else {
                				realplayer1 = (Player) victim;
                			}
                		}
        			}
            		final Player p1 = realplayer1;
                    if (!(Damagelist.containsKey(realplayer1))) {
                	    if (!realplayer1.hasPermission("antirelog.pvpbypass") || !realplayer1.isOp()) {
                            Calendar c = Calendar.getInstance();
                            long start = c.getTimeInMillis() / 1000;
                            long endTime = start + plugin.getConfig().getInt("StunDuration");
                            Damagelist.put(realplayer1, endTime);
                            realplayer1.sendMessage(ChatColor.RED + "[AntiRelog] "  + plugin.getConfig().getString("Tag-Message"));
                            int id = 0;
                            id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                            	   public void run() {
                            	       p1.sendMessage(ChatColor.GREEN + "[AntiRelog] " + plugin.getConfig().getString("UnTag-Message"));
                            	       timer.remove(p1);
                            	       Damagelist.remove(p1);
                            	   }
                            	}, plugin.getConfig().getInt("StunDuration") * 20L);
                            timer.put(p1, id);
                    	    }
                    }
                    else if (Damagelist.containsKey(realplayer1)) {
                	    if (!realplayer1.hasPermission("antirelog.pvpbypass") || !realplayer1.isOp()) {
                            Calendar c = Calendar.getInstance();
                            long start = c.getTimeInMillis() / 1000;
                            long endTime = start + plugin.getConfig().getInt("StunDuration");
                            Damagelist.put(realplayer1, endTime);
                            plugin.getServer().getScheduler().cancelTask(timer.get(realplayer1));
                            int id = 0;
                            id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                            	   public void run() {
                            	       p1.sendMessage(ChatColor.GREEN + "[AntiRelog] " + plugin.getConfig().getString("UnTag-Message"));
                            	       timer.remove(p1);
                            	       Damagelist.remove(p1);
                            	   }
                            	}, plugin.getConfig().getInt("StunDuration") * 20L);
                            timer.put(p1, id);
                	    }
                    }
        		}
        	}
        }
	}
	
}
	
}
}
 
/*
			System.out.println("HI");
	        task = new MyTask(plugin);
            if(Damagelist.contains(realplayer1) || Damagelist.contains(realplayer2)) {
            	Alreadydmg.add(realplayer1);
            	Alreadydmg.add(realplayer2);
            	Alreadydmg.toArray();
            } else {
                Damagelist.add(realplayer1);
                Damagelist.add(realplayer2);    
            	if (realplayer1.isOp() || realplayer1.hasPermission("antirelog.pvpbypass")) {
            		Damagelist.remove(realplayer1);
            	}
            	if (realplayer2.isOp() || realplayer2.hasPermission("antirelog.pvpbypass")) {
            		Damagelist.remove(realplayer2);
            	}
    		duration = new WaitDuration(plugin);
            }
       	}

            }
		}
}
	
public void Then() {

	isDamaged = false;
	playeradd = true;
	}
}

*/
