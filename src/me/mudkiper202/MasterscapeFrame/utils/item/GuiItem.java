package me.mudkiper202.MasterscapeFrame.utils.item;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.entity.Player;

public class GuiItem {

	private Item item;
	
	Frame frame;
	
	private String action;
	
	public GuiItem(Frame frame, Item item, String action) {
		this.frame = frame;
		this.item = item;
		this.action = action;
	}
	
	public void triggerAction(Player p) {
		frame.triggerAction(action, p);
	}
	
	public Item getItem() {
		return item;
	}
	
}
