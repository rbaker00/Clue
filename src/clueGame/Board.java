package clueGame;

import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;

//Class to contain a clue board
public class Board extends JPanel{
	private BoardCell[][] grid;
	private int numRows = 0;
	private int numColumns = 0;
	private int rectSize;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
	private ArrayList<Room> rooms;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private ArrayList<Player> players;
	private int currentPlayer;
	private Solution solution;
	private boolean accusation;
	ClueGame frame;
	
	public Board() {
		super();
	}
	//Returns the instance of the board (because only 1 board can be created)
	public static Board getInstance() {
		return theInstance;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	//Calls the methods to set up the board from the two config files
	public void initialize() {
		currentPlayer = -1;
		addMouseListener(new ClickListener());
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		players = new ArrayList<Player>();
		accusation = false;
		try {
			loadSetupConfig();
			loadLayoutConfig();
			setAllAdjacencies();
		}
		catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	//Loads the file that stores all of the rooms and some information about them
	public void loadSetupConfig() throws BadConfigFormatException {
		rooms = new ArrayList<Room>();
		File setup = new File(setupConfigFile);
		int weapons = 0;
		ArrayList<Card> deck = new ArrayList<Card>();
	    Scanner myReader;
		try {
			myReader = new Scanner(setup);
			roomMap = new HashMap<Character, Room>();
			setupCards(myReader, deck, weapons);
		    myReader.close();
		    if (players.size() != 0) {
		    	dealOutDeck(deck, weapons);
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
	private void setupCards(Scanner myReader, ArrayList<Card> deck, int weapons) throws BadConfigFormatException {
		ArrayList<Card> playerCards = new ArrayList<Card>();
		ArrayList<Card> roomCards = new ArrayList<Card>();
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		while (myReader.hasNextLine()) {
		    String[] data = myReader.nextLine().split(", ");
		    if (data[0].equals("Room") || data[0].equals("Space")) { //puts the data of the room in the appropriate data structures
		        if (data.length != 3) {
		        	myReader.close();
			    	throw new BadConfigFormatException("Room");
		        }
		    	rooms.add(new Room(data[1]));
		        roomMap.put(data[2].charAt(0), rooms.get(rooms.size()-1));
		        if (data[0].equals("Room")) {
		        	Card roomCard = new Card(data[1], CardType.ROOM);
		        	roomCards.add(roomCard);
		        }
		    }
		    else if (data[0].equals("Player")) {
		    	if (data.length != 5) {
		        	myReader.close();
			    	throw new BadConfigFormatException("size");
		        }
		    	String[] color = data[2].split(" ");
		    	if (color.length != 3) {
		        	myReader.close();
			    	throw new BadConfigFormatException("color");
		        }
		    	Color theColor = new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
		    	String[] location = data[3].split(" ");
		    	if (location.length != 2) {
		        	myReader.close();
			    	throw new BadConfigFormatException("location");
		        }
		    	if (data[4].equals("Computer")) {
		    		players.add(new ComputerPlayer(data[1], theColor, Integer.parseInt(location[0]), Integer.parseInt(location[1])));
		    	}
		    	else if (data[4].equals("Human")) {
		    		players.add(new HumanPlayer(data[1], theColor, Integer.parseInt(location[0]), Integer.parseInt(location[1])));
		    	}
		    	else {
		    		myReader.close();
			    	throw new BadConfigFormatException("type");
		    	}
		    	Card personCard = new Card(data[1], CardType.PERSON);
		    	playerCards.add(personCard);
		    }
		    else if (data[0].equals("Weapon")) {
		    	if(data.length != 2) {
		    		myReader.close();
		    		throw new BadConfigFormatException("Weapon");
		    	}
		    	else {
		    		Card weaponCard = new Card(data[1], CardType.WEAPON);
		    		weaponCards.add(weaponCard);
		    		weapons++;
		    	}
		    }
		    else if (!data[0].substring(0, 2).equals("//")) { //thrown if a line does not contain Room or Space and is not a comment
		    	myReader.close();
		    	throw new BadConfigFormatException("Comment");
		    }
		}
		if (playerCards.size() != 0) {
			Player.setDeck((ArrayList<Card>) playerCards.clone(), (ArrayList<Card>) roomCards.clone(), (ArrayList<Card>) weaponCards.clone());
			solution = new Solution();
			int rand = (int)(Math.random()*(playerCards.size()));
			solution.player = playerCards.get(rand);
			playerCards.remove(rand);
			rand = (int)(Math.random()*(roomCards.size()));
			solution.room = roomCards.get(rand);
			roomCards.remove(rand);
			rand = (int)(Math.random()*(weaponCards.size()));
			solution.weapon = weaponCards.get(rand);
			weaponCards.remove(rand);
			deck.addAll(playerCards);
			deck.addAll(roomCards);
			deck.addAll(weaponCards);
		}
	}
	//Deals out all the deck withhout the solution to 
	private void dealOutDeck(ArrayList<Card> deck, int weapons) {
		Collections.shuffle(deck);
		int player = 0;
		for (Card card: deck) {
			players.get(player).updateHand(card);
			player++;
			if (player == players.size()) {
				player = 0;
			}
		}
	}
	//Loads the file that stores the board
	public void loadLayoutConfig() throws BadConfigFormatException {
		File layout = new File(layoutConfigFile);
		try {
			Scanner myReader = new Scanner(layout);
			ArrayList<String> lines = new ArrayList<String>();
			while (myReader.hasNextLine()) {
		        lines.add(myReader.nextLine());
		    }
			myReader.close();
			numRows = lines.size();
			String[] theLine = lines.get(0).split(",");
			numColumns = theLine.length;
			grid = new BoardCell[numRows][numColumns];
			setupGrid(lines, theLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
	}
	private void setupGrid(ArrayList<String> lines, String[] theLine) throws BadConfigFormatException {
		// iterate through all characters in our array list of lines
		for (int row = 0; row < numRows; row++) {
			if (theLine == null) {
				theLine = lines.get(row).split(",");
			}
			if(theLine.length != numColumns) {
				throw new BadConfigFormatException("Board layout file does not have the same number of columns in every row.");
			}
			for (int col = 0; col < numColumns; col++) {
				char initial = theLine[col].charAt(0);
				if(!roomMap.containsKey(initial)) { //checks that the room is valid
					throw new BadConfigFormatException("Board layout refers to room that is not in the setup file.");
				}
				DoorDirection doorDirection = DoorDirection.NONE;
				boolean roomLabel = false;
				boolean roomCenter = false;
				char secretPassage = ' ';
				if (theLine[col].length() > 1) {
					if(theLine[col].length() > 2) {
						throw new BadConfigFormatException("Too many characters in Board Cell from layout file");
					}
					switch(theLine[col].charAt(1)) { // handles when a cell has some sort of modifier after the initial
					case '#':
						roomLabel = true;
						break;
					case '*':
						roomCenter = true;
						break;
					case '<':
						doorDirection = DoorDirection.LEFT;
						break;
					case '^':
						doorDirection = DoorDirection.UP;
						break;
					case '>':
						doorDirection = DoorDirection.RIGHT;
						break;
					case 'v':
						doorDirection = DoorDirection.DOWN;
						break;
					default:
						secretPassage = theLine[col].charAt(1); // by default we check for a secret passage
						roomMap.get(initial).setSecretPassage(roomMap.get(secretPassage));
						if(!roomMap.containsKey(secretPassage)) { // if no secret passage and initial is greater than one character, throw an error
							throw new BadConfigFormatException("Board layout refers to room that is not in the setup file.");
						}
						break;
					}
				}
				else if(theLine[col].length() != 1) { // initial is empty, throw an error
					throw new BadConfigFormatException("Empty cell in layout file.");
				}
				grid[row][col] = new BoardCell(row, col, initial, doorDirection, roomLabel, roomCenter, secretPassage, roomMap.get(initial).getName()); // create cell and set grid
				if (roomLabel) { // if we read in '#' we have to update the room label
					roomMap.get(initial).setLabelCell(grid[row][col]);
				}
				else if (roomCenter) { // if we read in '*' we have to update the room center
					roomMap.get(initial).setCenterCell(grid[row][col]);
				}
			}
			theLine = null;
		}
	}
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.setupConfigFile = "data/" + setupConfigFile;
		this.layoutConfigFile = "data/" + layoutConfigFile;
	}
	public Room getRoom(char c) {
		return roomMap.get(c);
	}
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}
	public void calcTargets(BoardCell start, int pathlength) {
		targets.clear();
		visited.add(start);
		findAllTargets(start, pathlength);
		if (getCurrentPlayer().isMovedSuggestion()) {
			targets.add(start);
			getCurrentPlayer().setMovedSuggestion(false);
		}
		visited.remove(start);
		return;
	}
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for(BoardCell adjCell : thisCell.getAdjList()) {
			if(visited.contains(adjCell) || (adjCell.getOccupied() && !adjCell.isRoomCenter())) { //continues if cell has been visited or cannot be moved through
				continue;
			}
			visited.add(adjCell);
			if(numSteps==1 || (adjCell.getInitial()!='W' && adjCell.getInitial()!='X')) { //Stops the movement if the number of steps have run out or if the cell is a room
				targets.add(adjCell);
			} else {
				findAllTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}
	}
	private void setAllAdjacencies() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				setAdjacencyList(grid[row][col]);
			}
		}
	}
	// look at nearby available cells and determine how to handle them and when to add them to adjacency lists.
	private void setAdjacencyList(BoardCell theCell) {
		// find the type of cell we are on.
		char initial = theCell.getInitial();
		if (initial == 'W') { // if we are on a walkway
			for (int row = -1; row <= 1; row += 2) { // look at cells directly above and below current
				if (theCell.getRows() + row > -1 && theCell.getRows() + row < numRows && !grid[theCell.getRows() + row][theCell.getColumns()].getOccupied() && grid[theCell.getRows() + row][theCell.getColumns()].getInitial() == 'W') {
					theCell.addAdjacency(getCell(theCell.getRows() + row, theCell.getColumns())); // add adjacencies of nearby cells
				}
			}
			for (int col = -1; col <= 1; col += 2) { // look at cells directly to the right and the left of current
				if (theCell.getColumns() + col > -1 && theCell.getColumns() + col < numColumns && !grid[theCell.getRows()][theCell.getColumns() + col].getOccupied() && grid[theCell.getRows()][theCell.getColumns() + col].getInitial() == 'W') {
					theCell.addAdjacency(getCell(theCell.getRows(), theCell.getColumns() + col)); // add adjacencies of nearby cells
				}
			}
			if (theCell.getDoorDirection() != DoorDirection.NONE) { // handle case that our cell is a doorway
				char theRoom;
				switch (theCell.getDoorDirection()) { // determine what room the door goes into
				case UP:
					theRoom = grid[theCell.getRows() - 1][theCell.getColumns()].getInitial();
					break;
				case LEFT:
					theRoom = grid[theCell.getRows()][theCell.getColumns() - 1].getInitial();
					break;
				case RIGHT:
					theRoom = grid[theCell.getRows()][theCell.getColumns() + 1].getInitial();
					break;
				default:
					theRoom = grid[theCell.getRows() + 1][theCell.getColumns()].getInitial();
					break;
				}
				theCell.addAdjacency(roomMap.get(theRoom).getCenterCell()); // add adjacency to the room.
				roomMap.get(theRoom).getCenterCell().addAdjacency(theCell);
			}
		}
		else if (initial != 'X' && roomMap.get(initial).getCenterCell().equals(theCell) && roomMap.get(initial).getSecretPassage() != null) { // handle case if inside the room
			theCell.addAdjacency(roomMap.get(initial).getSecretPassage().getCenterCell()); // if secret passage available it should be accessible from room center
		}
	}
	public boolean checkAccusation(Solution accusation) {
		return solution.equals(accusation);
	}
	public Object[] handleSuggestion (Solution suggestion, Player accuser) {
		BoardCell room = null;
		for (Room aRoom : rooms) {
			if (aRoom.getName() == suggestion.room.getName()) {
				room = aRoom.getCenterCell();
			}
		}
		Card disputeCard = null;
		Player disputePlayer = null;
		for (Player player : players) {
			if (player.getName() == suggestion.player.getName()) {
				grid[player.getRow()][player.getCol()].setOccupied(false);
				grid[room.getRows()][room.getColumns()].setOccupied(true);
				player.move(room.getRows(), room.getColumns());
				player.setMovedSuggestion(true);
				repaint();
			}
			if (disputeCard == null) {
				disputeCard = player.disproveSuggestion(suggestion);
				disputePlayer = player;
				if (player.equals(accuser)) {
					disputeCard = null;
					disputePlayer = null;
				}
			}
		}
		Object[] dispute = new Object[2];
		dispute[0] = disputeCard;
		dispute[1] = disputePlayer;
		return dispute;
	}
	
	//Deals with painting the board, with the cells, players, and rooms
	public void paintComponent(Graphics g) {
		Set<Character> roomChars = new HashSet<Character>();
		if (players.get(currentPlayer) instanceof HumanPlayer) {
			for (BoardCell cell : targets) {
				roomChars.add(cell.getInitial());
			}
		}
		super.paintComponent(g);
		if (getWidth() / numColumns < getHeight() / numRows) { //calculates the proper board cell size
			rectSize = getWidth() / numColumns;
		}
		else {
			rectSize = getHeight() / numRows;
		}
		for (int row = 0; row < numRows; row++) { //draws the cells
			for (int col = 0; col < numColumns; col++) {
				if (grid[row][col].getInitial() != 'W') {
					if (roomChars.contains(grid[row][col].getInitial())) {
						grid[row][col].draw(rectSize, g, Color.yellow);
					}
					else {
						grid[row][col].draw(rectSize, g, Color.gray);
					}
				}
				else {
					grid[row][col].draw(rectSize, g, Color.red);
				}
			}
		}
		for (Room room : rooms) { //draws the room names
			if (room.getLabelCell() != null) {
				room.draw(rectSize, g);
			}
		}
		Map<BoardCell, Integer> offsetMap = new HashMap<BoardCell, Integer>(); //keeps track of where players are in order to offset players on the same space
		for (Player player : players) { //draws the players
			if (!offsetMap.containsKey(grid[player.getRow()][player.getCol()])) {
				offsetMap.put(grid[player.getRow()][player.getCol()], 0);
			}
			else {
				offsetMap.put(grid[player.getRow()][player.getCol()], offsetMap.get(grid[player.getRow()][player.getCol()]) + rectSize/2);
			}
			player.draw(rectSize, g, offsetMap.get(grid[player.getRow()][player.getCol()]));
		}
		if (players.get(currentPlayer) instanceof HumanPlayer) {
			for (BoardCell cell : targets) {
				cell.draw(rectSize, g, Color.yellow);
			}
		}
	}
	
	public void nextPlayer (int roll) {
		roll=6;
		if (targets.isEmpty()) { //If targets isn't empty, the player hasn't moved
			currentPlayer = (currentPlayer + 1)%players.size();
			calcTargets(grid[players.get(currentPlayer).getRow()][players.get(currentPlayer).getCol()], roll);
			repaint();
			if (players.get(currentPlayer) instanceof ComputerPlayer) { //does the necessary function calls to move the computer player
				BoardCell target = ((ComputerPlayer)players.get(currentPlayer)).selectTargets(targets);
				grid[players.get(currentPlayer).getRow()][players.get(currentPlayer).getCol()].setOccupied(false);
				target.setOccupied(true);
				players.get(currentPlayer).move(target.getRows(), target.getColumns());
				targets.clear();
				repaint();
				if (target.getInitial() != 'W') {
					computerSuggestion();
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Error: player hasn't moved.");
		}
	}
	private void computerSuggestion() {
		BoardCell loc = grid[getCurrentPlayer().getRow()][getCurrentPlayer().getCol()];
		Room theRoom = roomMap.get(loc.getInitial());
		Solution suggestion = ((ComputerPlayer)getCurrentPlayer()).createSuggestion(theRoom);
		frame.setGuess(suggestion);
		Card dispute = (Card)handleSuggestion(suggestion, getCurrentPlayer())[0];
		if (dispute == null) {
			frame.setGuessResult("No one else can disprove");
		}
		else {
			getCurrentPlayer().updateSeen(dispute);
			frame.setGuessResult("Someone else can disprove");
		}
		frame.updateControl();
	}
	private void humanSuggestion(Card player, Card weapon) {
		Solution suggestion = new Solution();
		suggestion.player = player;
		suggestion.weapon = weapon;
		BoardCell roomCell = grid[getCurrentPlayer().getRow()][getCurrentPlayer().getCol()];
		Room currRoom = roomMap.get(roomCell.getInitial());
		for (Card room : Player.getRoomCards()) {
			if(room.getName().equals(currRoom.getName())) {
				suggestion.room = room;
				break;
			}
		}
		frame.setGuess(suggestion);
		Object[] dispute = handleSuggestion(suggestion, getCurrentPlayer());
		if (dispute[0] == null) {
			frame.setGuessResult("No one else can disprove");
		}
		else {
			getCurrentPlayer().updateSeen((Card)dispute[0]);
			frame.updateSeen(getCurrentPlayer());
			frame.setGuessResult(((Player)dispute[1]).getName() + " disputed with " + ((Card)dispute[0]).getName());
		}
		frame.updateControl();
	}
	public void accusationButton() {
		accusation = true;
		SuggestionDialog dialog = new SuggestionDialog();
		dialog.setVisible(true);
	}
	private void humanAccusation(Card player, Card weapon, Card room) {
		Solution accusation = new Solution();
		accusation.player = player;
		accusation.weapon = weapon;
		accusation.room = room;
		if (checkAccusation(accusation)) {
			JOptionPane.showMessageDialog(this, "Congrats, you won!");
		}
		else {
			JOptionPane.showMessageDialog(this, "Unfortunately you lost :(");
			frame.end();
		}
		
	}
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public Solution getSolution() {
		return solution;
	}
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}
	public Room getPlayerRoom(Player player) {
		BoardCell loc = grid[player.getRow()][player.getCol()];
		return roomMap.get(loc.getInitial());
	}
	public void notATarget() {
		JOptionPane.showMessageDialog(this, "Error: Not a target.");
	}
	public void setFrame(ClueGame frame) {
		this.frame = frame;
	}
	private class SuggestionDialog extends JDialog {
		private JComboBox<String> personSelect;
		private JComboBox<String> weaponSelect;
		private JComboBox<String> roomSelect;
		private JButton submit;
		private JButton cancel;
		SuggestionDialog() {
			
			//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel dialog = new JPanel();
			setSize(200, 300);
			JPanel room = new JPanel(new BorderLayout());
			JPanel person = new JPanel(new BorderLayout());
			JPanel weapon = new JPanel(new BorderLayout());
			JPanel bottom = new JPanel(new BorderLayout());
			
			room.add(new JLabel("Current room"), BorderLayout.WEST);
			if (!accusation) {
				JTextField currRoom = new JTextField(getPlayerRoom(getCurrentPlayer()).getName());
				currRoom.setEditable(false);
				room.add(currRoom, BorderLayout.EAST);
			}
			else {
				String[] roomNames = new String[Player.getRoomCards().size()];
				for (int i = 0; i < Player.getRoomCards().size(); i++) {
					roomNames[i] = Player.getRoomCards().get(i).getName();
				}
				roomSelect = new JComboBox<String>(roomNames);
				room.add(roomSelect, BorderLayout.EAST);
			}
			dialog.add(room);
			
			person.add(new JLabel("Person"), BorderLayout.WEST);
			String[] personNames = new String[Player.getPlayerCards().size()];
			for (int i = 0; i < Player.getPlayerCards().size(); i++) {
				personNames[i] = Player.getPlayerCards().get(i).getName();
			}
			personSelect = new JComboBox<String>(personNames);
			person.add(personSelect, BorderLayout.EAST);
			dialog.add(person);
			
			
			weapon.add(new JLabel("Weapon"), BorderLayout.WEST);
			String[] weaponNames = new String[Player.getWeaponCards().size()];
			for (int i = 0; i < Player.getWeaponCards().size(); i++) {
				weaponNames[i] = Player.getWeaponCards().get(i).getName();
			}
			weaponSelect = new JComboBox<String>(weaponNames);
			weaponSelect.setAlignmentX(RIGHT_ALIGNMENT);
			weapon.add(weaponSelect, BorderLayout.EAST);
			dialog.add(weapon);
			
			submit = new JButton("Submit");
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					if (!accusation) {
						humanSuggestion(Player.getPlayerCards().get(personSelect.getSelectedIndex()), Player.getWeaponCards().get(weaponSelect.getSelectedIndex()));
					}
					else {
						humanAccusation(Player.getPlayerCards().get(personSelect.getSelectedIndex()), Player.getWeaponCards().get(weaponSelect.getSelectedIndex()), Player.getRoomCards().get(roomSelect.getSelectedIndex()));
					}
				}
			});
			cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			bottom.add(submit, BorderLayout.WEST);
			bottom.add(cancel, BorderLayout.EAST);
			dialog.add(bottom);
			add(dialog);
		}
		
	}
	private class ClickListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (players.get(currentPlayer) instanceof HumanPlayer) {
				int x = e.getPoint().x;
				int y = e.getPoint().y;
				int col = x/rectSize; //calculates the column that the player clicked on
				int row = y/rectSize; //calculates the row that the player clicked on
				if (col < numColumns && row < numRows) { //checks that the player clicked within the board
					if (grid[row][col].getInitial() != 'W') { //checks if they clicked on a walkway or a room
						if (targets.contains(roomMap.get(grid[row][col].getInitial()).getCenterCell())) { //checks if the clicked room is a target
							row = roomMap.get(grid[row][col].getInitial()).getCenterCell().getRows();
							col = roomMap.get(grid[row][col].getInitial()).getCenterCell().getColumns();
							targets.clear();
							grid[players.get(currentPlayer).getRow()][players.get(currentPlayer).getCol()].setOccupied(false);
							grid[row][col].setOccupied(true);
							players.get(currentPlayer).move(row, col);
							repaint();
							SuggestionDialog dialog = new SuggestionDialog();
							dialog.setVisible(true);
						}
						else {
							notATarget();
						}
					}
					else if (x > col*rectSize + rectSize/10 && x < col*rectSize + 9*rectSize/10) { //checks if not in one of the left or right borders
						if (y > row*rectSize + rectSize/10 && y < row*rectSize + 9*rectSize/10) { //checks if not in one of the top or down borders
							if (targets.contains(grid[row][col])) { //checks if the cell is a target
								targets.clear();
								grid[players.get(currentPlayer).getRow()][players.get(currentPlayer).getCol()].setOccupied(false);
								grid[row][col].setOccupied(true);
								players.get(currentPlayer).move(row, col);
								repaint();
							}
							else {
								notATarget();
							}
						}
					}
				}
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	};
}
