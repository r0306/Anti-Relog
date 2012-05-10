package com.github.r0306.antirelog;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MyTask implements Runnable
{

private antirelog plugin;
public int id = -1;
private WaitDuration duration;
 
   /**
   * Generic constructor
   * @param Plugin task is associated with
 * @return 
   */		
public MyTask (antirelog plugin)
{
    this.plugin = plugin;
    if (plugin != null) {
		id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 1L, 5L);	
		}
    else if (id == -1) {
            plugin.getLogger().warning("[AntiRelog] An Error Has Occurred.");
        }
    }

    
   @Override
   public void run()
   {
 
    
/*
	   if (DamageListener.Alreadydmg.contains(DamageListener.realplayer1) && DamageListener.Alreadydmg.contains(DamageListener.realplayer2)) {
		   for (int i = 0; i < 1; i ++) {
		   plugin.getServer().getScheduler().cancelTask(WaitDuration.WaitTask);
		   duration = new WaitDuration(plugin);
		   plugin.getServer().getScheduler().cancelTask(id);

		   }
	   }
*/

   } 

   
   public boolean stopTask()
   {
        if(id != -1)
        {
            plugin.getServer().getScheduler().cancelTask(id);
            return true;

        }
        return false;
   }
}