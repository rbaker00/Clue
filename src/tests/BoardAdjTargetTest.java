package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
		
		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("board.csv", "Rooms.txt");		
			// Initialize will load config files 
			board.initialize();
		}

		// Ensure that player does not move around within room
		// These cells are LIGHT ORANGE on the planning spreadsheet
		@Test
		public void testAdjacenciesRooms()
		{
			// we want to test a couple of different rooms.
			// First, the study that only has a single door but a secret room
			Set<BoardCell> testList = board.getAdjList(1, 20);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(2, 16)));
			assertTrue(testList.contains(board.getCell(22, 5)));
			
			// now test the ballroom (note not marked since multiple test here)
			testList = board.getAdjList(3, 4);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(7, 4)));
			
			// one more room, the kitchen
			testList = board.getAdjList(13, 22);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(13, 18)));
			assertTrue(testList.contains(board.getCell(18, 21)));
		}

		
		// Ensure door locations include their rooms and also additional walkways
		// These cells are LIGHT ORANGE on the planning spreadsheet
		@Test
		public void testAdjacencyDoor()
		{
			Set<BoardCell> testList = board.getAdjList(7, 4);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(3, 4)));
			assertTrue(testList.contains(board.getCell(7, 3)));

			testList = board.getAdjList(5, 11);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(2, 11)));
			assertTrue(testList.contains(board.getCell(5,10)));
			assertTrue(testList.contains(board.getCell(5, 12)));
			
			testList = board.getAdjList(13, 18);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(13, 17)));
			assertTrue(testList.contains(board.getCell(12, 18)));
			assertTrue(testList.contains(board.getCell(14, 18)));
			assertTrue(testList.contains(board.getCell(13, 22)));
		}
		
		// Test a variety of walkway scenarios
		// These tests are Dark Orange on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			Set<BoardCell> testList = board.getAdjList(8, 0);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(7, 0)));
			
			// Test near a door but not adjacent
			testList = board.getAdjList(7, 6);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(7, 5)));
			assertTrue(testList.contains(board.getCell(7, 7)));
			assertTrue(testList.contains(board.getCell(8, 6)));

			// Test adjacent to walkways
			testList = board.getAdjList(7, 9);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(7, 8)));
			assertTrue(testList.contains(board.getCell(7, 10)));
			assertTrue(testList.contains(board.getCell(6, 9)));
			assertTrue(testList.contains(board.getCell(8, 9)));

			// Test next to closet
			testList = board.getAdjList(8,14);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(7, 14)));
			assertTrue(testList.contains(board.getCell(8, 13)));
			assertTrue(testList.contains(board.getCell(8, 15)));
		
		}
		
		
		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsInDeliveryRoom() {
			// test a roll of 1
			board.calcTargets(board.getCell(16, 4), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCell(16, 8)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(16, 4), 3);
			targets= board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(14, 8)));
			assertTrue(targets.contains(board.getCell(18, 8)));	
			assertTrue(targets.contains(board.getCell(16, 10)));
			assertTrue(targets.contains(board.getCell(15, 9)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(12, 20), 4);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(14, 7)));
			assertTrue(targets.contains(board.getCell(18, 7)));	
			assertTrue(targets.contains(board.getCell(16, 11)));
			assertTrue(targets.contains(board.getCell(13, 8)));	
		}
		
		@Test
		public void testTargetsInKitchen() {
			// test a roll of 1
			board.calcTargets(board.getCell(1, 20), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(2, 16)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(1, 20), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(22, 5)));
			assertTrue(targets.contains(board.getCell(0, 16)));	
			assertTrue(targets.contains(board.getCell(4, 16)));
			assertTrue(targets.contains(board.getCell(2, 14)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(1, 20), 4);
			targets= board.getTargets();
			assertEquals(10, targets.size());
			assertTrue(targets.contains(board.getCell(2, 13)));
			assertTrue(targets.contains(board.getCell(5, 16)));	
			assertTrue(targets.contains(board.getCell(0, 15)));
			assertTrue(targets.contains(board.getCell(22, 5)));	
			
			// test out of center
			board.calcTargets(board.getCell(1, 21), 4);
			targets= board.getTargets();
			assertEquals(0, targets.size());
		}

		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsAtDoor() {
			// test a roll of 1, at door
			board.calcTargets(board.getCell(2, 16), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(1, 16)));
			assertTrue(targets.contains(board.getCell(2, 15)));	
			assertTrue(targets.contains(board.getCell(1, 20)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(2, 16), 3);
			targets= board.getTargets();
			assertEquals(11, targets.size());
			assertTrue(targets.contains(board.getCell(2, 13)));
			assertTrue(targets.contains(board.getCell(5, 16)));	
			assertTrue(targets.contains(board.getCell(0, 15)));
			assertTrue(targets.contains(board.getCell(1, 20)));	
		}

		@Test
		public void testTargetsInWalkway1() {
			// test a roll of 1
			board.calcTargets(board.getCell(7, 9), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(7, 8)));
			assertTrue(targets.contains(board.getCell(7, 10)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(7, 9), 3);
			targets= board.getTargets();
			assertEquals(14, targets.size());
			assertTrue(targets.contains(board.getCell(4, 9)));
			assertTrue(targets.contains(board.getCell(7, 6)));
			assertTrue(targets.contains(board.getCell(7, 12)));	
		}

		@Test
		public void testTargetsInWalkway2() {
			// test a roll of 1
			board.calcTargets(board.getCell(18, 0), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(18, 1)));
			assertTrue(targets.contains(board.getCell(19, 0)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(13, 7), 3);
			targets= board.getTargets();
			assertEquals(10, targets.size());
			assertTrue(targets.contains(board.getCell(15, 6)));
			assertTrue(targets.contains(board.getCell(14, 7)));
			assertTrue(targets.contains(board.getCell(11, 8)));	
		}

		@Test
		// test to make sure occupied locations do not cause problems
		public void testTargetsOccupied() {
			// test a roll of 4 blocked 2 down
			board.getCell(15, 7).setOccupied(true);
			board.calcTargets(board.getCell(13, 7), 4);
			board.getCell(15, 7).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(13, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(15, 9)));
			assertTrue(targets.contains(board.getCell(11, 5)));	
			assertFalse( targets.contains( board.getCell(15, 7))) ;
			assertFalse( targets.contains( board.getCell(17, 7))) ;
		
			// we want to make sure we can get into a room, even if flagged as occupied
			board.getCell(12, 20).setOccupied(true);
			board.getCell(8, 18).setOccupied(true);
			board.calcTargets(board.getCell(8, 17), 1);
			board.getCell(12, 20).setOccupied(false);
			board.getCell(8, 18).setOccupied(false);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(7, 17)));	
			assertTrue(targets.contains(board.getCell(8, 16)));	
			assertTrue(targets.contains(board.getCell(12, 20)));	
			
			// check leaving a room with a blocked doorway
			board.getCell(12, 15).setOccupied(true);
			board.calcTargets(board.getCell(12, 20), 3);
			board.getCell(12, 15).setOccupied(false);
			targets= board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(6, 17)));
			assertTrue(targets.contains(board.getCell(8, 19)));	
			assertTrue(targets.contains(board.getCell(8, 15)));
}
