package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

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
	public static void testSelectTargets() {
		
	}
	
	@Test
	public static void testCreateSuggestion() {
		
	}
}
