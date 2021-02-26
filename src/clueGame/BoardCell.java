package clueGame;

import java.util.Set;

public class BoardCell {
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private Boolean roomLabel, roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public BoardCell() {
		super();
	}
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	public int getRows() {
		return row;
	}
	public int getColumns() {
		return col;
	}
	public char getInitial() {
		return initial;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public Boolean getRoomLabel() {
		return roomLabel;
	}
	public Boolean getRoomCenter() {
		return roomCenter;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
}
