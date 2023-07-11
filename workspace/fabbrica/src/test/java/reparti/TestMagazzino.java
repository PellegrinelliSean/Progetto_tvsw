package reparti;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fabbrica.ScaffalePienoException;

// I test sono la traduzione di quelli generati con ATGT
public class TestMagazzino {
	Magazzino m;
	
	@Before
	public void Inizializzazione() {
		m = new Magazzino(10);
	}
		
	@Test
	public void testBR_r_Main_FF() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		try {m.compra();} catch (ScaffalePienoException e) { fail(); }
		assertEquals(1, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
	}

	@Test
	public void testBR_r_Main_FT() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		for(int i = 1; i <= 10; i++) {
			try {m.compra();} catch (ScaffalePienoException e) { fail(); }
			assertEquals(i, m.scaffale_semilavorati);
		}
		
		try {m.compra(); fail();} catch (ScaffalePienoException e) {}
	}
	
	@Test
	public void testBR_r_Main_TF() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		try {m.compra();} catch (ScaffalePienoException e) { fail(); }
		assertEquals(1, m.scaffale_semilavorati);
		
		try {m.riposiziona();} catch (ScaffalePienoException e) { fail(); }
		assertEquals(1, m.scaffale_prod_finiti);
	}
	
	@Test
	public void testBR_r_Main_TT() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		try {m.compra();} catch (ScaffalePienoException e) { fail(); }
		assertEquals(1, m.scaffale_semilavorati);
		
		for(int i = 1; i <= 10; i++) {
			try {m.riposiziona();} catch (ScaffalePienoException e) { fail(); }
			assertEquals(i, m.scaffale_prod_finiti);
		}
		
		try {m.riposiziona(); fail();} catch (ScaffalePienoException e) {}
	}
}
