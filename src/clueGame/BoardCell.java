package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public BoardCell() {
		super();
	}
	public void addAdj(BoardCell adj) {
		//adjList.add(adj);
	}
	public int getRows() {
		//return row;
		return -1;
	}
	public int getColumns() {
		//return col;
		return -1;
	}
	public char getInitial() {
		//return initial;
		return ' ';
	}
	public DoorDirection getDoorDirection() {
		//return doorDirection;
		return DoorDirection.NONE;
	}
	public boolean getRoomLabel() {
		//return roomLabel;
		return false;
	}
	public boolean isRoomCenter() {
		//return roomCenter;
		return false;
	}
	public char getSecretPassage() {
		//return secretPassage;
		return ' ';
	}
	public Set<BoardCell> getAdjList() {
		//return adjList;
		return new HashSet<BoardCell>;
	}
	public boolean isDoorway() {
		return false;
	}
	public boolean isLabel() {
		return false;
	}
}
