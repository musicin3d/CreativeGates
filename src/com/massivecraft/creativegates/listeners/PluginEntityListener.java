package com.massivecraft.creativegates.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.massivecraft.creativegates.CreativeGates;
import com.massivecraft.creativegates.Gates;

public class PluginEntityListener implements Listener {
	
	CreativeGates p = CreativeGates.p;
	
	// Gates can not be destroyed by explosions
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event)
	{
		if (event.isCancelled()) return;
		
		for (Block block : event.blockList())
		{
			if (Gates.i.findFrom(block) != null)
			{
				event.setCancelled(true);
				return;
			}
		}
	}
}
