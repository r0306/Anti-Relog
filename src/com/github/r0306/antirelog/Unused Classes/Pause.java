package com.github.r0306.antirelog;

public class Pause implements Runnable{

private antirelog plugin;		
public Pause (antirelog plugin) {
	this.plugin = plugin;
}
public int id;
public int getID() {
	return id;
}
public void setID(int id) {
	this.id = id;
}
@Override
public void run() {
	int delay = plugin.getConfig().getInt("StunDuration") * 20;
   plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			if (DamageListener.timerTask == true) {
			DamageListener dl = new DamageListener(plugin);
			dl.Then();
			} 
			else if (DamageListener.timerTask == false) {
				plugin.getServer().getScheduler().cancelTask(id);
				DamageListener.timerTask = true;
			}
		}
	}, delay);
}
}
