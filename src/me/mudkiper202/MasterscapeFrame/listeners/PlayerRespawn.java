package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerRespawn implements Listener{

	Frame frame;
	
	public PlayerRespawn(Frame frame) {
		this.frame = frame;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if(!frame.flags.get("spawn")) return;
		Player p = e.getPlayer();
		if(frame.flags.get("saturation")) p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1), false);
		if(frame.flags.get("waterBreathing")) p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1), true);
		if(frame.getConfig().getConfigurationSection("spawn") != null) {
			World w = Bukkit.getServer().getWorld(frame.getConfig().getString("spawn.world"));
			double x = frame.getConfig().getDouble("spawn.x");
			double y = frame.getConfig().getDouble("spawn.y");
			double z = frame.getConfig().getDouble("spawn.z");
			int pitch = frame.getConfig().getInt("spawn.yaw");
			int yaw = frame.getConfig().getInt("spawn.pitch");
			Location spawn = new Location(w,x,y,z,Float.valueOf(pitch),Float.valueOf(yaw));
			e.setRespawnLocation(spawn);
		} else {
			p.sendMessage(ChatColor.RED + "This spawn has not yet been set");
		}
	}
	
}
