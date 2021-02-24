package tests;

import experiment.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
	}
}
