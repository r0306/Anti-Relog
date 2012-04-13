package com.github.r0306.antirelog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
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
	static Set<Player> TempBan = Collections.newSetFromMap(new WeakHashMap<Player,Boolean>());
	public static long hi;
	static String playernamelog;
	@EventHandler
	public void Log (PlayerQuitEvent event) {
		int stunTime = plugin.getConfig().getInt("StunDuration");
		final Player playerquit = event.getPlayer();
		if (playerquit.hasPermission("antirelog.pvpbypass") || playerquit.isOp()) {
			DamageListener.Damagelist.remove(playerquit);
		} else {
			if (DamageListener.Damagelist.contains(playerquit) && stunTime > 0) {
				playerquit.setBanned(true);
				String playerquitname = playerquit.getName();
				playernamelog = playerquitname;
				Bukkit.broadcastMessage(ChatColor.GREEN + "[AntiRelog] " + playerquitname + " has logged off during combat.");
				Bukkit.broadcastMessage(ChatColor.GREEN + plugin.getConfig().getString("TempBanMsg"));
				DamageListener.Damagelist.remove(playerquit);
	        	PVPLogger log = new PVPLogger();
	       		log.Write();
		        for (ItemStack i : playerquit.getInventory().getContents())
		        {
		            if (i != null) {
		            playerquit.getWorld().dropItemNaturally(playerquit.getLocation(), i);
		            playerquit.getInventory().remove(i);
		            }
		        }
				int banduration = plugin.getConfig().getInt("BanDuration") * 1200;
        		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						TempBan.remove(playerquit);
	        			playerquit.setBanned(false); 
	    	        	PVPLogger log = new PVPLogger();
	        			log.WriteUnbanned();
					}
        		}, banduration);


			}
		}
			
	}
		
	}

