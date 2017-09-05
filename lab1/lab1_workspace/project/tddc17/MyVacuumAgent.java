package tddc17;


import aima.core.environment.liuvacuum.*;
import aima.core.agent.Action;
import aima.core.agent.AgentProgram;
import aima.core.agent.Percept;
import aima.core.agent.impl.*;

import java.util.Random;

class MyAgentState
{
	public int[][] world = new int[30][30];
	public int initialized = 0;
	final int UNKNOWN 	= 0;
	final int WALL 		= 1;
	final int CLEAR 	= 2;
	final int DIRT		= 3;
	final int HOME		= 4;
	final int ACTION_NONE 			= 0;
	final int ACTION_MOVE_FORWARD 	= 1;
	final int ACTION_TURN_RIGHT 	= 2;
	final int ACTION_TURN_LEFT 		= 3;
	final int ACTION_SUCK	 		= 4;
	
	public int agent_x_position = 1;
	public int agent_y_position = 1;
	public int agent_last_action = ACTION_NONE;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public int agent_direction = EAST;
	
	public Mode mode = new FindWallMode();
	
	MyAgentState()
	{
		for (int i=0; i < world.length; i++)
			for (int j=0; j < world[i].length ; j++)
				world[i][j] = UNKNOWN;
		world[1][1] = HOME;
		agent_last_action = ACTION_NONE;
	}
	
	// Based on the last action and the received percept updates the x & y agent position
	public void updatePosition(DynamicPercept p)
	{
		Boolean bump = (Boolean)p.getAttribute("bump");

		if (agent_last_action==ACTION_MOVE_FORWARD && !bump)
	    {
			switch (agent_direction) {
			case MyAgentState.NORTH:
				agent_y_position--;
				break;
			case MyAgentState.EAST:
				agent_x_position++;
				break;
			case MyAgentState.SOUTH:
				agent_y_position++;
				break;
			case MyAgentState.WEST:
				agent_x_position--;
				break;
			}
	    }
	}
	
	public void updateLastAction(Action action) {
		if (action == LIUVacuumEnvironment.ACTION_MOVE_FORWARD) {
			this.agent_last_action = ACTION_MOVE_FORWARD;
		} else if (action == LIUVacuumEnvironment.ACTION_TURN_RIGHT) {
			this.agent_last_action = ACTION_TURN_RIGHT;
		} else if (action == LIUVacuumEnvironment.ACTION_TURN_LEFT) {
			this.agent_last_action = ACTION_TURN_LEFT;
		} else if (action == LIUVacuumEnvironment.ACTION_SUCK) {
			this.agent_last_action = ACTION_SUCK;
		}
	}
	
	public void updateDirection(Action action) {
		if (action == LIUVacuumEnvironment.ACTION_TURN_LEFT) {
			this.agent_direction = ((this.agent_direction-1) % 4);
		    if (this.agent_direction<0) 
		    	this.agent_direction +=4;
		} else if (action == LIUVacuumEnvironment.ACTION_TURN_RIGHT){
			this.agent_direction = ((this.agent_direction+1) % 4);
		}
	}
	
	public void printWorldDebug()
	{
		for (int i=0; i < world.length; i++)
		{
			for (int j=0; j < world[i].length ; j++)
			{
				if (world[j][i]==UNKNOWN)
					System.out.print(" ? ");
				if (world[j][i]==WALL)
					System.out.print(" # ");
				if (world[j][i]==CLEAR)
					System.out.print(" . ");
				if (world[j][i]==DIRT)
					System.out.print(" D ");
				if (world[j][i]==HOME)
					System.out.print(" H ");
			}
			System.out.println("");
		}
	}
	
	public Position currentPosition() {
		return new Position(this.agent_x_position, this.agent_y_position);
	}
	
	public void setPosition(Position position, int type) {
		this.world[position.x][position.y]= type; 
	}
	
	public Position getForwardPosition() {
		switch (this.agent_direction) {
		case MyAgentState.NORTH:
			return new Position(this.agent_x_position,this.agent_y_position-1);
		case MyAgentState.EAST:
			return new Position(this.agent_x_position+1,this.agent_y_position);
		case MyAgentState.SOUTH:
			return new Position(this.agent_x_position,this.agent_y_position+1);
		case MyAgentState.WEST:
			return new Position(this.agent_x_position-1,this.agent_y_position);
		default: 
			return new Position(this.agent_x_position,this.agent_y_position);
		}
	}
	
