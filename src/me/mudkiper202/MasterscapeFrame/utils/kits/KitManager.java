package me.mudkiper202.MasterscapeFrame.utils.kits;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.mudkiper202.MasterscapeFrame.Frame;
import me.mudkiper202.MasterscapeFrame.utils.Time;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class KitManager {

	Frame frame;
	
	private FileConfiguration kitConfig;
	private File kitConfigFile;
	
	public List<Kit> kits = new ArrayList<Kit>();
	public HashMap<String, HashMap<String, Date>> kitCooldown = new HashMap<String, HashMap<String, Date>>();
	
	public KitManager(String fileName, Frame frame) {
		this.frame = frame;
		if(!frame.getDataFolder().exists()) {
			try {
				frame.getDataFolder().createNewFile();
			} catch(IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED+"Could not create the data folder!");
			}
		}
		
		kitConfigFile = new File(frame.getDataFolder(), fileName+".yml");
		if(!kitConfigFile.exists()) {
			try {
				kitConfigFile.createNewFile();
			} catch(IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED+"Could not create the "+fileName+".yml file!");
			}
		}
		kitConfig = YamlConfiguration.loadConfiguration(kitConfigFile);
		kitConfig.options().copyDefaults(true);
		saveConfig();
		kitCooldown = loadCooldowns();
		startupCooldownCounter();
	}
	
	public void registerKit(Kit kit) {
		kits.add(kit);
	}
	
	public String displayKits(boolean fullVisibility) {
		String str = "";
		for(Kit kit: kits) {
			if(!kit.isVisible()&&fullVisibility==false) continue;
			str = str+ChatColor.GOLD+kit.getName()+", ";
		}
		if(str.isEmpty()) return str;
		str.substring(0, str.length()-2);
		str=str+".";
		return str;
	}
	
	public boolean giveKit(Player p, Kit kit) {
		if(kitCooldown.containsKey(p.getName())) {
			Bukkit.broadcastMessage("cooldown active");
			for(String str: kitCooldown.get(p.getName()).keySet()) {
				Bukkit.broadcastMessage("cooldown 2");
				if(kit.getName().equalsIgnoreCase(str)) {
					Bukkit.broadcastMessage("cooldown 3");
					Date date = new Date();
					int timeLeft = (int) (kitCooldown.get(p.getName()).get(str).getTime() - date.getTime());
					int[] components = Time.splitIntoTimeComponents(timeLeft);
					p.sendMessage(showRemainingTime(components));
					return false;
				}
			}
		}
		if(p.hasPermission(kit.getPermission())) {
			kit.giveKit(p);
			p.sendMessage(ChatColor.GREEN+"Giving you kit "+kit.getName()+"!");
			if(p.hasPermission("scape.kit.cooldown")) return true;
			HashMap<String, Date> cooldown = new HashMap<String, Date>();
			cooldown.put(kit.getName(), new Date());
			kitCooldown.put(p.getName(), cooldown);
			Bukkit.broadcastMessage("it should work");
			return true;
		}
		p.sendMessage(ChatColor.RED+"Sorry, you don't have enough permission for this kit!");
		return false;
	}
	
	private String showRemainingTime(int[] components) {
		String prefix = ChatColor.RED+"Please wait ";
		String suffix = ChatColor.RED+"until you can use that kit again!";
		switch(components.length) {
		case 1:
			return (prefix+components[0]+" seconds "+suffix);
		case 2:
			return (prefix+components[1]+" minutes and "+components[0]+" seconds "+suffix);
		case 3:
			return (prefix+components[2]+" hours, "+components[1]+" minutes, and "+components[0]+" seconds "+suffix);
		case 4:
			return (prefix+components[3]+" days, "+components[2]+"hours and "+components[1]+" minutes "+suffix);
		}
		return (ChatColor.RED+"There is currently a cooldown for this kit but we cant seem to find it! :(");
	}
	
	private void startupCooldownCounter() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(frame, new Runnable() {
			public void run() {
				for(String i: kitCooldown.keySet()) {
					for(String j: kitCooldown.get(i).keySet()) {
						Date date = new Date();
						Bukkit.broadcastMessage("counting cooldown for "+i+" and kit "+j);
						if(kitCooldown.get(i).get(j).getTime()<=date.getTime()) {
							Bukkit.broadcastMessage(kitCooldown.get(i).get(j).getTime()+", "+date.getTime());
							kitCooldown.get(i).remove(j);
							kitConfig.set("cooldowns."+i+"."+j, null);
							saveConfig();
						}
					}
				}
			}
		}, 20L, 20L);
	}
	
	public HashMap<String, HashMap<String, Date>> loadCooldowns() {
		Date currentDate = new Date();
		HashMap<String, HashMap<String, Date>> cooldowns = new HashMap<String, HashMap<String, Date>>();
		if(kitConfig.getConfigurationSection("cooldowns")!=null) {
			for(String player: kitConfig.getConfigurationSection("cooldowns").getKeys(false)) {
				HashMap<String, Date> kits = new HashMap<String, Date>();
				for(String kit: kitConfig.getConfigurationSection("cooldowns."+player).getKeys(false)) {
					Date date = new Date(kitConfig.getInt("cooldowns."+player+"."+kit));
					if(date.getTime()>=currentDate.getTime()) {
						kitConfig.set("cooldowns."+player+"."+kit, null);
					} else {
						kits.put(kit, new Date(kitConfig.getInt("cooldowns."+player+"."+kit)));
					}
				}
				cooldowns.put(player, kits);
			}
		}
		return cooldowns;
	}
	
	public void saveCooldowns(HashMap<String, HashMap<String, Date>> cooldowns) {
		for(String player: cooldowns.keySet()) {
			for(String kit: cooldowns.get(player).keySet()) {
				kitConfig.set("cooldowns."+player+"."+kit, cooldowns.get(player).get(kit).getTime());
			}
		}
		saveConfig();
	}
	

	public void saveConfig() {
		try {
			kitConfig.save(kitConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
