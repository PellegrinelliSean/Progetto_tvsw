asm magazzino

import StandardLibrary

signature:
	// DOMAINS
	domain RiempimentoScaffali subsetof Integer
	
	// FUNCTIONS
	controlled scaffale_prod_finiti: RiempimentoScaffali
	controlled scaffale_semilavorati: RiempimentoScaffali
	// true per prod_finiti, false per semilavorati
	monitored scelta_scaffale: Boolean
	// true per inserimento, false per prelievo
	monitored azione: Boolean
	
definitions:
	// DOMAIN DEFINITIONS
	domain RiempimentoScaffali = {0:10}

	// MAIN RULE
	main rule r_Main = if (scelta_scaffale) then
							if(azione) then
								if(scaffale_prod_finiti >= 10) then
									skip
								else
									scaffale_prod_finiti := scaffale_prod_finiti + 1
								endif
							else
								if(scaffale_prod_finiti <= 0) then
									skip
								else
									scaffale_prod_finiti := scaffale_prod_finiti - 1
								endif
							endif
						else
							if(azione) then
								if(scaffale_semilavorati >= 10) then
									skip
								else
									scaffale_semilavorati := scaffale_semilavorati + 1
								endif
							else
								if(scaffale_semilavorati <= 0) then
									skip
								else
									scaffale_semilavorati := scaffale_semilavorati - 1
								endif
							endif
						endif
	
// INITIAL STATE
default init s0:
	function scaffale_prod_finiti = 0
	function scaffale_semilavorati = 0