package io.inkstudios.timedactions;

import org.spigotmc.CaseInsensitiveMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class TimedActions {

	private final Map<String, TimedAction> timedActions = new CaseInsensitiveMap<>();
	
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
	
}