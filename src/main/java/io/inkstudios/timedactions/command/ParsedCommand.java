package io.inkstudios.timedactions.command;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ParsedCommand {
	
	private final String placeholder;
	private final Set<Player> players;
	
	public ParsedCommand(String placeholder, Set<Player> players) {
		this.placeholder = placeholder;
		this.players = players;
	}
	
	public String getPlaceholder() {
		return placeholder;
	}
	
	public Set<Player> getPlayers() {
		return new HashSet<>(players);
	}
	
}