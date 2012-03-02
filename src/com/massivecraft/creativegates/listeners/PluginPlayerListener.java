package com.massivecraft.creativegates.listeners;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.massivecraft.creativegates.Conf;
import com.massivecraft.creativegates.Gate;
import com.massivecraft.creativegates.CreativeGates;
import com.massivecraft.creativegates.Gates;
import com.massivecraft.creativegates.Lang;
import com.massivecraft.creativegates.Permission;
import com.massivecraft.creativegates.WorldCoord;
import com.massivecraft.creativegates.event.CreativeGatesTeleportEvent;

public class PluginPlayerListener implements Listener
{
	CreativeGates p = CreativeGates.p;
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if (event.isCancelled()) return;
		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
		
		// We look one up due to half blocks.
		Block blockToTest = event.getTo().getBlock().getRelative(BlockFace.UP);
		
		// Fast material check 
		if (blockToTest.getType() != Material.STATIONARY_WATER) return;
		
		// Find the gate if there is one
		Gate gateFrom = Gates.i.findFromContent(blockToTest);
		if (gateFrom == null) return;
		
		// Is the gate intact?
		if ( ! gateFrom.isIntact())
		{
			gateFrom.close();
			return;
		}
		
		// Can the player use gates?
		if ( ! Permission.USE.has(event.getPlayer(), true)) return;
		
		// Find the target location
		Gate gateTo = gateFrom.getMyTargetGate();
		Location targetLocation = gateTo == null ? null : gateTo.getMyOwnExitLocation();
		if (targetLocation == null)
		{
			event.getPlayer().sendMessage(p.txt.parse(Lang.useFailNoTargetLocation));
			return;
		}
		
		HashSet<Material> frameMaterials = new HashSet<Material>();
		for(int id : gateFrom.frameMaterialIds)
		{
		    frameMaterials.add(Material.getMaterial(id));
		}
		
		CreativeGatesTeleportEvent gateevent = new CreativeGatesTeleportEvent(event, targetLocation, frameMaterials, gateFrom, gateTo);
		p.getServer().getPluginManager().callEvent(gateevent);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.isCancelled()) return;
		
		// We are only interested in clicks on a block with a wand
		if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getPlayer().getItemInHand().getTypeId() != Conf.wand) return;
		
		Block clickedBlock = event.getClickedBlock();
		Player player = event.getPlayer();
		
		// Did we hit an existing gate?
		// In such case send information.
		Gate gate = Gates.i.findFrom(clickedBlock);
		if (gate != null)
		{
			gate.informPlayer(player);
			return;
		}
		
		// Did we hit a diamond block?
		if (clickedBlock.getTypeId() == Conf.block)
		{
			// create a gate if the player has the permission
			if (Permission.CREATE.has(player, true))
			{
				Gates.i.open(new WorldCoord(clickedBlock), player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerBucketFill(PlayerBucketFillEvent event)
	{
		if (event.isCancelled())
		{
			return;
		}
		
		if ( Gates.i.findFromContent(event.getBlockClicked()) != null )
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
		if (event.isCancelled())
		{
			return;
		}
		
		if ( Gates.i.findFromContent(event.getBlockClicked().getRelative(event.getBlockFace())) != null )
		{
			event.setCancelled(true);
		}
	}
}
