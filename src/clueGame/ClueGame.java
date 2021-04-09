package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private static Board center;
	private static GameControlPanel bottom;
	private static CardPanel right;
	private static JPanel mainPanel;
	public ClueGame(String title) {
		super(title);
		center = Board.getInstance();
		center.setConfigFiles("board.csv", "ClueSetup.txt");	
		center.initialize();
		
		bottom = new GameControlPanel();
		right = new CardPanel();
		
		bottom.createUI();
		right.createUI();
		
		bottom.setPreferredSize(new Dimension(750, 180));
		right.setPreferredSize(new Dimension(150, 800));
		
		mainPanel = new JPanel(new BorderLayout());
	}
	private void createUI() {
		mainPanel.add(center, BorderLayout.CENTER);
		mainPanel.add(bottom, BorderLayout.SOUTH);
		mainPanel.add(right, BorderLayout.EAST);
	}
	public static void main(String[] args) {
		ClueGame gui = new ClueGame("Clue Game");
		gui.setContentPane(mainPanel); // put the panel in the frame
		gui.setSize(750, 750);  // size the frame
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		gui.createUI();
		gui.setVisible(true); // make it visible
	}
}

