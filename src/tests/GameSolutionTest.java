package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {
	public static Card reedCard = new Card("Reed", CardType.PERSON);
	public static Card henryCard = new Card("Henry", CardType.PERSON);
	public static Card steveCard = new Card("Steve", CardType.PERSON);
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
		//board.initialize();
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Reed", Color.black, 0, 0));
		players.add(new ComputerPlayer("Henry", Color.black, 0, 0));
		players.add(new ComputerPlayer("Steve", Color.black, 0, 0));
		players.add(new ComputerPlayer("Rob", Color.black, 0, 0));
		board.setPlayers(players);
	}
	
	@Test
	public void testAccusation() {
		Solution theSolution = new Solution();
		theSolution.player = reedCard;
		theSolution.room = bedroomCard;
		theSolution.weapon = gunCard;
		Solution theAccusation = new Solution();
		theAccusation.player = reedCard;
		theAccusation.room = bedroomCard;
		theAccusation.weapon = gunCard;
		board.setSolution(theSolution);
		
		assertTrue(board.checkAccusation(theAccusation));
		
		theAccusation.player = henryCard;
		assertFalse(board.checkAccusation(theAccusation));
		
		theAccusation.player = reedCard;
		theAccusation.room = kitchenCard;
		assertFalse(board.checkAccusation(theAccusation));
		
		theAccusation.room = bedroomCard;
		theAccusation.weapon = knifeCard;
		assertFalse(board.checkAccusation(theAccusation));
	}
	
	@Test
	public void testSuggestion() {
		ArrayList<Player> players = board.getPlayers();
		players.get(0).updateHand(reedCard);
		players.get(0).updateHand(henryCard);
//		players.get(0).updateHand(steveCard);
		players.get(1).updateHand(robCard);
//		players.get(1).updateHand(bedroomCard);
		players.get(1).updateHand(kitchenCard);
		players.get(2).updateHand(livingCard);
		players.get(2).updateHand(bathroomCard);
		players.get(2).updateHand(gunCard);
		players.get(3).updateHand(knifeCard);
		players.get(3).updateHand(carCard);
//		players.get(3).updateHand(pillowCard);
		
		Solution suggestion = new Solution();
		suggestion.player = reedCard;
		suggestion.room = livingCard;
		suggestion.weapon = gunCard;
		assertEquals(reedCard, players.get(0).disproveSuggestion(suggestion));
		Card dissprove = players.get(2).disproveSuggestion(suggestion);
		assertTrue(gunCard.equals(dissprove) || livingCard.equals(dissprove));
		assertNull(players.get(3).disproveSuggestion(suggestion));
		
		
		suggestion.player = steveCard;
		suggestion.room = bedroomCard;
		suggestion.weapon = pillowCard;
		assertNull(board.handleSuggestion(suggestion, players.get(0)));
		
		suggestion.player = reedCard;
		assertNull(board.handleSuggestion(suggestion, players.get(0)));
		assertEquals(reedCard, board.handleSuggestion(suggestion, players.get(1)));
		
		suggestion.room = kitchenCard;
		assertEquals(reedCard, board.handleSuggestion(suggestion, players.get(2)));
	}
}
