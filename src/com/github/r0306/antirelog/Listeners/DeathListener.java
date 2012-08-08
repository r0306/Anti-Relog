package com.github.r0306.AntiRelog.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Configuration;

public class DeathListener implements Listener
{

	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (DataBase.isInCombat(player))
		{
			
			DataBase.removeFromCombat(player);
			
			if (Configuration.tagMessageEnabled())
			{
				
				player.sendMessage(Configuration.getTagMessage());
				
			}
			
		}
		
	}
	
}
