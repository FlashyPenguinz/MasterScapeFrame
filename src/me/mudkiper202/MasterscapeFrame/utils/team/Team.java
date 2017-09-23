package me.mudkiper202.MasterscapeFrame.utils.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Team {

	private String name;
	
	private List<String> players;
	
	public Team(int ID, String name, String name2) {
		this.players = new ArrayList<String>();
		players.add(name);
		players.add(name2);
	}
	
	public Team(List<String> players) {
		this.players = players;
	}
	
	public void addPlayer(String p) {
		players.add(p);
	}
	
	public void removePlayer(String p) {
		players.remove(p);
	}
	
	public boolean contains(Player p) { 
		return players.contains(p.getName());
	}
	
	public boolean playersInTeam(Player p, Player s) {
		boolean state = false;
		if(players.contains(p.getName())&&players.contains(s.getName())) state = true;
		return state;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
}