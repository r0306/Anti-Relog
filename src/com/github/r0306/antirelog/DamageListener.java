package com.github.r0306.antirelog;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.heroes.api.HeroAttackDamageCause;
import com.herocraftonline.heroes.characters.Hero;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import static com.sk89q.worldguard.bukkit.BukkitUtil.*;

public class DamageListener implements Listener{
	private antirelog plugin;		
	public DamageListener (antirelog plugin) {
		this.plugin = plugin;
	}
		static Player realplayer1;
		static Player realplayer2;
		boolean playeradd = false;
		private MyTask task;
		private WaitDuration duration;
		public static boolean isDamaged = false;
		public static boolean timerTask = true;
		static Set<Player> Alreadydmg = Collections.newSetFromMap(new WeakHashMap<Player,Boolean>());
		//static Set<Player> Damagelist = Collections.newSetFromMap(new WeakHashMap<Player,Boolean>());
		public static HashMap<Player, Long> Damagelist = new HashMap<Player, Long>();
		public static Set<Integer> tasks= Collections.newSetFromMap(new WeakHashMap<Integer, Boolean>());
		

@EventHandler (priority = EventPriority.HIGHEST)
public void damage (EntityDamageEvent event) {

	Heroes = Heroes.skills;
	if (event.isCancelled() == true|| event.getDamage() <= 0) {
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
        if(edbeEvent.getDamager() instanceof Player && victim instanceof Player) {
    		EntityDamageByEntityEvent edbeEvent1 = (EntityDamageByEntityEvent) event;
        	Entity attacker = edbeEvent1.getDamager();
            Player player = (Player) victim;
            realplayer1 = player;
            Player player2 = (Player) attacker;
            realplayer2 = player2;
            if (!(Damagelist.containsValue(realplayer1))) {
                Calendar c = Calendar.getInstance();
                long start = c.getTimeInMillis() / 1000;
                long endTime = start + plugin.getConfig().getInt("StunDuration");
                Damagelist.put(realplayer1, endTime);
            }
            else if (Damagelist.containsValue(realplayer1)) {
                Calendar c = Calendar.getInstance();
                long start = c.getTimeInMillis() / 1000;
                long endTime = start + plugin.getConfig().getInt("StunDuration");
                Damagelist.put(realplayer1, endTime);
            }
            if (!(Damagelist.containsValue(realplayer2))) {
                Calendar c = Calendar.getInstance();
                long start = c.getTimeInMillis() / 1000;
                long endTime = start + plugin.getConfig().getInt("StunDuration");
                Damagelist.put(realplayer2, endTime);
            }
            else if (Damagelist.containsValue(realplayer2)) {
                Calendar c = Calendar.getInstance();
                long start = c.getTimeInMillis() / 1000;
                long endTime = start + plugin.getConfig().getInt("StunDuration");
                Damagelist.put(realplayer2, endTime);
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
