package tddc17;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.Set;

import aima.core.environment.liuvacuum.LIUVacuumEnvironment;

public class BreadthFirstMode implements Mode {
	private Queue<Position> path = new LinkedList<Position>();

	@Override
	public ModeResult runMode(MyAgentState state, Boolean bump, Boolean dirt, Boolean home) {
		if (state.getTileData(state.getCurrentPosition()) != state.HOME) {
			state.setTileData(state.getCurrentPosition(), state.CLEAR);
		}
		if (bump) {
			state.setTileData(state.getForwardPosition(), state.WALL);
		}
		
		Position currentPos = state.getCurrentPosition();
		
		if (this.path.isEmpty()) {
			Queue<Position> waypoints = this.pathToClosest(state, currentPos, state.UNKNOWN);
			if (waypoints != null) {
				this.path = waypoints;
			} else {
				state.returningHome = true;
				this.path = this.pathToClosest(state, currentPos, state.HOME);
			}
		}
		
		return this.moveTowards(state, this.path.peek());
	}
	
	private ModeResult moveTowards(MyAgentState state, Position pos) {
		if (pos.equals(state.getLeftPosition())) {
			return new ModeResult(LIUVacuumEnvironment.ACTION_TURN_LEFT, this);
		} else if (pos.equals(state.getRightPosition())) {
			return new ModeResult(LIUVacuumEnvironment.ACTION_TURN_RIGHT, this);
		} else if (pos.equals(state.getForwardPosition())) {
			// Only consume moves from the path if we actually move forward
			this.path.remove();
			return new ModeResult(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, this);
		} else {
			// The agent ends up here if pos is behind it, so it rotates.
			return new ModeResult(LIUVacuumEnvironment.ACTION_TURN_LEFT, this);
		}
	}
	
	// Uses breadth first to establish a path to the closest tile of the goal type
	private Queue<Position> pathToClosest(MyAgentState state, Position start, int goal) {
		Queue<Position> frontier = new LinkedList<Position>();
		Set<String> enqueued = new HashSet<String>();
		Map<Position, Position> preceedes = new HashMap<Position, Position>();
		frontier.add(start);
		enqueued.add(start.toString());

		while (!frontier.isEmpty()) {
			Position pos = frontier.remove();
			if (state.getTileData(pos) == goal) {
				return toQueue(preceedes, pos, start);
			}
			
			for (Position neighbour : pos.neighbours()) {
				if (!enqueued.contains(neighbour.toString()) && state.getTileData(neighbour) != state.WALL) {
					preceedes.put(neighbour, pos);
					frontier.add(neighbour);
					enqueued.add(neighbour.toString());
				}
			}
		}
		return null;
	}
	
	// Returns a queue of tiles leading from the "from" position to the "to" position
	private Queue<Position> toQueue(Map<Position, Position> preceedes, Position from, Position to) {
		Stack<Position> reversePath = new Stack<Position>();
		Position current = from;
		do {
			reversePath.add(current);
			current = preceedes.get(current);
		} while (!current.equals(to));
		
		Queue<Position> path = new LinkedList<Position>();
		while (!reversePath.empty()) {
			path.add(reversePath.pop());
		}
		return path;
	}
}
