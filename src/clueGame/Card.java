package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	public Card (String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	@Override
    public boolean equals(Object o) {
  
        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
  
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Card)) {
            return false;
        }
          
        // typecast o to Complex so that we can compare data members 
        Card c = (Card) o;
          
        // Compare the data members and return accordingly 
        return cardName.equals(c.cardName) && type.equals(c.type);
    }

	public CardType getType() {
		return type;
	}
	public String getName() {
		return cardName;
	}
}
