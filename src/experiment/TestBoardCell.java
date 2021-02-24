package experiment;

import java.util.*;

public class TestBoardCell {
	private int row;
	private int column;
	private Set<TestBoardCell> adjacents;
	private boolean room;
	private boolean occupied;
	
	public TestBoardCell(int row, int column) {
		super();
		//this.row = row;
		//this.column = column;
		//adjacents = new HashSet<TestBoardCell>();
	}
	public void addAdjacency(TestBoardCell cell) {
		//adjacents.add(cell);
	}
	public Set<TestBoardCell> getAdjList() {
		//return adjacents;
		return null;
	}
	public void setIsRoom(boolean room) {
		//this.room = room;
	}
	public boolean getRoom() {
		//return room;
		return false;
	}
	public void setOccupied(boolean occupied) {
		//this.occupied = occupied;
	}
	public boolean getOccupied() {
		//return occupied;
		return false;
		
	}
}
