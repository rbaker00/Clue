package clueGame;

import java.util.ArrayList;

public abstract class Player {
	String name;
	String color;
	char playerType;
	BoardCell startingLocation;
	ArrayList<Card> hand;
	Player(String name, String color) {
		super();
		this.name = name;
		this.color = color;
		hand = new ArrayList<Card>();
	}
	public void updateHand(Card card) {
		return;
	}
	public ArrayList<Card> getHand() {
		return hand;
	}
}
