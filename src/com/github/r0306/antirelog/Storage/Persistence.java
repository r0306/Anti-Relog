package com.github.r0306.AntiRelog.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;

import com.github.r0306.AntiRelog.Util.Plugin;

public class Persistence
{

	public static void saveQueue() throws IOException
	{
		
		FileOutputStream fwriter = new FileOutputStream(createNewFile());
		ObjectOutputStream writer = new ObjectOutputStream(fwriter);

		writer.writeObject(DataBase.getLoginQueue());
		
		writer.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Set<Integer>> loadQueue() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		
		HashMap<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();
		
		FileInputStream freader = new FileInputStream(createNewFile());
		
		if (freader.available() > 0)
		{
		
			ObjectInputStream reader = new ObjectInputStream(freader);
			
			map = (HashMap<String, Set<Integer>>) reader.readObject();

			reader.close();
			
		}
		
		return map;
		
	}
	
	public static File createNewFile() throws IOException
	{
		
		File file = new File(Plugin.getPlugin().getDataFolder(), "Data.bin");
		
		if (!file.exists())
		{
			
			Plugin.getPlugin().getDataFolder().mkdirs();
			file.createNewFile();
			
		}
		
		return file;
		
	}
	
}
