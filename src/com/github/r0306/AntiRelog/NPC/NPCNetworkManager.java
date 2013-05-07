package com.github.r0306.AntiRelog.NPC;

import java.io.IOException;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;

import net.minecraft.server.v1_5_R3.Connection;
import net.minecraft.server.v1_5_R3.NetworkManager;
import net.minecraft.server.v1_5_R3.Packet;

/**
*
* @author martin
*/

public class NPCNetworkManager extends NetworkManager
{

	public NPCNetworkManager() throws IOException
	{
	
		super(((CraftServer) Bukkit.getServer()).getServer().getLogger(), new NullSocket(), "NPC Manager", new Connection() {
	
			@Override
			public boolean a() 
			{
	
				return true;
	
			}

		}, null);
	
		try {
	
			Field f = NetworkManager.class.getDeclaredField("n");
			f.setAccessible(true);
		f.set(this, false);
		} catch (Exception e) {
		e.printStackTrace();
	
		}
	
	}
	
	@Override
	public void a(Connection nethandler)
	{
	
	}
	
	@Override
	public void queue(Packet packet) 
	{
	
	}
	
	@Override
	public void a(String s, Object... aobject) 
	{
	
	}
	
	@Override
	public void a() 
	{

	}
	
}