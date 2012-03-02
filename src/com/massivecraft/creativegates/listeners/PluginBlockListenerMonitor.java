package com.massivecraft.creativegates.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.massivecraft.creativegates.Gate;
import com.massivecraft.creativegates.CreativeGates;
import com.massivecraft.creativegates.Gates;


public class PluginBlockListenerMonitor implements Listener
{
	CreativeGates p = CreativeGates.p;
	
	// Destroy the gate if the frame breaks
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (event.isCancelled()) return;
		
		Gate gate = Gates.i.findFromFrame(event.getBlock());
		if (gate != null)
		{
			gate.close();
		}
	}
}
