package io.inkstudios.timedactions;

import io.inkstudios.commons.log.Logger;
import io.inkstudios.timedactions.command.TimedActionCommand;

import org.spigotmc.CaseInsensitiveMap;

import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class TimedActions {
	
	private static final Pattern SEMI_COLON = Pattern.compile(";");
	private static final Pattern COLON = Pattern.compile(":");
	
	private final Map<String, TimedAction> timedActions = new CaseInsensitiveMap<>();
	
	private ActiveTimedAction activeTimedAction;
	private Instant nextTimedActionTimestamp;
	
	public void loadTimedActions(FileConfiguration configuration) {
		Objects.requireNonNull(configuration, "configuration");
		
		timedActions.clear();
		
		configuration.getConfigurationSection("time-actions").getKeys(false).forEach(key -> {
			String path = "time-actions." + key + ".";
			
			TimedAction.Builder timedActionBuilder = TimedAction.newBuilder()
					.setName(configuration.getString(path + "name"))
					.setChance(configuration.getDouble(path + "chance"))
					.setTimeAfterAnnouncement(configuration.getLong(path + "time-after-announce"))
					.setDiscordMessage(configuration.getString(path + "discord-message"))
					.setMinecraftMessage(configuration.getString(path + "minecraft-message"));
			
			configuration.getStringList(path + "commands.player").forEach(line -> {
				int times = 1;
				
				final String command;
				
				String lowerCommand = line.toLowerCase();
				String[] commandSplit = SEMI_COLON.split(lowerCommand);
				if (commandSplit.length > 1) {
					String[] run = COLON.split(commandSplit[1]);
					if (run.length > 1) {
						try {
							times = Integer.parseInt(run[1].trim());
						} catch (NumberFormatException exception) {
							Logger.warn("Command failed to compile ");
						}
					}
					command = commandSplit[0];
				} else {
					command = line;
				}
				
				timedActionBuilder.addCommand(
						TimedActionCommand.newBuilder()
								.setCommand(command)
								.setExecutionQuantity(times)
								.build()
				);
			});
			
			TimedAction timedAction = timedActionBuilder.build();
			registerTimedAction(timedAction.getName(), timedAction);
		});
	}
	
	public Optional<TimedAction> getTimedAction(String name) {
		return name == null ? Optional.empty() : Optional.ofNullable(timedActions.get(name));
	}
	
	public boolean isTimedAction(String name) {
		return name != null && timedActions.containsKey(name);
	}
	
	public void registerTimedAction(String name, TimedAction timedAction) {
		Objects.requireNonNull(name, "name");
		Objects.requireNonNull(timedAction, "timedAction");
		timedActions.put(name, timedAction);
	}
	
	public void unregisterTimedAction(String name) {
		Objects.requireNonNull(name, "name");
		timedActions.remove(name);
	}
	
	public void unregisterTimedAction(TimedAction timedAction) {
		Objects.requireNonNull(timedAction, "timedAction");
		timedActions.remove(timedAction.getName());
	}
	
	public List<TimedAction> listTimedActions() {
		return new ArrayList<>(timedActions.values());
	}
	
	public Stream<TimedAction> streamTimedActions() {
		return listTimedActions().stream();
	}
	
	@Nullable
	public ActiveTimedAction getActiveTimedAction() {
		return activeTimedAction;
	}
	
	public void setActiveTimedAction(@Nullable ActiveTimedAction activeTimedAction) {
		this.activeTimedAction = activeTimedAction;
	}
	
	@Nullable
	public Instant getNextTimedActionTimestamp() {
		return nextTimedActionTimestamp;
	}
	
	public void setNextTimedActionTimestamp(Instant nextTimedActionTimestamp) {
		Objects.requireNonNull(nextTimedActionTimestamp, "nextTimedActionTimestamp");
		this.nextTimedActionTimestamp = nextTimedActionTimestamp;
	}
	
}