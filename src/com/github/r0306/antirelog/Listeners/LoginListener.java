package com.github.r0306.AntiRelog.Listeners;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Colors;
import com.github.r0306.AntiRelog.Util.Configuration;
import com.github.r0306.AntiRelog.Util.Plugin;
import com.github.r0306.AntiRelog.Util.Util;

public class LoginListener implements Listener, Colors
{

	@EventHandler (ignoreCancelled = true)
	public void onJoin(PlayerLoginEvent event)
	{
		
		final Player player = event.getPlayer();
		
		if (DataBase.isBanned(player.getName()))
		{
			
			event.setResult(Result.KICK_OTHER);
			
			event.setKickMessage(Configuration.getBanMessage());
				
		}
		
		else if (DataBase.containsNPC(event.getPlayer().getName()))
		{
			
			event.setResult(Result.KICK_OTHER);
			
			event.setKickMessage(Configuration.getNPCBattleMessage());
			
		}
		
		if (event.getResult() == Result.ALLOWED)
		{
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
			{

				@Override
	        	public void run()
				{
					
					if (DataBase.isLoginQueued(player))
					{

						if (Configuration.unbanMessageEnabled())
						{

							player.sendMessage(Configuration.getUnbanMessage());
							
						}
						
						if (!Configuration.runCommandsIsEmpty())
						{
							
							for (String s : Configuration.getCommands())
							{
								
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Util.replacePlayerName(s, player));
								
							}
							
						}
							
						removePlayerInventory(player);
						
						DataBase.removeFromLoginQueue(player);
							
					
					}
						
				}
				
			}, 0L);
							
		}
				
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (Configuration.motdEnabled())
		{
			
			player.sendMessage(Configuration.getMotd());
			
		}
		
	}
	
	public void removePlayerInventory(Player player)
	{
		
		Set<Integer> items = DataBase.getItems(player);

		if (items.contains(0))
		{

			player.getInventory().clear();
			
		}
		
		if (items.contains(1))
		{
			
			player.getInventory().setArmorContents(new ItemStack[4]);
			
		}
		
		if (items.contains(2))
		{
			
			player.setLevel(0);
			player.setExp(0f);	
			
		}
		
	}
	
}
