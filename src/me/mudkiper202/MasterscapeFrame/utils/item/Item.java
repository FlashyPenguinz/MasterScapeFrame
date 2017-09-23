package me.mudkiper202.MasterscapeFrame.utils.item;

import me.mudkiper202.MasterscapeFrame.utils.general.Enchant;

import org.bukkit.inventory.ItemStack;

public interface Item {
	
	public void setAmount(int amount);
	
	public void setName(String name);
	
	public void setEnchant(Enchant enchant);
	
	public void setEnchantsVisible(boolean state);
	
	public String getName();
	
	public void setUnbreakable(boolean value);
	
	public void addLore(String line);
	
	public ItemStack getItem();
	
}
