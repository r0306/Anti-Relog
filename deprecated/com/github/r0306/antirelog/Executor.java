package com.github.r0306.antirelog;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Executor implements CommandExecutor {
	
	private antirelog plugin;
	 
	public Executor(antirelog plugin) {
		this.plugin = plugin;
	}
	public static String unban = null;
	public static String unbanBy = null;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		} else {
			System.out.println("You can only use this command in-game!");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("ar")) {
			if (args.length == 0) {
			player.sendMessage(ChatColor.GRAY + "[AntiRelog] This is version 1.5.");
			return true;
			}
			
			if (((args.length == 1) && args[0].equalsIgnoreCase("t")) || ((args.length == 1) && (args[0].equalsIgnoreCase("time")))) {
				if (DamageListener.Damagelist.containsKey(player)) {
				    Calendar c = Calendar.getInstance();
					long endTime = DamageListener.Damagelist.get(player);
					if (endTime > (c.getTimeInMillis() / 1000)){
					long timeLeft = DamageListener.Damagelist.get(player) - (c.getTimeInMillis() / 1000);
					player.sendMessage(ChatColor.DARK_AQUA + "You have " + timeLeft + " seconds before combat ends.");
					return true;
					} else { 
					player.sendMessage(ChatColor.GREEN + "You are not currently in combat.");
					return true;
					} 
				} else {
					player.sendMessage(ChatColor.GREEN + "You are not currently in combat.");
					return true;
				}
			}
			else if (args.length == 2 && args[0].equalsIgnoreCase("unban")) {
				if (player.hasPermission("antirelog.unban") || player.isOp()) {
					OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[1]);
					if (p == null) {
						player.sendMessage(ChatColor.RED + "[AntiRelog] Player is not found. Please check the spelling and try again.");
					} else {
						if (Bukkit.getServer().getOnlineMode() == true) {
							if (LogPrevention.TempBan.contains(p)) {
								LogPrevention.TempBan.remove(p);
								LogPrevention.TempBanList.remove(p.getName());
								player.sendMessage(ChatColor.GOLD + "[AntiRelog] " + args[1] + " was unbanned.");
								unban = args[1];
								unbanBy = player.getName();
					        	PVPLogger log = new PVPLogger(plugin);
								log.WriteUnbannedByPlayer();
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "[AntiRelog] " + args[1] + " was not banned.");
								return true;
							}
						} else {
							if (LogPrevention.TempBanNames.contains(args[1])) {
								p.setBanned(false);
								LogPrevention.TempBan.remove(p);
								LogPrevention.TempBanNames.remove(args[1]);
								player.sendMessage(ChatColor.GOLD + "[AntiRelog] " + args[1] + " was unbanned.");
								unban = args[1];
								unbanBy = player.getName();
					        	PVPLogger log = new PVPLogger(plugin);
								log.WriteUnbannedByPlayer();
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "[AntiRelog] " + args[1] + " was not banned.");
								return true;
							}
						}
					}
				}
			} else {
			player.sendMessage(ChatColor.RED + "[AntiRelog] Unknown command.");
			return true;
			} 
		}	
		else if (args.length < 0 || args.length > 2) {
			player.sendMessage(ChatColor.RED + "[AntiRelog] Unknown command.");
		}	
		else if (cmd.getName().equalsIgnoreCase("antirelog")) {
				if (args.length == 0) {
				player.sendMessage(ChatColor.GRAY + "[AntiRelog] This is Version 1.5.");
				return true;
				}
				else if (((args.length == 1) && args[0].equalsIgnoreCase("t")) || ((args.length == 1) && (args[0].equalsIgnoreCase("time")))) {
					if (DamageListener.Damagelist.containsKey(player)) {
					    Calendar c = Calendar.getInstance();
						long endTime = DamageListener.Damagelist.get(player);
						if (endTime > (c.getTimeInMillis() / 1000)){
						long timeLeft = DamageListener.Damagelist.get(player) - (c.getTimeInMillis() / 1000);
						player.sendMessage(ChatColor.DARK_AQUA + "You have  " + timeLeft + " seconds before combat ends.");
						return true;
						} else {
						player.sendMessage(ChatColor.GREEN + "You are not currently in combat.");
						return true;
						}
					} else {
						player.sendMessage(ChatColor.GREEN + "You are not currently in combat.");
						return true;
					}
				}
				if (player.hasPermission("antirelog.unban") || player.isOp()) {
					OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[1]);
					if (p == null) {
						player.sendMessage(ChatColor.RED + "[AntiRelog] Player is not found. Please check the spelling and try again.");
					} else {
						if (Bukkit.getServer().getOnlineMode() == true) {
							if (LogPrevention.TempBan.contains(p)) {
								LogPrevention.TempBan.remove(p);
								LogPrevention.TempBanList.remove(p.getName());
								player.sendMessage(ChatColor.GOLD + "[AntiRelog] " + args[1] + " was unbanned.");
					        	PVPLogger log = new PVPLogger(plugin);
								log.WriteUnbannedByPlayer();
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "[AntiRelog] " + args[1] + " was not banned.");
								return true;
							}
						} else {
							if (LogPrevention.TempBan.contains(p)) {
								p.setBanned(false);
								LogPrevention.TempBan.remove(p);
								player.sendMessage(ChatColor.GOLD + "[AntiRelog] " + args[1] + " was unbanned.");
					        	PVPLogger log = new PVPLogger(plugin);
								log.WriteUnbannedByPlayer();
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "[AntiRelog] " + args[1] + " was not banned.");
								return true;
							}
						}
					}
				} else {
				player.sendMessage(ChatColor.RED + "[AntiRelog] Unknown command.");
				return false;
				} 
		}
		return false;
	
}
		
}

		

	

