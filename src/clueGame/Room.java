package clueGame;

//Class used to contain a room for a clue game
public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private Room secretPassage;
	public Room getSecretPassage() {
		return secretPassage;
	}
	public void setSecretPassage(Room secretPassage) {
		this.secretPassage = secretPassage;
	}
	public Room() {
		super();
	}
	public Room(String name) {
		super();
		this.name = name;
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
	
}
