package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	private char playerType;
	private int row;
	private int column;
	private ArrayList<Card> hand;
	private ArrayList<Card> seen;
	private Solution suggestion;
	Player(String name, Color color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = col;
		hand = new ArrayList<Card>();
		seen = new ArrayList<Card>();
	}
	public void updateHand(Card card) {
		hand.add(card);
	}
	public Card disproveSuggestion(Solution s) {
		ArrayList<Card> match = new ArrayList<Card>();
		for(Card c : hand) {
			if(c == s.player) {
				match.add(c);
			}
			if(c == s.room) {
				match.add(c);
			}
			if(c == s.weapon) {
				match.add(c);
			}
		}
		if(match.size() == 1) {
			return match.get(0);
		}
		else if(match.size() > 1) {
			int index = (int) Math.random() * (match.size());
			return match.get(index);
		}
		else {return null;}
	}
	public void updateSeen(Card seenCard) {
		if(seen.contains(seenCard)) {
			return;
		}
		else {
			seen.add(seenCard);
		}
	}
	public ArrayList<Card> getHand() {
		return hand;
	}
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return column;
	}
}
