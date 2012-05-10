package com.github.r0306.antirelog;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;

public class PreLogin implements Listener{
	private antirelog plugin;

	public PreLogin (antirelog plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void Join (PlayerPreLoginEvent event) {
		String name = event.getName();
		if (LogPrevention.TempBanList.contains(name)) {
		event.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, plugin.getConfig().getString("PlayerBanMsg"));
	}
	}
}