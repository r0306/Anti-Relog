package com.github.r0306.antirelog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.github.r0306.antirelog.antirelog;

public class LoginListener implements Listener {
private antirelog plugin;

public LoginListener (antirelog plugin) {
	this.plugin = plugin;
}
	
@EventHandler
public void JoinListener (PlayerJoinEvent event) {
event.getPlayer().sendMessage(ChatColor.GOLD + plugin.getConfig().getString("MOTD"));
}

}
