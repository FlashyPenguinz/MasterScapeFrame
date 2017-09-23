package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.area.Area;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
	
	Frame frame;
	
	public BlockBreak(Frame pl) {
		frame = pl;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(frame.flags.get("universalBlockBreak")==false&&!(p.hasPermission("scape.blockbreak"))) e.setCancelled(true);
		for(Area area : frame.areaManager.getAreas()) {
			if(area.containsPlayer(p)) {
				if(area.getRule("blockbreak").getState()==false) {
					if(!(p.hasPermission("scape.blockbreak"))) e.setCancelled(true);
				}
			}
		}
	}
}
