package clueGame;

import java.util.ArrayList;

//Class used to contain a room for a clue game
public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private Room secretPassage;
	private ArrayList<BoardCell> doors;
	
	public Room() {
		super();
		doors = new ArrayList<BoardCell>();
	}
	public Room getSecretPassage() {
		return secretPassage;
	}
	public void setSecretPassage(Room secretPassage) {
		this.secretPassage = secretPassage;
	}
	public Room(String name) {
		super();
		this.name = name;
		doors = new ArrayList<BoardCell>();
	}
	public String getName() {
		return name;
	}
	public BoardCell getLabelCell() {
		return labelCell;
	}
	public BoardCell getCenterCell() {
		return centerCell;
	}
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	public void addDoor (BoardCell door) {
		doors.add(door);
	}
	public ArrayList<BoardCell> getDoors() {
		return doors;
	}
}
