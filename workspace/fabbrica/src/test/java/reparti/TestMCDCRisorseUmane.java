package reparti;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMCDCRisorseUmane {
	@Test
	public void testValutaMCDC() {
		RisorseUmane rs = new RisorseUmane(5);
		
		int[] oracolo = new int[] {50, 50, 50, 50, 50};
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella A Riga 2
		rs.valuta(-1, 230, 18, false);
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella A Riga 3
		rs.valuta(100, 230, 18, false);
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella A Riga 1; tabella B riga 2; tabella D riga 1; tabella E riga 1
		rs.valuta(2, 230, 18, true);
		oracolo[2] = 30;
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella B riga 3
		rs.valuta(2, 100, 5, false);
		oracolo[2] = 10;
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella E riga 2
		rs.valuta(2, 100, 5, false);
		oracolo[2] = 0;
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella B riga 1; tabella C riga 1
		rs.valuta(3, 230, 18, false);
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella B riga 4; tabella C riga 3
		rs.valuta(3, 190, 60, false);
		oracolo[3] = 70;
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella C riga 2
		rs.valuta(3, 250, 18, false);
		oracolo[3] = 90;
		assertArrayEquals(oracolo, rs.valutazioni);
		
		// tabella D riga 2
		rs.valuta(3, 250, 18, false);
		oracolo[3] = 100;
		assertArrayEquals(oracolo, rs.valutazioni);
	}
	
	@Test
	public void testMinMCDC() {
		RisorseUmane rs = new RisorseUmane(5);
		int oracolo = 30;
		
		rs.valuta(2, 230, 18, true);
		assertEquals(oracolo, rs.minVal());
	}
	
	@Test
	public void testMaxMCDC() {
		RisorseUmane rs = new RisorseUmane(5);
		int oracolo = 70;
		
		rs.valuta(3, 190, 60, false);
		assertEquals(oracolo, rs.maxVal());
	}
}
