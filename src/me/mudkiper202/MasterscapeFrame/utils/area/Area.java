package me.mudkiper202.MasterscapeFrame.utils.area;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class Area {
	
	private String name;
	private List<CuboidSelection> areas;
	private List<AreaRule> rules;
	
	public Area(String name, Selection selection) {
		this.name = name;
		areas = new ArrayList<CuboidSelection>();
		
		addSelection(selection);
		
		rules = new ArrayList<AreaRule>();
		rules.add(AreaRule.PVP);
		rules.add(AreaRule.BLOCKBREAK);
	}
	
	public Area(String name, List<CuboidSelection> selections, List<AreaRule> rules) {
		this.name = name;
		this.areas = selections;
		this.rules = rules;
	}
	
	public void addSelection(Selection selection) {
		areas.add((CuboidSelection) selection);
	}
	
	public boolean containsPlayer(Player p) {
		for(CuboidSelection s: areas) {
			if(s.contains(p.getLocation())) {
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public List<CuboidSelection> getAreas() {
		return areas;
	}
	
	public AreaRule getRule(String str) {
		for(AreaRule r: rules) {
			if(r.toString().equalsIgnoreCase(str)) {
				return r;
			}
		}
		return null;
	}
	
	public List<AreaRule> getRules() {
		return rules;
	}
	
	public void setRule(String rule, boolean state) {
		for(AreaRule r: rules) {
			if(r.toString().equalsIgnoreCase(rule)) {
				r.setState(state);
			}
		}
	}
	
}
