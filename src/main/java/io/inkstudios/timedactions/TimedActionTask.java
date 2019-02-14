package io.inkstudios.timedactions;

import io.inkstudios.commons.spigot.chat.ChatUtil;
import io.inkstudios.timedactions.command.ParsedCommand;
import io.inkstudios.timedactions.metadata.TimedActionMetadataDefaults;

import org.bukkit.Bukkit;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TimedActionTask implements Runnable {
	
	@Override
	public void run() {
		TimedActionsPlugin plugin = TimedActionsPlugin.getInstance();
		TimedActions timedActions = plugin.getTimedActions();
		
		ActiveTimedAction activeTimedAction = timedActions.getActiveTimedAction();
		if (activeTimedAction == null) {
			Instant nextTimedActionTimestamp = timedActions.getNextTimedActionTimestamp();
			if (nextTimedActionTimestamp == null) {
				timedActions.setNextTimedActionTimestamp(getNextActionTimestamp());
			} else if (nextTimedActionTimestamp.isBefore(Instant.now())) {
				TimedAction nextTimedAction = selectNextAction(timedActions.listTimedActions());
				if (nextTimedAction == null) {
					throw new IllegalStateException("Failed to find next timed action. Do they all add up to 100%?");
				}
				timedActions.setActiveTimedAction(
						new ActiveTimedAction(nextTimedAction, Instant.now(),
								Instant.now().plusMillis(nextTimedAction.getTimeAfterAnnouncement())));
				
				Bukkit.broadcastMessage(ChatUtil.toColor(nextTimedAction.getMinecraftMessage()));
				plugin.sendDiscordMessage(nextTimedAction.getDiscordMessage());
			}
		} else {
			if (activeTimedAction.getCommandExecuteTimestamp().isAfter(Instant.now())) {
				return;
			}
			
			if (!activeTimedAction.isExecuted()) {
				activeTimedAction.setExecuted(true);
				
				activeTimedAction.getTimedAction().getCommands().forEach(timedActionCommand -> {
					for (int i = 0; i < timedActionCommand.getExecutionQuantity(); i++) {
						ParsedCommand command = timedActionCommand.parseCommandPlaceholder();
						if (command == null) {
							break;
						}
						
						command.getPlayers().forEach(player -> {
							String cmd = timedActionCommand.getCommand()
									.replace(command.getPlaceholder(), player.getName());
							
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
						});
					}
				});
				
				Bukkit.getOnlinePlayers().forEach(online -> plugin.getLocale().sendMessage(online, "event-executed",
						TimedActionMetadataDefaults.TIMED_ACTION_NAME, activeTimedAction.getTimedAction().getName()));
				return;
			}
			
			timedActions.setActiveTimedAction(null);
		}
	}
	
	private TimedAction selectNextAction(List<TimedAction> timedActions) {
		double probability = ThreadLocalRandom.current().nextDouble(100);
		double cumulativeProbability = 0D;
		for (TimedAction timedAction : timedActions) {
			cumulativeProbability += timedAction.getChance();
			if (probability <= cumulativeProbability) {
				return timedAction;
			}
		}
		return null;
	}
	
	private Instant getNextActionTimestamp() {
		TimedActionConfiguration configuration = TimedActionsPlugin.getInstance().getTimedActionConfiguration();
		return Instant.now().plusMillis(ThreadLocalRandom.current().nextLong(configuration.getIntervalTimeMin(),
				configuration.getIntervalTimeMax()));
	}
	
}