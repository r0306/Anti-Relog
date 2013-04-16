package com.github.r0306.AntiRelog.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Clock;
import com.github.r0306.AntiRelog.Util.Colors;
import com.github.r0306.AntiRelog.Util.Configuration;

public class FreezeCommand implements Listener, Colors
{

	@EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void freezeCommand(PlayerCommandPreprocessEvent event)
	{
		
		Player player = event.getPlayer();	

		if (DataBase.isInCombat(player))
		{
			
			if (Configuration.commandDisabled())
			{
				
				cancelEvent(event);
				
			}
			
			else if (!Clock.isEnded(DataBase.getEndingTime(player)))
			{

				if (!Configuration.commandsIsEmpty())
				{
				
					String command = event.getMessage();
					
					if (command.startsWith("/ar") || command.startsWith("/antirelog"))
					{
						
						return;
						
					}
					
					for (String s : Configuration.getDisallowedCommands())
					{

						if (("/" + s).toLowerCase().contains(command.toLowerCase()))
						{

							cancelEvent(event);
							break;
							
						}
						
					}
				
				}
			
			}
			
			else
			{
				
				DataBase.removeFromCombat(player);
				
			}
			
		}
		
	}
	
	public void cancelEvent(PlayerCommandPreprocessEvent event)
	{
		
		Player player = event.getPlayer();
		
		
		if (Configuration.tagMessageEnabled())
		{
		
			player.sendMessage(name + Configuration.getFreezeMessage());
		
		}
			
		event.setCancelled(true);
		
	}
	
}
