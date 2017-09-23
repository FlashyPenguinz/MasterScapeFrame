package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.item.ActionItem;
import me.mudkiper202.MasterscapeFrame.utils.kits.Kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

	private Frame frame;
	
	public PlayerInteract(Frame frame) {
		this.frame = frame;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.LEFT_CLICK_AIR||
				e.getAction() == Action.LEFT_CLICK_BLOCK||
				e.getAction() == Action.RIGHT_CLICK_AIR||
				e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getAction()==Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock() instanceof Sign) {
					Sign sign = (Sign) e.getClickedBlock();
					if(sign.getLine(0).equals(ChatColor.BLACK+"["+ChatColor.DARK_GRAY+"Kit"+ChatColor.BLACK+"]")) {
						for(Kit kit: frame.kitManager.kits) {
							if(kit.getName().equalsIgnoreCase(ChatColor.stripColor(sign.getLine(1)))) {
								frame.kitManager.giveKit(p, kit);
							}
						}
					}
				}
			}
			for(String s: frame.actionItems.keySet()) {
				ActionItem actionItem = frame.actionItems.get(s);
				if(p.getInventory().getItemInMainHand()!=null&&p.getInventory().getItemInMainHand().getType()!=Material.AIR&&p.getInventory().getItemInMainHand().getItemMeta().getDisplayName()!=null) {
					if(actionItem.isItemEqualAction(p.getInventory().getItemInMainHand())) {
						if(actionItem.isLeftClick()) {
							if(e.getAction() == Action.LEFT_CLICK_AIR||e.getAction() == Action.LEFT_CLICK_BLOCK) {
								actionItem.triggerAction(p);
								return;
							}
						}
						if(actionItem.isRightClick()) {
							if(e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK) {
								actionItem.triggerAction(p);
								return;
							}
						}
					}
				}
			}
		}
	}
	
}
