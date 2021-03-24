package clueGame;

public class Solution {
	public Card player;
	public Card room;
	public Card weapon;
	public Solution() {
		super();
	}
	public Solution(Card player, Card room, Card weapon) {
		super();
		this.player = player;
		this.room = room;
		this.weapon = weapon;
	}
	public boolean equals(Solution target) {
		return player.equals(target.player) && room.equals(target.room) && weapon.equals(target.weapon);
	}
}
