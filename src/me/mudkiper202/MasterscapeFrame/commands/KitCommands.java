package me.mudkiper202.MasterscapeFrame.commands;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.kits.Kit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommands implements CommandExecutor {

	private Frame frame;
	
	public KitCommands(Frame frame) {
		this.frame = frame;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("kit")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED+"You must be a player to do this command!");
				return false;
			}
			Player p = (Player) sender;
			if(args.length==1) {
				for(Kit kit: frame.kitManager.kits) {
					if(kit.getName().equalsIgnoreCase(args[0])) {
						if(!kit.isVisible()&&!p.hasPermission("scape.kitVisibility")) {
							p.sendMessage(ChatColor.RED+"This kit doesnt exist. Do /kits to see all availible kits!");
							return false;
						}
						return frame.kitManager.giveKit(p, kit);
					}
				}
				p.sendMessage(ChatColor.RED+"This kit doesnt exist. Do /kits to see all availible kits!");
				return false;
			}
		}
		if(label.equalsIgnoreCase("kits")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED+"You must be a player to do this command!");
				return false;
			}
			Player p = (Player) sender;
			p.sendMessage(frame.kitManager.displayKits(p.hasPermission("scape.kitVisibility")));
			return true;
		}
		return false;
	}
	
}
