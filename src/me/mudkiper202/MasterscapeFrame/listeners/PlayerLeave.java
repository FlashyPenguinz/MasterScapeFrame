package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

	Frame plugin;
	
	public PlayerLeave(Frame pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		e.setQuitMessage(ChatColor.GREEN+e.getPlayer().getName()+ChatColor.GOLD+" has left the server!");
		if(plugin.flags.get("vanish")==true&&plugin.vanished.contains(e.getPlayer().getName())) {
			plugin.vanished.remove(e.getPlayer());
			for(Player p: Bukkit.getOnlinePlayers()) {
				p.showPlayer(e.getPlayer());
			}
		}
	}
}
