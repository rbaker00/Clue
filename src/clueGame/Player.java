package clueGame;

public abstract class Player {
	String name;
	String color;
	char playerType;
	BoardCell startingLocation;
	Player(String name, String color) {
		super();
		this.name = name;
		this.color = color;
	}
}
