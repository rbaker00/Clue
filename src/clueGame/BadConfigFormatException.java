package clueGame;

public class BadConfigFormatException extends RuntimeException {
	public BadConfigFormatException() {
		super("Format error in one of the configuration files.");
	}
	public BadConfigFormatException(String msg) {
		super(msg);
	}
}
