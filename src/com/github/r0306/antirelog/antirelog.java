package com.github.r0306.antirelog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class antirelog extends JavaPlugin{
	
	public static boolean WorldGuard = false;
	public static boolean Factions = false;
	public static boolean Citizens = false;
	List<String> list = Arrays.asList("tp", "warp", "home", "tpa", "creative");
	private MyTask task;
	private Executor myExecutor;
	public static Logger log = Logger.getLogger("Minecraft");
	String[] cmdd = null;
	
	public void onEnable() {

		loadConfiguration();
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new FreezeCommand(this), this);
        getServer().getPluginManager().registerEvents(new LogPrevention(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new PreLogin(this), this);
    	if (this.getConfig().getStringList("DisallowedCMDs") != null) {
    		String commands = this.getConfig().getStringList("DisallowedCMDs").toString().replace("[", "");
    		String commands1 = commands.replace("]", "");
    		cmdd = commands1.split(", ");
    		}
//        task = new MyTask(this);
        log.addHandler(new Handler() {
			@Override
			public void publish(LogRecord logRecord) {
                String mystring = logRecord.getMessage();
                if(mystring.contains(" lost connection: "))
                {
                        String myarray[] = mystring.split(" ");
                        String DisconnectMessage = myarray[3];
                        LogPrevention.DisconnectMsg = DisconnectMessage;
                }
			}

			@Override
			public void flush() {

				
			}

			@Override
			public void close() throws SecurityException {
				
			}
            
        });
	
		System.out.println("AntiRelog version [" + getDescription().getVersion() + "] loaded.");
    	myExecutor = new Executor(this);
    	getCommand("ar").setExecutor(myExecutor);
    	getCommand("antirelog").setExecutor(myExecutor);
        PermissionManager permissionsExPlugin = null;
		Plugin pex = this.getServer().getPluginManager().getPlugin("PermissionsEx");
		if (pex == null) {
			System.out.println("[AntiRelog] Permissions system not detected. Defaulting to OP.");
		} 
		else if (! pex.isEnabled()) {
			System.out.println("[AntiRelog] PermissionsEX is not enabled. Defaulting to OP.");
		} else {
	        permissionsExPlugin = PermissionsEx.getPermissionManager();
			System.out.println("[AntiRelog] Using PermissionsEX.");
		}
        Plugin wg = this.getServer().getPluginManager().getPlugin("WorldGuard");
        if (wg == null) {
        	System.out.println("[AntiRelog] WorldGuard not detected. But thats okay! :)");
        } else {
        	WorldGuard = true;
        	System.out.println("[AntiRelog] Using WorldGuard configurations for PVP zones.");
        }
        Plugin fact = this.getServer().getPluginManager().getPlugin("Factions");
        if (fact == null) {
        	System.out.println("[AntiRelog] Factions not detected. But thats okay! :)");
        } else {
        	Factions = true;
        	System.out.println("[AntiRelog] Getting Factions areas for PVP zones.");
        }
        Plugin citizen = this.getServer().getPluginManager().getPlugin("Citizens");
        if (fact == null) {
        	System.out.println("[AntiRelog] Citizens not detected. But thats okay! :)");
        } else {
        	Citizens = true;
        	System.out.println("[AntiRelog] Citizens compitibility is enabled.");
        }
	}

	public void onDisable() {
		
		System.out.println("AntiRelog version [" + getDescription().getVersion() + "] unloaded");
}
	public void loadConfiguration() {
		FileConfiguration cfg = this.getConfig();
		FileConfigurationOptions cfgOptions = cfg.options();
		cfg.addDefault("MOTDToggle", "on");
		cfg.addDefault("MOTD", "Welcome to the server.");
		cfg.addDefault("StunMSG", "There's no running away from a battle!");
		cfg.addDefault("StunDuration", 7);
		cfg.addDefault("BanDuration", 5);
		cfg.addDefault("PlayerBanMsg", "You have been banned for 5 minutes due to PVP logging.");
		cfg.addDefault("TempBanMsgToggle", "on");
		cfg.addDefault("TempBanMsg", "Cowards will not be tolerated on this server.");
		cfg.addDefault("UnbanMSGToggle", "on");
		cfg.addDefault("UnbanMSG", "You logged off during PVP and as a result you have lost your items.");
		cfg.addDefault("CMDToggle", "on");
		if (!this.getConfig().contains("DisallowedCMDs")) {
		cfg.addDefault("DisallowedCMDs", list);
		}
		cfgOptions.copyDefaults(true);
		cfgOptions.header("This is the AntiRelog configuration file." + System.getProperty( "line.separator" ) + "Editing this file with Notepad++ is strongly recommended." + System.getProperty( "line.separator" ) +  "Save the file and reload the server after you are done editing for changes to take place." + "Here are the explanations for each option:" + System.getProperty( "line.separator" ) + "MOTDToggle: Set to true or on to turn on MOTD. Set to anything else to turn it off." + System.getProperty( "line.separator" ) + "MOTD: This field contains the message that players will see when they join the server." + System.getProperty( "line.separator" ) + "StunMSG: This sets the message shown if players try to use a command during PVP." + System.getProperty( "line.separator" ) + "StunDuration: This defines the amount of time in SECONDS that players must wait before using commands if they are hit. Set to 0 if you want to disable this feature." + System.getProperty( "line.separator" ) + "BanDuration: This defines the amount of time in MINUTES that a player will be banned if he/she logs off while in combat. Set to 0 to disable this feature." + System.getProperty( "line.separator" ) + "PlayerBanMsg: Players who have been temporarily banned will see this message if they try to connect." +  System.getProperty( "line.separator" ) + "TempBanMsgToggle: Set this to on or true to alert the server when someone logs off during PVP." + System.getProperty( "line.separator" ) + "TempBanMsg: This message will be displayed to everyone on the server when a player logs off while in combat." + System.getProperty( "line.separator" ) + "UnbanMSGToggle: Set this to on or true to send a message to players who have been unbanned when they log on." + System.getProperty( "line.separator" ) + "UnbanMSG: Message players will see when they are unbanned and log back on." + System.getProperty( "line.separator" ) + "CMDToggle: Set this to on block specific commands in PVP. Set to off to disable this function." + System.getProperty( "line.separator" ) + "DisallowedCMDs: This is a list of the disallowed commands. Add to the list using the same format." + System.getProperty( "line.separator" ) + "To block all commands at once, use '*'.");
		cfgOptions.copyHeader(true);
		saveConfig();
	}

}
	   
