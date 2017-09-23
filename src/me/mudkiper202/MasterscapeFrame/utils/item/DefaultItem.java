package me.mudkiper202.MasterscapeFrame.utils.item;

import java.util.ArrayList;
import java.util.List;

import me.mudkiper202.MasterscapeFrame.utils.general.Color;
import me.mudkiper202.MasterscapeFrame.utils.general.Enchant;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefaultItem implements Item {

	private ItemStack item;
	
	public DefaultItem(Material material) {
		item = new ItemStack(material);
	}
	
	public DefaultItem(Material material, Color color) {
		item = new ItemStack(material, 1, (byte) color.getValue());
	}
	
	public DefaultItem(Material material, int amount, int data) {
		item = new ItemStack(material, amount, (byte) data);
	}
	
	public DefaultItem(Material material, int amount) {
		item = new ItemStack(material);
		setAmount(amount);
	}
	
	@Override
	public void setEnchant(Enchant enchant) {
		this.getItem().addEnchantment(enchant.getEnchantment(), enchant.getLevel());
	}

	@Override
	public void setAmount(int amount) {
		item.setAmount(amount);
	}

	@Override
	public void setName(String name) {
		ItemMeta tempMeta = item.getItemMeta();
		tempMeta.setDisplayName(name);
		item.setItemMeta(tempMeta);
	}

	@Override
	public String getName() {
		return item.getItemMeta().getDisplayName();
	}

	public void setUnbreakable(boolean value) {
		ItemMeta tempMeta = item.getItemMeta();
		tempMeta.setUnbreakable(value);
		item.setItemMeta(tempMeta);
	}

	@Override
	public void addLore(String line) {
		List<String> lore;
		lore = new ArrayList<String>();
		if(item.getItemMeta().hasLore()) lore = item.getItemMeta().getLore();
		lore.add(line);
		ItemMeta tempMeta = item.getItemMeta();
		tempMeta.setLore(lore);
		item.setItemMeta(tempMeta);
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void setEnchantsVisible(boolean state) {
		if(state) item.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
		else if(item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) item.getItemMeta().removeItemFlags(ItemFlag.HIDE_ENCHANTS);
	}
}