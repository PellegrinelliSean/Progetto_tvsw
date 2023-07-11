package reparti;

public class RisorseUmane {
	double[] valutazioni;
	
	public RisorseUmane(int numDipendenti) {
		valutazioni = new double[numDipendenti];
		for (int i = 0; i < valutazioni.length; i++) {
			valutazioni[i] = 50;
		}
	}
	
	public void valuta(int i, int giorniLavoro, int oreStraordinari, boolean sanzione) {
		// valutazione negativa
		if (sanzione || (giorniLavoro < 200 && oreStraordinari < 10)) valutazioni[i] -= 20;
		// valutazione positiva
		else if (giorniLavoro > 240 || oreStraordinari > 50) valutazioni[i] += 20;
		if (valutazioni[i] > 100) valutazioni[i] = 100;
		if (valutazioni[i] < 0) valutazioni[i] = 0;
	}
	
	public double minVal() {
		double min = 101;
		for (int i = 0; i < valutazioni.length; i++) {
			if (valutazioni[i] < min)
				min = valutazioni[i];
		}
		return min;
	}
	
	public double maxVal() {
		double max = -1;
		for(int i = 0; i < valutazioni.length; i++) {
			if (valutazioni[i] > max)
				max = valutazioni[i];
		}
		return max;
	}
}
