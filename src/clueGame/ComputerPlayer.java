package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	public Solution createSolution() {
		return null;
	}
	public BoardCell selectTargets() {
		return null;
	}
}
