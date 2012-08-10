package com.github.r0306.AntiRelog.Listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.r0306.AntiRelog.Storage.DataBase;

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
			
			LogPrevention.dropItems(player);
			
			LogPrevention.dropArmor(player);
			
			LogPrevention.dropExp(DataBase.getNPCByEntity(player));
			
			DataBase.addToLoginQueue(player.getName(), dropAll());	

		}
		
	}
	
	public Set<Integer> dropAll()
	{
		
		Set<Integer> items = new HashSet<Integer>();
		
		items.add(0);
		items.add(1);
		items.add(2);
		
		return items;
		
	}
		
}
