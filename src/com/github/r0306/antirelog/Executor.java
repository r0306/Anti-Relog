package com.github.r0306.AntiRelog;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.r0306.AntiRelog.NPC.AntiRelogNPC;
import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Clock;
import com.github.r0306.AntiRelog.Util.Colors;
import com.github.r0306.AntiRelog.Util.Util;

public class Executor implements CommandExecutor, Colors
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
	
		Player player = null;
		
		if (sender instanceof Player)
		{
			
			player = (Player) sender;
			
		}
	
		if (cmd.getName().equalsIgnoreCase("ar") || cmd.getName().equalsIgnoreCase("antirelog") || cmd.getName().equalsIgnoreCase("arl"))
		{
			
			if (args.length == 0)
			{
				AntiRelogNPC.spawnNPC(player);
				displayIntro(sender);
				
			}
			
			else if (args.length == 1)
			{
				
				if (args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("time"))
				{
					
					if (playerCheck(sender))
					{
						
						getRemainingTime(player);
						
					}
					
				}
				
				else
				{
					
					unknownCommand(sender);
					
				}
				
			}
			
			else if (args.length == 2)
			{
				
				if (args[0].equalsIgnoreCase("unban"))
				{
					
					if (Util.canUnban(sender))
					{

						try
						{
						
							unbanPlayer(sender, args[1]);
						
						} catch (IOException e) {

							e.printStackTrace();
						
						}
						
					}
					
					else
					{
						
						noPermissions(sender);
						
					}
					
				}
				
				else
				{
					
					unknownCommand(sender);
					
				}
				
			}
			
			else
			{
				
				unknownCommand(sender);
				
			}
			
		}
		
		return true;
	
	}
	
	public void displayIntro(CommandSender sender)
	{
		
		sender.sendMessage(ChatColor.GRAY + "[AntiRelog] This is version " + version + ".");
		
	}
	
	public void displayRemainingTime(Player player)
	{
		
		
		
	}
	
	public boolean playerCheck(CommandSender sender)
	{
		
		if (sender instanceof Player)
		{
			
			return true;
			
		}
		
		sender.sendMessage(name + ChatColor.RED + "You must be a player to use this command!");
		
		return false;
		
	}
	
	public void getRemainingTime(Player player)
	{
		
		if (DataBase.isInCombat(player))
		{
			
			long end = DataBase.getEndingTime(player);
			
			if (!Clock.isEnded(end))
			{
				
				long remaining = Clock.getElapsed(Clock.getTime(), end);
				
				player.sendMessage(name + ChatColor.DARK_AQUA +  "You have " + remaining + " seconds before combat ends.");
				
			}
			
		}
		else
		{
			
			player.sendMessage(name + ChatColor.GREEN + "You are not currently in combat.");
			
		}
		
	}
	
	public void unbanPlayer(CommandSender sender, String player) throws IOException
	{
		
		if (DataBase.isBanned(player))
		{
			
			DataBase.unbanPlayer(player);
			
			sender.sendMessage(name + ChatColor.GREEN + ChatColor.GREEN + player + " was unbanned.");
			
			AntiRelog.logger.log(player, sender, 3);
			
		}
		else
		{
			
			sender.sendMessage(name + ChatColor.RED + "Player " + player + " was not banned. Check the spelling and case and try again.");
			
		}
		
	}
	
	public void noPermissions(CommandSender sender)
	{
		
		sender.sendMessage(name + ChatColor.RED + "You do not have permission!");
		
	}
	
	public void unknownCommand(CommandSender sender)
	{
		
		sender.sendMessage(name + ChatColor.RED + "Unknown command.");
		
	}
		
}
