package io.inkstudios.timedactions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TimedAction {
	
	public static TimedAction.Builder newBuilder() {
		return new Builder();
	}
	
	public static final class Builder {
		
		private String name;
		private double chance = -1;
		private final Set<String> servers = new HashSet<>();
		private long timeAfterAnnouncement = -1;
		private String discordMessage;
		private String minecraftMessage;
		private final List<TimedActionCommand> commands = new ArrayList<>();
		
		private Builder() {
		
		}
		
		public TimedAction build() {
			validate();
			return new TimedAction(name, chance, servers, timeAfterAnnouncement, discordMessage,
					minecraftMessage, commands);
		}
		
		private void validate() {
			Objects.requireNonNull(name, "name is missing");
			Objects.requireNonNull(discordMessage, "discord message is missing");
			Objects.requireNonNull(minecraftMessage, "minecraft message is missing");
			
			if (chance == -1) {
				throw new IllegalArgumentException("chance is missing");
			}
			
			if (timeAfterAnnouncement == -1) {
				throw new IllegalArgumentException("time after announcement is missing");
			}
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setChance(double chance) {
			this.chance = chance;
			return this;
		}
		
		public Builder addServer(String server) {
			Objects.requireNonNull(server, "server");
			servers.add(server);
			return this;
		}
		
		public Builder addServers(Collection<String> servers) {
			Objects.requireNonNull(servers, "servers");
			servers.forEach(this::addServer);
			return this;
		}
		
		public Builder setTimeAfterAnnouncement(long timeAfterAnnouncement) {
			this.timeAfterAnnouncement = timeAfterAnnouncement;
			return this;
		}
		
		public Builder setDiscordMessage(String discordMessage) {
			this.discordMessage = discordMessage;
			return this;
		}
		
		public Builder setMinecraftMessage(String minecraftMessage) {
			this.minecraftMessage = minecraftMessage;
			return this;
		}
		
		public Builder addCommand(TimedActionCommand command) {
			Objects.requireNonNull(command, "command");
			commands.add(command);
			return this;
		}
		
		public Builder addCommands(Collection<TimedActionCommand> commands) {
			Objects.requireNonNull(commands, "commands");
			commands.forEach(this::addCommand);
			return this;
		}
		
	}
	
	private final String name;
	private final double chance;
	private final Set<String> servers;
	private final long timeAfterAnnouncement;
	private final String discordMessage;
	private final String minecraftMessage;
	private final List<TimedActionCommand> commands;
	
	private TimedAction(String name, double chance, Set<String> servers, long timeAfterAnnouncement,
						String discordMessage, String minecraftMessage, List<TimedActionCommand> commands) {
		this.name = name;
		this.chance = chance;
		this.servers = servers;
		this.timeAfterAnnouncement = timeAfterAnnouncement;
		this.discordMessage = discordMessage;
		this.minecraftMessage = minecraftMessage;
		this.commands = commands;
	}
	
	public String getName() {
		return name;
	}
	
	public double getChance() {
		return chance;
	}
	
	public Set<String> getServers() {
		return new HashSet<>(servers);
	}
	
	public long getTimeAfterAnnouncement() {
		return timeAfterAnnouncement;
	}
	
	public String getDiscordMessage() {
		return discordMessage;
	}
	
	public String getMinecraftMessage() {
		return minecraftMessage;
	}
	
	public List<TimedActionCommand> getCommands() {
		return new ArrayList<>(commands);
	}
	
}