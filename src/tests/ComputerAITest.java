package tests;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;

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
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("board.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
	}
	
	@Test
	public void testSelectTargets() {
		
	}
	
	@Test
	public void testCreateSuggestion() {
		Room bedroom = new Room("Bedroom");
		Room kitchen = new Room("Kitchen");
		Room living = new Room("Living Room");
		Room bathroom = new Room("Bathroom");
		ComputerPlayer cp1 = new ComputerPlayer("Reed", Color.black, 0, 0);
		cp1.updateHand(reedCard);
		cp1.updateHand(bathroomCard);
		cp1.updateHand(gunCard);
		ComputerPlayer cp2 = new ComputerPlayer("Henry", Color.black, 0, 0);
		cp2.updateHand(henryCard);
		cp2.updateHand(bedroomCard);
		cp2.updateHand(knifeCard);
		ComputerPlayer cp3 = new ComputerPlayer("Steve", Color.black, 0, 0);
		cp3.updateHand(steveCard);
		cp3.updateHand(kitchenCard);
		cp3.updateHand(carCard);
		ComputerPlayer cp4 = new ComputerPlayer("Rob", Color.black, 0, 0);
		cp4.updateHand(robCard);
		cp4.updateHand(livingCard);
		cp4.updateHand(pillowCard);
		
		assertEquals(cp1.createSuggestion(bathroom).room, bathroomCard);
		assertEquals(cp2.createSuggestion(bedroom).room, bedroomCard);
		assertEquals(cp3.createSuggestion(kitchen).room, kitchenCard);
		assertEquals(cp4.createSuggestion(living).room, livingCard);
		
	}
}
