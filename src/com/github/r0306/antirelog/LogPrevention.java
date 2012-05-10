package com.github.r0306.antirelog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LogPrevention implements Listener {
	private antirelog plugin;	
	public LogPrevention (antirelog plugin) {
		this.plugin = plugin;
	}
	private MyTask task;
	static Set<Player> TempBan = Collections.newSetFromMap(new WeakHashMap<Player,Boolean>());
	static Set<String> TempBanList = Collections.newSetFromMap(new WeakHashMap<String,Boolean>());
	static Set<Player> OnJoinMessage = Collections.newSetFromMap(new WeakHashMap<Player, Boolean>());
	public static long hi;
	public static String DisconnectMsg = null;
	static String playernamelog;
	@EventHandler
	public void Log (PlayerQuitEvent event) {
	    Calendar c = Calendar.getInstance();
		int stunTime = plugin.getConfig().getInt("StunDuration");
		final Player playerquit = event.getPlayer();
		if (playerquit.hasPermission("antirelog.pvpbypass") || playerquit.isOp() || DisconnectMsg == null || DisconnectMsg.equalsIgnoreCase("disconnect.endofstream") || DisconnectMsg.equalsIgnoreCase("disconnect.timeout") || DisconnectMsg.equalsIgnoreCase("disconnect.overflow")) {
			DamageListener.Damagelist.remove(playerquit);
			DisconnectMsg = null;
		} else {
			if (DamageListener.Damagelist.containsKey(playerquit) && stunTime > 0) {
				final long endTime = DamageListener.Damagelist.get(playerquit);
				if (endTime > (c.getTimeInMillis() / 1000)) {
				DisconnectMsg = null;
				String playerquitname = playerquit.getName();
				playernamelog = playerquitname;
				if (plugin.getConfig().getString("TempBanMsgToggle").equalsIgnoreCase("true") || plugin.getConfig().getString("TempBanMsgToggle").equalsIgnoreCase("on")) {
				Bukkit.broadcastMessage(ChatColor.GREEN + "[AntiRelog] " + playerquitname + " has logged off during combat.");
				Bukkit.broadcastMessage(ChatColor.GREEN + plugin.getConfig().getString("TempBanMsg"));
				}
				DamageListener.Damagelist.remove(playerquit);
				final String quitname = playerquit.getName();
				TempBan.add(playerquit);
				TempBanList.add(quitname);
	        	PVPLogger log = new PVPLogger();
	       		log.Write();
		        for (ItemStack i : playerquit.getInventory().getContents())
		        {
		            if (i != null) {
		            playerquit.getWorld().dropItemNaturally(playerquit.getLocation(), i);
		            playerquit.getInventory().remove(i);
		            }
		        }
		        ItemStack[] armor = playerquit.getInventory().getArmorContents();
		        for (ItemStack content: armor)
		        {
		        	if (content.getAmount() != 0) {
		        		playerquit.getWorld().dropItemNaturally(playerquit.getLocation(), content);
		        	}
		        }
		        playerquit.getInventory().setArmorContents(new ItemStack[4]);
	       		float Exp = playerquit.getExp();
	       		int ExpOrbs = (int)Exp;
	       		int Level = playerquit.getLevel();
	       		int ExpTotal = ((2 * Level * Level) + (5 * ExpOrbs)) / 5;
	       		World world = playerquit.getWorld();
	       		for (int i = 0; i < 6; i ++) {
	       	    ((ExperienceOrb)world.spawn(playerquit.getLocation(), ExperienceOrb.class)).setExperience( ExpTotal );
				}
			    playerquit.setLevel( 0 );
	       	    playerquit.setExp( 0 );
				int banduration = plugin.getConfig().getInt("BanDuration") * 1200;
				OnJoinMessage.add(playerquit);
        		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
							TempBan.remove(playerquit);
							TempBanList.remove(quitname);
		    	        	PVPLogger log = new PVPLogger();
		        			log.WriteUnbanned();
						}
					}
        		 , banduration);


			}
		else if (endTime == c.getTimeInMillis() || endTime < (c.getTimeInMillis())) {
			 DamageListener.Damagelist.remove(playerquit);
		}
		
		}	
	}
}
	
}

		
	

