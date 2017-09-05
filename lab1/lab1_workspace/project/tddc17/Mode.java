package tddc17;

public interface Mode {
	public ModeResult runMode(MyAgentState state, Boolean bump, Boolean dirt, Boolean home);
}