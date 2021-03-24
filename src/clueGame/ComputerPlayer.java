package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	//Generates a somewhat random suggestion, where the room is passed through as a parameter
	public Solution createSuggestion(Room currentRoom) {
		ArrayList<Card> players = this.getPlayerCards();
		ArrayList<Card> rooms = this.getRoomCards();
		ArrayList<Card> weapons = this.getWeaponCards();
		Solution suggestion = new Solution();
		for (Card c : players) { //removes seen players
			if (this.getHand().contains(c)) {
				players.remove(players.indexOf(c));
			}
		}
		for (Card c : rooms) { //sets the room card
			if (c.getName().equals(currentRoom.getName())) {
				suggestion.room = c;
			}
		}
		for (Card c : weapons) { //removes seen weapons
			if (this.getHand().contains(c)) {
				players.remove(c);
			}
		}
		suggestion.player = players.get((int)(Math.random()*players.size()));
		suggestion.weapon = weapons.get((int)(Math.random()*weapons.size()));
		return suggestion;
	}
	//Selects a somewhat random target, but will always go to a room that it hasn't seen
	public BoardCell selectTargets(Set<BoardCell> targets) {
		int index = 0;
		int rand = (int)(Math.random()*targets.size());
		BoardCell randBoardCell = null;
		ArrayList<BoardCell> rooms = new ArrayList<BoardCell>();
		for (BoardCell b : targets) {
			if (b.isRoomCenter()) {
				boolean seen = false;
				for (Card c : this.getSeen()) {
					if (c.getName().equals(b.getRoomName())) {
						seen = true;
						break;
					}
				}
				if (!seen) { //if a room hasn't been seen, adds it to a list
					rooms.add(b);
				}
			}
			if (index == rand) { //gets a random target
				randBoardCell = b;
			}
			index++;
		}
		if (!rooms.isEmpty()) { //chooses a random unseen room, if one exists
			randBoardCell = rooms.get((int)(Math.random()*rooms.size()));
		}
		return randBoardCell;
	}
}
