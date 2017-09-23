package me.mudkiper202.MasterscapeFrame.utils.portal;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.entity.Player;

public class PortalObjective {
	
	private PortalObjectiveType type;
	private String objective;
	
	public PortalObjective(PortalObjectiveType type, String objective) {
		this.type = type;
		this.objective = objective;
	}
	
	public void activateObjective(Player p, Frame frame) {
		if(type==PortalObjectiveType.GUI) {
			p.openInventory(frame.guis.get(objective).getShowableInventory());
		} else if(type==PortalObjectiveType.SERVER) {
			frame.sendToServer(p, objective);
		}
	}
	
	public PortalObjectiveType getType() {
		return type;
	}
	
	public String getObjective() {
		return objective;
	}
	
}
