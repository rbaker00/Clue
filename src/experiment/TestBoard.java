package experiment;

import java.util.*;

public class TestBoard {
	Set<TestBoardCell> board;
	public TestBoard() {
		super();
		//board = new HashSet<TestBoardCell>();
	}
	public void calcTargets(TestBoardCell start, int pathlength) {
		return;
	}
	public Set<TestBoardCell> getTargets() {
		return new HashSet<TestBoardCell>();
	}
	public TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(-1, -1);
	}
}
