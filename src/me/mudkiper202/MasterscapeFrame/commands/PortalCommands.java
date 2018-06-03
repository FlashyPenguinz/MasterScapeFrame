package me.mudkiper202.MasterscapeFrame.commands;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.portal.PortalObjective;
import me.mudkiper202.MasterscapeFrame.utils.portal.PortalObjectiveType;
import me.mudkiper202.MasterscapeHub.utils.Portal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class PortalCommands implements CommandExecutor {

	private Frame frame;
	
	public PortalCommands(Frame frame) {
		this.frame = frame;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//portal create (name) (objectiveType) (objectiveLocation)
		if(label.equalsIgnoreCase("portal")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to do this command!");
				return false;
			}
			if(!sender.hasPermission("scape.portal")) return false;
			Player p = (Player) sender;
			if(args.length>=2) {
				if(args[0].equalsIgnoreCase("create")) {
					Selection s = frame.getWorldEdit().getSelection(p);
					if(s!=null) {
						CuboidSelection cs = (CuboidSelection) s;
						PortalObjectiveType o = PortalObjectiveType.valueOf(args[2].toUpperCase());
						if(cs!=null&&o!=null) frame.portals.add(new Portal(frame, args[1], cs, new PortalObjective(o, args[3])));
					} else {
						p.sendMessage(ChatColor.RED+"Please make a selection");
						return false;
					}
				} else
				if(args[0].equalsIgnoreCase("remove")) {
					for(Portal portal: frame.portals) {
						if(portal.getName().equalsIgnoreCase(args[1])) {
							frame.portals.remove(portal);
							frame.getConfig().set("portal."+portal.getName()+".removed", true);
						}
					}
				}
			} else {
				p.sendMessage(ChatColor.RED + "Not enough arguments!");
			}
		}
		return false;
	}

	
	
}
