package clueGame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel {
	JTextField guessTextField;
	JTextField guessResultTextField;
	JTextField whoseTurnTextField;
	JTextField rollTextField;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		super();
		whoseTurnTextField = new JTextField(15);
		whoseTurnTextField.setEditable(false);
		rollTextField = new JTextField(5);
		rollTextField.setEditable(false);
		guessTextField = new JTextField(20);
		guessTextField.setEditable(false);
		guessResultTextField = new JTextField(20);
		guessResultTextField.setEditable(false);
	}
	
	private void setGuessResult(String string) {
		return;
	}
	
	private void setGuess(String guess) {
		guessTextField.setText(guess);
	}

	private void setTurn(ComputerPlayer computerPlayer, int roll) {
		whoseTurnTextField.setText(computerPlayer.getName());
		rollTextField.setText(String.valueOf(roll));
	}
	
	private void createUI() {
		JPanel topPanel = new JPanel();
		JPanel top_leftPanel = new JPanel();
		JPanel top_rightPanel = new JPanel();
		topPanel.add(top_leftPanel, BorderLayout.WEST);
		JLabel whoseTurnLabel = new JLabel("Whose turn?");
		top_leftPanel.add(whoseTurnLabel, BorderLayout.NORTH);
		top_leftPanel.add(whoseTurnTextField, BorderLayout.SOUTH);
		topPanel.add(top_rightPanel, BorderLayout.CENTER);
		JLabel rollLabel = new JLabel("Roll:");
		top_rightPanel.add(rollLabel);
		top_rightPanel.add(rollTextField);
		JButton makeAccusationButton = new JButton("Make Accusation");
		topPanel.add(makeAccusationButton, BorderLayout.EAST);
		JButton nextButton = new JButton("NEXT!");
		topPanel.add(nextButton, BorderLayout.EAST);
		JPanel bottomPanel = new JPanel();
		JPanel bottom_leftPanel = new JPanel();
		bottom_leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Guess"));
		
		bottom_leftPanel.add(guessTextField);
		JPanel bottom_rightPanel = new JPanel();
		bottom_rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Guess Result"));
		
		bottom_rightPanel.add(guessResultTextField);
		bottomPanel.add(bottom_leftPanel, BorderLayout.WEST);
		bottomPanel.add(bottom_rightPanel, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame("Clue Game");  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		panel.createUI();
		frame.setVisible(true); // make it visible
		
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", java.awt.Color.getColor("orange"), 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}