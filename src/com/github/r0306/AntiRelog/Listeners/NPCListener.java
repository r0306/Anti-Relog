package com.github.r0306.AntiRelog.Listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Configuration;

public class NPCListener implements Listener
{

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		
		Entity entity = event.getEntity();
		Entity attacker = event.getDamager();
				
		if (DataBase.isNPC(entity))
		{
				
			if (event.getDamage() > 0)
			{
			
				entity.setVelocity(attacker.getLocation().getDirection().normalize().multiply(1));
			
			}
				
		}
				
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onDeath(EntityDeathEvent event)
	{
		
		Entity entity = event.getEntity();
		
		if (DataBase.isNPC(entity))
		{
			
			HumanEntity player = (HumanEntity) entity;
			
			event.getDrops().clear();
			
			Set<Integer> items = new HashSet<Integer>();
			
			if (Configuration.dropItemsEnabled())
			{
				
				LogPrevention.dropItems(player);
				items.add(0);
				
			}
			
			if (Configuration.dropArmorEnabled())
			{
				
				LogPrevention.dropArmor(player);
				items.add(1);
				
			}
			
			if (Configuration.dropExpEnabled())
			{
				
				LogPrevention.dropExp(DataBase.getNPCByEntity(entity));
				items.add(2);
				
			}
			
			DataBase.addToLoginQueue(player.getName(), items);	

		}
		
	}
	
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		
		LivingEntity entity = (LivingEntity) event.getEntity();
		
		if (DataBase.isNPC(event.getEntity()))
		{
			
			DataBase.removeNPC(DataBase.getNPCByEntity(entity).getName());
			
		}
		
		 if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent)
		 {
			
			 EntityDamageByEntityEvent dEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
			
			 if (DataBase.isNPC(dEvent.getDamager()))
			 {
				 
				 DataBase.removeNPC(DataBase.getNPCByEntity(dEvent.getDamager()).getName());
				 
			 }
			 
		 }
	
	}
			
}
