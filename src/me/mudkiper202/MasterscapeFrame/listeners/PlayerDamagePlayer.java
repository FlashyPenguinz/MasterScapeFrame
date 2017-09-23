package me.mudkiper202.MasterscapeFrame.listeners;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.team.Team;
import me.mudkiper202.MasterscapeFrame.utils.area.Area;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamagePlayer implements Listener {
	
	private Frame frame;
	
	public PlayerDamagePlayer(Frame frame) {
		this.frame = frame;
	}
	
	@EventHandler
	public void onPlayerDamagePlayer(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		Player damager = null;
		
		if(e.getDamager() instanceof Player) {
			damager = (Player) e.getDamager();
		} else if(e.getDamager() instanceof Projectile) {
			if(((Projectile) e.getDamager()).getShooter() instanceof Player) damager = (Player) ((Projectile) e.getDamager()).getShooter();
		} else return;
		//Damager and Player has been defined and are indead a player.
		
		//Check the universal flag
		if(frame.flags.get("universalPvp")==false) {
			e.setCancelled(true);
			return;
		}
		//Check if they are in an area where they cannot pvp.
		for(Area a: frame.areaManager.getAreas()) {
			if(a.getRule("pvp").getState()==false) {
				if(a.containsPlayer(p)||a.containsPlayer(damager)) {
					e.setCancelled(true);
					return;
				}
			}
		}
		
		//Check if both players are in a team.
		for(Team t: frame.teams) {
			if(t.playersInTeam(p, damager)) {
				damager.sendMessage(ChatColor.RED+"Do not attack your teammates!");
				e.setCancelled(true);
				return;
			}
		}
		//If we got this far then the event goes through and the player is combat tagged if the flag is on.
		if(!frame.flags.get("combatTagging")) return;
		if(frame.flying.contains(p.getName())) {
			p.setFlying(false);
			frame.flying.remove(p.getName());
		}
		if(frame.vanished.contains(p.getName())) {
			for(Player t: Bukkit.getOnlinePlayers()) t.showPlayer(p);
			frame.vanished.remove(p.getName());
		}
		if(!frame.combatTagged.contains(p.getName())) {
			p.sendMessage(ChatColor.RED+"You have been combat tagged!");
			frame.combatTagged.add(p.getName());
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(frame, new Runnable() {
				public void run() {
					p.sendMessage(ChatColor.GREEN+"You are no longer combat tagged!");
					frame.combatTagged.remove(p.getName());
				}
			}, 900L);
		}
		if(!frame.combatTagged.contains(damager.getName())) {
			damager.sendMessage(ChatColor.RED+"You have been combat tagged!");
			frame.combatTagged.add(damager.getName());
			Player temp = damager;
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(frame, new Runnable() {
				public void run() {
					temp.sendMessage(ChatColor.GREEN+"You are no longer combat tagged!");
					frame.combatTagged.remove(temp.getName());
				}
			}, 900L);
		}
	}

}
