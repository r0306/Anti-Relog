package com.github.r0306.AntiRelog.Storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.r0306.AntiRelog.NPC.HumanNPC;
import com.github.r0306.AntiRelog.Util.Clock;
import com.github.r0306.AntiRelog.Util.Configuration;

public class DataBase
{

	private static Set<String> loginQueue = new HashSet<String>();
	private static Set<String> banned = new HashSet<String>();
	private static HashMap<String, Entity> lastDamager = new HashMap<String, Entity>();
	private static HashMap<String, HumanNPC> npcs = new HashMap<String, HumanNPC>();
	private static HashMap<String, Long> inCombat = new HashMap<String, Long>();
	private static HashMap<String, Integer> ids = new HashMap<String, Integer>();
	
	public static void addToLoginQueue(Player player)
	{
		
		loginQueue.add(player.getName());
		
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
		
		return loginQueue.contains(player.getName());
		
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
			
			if (Configuration.unbanMessageEnabled())
			{
				
				loginQueue.add(player);
				
			}
			
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

	public static Set<String> getLoginQueue()
	{
		
		return loginQueue;
		
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
	
	public static void registerNPC(HumanNPC npc)
	{
		
		npcs.put(npc.getName(), npc);
		
	}
	
	public static void removeNPC(HumanNPC npc)
	{
		
		if (containsNPC(npc))
		{
			
			npcs.remove(npc.getName());
			
		}
		
	}
		
	public static boolean containsNPC(HumanNPC npc)
	{
		
		return npcs.containsKey(npc.getName());
		
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
