package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

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
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
	public void createUI() {
		// puts the finished Panels together
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Known Cards"));
		
		JPanel people = new JPanel();
		people.setLayout(new BoxLayout(people, BoxLayout.Y_AXIS));
		people.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "People"));
		people.add(peopleHand);
		people.add(peopleSeen);
		people.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		add(people);
		
		JPanel rooms = new JPanel();
		rooms.setLayout(new BoxLayout(rooms, BoxLayout.Y_AXIS));
		rooms.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Rooms"));
		rooms.add(roomHand);
		rooms.add(roomSeen);
		rooms.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		add(rooms);
		
		JPanel weapons = new JPanel();
		weapons.setLayout(new BoxLayout(weapons, BoxLayout.Y_AXIS));
		weapons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Weapons"));
		weapons.add(weaponHand);
		weapons.add(weaponSeen);
		weapons.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		add(weapons);
	}
	private void setHuman (Player player) {
		// add TextFields for each Card in Hand
		for (Card card : player.getHand()) {
			JTextField text = new JTextField();
			text.setEditable(false);
			text.setText(card.getName());
			switch (card.getType()) {
			case PERSON:
				peopleHand.add(text);
				break;
			case ROOM:
				roomHand.add(text);
				break;
			case WEAPON:
				weaponHand.add(text);
				break;
			}
		}
		
		// add TextFields for each Card in Seen
		for (Card card : player.getSeen()) {
			JTextField text = new JTextField();
			text.setEditable(false);
			text.setText(card.getName());
			switch (card.getType()) {
			case PERSON:
				peopleSeen.add(text);
				break;
			case ROOM:
				roomSeen.add(text);
				break;
			case WEAPON:
				weaponSeen.add(text);
				break;
			}
		}
		
		// check if any Hands are empty and if so add a None TextField
		if(peopleHand.getComponentCount()==1) {
			JTextField text = new JTextField();
			text.setEditable(false);
			text.setText("None");
			peopleHand.add(text);
		}
		if(roomHand.getComponentCount()==1) {
			JTextField text = new JTextField();
			text.setEditable(false);
			text.setText("None");
			roomHand.add(text);
		}
		if(weaponHand.getComponentCount()==1) {
			JTextField text = new JTextField();
			text.setEditable(false);
			text.setText("None");
			weaponHand.add(text);
		}
		
		// check if any of the Seens are empty and if so add a None TextField
		if(peopleSeen.getComponentCount()==1) {
			JTextField text = new JTextField();
			text.setEditable(false);
			text.setText("None");
			peopleSeen.add(text);
		}
		if(roomSeen.getComponentCount()==1) {
			JTextField text = new JTextField();
			text.setEditable(false);
			text.setText("None");
			roomSeen.add(text);
		}
		if(weaponSeen.getComponentCount()==1) {
			JTextField text = new JTextField();
			text.setEditable(false);
			text.setText("None");
			weaponSeen.add(text);
		}
	}
	public static void main(String[] args) {
		CardPanel panel = new CardPanel();  // create the panel
		JFrame frame = new JFrame("Clue Game");  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(150, 800);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		
		
		Player player = new HumanPlayer("Test", Color.black, 0, 0);
		player.updateHand(new Card("Person1", CardType.PERSON));
		player.updateHand(new Card("Person2", CardType.PERSON));
		player.updateHand(new Card("Room1", CardType.ROOM));
		player.updateHand(new Card("Room2", CardType.ROOM));
		player.updateHand(new Card("Weapon1", CardType.WEAPON));
		player.updateHand(new Card("Weapon2", CardType.WEAPON));
		player.updateSeen(new Card("Person3", CardType.PERSON));
		player.updateSeen(new Card("Person4", CardType.PERSON));
		player.updateSeen(new Card("Room3", CardType.ROOM));
		player.updateSeen(new Card("Room4", CardType.ROOM));
		player.updateSeen(new Card("Weapon3", CardType.WEAPON));
		player.updateSeen(new Card("Weapon4", CardType.WEAPON));
		panel.setHuman(player);
		panel.createUI();
		frame.setVisible(true); // make it visible
		// test filling in the data
//		panel.setTurn(new ComputerPlayer( "Col. Mustard", java.awt.Color.getColor("orange"), 0, 0), 5);
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
	}
}
