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
	Player(String name, Color color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = col;
		hand = new ArrayList<Card>();
	}
	public void updateHand(Card card) {
		hand.add(card);
	}
	public Card disproveSuggestion(Solution s) {
		boolean sp = false;
		boolean sr = false;
		boolean sw = false;
		int maxI = -1;
		for(Card c : hand) {
			switch(c.getType()) {
			case PERSON:
				if(s.player == c) {
					sp = true;
				}
				break;
			case ROOM:
				if(s.room == c) {
					sr = true;
				}
				break;
			case WEAPON:
				if(s.weapon == c) {
					sw = true;
				}
				break;
			}
		}
		if(sp) {maxI++;}
		if(sr) {maxI++;}
		if(sw) {maxI++;}
		int index = (int) Math.random() * (maxI-0+1);
		switch(index) {
		case 0:
			for(Card c : hand) {
				if(c.getType() == CardType.PERSON) {
					return c;
				}
			}
		case 1:
			for(Card c : hand) {
				if(c.getType() == CardType.ROOM) {
					return c;
				}
			}
		case 2:
			for(Card c : hand) {
				if(c.getType() == CardType.WEAPON) {
					return c;
				}
			}
		}
		
		return null;
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
