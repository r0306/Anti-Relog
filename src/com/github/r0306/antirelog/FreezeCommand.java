package com.github.r0306.antirelog;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class FreezeCommand implements Listener {

	private antirelog plugin;	
	public FreezeCommand (antirelog plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void freeze (PlayerCommandPreprocessEvent event) {
		
			Player player = event.getPlayer();
		    Calendar c = Calendar.getInstance();
	    if (player.hasPermission("antirelog.pvpbypass") || player.isOp()) {
            DamageListener.Damagelist.remove(player);
        }	
		if (DamageListener.Damagelist.containsKey(player)) {
			long endTime = DamageListener.Damagelist.get(player);
			if (!player.hasPermission("antirelog.pvpbypass") || !player.isOp()) {
				if (endTime > (c.getTimeInMillis() / 1000)){
					 String message = event.getMessage();
					 if (message.startsWith("/")) {	
						 for (int i = 0; i < plugin.cmdd.length; i++) {
						 if (message.startsWith("/ar") || message.startsWith("/antirelog") || plugin.getConfig().getString("CMDToggle").equalsIgnoreCase("off") || plugin.getConfig().getString("CMDToggle").equalsIgnoreCase("false")) {
							 break;
						 } else if (plugin.getConfig().getStringList("DisallowedCMDs").contains("*") || message.contains("/" + plugin.cmdd[i])) {
				                event.getPlayer().sendMessage(ChatColor.RED + plugin.getConfig().getString("StunMSG"));	   
				                event.setCancelled(true);
				                break;
						 }
					 }
				}

			else if (endTime == (c.getTimeInMillis() / 1000) || endTime < (c.getTimeInMillis() / 1000)) {
				 DamageListener.Damagelist.remove(player);
			}
	
	}

}				                
				         
}
	
}
	
}

		

	

