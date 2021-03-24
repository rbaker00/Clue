package clueGame;

import java.util.HashSet;
import java.util.Set;

//Class used to contain a cell of a clue game
public class BoardCell {
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean isOccupied = false;
	private String roomName;
	
	public BoardCell() {
		super();
	}
	
	public BoardCell(int row, int col, char initial, DoorDirection doorDirection, boolean roomLabel, boolean roomCenter, char secretPassage, String roomName) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.doorDirection = doorDirection;
		this.roomLabel = roomLabel;
		this.roomCenter = roomCenter;
		this.secretPassage = secretPassage;
		this.roomName = roomName;
		adjList = new HashSet<BoardCell>();
	}
	public void addAdjacency(BoardCell adj) {
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
	public String getRoomName() {
		return roomName;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	public boolean isDoorway() {
		return doorDirection != DoorDirection.NONE;
	}
	public boolean isLabel() {
		return roomLabel;
	}
	public boolean getOccupied() {
		return isOccupied;
	}
	public void setOccupied(boolean b) {
		isOccupied = b;
	}
}
