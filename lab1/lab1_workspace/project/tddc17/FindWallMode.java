package tddc17;

import aima.core.environment.liuvacuum.LIUVacuumEnvironment;

public class FindWallMode implements Mode {

	@Override
	public ModeResult runMode(MyAgentState state, Boolean bump, Boolean dirt, Boolean home) {
		if (bump) {
			return new ModeResult(LIUVacuumEnvironment.ACTION_TURN_LEFT, new FollowWallMode());
		} else {
			return new ModeResult(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, this);
		}
	}
}
