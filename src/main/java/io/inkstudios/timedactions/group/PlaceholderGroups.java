package io.inkstudios.timedactions.group;

import org.spigotmc.CaseInsensitiveMap;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public final class PlaceholderGroups {
	
	private final Map<String, PlaceholderGroup> placeholderGroups = new CaseInsensitiveMap<>();
	
	public void loadGroups(FileConfiguration configuration) {
		Objects.requireNonNull(configuration, "configuration");
		
		placeholderGroups.clear();
		
		configuration.getConfigurationSection("placeholder-groups").getKeys(false).forEach(key -> {
			String path = "placeholder-groups." + key + ".";
			
			placeholderGroups.put(key,
					new PlaceholderGroup(key, configuration.getStringList("placeholder-groups." + key)));
		});
	}
	
	public Optional<PlaceholderGroup> getPlaceholderGroup(String placeholder) {
		Objects.requireNonNull(placeholder, "placeholder");
		return Optional.ofNullable(placeholderGroups.get(placeholder));
	}
	
	public boolean isPlaceholderGroup(String placeholder) {
		Objects.requireNonNull(placeholder, "placeholder");
		return placeholderGroups.containsKey(placeholder);
	}
	
	public void addPlaceholderGroup(String placeholder, PlaceholderGroup placeholderGroup) {
		Objects.requireNonNull(placeholder, "placeholder");
		Objects.requireNonNull(placeholderGroup, "placeholderGroup");
		placeholderGroups.put(placeholder, placeholderGroup);
	}
	
	public void invalidatePlaceholderGroup(String placeholder) {
		Objects.requireNonNull(placeholder, "placeholder");
		placeholderGroups.remove(placeholder);
	}
	
	public void invalidatePlaceholderGroup(PlaceholderGroup placeholderGroup) {
		Objects.requireNonNull(placeholderGroup, "placeholderGroup");
		placeholderGroups.remove(placeholderGroup.getPlaceholder());
	}
	
	public List<PlaceholderGroup> listPlaceholderGroups() {
		return new ArrayList<>(placeholderGroups.values());
	}
	
	public Stream<PlaceholderGroup> streamPlaceholderGroups() {
		return listPlaceholderGroups().stream();
	}
	
}