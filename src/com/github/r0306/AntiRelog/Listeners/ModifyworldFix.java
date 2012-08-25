package com.github.r0306.AntiRelog.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import com.github.r0306.AntiRelog.NPC.AntiRelogNPC;
import com.github.r0306.AntiRelog.Storage.DataBase;

public class ModifyworldFix implements Listener
{

	@EventHandler (ignoreCancelled = false, priority = EventPriority.HIGHEST)
	public void onDamageEntity(EntityDamageEvent event)
	{
		
		Entity entity = event.getEntity();
		
		if (DataBase.isNPC(entity))
		{
			
			event.setCancelled(false);
						
		}
		
	}
	
	@EventHandler (ignoreCancelled = false, priority = EventPriority.HIGHEST)
	public void onDamageEntityPlayer(EntityDamageByEntityEvent event)
	{

		Entity entity = event.getEntity();
		Entity attacker = event.getDamager();
		
		if (DataBase.isNPC(attacker))
		{

			event.setCancelled(false);

			event.setDamage((int) AntiRelogNPC.calculateDamage(DataBase.getNPCByEntity(attacker), event.getEntity(), null));
			
		}
		
		else if (DataBase.isNPC(entity))
		{
			
			event.setCancelled(false);
			
			event.setDamage((int) AntiRelogNPC.calculateDamage(null, event.getEntity(), attacker));
			
		}
				
	}
	
}
