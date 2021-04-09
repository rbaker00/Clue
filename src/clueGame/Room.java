package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//Class used to contain a room for a clue game
public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private Room secretPassage;
	
	public Room() {
		super();
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
	//draws the room name on the label cell
	public void draw(int rectSize, Graphics g) {
		g.setColor(Color.orange);
		g.setFont(new Font("TimesRoman", Font.PLAIN, rectSize*9/20)); 
		g.drawString(name, labelCell.getColumns()*rectSize, labelCell.getRows()*rectSize + rectSize/2);
	}
}
