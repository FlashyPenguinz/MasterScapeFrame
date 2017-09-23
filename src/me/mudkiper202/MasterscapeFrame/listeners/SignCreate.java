package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.kits.Kit;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignCreate implements Listener {
	
	Frame frame;
	
	public SignCreate(Frame frame) {
		this.frame = frame;
	}
	
	/*
	[Kit]
	Basic
	Right click
	to get!
	**/
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		for(int i=0;i<e.getLines().length;i++) {
			if(e.getPlayer().hasPermission("scape.colorsign")) {
				e.setLine(i, ChatColor.translateAlternateColorCodes('&', e.getLine(i)));
			}
		}
		if(e.getPlayer().hasPermission("scape.kit.sign")) {
			if(e.getLine(0).equalsIgnoreCase("[kit]")) {
				for(Kit kit: frame.kitManager.kits) {
					if(kit.getName().equalsIgnoreCase(e.getLine(1))) {
						e.setLine(0, ChatColor.BLACK+"["+ChatColor.DARK_GRAY+"Kit"+ChatColor.BLACK+"]");
						e.setLine(1, ChatColor.DARK_GRAY+kit.getName());
						e.setLine(2, ChatColor.GREEN+"Right Click");
						e.setLine(3, ChatColor.GREEN+"To Get!");
					}
				}
			}
		}
	}
	
}
