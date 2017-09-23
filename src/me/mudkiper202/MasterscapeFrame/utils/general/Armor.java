package me.mudkiper202.MasterscapeFrame.utils.general;

import me.mudkiper202.MasterscapeFrame.utils.item.Item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Armor {

	private Item helmet, chestplate, leggings, boots;
	private boolean direct;
	
	public Armor(Item helmet, Item chestplate, Item leggings, Item boots, boolean direct) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		this.direct = direct;
	}
	
	public Armor(ArmorType armor, boolean direct) {
		this.helmet = armor.getArmor()[0];
		this.chestplate = armor.getArmor()[1];
		this.leggings = armor.getArmor()[2];
		this.boots = armor.getArmor()[3];
		this.direct = direct;
	}
	
	public void equip(Player p) {
		if(direct) {
			p.getInventory().setArmorContents(new ItemStack[] {helmet.getItem(), chestplate.getItem(), leggings.getItem(), boots.getItem()});
		} else {
			for(Item item: new Item[] {helmet, chestplate, leggings, boots}) {
				p.getInventory().addItem(item.getItem());
			}
		}
	}

	public Item getHelmet() {
		return helmet;
	}

	public void setHelmet(Item helmet) {
		this.helmet = helmet;
	}

	public Item getChestplate() {
		return chestplate;
	}

	public void setChestplate(Item chestplate) {
		this.chestplate = chestplate;
	}

	public Item getLeggings() {
		return leggings;
	}

	public void setLeggings(Item leggings) {
		this.leggings = leggings;
	}

	public Item getBoots() {
		return boots;
	}

	public void setBoots(Item boots) {
		this.boots = boots;
	}
	
}
