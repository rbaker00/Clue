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
	public CardPanel() {
		super(new BorderLayout());
		peopleHand = new JPanel(new BorderLayout());
		peopleSeen = new JPanel(new BorderLayout());
		roomHand = new JPanel(new BorderLayout());
		roomSeen = new JPanel(new BorderLayout());
		weaponHand = new JPanel(new BorderLayout());
		weaponSeen = new JPanel(new BorderLayout());
	}
	private void createUI() {
		JPanel all = new JPanel(new BorderLayout());
		all.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Known Cards"));
		add(all, BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame("Clue Game");  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(150, 800);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		panel.createUI();
		frame.setVisible(true); // make it visible
		
		
		// test filling in the data
//		panel.setTurn(new ComputerPlayer( "Col. Mustard", java.awt.Color.getColor("orange"), 0, 0), 5);
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
	}
}
