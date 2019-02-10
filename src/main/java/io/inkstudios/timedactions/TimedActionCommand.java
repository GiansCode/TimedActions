package io.inkstudios.timedactions;

import io.inkstudios.timedactions.command.AllPlayerCommandPlaceholderParser;
import io.inkstudios.timedactions.command.ParsedCommand;
import io.inkstudios.timedactions.command.PlaceholderGroupCommandPlaceholderParser;
import io.inkstudios.timedactions.command.RandomPlayerCommandPlaceholderParser;

import java.util.Objects;

public class TimedActionCommand {

	public static final String ALL_PLAYERS = "%all_players%";
	public static final String RANDOM_PLAYER = "%random_player%";
	
	public static TimedActionCommand.Builder newBuilder() {
		return new Builder();
	}
	
	public static final class Builder {
		
		private TimedActionCommandExecutorType type;
		private String command;
		private int executionQuantity = -1;
		
		private Builder() {
		
		}
		
		public TimedActionCommand build() {
			validate();
			return new TimedActionCommand(type, command, executionQuantity);
		}
		
		private void validate() {
			Objects.requireNonNull(type, "Timed action command executor type is missing");
			Objects.requireNonNull(command, "Command is missing");
			
			if (executionQuantity == -1) {
				throw new IllegalArgumentException("execution quantity is missing");
			}
		}
		
		public Builder setTimedActionCommandExecutorType(TimedActionCommandExecutorType type) {
			this.type = type;
			return this;
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

	private final TimedActionCommandExecutorType type;
	private final String command;
	private final int executionQuantity;
	
	private TimedActionCommand(TimedActionCommandExecutorType type, String command, int executionQuantity) {
		this.type =  type;
		this.command = command;
		this.executionQuantity = executionQuantity;
	}
	
	public TimedActionCommandExecutorType getType() {
		return type;
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