package me.mudkiper202.MasterscapeFrame.commands;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.area.Area;
import me.mudkiper202.MasterscapeFrame.utils.area.AreaRule;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

public class AreaCommands implements CommandExecutor {

	private Frame frame;
	
	public AreaCommands(Frame frame) {
		this.frame = frame;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("area")) {
			if(sender.hasPermission("scape.area")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You must be a player to do this command!");
					return false;
				}
				Player p = (Player) sender;
				if(args.length>=2) {
					if(args[0].equalsIgnoreCase("create")) {
						Selection s = null;
						if(frame.getWorldEdit().getSelection(p)!=null) {
							s = frame.getWorldEdit().getSelection(p);
							for(Area a: frame.areaManager.getAreas()) {
								if(a.getName().equals(args[1])) {
									p.sendMessage(ChatColor.RED+"This area already exists!");
									return false;
								}
							}
							frame.areaManager.addArea(new Area(args[1], s));
							p.sendMessage(ChatColor.GREEN+"Area added with name of "+args[1]+"!");
							return true;
						} else {
							p.sendMessage(ChatColor.RED+"Please make a selection");
							return false;
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						Area area = null;
						for(Area a: frame.areaManager.getAreas()) {
							if(a.getName().equalsIgnoreCase(args[1])) {
								area = a;
								break;
							}
						}
						if(area!=null) {
							frame.areaManager.removeArea(area);
							p.sendMessage(ChatColor.GREEN+"Successfully removed area with name of "+ area.getName()+"!");
							return true;
						} else {
							p.sendMessage(ChatColor.RED+"This area doesnt exist");
							return false;
						}
					} else if(args[0].equalsIgnoreCase("add")) {
						Selection s = null;
						if(frame.getWorldEdit().getSelection(p)!=null) {
							s = frame.getWorldEdit().getSelection(p);
							for(Area area: frame.areaManager.getAreas()) {
								if(area.getName().equalsIgnoreCase(args[1])) {
									area.addSelection(s);
									p.sendMessage(ChatColor.GREEN+"Selection added to area "+area.getName()+"!");
									return true;
								}
							}
							p.sendMessage(ChatColor.RED+"That area does not exist!");
							return false;
						} else {
							p.sendMessage(ChatColor.RED+"Please make a selection");
							return false;
						}
					} else if(args[0].equalsIgnoreCase("rules")) {
						for(Area area: frame.areaManager.getAreas()) {
							if(area.getName().equalsIgnoreCase(args[1])) {
								for(AreaRule rule: area.getRules()) {
									if(rule.toString().equalsIgnoreCase(args[2])) {
										if(args[3].equalsIgnoreCase("true")||args[3].equalsIgnoreCase("false")) {
											rule.setState(Boolean.getBoolean(args[3]));
											p.sendMessage(ChatColor.GREEN+"Area "+area.getName()+"'s rule "+rule.toString()+"'s state has been changed to "+rule.getState()+"!");
											return true;
										} else {
											p.sendMessage(ChatColor.RED+"Correct Usage: /area rules (name) (rule) (true/false)");
											return false;
										}
									}
								}
								String temp = "";
								for(AreaRule r: AreaRule.values()) {
									temp+=" "+r+",";
								}
								temp.substring(0, temp.length()-1);
								p.sendMessage(ChatColor.RED+"There are no rules of this name! The only rules availible are:"+temp+"!");
								return false;
							}
						}
						p.sendMessage(ChatColor.RED+"That area does not exist!");
						return false;
					}
				}
			}
		}
		return false;
	}
	
}
