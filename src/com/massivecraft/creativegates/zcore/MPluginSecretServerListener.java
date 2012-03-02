package com.massivecraft.creativegates.zcore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class MPluginSecretServerListener implements Listener
{
	private MPlugin p;
	public MPluginSecretServerListener(MPlugin p)
	{
		this.p = p;
	}
	
	// This method is not perfect. It says unknown console command.
	@EventHandler
	public void onServerCommand(ServerCommandEvent event)
	{
		if (event.getCommand().length() == 0) return;
		
		if (p.handleCommand(event.getSender(), event.getCommand()))
		{
			event.setCommand("");
		}
	}
	
}
