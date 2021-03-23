package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	public Card (String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	public boolean equals(Card target) {
		return cardName == target.cardName && type == target.type;
	}
	public CardType getType() {
		return type;
	}
}
