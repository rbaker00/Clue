package experiment;

import java.util.*;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	public TestBoard() {
		super();
		grid = new TestBoardCell[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				grid[row][col] = new TestBoardCell(row, col);
			}
		}
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				this.setAdjacencyList(grid[row][col]);
			}
		}
	}
	public void calcTargets(TestBoardCell start, int pathlength) {
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		findAllTargets(start, pathlength);
		return;
	}
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		
	}
	private void setAdjacencyList(TestBoardCell theCell) {
		for (int row = -1; row <= 1; row += 2) {
			if (theCell.getColumn() + row > -1 && theCell.getColumn() + row < ROWS) {
				theCell.addAdjacency(getCell(theCell.getRow() + row, theCell.getColumn()));
			}
		}
		for (int col = -1; col <= 1; col += 2) {
			if (theCell.getColumn() + col > -1 && theCell.getColumn() + col < COLS) {
				theCell.addAdjacency(getCell(theCell.getRow(), theCell.getColumn() + col));
			}
		}
	}
	public Set<TestBoardCell> getTargets() {
		
		return null;
	}
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
