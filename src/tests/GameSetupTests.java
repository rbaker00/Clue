package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import clueGame.Board;
import clueGame.Player;

public class GameSetupTests {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("board.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	@Test
	public void testPeople() {
		Player[] people = board.getPlayers();
		assertEquals(6, people.length);
		assertTrue("Reed Baker".equals(people[0]));
		assertTrue("Henry Purdum".equals(people[1]));
		assertTrue("Stephen Strange".equals(people[2]));
		assertTrue("Hippocrates".equals(people[3]));
		assertTrue("Martha Ballard".equals(people[4]));
		assertTrue("Mary Seacole".equals(people[5]));
	}
}
