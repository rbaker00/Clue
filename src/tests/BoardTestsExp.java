package tests;

import org.junit.jupiter.api.BeforeEach;

import experiment.TestBoardCell;
import junit.framework.*;

@Test
public class BoardTestsExp {
	TestBoard board;
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	@Test
	public void testAdjacency() {
		// top left
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
	}
}
