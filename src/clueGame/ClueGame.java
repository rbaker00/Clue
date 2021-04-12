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
		bottom.initFields();
		right.createUI();
		
		// set prefered size of side panels
		bottom.setPreferredSize(new Dimension(750, 180));
		right.setPreferredSize(new Dimension(150, 800));
		
		// create/init a JPanel to hold these three guis
		mainPanel = new JPanel(new BorderLayout());
	}
	private void createUI() {
		// add each component to it's respective part of the mainPanel
		mainPanel.add(center, BorderLayout.CENTER);
		mainPanel.add(bottom, BorderLayout.SOUTH);
		mainPanel.add(right, BorderLayout.EAST);
	}
	private void updateUI(String guessResult, String guess, ComputerPlayer cp, int roll) {
		bottom.updateFields(guessResult, guess, cp, roll);
	}
	
	public static void main(String[] args) {
		// create and draw the entire gui
		ClueGame gui = new ClueGame("Clue Game");
		gui.setContentPane(mainPanel); // put the panel in the frame
		gui.setSize(750, 750);  // size the frame
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		gui.createUI();
		gui.setVisible(true); // make it visible
		
		JOptionPane.showMessageDialog(gui, "You are [Miss Scarlet]. Can you find the solution before the Computer players?");
		
		bottom.getNextButton().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				updateUI();
			}
		}
		);
	}
}

