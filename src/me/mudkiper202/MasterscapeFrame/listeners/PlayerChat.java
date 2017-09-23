package me.mudkiper202.MasterscapeFrame.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerChat implements Listener{
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String name = p.getName();
		@SuppressWarnings("deprecation")
		String prefix = PermissionsEx.getUser(p).getGroups()[0].getPrefix();
		p.setDisplayName(ChatColor.translateAlternateColorCodes('&',prefix)+name);
		if(p.hasPermission("scape.colorchat")) {
			e.setMessage(ChatColor.translateAlternateColorCodes('&',e.getMessage()));
		}
		e.setFormat(p.getDisplayName()+": "+ChatColor.RESET+e.getMessage());
	}
	
}
