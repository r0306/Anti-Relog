package com.github.r0306.antirelog;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class DamageListener implements Listener{
	private antirelog plugin;		
	public DamageListener (antirelog plugin) {
		this.plugin = plugin;
	}
	
		boolean playeradd = false;
		public static boolean isDamaged = false;
		static Set<Player> Damagelist = Collections.newSetFromMap(new WeakHashMap<Player,Boolean>());

@EventHandler
public void damage (EntityDamageEvent event) {
	Entity victim = event.getEntity();
	if (event instanceof EntityDamageByEntityEvent) {
		EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) event;
        if(edbeEvent.getDamager() instanceof Player && victim instanceof Player) {
    		EntityDamageByEntityEvent edbeEvent1 = (EntityDamageByEntityEvent) event;
        	Entity attacker = edbeEvent1.getDamager();
            final Player player = (Player) victim;
            final Player player2 = (Player) attacker;
            Damagelist.add(player);
            Damagelist.add(player2);
        		isDamaged = true;
				int delay = plugin.getConfig().getInt("StunDuration") * 20;
        		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
	        			Damagelist.remove(player);
	        			Damagelist.remove(player2);
						isDamaged = false;
						playeradd = true;
					}
        		}, delay);


        		
        		}
	      }
	 }
}


