package com.github.r0306.antirelog;


public class WaitDuration implements Runnable {
	
	public static int WaitTask = -1;
	public static boolean isOn = false;

	
	private antirelog plugin;		
	public WaitDuration (antirelog plugin) {
		this.plugin = plugin;
		if (plugin != null) {
		WaitTask = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, plugin.getConfig().getInt("StunDuration") * 20);
		}
		if (WaitTask == -1) {
			System.out.println("[AntiRelog] An error has occurred.");
		}
	}
	
	@Override
	public void run() {
		DamageListener.Damagelist.remove(DamageListener.realplayer1);
		DamageListener.Damagelist.remove(DamageListener.realplayer2);
		DamageListener.Alreadydmg.remove(DamageListener.realplayer1);
		DamageListener.Alreadydmg.remove(DamageListener.realplayer2);
		System.out.println("Done");
	}

}
