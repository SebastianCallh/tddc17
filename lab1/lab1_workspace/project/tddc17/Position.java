package tddc17;

import java.util.ArrayList;
import java.util.List;

// A class to represent positions in a 2D grid.
public class Position {

	public final int x;
	public final int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return this.x + " " + this.y;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Position))return false;
	    Position pos = (Position)other;
		return this.x == pos.x && this.y == pos.y;
	}
	
	public List<Position> neighbours() {
		List<Position> neighbours = new ArrayList<Position>();
		neighbours.add(this.north());
		neighbours.add(this.east());
		neighbours.add(this.west());
		neighbours.add(this.south());
		return neighbours;
	}
	
	public Position north() {
		return new Position(this.x,this.y-1);
	}
	
	
	public Position east() {
		return new Position(this.x+1,this.y);
	}
	
	public Position south() {
		return new Position(this.x,this.y+1);
	}
	
	public Position west() {
		return new Position(this.x-1,this.y);
	}
}
