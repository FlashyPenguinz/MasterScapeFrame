package me.mudkiper202.MasterscapeFrame.utils.item;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionItem {

	private Item item;
	
	private Frame frame;
	
	private String action;
	
	private boolean leftClick;
	private boolean rightClick;
	
	public ActionItem(Frame frame, Item item, String action, boolean lClick, boolean rClick) {
		this.frame = frame;
		this.item = item;
		this.action = action;
		this.leftClick = lClick;
		this.rightClick = rClick;
	}
	
	public boolean isItemEqualAction(ItemStack item) {
		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(this.item.getName())) return true;
		return false;
	}
	
	public void triggerAction(Player p) {
		frame.triggerAction(action, p);
	}

	public ItemStack getActionItem() {
		return item.getItem();
	}

	public boolean isLeftClick() {
		return leftClick;
	}

	public boolean isRightClick() {
		return rightClick;
	}
	
}
