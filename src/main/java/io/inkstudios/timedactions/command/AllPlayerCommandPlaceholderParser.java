package io.inkstudios.timedactions.command;

import org.bukkit.Bukkit;

import java.util.HashSet;

public class AllPlayerCommandPlaceholderParser implements CommandPlaceholderParser {
	
	@Override
	public ParsedCommand parsePlayers(String command) {
		return new ParsedCommand(TimedActionCommand.ALL_PLAYERS, new HashSet<>(Bukkit.getOnlinePlayers()));
	}
	
}