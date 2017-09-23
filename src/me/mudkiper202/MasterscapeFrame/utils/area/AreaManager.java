package me.mudkiper202.MasterscapeFrame.utils.area;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.mudkiper202.MasterscapeFrame.Frame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

public class AreaManager {
	
	Frame frame;
	
	private List<Area> areas = new ArrayList<Area>();
	
	private FileConfiguration areaConfig;
	private File areaConfigFile;
	
	public AreaManager(String fileName, Frame frame) {
		//Starts up and runs a separate config for areas
		if(!frame.getDataFolder().exists()) {
			try {
				frame.getDataFolder().createNewFile();
			} catch(IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED+"Could not create the data folder!");
			}
		}
		
		areaConfigFile = new File(frame.getDataFolder(), fileName+".yml");
		if(!areaConfigFile.exists()) {
			try {
				areaConfigFile.createNewFile();
			} catch(IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED+"Could not create the "+fileName+".yml file!");
			}
		}
		areaConfig = YamlConfiguration.loadConfiguration(areaConfigFile);
		areaConfig.options().copyDefaults(true);
		saveConfig();
		loadConfig();
	}
	
	public void saveAreas() {
		for(Area area: areas) {
			String mainPrefix = "area."+area.getName()+".";
			areaConfig.set(mainPrefix+"name", area.getName());
			for(AreaRule rule: area.getRules()) {
				areaConfig.set(mainPrefix+"rules."+rule.toString(), rule.getState());
			}
			int i=0;
			for(CuboidSelection s: area.getAreas()) {
				areaConfig.set(mainPrefix+"world", s.getWorld().getName());
				String prefix=mainPrefix+i+".maxP.";
				areaConfig.set(prefix+"x", s.getMaximumPoint().getBlockX());
				areaConfig.set(prefix+"y", s.getMaximumPoint().getBlockY());
				areaConfig.set(prefix+"z", s.getMaximumPoint().getBlockZ());
				prefix=mainPrefix+i+".minP.";
				areaConfig.set(prefix+"x", s.getMinimumPoint().getBlockX());
				areaConfig.set(prefix+"y", s.getMinimumPoint().getBlockY());
				areaConfig.set(prefix+"z", s.getMinimumPoint().getBlockZ());
				i++;
			}
		}
		saveConfig();
	}
	
	public void loadConfig() {
		if(areaConfig.getConfigurationSection("area")!=null) {
			for(String s: areaConfig.getConfigurationSection("area").getKeys(false)) {
				String mainPrefix = "area."+s+".";
				if(areaConfig.getBoolean(mainPrefix+"removed")==true) continue;
				String name = s;
				List<AreaRule> rules = new ArrayList<AreaRule>();
				for(String rule: areaConfig.getConfigurationSection("area."+name+".rules").getKeys(false)) {
					AreaRule tempRule = AreaRule.valueOf(rule);
					tempRule.setState(areaConfig.getBoolean(mainPrefix+"rules."+rule));
					rules.add(tempRule);
				}
				List<CuboidSelection> selections = new ArrayList<CuboidSelection>();
				World world = Bukkit.getWorld(areaConfig.getString(mainPrefix+"world"));
				for(String str: areaConfig.getConfigurationSection("area."+s).getKeys(false)) {
					if(str.equalsIgnoreCase("name")||str.equalsIgnoreCase("rules")||str.equalsIgnoreCase("world")) continue;
					mainPrefix=mainPrefix+"."+str+".";
					String prefix=mainPrefix+"maxP.";
					Location maxPoint = new Location(world, areaConfig.getInt(prefix+"x"),
							areaConfig.getInt(prefix+"y"),
							areaConfig.getInt(prefix+"z"));
					prefix = mainPrefix+"minP";
					Location minPoint = new Location(world, areaConfig.getInt(prefix+"x"),
							areaConfig.getInt(prefix+"y"),
							areaConfig.getInt(prefix+"z"));
					selections.add(new CuboidSelection(world, maxPoint, minPoint));
				}
				areas.add(new Area(name, selections, rules));
			}
		}
	}
	
	public void saveConfig() {
		try {
			areaConfig.save(areaConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Area> getAreas() {
		return areas;
	}
	
	public void addArea(Area area) {
		areas.add(area);
	}
	
	public void removeArea(Area area) {
		areas.remove(area);
	}
	
}
