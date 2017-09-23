package me.mudkiper202.MasterscapeFrame.commands;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly extends CombatTaggable implements CommandExecutor {

	Frame plugin;
	
	public Fly(Frame pl) {
		super(pl);
		
		plugin = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(label.equalsIgnoreCase("fly")) {
			if(plugin.flags.get("fly")||plugin.flags.get("universalFly")) {
				if(plugin.flags.get("fly")) {
					if(!sender.hasPermission("scape.fly")&&!plugin.flags.get("universalFly")) {
						sender.sendMessage(ChatColor.RED+"You are not permitted to do this!");
						return false;
					}
				}
			} else {
				return false;
			}
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to do this command!");
				return false;
			}
			Player p = (Player) sender;
			if(taggedCheck(p)) return false;
			if(plugin.flying.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "Flying disabled!");
				p.setAllowFlight(false);
				plugin.flying.remove(p.getName());
			} else {
				p.sendMessage(ChatColor.GREEN + "Flying enabled!");
				p.setAllowFlight(true);
				plugin.flying.add(p.getName());
			}	
		}
		return false;
	}

}
