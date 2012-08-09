package com.github.r0306.AntiRelog.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Configuration;
import com.github.r0306.AntiRelog.Util.Plugin;

public class AntiRelogNPC
{
	
	public static void spawnNPC(Player player)
	{
				
		NPCManager npcHandler = new NPCManager(Plugin.getPlugin());
/*
		HumanNPC humanNPC = (HumanNPC) npcHandler.spawnHumanNPC(player.getName(), player.getLocation());

		humanNPC.getInventory().setContents(player.getInventory().getContents());		
		
		humanNPC.getInventory().setArmorContents(player.getInventory().getArmorContents());
		
		DataBase.registerNPC(humanNPC);
		*/
		setTarget(player, DataBase.getLastDamager(player));//DataBase.getLastDamager(player));
		
		
	}
	
	public static void setTarget(final Player player, final Entity target)
	{
	
		NPCManager npcHandler = new NPCManager(Plugin.getPlugin());
		
		final HumanNPC npc = (HumanNPC) npcHandler.spawnHumanNPC(player.getName(), player.getLocation());

		npc.getInventory().setContents(player.getInventory().getContents());		
		
		npc.getInventory().setArmorContents(player.getInventory().getArmorContents());
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			int counter = 0;
			
			@Override
        	public void run()
			{
										
				LivingEntity bukkitNPC = (LivingEntity) npc.getBukkitEntity();
				
				npc.lookAtPoint(((LivingEntity)target).getEyeLocation().add(0, 0.5, 0));
				
					if (bukkitNPC.getLocation().distance(target.getLocation()) < 30)
					{
						
						if (npc.getInventory().contains(Material.BOW) && npc.getInventory().contains(Material.ARROW) && counter % 40 == 0)
						{
									
						//	switchToHand(npc, Material.BOW);
							
							Location l = bukkitNPC.getLocation();

							l.setYaw(npc.getEntity().yaw);
							npc.getEntity().pitch = bukkitNPC.getLocation().getPitch();
							l.setPitch(npc.getEntity().pitch);
							bukkitNPC.teleport(l);
						
							bukkitNPC.launchProjectile(Arrow.class);
							
							//removeItem(npc, Material.ARROW, 1);
							
						}
							
					}
					
					else if (bukkitNPC.getLocation().distance(target.getLocation()) < 5)
					{
						
						if (counter % 30 == 0)
						{
						
							if (bukkitNPC.getLocation().distance(target.getLocation()) < 3)
							{
							
								npc.animateArmSwing();
						
								((LivingEntity)target).damage((int) calculateDamage(npc, target), target);
								
							}
							
						}
						
					}
					else
					{
						
						npc.walkTo(target.getLocation());
						
					}
				
					counter ++;
					
			}
        	
		}, 1L, 1L);
		
	}
	
    public static float getLookAtYaw(Vector motion) {
        double dx = motion.getX();
        double dz = motion.getZ();
        double yaw = 0;
        // Set yaw
        if (dx != 0) {
            // Set yaw start value based on dx
            if (dx < 0) {
                yaw = 1.5 * Math.PI;
            } else {
                yaw = 0.5 * Math.PI;
            }
            yaw -= Math.atan(dz / dx);
        } else if (dz < 0) {
            yaw = Math.PI;
        }
        return (float) (-yaw * 180 / Math.PI - 90);
    }
		
	public static void switchToHand(HumanNPC npc, Material item)
	{
		
		if (npc.getInventory().getItemInHand() != null && npc.getInventory().getItemInHand().getType() != item)
		{
		
			int slot = getSlot(npc, item);
			
			ItemStack itemstack = npc.getInventory().getItem(slot);
			
			ItemStack currentHand = npc.getInventory().getItemInHand();
									
			npc.getInventory().setItemInHand(itemstack);
		
			npc.getInventory().addItem(currentHand);
						
		}
		
	}
	
	public static void removeItem(HumanNPC npc, Material item, int amount)
	{
		
		if (npc.getInventory().contains(item))
		{
		
			int slot = getSlot(npc, item);

			int currentAmount = npc.getInventory().getItem(slot).getAmount();
			
			if (!(currentAmount - amount < 0))
			{
				
				if (currentAmount - amount == 0)
				{
					
					npc.getInventory().remove(item);
					
				}
				
				else
				{
				
					npc.getInventory().getItem(slot).setAmount(currentAmount - amount);
					
				}

			}
			
		}
		
	}
	
	public static int getSlot(HumanNPC npc, Material type)
	{
		
		int slot = 0;
		
		for (ItemStack i : npc.getInventory().getContents())
		{
			
			if (i != null)
			{
				
				if (i.getType() == type)
				{
					
					return slot;
					
				}
				
			}
			
			slot ++;
			
		}
		
		return -1;
		
	}
	
	public static Arrow shootArrow(HumanNPC npc)
	{
		
		LivingEntity entity = (LivingEntity) npc.getBukkitEntity();
		
		npc.getEntity().boundingBox.shrink(0.5, 0.5, 0.5);
		
		Location location = entity.getEyeLocation();
		
		Vector vector = entity.getEyeLocation().toVector().add(entity.getLocation().getDirection().normalize().multiply(2)).multiply(5);
		
		Arrow arrow = npc.getBukkitEntity().getWorld().spawnArrow(location, vector, 2f, 1f);
		
		arrow.setShooter(entity);
		
		
		return arrow;
		
	}
	
	public static double calculateDamage(HumanNPC npc, Entity target)
	{
		
		double damage = 0;
		double defense = 0;
		
		if (target instanceof Player)
		{
			
			Player player = (Player) target;
			
			if (player.getInventory().getBoots() != null)
			{

				Material material = player.getInventory().getBoots().getType();
				
				switch(material)
				{
				
					case LEATHER_BOOTS: defense += 0.02;
					case CHAINMAIL_BOOTS: defense += 0.03;
					case IRON_BOOTS: defense += 0.05;
					case GOLD_BOOTS: defense += 0.8;
					case DIAMOND_BOOTS: defense += 0.10;
				
				}
				
			}
			
			if (player.getInventory().getLeggings() != null)
			{

				Material material = player.getInventory().getLeggings().getType();
				
				switch(material)
				{
				
					case LEATHER_LEGGINGS: defense += 0.03;
					case CHAINMAIL_LEGGINGS: defense += 0.04;
					case IRON_LEGGINGS: defense += 0.07;
					case GOLD_LEGGINGS: defense += 0.10;
					case DIAMOND_LEGGINGS: defense += 0.13;
				
				}
				
			}
			
			if (player.getInventory().getChestplate() != null)
			{

				Material material = player.getInventory().getChestplate().getType();
				
				switch(material)
				{
				
					case LEATHER_CHESTPLATE: defense += 0.05;
					case CHAINMAIL_CHESTPLATE: defense += 0.07;
					case IRON_CHESTPLATE: defense += 0.08;
					case GOLD_CHESTPLATE: defense += 0.12;
					case DIAMOND_CHESTPLATE: defense += 0.15;
				
				}
				
			}
			
			if (player.getInventory().getHelmet() != null)
			{

				Material material = player.getInventory().getHelmet().getType();
				
				switch(material)
				{
				
					case LEATHER_HELMET: defense += 0.02;
					case CHAINMAIL_HELMET: defense += 0.03;
					case IRON_HELMET: defense += 0.05;
					case GOLD_HELMET: defense += 0.8;
					case DIAMOND_HELMET: defense += 0.10;
				
				}
				
			}
			
			if (npc.getInventory().getItemInHand() != null)
			{
				
				String s = player.getInventory().getItemInHand().getType().name().split("_")[0];
												
				if (s.equalsIgnoreCase("WOODEN")) damage = 3;
				else if (s.equalsIgnoreCase("STONE")) damage = 5;
				else if (s.equalsIgnoreCase("IRON")) damage = 6;
				else if (s.equalsIgnoreCase("GOLD")) damage = 7.5;
				else if (s.equalsIgnoreCase("DIAMOND")) damage = 8.5;
				else damage = 2;
								
				if (npc.getInventory().getItemInHand().getType().name().contains("SWORD"))
				{
					
					damage += 1;
					
				}
			
			}
			
		}
		
		return damage - (damage * defense);
		
	}
		
}
