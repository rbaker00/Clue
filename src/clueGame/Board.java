package clueGame;

import java.util.Map;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
	
	private Board() {
		super();
	}
	public static Board getInstance() {
		return theInstance;
	}
	public int getRows() {
		return numRows;
	}
	public int getColumns() {
		return numColumns;
	}
	public void initialize() {
		
	}
	public void loadConfigFiles() {
		
	}
	public void loadSetupConfig() {
		
	}
	public void loadLayoutConfig() {
		
	}
}
