package com.github.r0306.AntiRelog.Listeners;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.AntiRelog.AntiRelog;
import com.github.r0306.AntiRelog.NPC.AntiRelogNPC;
import com.github.r0306.AntiRelog.NPC.HumanNPC;
import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Clock;
import com.github.r0306.AntiRelog.Util.Colors;
import com.github.r0306.AntiRelog.Util.Configuration;
import com.github.r0306.AntiRelog.Util.Util;

public class LogPrevention implements Listener, Colors
{

	private static String disconnectMessage;
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) throws IOException
	{
		
		Player player = event.getPlayer();
		
		if (DataBase.isInCombat(player))
		{

			long end = DataBase.getEndingTime(player);
			Set<Integer> items = new HashSet<Integer>();
			
			if (!Clock.isEnded(end))
			{

				if (isPvPLog())
				{

					if (Configuration.npcEnabled())
					{

						if (DataBase.containsLastDamager(player))
						{

							AntiRelogNPC.spawnNPC(player, Configuration.npcAggressive() || Util.aggroNPC(player) ? true : false);
							
							Clock.scheduleDelayedDespawn(player);
							
						}
						
					}					
					
					else
					{
						
						if (Configuration.dropItemsEnabled())
						{
							
							dropItems(player);
							items.add(0);
							
						}
						
						if (Configuration.dropArmorEnabled())
						{
							
							dropArmor(player);
							items.add(1);
							
						}
						
						if (Configuration.dropExpEnabled())
						{
							
							dropExp(player);
							items.add(2);
							
						}
												
					}
					
					if (Configuration.broadcastEnabled())
					{
						
						Bukkit.broadcastMessage(name + green + player.getName() + " has logged off during combat.");
						Bukkit.broadcastMessage(Configuration.getBroadcastMessage().replaceAll("<player>", player.getName()));
						
					}
					
					DataBase.addToLoginQueue(player.getName(), items);
					
					DataBase.banPlayer(player);
					
					AntiRelog.logger.log(player.getName(), null, 0);
					
					Clock.scheduleDelayedUnban(player);
										
				}
				
			}
			
		}
			
	}
	
	public static void dropItems(HumanEntity player)
	{
		
        for (ItemStack i : player.getInventory().getContents())
        {
         
        	if (i != null)
        	{
            
        		player.getWorld().dropItemNaturally(player.getLocation(), i);
        		player.getInventory().remove(i);
            
        	}
        
        }
		
	}
	
	public static void dropArmor(HumanEntity player)
	{
		
        for (ItemStack armor : player.getInventory().getArmorContents())
        {
        	
        	if (armor.getAmount() != 0)
        	{
        	
        		player.getWorld().dropItemNaturally(player.getLocation(), armor);

        		player.getInventory().setArmorContents(new ItemStack[4]);
        	
        	}
        
        }
		
	}
	
	public static void dropExp(Player player)
	{
		
   		float Exp = player.getExp();
   		
   		int ExpOrbs = (int) Exp;
   		
   		int Level = player.getLevel();
   		
   		int ExpTotal = ((2 * Level * Level) + (5 * ExpOrbs)) / 5;
   		
   		World world = player.getWorld();
   		
   		for (int i = 0; i < 6; i ++)
   		{
   	    
   			((ExperienceOrb)world.spawn(player.getLocation(), ExperienceOrb.class)).setExperience( ExpTotal );
		
   		}
	    
   		player.setLevel( 0 );
   	    
   		player.setExp( 0 );
   		
	}
	
	public static void dropExp(HumanNPC npc)
	{
		   		
   		int ExpTotal = (int) (npc.getExp() / 5);
   		
   		World world = npc.getBukkitEntity().getLocation().getWorld();
   		
   		for (int i = 0; i < 6; i ++)
   		{
   	    
   			((ExperienceOrb)world.spawn(npc.getBukkitEntity().getLocation(), ExperienceOrb.class)).setExperience( ExpTotal );
		
   		}
   		
	}
	
	public static void setDisconnectMessage(String message)
	{
		
		disconnectMessage = message;
		
	}
	
	public boolean isPvPLog()
	{
		
		if (disconnectMessage.equalsIgnoreCase("disconnect.timeout") || disconnectMessage.equalsIgnoreCase("disconnect.overflow") || disconnectMessage.equalsIgnoreCase("disconnect.genericreason") || disconnectMessage.equalsIgnoreCase("disconnect.timeout"))
		{
			
			return false;
			
		}
		
		return true;
		
	}
	
}
