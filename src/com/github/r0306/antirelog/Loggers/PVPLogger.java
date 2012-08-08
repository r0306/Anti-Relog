package com.github.r0306.AntiRelog.Loggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PVPLogger
{

	File log;
	
	public PVPLogger() throws IOException
	{
		
		log = new File("PVP Bans.txt");
		
		if (!log.exists())
		{
			
			System.out.println("[AntiRelog] Creating new log file...");
			
			log.createNewFile();
			
		}
		
	}
	
	public void log (String player, CommandSender sender, int reason) throws IOException
	{
		
	    FileWriter fileWriter = new FileWriter(log, true);

	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	    
	    Calendar c = Calendar.getInstance();
	   
	    String stringdate = c.getTime().toString();
		
		if (reason == 0)
		{
			
    	    bufferedWriter.write(stringdate + " " + player + " logged off during combat and was temporarily banned." + System.getProperty( "line.separator" ));
			
		}
		
		else if (reason == 1)
		{
			
			bufferedWriter.write(stringdate + " " + player + " has been unbanned." + System.getProperty( "line.separator" ));
			
		}
		
		else if (reason == 2)
		{
			
			bufferedWriter.write(stringdate + " " + player + " has been unbanned due to server reloading or shutting down." + System.getProperty( "line.separator" ));
			
		}
		
		else if (reason == 3)
		{
			
    	    bufferedWriter.write(stringdate + " " + player + " has been unbanned by " + sender.getName() + "." + System.getProperty( "line.separator" ));
			
		}
		
	    bufferedWriter.close();
		
	}
	
}
