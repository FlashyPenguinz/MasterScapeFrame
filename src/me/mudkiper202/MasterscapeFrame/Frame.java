package me.mudkiper202.MasterscapeFrame;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.mudkiper202.MasterscapeHub.Hub;
import me.mudkiper202.MasterscapeHub.listeners.BlockBreak;
import me.mudkiper202.MasterscapeHub.listeners.InventoryClick;
import me.mudkiper202.MasterscapeHub.listeners.PlayerChat;
import me.mudkiper202.MasterscapeHub.listeners.PlayerDamagePlayer;
import me.mudkiper202.MasterscapeHub.listeners.PlayerDamaged;
import me.mudkiper202.MasterscapeHub.listeners.PlayerDrop;
import me.mudkiper202.MasterscapeHub.listeners.PlayerInteract;
import me.mudkiper202.MasterscapeHub.listeners.PlayerJoin;
import me.mudkiper202.MasterscapeHub.listeners.PlayerLeave;
import me.mudkiper202.MasterscapeHub.listeners.PlayerMove;
import me.mudkiper202.MasterscapeHub.listeners.PlayerRespawn;
import me.mudkiper202.MasterscapeHub.listeners.SignCreate;
import me.mudkiper202.MasterscapeHub.listeners.WeatherCycle;
import me.mudkiper202.MasterscapeHub.utils.ActionItem;
import me.mudkiper202.MasterscapeHub.utils.Portal;
import me.mudkiper202.MasterscapeHub.utils.PortalUtils;
import me.mudkiper202.MasterscapePvp.Pvp;
import me.mudkiper202.MasterscapeFrame.commands.AreaCommands;
import me.mudkiper202.MasterscapeFrame.commands.Fly;
import me.mudkiper202.MasterscapeFrame.commands.KitCommands;
import me.mudkiper202.MasterscapeFrame.commands.PortalCommands;
import me.mudkiper202.MasterscapeFrame.commands.Rules;
import me.mudkiper202.MasterscapeFrame.commands.SpawnCommands;
import me.mudkiper202.MasterscapeFrame.commands.TeamCommands;
import me.mudkiper202.MasterscapeFrame.commands.Vanish;
import me.mudkiper202.MasterscapeFrame.utils.Gui;
import me.mudkiper202.MasterscapeFrame.utils.area.AreaManager;
import me.mudkiper202.MasterscapeFrame.utils.kits.KitManager;
import me.mudkiper202.MasterscapeFrame.utils.team.Team;
import me.mudkiper202.MasterscapeFrame.utils.team.TeamRequest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.permissions.exceptions.PermissionBackendException;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class Frame extends JavaPlugin {

	//          !!!!LIST!!!!
	//
	// - Economy
	// - Crates
	
	public AreaManager areaManager;
	public KitManager kitManager;
	
	public String name;
	
	public HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
	
	public HashMap<String, ActionItem> actionItems = new HashMap<String, ActionItem>();
	public HashMap<String, Gui> guis = new HashMap<String, Gui>();
	
	public int teamID;
	public List<Team> teams = new ArrayList<Team>();
	public List<TeamRequest> teamRequests = new ArrayList<TeamRequest>();
	public HashMap<String, List<String>> teamCooldowns = new HashMap<String, List<String>>();
	
	public List<String> flying = new ArrayList<String>();
	public List<String> vanished = new ArrayList<String>();
	public List<Portal> portals = new ArrayList<Portal>();
	public List<String> combatTagged = new ArrayList<String>();
	
	public int message;
	public int time;
	
	@Override
	public void onEnable() {
		registerConfig();
		registerFlags();
		areaManager = new AreaManager("areaConfig", this);
		kitManager = new KitManager("kitConfig", this);
		registerCommands();
		registerListeners();
		registerBroadcast();
		permissionsReload();
		portals = PortalUtils.loadPortals(this);
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	@Override
	public void onDisable() {
		areaManager.saveAreas();
		kitManager.saveCooldowns(kitManager.kitCooldown);
		PortalUtils.savePortals(portals, this);
	}
	
	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockBreak(this), this);
		pm.registerEvents(new PlayerMove(this), this);
		pm.registerEvents(new PlayerChat(), this);
		pm.registerEvents(new PlayerDamagePlayer(this), this);
		pm.registerEvents(new PlayerDamaged(this), this);
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new SignCreate(this), this);
		pm.registerEvents(new WeatherCycle(), this);
		pm.registerEvents(new InventoryClick(this), this);
		pm.registerEvents(new PlayerDrop(this), this);
		pm.registerEvents(new PlayerLeave(this), this);
		pm.registerEvents(new PlayerRespawn(this), this);
	}

	private void registerCommands() {
		getCommand("setspawn").setExecutor(new SpawnCommands(this));
		getCommand("spawn").setExecutor(new SpawnCommands(this));
		getCommand("fly").setExecutor(new Fly(this));
		getCommand("vanish").setExecutor(new Vanish(this));
		getCommand("v").setExecutor(new Vanish(this));
		getCommand("team").setExecutor(new TeamCommands(this));
		getCommand("area").setExecutor(new AreaCommands(this));
		getCommand("rules").setExecutor(new Rules(this));
		getCommand("portal").setExecutor(new PortalCommands(this));
		getCommand("kit").setExecutor(new KitCommands(this));
		getCommand("kits").setExecutor(new KitCommands(this));
	}
	
	private void registerFlags() {
		
		flags.put("spawn", true);
		flags.put("spawnJoin", true);
		
		flags.put("voidFall", true);
		flags.put("fallDamage", false);
		
		flags.put("economy", false);
		
		flags.put("universalPvp", false);
		flags.put("universalBlockBreak", false);
		
		flags.put("saturation", true);
		flags.put("waterBreathing", true);
		flags.put("drops", true);
		
		flags.put("universalFly", false);
		flags.put("fly", true);
		flags.put("vanish", true);
		
		flags.put("kits", false);
		flags.put("teams", false);
		flags.put("combatTagging", false);
	}
	
	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void registerNoLag() {
		time = getConfig().getInt("antilaginterval");
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				time--;
				if(time==0) {
					Bukkit.broadcastMessage(ChatColor.RED+"All items have been cleared!");
					for(World w: Bukkit.getWorlds()) {
						for(Entity e: w.getEntities()) {
							if(e.getType().equals(EntityType.DROPPED_ITEM)) {
								e.remove();
							}
						}
					}
					time = getConfig().getInt("antilaginterval");
				}
				if(time<=30) {
					if(time%10==0||time<=3) {
						Bukkit.broadcastMessage(ChatColor.RED+"There are "+time+" seconds until items are cleared!");
					}
				}
			}
		}, 20L, 20L);
	}
	
	public void sendToServer(Player p, String targetServer) {
		p.sendMessage(ChatColor.GREEN+"Sending you to server!");
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(targetServer);
		}catch(Exception e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
	}
	
	private void permissionsReload() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				try {
					PermissionsEx.getPermissionManager().getBackend().reload();
				} catch (PermissionBackendException e) {
					e.printStackTrace();
				}
			}
		}, (30)*20L, (30)*20L);
	}
	
	public void registerBroadcast() {
		if(getConfig().getConfigurationSection("broadcast") != null) {
			int interval = getConfig().getInt("broadcastInterval");
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				public void run() {
					List<String> messages = getConfig().getStringList("broadcast.messages");
					Random r = new Random();
					int messageNumber = r.nextInt(messages.size()-1);
					while(message==messageNumber) {
						messageNumber = r.nextInt(messages.size()-1);
					}
					message = messageNumber;
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b[&aMasterScapeMC&b]&6 ")+messages.get(messageNumber));
				}
			}, (interval)*20, (interval)*20);
		}
	}
	
	public WorldEditPlugin getWorldEdit() {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (p instanceof WorldEditPlugin) return (WorldEditPlugin) p;
        else return null;
	}
	
	public void addGui(String str, Gui gui) {
		guis.put(str, gui);
	}
	
	public void addActionItem(String str, ActionItem item) {
		actionItems.put(str, item);
	}
	
	public void triggerAction(String str, Player p) {
		Plugin pl = getServer().getPluginManager().getPlugin(name);
		MethodLoader methods = null;
		Hub h = null;
		Pvp pvp = null;
		if(pl.getName().equalsIgnoreCase("hub")) {
			methods = ((Hub) pl);
			h = ((Hub) pl);
		} else if(pl.getName().equalsIgnoreCase("pvp")) {
			methods = ((Pvp) pl);
			pvp = ((Pvp) pl);
		}
		
		if(methods!=null) {
			try {
				Method method;
				method = methods.getMethodClass().getMethod(str, Player.class);
				if(h!=null) {
					method.invoke(h.methods, p);
				} else if(pvp!=null) {
					method.invoke(pvp.methods, p);
				}
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
}
