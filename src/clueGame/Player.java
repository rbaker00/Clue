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
	private static ArrayList<Card> playerCards = new ArrayList<Card>();
	private static ArrayList<Card> roomCards = new ArrayList<Card>();
	private static ArrayList<Card> weaponCards = new ArrayList<Card>();
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
		this.updateSeen(card);
	}
	public Card disproveSuggestion(Solution s) {
		ArrayList<Card> match = new ArrayList<Card>();
		for(Card c : hand) {
			if(c.equals(s.player)) {
				match.add(c);
			}
			if(c.equals(s.room)) {
				match.add(c);
			}
			if(c.equals(s.weapon)) {
				match.add(c);
			}
		}
		if(match.size() == 1) {
			return match.get(0);
		}
		else if(match.size() > 1) {
			return match.get((int)(Math.random() * match.size()));
		}
		else {return null;}
	}
	public void updateSeen(Card seenCard) {
		if(!seen.contains(seenCard)) {
			seen.add(seenCard);
		}
	}
	public Solution getSuggestion() {
		return suggestion;
	}
	public ArrayList<Card> getHand() {
		return hand;
	}
	public ArrayList<Card> getSeen() {
		return seen;
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
	public boolean equals(Player target) {
		if (target == null) {
			return false;
		}
		return name.equals(target.name);
	}
	public void setDeck(ArrayList<Card> playerCards, ArrayList<Card> roomCards, ArrayList<Card> weaponCards) {
		this.playerCards = playerCards;
		this.roomCards = roomCards;
		this.weaponCards = weaponCards;
	}
	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}
	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}
	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}
}
