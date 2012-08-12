package com.github.r0306.AntiRelog.Storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.r0306.AntiRelog.AntiRelog;
import com.github.r0306.AntiRelog.NPC.HumanNPC;
import com.github.r0306.AntiRelog.NPC.NPC;
import com.github.r0306.AntiRelog.Util.Clock;

public class DataBase
{

	private static Set<String> banned = new HashSet<String>();
	private static HashMap<String, Set<Integer>> loginQueue = new HashMap<String, Set<Integer>>();
	private static HashMap<String, Entity> lastDamager = new HashMap<String, Entity>();
	private static HashMap<String, Long> inCombat = new HashMap<String, Long>();
	private static HashMap<String, Integer> ids = new HashMap<String, Integer>();
	
	public static void loadQueue() throws FileNotFoundException, ClassNotFoundException, IOException
	{
		
		loginQueue = Persistence.loadQueue();
		
	}
	
	public static void saveQueue() throws IOException
	{
		
		Persistence.saveQueue();
		
	}
	
	public static void addToLoginQueue(String player, Set<Integer> items)
	{
		
		loginQueue.put(player, items);
	
	}
	
	public static void clearAll()
	{
		
		banned = null;
		loginQueue = null;
		lastDamager = null;
		inCombat = null;
		ids = null;
		
	}
	
	public static void removeFromLoginQueue(Player player)
	{
		
		if (isLoginQueued(player))
		{
			
			loginQueue.remove(player.getName());
			
		}
		
	}
	
	public static boolean isLoginQueued(Player player)
	{
		
		return loginQueue.containsKey(player.getName());
		
	}
	
	public static HashMap<String, Set<Integer>> getLoginQueue()
	{
		
		return loginQueue;
		
	}
		
	public static void banPlayer(Player player)
	{
		
		banned.add(player.getName());
		
	}
	
	public static void unbanPlayer(String player)
	{
		
		if (isBanned(player))
		{
			
			banned.remove(player);
			
		}
		
	}
	
	public static boolean isBanned(String player)
	{
		
		return banned.contains(player);
		
	}
	
	public static Set<String> getBanned()
	{
		
		return banned;
		
	}

	public static Set<Integer> getItems(Player player)
	{
		
		return loginQueue.get(player.getName());
		
	}
	
	public static Entity getLastDamager(Player player)
	{
		
		if (containsLastDamager(player))
		{
			
			return lastDamager.get(player.getName());
			
		}
		
		return null;
		
	}
	
	public static void setLastDamager(Player player, Entity entity)
	{
		
		lastDamager.put(player.getName(), entity);
		
		Clock.scheduleRemoveEntity(player, entity);
		
	}
	
	public static void removeLastDamager(Player player)
	{
		
		if (containsLastDamager(player))
		{
			
			lastDamager.remove(player.getName());
			
		}
		
	}
	
	public static boolean containsLastDamager(Player player)
	{
		
		return lastDamager.containsKey(player.getName());
		
	}

	public static boolean containsNPC(String name)
	{
		
		return AntiRelog.npcHandler.getHumanNPCByName(name).size() > 0;
		
	}
	
	public static HumanNPC getNPCByName(String name)
	{
		
		if (containsNPC(name))
		{
			
			return (HumanNPC) AntiRelog.npcHandler.getHumanNPCByName(name).get(0);
			
		}
		
		return null;
		
	}
	
	public static HumanNPC getNPCByEntity(Entity entity)
	{

		for (NPC npc : AntiRelog.npcHandler.getNPCs())
		{
			
			if (((HumanNPC) npc).getBukkitEntity().getEntityId() == entity.getEntityId())
			{
				
				return (HumanNPC) npc;
				
			}
			
		}
		
		return null;

	}
	
	public static void removeNPC(String name)
	{
		
		if (containsNPC(name))
		{
			
			NPC npc = getNPCByName(name);
			
			if (npc.isAggressive())
			{

				Bukkit.getScheduler().cancelTask(npc.getId());
				
				AntiRelog.npcHandler.despawnHumanByName(name);
			
			}
				
		}
		
	}
	
	public static boolean isNPC(Entity entity)
	{
		
		for (NPC npc : AntiRelog.npcHandler.getNPCs())
		{
			
			if (((HumanNPC) npc).getBukkitEntity().getEntityId() == entity.getEntityId())
			{
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public static void addInCombat(Player player, Long time)
	{
		
		inCombat.put(player.getName(), time);
		
	}
	
	public static void removeFromCombat(Player player)
	{
		
		if (isInCombat(player))
		{
			
			inCombat.remove(player.getName());
			
			if (isScheduled(player))
			{
				
				Bukkit.getScheduler().cancelTask(getTaskId(player));
				
			}
			
		}
		
	}
	
	public static boolean isInCombat(Player player)
	{
		
		return inCombat.containsKey(player.getName());
		
	}
	
	public static long getEndingTime(Player player)
	{
			
		return isInCombat(player) ? inCombat.get(player.getName()) : -1;
		
	}
	
	public static void registerTask(Player player, int id)
	{
		
		ids.put(player.getName(), id);
		
	}
	
	public static void removeTask(Player player)
	{
		
		if (isScheduled(player))
		{
			
			ids.remove(player.getName());
			
		}
		
	}
	
	public static boolean isScheduled(Player player)
	{
		
		return ids.containsKey(player.getName());
		
	}
	
	public static int getTaskId(Player player)
	{
		
		if (isScheduled(player))
		{
			
			return ids.get(player.getName());
			
		}
		
		return -1;
		
	}
	
}
