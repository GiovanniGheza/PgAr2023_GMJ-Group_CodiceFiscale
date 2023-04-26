package it.unibs.pgar.codicefiscale;

import java.util.ArrayList;

/**
 * la classe CreatoreControlloreCodiciFiscali ha la doppia funzione di creare e controllare codici fiscali,
 * contiene in se, sottoforma di costanti, la lunghezza che un codice fiscale dovrebbe avere, la posizione all'interno del codice delle varie sottoparti,
 * il minimo giorno di nascita che una persona puÚ avere, il numero di giorni in ogni mese, di quanti giorni il giorno di nascita di una donna Ë spostato,
 * le vocali e i codici dei mesi.
 * @author Gheza Giovanni
 */
public class CreatoreControlloreCodiciFiscali {
	static final int LUNGHEZZA_CODICE_FISCALE = 16;

	static final int INIZIO_NEL_CODICE_DEL_NOME_CON_COGNOME = 0;
	static final int FINE_NEL_CODICE_DEL_NOME_CON_COGNOME = 5;

	static final int INIZIO_NEL_CODICE_DEL_NUMERO_DELL_ANNO_DI_NASCITA = 6;
	static final int FINE_NEL_CODICE_DEL_NUMERO_DELL_ANNO_DI_NASCITA = 7;

	static final int POSIZIONE_NEL_CODICE_DEL_MESE = 8;

	static final int INIZIO_NEL_CODICE_DEL_NUMERO_DEL_GIORNO_DI_NASCITA = 9;
	static final int FINE_NEL_CODICE_DEL_NUMERO_DEL_GIORNO_DI_NASCITA = 10;

	static final int POSIZIONE_NEL_CODICE_DELLA_PARTE_ALFABETICA_DEL_COMUNE_DI_NASCITA = 11;
	static final int INIZIO_NEL_CODICE_DELLA_PARTE_NUMERICA_DEL_COMUNE_DI_NASCITA = 12;
	static final int FINE_NEL_CODICE_DELLA_PARTE_NUMERICA_DEL_COMUNE_DI_NASCITA = 14;

	static final int POSIZIONE_NEL_CODICE_DEL_CARATTERE_DI_CONTROLLO = 15;

	static final int MINIMO_GIORNO_DI_NASCITA_PER_UOMINI = 1;
	static final private int SURPLUS_DI_GIORNI_PER_DONNE = 40;

	static final int MASSIMO_GIORNO_DI_NASCITA_PER_UOMINI_PER_APR_GIU_SETT_NOV = 30;
	static final int MASSIMO_GIORNO_DI_NASCITA_PER_UOMINI_PER_FEB = 28;
	static final int MASSIMO_GIORNO_DI_NASCITA_PER_UOMINI_PER_GEN_MAR_MAG_LUG_AGO_OTT_DIC = 31;

