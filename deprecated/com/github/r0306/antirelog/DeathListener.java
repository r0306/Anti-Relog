package com.github.r0306.antirelog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class DeathListener implements Listener{
	
	private antirelog plugin;
	
	public DeathListener(antirelog plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void PlayerDeath (PlayerDeathEvent event) {
		Player playerdeath = event.getEntity();
		if (DamageListener.Damagelist.containsKey(playerdeath)) {
			DamageListener.Damagelist.remove(playerdeath);
		}
		if (DamageListener.timer.containsKey(playerdeath)) {
			Bukkit.getScheduler().cancelTask(DamageListener.timer.get(playerdeath));
			DamageListener.timer.remove(playerdeath);
			playerdeath.sendMessage(ChatColor.GREEN + "[AntiRelog] " + plugin.getConfig().getString("UnTag-Message"));
		}
	}

}
