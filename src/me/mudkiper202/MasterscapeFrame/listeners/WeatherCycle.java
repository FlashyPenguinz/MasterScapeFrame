package me.mudkiper202.MasterscapeFrame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherCycle implements Listener{
	
	@EventHandler
	public void onWeatherCycle(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

}
