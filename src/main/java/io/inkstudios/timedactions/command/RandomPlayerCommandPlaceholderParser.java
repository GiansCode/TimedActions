package io.inkstudios.timedactions.command;

import com.google.common.collect.Sets;

import io.inkstudios.timedactions.TimedActionCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayerCommandPlaceholderParser implements CommandPlaceholderParser {
	
	@Override
	public ParsedCommand parsePlayers(String command) {
		List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
		
		if (players.isEmpty()) {
			return null;
		}
		
		Player randomPlayer = players.get(ThreadLocalRandom.current().nextInt(players.size()));
		return new ParsedCommand(TimedActionCommand.RANDOM_PLAYER, Sets.newHashSet(randomPlayer));
	}
	
}