	static final private char[] VOCALI = {'A', 'E', 'I', 'O', 'U'};
	static final private char[] CODICI_DEI_MESI = {'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};

	private ArrayList<Comune> listaComuni;

	public CreatoreControlloreCodiciFiscali(ArrayList<Comune> listaComuni) {
		this.listaComuni = listaComuni;
	}

	/**
	 * genera il codice fisale della persona in questione
	 * @param personaInQuestione - la persona di cui si vuole generare il codice fiscale
	 * @return il codice fiscale generato sottoforma di String
	 */
	public String generaCodiceFiscale(Persona personaInQuestione) {
		StringBuffer codiceFiscale = new StringBuffer("");

		codiceFiscale.append(generaCodiceCognome(personaInQuestione.getCognome()));
		codiceFiscale.append(generaCodiceNome(personaInQuestione.getNome()));
		codiceFiscale.append(generaCodiceAnnoDiNascita(personaInQuestione.getAnno()));
		codiceFiscale.append(generaCodiceMeseDiNascita(personaInQuestione.getMese()));
		codiceFiscale.append(generaCodiceGiornoDiNascita(personaInQuestione.getGiorno(), personaInQuestione.getSesso()));
		codiceFiscale.append(generaCodiceComuneDiNascita(personaInQuestione.getComune_nascita()));

		return codiceFiscale.toString();
	}

	/**
	 * genera la parte del codice fiscale definita dal cognome
	 * @param cognome - una stringa che rappresenta il cognome di una persona
	 * @return la parte del codice fiscale definita dal cognome sottoforma di String
	 */
	private String generaCodiceCognome(String cognome) {

		cognome = new String(cognome.toUpperCase());

		StringBuffer codice = new StringBuffer("");
		char[] cognomeInArrayDiChar = cognome.toCharArray();
		int caratteriTrovati = 0;

		//aggiunta delle consonanti
		for(char charDaControllare : cognomeInArrayDiChar) {
			if(charDaControllare != VOCALI[0]
					&& charDaControllare != VOCALI[1]
							&& charDaControllare != VOCALI[2]
									&& charDaControllare != VOCALI[3]
											&& charDaControllare != VOCALI[4]
					) {
				codice.append(charDaControllare);
				caratteriTrovati ++;
			}

			if(caratteriTrovati == 3)
				return codice.toString();
		}

		//se il codice non ha raggiunto la dimensione 3, ovvero ci sono meno di tre consonanti, devo aggiungere le vocali dopo le consonanti
		for(char charDaControllare : cognomeInArrayDiChar) {
			if(charDaControllare == VOCALI[0]
					|| charDaControllare == VOCALI[1]
							|| charDaControllare == VOCALI[2]
									|| charDaControllare == VOCALI[3]
											|| charDaControllare == VOCALI[4]
					) {
				codice.append(charDaControllare);
				caratteriTrovati ++;
			} 

			if(caratteriTrovati == 3)
				return codice.toString();
		}

		//se non ho n√® abbastanza vocali, n√® abbatanza vocali devo aggiungere 'X' alla fine fino ad arrivare a tre caratteri
		if(caratteriTrovati == 2)
			codice.append('X');

		if(caratteriTrovati == 1)
			codice.append("XX");

		if(caratteriTrovati == 0) //Darcsen
			codice.append("XXX");

		return codice.toString();
	}

	/**
	 * genera la parte del codice fiscale definita dal nome
	 * @param nome - una stringa che rappresenta il nome di una persona
	 * @return la parte del codice fiscale definita dal nome sottoforma di String
	 */
	private String generaCodiceNome(String nome) {

		nome = new String(nome.toUpperCase());

		StringBuffer codice = new StringBuffer();
		char[] nomeInArrayDiChar = nome.toCharArray();
		int consonantiIncontrate = 0;
		int caratteriTrovati = 0;

		//aggiunta delle consonanti
		for(char charDaControllare : nomeInArrayDiChar) {
			if(charDaControllare != VOCALI[0]
					&& charDaControllare != VOCALI[1]
							&& charDaControllare != VOCALI[2]
									&& charDaControllare != VOCALI[3]
											&& charDaControllare != VOCALI[4]
					) {
				consonantiIncontrate++;

				if(consonantiIncontrate != 2) { //visto che non devo inserire la seconda consonante quando sono alla seconda consonante incontrata salto
					codice.append(charDaControllare);
					caratteriTrovati ++;
				}
			}

			if(caratteriTrovati == 3)
				return codice.toString();
		}

		//il nome non ha almeno quattro consonanti, quindi devo considerare anche la seconda consonante
		//resetto i caratteri trovati e la StringBuffer
		caratteriTrovati = 0;
		codice.setLength(0);

		for(char charDaControllare : nomeInArrayDiChar) {
			if(charDaControllare != VOCALI[0]
					&& charDaControllare != VOCALI[1]
							&& charDaControllare != VOCALI[2]
									&& charDaControllare != VOCALI[3]
											&& charDaControllare != VOCALI[4]
					) {
				codice.append(charDaControllare);
				caratteriTrovati ++;
			}

			if(caratteriTrovati == 3)
				return codice.toString();
		}

		//se il codice non ha raggiunto la dimensione 3, ovvero ci sono meno di tre consonanti, devo aggiungere le vocali dopo le consonanti
		for(char charDaControllare : nomeInArrayDiChar) {
			if(charDaControllare == VOCALI[0]
					|| charDaControllare == VOCALI[1]
							|| charDaControllare == VOCALI[2]
									|| charDaControllare == VOCALI[3]
											|| charDaControllare == VOCALI[4]
					) {
				codice.append(charDaControllare);
				caratteriTrovati ++;
			}

			if(caratteriTrovati == 3)
				return codice.toString();
		}

		//se non ho n√® abbastanza vocali, n√® abbatanza vocali devo aggiungere 'X' alla fine fino ad arrivare a tre caratteri
		if(caratteriTrovati == 2)
			codice.append('X');

		if(caratteriTrovati == 1)
			codice.append("XX");

		if(caratteriTrovati == 0) //"L'uomo senza nome"
			codice.append("XXX");

		return codice.toString();
	}

	/**
	 * genera la parte del codice fiscale definita dall'anno di nascita
	 * @param annoDiNascita - un intero che rappresenta l'anno di nascita di una persona
	 * @return la parte del codice fiscale definita dall'anno di nascita sottoforma di String
	 */
	private String generaCodiceAnnoDiNascita(int annoDiNascita) {
		return new String(Integer.toString(annoDiNascita).substring(2));
	}

	/**
	 * genera la parte del codice fiscale definita dal mese di nascita
	 * @param meseDiNascita - un intero che rappresenta il mese di nascita di una persona
	 * @return la parte del codice fiscale definita dal mese di nascita sottoforma di String
	 */
	private String generaCodiceMeseDiNascita(int meseDiNascita) {
		return new String("" + CODICI_DEI_MESI[meseDiNascita-1]);
	}

	/**
	 * genera la parte del codice fiscale definita dal giorno di nascita e dal sesso
	 * @param giornoDiNascita - un intero che rappresenta il giorno di nascita di una persona
	 * @param sesso - una stringa che rappresenta il sesso di una persona
	 * @return la parte del codice fiscale definita dal giorno di nascita e dal sesso sottoforma di String
	 */
	private String generaCodiceGiornoDiNascita(int giornoDiNascita, String sesso) {
		//aggiunta dei quaranta giorni in pi√π per le donne
		if(sesso.equals("F"))
			giornoDiNascita += SURPLUS_DI_GIORNI_PER_DONNE;

		StringBuffer codiceGiornoDiNascita = new StringBuffer(Integer.toString(giornoDiNascita));

		//se il giorno di nascita √® prima del dieci di un mese ci deve essere uno zero prima del numero
		if(giornoDiNascita < 10) {
			codiceGiornoDiNascita.insert(0, '0');
		}

		return codiceGiornoDiNascita.toString();
	}

	/**
	 * trova all'interno della lista dei comuni il comune di nascita e il codice associato
	 * @param comuneDiNascita - una stringa che rappresenta il comune di nascita di una persona
	 * @return il codice del cumune di nascita
	 */
	private String generaCodiceComuneDiNascita(String comuneDiNascita) {
		for(Comune comuneInQuestione: listaComuni) {
			if(comuneInQuestione.getNome().equals(comuneDiNascita))
				return comuneInQuestione.getCodice();
		}

		return "!!!!";
	}

	/**
	 * controlla la correttezza del codice fiscale inserito
	 * @param codiceFiscale - il codice fiscale che si vuole controllare
	 * @return true se il codice fiscale Ë corretto, false altrimenti
	 */
	public boolean controlloCorrettezzaCodiceFiscale(String codiceFiscale) {

		//controllo lunghezza
		if(codiceFiscale.length() != LUNGHEZZA_CODICE_FISCALE)
			return false;

		//controllo che tutti i caratteri siano in uppercase, non siano spazi e non siano caratteri speciali
		char[] codiceFiscaleInArrayDiChar = codiceFiscale.toCharArray();

		for (char c : codiceFiscaleInArrayDiChar) {

			if(Character.isLetter(c)) //i numeri non possono essere ne maiuscoli ne minuscoli ne spazi, quindi devo prendere solo lettere

				if(Character.isLowerCase(c) //se Ë lettera minuscola...

						|| !(c >= 'A' && c <= 'Z') //..o Ë una lettera non inglese (accentata, o lettera straniera)...

						|| Character.isWhitespace(c) //...o Ë uno spazi o un'altro tipo di separatore...

						|| (!Character.isLetter(c) && !Character.isDigit(c) && !Character.isWhitespace(c)) //...o Ë un carattere speciale...

						) {

					return false;//..ritorno false.

				}
		}

		//controllo che i caratteri per il Nome e il Cognome siano lettere
		char[] parteDelNomeECognome = codiceFiscale.substring(INIZIO_NEL_CODICE_DEL_NOME_CON_COGNOME, FINE_NEL_CODICE_DEL_NOME_CON_COGNOME + 1).toCharArray();

		for (char c : parteDelNomeECognome) {
			if(!Character.isLetter(c)) 
				return false;
		}

		//controllo che i caratteri dell'anno di nascita siano numeri
		if(!Character.isDigit(codiceFiscale.charAt(INIZIO_NEL_CODICE_DEL_NUMERO_DELL_ANNO_DI_NASCITA)) || !Character.isDigit(codiceFiscale.charAt(FINE_NEL_CODICE_DEL_NUMERO_DELL_ANNO_DI_NASCITA)))
			return false;

		//controllo che il codice del mese di nascita sia uno dei codici accettati
		char codiceDelMese = codiceFiscale.charAt(POSIZIONE_NEL_CODICE_DEL_MESE);
		boolean correttezzaDelMese = false;
		for (char c : CODICI_DEI_MESI) {
			if(codiceDelMese == c) {
				correttezzaDelMese = true;
				break;
			}
		}
		if(!correttezzaDelMese)
			return false;
		
		
		//controllo che giorno di nascita
		//controllo che il giorno di nascita sia un numero
		String giornoDiNascitaComeString = codiceFiscale.substring(INIZIO_NEL_CODICE_DEL_NUMERO_DEL_GIORNO_DI_NASCITA, FINE_NEL_CODICE_DEL_NUMERO_DEL_GIORNO_DI_NASCITA + 1);
		char[] giornoDiNascitaComeCharArray = giornoDiNascitaComeString.toCharArray();
		for(char c: giornoDiNascitaComeCharArray) {
			if(!Character.isDigit(c)) 
				return false;
		}
		//ottengo il giorno di nascita come intero
		int giornoDiNascita = Integer.parseInt(giornoDiNascitaComeString);

		//se il giorno di nascita √® maggiore di 31 potrebbe essere una donna, quindi per avre tutte le date tra 1 e 31 tolgo il sourplus dato delle donne alla data
		if(giornoDiNascita > SURPLUS_DI_GIORNI_PER_DONNE)
			giornoDiNascita -= SURPLUS_DI_GIORNI_PER_DONNE;
		//caso 30 giorni
		if(codiceDelMese == CODICI_DEI_MESI[10] //novembre
				|| codiceDelMese == CODICI_DEI_MESI[3] //april
						|| codiceDelMese == CODICI_DEI_MESI[5] //giugno
								|| codiceDelMese == CODICI_DEI_MESI[8]) //e settembre
		{

			if(giornoDiNascita < MINIMO_GIORNO_DI_NASCITA_PER_UOMINI || giornoDiNascita > MASSIMO_GIORNO_DI_NASCITA_PER_UOMINI_PER_APR_GIU_SETT_NOV)
				return false;
		} else //caso 31 giorni

			if(codiceDelMese == CODICI_DEI_MESI[0] //gennaio
					|| codiceDelMese == CODICI_DEI_MESI[2] //marzo
							|| codiceDelMese == CODICI_DEI_MESI[4] //maggio
									|| codiceDelMese == CODICI_DEI_MESI[6] //luglio
											|| codiceDelMese == CODICI_DEI_MESI[7] //agosto
													|| codiceDelMese == CODICI_DEI_MESI[9] //ottobre
															|| codiceDelMese == CODICI_DEI_MESI[11] //dicembre
					){

				if(giornoDiNascita < MINIMO_GIORNO_DI_NASCITA_PER_UOMINI || giornoDiNascita > MASSIMO_GIORNO_DI_NASCITA_PER_UOMINI_PER_GEN_MAR_MAG_LUG_AGO_OTT_DIC)
					return false;
			} else //caso febbraio
			{
				if(giornoDiNascita < MINIMO_GIORNO_DI_NASCITA_PER_UOMINI || giornoDiNascita > MASSIMO_GIORNO_DI_NASCITA_PER_UOMINI_PER_FEB)
					return false;
			}
		
		//controllo del luogo di nascita
		//controllo che la parte alfabetica sia una lettera
		char letteraComuneDiNascita = codiceFiscale.charAt(POSIZIONE_NEL_CODICE_DELLA_PARTE_ALFABETICA_DEL_COMUNE_DI_NASCITA);
		if(!Character.isLetter(letteraComuneDiNascita))
			return false;
		//controllo che la parte numerica sia un numero
		char[] parteNumericaComuneDiNascita = codiceFiscale.substring(INIZIO_NEL_CODICE_DELLA_PARTE_NUMERICA_DEL_COMUNE_DI_NASCITA, FINE_NEL_CODICE_DELLA_PARTE_NUMERICA_DEL_COMUNE_DI_NASCITA + 1).toCharArray();
		for (char c : parteNumericaComuneDiNascita)
			if(!Character.isDigit(c))
				return false;
		
		//controllo carattere di controllo
		char carattereDiControllo = codiceFiscale.charAt(POSIZIONE_NEL_CODICE_DEL_CARATTERE_DI_CONTROLLO);
		if(!Character.isLetter(carattereDiControllo))
			return false;
		return true;

	}
}