package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDrop implements Listener {

	Frame frame;
	
	public PlayerDrop(Frame frame) {
		this.frame = frame;
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		if(frame.flags.get("drops")==false) e.setCancelled(true);
	}
	
}
