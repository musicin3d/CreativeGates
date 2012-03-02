package com.massivecraft.creativegates.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.massivecraft.creativegates.Gate;
import com.massivecraft.creativegates.CreativeGates;
import com.massivecraft.creativegates.Gates;
import com.massivecraft.creativegates.Permission;


public class PluginBlockListener implements Listener
{
	CreativeGates p = CreativeGates.p;
	
	@EventHandler
	public void onBlockPistonExtend(BlockPistonExtendEvent event)
	{
		if (event.isCancelled())
		{
			return;
		}
		
		for (Block block : event.getBlocks())
		{
			if (Gates.i.findFrom(block) != null)
			{
				event.setCancelled(true);
				return;
			}
		}
	}
	
	// The purpose is to stop the water from falling
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event)
	{
		if (event.isCancelled()) return;
		
		Block blockFrom = event.getBlock();
		boolean isWater = blockFrom.getTypeId() == 9;
		
		if ( ! isWater)
		{
			return;
		}
		
		if (Gates.i.findFrom(blockFrom) != null)
		{
			event.setCancelled(true);
		}
	}
	
	// The gate content is invulnerable
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (event.isCancelled()) return;
		
		Gate gate = Gates.i.findFromContent(event.getBlock()); 
		if (gate != null)
		{
			event.setCancelled(true);
		}
	}
	
	// Is the player allowed to destroy gates?
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (event.isCancelled()) return;
		
		Gate gate = Gates.i.findFromFrame(event.getBlock());
		if (gate == null)
		{
			return;
		}
		
		// A player is attempting to destroy a gate. Can he?
		if ( ! Permission.DESTROY.has(event.getPlayer(), true))
		{
			event.setCancelled(true);
		}
	}
}
