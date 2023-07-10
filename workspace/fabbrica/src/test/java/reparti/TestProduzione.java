package reparti;

import static org.junit.Assert.*;

import org.junit.Test;

import fabbrica.InvalidInputException;

public class TestProduzione {

	@Test
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
}
