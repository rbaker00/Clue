package clueGame;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
	private BoardCell[][] grid;
	private int numRows = 0;
	private int numColumns = 0;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
	private ArrayList<Room> rooms;
	private char unused;
	
	private Board() {
		super();
	}
	public static Board getInstance() {
		return theInstance;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public void initialize() {
		loadConfigFiles();
	}
	public void loadConfigFiles() {
		loadSetupConfig();
		loadLayoutConfig();
	}
	public void loadSetupConfig() {
		rooms = new ArrayList<Room>();
		File setup = new File(setupConfigFile);
	    Scanner myReader;
		try {
			myReader = new Scanner(setup);
			roomMap = new HashMap<Character, Room>();
			while (myReader.hasNextLine()) {
		        String[] data = myReader.nextLine().split(", ");
		        if(data.length > 3) {
		        	myReader.close();
		        	throw new BadConfigFormatException();
		        }
		        if ((data[0].equals("Room") || data[0].equals("Space")) && data.length == 3) {
			        rooms.add(new Room(data[1]));
			        roomMap.put(data[2].charAt(0), rooms.get(rooms.size()-1));
		        }
		        else {
		        	myReader.close();
		        	throw new BadConfigFormatException();
		        }
		    }
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
<<<<<<< HEAD
		roomMap = new HashMap<Character, Room>();
		while (myReader.hasNextLine()) {
			
	        String[] data = myReader.nextLine().split(", ");
	        if(data.length > 3) {
	        	throw new BadConfigFormatException();
	        }
	        if (data[0].equals("Room") || data[0].equals("Space")) {
		        rooms.add(new Room(data[1]));
		        roomMap.put(data[2].charAt(0), rooms.get(rooms.size()-1));
	        }
	        else if (!data[0].substring(0, 2).equals("//")){
	        	throw new BadConfigFormatException();
	        }
	    }
	    myReader.close();
=======
>>>>>>> ef85d6aac2c0b7e0eaed6cf1c4747d2935c7ac11
	}
	public void loadLayoutConfig() {
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
			for (int row = 0; row < numRows; row++) {
				if (theLine == null) {
					theLine = lines.get(row).split(",");
				}
				if(theLine.length != numColumns) {
					throw new BadConfigFormatException("Board layout file does not have the same number of columns in every row.");
				}
				for (int col = 0; col < numColumns; col++) {
					char initial = theLine[col].charAt(0);
					if(!roomMap.containsKey(initial)) {
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
						switch(theLine[col].charAt(1)) {
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
				}
				theLine = null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
	}
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.setupConfigFile = "data/" + setupConfigFile;
		this.layoutConfigFile = "data/" + layoutConfigFile;
	}
	public Room getRoom(char c) {
		return roomMap.get(c);
	}
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}
}
