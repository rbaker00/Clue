package clueGame;

import java.util.*;

import experiment.TestBoardCell;

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
	    Scanner myReader;
		try {
			myReader = new Scanner(setup);
			roomMap = new HashMap<Character, Room>();
			setupRoomsAndRoomMap(myReader);
		    myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
	private void setupRoomsAndRoomMap(Scanner myReader) throws BadConfigFormatException {
		while (myReader.hasNextLine()) {
			
		    String[] data = myReader.nextLine().split(", ");
		    if(data.length > 3 && !data[0].substring(0, 2).equals("//")) { //thrown if line is not a comment and is too long
		    	myReader.close();
		    	throw new BadConfigFormatException();
		    }
		    if (data[0].equals("Room") || data[0].equals("Space")) { //puts the data of the room in the appropriate data structures
		        rooms.add(new Room(data[1]));
		        roomMap.put(data[2].charAt(0), rooms.get(rooms.size()-1));
		    }
		    else if (!data[0].substring(0, 2).equals("//")){ //thrown if a line does not contain Room or Space and is not a comment
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
		ArrayList<BoardCell> doors = new ArrayList<BoardCell>();
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
					switch(theLine[col].charAt(1)) { //handles when a cell has some sort of modifier after the initial
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
						secretPassage = theLine[col].charAt(1);
						roomMap.get(initial).setSecretPassage(roomMap.get(secretPassage));
						if(!roomMap.containsKey(secretPassage)) {
							throw new BadConfigFormatException("Board layout refers to room that is not in the setup file.");
						}
						break;
					}
				}
				else if(theLine[col].length() != 1) {
					throw new BadConfigFormatException("Empty cell in layout file.");
				}
				grid[row][col] = new BoardCell(row, col, initial, doorDirection, roomLabel, roomCenter, secretPassage);
				if (roomLabel) {
					roomMap.get(initial).setLabelCell(grid[row][col]);
				}
				else if (roomCenter) {
					roomMap.get(initial).setCenterCell(grid[row][col]);
				}
				else if (doorDirection != DoorDirection.NONE) {
					doors.add(grid[row][col]);
				}
			}
			theLine = null;
		}
		for (BoardCell theCell : doors) {
			switch (theCell.getDoorDirection()) {
			case UP:
				roomMap.get(grid[theCell.getRows() - 1][theCell.getColumns()].getInitial()).addDoor(theCell);
				break;
			case LEFT:
				roomMap.get(grid[theCell.getRows()][theCell.getColumns() - 1].getInitial()).addDoor(theCell);
				break;
			case RIGHT:
				roomMap.get(grid[theCell.getRows()][theCell.getColumns() + 1].getInitial()).addDoor(theCell);
				break;
			default:
				roomMap.get(grid[theCell.getRows() + 1][theCell.getColumns()].getInitial()).addDoor(theCell);
				break;
			}
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
			if(visited.contains(adjCell) || (adjCell.getOccupied() && !adjCell.isRoomCenter())) {
				continue;
			}
			visited.add(adjCell);
			if(numSteps==1 || (adjCell.getInitial()!='W' && adjCell.getInitial()!='X')) {
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
	private void setAdjacencyList(BoardCell theCell) {
		if (theCell.getInitial() == 'W') {
			for (int row = -1; row <= 1; row += 2) {
				if (theCell.getRows() + row > -1 && theCell.getRows() + row < numRows && !grid[theCell.getRows() + row][theCell.getColumns()].getOccupied() && grid[theCell.getRows() + row][theCell.getColumns()].getInitial() == 'W') {
					theCell.addAdjacency(getCell(theCell.getRows() + row, theCell.getColumns()));
				}
			}
			for (int col = -1; col <= 1; col += 2) {
				if (theCell.getColumns() + col > -1 && theCell.getColumns() + col < numColumns && !grid[theCell.getRows()][theCell.getColumns() + col].getOccupied() && grid[theCell.getRows()][theCell.getColumns() + col].getInitial() == 'W') {
					theCell.addAdjacency(getCell(theCell.getRows(), theCell.getColumns() + col));
				}
			}
			if (theCell.getDoorDirection() != DoorDirection.NONE) {
				char theRoom;
				switch (theCell.getDoorDirection()) {
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
				theCell.addAdjacency(roomMap.get(theRoom).getCenterCell());
			}
		}
		else if (theCell.getInitial() != 'X' && roomMap.get(theCell.getInitial()).getCenterCell().equals(theCell)) {
			for (BoardCell door : roomMap.get(theCell.getInitial()).getDoors()) {
				theCell.addAdjacency(door);
			}
			if (roomMap.get(theCell.getInitial()).getSecretPassage() != null) {
				theCell.addAdjacency(roomMap.get(theCell.getInitial()).getSecretPassage().getCenterCell());
			}
		}
	}
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
	public Set<BoardCell> getTargets() {
		return targets;
//		return new HashSet<BoardCell>();
	}
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
