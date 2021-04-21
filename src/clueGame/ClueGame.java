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
	private String guess;
	private String guessResult;
	private int roll;
	public ClueGame(String title) {
		// send title to JFrame constructor
		super(title);
		
		// get initialize the board and draw
		center = Board.getInstance();
		center.setConfigFiles("board.csv", "ClueSetup.txt");	
		center.initialize();
		center.setFrame(this);
		
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
		
		// init fields in gui
		roll = (int)(Math.random() * 6) + 1;
		center.nextPlayer(roll);
		String currPlayerName = center.getCurrentPlayer().getName();
		right.setHuman(center.getCurrentPlayer());
		
		// init player dialog
		String dialog = "You are " + currPlayerName + ". Can you find the solution before the Computer players?";
		
		// show dialog to tell player who they are and start game
		guess = "No guess";
		guessResult = "N/A";
		updateControl();
		JOptionPane.showMessageDialog(this, dialog);
		
		// handle what happens when next button is clicked 
		bottom.getNextButton().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				roll = (int)(Math.random() * 6) + 1;
				guess = "No guess";
				guessResult = "N/A";
				center.nextPlayer(roll);
				updateControl();
			}
		});
		
		// handle what happens when make accusation button is clicked 
		bottom.getAccusationButton().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(center.getCurrentPlayer() instanceof HumanPlayer) {
					System.out.println("test");
				}
			}
		});
	}
	private void createUI() {
		// add each component to it's respective part of the mainPanel
		mainPanel.add(center, BorderLayout.CENTER);
		mainPanel.add(bottom, BorderLayout.SOUTH);
		mainPanel.add(right, BorderLayout.EAST);
	}
	public void setGuess(Solution suggestion) {
		guess = "Player: " + suggestion.player.getName() + "; Room: " + suggestion.room.getName() + "; Weapon: " + suggestion.weapon.getName();
	}
	public void setGuessResult(String guessResult) {
		this.guessResult = guessResult;
	}
	public void updateControl() {
		bottom.updateFields(guessResult, guess, center.getCurrentPlayer(), roll);
	}
	public void updateSeen(Player human) {
		right.updateSeen(human);
		revalidate();
	}
	public static void main(String[] args) {
		// create and draw the entire gui
		ClueGame gui = new ClueGame("Clue Game");
		gui.setContentPane(mainPanel); // put the panel in the frame
		gui.setSize(750, 750);  // size the frame
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		gui.createUI();
		gui.setVisible(true); // make it visible
	}
}

