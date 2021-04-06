package clueGame;

import java.awt.Color;
import java.awt.Graphics;
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
	public void draw(int rectSize, Graphics g) {
		if (initial == 'W') {
			g.setColor(Color.black);
			g.fillRect(col*rectSize, row*rectSize, rectSize, rectSize);
			g.setColor(Color.yellow);
			g.fillRect(col*rectSize+rectSize/10, row*rectSize+rectSize/10, rectSize*4/5, rectSize*4/5);
			if (doorDirection != DoorDirection.NONE) {
				g.setColor(Color.blue);
				switch (doorDirection) {
				case UP:
					g.fillRect(col*rectSize+rectSize/10, row*rectSize, rectSize*4/5, rectSize*2/10);
					break;
				case DOWN:
					g.fillRect(col*rectSize+rectSize/10, row*rectSize+rectSize*4/5, rectSize*4/5, rectSize*2/10);
					break;
				case LEFT:
					g.fillRect(col*rectSize, row*rectSize+rectSize/10, rectSize*2/10, rectSize*4/5);
					break;
				case RIGHT:
					g.fillRect(col*rectSize+rectSize*4/5, row*rectSize+rectSize/10, rectSize*2/10, rectSize*4/5);
					break;
				}
			}
		}
		else {
			if (initial == 'X') {
				g.setColor(Color.black);
			}
			else {
				g.setColor(Color.gray);
			}
			g.fillRect(col*rectSize, row*rectSize, rectSize, rectSize);
		}
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
