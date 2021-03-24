package clueGame;

public class Solution {
	public Card player;
	public Card room;
	public Card weapon;
	public boolean equals(Solution target) {
		return player.equals(target.player) && room.equals(target.room) && weapon.equals(target.weapon);
	}
}
