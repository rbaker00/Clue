package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	public Solution createSuggestion(Room currentRoom) {
		ArrayList<Card> randomPlayers = new ArrayList<Card>();
		ArrayList<Card> randomWeapons = new ArrayList<Card>();
		Solution suggestion = new Solution();
		for (Card c : getPlayerCards()) {
			if (!getSeen().contains(c) && !getHand().contains(c)) {
				randomPlayers.add(c);
			}
		}
		for (Card c : getRoomCards()) {
			if (c.getName().equals(currentRoom.getName())) {
				suggestion.room = c;
			}
		}
		for (Card c : getWeaponCards()) {
			if (!getSeen().contains(c) && !getHand().contains(c)) {
				randomWeapons.add(c);
			}
		}
		suggestion.player = randomWeapons.get((int)(Math.random()*randomPlayers.size()));
		suggestion.weapon = randomWeapons.get((int)(Math.random()*randomWeapons.size()));
		return suggestion;
	}
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
				if (!seen) {
					rooms.add(b);
				}
			}
			if (index == rand) {
				randBoardCell = b;
			}
			index++;
		}
		if (!rooms.isEmpty()) {
			randBoardCell = rooms.get((int)(Math.random()*rooms.size()));
		}
		return randBoardCell;
	}
}
