package reparti;

public class Magazzino {
	int scaffale_prod_finiti;
	int scaffale_semilavorati;
	int dim_scaffali;

	public Magazzino(int dim) {
		dim_scaffali = dim;
		scaffale_prod_finiti = 0;
		scaffale_semilavorati = 0;
	}

	public boolean compra() {
		if (scaffale_semilavorati >= dim_scaffali)
			return false;
		else
			scaffale_semilavorati++;
		return true;
	}
	
	public boolean lavora() {
		if (scaffale_semilavorati <= 0)
			return false;
		else
			scaffale_semilavorati--;
		return true;
	}

	public boolean riposiziona() {
		if (scaffale_prod_finiti >= dim_scaffali)
			return false;
		else
			scaffale_prod_finiti++;
		return true;
	}
	
	public boolean vendi() {
		if (scaffale_prod_finiti <= 0)
			return false;
		else
			scaffale_prod_finiti--;
		return true;
	}
}
