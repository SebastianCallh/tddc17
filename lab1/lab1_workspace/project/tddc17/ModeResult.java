package tddc17;

import aima.core.agent.Action;

public class ModeResult {
	
	public ModeResult(Action action, Mode nextMode) {
		this.action = action;
		this.nextMode = nextMode;
	}
	
	public final Action action;
	public final Mode nextMode;
}
