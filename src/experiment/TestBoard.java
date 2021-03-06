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
		visited = new HashSet<TestBoardCell>();
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
		visited.add(start);
		findAllTargets(start, pathlength);
		visited.remove(start);
		return;
	}
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for(TestBoardCell adjCell : thisCell.getAdjList()) {
			if(visited.contains(adjCell) || adjCell.getOccupied()) {
				continue;
			}
			visited.add(adjCell);
			if(numSteps==1 || adjCell.getRoom()) {
				targets.add(adjCell);
			} else {
				findAllTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}
	}
	private void setAdjacencyList(TestBoardCell theCell) {
		for (int row = -1; row <= 1; row += 2) {
			if (theCell.getRow() + row > -1 && theCell.getRow() + row < ROWS) {
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
		return targets;
	}
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
