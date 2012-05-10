package com.github.r0306.antirelog;

import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Executor implements CommandExecutor {
	
	private antirelog plugin;
	 
	public Executor(antirelog plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		} else {
			System.out.println("You can only use this command in-game!");
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("ar")) {
			if (args.length == 0) {
			player.sendMessage(ChatColor.GRAY + "[AntiRelog] This is version 1.3.");
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
			} else {
			player.sendMessage(ChatColor.RED + "[AntiRelog] Unknown command.");
			return false;
			} 
		}	
		else if (args.length < 0 || args.length > 1) {
			player.sendMessage(ChatColor.RED + "[AntiRelog] Unknown command.");
		}	
		else if (cmd.getName().equalsIgnoreCase("antirelog")) {
				if (args.length == 0) {
				player.sendMessage(ChatColor.GRAY + "[AntiRelog] This is Version 1.0.");
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
				} else {
				player.sendMessage(ChatColor.RED + "[AntiRelog] Unknown command.");
				return false;
				} 
		}
		return false;
	
}
		
}

		

	

