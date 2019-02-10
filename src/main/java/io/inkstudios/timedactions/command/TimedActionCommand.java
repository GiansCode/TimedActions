package io.inkstudios.timedactions.command;

import java.util.Objects;

public class TimedActionCommand {

	public static final String ALL_PLAYERS = "%all_players%";
	public static final String RANDOM_PLAYER = "%random_player%";
	
	public static TimedActionCommand.Builder newBuilder() {
		return new Builder();
	}
	
	public static final class Builder {
		
		private String command;
		private int executionQuantity = -1;
		
		private Builder() {
		
		}
		
		public TimedActionCommand build() {
			validate();
			return new TimedActionCommand(command, executionQuantity);
		}
		
		private void validate() {
			Objects.requireNonNull(command, "Command is missing");
			
			if (executionQuantity == -1) {
				throw new IllegalArgumentException("execution quantity is missing");
			}
		}
		
		public Builder setCommand(String command) {
			this.command = command;
			return this;
		}
		
		public Builder setExecutionQuantity(int executionQuantity) {
			this.executionQuantity = executionQuantity;
			return this;
		}
		
	}

	private final String command;
	private final int executionQuantity;
	
	private TimedActionCommand(String command, int executionQuantity) {
		this.command = command;
		this.executionQuantity = executionQuantity;
	}
	
	public String getCommand() {
		return command;
	}
	
	public int getExecutionQuantity() {
		return executionQuantity;
	}
	
	public ParsedCommand parseCommandPlaceholder() {
		String command = this.command.toLowerCase();
		if (command.contains(ALL_PLAYERS)) {
			return new AllPlayerCommandPlaceholderParser().parsePlayers(command);
		} else if (command.contains(RANDOM_PLAYER)) {
			return new RandomPlayerCommandPlaceholderParser().parsePlayers(command);
		} else {
			return new PlaceholderGroupCommandPlaceholderParser().parsePlayers(command);
		}
	}
	
}