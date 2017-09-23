package me.mudkiper202.MasterscapeFrame.utils.portal;

import java.util.ArrayList;
import java.util.List;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

public class Portal {

	private List<String> current = new ArrayList<String>();
	
	private Frame frame;
	
	private String name;
	private CuboidSelection area;
	private PortalObjective objective;
	
	public Portal(Frame frame, String name, CuboidSelection area, PortalObjective objective) {
		this.frame = frame;
		this.name = name;
		this.area = area;
		this.objective = objective;
	}

	public boolean isPortalActive(Player p) {
		if(area.contains(p.getLocation())&&!current.contains(p.getName())) {
			current.add(p.getName());
			return true;
		}
		if(current.contains(p.getName())) current.remove(p.getName());
		return false;
	}
	
	public void trigger(Player p) {
		objective.activateObjective(p, frame);
	}
	
	public String getName() {
		return name;
	}

	public CuboidSelection getArea() {
		return area;
	}

	public PortalObjective getObjective() {
		return objective;
	}
	
}
