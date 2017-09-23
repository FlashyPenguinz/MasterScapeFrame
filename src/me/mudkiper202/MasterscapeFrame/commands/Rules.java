package me.mudkiper202.MasterscapeFrame.commands;

import java.util.List;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Rules implements CommandExecutor {

	private Frame frame;
	
	public Rules(Frame frame) {
		this.frame = frame;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("rules")) {
			List<String> rules = frame.getConfig().getStringList("rules");
			sender.sendMessage(ChatColor.GREEN+"The following rules are:");
			for(String rule: rules) {
				sender.sendMessage(ChatColor.GRAY+"- "+ChatColor.RED+rule);
			}
		}
		return false;
	}
}
