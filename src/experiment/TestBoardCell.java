package experiment;

import java.util.*;

public class TestBoardCell {
	private int row;
	private int column;
	private Set<TestBoardCell> adjacents;
	private boolean isRoom;
	private boolean isOccupied;
	
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		adjacents = new HashSet<TestBoardCell>();
	}
	public void addAdjacency(TestBoardCell cell) {
		adjacents.add(cell);
	}
	public Set<TestBoardCell> getAdjList() {
		return adjacents;
	}
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	public boolean getRoom() {
		return isRoom;
	}
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	public boolean getOccupied() {
		return isOccupied;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
}
