package com.github.r0306.AntiRelog;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.r0306.AntiRelog.Listeners.DamageListener;
import com.github.r0306.AntiRelog.Listeners.DeathListener;
import com.github.r0306.AntiRelog.Listeners.FreezeCommand;
import com.github.r0306.AntiRelog.Listeners.LogPrevention;
import com.github.r0306.AntiRelog.Listeners.LoginListener;
import com.github.r0306.AntiRelog.Listeners.ModifyworldFix;
import com.github.r0306.AntiRelog.Listeners.NPCListener;
import com.github.r0306.AntiRelog.Loggers.LogHandler;
import com.github.r0306.AntiRelog.Loggers.PVPLogger;
import com.github.r0306.AntiRelog.NPC.NPCManager;
import com.github.r0306.AntiRelog.Storage.DataBase;
import com.github.r0306.AntiRelog.Util.Colors;
import com.github.r0306.AntiRelog.Util.Plugin;
import com.github.r0306.AntiRelog.Util.Util;

public class AntiRelog extends JavaPlugin implements Colors
{

	public static PVPLogger logger;
	public static NPCManager npcHandler;
	public static org.bukkit.plugin.Plugin modifyWorld;
	
	public void onEnable()
	{
		
		new Plugin(this);
		
		new LogHandler();
		
		loadConfiguration();
			
		try
		{
		
			logger = new PVPLogger();
		
		} catch (IOException e) {

			e.printStackTrace();

		}
		
		try
		{
		
			DataBase.loadQueue();
		
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		
		} catch (IOException e) {

			e.printStackTrace();
		
		}
				
		npcHandler = new NPCManager(this);
		
		if (getServer().getPluginManager().getPlugin("Modifyworld") != null)
		{
			
			modifyWorld = getServer().getPluginManager().getPlugin("Modifyworld");
			
		}
		
		registerExecutors();
		
		registerListeners();
		
		scheduleRepeatingUpdateCheck();
		
		System.out.println("AntiRelog version [" + getDescription().getVersion() + "] loaded");
		
	}
	
	public void onDisable()
	{
		
		try 
		{
		
			DataBase.saveQueue();
		
		} catch (IOException e) {

			e.printStackTrace();
		
		}
		
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
		getServer().getPluginManager().registerEvents(new NPCListener(), this);
		
		if (modifyWorld != null)
		{
			
			getServer().getPluginManager().registerEvents(new ModifyworldFix(), this);
			
		}
		
	}
	
	public void scheduleRepeatingUpdateCheck()
	{
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
        	public void run()
			{
        	
				try
				{
				
					if (Util.updateAvaliable())
					{
						
						Bukkit.getConsoleSender().sendMessage(aqua + "[AntiRelog] An update is available!");
						
						Bukkit.getConsoleSender().sendMessage(aqua + "Go to " + yellow + Util.getDownload() + aqua + " to update your plugin to version " + Util.getNewVersion() + "!");
						
					}
				
				} catch (Exception e) {

					e.printStackTrace();

				}
					
			}
        	
		}, 0L, 360000L);
		
	}
	
	public void shutdownSequence() throws IOException
	{
		
		for (String s : DataBase.getBanned())
		{
			
			logger.log(s, null, 2);
			
		}
		
		DataBase.clearAll();
		
		logger = null;
		
		npcHandler = null;
		
		modifyWorld = null;
		
		Plugin.shutDown();
		
	}
			
	public void loadConfiguration()
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
		cfg.addDefault("PvP.Unban.Run-Commands", Arrays.asList("say <player> has logged on after being banned (edit the config to change this default runnable command)."));
		
		cfg.addDefault("PvP.CombatLog.NPC.Enabled", false);
		cfg.addDefault("PvP.CombatLog.NPC.Despawn-Delay", 120);
		cfg.addDefault("PvP.CombatLog.NPC.Default-Aggressive", false);
					
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
			   "Save the file and reload the server after you are done editing for changes to take place." + newLine + 
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
			   "Unban Message: This is the message that will be sent to players when they log on after being unbanned." + newLine +
			   "Unban Runnbale Commands: Now, you can run multiple commands when a previously banned player logs on. You can use this along with economy plugins to penalize combat loggers. Set first line of list to 'null' to disable this." + newLine + newLine +
			   "NPC: Setting this to true spawns an NPC in place of the combat logger. The NPC will fight back and drop the items if killed. If the NPC kills the attacker, it will despawn." + newLine +
			   "NPC Despawn Delay: Defines the number of seconds before the NPC is despawned." + newLine +
			   "NPC Default Aggressive: Set this to true to make all spawned NPCs defend themselves and attack the enemy. If set to false, individual permission nodes can be assigned giving the 'antirelog.aggressivenpc' node." + newLine + newLine +
			   "Drops: If these are set to true, the player will drop that equipment/item upon combat logging. If NPCs are enabled, the NPC will drop those items upon being killed." + newLine + newLine +
			   "Disallow All: Set this to true to disallow all commands while in PVP. (Overrides command list)." + newLine +
			   "Freeze Duration: This defines the amount of time in SECONDS that players must wait before using commands if they are hit. Set to 0 if you want to disable this feature." + newLine + 
			   "Freeze Message: This sets the message shown if players try to use a command during PVP." + newLine + 
               "DisallowedCMDs: This is a list of the disallowed commands. Add to the list using the same format as shown. Set first line to 'null' to allow all commands.";
		
	}
	
}
