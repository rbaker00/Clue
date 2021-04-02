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

public class CardPanel extends JPanel {
	JPanel peopleHand;
	JPanel peopleSeen;
	JPanel roomHand;
	JPanel roomSeen;
	JPanel weaponHand;
	JPanel weaponSeen;
	private void createUI() {
		
	}
	public static void main(String[] args) {
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame("Clue Game");  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		panel.createUI();
		frame.setVisible(true); // make it visible
		
		
		// test filling in the data
//		panel.setTurn(new ComputerPlayer( "Col. Mustard", java.awt.Color.getColor("orange"), 0, 0), 5);
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
	}
}
