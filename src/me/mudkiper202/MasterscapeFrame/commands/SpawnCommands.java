package me.mudkiper202.MasterscapeFrame.commands;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommands extends CombatTaggable implements CommandExecutor {

	Frame plugin;
	
	public SpawnCommands(Frame pl) {
		super(pl);
		
		plugin = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!plugin.flags.get("spawn")) return false;
		if(label.equalsIgnoreCase("spawn")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to do this command!");
				return false;
			}
			Player p = (Player) sender;
			if(taggedCheck(p)) return false;
			if(plugin.getConfig().getConfigurationSection("spawn") != null) {
				World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("spawn.world"));
				double x = plugin.getConfig().getDouble("spawn.x");
				double y = plugin.getConfig().getDouble("spawn.y");
				double z = plugin.getConfig().getDouble("spawn.z");
				int pitch = plugin.getConfig().getInt("spawn.yaw");
				int yaw = plugin.getConfig().getInt("spawn.pitch");
				Location spawn = new Location(w,x,y,z,Float.valueOf(pitch),Float.valueOf(yaw));
				p.teleport(spawn);
				return true;
			} else {
				p.sendMessage(ChatColor.RED + "This spawn has not yet been set");
			}
			return false;
		}
		
		if(label.equalsIgnoreCase("setspawn")) {
			if(!sender.hasPermission("scape.setspawn")) {
				sender.sendMessage(ChatColor.RED+"You are not permitted to do this!");
				return false;
			}
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to do this command!");
				return false;
			}
			Player p = (Player) sender;
			plugin.getConfig().set("spawn.world", p.getLocation().getWorld().getName());
			plugin.getConfig().set("spawn.x", p.getLocation().getX());
			plugin.getConfig().set("spawn.y", p.getLocation().getY());
			plugin.getConfig().set("spawn.z", p.getLocation().getZ());
			plugin.getConfig().set("spawn.pitch", (int) p.getLocation().getPitch());
			plugin.getConfig().set("spawn.yaw", (int)p.getLocation().getYaw());
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "Spawn Set!");
			return true;
		}
		return false;
	}

	
	
}
