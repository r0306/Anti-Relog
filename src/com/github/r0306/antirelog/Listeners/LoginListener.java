package com.github.r0306.AntiRelog.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Configuration;

public class LoginListener implements Listener
{

	@EventHandler (ignoreCancelled = true)
	public void onJoin(PlayerLoginEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (DataBase.isBanned(player.getName()))
		{
			
			event.setResult(Result.KICK_OTHER);
			
			event.setKickMessage(Configuration.getBanMessage());
			
		}
		
		if (Configuration.motdEnabled())
		{
			
			player.sendMessage(Configuration.getMotd());
			
		}
		
		if (DataBase.isLoginQueued(player) && Configuration.unbanMessageEnabled())
		{
			
			player.sendMessage(Configuration.getUnbanMessage());
			
		}
			
	}
	
}
