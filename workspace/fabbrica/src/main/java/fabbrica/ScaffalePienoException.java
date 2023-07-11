package fabbrica;

@SuppressWarnings("serial")
public class ScaffalePienoException extends Exception {
	public ScaffalePienoException() {
		super("Attenzione, non c'è più spazio in questo scaffale!");
	}
}
