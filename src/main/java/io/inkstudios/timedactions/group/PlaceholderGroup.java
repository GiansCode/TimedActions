package io.inkstudios.timedactions.group;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderGroup {
	
	private final String placeholder;
	private final List<String> groups;
	
	public PlaceholderGroup(String placeholder, List<String> groups) {
		this.placeholder = placeholder;
		this.groups = new ArrayList<>(groups);
	}
	
	public String getPlaceholder() {
		return placeholder;
	}

	public boolean isGroup(String group) {
		return groups.contains(group);
	}
	
	public List<String> getGroups() {
		return new ArrayList<>(groups);
	}
	
}