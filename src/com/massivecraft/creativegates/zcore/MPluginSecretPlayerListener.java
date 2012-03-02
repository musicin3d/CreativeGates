package com.massivecraft.creativegates.zcore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import com.massivecraft.creativegates.zcore.persist.EM;
import com.massivecraft.creativegates.zcore.persist.Entity;
import com.massivecraft.creativegates.zcore.persist.EntityCollection;
import com.massivecraft.creativegates.zcore.persist.PlayerEntityCollection;

public class MPluginSecretPlayerListener implements Listener
{
	private MPlugin p;
	public MPluginSecretPlayerListener(MPlugin p)
	{
		this.p = p;
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		if (event.isCancelled()) return;

		if (p.handleCommand(event.getPlayer(), event.getMessage()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event)
	{
		if (event.isCancelled()) return;
		
		if (p.handleCommand(event.getPlayer(), event.getMessage()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPreLogin(PlayerPreLoginEvent event)
	{
		for (EntityCollection<? extends Entity> ecoll : EM.class2Entities.values())
		{
			if (ecoll instanceof PlayerEntityCollection)
			{
				ecoll.get(event.getName());
			}
		}
	}
}
