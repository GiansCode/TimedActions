package io.inkstudios.timedactions;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public final class TimedActionConfiguration {

	private long intervalTimeMin;
	private long intervalTimeMax;
	
	private String discordToken;
	private long discordChannelId;
	
	public void load(FileConfiguration configuration) {
		Objects.requireNonNull(configuration, "configuration");
		
		intervalTimeMin = configuration.getLong("interval-time.min");
		intervalTimeMax = configuration.getLong("interval-time.max");
		
		discordToken = configuration.getString("discord.token");
		discordChannelId = configuration.getLong("discord.channel-id");
	}
	
	public long getIntervalTimeMin() {
		return intervalTimeMin;
	}
	
	public long getIntervalTimeMax() {
		return intervalTimeMax;
	}
	
	public String getDiscordToken() {
		return discordToken;
	}
	
	public long getDiscordChannelId() {
		return discordChannelId;
	}
	
}