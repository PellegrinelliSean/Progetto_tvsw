package reparti;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestProduzione {

	@Test
	// Equivalente dello scenario avalla utilizzo_linea1
	public void utilizzoLinea1() {
		Produzione prod;
		try { 
			prod = new Produzione(3); 
			Fasi[] oracolo = new Fasi[] {Fasi.ATTESA_POSIZIONAMENTO, Fasi.ATTESA_POSIZIONAMENTO, Fasi.ATTESA_POSIZIONAMENTO};
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo[0] = Fasi.RIFINITURA;
			prod.posiziona(0);
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo[0] = Fasi.COLORAZIONE;
			prod.avanza();
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo[0] = Fasi.PREPARAZIONE_LUCIDATURA;
			prod.avanza();
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo[0] = Fasi.LUCIDATURA;
			prod.avanza();
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo[0] = Fasi.LUCIDATURA;
			prod.avanza();
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo[0] = Fasi.ATTESA_PRELIEVO;
			prod.avanza();
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo[0] = Fasi.ATTESA_POSIZIONAMENTO;
			prod.preleva(0);
			assertArrayEquals(oracolo, prod.fase_linea);
		} 
		catch (InvalidInputException e) { fail(); }
	}
	
	@Test
	// Copertura delle istruzioni
	public void produzioneStatement() {
		Produzione prod;
		
		//Tre casi di input errati
		try { prod = new Produzione(0);
			fail(); }
		catch (InvalidInputException e) {}
		
		try { prod = new Produzione(3);
			prod.posiziona(-1);
			fail(); }
		catch (InvalidInputException e) {}
		
		try { prod = new Produzione(3); 
		prod.preleva(-1);
		fail(); } 
		catch (InvalidInputException e) {}
		
		try { 
			prod = new Produzione(3); 
			Fasi[] oracolo = new Fasi[] {Fasi.ATTESA_POSIZIONAMENTO, Fasi.ATTESA_POSIZIONAMENTO, Fasi.ATTESA_POSIZIONAMENTO};
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo = new Fasi[] {Fasi.ATTESA_POSIZIONAMENTO, Fasi.RIFINITURA, Fasi.ATTESA_POSIZIONAMENTO};
			prod.posiziona(1);
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo = new Fasi[] {Fasi.RIFINITURA, Fasi.COLORAZIONE, Fasi.ATTESA_POSIZIONAMENTO};
			prod.posiziona(0);
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo = new Fasi[] {Fasi.COLORAZIONE, Fasi.PREPARAZIONE_LUCIDATURA, Fasi.RIFINITURA};
			prod.posiziona(2);
			assertArrayEquals(oracolo, prod.fase_linea);

			oracolo = new Fasi[] {Fasi.PREPARAZIONE_LUCIDATURA, Fasi.LUCIDATURA, Fasi.COLORAZIONE};
			prod.posiziona(0); // equivalente ad avanza()
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo = new Fasi[] {Fasi.PREPARAZIONE_LUCIDATURA, Fasi.LUCIDATURA, Fasi.PREPARAZIONE_LUCIDATURA};
			prod.preleva(0); // equivalente ad avanza()
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo = new Fasi[] {Fasi.LUCIDATURA, Fasi.ATTESA_PRELIEVO, Fasi.PREPARAZIONE_LUCIDATURA};
			prod.avanza();
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo = new Fasi[] {Fasi.LUCIDATURA, Fasi.ATTESA_POSIZIONAMENTO, Fasi.PREPARAZIONE_LUCIDATURA};
			prod.preleva(1);
			assertArrayEquals(oracolo, prod.fase_linea);
			
			oracolo = new Fasi[] {Fasi.ATTESA_PRELIEVO, Fasi.ATTESA_POSIZIONAMENTO, Fasi.LUCIDATURA};
			prod.avanza();
			assertArrayEquals(oracolo, prod.fase_linea);
		} 
		catch (InvalidInputException e) { fail(); }
	}
}