	public Position getRightPosition() {
	switch (this.agent_direction) {
		case MyAgentState.NORTH:
			return new Position(this.agent_x_position+1,this.agent_y_position);
		case MyAgentState.EAST:
			return new Position(this.agent_x_position,this.agent_y_position+1);
		case MyAgentState.SOUTH:
			return new Position(this.agent_x_position-1,this.agent_y_position);
		case MyAgentState.WEST:
			return new Position(this.agent_x_position,this.agent_y_position-1);
		default: 
			return new Position(this.agent_x_position,this.agent_y_position);
		}
	}
	
	public int getTileData(Position p) {
		return this.world[p.x][p.y];
		
	}
}

class MyAgentProgram implements AgentProgram {

	private int initnialRandomActions = 10;
	private Random random_generator = new Random();
	
	// Here you can define your variables!
	public int iterationCounter = 1000;
	public MyAgentState state = new MyAgentState();
	
	// moves the Agent to a random start position
	// uses percepts to update the Agent position - only the position, other percepts are ignored
	// returns a random action
	private Action moveToRandomStartPosition(DynamicPercept percept) {
		int action = random_generator.nextInt(6);
		initnialRandomActions--;
		state.updatePosition(percept);
		if(action==0) {
		    state.agent_direction = ((state.agent_direction-1) % 4);
		    if (state.agent_direction<0) 
		    	state.agent_direction +=4;
		    state.agent_last_action = state.ACTION_TURN_LEFT;
			return LIUVacuumEnvironment.ACTION_TURN_LEFT;
		} else if (action==1) {
			state.agent_direction = ((state.agent_direction+1) % 4);
		    state.agent_last_action = state.ACTION_TURN_RIGHT;
		    return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
		} 
		state.agent_last_action=state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}
	
	
	@Override
	public Action execute(Percept percept) {
		
		// DO NOT REMOVE this if condition!!!
    	if (initnialRandomActions>0) {
    		return moveToRandomStartPosition((DynamicPercept) percept);
    	} else if (initnialRandomActions==0) {
    		
    		// process percept for the last step of the initial random actions
    		initnialRandomActions--;
    		state.updatePosition((DynamicPercept) percept);
			System.out.println("Processing percepts after the last execution of moveToRandomStartPosition()");
			state.agent_last_action=state.ACTION_SUCK;
	    	return LIUVacuumEnvironment.ACTION_SUCK;
    	}
    	iterationCounter--;
	    
	    if (iterationCounter==0)
	    	return NoOpAction.NO_OP;
	    
	    
	    DynamicPercept p = (DynamicPercept) percept;
	    Boolean bump = (Boolean)p.getAttribute("bump");
	    Boolean dirt = (Boolean)p.getAttribute("dirt");
	    Boolean home = (Boolean)p.getAttribute("home");
	    System.out.println("percept: " + p);
	    state.printWorldDebug();

		state.updatePosition((DynamicPercept) percept);
		System.out.println(state.agent_x_position + " " + state.agent_y_position);
	    // Sucking dirt is prioritized
	    if (dirt) {
	    	System.out.println("DIRT -> choosing SUCK action!");
	    	state.updateLastAction(LIUVacuumEnvironment.ACTION_SUCK);
	    	return LIUVacuumEnvironment.ACTION_SUCK;
	    } else {
	    	ModeResult result = state.mode.runMode(state, bump, dirt, home);
	    	state.updateLastAction(result.action);
	    	state.updateDirection(result.action);
	    	state.setPosition(state.currentPosition(), state.CLEAR);
	    	state.mode = result.nextMode;
	    	return result.action;
	    }
	}
	
	private boolean done() {
		return  state.world[state.agent_x_position][state.agent_y_position] != state.UNKNOWN &&
				state.world[state.agent_x_position][state.agent_y_position] != state.WALL;
	}
}

/*
 * 
	public void updateWorld(int x_position, int y_position, int info)
	{
		world[x_position][y_position] = info;
	}
 */
public class MyVacuumAgent extends AbstractAgent {
    public MyVacuumAgent() {
    	super(new MyAgentProgram());
	}
}
