package com.github.r0306.AntiRelog.Util;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;


public class Configuration implements Colors
{

	private static FileConfiguration config = Plugin.getPlugin().getConfig();
	
	private static String npcBattleMessage = red + "Your NPC is currently in battle. Please wait until combat ends before logging on.";
	
	private static boolean notifyUpdate = config.getBoolean("Update-Notification");
	
	private static boolean motdEnabled = config.getBoolean("MOTD.Enabled");
		
	private static String motdMessage = Util.colorizeText(config.getString("MOTD.Message"));
	
	private static boolean mobLoggerEnabled = config.getBoolean("Mobs.Logger-Enabled");
	
	private static boolean passiveLoggerEnabled = config.getBoolean("Mobs.Passive-Logger-Enabled");
	
	private static boolean tagMessageEnabled = config.getBoolean("PvP.Tag-Message.Enabled");
	
	private static String tagMessage = Util.colorizeText(config.getString("PvP.Tag-Message.Tag"));
	
	private static String unTagMessage = Util.colorizeText(config.getString("PvP.Tag-Message.Un-Tag"));
	
	private static int banDuration = config.getInt("PvP.Ban.Duration");
	
	private static String banMessage = Util.colorizeText(config.getString("PvP.Ban.Message"));
	
	private static boolean broadcastEnabled = config.getBoolean("PvP.Ban.Broadcast-Enabled");
	
	private static String broadcastMessage = Util.colorizeText(config.getString("PvP.Ban.Broadcast-Message"));
	
	private static boolean unbanMessageEnabled = config.getBoolean("PvP.Unban.Message-Enabled");
	
	private static String unbanMessage = Util.colorizeText(config.getString("PvP.Unban.Message"));
	
	private static List<String> runCommands = config.getStringList("PvP.Unban.Run-Commands");
	
	private static boolean npcEnabled = config.getBoolean("PvP.CombatLog.NPC.Enabled");
	
	private static int npcDespawnTime = config.getInt("PvP.CombatLog.NPC.Despawn-Delay");
	
	private static boolean npcAggro = config.getBoolean("PvP.CombatLog.NPC.Default-Aggressive");
	
	private static boolean dropItems = config.getBoolean("PvP.CombatLog.Drop.Items");
	
	private static boolean dropArmor = config.getBoolean("PvP.CombatLog.Drop.Armor");
	
	private static boolean dropExp = config.getBoolean("PvP.CombatLog.Drop.Exp");
	
	private static boolean disallowAll = config.getBoolean("PvP.Command.Disallow-All");
	
	private static int freezeDuration = config.getInt("PvP.Command.Freeze-Duration");
	
	private static String freezeMessage = Util.colorizeText(config.getString("PvP.Command.Freeze-Message"));
	
	private static List<String> disallowedCommands = config.getStringList("PvP.Command.Disallowed-List");
	
	private static List<String> whiteList = config.getStringList("PvP.Command.WhiteList");
	
	public static String getNPCBattleMessage()
	{
		
		return npcBattleMessage;
		
	}
	
	public static boolean updateEnabled()
	{
		
		return notifyUpdate;
		
	}
			
	public static boolean motdEnabled()
	{
	
		return motdEnabled;
		
	}
	
	public static String getMotd()
	{
		
		return motdMessage;
	
	}
	
	public static boolean mobLoggerEnabled()
	{
		
		return mobLoggerEnabled;
		
	}
	
	public static boolean passiveLoggerEnabled()
	{
		
		return passiveLoggerEnabled;
		
	}
	
	public static boolean tagMessageEnabled()
	{
		
		return tagMessageEnabled;
		
	}
	
	public static String getTagMessage()
	{
		
		return tagMessage;
		
	}
	
	public static String getUnTagMessage()
	{
		
		return unTagMessage;
		
	}
	
	public static int getBanDuration()
	{
		
		return banDuration * 1200;
		
	}
	
	public static String getBanMessage()
	{
		
		return banMessage;
		
	}
	
	public static boolean broadcastEnabled()
	{
		
		return broadcastEnabled;
		
	}
	
	public static String getBroadcastMessage()
	{
		
		return broadcastMessage;
		
	}
	
	public static boolean unbanMessageEnabled()
	{
		
		return unbanMessageEnabled;
		
	}
	
	public static String getUnbanMessage()
	{
		
		return unbanMessage;
		
	}
	
	public static List<String> getCommands()
	{
		
		return runCommands;
		
	}
	
	public static boolean npcEnabled()
	{
		
		return npcEnabled;
		
	}
	
	public static boolean npcAggressive()
	{
		
		return npcAggro;
		
	}
	
	public static int getDespawnTime()
	{
		
		return npcDespawnTime * 20;
		
	}
	
	public static boolean dropItemsEnabled()
	{
		
		return dropItems;
		
	}
	
	public static boolean dropArmorEnabled()
	{
		
		return dropArmor;
		
	}
	
	public static boolean dropExpEnabled()
	{
		
		return dropExp;
	
	}
	
	public static boolean commandDisabled()
	{
		
		return disallowAll;
		
	}
	
	public static int getFreezeDuration()
	{
		
		return freezeDuration * 20;
		
	}
	
	public static String getFreezeMessage()
	{
		
		return freezeMessage;
		
	}
	
	public static List<String> getDisallowedCommands()
	{
		
		return disallowedCommands;
		
	}
	
	public static List<String> getWhiteList()
	{
		
		return whiteList;
		
	}
	
	public static boolean whiteListIsEmpty()
	{
		
		return Configuration.getWhiteList().size() == 0 ||  Configuration.getDisallowedCommands().get(0) == "null";
		
	}
	
	public static boolean commandsIsEmpty()
	{
		
		return Configuration.getDisallowedCommands().size() == 0 || Configuration.getDisallowedCommands().get(0) == "null";
		
	}
	
	public static boolean runCommandsIsEmpty()
	{
		
		return Configuration.getDisallowedCommands().size() == 0 || Configuration.getDisallowedCommands().get(0) == "null";
		
	}

}
