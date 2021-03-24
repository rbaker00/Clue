package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import clueGame.Board;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class GameSetupTests {
	private static Board board;
	private static final int NUM_ROOMS = 9;
	private static final int NUM_PLAYERS = 6;
	private static final int NUM_WEAPONS = 6;
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
		ArrayList<Player> people = board.getPlayers();
		assertEquals(NUM_PLAYERS, people.size());
		assertTrue("Reed Baker".equals(people.get(0).getName()));
		assertTrue("Henry Purdum".equals(people.get(1).getName()));
		assertTrue("Stephen Strange".equals(people.get(2).getName()));
		assertTrue("Hippocrates".equals(people.get(3).getName()));
		assertTrue("Martha Ballard".equals(people.get(4).getName()));
		assertTrue("Mary Seacole".equals(people.get(5).getName()));
		
		assertEquals(new Color(102, 204, 0), new Color(102, 204, 0));
		assertEquals(new Color(102, 204, 0), people.get(0).getColor());
		assertEquals(new Color(0, 76, 153), people.get(1).getColor());
		assertEquals(new Color(204, 204, 0), people.get(2).getColor());
		assertEquals(new Color(204, 255, 153), people.get(3).getColor());
		assertEquals(new Color(178, 102, 255), people.get(4).getColor());
		assertEquals(new Color(255, 0, 0), people.get(5).getColor());
		
		assertEquals(13, people.get(0).getRow());
		assertEquals(2, people.get(0).getCol());
		assertEquals(6, people.get(1).getRow());
		assertEquals(1, people.get(1).getCol());
		assertEquals(2, people.get(2).getRow());
		assertEquals(9, people.get(2).getCol());
		assertEquals(0, people.get(3).getRow());
		assertEquals(15, people.get(3).getCol());
		assertEquals(5, people.get(4).getRow());
		assertEquals(24, people.get(4).getCol());
		assertEquals(10, people.get(5).getRow());
		assertEquals(23, people.get(5).getCol());
		
		assertEquals(people.get(2).getClass(), HumanPlayer.class);
		assertEquals(people.get(0).getClass(), ComputerPlayer.class);
		assertEquals(people.get(1).getClass(), ComputerPlayer.class);
		assertEquals(people.get(3).getClass(), ComputerPlayer.class);
		assertEquals(people.get(4).getClass(), ComputerPlayer.class);
		assertEquals(people.get(5).getClass(), ComputerPlayer.class);
	}
	
	@Test
	public void testCards() {
		int rooms = 0;
		int players = 0;
		int weapons = 0;
		for (Player person : board.getPlayers()) {
			assertNotNull(person);
			assertFalse(person.getHand().size() == 0);
			for (Card card : person.getHand()) {
				switch (card.getType()) {
				case ROOM:
					rooms++;
					break;
				case PERSON:
					players++;
					break;
				case WEAPON:
					weapons++;
					break;
				}
			}
		}
		assertEquals(NUM_ROOMS-1, rooms);
		assertEquals(NUM_PLAYERS-1, players);
		assertEquals(NUM_WEAPONS-1, weapons);
	}
	
	@Test
	public void testSolution() {
		assertNotNull(board.getSolution().player);
		assertNotNull(board.getSolution().room);
		assertNotNull(board.getSolution().weapon);
	}
}
