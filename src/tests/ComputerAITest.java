package tests;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertEquals;

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
	
	@Test
	public void testSelectTargets() {
		ComputerPlayer player = new ComputerPlayer("Bob", Color.black, 0, 0);
		Set<BoardCell> targets = new HashSet<BoardCell>();
		
		//Tests that the player chooses a target
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, false, ' ', "Test"));
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, false, ' ', "Test"));
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, false, ' ', "Test"));
		BoardCell target = player.selectTargets(targets);
		assertTrue(targets.contains(target));
		
		//Tests that the player chooses an unseen room
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, true, ' ', "Bedroom"));
		target = player.selectTargets(targets);
		assertTrue(targets.contains(target));
		assertEquals("Bedroom", target.getRoomName());
		
		//Tests that the player chooses a random location when a room has been seen
		player.updateSeen(new Card("Bedroom", CardType.ROOM));
		target = player.selectTargets(targets);
		assertTrue(targets.contains(target));
		
		//Tests that the player still chooses a room if it has been seen
		targets.clear();
		targets.add(new BoardCell(0, 0, 'T', DoorDirection.NONE, false, true, ' ', "Bedroom"));
		target = player.selectTargets(targets);
		assertTrue(targets.contains(target));
		assertEquals("Bedroom", target.getRoomName());
	}
	
	@Test
	public void testCreateSuggestion() {
		Room bedroom = new Room("Bedroom");
		Room kitchen = new Room("Kitchen");
		Room living = new Room("Living Room");
		Room bathroom = new Room("Bathroom");
		ArrayList<Card> players = new ArrayList<Card>();
		players.add(reedCard);
		players.add(henryCard);
		players.add(steveCard);
		players.add(robCard);
		ArrayList<Card> rooms = new ArrayList<Card>();
		rooms.add(bedroomCard);
		rooms.add(kitchenCard);
		rooms.add(bathroomCard);
		rooms.add(livingCard);
		ArrayList<Card> weapons = new ArrayList<Card>();
		weapons.add(knifeCard);
		weapons.add(gunCard);
		weapons.add(carCard);
		weapons.add(pillowCard);
		
		ComputerPlayer cp1 = new ComputerPlayer("Reed", Color.black, 0, 0);
		cp1.updateHand(reedCard);
		cp1.updateHand(bathroomCard);
		cp1.updateHand(gunCard);
		cp1.setDeck(players, rooms, weapons);
		ComputerPlayer cp2 = new ComputerPlayer("Henry", Color.black, 0, 0);
		cp2.updateHand(henryCard);
		cp2.updateHand(bedroomCard);
		cp2.updateHand(knifeCard);
		cp2.setDeck(players, rooms, weapons);
		ComputerPlayer cp3 = new ComputerPlayer("Steve", Color.black, 0, 0);
		cp3.updateHand(steveCard);
		cp3.updateHand(kitchenCard);
		cp3.updateHand(carCard);
		cp3.setDeck(players, rooms, weapons);
		ComputerPlayer cp4 = new ComputerPlayer("Rob", Color.black, 0, 0);
		cp4.updateHand(robCard);
		cp4.updateHand(livingCard);
		cp4.updateHand(pillowCard);
		cp4.setDeck(players, rooms, weapons);
		
		assertEquals(cp1.createSuggestion(bathroom).room, bathroomCard);
		assertEquals(cp2.createSuggestion(bedroom).room, bedroomCard);
		assertEquals(cp3.createSuggestion(kitchen).room, kitchenCard);
		assertEquals(cp4.createSuggestion(living).room, livingCard);
	}
}
