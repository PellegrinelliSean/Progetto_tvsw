package reparti;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// I test sono la traduzione di quelli generati con ATGT
public class TestMagazzino {
	Magazzino m;
	
	@Before
	public void Inizializzazione() {
		m = new Magazzino(10);
	}
		
	@Test
	public void testBR_r_Main_FFF() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		m.lavora();
		m.compra();
		
		assertEquals(1, m.scaffale_semilavorati);
		
		m.lavora();
		
		assertEquals(0, m.scaffale_semilavorati);
	}
	
	@Test
	public void testBR_r_Main_FFT() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		m.lavora();
		
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
	}
	
	@Test
	public void testBR_r_Main_FTF() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		m.lavora();
		m.compra();
		
		assertEquals(1, m.scaffale_semilavorati);
	}

	@Test
	public void testBR_r_Main_FTT() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		m.lavora();
		
		for(int i = 1; i <= 10; i++) {
			m.compra();
			assertEquals(i, m.scaffale_semilavorati);
		}
		
		m.compra();
		assertEquals(10, m.scaffale_semilavorati);
	}
	
	@Test
	public void testBR_r_Main_TFF() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		m.lavora();
		m.riposiziona();
		
		assertEquals(1, m.scaffale_prod_finiti);
		
		m.vendi();
		
		assertEquals(0, m.scaffale_prod_finiti);
	}
	
	@Test
	public void testBR_r_Main_TFT() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		m.lavora();
		m.vendi();
		
		assertEquals(0, m.scaffale_prod_finiti);
	}
	
	@Test
	public void testBR_r_Main_TTF() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		m.lavora();
		m.riposiziona();
		
		assertEquals(1, m.scaffale_prod_finiti);
	}
	
	@Test
	public void testBR_r_Main_TTT() {
		assertEquals(0, m.scaffale_semilavorati);
		assertEquals(0, m.scaffale_prod_finiti);
		
		m.lavora();
		
		for(int i = 1; i <= 10; i++) {
			m.riposiziona();
			assertEquals(i, m.scaffale_prod_finiti);
		}
		
		m.riposiziona();
		assertEquals(10, m.scaffale_prod_finiti);
	}
}
