package reparti;

@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
	public InvalidInputException() {
		super("Attenzione, l'input scelto non è valido!");
	}
}
