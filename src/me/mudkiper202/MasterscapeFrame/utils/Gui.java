package me.mudkiper202.MasterscapeFrame.utils;

import java.util.ArrayList;
import java.util.List;

import me.mudkiper202.MasterscapeHub.utils.GuiItem;

import org.bukkit.inventory.Inventory;

public class Gui {

	private Inventory inv;
	private List<GuiItem> items;
	
	public Gui(Inventory inv) {
		this.inv = inv;
		this.items = new ArrayList<GuiItem>();
	}
	
	public void addGuiItem(GuiItem guiItem, int slot) {
		items.add(guiItem);
		inv.setItem(slot, guiItem.getItem().getItem());
	}

	public Inventory getShowableInventory() {
		return inv;
	}

	public List<GuiItem> getItems() {
		return items;
	}
	
}
