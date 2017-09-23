package me.mudkiper202.MasterscapeFrame.utils.portal;

import java.util.ArrayList;
import java.util.List;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

public class PortalUtils {

	public static List<Portal> loadPortals(Frame frame) {
		List<Portal> portals = new ArrayList<Portal>();
		if(frame.getConfig().getConfigurationSection("portal")==null) return new ArrayList<Portal>();
		for(String name: frame.getConfig().getConfigurationSection("portal").getKeys(false)) {
			String prefix = "portal."+name;
			if(frame.getConfig().getConfigurationSection(prefix) != null) {
				if(frame.getConfig().getBoolean(prefix+".removed")==false) {
					World world = Bukkit.getWorld(frame.getConfig().getString(prefix+".world"));
					int minpX = frame.getConfig().getInt(prefix+".minp.x");
					int minpY = frame.getConfig().getInt(prefix+".minp.y");
					int minpZ = frame.getConfig().getInt(prefix+".minp.z");
					int maxpX = frame.getConfig().getInt(prefix+".maxp.x");
					int maxpY = frame.getConfig().getInt(prefix+".maxp.y");
					int maxpZ = frame.getConfig().getInt(prefix+".maxp.z");
					Location minP = new Location(world,minpX,minpY,minpZ);
					Location maxP = new Location(world,maxpX,maxpY,maxpZ);
					PortalObjectiveType type = PortalObjectiveType.valueOf(frame.getConfig().getString(prefix+".objectiveType"));
					String objective = frame.getConfig().getString(prefix+".objective");
					CuboidSelection s = new CuboidSelection(world,minP,maxP);
					portals.add(new Portal(frame, name, s, new PortalObjective(type, objective)));
				}
			}
		}
		return portals;
	}
	
	public static void savePortals(List<Portal> portals, Frame frame) {
		for(Portal p: portals) {
			int maxpX = p.getArea().getMaximumPoint().getBlockX();
			int maxpY = p.getArea().getMaximumPoint().getBlockY();
			int maxpZ = p.getArea().getMaximumPoint().getBlockZ();
			int minpX = p.getArea().getMinimumPoint().getBlockX();
			int minpY = p.getArea().getMinimumPoint().getBlockY();
			int minpZ = p.getArea().getMinimumPoint().getBlockZ();
			String prefix = "portal."+p.getName()+".";
			if(frame.getConfig().getConfigurationSection(prefix+"removed") == null) frame.getConfig().set(prefix+"removed", false);
			frame.getConfig().set(prefix+"world", p.getArea().getWorld().getName());
			frame.getConfig().set(prefix+"minp.x", minpX);
			frame.getConfig().set(prefix+"minp.y", minpY);
			frame.getConfig().set(prefix+"minp.z", minpZ);
			frame.getConfig().set(prefix+"maxp.x", maxpX);
			frame.getConfig().set(prefix+"maxp.y", maxpY);
			frame.getConfig().set(prefix+"maxp.z", maxpZ);
			frame.getConfig().set(prefix+"objectiveType", p.getObjective().getType().name());
			frame.getConfig().set(prefix+"objective", p.getObjective().getObjective());
			frame.saveConfig();
		}
	}
}