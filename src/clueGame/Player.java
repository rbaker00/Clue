package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
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
	}
	//Returns a card that matches a card in the Solution parameter
	public Card disproveSuggestion(Solution s) {
		ArrayList<Card> match = new ArrayList<Card>();
		for(Card c : hand) { //searches for matching cards
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
		if(match.size() == 1) { //returns the single match
			return match.get(0);
		}
		else if(match.size() > 1) { //returns a random match
			return match.get((int)(Math.random() * match.size()));
		}
		else {return null;}
	}
	//adds a card to the seen list if it doesn't exist there already
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
	public static void setDeck(ArrayList<Card> playerCards, ArrayList<Card> roomCards, ArrayList<Card> weaponCards) {
		Player.playerCards = playerCards;
		Player.roomCards = roomCards;
		Player.weaponCards = weaponCards;
	}
	//Draws a circle to represent the player
	public void draw(int rectSize, Graphics g, int offset) {
		g.setColor(color);
		g.fillOval(column*rectSize+rectSize/10 + offset, row*rectSize+rectSize/10, rectSize*4/5, rectSize*4/5);
	}
	public static ArrayList<Card> getPlayerCards() {
		return playerCards;
	}
	public static ArrayList<Card> getRoomCards() {
		return roomCards;
	}
	public static ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}
	public void move (int row, int column) {
		this.row = row;
		this.column = column;
	}
}
