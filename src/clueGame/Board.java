package clueGame;

import java.util.*;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;

//Class to contain a clue board
public class Board {
	private BoardCell[][] grid;
	private int numRows = 0;
	private int numColumns = 0;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
	private ArrayList<Room> rooms;
	Set<BoardCell> targets;
	Set<BoardCell> visited = new HashSet<BoardCell>();
	private ArrayList<Player> players;
	private Solution solution;
	
	private Board() {
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
		players = new ArrayList<Player>();
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
		ArrayList<Card> deck = new ArrayList<Card>();
	    Scanner myReader;
		try {
			myReader = new Scanner(setup);
			roomMap = new HashMap<Character, Room>();
			setupCards(myReader, deck);
		    myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
	private void setupCards(Scanner myReader, ArrayList<Card> deck) throws BadConfigFormatException {
		while (myReader.hasNextLine()) {
		    String[] data = myReader.nextLine().split(", ");
		    if(!(data.length == 2 || data.length == 3 || data.length == 5)  && !data[0].substring(0, 2).equals("//") && !data[0].equals("Weapon")) { //thrown if line is not a comment and is not the correct size
		    	myReader.close();
		    	throw new BadConfigFormatException();
		    }
		    if (data[0].equals("Room") || data[0].equals("Space")) { //puts the data of the room in the appropriate data structures
		        if (data.length != 3) {
		        	myReader.close();
			    	throw new BadConfigFormatException();
		        }
		    	rooms.add(new Room(data[1]));
		        roomMap.put(data[2].charAt(0), rooms.get(rooms.size()-1));
		        if (data[0].equals("Room")) {
		        	deck.add(new Card(data[1], CardType.ROOM));
		        }
		    }
		    else if (data[0].equals("Player")) {
		    	if (data.length != 5) {
		        	myReader.close();
			    	throw new BadConfigFormatException();
		        }
		    	String[] color = data[2].split(" ");
		    	if (color.length != 3) {
		        	myReader.close();
			    	throw new BadConfigFormatException();
		        }
		    	Color theColor = new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[0]));
		    	String[] location = data[3].split(" ");
		    	if (location.length != 2) {
		        	myReader.close();
			    	throw new BadConfigFormatException();
		        }
		    	if (data[4] == "Computer") {
		    		players.add(new ComputerPlayer(data[1], theColor, Integer.parseInt(location[0]), Integer.parseInt(location[1])));
		    	}
		    	else if (data[4] == "Human") {
		    		players.add(new HumanPlayer(data[1], theColor, Integer.parseInt(location[0]), Integer.parseInt(location[1])));
		    	}
		    	else {
		    		myReader.close();
			    	throw new BadConfigFormatException();
		    	}
		    	deck.add(new Card(data[1], CardType.PERSON));
		    }
		    else if (data[0].equals("Weapon")) {
		    	
		    }
		    else if (!data[0].substring(0, 2).equals("//") && !data[0].equals("Weapon") && !data[0].equals("Player")){ //thrown if a line does not contain Room or Space and is not a comment
		    	myReader.close();
		    	throw new BadConfigFormatException();
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
				grid[row][col] = new BoardCell(row, col, initial, doorDirection, roomLabel, roomCenter, secretPassage); // create cell and set grid
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
		targets = new HashSet<BoardCell>();
		visited.add(start);
		findAllTargets(start, pathlength);
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
	public void deal() {
		return;
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
		return new ArrayList<Player>();
	}
	public Solution getSolution() {
		return solution;
	}
}
