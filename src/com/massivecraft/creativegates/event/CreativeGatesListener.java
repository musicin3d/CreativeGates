package com.massivecraft.creativegates.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class CreativeGatesListener implements Listener
{
	/*@EventHandler
	public void onCustomEvent(Event event)
    {
        if(event instanceof CreativeGatesTeleportEvent)
        {
            onPlayerGateTeleport((CreativeGatesTeleportEvent) event);
        }
    }*/
	
	@EventHandler
    public void onPlayerGateTeleport(CreativeGatesTeleportEvent event) {}
}
