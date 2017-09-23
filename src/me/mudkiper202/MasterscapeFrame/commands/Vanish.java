package me.mudkiper202.MasterscapeFrame.commands;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish extends CombatTaggable implements CommandExecutor {

	private Frame frame;
	
	public Vanish(Frame frame) {
		super(frame);
		
		this.frame = frame;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("v")||label.equalsIgnoreCase("vanish")) {
			if(!frame.flags.get("vanish")) return false;
			if(!sender.hasPermission("scape.vanish")) {
				sender.sendMessage(ChatColor.RED+"You are not permitted to do this!");
				return false;
			}
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to do this command!");
				return false;
			}
			Player p = (Player) sender;
			if(taggedCheck(p)) return false;
			if(!frame.vanished.contains(p.getName())) {
				for(Player pl: Bukkit.getOnlinePlayers()) {
					pl.hidePlayer(p);
				}
				frame.vanished.add(p.getName());
				p.sendMessage(ChatColor.GREEN+"You have been vanished!");
				return true;
			} else {
				for(Player pl: Bukkit.getOnlinePlayers()) {
					pl.showPlayer(p);
				}
				frame.vanished.remove(p.getName());
				p.sendMessage(ChatColor.GREEN+"You have been unvanished!");
				return true;
			}
		}
		return false;
	}
	
}
