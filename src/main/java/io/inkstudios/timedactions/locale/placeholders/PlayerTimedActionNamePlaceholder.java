package io.inkstudios.timedactions.locale.placeholders;

import io.inkstudios.commons.name.Name;
import io.inkstudios.commons.spigot.locale.placeholder.PlayerPlaceholder;
import io.inkstudios.commons.spigot.metadata.Metadata;
import io.inkstudios.timedactions.metadata.TimedActionMetadataDefaults;

import org.bukkit.entity.Player;

@Name("TIMED_ACTION_NAME")
public class PlayerTimedActionNamePlaceholder implements PlayerPlaceholder {
	
	@Override
	public String apply(Player player) {
		return Metadata.readString(player, TimedActionMetadataDefaults.TIMED_ACTION_NAME);
	}
	
}