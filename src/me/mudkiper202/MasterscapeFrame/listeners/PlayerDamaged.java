package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamaged implements Listener {

	private Frame frame;
	
	public PlayerDamaged(Frame frame) {
		this.frame = frame;
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(e.getCause()==DamageCause.VOID) {
				if(frame.flags.get("voidFall")==false&&frame.flags.get("spawn")==true) {
					e.setCancelled(true);
					if(frame.getConfig().getConfigurationSection("spawn") != null) {
						World w = Bukkit.getServer().getWorld(frame.getConfig().getString("spawn.world"));
						double x = frame.getConfig().getDouble("spawn.x");
						double y = frame.getConfig().getDouble("spawn.y");
						double z = frame.getConfig().getDouble("spawn.z");
						int pitch = frame.getConfig().getInt("spawn.yaw");
						int yaw = frame.getConfig().getInt("spawn.pitch");
						Location spawn = new Location(w,x,y,z,Float.valueOf(pitch),Float.valueOf(yaw));
						p.teleport(spawn);
					} else {
						p.sendMessage(ChatColor.RED + "This spawn has not yet been set");
					}
				}
			}
			if(e.getCause()==DamageCause.FALL&&frame.flags.get("fallDamage")==false) e.setCancelled(true);
		}
	}
	
}
