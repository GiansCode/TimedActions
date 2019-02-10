package io.inkstudios.timedactions;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public final class TimedActionConfiguration {

	private long intervalTimeMin;
	private long intervalTimeMax;
	
	public void load(FileConfiguration configuration) {
		Objects.requireNonNull(configuration, "configuration");
		
		intervalTimeMin = configuration.getLong("interval-time.min");
		intervalTimeMax = configuration.getLong("interval-time.max");
	}
	
	public long getIntervalTimeMin() {
		return intervalTimeMin;
	}
	
	public long getIntervalTimeMax() {
		return intervalTimeMax;
	}
	
}