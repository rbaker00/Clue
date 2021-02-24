package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int column;
	private Set<TestBoardCell> adjacents;
	private boolean room;
	private boolean occupied;
	
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		adjacents = new HashSet<TestBoardCell>();
	}
	
	void addAdjacency(TestBoardCell cell) {
		adjacents.add(cell);
	}
	Set<TestBoardCell> getAdjList() {
		return adjacents;
	}
	void setRoom(boolean room) {
		this.room = room;
	}
	boolean getRoom() {
		return room;
	}
	void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	boolean getOccupied() {
		return occupied;
	}
}
