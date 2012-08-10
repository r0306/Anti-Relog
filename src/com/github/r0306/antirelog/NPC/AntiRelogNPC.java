package com.github.r0306.AntiRelog.NPC;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.AntiRelog.AntiRelog;
import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Plugin;

public class AntiRelogNPC
{
	
	public static void spawnNPC(Player player, boolean aggro)
	{

		NPC npc = AntiRelog.npcHandler.spawnHumanNPC(player.getName(), player.getLocation());
		
		HumanNPC humanNPC = (HumanNPC) npc;

		humanNPC.getInventory().setContents(player.getInventory().getContents());		
		
		humanNPC.getInventory().setArmorContents(player.getInventory().getArmorContents());
		
		humanNPC.setExp(player.getTotalExperience());
		
		((LivingEntity)humanNPC.getBukkitEntity()).setNoDamageTicks(1);
				
		((LivingEntity)humanNPC.getBukkitEntity()).setHealth(player.getHealth());
		
		((LivingEntity)humanNPC.getBukkitEntity()).addPotionEffects(player.getActivePotionEffects());
		
		if (aggro)
		{

			Entity target = DataBase.getLastDamager(player);
			
			setTarget(humanNPC, target);
			
			humanNPC.setAggressive(true);
			
			humanNPC.setTarget(target);
						
		}
								
	}
	
	public static void setTarget(final HumanNPC npc, final Entity target)
	{

		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			int counter = 0;
			
			@Override
        	public void run()
			{

				double distance = distance(npc, target);
				
				LivingEntity bukkitNPC = (LivingEntity) npc.getBukkitEntity();
				
				npc.lookAtPoint(((LivingEntity)target).getEyeLocation());
				
				if (!bukkitNPC.isDead() && !target.isDead())
				{
				
					if (distance > 4 && distance < 30 && npc.getInventory().contains(Material.BOW) && npc.getInventory().contains(Material.ARROW))
					{
	
						if (counter % 40 == 0)
						{
							
							switchToHand(npc, Material.BOW);
	
							bukkitNPC.launchProjectile(Arrow.class);
							
							removeItem(npc, Material.ARROW, 1);
						
						}	
							
					}
					
					else if (distance <= 4)
					{
						
						if (counter % 30 == 0)
						{
							
							npc.walkTo(getApproximateTargetLocation(target));
							
							npc.lookAtPoint(((LivingEntity)target).getEyeLocation());
							
							getOptimalWeapon(npc);
							
							npc.animateArmSwing();

							((LivingEntity)target).damage((int) calculateDamage(npc, target, null), (LivingEntity)npc.getBukkitEntity());
							

							
						}
						
					}
					else
					{
						
						if (counter % 10 == 0)
						{
						
							npc.walkTo(getApproximateTargetLocation(target));
						
						}
							
					}
					
				}
				
				else
				{
					
					DataBase.removeNPC(npc.getName());
					
				}
			
				counter ++;
					
			}
        	
		}, 1L, 1L);
		
		npc.setId(id);
		
	}
	
	public static double distance(HumanNPC npc, Entity target)
	{
		
		return npc.getBukkitEntity().getLocation().distance(target.getLocation());
		
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
	
	public static void getOptimalWeapon(HumanNPC npc)
	{
		
		for (Material material : getWeapons())
		{
			
			if (npc.getInventory().contains(material))
			{
				
				switchToHand(npc, material);
				break;
				
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
		
	public static double calculateDamage(HumanNPC npc, Entity target, Entity attacker)
	{
		
		double damage = 3;
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
			
			if (npc != null)
			{
			
				if (npc.getInventory().getItemInHand() != null)
				{
					
					String s = player.getInventory().getItemInHand().getType().name();
													
					if (s.contains("WOODEN")) damage = 1;
					else if (s.contains("STONE")) damage = 2;
					else if (s.contains("IRON")) damage = 3;
					else if (s.contains("GOLD")) damage = 4.5;
					else if (s.contains("DIAMOND")) damage = 5.5;
									
					if (npc.getInventory().getItemInHand().getType().name().contains("SWORD"))
					{
						
						damage += 2;
						
					}
				
				}
				
			}
			
			else if (attacker instanceof Projectile)
			{
				
				damage = 5;
				
			}
			
			else
			{
				
				HumanEntity damager = (HumanEntity) attacker;
				
				if (damager.getInventory().getItemInHand() != null)
				{
					
					String s = damager.getInventory().getItemInHand().getType().name();
					
					if (s.contains("WOODEN")) damage = 1;
					else if (s.contains("STONE")) damage = 2;
					else if (s.contains("IRON")) damage = 3;
					else if (s.contains("GOLD")) damage = 4.5;
					else if (s.contains("DIAMOND")) damage = 5.5;
					
					if (damager.getInventory().getItemInHand().getType().name().contains("SWORD"))
					{
						
						damage += 2;
						
					}
					
				}
				
			}
			
		}
		
		return damage - (damage * defense);
		
	}
	
	public static List<Material> getWeapons()
	{
	
		return Arrays.asList(new Material[]{Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD,
				                            Material.WOOD_SWORD, Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE});
		
		
	}
	
	public static Location getApproximateTargetLocation(Entity target)
	{
		
		BlockFace face = getTargetFacing(target);
		
		return target.getLocation().getBlock().getRelative(face, 2).getLocation();
		
	}
	
	public static BlockFace getTargetFacing(Entity target)
	{
	
         float y = target.getLocation().getYaw();
         if( y < 0 ) y += 360;
         y %= 360;
         int i = (int)((y+8) / 22.5);
         
         if(i == 0) return BlockFace.WEST;
         else if(i == 1) return BlockFace.NORTH_WEST;
         else if(i == 2) return BlockFace.NORTH_WEST;
         else if(i == 3) return BlockFace.NORTH_WEST;
         else if(i == 4) return BlockFace.NORTH;
         else if(i == 5) return BlockFace.NORTH_EAST;
         else if(i == 6) return BlockFace.NORTH_EAST;
         else if(i == 7) return BlockFace.NORTH_EAST;
         else if(i == 8) return BlockFace.EAST;
         else if(i == 9) return BlockFace.SOUTH_EAST;
         else if(i == 10) return BlockFace.SOUTH_EAST;
         else if(i == 11) return BlockFace.SOUTH_EAST;
         else if(i == 12) return BlockFace.SOUTH;
         else if(i == 13) return BlockFace.SOUTH_WEST;
         else if(i == 14) return BlockFace.SOUTH_WEST;
         else if(i == 15) return BlockFace.SOUTH_WEST;

         return BlockFace.WEST;
		
	}
	
}
