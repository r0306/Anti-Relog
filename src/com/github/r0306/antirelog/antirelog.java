package com.github.r0306.AntiRelog;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.r0306.AntiRelog.Listeners.DamageListener;
import com.github.r0306.AntiRelog.Listeners.DeathListener;
import com.github.r0306.AntiRelog.Listeners.FreezeCommand;
import com.github.r0306.AntiRelog.Listeners.LogPrevention;
import com.github.r0306.AntiRelog.Listeners.LoginListener;
import com.github.r0306.AntiRelog.Loggers.LogHandler;
import com.github.r0306.AntiRelog.Loggers.PVPLogger;
import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Plugin;
import com.github.r0306.AntiRelog.Util.Util;

public class AntiRelog extends JavaPlugin
{

	public static PVPLogger logger;
	
	public void onEnable()
	{
		
		new Plugin(this);
		
		new LogHandler();
		
		try
		{
		
			logger = new PVPLogger();
		
		} catch (IOException e) {

			e.printStackTrace();

		}
		
		registerExecutors();
		
		registerListeners();
		
		loadConfiguration();
		
		System.out.println("AntiRelog version [" + getDescription().getVersion() + "] loaded");
		
	}
	
	public void onDisable()
	{
		
		try
		{
		
			shutdownSequence();
		
		} catch (IOException e) {

			e.printStackTrace();

		}
		
		System.out.println("AntiRelog version [" + getDescription().getVersion() + "] unloaded");
		
	}
	
	public void registerExecutors()
	{
		
		getCommand("ar").setExecutor(new Executor());
		getCommand("arl").setExecutor(new Executor());
		getCommand("antirelog").setExecutor(new Executor());

	}
	
	public void registerListeners()
	{
		
		getServer().getPluginManager().registerEvents(new DamageListener(), this);
		getServer().getPluginManager().registerEvents(new DeathListener(), this);
		getServer().getPluginManager().registerEvents(new LoginListener(), this);
		getServer().getPluginManager().registerEvents(new LogPrevention(), this);
		getServer().getPluginManager().registerEvents(new FreezeCommand(), this);
		
	}
	
	public void shutdownSequence() throws IOException
	{
		
		for (String s : DataBase.getBanned())
		{
			
			logger.log(s, null, 2);
			
		}
		
	}
			
	private void loadConfiguration()
	{
		
		FileConfiguration cfg = this.getConfig();
		FileConfigurationOptions cfgOptions = cfg.options();
		
		cfg.addDefault("MOTD.Enabled", true);
		cfg.addDefault("MOTD.Message", "<yellow>Welcome to the server.");
		
		cfg.addDefault("Mobs.Logger-Enabled", false);
		cfg.addDefault("Mobs.Passive-Logger-Enabled", false);
		
		cfg.addDefault("PvP.Tag-Message.Enabled", true);
		cfg.addDefault("PvP.Tag-Message.Tag", "<red>You have been tagged and are now in combat. Logging off will result in penalty.");
		cfg.addDefault("PvP.Tag-Message.Un-Tag", "<green>You are not in combat anymore.");
				
		cfg.addDefault("PvP.Ban.Duration", 5);
		cfg.addDefault("PvP.Ban.Message", "<red>You have been banned for 5 minutes due to PVP logging.");
		
		cfg.addDefault("PvP.Ban.Broadcast-Enabled", true);
		cfg.addDefault("PvP.Ban.Broadcast-Message", "<green>Cowards will not be tolerated on this server.");

		cfg.addDefault("PvP.Unban.Message-Enabled", true);
		cfg.addDefault("PvP.Unban.Message", "<darkaqua>You logged off during PVP and as a result you have lost your items.");
		
		cfg.addDefault("PvP.CombatLog.NPC", false);
					
		cfg.addDefault("PvP.CombatLog.Drop.Items", true);
		cfg.addDefault("PvP.CombatLog.Drop.Armor", true);
		cfg.addDefault("PvP.CombatLog.Drop.Exp", true);
		
		cfg.addDefault("PvP.Command.Disallow-All", false);
		
		cfg.addDefault("PvP.Command.Freeze-Duration", 7);
		cfg.addDefault("PvP.Command.Freeze-Message", "<red>There's no running away from a battle!");

		cfg.addDefault("PvP.Command.Disallowed-List", Arrays.asList("tp", "warp", "home", "tpa", "creative"));
		
		cfgOptions.copyDefaults(true);
		
		cfgOptions.header(getHeader());
		
		cfgOptions.copyHeader(true);
		
		saveConfig();
		
	}
	
	private String getHeader()
	{
		
		String newLine = Util.newLine;
		
		return "This is the AntiRelog configuration file." + newLine + 
			   "Editing this file with Notepad++ is strongly recommended." + newLine +  
			   "Save the file and reload the server after you are done editing for changes to take place." + 
			   "Here are the explanations for each option:" + newLine + 
			   "MOTD: This field contains the message that players will see when they join the server. Set enabled to true or false to toggle on or off." + newLine +
			   "MOTD Message: Displays the message players will see when they join the server." + newLine + newLine +
			   "Mob Logger: Set this to true enable AntiRelog for hostile mobs. Players who log off during combat will face the same consequences as if they logged against another player." + newLine +
			   "Passive Mob Logger: Set this to true to enable Mob Logger for passive mobs. Must have Mob Logger enabled to use this." + newLine + newLine +
			   "Tag Message: This is the message sent when players enter combat." + newLine + 
			   "UnTag Message: This is the message sent when players are not in combat anymore." + newLine + newLine +
			   "Ban Duration: This defines the amount of time in MINUTES that a player will be banned if he/she logs off while in combat. Set to 0 to disable this feature." + newLine +
			   "Ban Message: Players who have been temporarily banned from PvP logging will see this message if they try to connect." + newLine +
			   "Ban Broadcast: Set this to true to alert all players on the server when someone combat logs." + newLine +
			   "Ban Broadcast Message: This message will be displayed to everyone on the server when a player logs off while in combat." + newLine + newLine +
			   "Unban Message Enabled: Set this to true to send a message to players when they log on after being unbanned." + newLine +
			   "Unban Message: This is the message that will be sent to players when they log on after being unbanned." + newLine + newLine +
			   "NPC: Setting this to true spawns an NPC in place of the combat logger. The NPC will fight back and drop the items if killed. If the NPC kills the attacker, it will despawn." + newLine +
			   "Drops: If these are set to true, the player will drop that equipment/item upon combat logging. If NPCs are enabled, the NPC will drop those items upon being killed." + newLine + newLine +
			   "Disallow All: Set this to true to disallow all commands while in PVP. (Overrides command list)." + newLine +
			   "Freeze Duration: This defines the amount of time in SECONDS that players must wait before using commands if they are hit. Set to 0 if you want to disable this feature." + newLine + 
			   "Freeze Message: This sets the message shown if players try to use a command during PVP." + newLine + 
               "DisallowedCMDs: This is a list of the disallowed commands. Add to the list using the same format as shown. Set to null to allow all commands.";
		
	}
	
}
