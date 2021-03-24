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
		if (target == null) {
			return false;
		}
		return cardName.equals(target.cardName) && type.equals(target.type);
	}
	public CardType getType() {
		return type;
	}
	public String getName() {
		return cardName;
	}
}
