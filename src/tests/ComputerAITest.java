package tests;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.DoorDirection;
import clueGame.Player;

public class ComputerAITest {
	public static Card reedCard = new Card("Reed", CardType.PERSON);
	public static Card henryCard = new Card("Henry", CardType.PERSON);
	public static Card steveCard = new Card("steve", CardType.PERSON);
	public static Card robCard = new Card("Rob", CardType.PERSON);
	
	public static Card bedroomCard = new Card("Bedroom", CardType.ROOM);
	public static Card kitchenCard = new Card("Kitchen", CardType.ROOM);
	public static Card livingCard = new Card("Living Room", CardType.ROOM);
	public static Card bathroomCard = new Card("Bathroom", CardType.ROOM);
	
	public static Card gunCard = new Card("Gun", CardType.WEAPON);
	public static Card knifeCard = new Card("Knife", CardType.WEAPON);
	public static Card carCard = new Card("Car", CardType.WEAPON);
	public static Card pillowCard = new Card("Pillow", CardType.WEAPON);
	
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
	public void testSelectTargets() {
		ComputerPlayer player = new ComputerPlayer("Bob", Color.black, 0, 0);
		Set<BoardCell> targets = new HashSet<BoardCell>();
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, false, ' ', "Test"));
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, false, ' ', "Test"));
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, false, ' ', "Test"));
		BoardCell target = player.selectTargets(targets);
		assertTrue(targets.contains(target));
		
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, true, ' ', "Bedroom"));
		target = player.selectTargets(targets);
		assertTrue(targets.contains(target));
		assertEquals("Bedroom", target.getRoomName());
		
		player.updateSeen(new Card("Bedroom", CardType.ROOM));
		target = player.selectTargets(targets);
		assertTrue(targets.contains(target));
		
		targets.clear();
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, true, ' ', "Bedroom"));
		target = player.selectTargets(targets);
		assertTrue(targets.contains(target));
		assertEquals("Bedroom", target.getRoomName());
	}
	
	@Test
	public static void testCreateSuggestion() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new ComputerPlayer("Reed", Color.black, 0, 0));
		players.add(new ComputerPlayer("Henry", Color.black, 0, 0));
		players.add(new ComputerPlayer("Steve", Color.black, 0, 0));
		players.add(new ComputerPlayer("Rob", Color.black, 0, 0));
		board.setPlayers(players);
		
	}
}
