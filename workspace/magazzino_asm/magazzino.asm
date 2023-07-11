asm magazzino

import StandardLibrary

signature:
	// DOMAINS
	domain RiempimentoScaffali subsetof Integer
	
	// FUNCTIONS
	controlled scaffale_prod_finiti: RiempimentoScaffali
	controlled scaffale_semilavorati: RiempimentoScaffali
	// azione true deposito un prodotto finito, azione false deposito un semilavorato
	monitored azione: Boolean
	
definitions:
	// DOMAIN DEFINITIONS
	domain RiempimentoScaffali = {0:10}

	// MAIN RULE
	main rule r_Main =  if (azione) then
							if(scaffale_prod_finiti >= 10) then
								skip
							else
								scaffale_prod_finiti := scaffale_prod_finiti + 1
							endif
						else
							if(scaffale_semilavorati >= 10) then
								skip
							else
								scaffale_semilavorati := scaffale_semilavorati + 1
							endif
						endif
	
// INITIAL STATE
default init s0:
	function scaffale_prod_finiti = 0
	function scaffale_semilavorati = 0