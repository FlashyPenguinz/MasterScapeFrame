package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoin implements Listener{

	Frame plugin;
	
	public PlayerJoin(Frame pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(ChatColor.GREEN+p.getName()+ChatColor.GOLD+" has joined the server!");
		if(plugin.flags.get("vanish")) {
			for(String pl: plugin.vanished) {
				p.hidePlayer(Bukkit.getPlayerExact(pl));
			}
		}
		if(plugin.flags.get("saturation")) p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1), false);
		if(plugin.flags.get("waterBreathing")) p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1), true);
		if(plugin.flags.get("spawnJoin")) {
			if(plugin.getConfig().getConfigurationSection("spawn") != null) {
				World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("spawn.world"));
				double x = plugin.getConfig().getDouble("spawn.x");
				double y = plugin.getConfig().getDouble("spawn.y");
				double z = plugin.getConfig().getDouble("spawn.z");
				int pitch = plugin.getConfig().getInt("spawn.yaw");
				int yaw = plugin.getConfig().getInt("spawn.pitch");
				Location spawn = new Location(w,x,y,z,Float.valueOf(pitch),Float.valueOf(yaw));
				p.teleport(spawn);
			} else {
				p.sendMessage(ChatColor.RED + "This spawn has not yet been set");
			}
		}
	}
}
