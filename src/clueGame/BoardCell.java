package clueGame;

import java.util.Set;

public class BoardCell {
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private Boolean roomLabel, roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
}
