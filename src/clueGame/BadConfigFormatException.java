package clueGame;

public class BadConfigFormatException extends Exception {
	private static final long serialVersionUID = 1L;
	public BadConfigFormatException() {
		super("Format error in one of the configuration files.");
	}
	public BadConfigFormatException(String msg) {
		super(msg);
	}
}
