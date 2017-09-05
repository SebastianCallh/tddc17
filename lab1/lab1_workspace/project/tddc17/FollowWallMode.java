package tddc17;

import aima.core.agent.Action;
import aima.core.environment.liuvacuum.LIUVacuumEnvironment;

public class FollowWallMode implements Mode {
	
	@Override
	public ModeResult runMode(MyAgentState state, Boolean bump, Boolean dirt, Boolean home) {
		if (hasCompletedTour(state)) {
			return new ModeResult(LIUVacuumEnvironment.ACTION_TURN_LEFT, new DepthFirstMode());
		} else if (rightTileKnown(state)) {
			if (bump) {
				return new ModeResult(LIUVacuumEnvironment.ACTION_TURN_LEFT, this);
			} else {
				return new ModeResult(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, this);
			}			
		} else {
			if (state.agent_last_action == state.ACTION_TURN_RIGHT){
				return new ModeResult(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, this);
			} else {
				return new ModeResult(LIUVacuumEnvironment.ACTION_TURN_RIGHT, this);
			}
		} 
			
			
			
		
		
		// state.agent_last_action
	}
	
	private Boolean hasCompletedTour(MyAgentState state) {
		return state.getTileData(state.getForwardPosition()) == state.CLEAR;
	}
	
	private Boolean rightTileKnown(MyAgentState state) {
		return state.getTileData(state.getRightPosition()) == state.UNKNOWN;
	}
}
