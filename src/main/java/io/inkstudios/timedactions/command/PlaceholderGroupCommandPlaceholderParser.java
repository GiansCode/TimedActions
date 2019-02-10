package io.inkstudios.timedactions.command;

import net.milkbowl.vault.permission.Permission;

import io.inkstudios.timedactions.TimedActionsPlugin;
import io.inkstudios.timedactions.group.PlaceholderGroup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PlaceholderGroupCommandPlaceholderParser implements CommandPlaceholderParser {
	
	@Override
	public ParsedCommand parsePlayers(String command) {
		PlaceholderGroup placeholderGroup = findPlaceholderGroup(command);
		if (placeholderGroup == null) {
			return null;
		}
		
		Set<Player> players = new HashSet<>();
		
		Permission permission = TimedActionsPlugin.getInstance().getPermission();
		placeholderGroup.getGroups().forEach(group ->
			Bukkit.getOnlinePlayers().stream()
					.filter(player -> permission.playerInGroup(player, group))
					.forEach(players::add)
		);
		
		return new ParsedCommand("%" + placeholderGroup.getPlaceholder() + "%", players);
	}
	
	private PlaceholderGroup findPlaceholderGroup(String command) {
		return TimedActionsPlugin.getInstance().getPlaceholderGroups()
				.streamPlaceholderGroups()
				.filter(placeholderGroup -> command.contains("%" + placeholderGroup.getPlaceholder() + "%"))
				.findFirst()
				.orElse(null);
	}
	
}