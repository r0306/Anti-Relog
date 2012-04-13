package com.github.r0306.antirelog;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class FreezeCommand implements Listener{
	private antirelog plugin;	
	public FreezeCommand (antirelog plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void freeze (PlayerCommandPreprocessEvent event) {
		if (DamageListener.isDamaged == true) {
			Player player = event.getPlayer();
			if (player.hasPermission("antirelog.pvpbypass") || player.isOp()) {
				DamageListener.isDamaged = false;
			} else {
				if (DamageListener.Damagelist.contains(player)){
				 if (DamageListener.isDamaged == true) {
					 String message = event.getMessage();
					 if (message.startsWith("/")) {				           
				                event.getPlayer().sendMessage(ChatColor.RED + plugin.getConfig().getString("StunMSG"));	   
				                event.setCancelled(true);
				         
				     }
				 }
				 }
			}
			}
		}
}
	

