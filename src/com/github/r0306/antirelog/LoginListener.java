package com.github.r0306.antirelog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import com.github.r0306.antirelog.antirelog;

public class LoginListener implements Listener {
private antirelog plugin;

public LoginListener (antirelog plugin) {
	this.plugin = plugin;
}
	
@EventHandler
public void JoinListener (PlayerJoinEvent event) {
	Player player = event.getPlayer();
	if (!LogPrevention.TempBanList.contains(player) && plugin.getConfig().getString("MOTDToggle").equalsIgnoreCase("on") || !LogPrevention.TempBanList.contains(player) && plugin.getConfig().getString("MOTDToggle").equalsIgnoreCase("true")) {
		event.getPlayer().sendMessage(ChatColor.GOLD + plugin.getConfig().getString("MOTD"));
	}

	if (!LogPrevention.TempBanList.contains(player) && LogPrevention.OnJoinMessage.contains(player)){
		if (plugin.getConfig().getString("UnbanMSGToggle").equalsIgnoreCase("on") || plugin.getConfig().getString("UnbanMSGToggle").equalsIgnoreCase("true"))
		player.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("UnbanMSG"));
		LogPrevention.OnJoinMessage.remove(player);
	
	}
}

}

