package com.github.r0306.antirelog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class antirelog extends JavaPlugin{
	
	public void onEnable() {

		loadConfiguration();
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new FreezeCommand(this), this);
        getServer().getPluginManager().registerEvents(new LogPrevention(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
		System.out.println("AntiRelog version [" + getDescription().getVersion() + "] loaded.");
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
        
	}
//hi	
	public void onDisable() {
		System.out.println("AntiRelog version [" + getDescription().getVersion() + "] unloaded");
}
	public void loadConfiguration() {
		FileConfiguration cfg = this.getConfig();
		FileConfigurationOptions cfgOptions = cfg.options();
		cfg.addDefault("MOTD", "Welcome to our server! This is the default message. To change it, go to the AntiRelog folder and open up Config file.");
		cfg.addDefault("StunMSG", "There's no running away from a battle!");
		cfg.addDefault("StunDuration", 7);
		cfg.addDefault("BanDuration", 5);
		cfg.addDefault("TempBanMsg", "Cowards will not be tolerated on this server.");
		cfgOptions.copyDefaults(true);
		cfgOptions.header("This is the AntiRelog configuration file." + System.getProperty( "line.separator" ) + "Editing this file with Notepad++ is strongly recommended." + System.getProperty( "line.separator" ) +  "Save the file and reload the server after you are done editing for changes to take place." + "Here are the explanations for each option:" + System.getProperty( "line.separator" ) + "MOTD: This field contains the message that players will see when they join the server." + System.getProperty( "line.separator" ) + "StunMSG: This sets the message shown if players try to use a command during PVP." + System.getProperty( "line.separator" ) + "StunDuration: This defines the amount of time in SECONDS that players must wait before using commands if they are hit. Set to 0 if you want to disable this feature." + System.getProperty( "line.separator" ) + "BanDuration: This defines the amount of time in MINUTES that a player will be banned if he/she logs off while in combat. Set to 0 to disable this feature." + System.getProperty( "line.separator" ) + "TempBanMsg: This message will be displayed to everyone on the server when a player logs off while in combat.");
		cfgOptions.copyHeader(true);
		saveConfig();
	}
	
}