package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
		peopleHand = new JPanel();
		peopleHand.setLayout(new BoxLayout(peopleHand, BoxLayout.Y_AXIS));
		peopleHand.add(new JLabel("In Hand:"));
		peopleSeen = new JPanel();
		peopleSeen.setLayout(new BoxLayout(peopleSeen, BoxLayout.Y_AXIS));
		peopleSeen.add(new JLabel("Seen:"));
		roomHand = new JPanel();
		roomHand.setLayout(new BoxLayout(roomHand, BoxLayout.Y_AXIS));
		roomHand.add(new JLabel("In Hand:"));
		roomSeen = new JPanel();
		roomSeen.setLayout(new BoxLayout(roomSeen, BoxLayout.Y_AXIS));
		roomSeen.add(new JLabel("Seen:"));
		weaponHand = new JPanel();
		weaponHand.setLayout(new BoxLayout(weaponHand, BoxLayout.Y_AXIS));
		weaponHand.add(new JLabel("In Hand:"));
		weaponSeen = new JPanel();
		weaponSeen.setLayout(new BoxLayout(weaponSeen, BoxLayout.Y_AXIS));
		weaponSeen.add(new JLabel("Seen:"));
	}
	private void createUI() {
		JPanel all = new JPanel();
		all.setLayout(new BoxLayout(all, BoxLayout.Y_AXIS));
		all.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Known Cards"));
		
		JPanel people = new JPanel();
		people.setLayout(new BoxLayout(people, BoxLayout.Y_AXIS));
		people.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "People"));
		people.add(peopleHand);
		people.add(peopleSeen);
		all.add(people);
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
