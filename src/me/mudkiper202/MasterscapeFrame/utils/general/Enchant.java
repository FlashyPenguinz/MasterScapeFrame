package me.mudkiper202.MasterscapeFrame.utils.general;

import org.bukkit.enchantments.Enchantment;

public class Enchant {

	private Enchantment enchantment;
	private int level;
	
	public Enchant(Enchantment enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public int getLevel() {
		return level;
	}
	
}
