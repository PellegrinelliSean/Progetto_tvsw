package reparti;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestPairWiseRisorseUmane {
	// parametri
	int i; 
	int giorniLavoro;
	int oreStraordinari;
	boolean sanzione;
	// output atteso
	int[] oracolo;
	// istanza della classe da testare
	RisorseUmane rs;
	
	public TestPairWiseRisorseUmane(int i, int giorniLavoro, int oreStraordinari, boolean sanzione, int[] oracolo) {
		this.rs = new RisorseUmane(3);
		this.i = i;
		this.giorniLavoro = giorniLavoro;
		this.oreStraordinari = oreStraordinari;
		this.sanzione = sanzione;
		this.oracolo = oracolo;
	}
	
	@Parameters
	public static Collection<Object[]> creaParametri() {
		return Arrays.asList(new Object[][] { 
			{ -1, -1, -1, true, new int[]{50, 50, 50} }, 
			{ 1, -1, 5, false, new int[]{50, 30, 50} }, 
			{ 10, -1, 25, true, new int[]{50, 50, 50} }, 
			{ -1, -1, 100, false, new int[]{50, 50, 50} }, 
			{ 1, 100, -1, false, new int[]{50, 30, 50} }, 
			{ 10, 100, 5, true, new int[]{50, 50, 50} }, 
			{ -1, 100, 25, false, new int[]{50, 50, 50} }, 
			{ -1, 100, 100, true, new int[]{50, 50, 50} }, 
			{ 10, 220, -1, true, new int[]{50, 50, 50} }, 
			{ -1, 220, 5, false, new int[]{50, 50, 50} }, 
			{ 1, 220, 25, true, new int[]{50, 30, 50} }, 
			{ -1, 220, 100, false, new int[]{50, 50, 50} }, 
			{ -1, 300, -1, false, new int[]{50, 50, 50} }, 
			{ 1, 300, 5, true, new int[]{50, 30, 50} }, 
			{ 10, 300, 25, false, new int[]{50, 50, 50} }, 
			{ -1, 300, 100, true, new int[]{50, 50, 50} },
		});
	}
	
	@Test
	public void testValutaPW() {
		rs.valuta(i, giorniLavoro, oreStraordinari, sanzione);
		assertArrayEquals(oracolo, rs.valutazioni);
	}
}
