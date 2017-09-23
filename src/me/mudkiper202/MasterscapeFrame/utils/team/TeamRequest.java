package me.mudkiper202.MasterscapeFrame.utils.team;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamRequest {

	Frame frame;
	
	private Object requestingItem;
	private String player;
	
	public TeamRequest(Team team, Player player, Frame frame) {
		this.requestingItem = team;
		this.player = player.getName();
		this.frame = frame;
	}
	
	public TeamRequest(Player team, Player player, Frame frame) {
		this.requestingItem = team.getName();
		this.player = player.getName();
		this.frame = frame;
	}
	
	public boolean accept() {
		if(requestingItem instanceof String) {
			Player p = Bukkit.getPlayer((String) requestingItem);
			Player t = Bukkit.getPlayer(player);
			if(p==null||t==null) {
				return false;
			}
			p.sendMessage(ChatColor.GREEN+"A new team with "+t.getName()+" was made!");
			t.sendMessage(ChatColor.GREEN+"A new team with "+p.getName()+" was made!");
			frame.teams.add(new Team(frame.teamID++, (String) requestingItem, player));
			return true;
		} 
		if(requestingItem instanceof Team) {
			Team team = (Team) requestingItem;
			Player p = Bukkit.getPlayer(player);
			if(p==null)return false;
			for(Team t: frame.teams) if((team.getName()==t.getName())) t.addPlayer(player);
			return true;
		}
		return false;
	}
	
	public String getRequestPlayer() {
		return player;
	}
}