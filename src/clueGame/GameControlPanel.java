package clueGame;


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel {
	// JPanels
	JPanel topPanel;
	JPanel top_leftPanel;
	JPanel top_rightPanel;
	JPanel bottomPanel;
	JPanel bottom_leftPanel;
	JPanel bottom_rightPanel;
	
	// JLabels
	JLabel whoseTurnLabel;
	JLabel rollLabel;
	
	// JTextFields
	JTextField guessTextField;
	JTextField guessResultTextField;
	JTextField whoseTurnTextField;
	JTextField rollTextField;
	
	//JButtons
	JButton makeAccusationButton;
	JButton nextButton;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		super();
		// JPanels
		topPanel = new JPanel(new BorderLayout());
		top_leftPanel = new JPanel(new BorderLayout());
		top_rightPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel(new BorderLayout());
		bottom_leftPanel = new JPanel(new BorderLayout());
		bottom_rightPanel = new JPanel(new BorderLayout());
		
		//JLabels
		whoseTurnLabel = new JLabel("Whose turn?");
		rollLabel = new JLabel("Roll:");
		
		// JTextFields
		whoseTurnTextField = new JTextField(15);
		whoseTurnTextField.setEditable(false);
		rollTextField = new JTextField(5);
		rollTextField.setEditable(false);
		guessTextField = new JTextField(20);
		guessTextField.setEditable(false);
		guessResultTextField = new JTextField(20);
		guessResultTextField.setEditable(false);
		
		// JButtons
		makeAccusationButton = new JButton("Make Accusation");
		nextButton = new JButton("NEXT!");
	}
	
	public JButton getNextButton() {
		return nextButton;
	}
	
	private void setGuessResult(String guessResult) {
		guessResultTextField.setText(guessResult);
	}
	
	private void setGuess(String guess) {
		guessTextField.setText(guess);
	}

	private void setTurn(ComputerPlayer computerPlayer, int roll) {
		whoseTurnTextField.setText(computerPlayer.getName());
		rollTextField.setText(String.valueOf(roll));
	}
	
	public void initFields() {
		// test filling in the data
		setTurn(new ComputerPlayer( "Col. Mustard", java.awt.Color.getColor("orange"), 0, 0), 5);
		setGuess( "I have no guess!");
		setGuessResult( "So you have nothing?");
	}
	
	public void updateFields(String guessResult, String guess, ComputerPlayer cp, int roll) {
		setTurn(cp, roll);
		setGuess(guess);
		setGuessResult(guessResult);
	}
	
	public void createUI() {
		
		// top panel adds and layout management
		topPanel.add(top_leftPanel, BorderLayout.WEST);
		top_leftPanel.add(whoseTurnLabel, BorderLayout.NORTH);
		top_leftPanel.add(whoseTurnTextField, BorderLayout.SOUTH);
		topPanel.add(top_rightPanel, BorderLayout.CENTER);
		top_rightPanel.add(rollLabel);
		top_rightPanel.add(rollTextField);
		topPanel.add(makeAccusationButton, BorderLayout.CENTER);
		topPanel.add(nextButton, BorderLayout.EAST);
		
		// bottom panel adds and layout management
		bottom_leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Guess"));
		bottom_leftPanel.add(guessTextField);
		bottom_rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Guess Result"));
		bottom_rightPanel.add(guessResultTextField);
		bottomPanel.add(bottom_leftPanel, BorderLayout.WEST);
		bottomPanel.add(bottom_rightPanel, BorderLayout.EAST);
		
		// game control panel adds and layout management
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