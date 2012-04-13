package com.github.r0306.antirelog;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener{
	
	@EventHandler
	public void PlayerDeath (PlayerDeathEvent event) {
		Player playerdeath = event.getEntity();
		if (DamageListener.Damagelist.contains(playerdeath)) {
			DamageListener.Damagelist.remove(playerdeath);
		}
	}

}
