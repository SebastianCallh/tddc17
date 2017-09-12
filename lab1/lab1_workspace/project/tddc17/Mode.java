package tddc17;

// The agent is modeled as a state machine. The mode it is in is run every update
// returning a ModeResult, which specifies an agent action and the next mode.
public interface Mode {
	public ModeResult runMode(MyAgentState state, Boolean bump, Boolean dirt, Boolean home);
}