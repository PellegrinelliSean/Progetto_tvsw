asm produzione

import StandardLibrary
import CTLlibrary
import LTLlibrary

signature:
	// DOMAINS
	// Il reparto di produzione è composto da tre linee che lavorano in parallelo
	domain Linee subsetof Integer
	// La fase di lucidatura per essere completata necessita di due passi della asm
	domain Lucidatura subsetof Integer
	// Le varie fasi in cui ciascuna linea può trovarsi, solo una linea può trovarsi nello stato di lucidatura in un dato momento
	enum domain Fasi = {ATTESA_POSIZIONAMENTO | RIFINITURA | COLORAZIONE | PREPARAZIONE_LUCIDATURA | LUCIDATURA | ATTESA_PRELIEVO}
	// Le azioni per interagire con le linee, se una linea non è pronta per posizionamento o prelievo il comando viene ignorato
	enum domain Azioni = {NULLA | POSIZIONA | PRELEVA}
	
	// FUNCTIONS
	// indica la fase di ciascuna linea
	controlled fase_linea: Linee -> Fasi
	// se diverso da 0 indica il tempo nella fase di lucidatura trascorso
	controlled fase_lucidatura: Linee -> Lucidatura
	// per scegliere l'azione
	monitored azione: Azioni
	// per scegliere su che linea performare l'azione
	monitored scelta_linea: Linee
	// due funzioni derivate utili per sapere se una certa linea può avviare la lucidatura
	derived prontoLucidatura1: Boolean
	derived prontoLucidatura2: Linee -> Boolean
	
definitions:
	// DOMAIN DEFINITIONS
	domain Linee = {1:3}
	domain Lucidatura = {0:2}

	// FUNCTION DEFINITIONS
	function prontoLucidatura1 = (forall $a in Linee with (fase_linea($a) != LUCIDATURA or fase_lucidatura($a) != 1))
	function prontoLucidatura2($l in Linee) = (forall $b in Linee with (fase_linea($b) != PREPARAZIONE_LUCIDATURA or $b >= $l))
	
	// RULE DEFINITIONS
	// una linea in PREPARAZIONE_LUCIDATURA va in LUCIDATURA solo se nessun'altra linea nello stato successivo sarà
	// in lucidatura
	rule r_prova_lucidatura($l in Linee) =  if (not prontoLucidatura1 or not prontoLucidatura2($l)) then
												skip
											else
												par
											 	fase_lucidatura($l) := 1
											 	fase_linea($l) := LUCIDATURA
											 	endpar
											endif
	
	// una linea in LUCIDATURA ci rimane per due stati della asm consecutivi
	rule r_lucida($l in Linee) = if (fase_lucidatura($l) = 1) then
								 	fase_lucidatura($l) := 2
								 else
								 	par
								 	fase_lucidatura($l) := 0
								 	fase_linea($l) := ATTESA_PRELIEVO
								 	endpar
								 endif
	
	// si fa avanzare la linea verso la fase successiva (se necessario), gestendo attentamente le fasi di PREPARAZIONE_LUCIDATURA
	// e LUCIDATURA
	rule r_linea($l in Linee) = if (fase_linea($l) = ATTESA_POSIZIONAMENTO or fase_linea($l) = ATTESA_PRELIEVO) then skip 
								else if (fase_linea($l) = RIFINITURA) then fase_linea($l) := COLORAZIONE
								else if (fase_linea($l) = COLORAZIONE) then fase_linea($l) := PREPARAZIONE_LUCIDATURA
								else if (fase_linea($l) = PREPARAZIONE_LUCIDATURA) then r_prova_lucidatura[$l]
								else if (fase_linea($l) = LUCIDATURA) then r_lucida[$l]
								endif endif endif endif endif

	// INVARIANTS
	// non è mai possibile avere due linee nella fase di LUCIDATURA
	invariant inv_lucidatura over fase_linea: 
		not(exist $l1 in Linee, $l2 in Linee with ($l1 != $l2 and fase_linea($l1) = LUCIDATURA and fase_linea($l2) = LUCIDATURA))
		
	// PROPERTIES
	// se non posiziono nulla su una linea in ATTESA_POSIZIONAMENTO allora rimarrò in questo stato
	CTLSPEC ag((forall $l in Linee with
		(fase_linea($l) = ATTESA_POSIZIONAMENTO and (scelta_linea != $l or azione != POSIZIONA)) implies ax(fase_linea($l) = ATTESA_POSIZIONAMENTO)))
	// se non prelevo nulla su una linea in ATTESA_PRELIEVO allora rimarrò in questo stato
	CTLSPEC ag((forall $l in Linee with
		(fase_linea($l) = ATTESA_PRELIEVO and (scelta_linea != $l or azione != PRELEVA)) implies ax(fase_linea($l) = ATTESA_PRELIEVO)))
	// è sempre possibile uscire dalla fase PREPARAZIONE_LUCIDATURA
	CTLSPEC ag((forall $l in Linee with
		(fase_linea($l) = PREPARAZIONE_LUCIDATURA implies ef(fase_linea($l) = LUCIDATURA))))
	// se una linea è in LUCIDATURA allora rimane tale fino a che fase_lucidatura = 2
	LTLSPEC g((forall $l in Linee with (fase_linea($l) = LUCIDATURA implies u(fase_linea($l) = LUCIDATURA, fase_lucidatura($l) = 2))))
	

	// MAIN RULE
	// se l'azione scelta non può essere eseguita viene ignorata, altrimenti si modifica lo stato della linea selezionata
	// successivamente si aggiorna lo stato di tutte le altre linee
	main rule r_Main =  if (azione = NULLA) then
							forall $a in Linee do r_linea[$a]
						else if ((azione = POSIZIONA and fase_linea(scelta_linea) != ATTESA_POSIZIONAMENTO) 
								or (azione = PRELEVA and fase_linea(scelta_linea) != ATTESA_PRELIEVO)) then
							forall $b in Linee do r_linea[$b]
						else
							par
							 if (azione = POSIZIONA and fase_linea(scelta_linea) = ATTESA_POSIZIONAMENTO) then
							 	fase_linea(scelta_linea) := RIFINITURA
							 else
							 	fase_linea(scelta_linea) := ATTESA_POSIZIONAMENTO
							 endif
							forall $l in Linee with $l != scelta_linea do r_linea[$l]
							endpar
						endif endif
						
// INITIAL STATE
default init s0:
	function fase_linea($l in Linee) = ATTESA_POSIZIONAMENTO
	function fase_lucidatura($l in Linee) = 0
