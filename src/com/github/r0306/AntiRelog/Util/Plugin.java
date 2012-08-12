package com.github.r0306.AntiRelog.Util;

import com.github.r0306.AntiRelog.AntiRelog;

public class Plugin 
{

	private static AntiRelog plugin;
	
	@SuppressWarnings("static-access")
	public Plugin(AntiRelog plugin)
	{
		
		this.plugin = plugin;
		
	}
	
	public static AntiRelog getPlugin()
	{
		
		return plugin;
		
	}
	
	public static void shutDown()
	{
		
		plugin = null;
		
	}
	
}
