package me.mudkiper202.MasterscapeFrame.utils.general;

import me.mudkiper202.MasterscapeFrame.utils.item.DefaultItem;
import me.mudkiper202.MasterscapeFrame.utils.item.Item;

import org.bukkit.Material;

public enum ArmorType {

	LEATHER(new DefaultItem(Material.LEATHER_HELMET), new DefaultItem(Material.LEATHER_CHESTPLATE), new DefaultItem(Material.LEATHER_LEGGINGS), new DefaultItem(Material.LEATHER_BOOTS)),
	GOLD(new DefaultItem(Material.GOLD_HELMET), new DefaultItem(Material.GOLD_CHESTPLATE), new DefaultItem(Material.GOLD_LEGGINGS), new DefaultItem(Material.GOLD_BOOTS)),
	CHAINMAIL(new DefaultItem(Material.CHAINMAIL_HELMET), new DefaultItem(Material.CHAINMAIL_CHESTPLATE), new DefaultItem(Material.CHAINMAIL_LEGGINGS), new DefaultItem(Material.CHAINMAIL_BOOTS)),
	IRON(new DefaultItem(Material.IRON_HELMET), new DefaultItem(Material.IRON_CHESTPLATE), new DefaultItem(Material.IRON_LEGGINGS), new DefaultItem(Material.IRON_BOOTS)),
	DIAMOND(new DefaultItem(Material.DIAMOND_HELMET), new DefaultItem(Material.DIAMOND_CHESTPLATE), new DefaultItem(Material.DIAMOND_LEGGINGS), new DefaultItem(Material.DIAMOND_BOOTS));
	
	private DefaultItem helmet, chestplate, leggings, boots;
	
	ArmorType(DefaultItem helmet, DefaultItem chestplate, DefaultItem leggings, DefaultItem boots) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}
	
	public Item[] getArmor() {
		return new DefaultItem[] {helmet, chestplate, leggings, boots};
	}
	
}
