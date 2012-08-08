package com.github.r0306.AntiRelog.Util;

import java.io.IOException;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.r0306.AntiRelog.AntiRelog;
import com.github.r0306.AntiRelog.Storage.DataBase;

public class Clock
{

	public static long getTime()
	{
		
		return Calendar.getInstance().getTimeInMillis() / 1000;
		
	}
	
	public static long getEnd()
	{
		
		return getTime() + Configuration.getFreezeDuration();
		
	}
	
	public static long getElapsed(long current, long end)
	{
		
		return end - current;
		
	}
	
	public static boolean isEnded(long end)
	{
		
		return getElapsed(getTime(), end) < 0;
		
	}
	
	public static void scheduleDelayedMessage(final Player player)
	{
		
		if (DataBase.isScheduled(player))
		{
			
			Bukkit.getScheduler().cancelTask(DataBase.getTaskId(player));
			
		}
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
        	public void run()
			{
        	
				if (Configuration.tagMessageEnabled())
				{
				
					player.sendMessage(Configuration.getUnTagMessage());
        	    
				}
				
				DataBase.removeFromCombat(player);
        	    DataBase.removeTask(player);
        	   
			}
        	
		}, Configuration.getFreezeDuration());
        
		DataBase.registerTask(player, id);
		
	}
	
	public static void scheduleDelayedUnban(final Player player)
	{
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
        	public void run()
			{
        	
				DataBase.unbanPlayer(player.getName());
				
				try
				{
				
					AntiRelog.logger.log(player.getName(), null, 1);
				
				} catch (IOException e) {

					e.printStackTrace();
				
				}
			
			}
        	
		}, Configuration.getBanDuration());
        		
	}
	
	public static void scheduleRemoveEntity(final Player player, final Entity entity)
	{
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
        	public void run()
			{
				
				if (DataBase.containsLastDamager(player))
				{
			
					if (DataBase.getLastDamager(player).getEntityId() == entity.getEntityId())
					{
				
						DataBase.removeLastDamager(player);
				
					}
			
				}
		
			}
        	
		}, Configuration.getFreezeDuration());
		
	}
	
}
