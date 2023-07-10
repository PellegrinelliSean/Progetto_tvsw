package reparti;

import fabbrica.InvalidInputException;

enum Fasi {ATTESA_POSIZIONAMENTO, RIFINITURA, COLORAZIONE, PREPARAZIONE_LUCIDATURA, LUCIDATURA, ATTESA_PRELIEVO}

public class Produzione {
	Fasi[] fase_linea;
	int[] fase_lucidatura;
	int n_linee;
	
	// inizializza n linee di produzione in ATTESA_POSIZIONAMENTO
	public Produzione(int n) throws InvalidInputException {
		if(n <= 0) throw new InvalidInputException();
		n_linee = 3;
		fase_linea = new Fasi[n_linee];
		for (int i = 0; i < fase_linea.length; i++)
			fase_linea[i] = Fasi.ATTESA_POSIZIONAMENTO;
		fase_lucidatura = new int[n_linee];
	}
	
	// fa procedere di un passo le linee
	public void avanza() {
		for (int i=0; i < fase_linea.length; i++)
			avanzaLinea(i);
	}
	
	// posiziona un prodotto da lavorare sulla linea l (se possibile) e fa avanzare le linee
	public void posiziona(int l) throws InvalidInputException {
		if(l < 0 || l >= n_linee) throw new InvalidInputException();
		if (fase_linea[l] == Fasi.ATTESA_POSIZIONAMENTO)
			fase_linea[l] = Fasi.RIFINITURA;
		else
			l = -1;
		for (int i = 0; i < fase_linea.length; i++) {
			if (i != l)
				avanzaLinea(i);
		}
	}
	
	// preleva un prodotto lavorato dalla linea l (se possibile) e fa avanzare le linee
	public void preleva(int l) throws InvalidInputException {
		if(l < 0 || l >= n_linee) throw new InvalidInputException();
		if (fase_linea[l] == Fasi.ATTESA_PRELIEVO)
			fase_linea[l] = Fasi.ATTESA_POSIZIONAMENTO;
		else
			l = -1;
		for (int i=0; i < fase_linea.length; i++) {
			if (i != l)
				avanzaLinea(i);
		}
	}
	
	// fa procedere la i-esima linea in base alla fase in cui si trova
	private void avanzaLinea(int i) {
		switch (fase_linea[i]) {
			case RIFINITURA:
				fase_linea[i] = Fasi.COLORAZIONE;
				break;
			case COLORAZIONE:
				fase_linea[i] = Fasi.PREPARAZIONE_LUCIDATURA;
				break;
			case PREPARAZIONE_LUCIDATURA:
				if (lucidaturaDisponibile(i)) {
					fase_linea[i] = Fasi.LUCIDATURA;
					fase_lucidatura[i] = 1;
				}
				break;
			case LUCIDATURA:
				if (fase_lucidatura[i] == 1)
					fase_lucidatura[i] = 2;
				else {
					fase_linea[i] = Fasi.ATTESA_PRELIEVO;
					fase_lucidatura[i] = 0;
				}
				break;
			default:
				break;			
		}
	}
	
	// verifica se la i-esima linea può passare alla LUCIDATURA
	private boolean lucidaturaDisponibile(int i) {
		for (int j = 0; j < fase_linea.length; j++) {
			// un altro è entrato nella seconda parte di LUCIDATURA in questa chiamata di preleva(), posiziona() o avanza()
			if (fase_linea[j] == Fasi.LUCIDATURA && fase_lucidatura[j] == 2 && j < i)
				return false;
			// un altro nella prima fase di LUCIDATURA
			if (fase_linea[j] == Fasi.LUCIDATURA && fase_lucidatura[j] == 1)
				return false;
			// un altro è nella seconda parte di LUCIDATURA e ne uscirà in questa chiamata di preleva(), posiziona() o avanza()
			if (fase_linea[j] == Fasi.LUCIDATURA && fase_lucidatura[j] == 2 && j > i) {
				fase_linea[j] = Fasi.ATTESA_PRELIEVO;
				fase_lucidatura[j] = 0;
				return true;
			}
		}
		// nessun caso particolare, posso andare in LUCIDATURA
		return true; 
	}
}
