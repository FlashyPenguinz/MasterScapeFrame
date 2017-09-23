package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.Gui;
import me.mudkiper202.MasterscapeFrame.utils.item.GuiItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

	Frame frame;
	
	public InventoryClick(Frame pl) {
		frame = pl;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		for(String s: frame.guis.keySet()) {
			Gui gui = frame.guis.get(s);
			if(e.getClickedInventory()==null||gui.getShowableInventory()==null) return;
			if(e.getClickedInventory().getName()==gui.getShowableInventory().getName()) {
				if(e.getCurrentItem()==null||e.getCurrentItem().getType()==Material.AIR) {
					p.closeInventory();
					return;
				}
				if(p.getInventory().contains(e.getCurrentItem())) {
					p.getInventory().remove(e.getCurrentItem());
				}
				e.setCancelled(true);
				for(GuiItem item: gui.getItems()) {
					if(item.getItem().getItem().getType()==e.getCurrentItem().getType()) {
						if(ChatColor.stripColor(item.getItem().getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))) {
							item.triggerAction(p);
							return;
						}
					}
				}
			}
		}
	}
}
