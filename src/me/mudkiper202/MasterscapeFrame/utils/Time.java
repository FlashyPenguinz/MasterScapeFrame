package me.mudkiper202.MasterscapeFrame.utils;

import java.util.concurrent.TimeUnit;

public class Time {

	public static int[] splitIntoTimeComponents(int time) {
		int days = (int) TimeUnit.SECONDS.toDays(time);
		int hours = (int) (TimeUnit.SECONDS.toHours(time) - (days*24));
		int minutes = (int) (TimeUnit.SECONDS.toMinutes(time) - (TimeUnit.SECONDS.toHours(time)*60));
		int seconds = (int) (TimeUnit.SECONDS.toSeconds(time) - (TimeUnit.SECONDS.toMinutes(time)*60));
		return new int[] {seconds, minutes, hours, days};
	}
	
}
