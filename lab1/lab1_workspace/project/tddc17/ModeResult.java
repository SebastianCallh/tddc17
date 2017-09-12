package tddc17;

import aima.core.agent.Action;

// The result of running a mode. Wraps an agent action and 
// the next mode of the agent.
public class ModeResult {
	
	public ModeResult(Action action, Mode nextMode) {
		this.action = action;
		this.nextMode = nextMode;
	}
	
	public final Action action;
	public final Mode nextMode;
}
