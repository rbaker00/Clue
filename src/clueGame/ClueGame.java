package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private static Board center;
	private static GameControlPanel bottom;
	private static CardPanel right;
	private static JPanel mainPanel;
	public ClueGame(String title) {
		// send title to JFrame constructor
		super(title);
		
		// get initialize the board and draw
		center = Board.getInstance();
		center.setConfigFiles("board.csv", "ClueSetup.txt");	
		center.initialize();
		
		// init other two panels
		bottom = new GameControlPanel();
		
		right = new CardPanel();
		
		// draw other two panels
		bottom.createUI();
		// test filling in the data
		right.createUI();
		
		// set prefered size of side panels
		bottom.setPreferredSize(new Dimension(750, 120));
		right.setPreferredSize(new Dimension(100, 800));
		
		// create/init a JPanel to hold these three guis
		mainPanel = new JPanel(new BorderLayout());
	}
	private void createUI() {
		// add each component to it's respective part of the mainPanel
		mainPanel.add(center, BorderLayout.CENTER);
		mainPanel.add(bottom, BorderLayout.SOUTH);
		mainPanel.add(right, BorderLayout.EAST);
	}
	
	public static void main(String[] args) {
		// create and draw the entire gui
		String guess = "I have no guess";
		String guessResult = "So you have nothing?";
		ClueGame gui = new ClueGame("Clue Game");
		gui.setContentPane(mainPanel); // put the panel in the frame
		gui.setSize(750, 750);  // size the frame
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		gui.createUI();
		int roll = (int)(Math.random() * 6) + 1;
		center.nextPlayer(roll);
		String currPlayerName = center.getCurrentPlayer().getName();
		String dialog = "You are " + currPlayerName + ". Can you find the solution before the Computer players?";
		JOptionPane.showMessageDialog(gui, dialog);
		bottom.updateFields(guessResult, guess, center.getCurrentPlayer(),roll);
		gui.setVisible(true); // make it visible
		
		bottom.getNextButton().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int roll = (int)(Math.random() * 6) + 1;
				center.nextPlayer(roll);
				String currPlayerName = center.getCurrentPlayer().getName();
				String dialog = "You are " + currPlayerName + ". Can you find the solution before the Computer players?";
				bottom.updateFields(guessResult, guess, center.getCurrentPlayer(),roll);
			}
		}
		);
	}
}

