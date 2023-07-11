package reparti;

import fabbrica.ScaffalePienoException;

public class Magazzino {
	int scaffale_prod_finiti;
	int scaffale_semilavorati;
	int dim_scaffali;

	public Magazzino(int dim) {
		dim_scaffali = dim;
		scaffale_prod_finiti = 0;
		scaffale_semilavorati = 0;
	}

	public void compra() throws ScaffalePienoException {
		if (scaffale_semilavorati >= dim_scaffali)
			throw new ScaffalePienoException();
		else
			scaffale_semilavorati++;

	}

	public void riposiziona() throws ScaffalePienoException {
		if (scaffale_prod_finiti >= dim_scaffali)
			throw new ScaffalePienoException();
		else
			scaffale_prod_finiti++;
	}
}
