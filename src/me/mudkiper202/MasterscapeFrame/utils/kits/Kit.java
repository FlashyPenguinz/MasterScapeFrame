package me.mudkiper202.MasterscapeFrame.utils.kits;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.general.Armor;
import me.mudkiper202.MasterscapeFrame.utils.item.Item;

public class Kit {

	private String name, permission;
	private int cooldownTime;
	private boolean visible;
	
	private Item[] items;
	private Armor armor;
	
	public Kit(Frame frame, String name, Permission permission, boolean visible, int cooldownTime, Armor armor, Item... items) {
		this.name = name;
		this.permission = permission.getName();
		this.visible = visible;
		this.cooldownTime = cooldownTime;
		this.armor = armor;
		this.items = items;
		
		frame.getServer().getPluginManager().addPermission(permission);
	}

	public void giveKit(Player p) {
		armor.equip(p);
		for(Item item: items) {
			if(p.getInventory().firstEmpty()==(-1)) {
				p.getWorld().dropItem(p.getLocation(), item.getItem());
			} else {
				p.getInventory().addItem(item.getItem());
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return permission;
	}

	public int getCooldownTime() {
		return cooldownTime;
	}

	public boolean isVisible() {
		return visible;
	}

	public Item[] getItems() {
		return items;
	}

	public Armor getArmor() {
		return armor;
	}
	
}
