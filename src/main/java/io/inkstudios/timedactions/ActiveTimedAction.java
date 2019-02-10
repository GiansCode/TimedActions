package io.inkstudios.timedactions;

import java.time.Instant;

public class ActiveTimedAction {
	
	private final TimedAction timedAction;
	private final Instant announcedTimestamp;
	private final Instant commandExecuteTimestamp;
	
	private boolean executed;
	
	public ActiveTimedAction(TimedAction timedAction, Instant announcedTimestamp, Instant commandExecuteTimestamp) {
		this.timedAction = timedAction;
		this.announcedTimestamp = announcedTimestamp;
		this.commandExecuteTimestamp = commandExecuteTimestamp;
	}
	
	public TimedAction getTimedAction() {
		return timedAction;
	}
	
	public Instant getAnnouncedTimestamp() {
		return announcedTimestamp;
	}
	
	public Instant getCommandExecuteTimestamp() {
		return commandExecuteTimestamp;
	}
	
	public boolean isExecuted() {
		return executed;
	}
	
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
	
}