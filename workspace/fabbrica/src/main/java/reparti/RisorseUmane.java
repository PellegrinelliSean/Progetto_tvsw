package reparti;

public class RisorseUmane {
	/*@ spec_public @*/ int[] valutazioni;
	
	//@ public invariant valutazioni != null && valutazioni.length > 0;
	/*@ public invariant (\forall int j; 0 <= j && j < valutazioni.length; 
								valutazioni[j] >= 0 && valutazioni[j] <= 100); @*/
	
	//@ requires numDipendenti > 0;
	//@ ensures (\forall int j; 0 <= j && j < valutazioni.length; valutazioni[j] == 50);
	public RisorseUmane(int numDipendenti) {
		valutazioni = new int[numDipendenti];
		//@ loop_invariant i >= 0 && (\forall int j; 0 <= j && j < i; valutazioni[j] == 50);
		for (int i = 0; i < valutazioni.length; i++) {
			valutazioni[i] = 50;
		}
	}
	
	//@ requires i >= 0 && i < valutazioni.length;
	//@ requires giorniLavoro >= 0 && giorniLavoro <= 365;
	//@ requires oreStraordinari >= 0 && oreStraordinari <= 730;
	/*@ ensures (valutazioni[i] == (\old(valutazioni[i]) - 20)) <== 
		((sanzione || (giorniLavoro < 200 && oreStraordinari < 10) && 
		((\old(valutazioni[i] - 20) >= 0)) && 
		((\old(valutazioni[i] - 20) <= 100)))); @*/
	/*@ ensures (valutazioni[i] == (\old(valutazioni[i]) + 20)) <== 
		((giorniLavoro > 240 || oreStraordinari > 50) && 
		((\old(valutazioni[i] - 20) >= 0)) && 
		((\old(valutazioni[i] - 20) <= 100))); @*/
	/*@ ensures (valutazioni[i] == (\old(valutazioni[i]))) <== 
	 	(!(sanzione || (giorniLavoro < 200 && oreStraordinari < 10)) &&
		(!(giorniLavoro > 240 || oreStraordinari > 50))); @*/
	//@ ensures (valutazioni[i] == 100) <== ((\old(valutazioni[i]) + 20) >= 100);
	//@ ensures (valutazioni[i] == 0) <== ((\old(valutazioni[i]) - 20) <= 0);
	public void valuta(int i, int giorniLavoro, int oreStraordinari, boolean sanzione) {
		if (i < 0 || i >= valutazioni.length) return;
		// valutazione negativa
		if (sanzione || (giorniLavoro < 200 && oreStraordinari < 10)) valutazioni[i] -= 20;
		// valutazione positiva
		else if (giorniLavoro > 240 || oreStraordinari > 50) valutazioni[i] += 20;
		if (valutazioni[i] > 100) valutazioni[i] = 100;
		if (valutazioni[i] < 0) valutazioni[i] = 0;
	}
	
	//@ ensures (\forall int j; 0 <= j && j < valutazioni.length; \result <= valutazioni[j]);
	//@ ensures (\exists int j; 0 <= j && j < valutazioni.length; \result == valutazioni[j]);
	public int minVal() {
		int min = valutazioni[0];
		/*@ loop_invariant i >= 0 && i <= valutazioni.length &&
	 	(\forall int j; 0 <= j && j < i; min <= valutazioni[j]) &&
	 	(\exists int j; 0 <= j && j < i; min == valutazioni[j]); @*/
		for (int i = 1; i < valutazioni.length; i++) {
			if (valutazioni[i] < min)
				min = valutazioni[i];
		}
		return min;
	}
	
	//@ ensures (\forall int j; 0 <= j && j < valutazioni.length; \result >= valutazioni[j]);
	//@ ensures (\exists int j; 0 <= j && j < valutazioni.length; \result == valutazioni[j]);
	public int maxVal() {
		int max = valutazioni[0];
		/*@ loop_invariant i >= 0 && i <= valutazioni.length &&
		 	(\forall int j; 0 <= j && j < i; max >= valutazioni[j]) &&
		 	(\exists int j; 0 <= j && j < i; max == valutazioni[j]); @*/
		for(int i = 1; i < valutazioni.length; i++) {
			if (valutazioni[i] > max)
				max = valutazioni[i];
		}
		return max;
	}
}
