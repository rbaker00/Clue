package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	Board center;
	GameControlPanel bottom;
	CardPanel right;
	public ClueGame() {
		super();
		center = new Board();
		bottom = new GameControlPanel();
		right = new CardPanel();
	}
	private void createUI() {
		
	}
	public static void main(String[] args) {
		ClueGame gui = new ClueGame();
		gui.createUI();
	}
}
