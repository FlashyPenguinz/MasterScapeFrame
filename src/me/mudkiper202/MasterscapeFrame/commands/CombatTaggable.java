package me.mudkiper202.MasterscapeFrame.commands;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CombatTaggable {

	private Frame frame;
	
	public CombatTaggable(Frame frame) {
		this.frame = frame;
	}
	
	public boolean taggedCheck(Player p) {
		boolean state = false;
		if(frame.combatTagged.contains(p.getName())) {
			state = true;
			p.sendMessage(ChatColor.RED+"You may not do this command while combat tagged!");
		}
		return state;
	}
	
}
