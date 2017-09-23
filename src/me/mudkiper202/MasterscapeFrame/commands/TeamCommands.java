package me.mudkiper202.MasterscapeFrame.commands;

import java.util.ArrayList;
import java.util.List;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.area.Area;
import me.mudkiper202.MasterscapeFrame.utils.team.Team;
import me.mudkiper202.MasterscapeFrame.utils.team.TeamRequest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommands implements CommandExecutor {

	Frame frame;
	
	public TeamCommands(Frame frame) {
		this.frame = frame;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(label.equalsIgnoreCase("team")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to do this command!");
				return false;
			}
			Player p = (Player) sender;
			if(!frame.flags.get("teams")) return false;
			if(args.length==1) {
				if(args[0].equalsIgnoreCase("help")) {
					for(String s: new String[] {"<player> - Invites a player to your current team or creates a new team!", "leave - Leaves your current team!"}) {
						p.sendMessage(ChatColor.GOLD+"/team "+s);
					}
				} else if(args[0].equalsIgnoreCase("leave")) {
					for(Team team: frame.teams) {
						if(team.contains(p)) {
							for(Area area: frame.areaManager.getAreas()) {
								if(area.getName().equalsIgnoreCase("spawn")&&!area.containsPlayer(p)) {
									p.sendMessage(ChatColor.RED+"You can only leave a team in spawn!");
									return false;
								}
							}
							for(String s: team.getPlayers()) {
								Player target = Bukkit.getPlayer(s);
								if(target!=null) {
									if((team.getPlayers().size()-1)==1) {
										target.sendMessage(ChatColor.RED+"The team has been disbanded!");
									} else {
										if(s!=p.getName()) {
											target.sendMessage(ChatColor.AQUA+p.getName()+" has left the team!");
										} else {
											p.sendMessage(ChatColor.AQUA+"You have left the team!");
										}
									}
								}
							}
							team.removePlayer(p.getName());
							if(team.getPlayers().size()==1) frame.teams.remove(team);
							return true;
						}
					}
					p.sendMessage(ChatColor.RED+"You are not currently in a team!");
					return false;
				} else if(args[0].equalsIgnoreCase("accept")) {
					boolean found = false;
					for(TeamRequest request: frame.teamRequests) {
						if(request.getRequestPlayer()==p.getName()) {
							found = true;
							if(!request.accept()) {
								p.sendMessage(ChatColor.RED+"Woops! Something went wrong when you tried accepting a team request!");
								return false;
							}
							frame.teamRequests.remove(request);
						}
					}
					if(found==false) {
						p.sendMessage(ChatColor.RED+"There are no current requests being send to you!");
						return false;
					}
					for(Team t: frame.teams) {
						if(t.contains(p)) {
							for(String player: t.getPlayers()) {
								Player s = Bukkit.getPlayer(player);
								if(s!=null) {
									if(s!=p) {
										s.sendMessage(ChatColor.GREEN+p.getName()+" has joined the team!");
									} else {
										p.sendMessage(ChatColor.GREEN+"You have joined a team!");
									}
								}
							}
						}
					}
					return true;
				} else if(Bukkit.getPlayerExact(args[0])!=null) {
					Player target = Bukkit.getPlayerExact(args[0]);
					for(TeamRequest request: frame.teamRequests) {
						if(request.getRequestPlayer()==p.getName()) {
							if(!request.accept()) {
								p.sendMessage(ChatColor.RED+"Woops! Something went wrong when you tried accepting a team request!");
								return false;
							}
							frame.teamRequests.remove(request);
						}
					}
					for(Team t: frame.teams) {
						if(t.contains(p)) {
							for(String player: t.getPlayers()) {
								Player s = Bukkit.getPlayer(player);
								if(s!=null) {
									if(s!=p) {
										s.sendMessage(ChatColor.GREEN+p.getName()+" has joined the team!");
									} else {
										p.sendMessage(ChatColor.GREEN+"You have joined a team!");
									}
									return true;
								}
							}
						}
					}
					if(frame.teamCooldowns.containsKey(p.getName())) {
						for(String s: frame.teamCooldowns.get(p.getName())) {
							if(s==args[0]) {
								p.sendMessage(ChatColor.RED+"Please wait until you can send another team request to this player!");
								return false;
							}
						}
					}
					if(isInTeam(target)) {
						p.sendMessage(ChatColor.RED+"This player is in a team already!");
						return false;
					}
					if(isInTeam(p)) {
						for(Team t: frame.teams){
							if(t.contains(p)) {
								frame.teamRequests.add(new TeamRequest(t, target, frame));
								target.sendMessage(ChatColor.GREEN+p.getName()+" has sent you a team request for you to join their team!");
								p.sendMessage(ChatColor.GREEN+"Request sent to "+target.getName()+"!");
							}
						}
 					} else {
 						frame.teamRequests.add(new TeamRequest(p, target, frame));
						target.sendMessage(ChatColor.GREEN+p.getName()+" has sent you a team request!");
						p.sendMessage(ChatColor.GREEN+"Request sent to "+target.getName()+"!");
					}
					if(!frame.teamCooldowns.containsKey(p.getName())) {
						List<String> cooldowns = new ArrayList<String>();
						cooldowns.add(target.getName());
						frame.teamCooldowns.put(p.getName(), cooldowns);
					} else {
						frame.teamCooldowns.get(p.getName()).add(target.getName());
					}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(frame, new Runnable() {
						public void run() {
							frame.teamCooldowns.get(p.getName()).remove(target.getName());
						}
					}, (30L)*20L);
				}
			} else {
				 p.sendMessage(ChatColor.RED+"Not enough arguments! Do /team help to get teaming commands!");
				 return false;
			}
		}
		return false;
	}
	
	public boolean isInTeam(Player p) {
		for(Team team: frame.teams) {
			if(team.contains(p)) {
				return true;
			}
		}
		return false;
	}
	
}
