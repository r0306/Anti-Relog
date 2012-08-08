package com.github.r0306.AntiRelog.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Configuration;
import com.github.r0306.AntiRelog.Util.Plugin;

public class AntiRelogNPC
{
	
	public static void spawnNPC(Player player)
	{
				
		NPCManager npcHandler = new NPCManager(Plugin.getPlugin());
		
		NPC npc = npcHandler.spawnHumanNPC(player.getName(), player.getLocation());
		
		HumanNPC humanNPC = (HumanNPC) npc;

		humanNPC.getInventory().setContents(player.getInventory().getContents());		
		
		humanNPC.getInventory().setArmorContents(player.getInventory().getArmorContents());
		
		DataBase.registerNPC(humanNPC);
		
		setTarget(humanNPC, DataBase.getLastDamager(player));//DataBase.getLastDamager(player));
		
	}
	
	public static void setTarget(final HumanNPC npc, final Entity target)
	{
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			final LivingEntity bukkitNPC = (LivingEntity) npc.getBukkitEntity();
			
			@Override
        	public void run()
			{
        		System.out.println(bukkitNPC.getLocation().distance(target.getLocation()));
        		


									
					if (bukkitNPC.getLocation().distance(target.getLocation()) < 30)
					{
						
						if (npc.getInventory().contains(Material.BOW) && npc.getInventory().contains(Material.ARROW))
						{
							
							switchToHand(npc, Material.BOW);

							bukkitNPC.launchProjectile(Arrow.class);
							
							removeItem(npc, Material.ARROW, 1);
							
						}
							
					}
					
					else if (bukkitNPC.getLocation().distance(target.getLocation()) < 5)
					{
						
						npc.animateArmSwing();
						
						bukkitNPC.damage((int) calculateDamage(npc, target), target);
						
					}
					else
					{
						
						npc.moveTo(Bukkit.getPlayer("Player").getLocation());
						
					}
				
			
			}
        	
		}, 1L, 1L);
		
	}
	
	public static void switchToHand(HumanNPC npc, Material item)
	{
		
		if (npc.getInventory().getItemInHand().getType() != item)
		{
		
			ItemStack itemstack = npc.getInventory().getItem(item.getId());
			
			ItemStack currentHand = npc.getInventory().getItemInHand();
			
			if (currentHand != null)
			{
			
				npc.getInventory().remove(currentHand);
			
				npc.getInventory().setItemInHand(itemstack);
			
				npc.getInventory().addItem(currentHand);
				
			}
			
			else
			{
				
				npc.getInventory().setItemInHand(itemstack);
				
			}
						
		}
		
	}
	
	public static void removeItem(HumanNPC npc, Material item, int amount)
	{
		
		if (npc.getInventory().contains(item))
		{
			
			int currentAmount = npc.getInventory().getItem(item.getId()).getAmount();
			
			if (!(currentAmount - amount < 0))
			{
				
				if (currentAmount - amount == 0)
				{
					
					npc.getInventory().remove(item);
					
				}
				
				else
				{
				
					npc.getInventory().getItem(item.getId()).setAmount(currentAmount - amount);
					
				}

			}
			
		}
		
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
