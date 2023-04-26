
package it.unibs.pgar.codicefiscale;

/**
 * una classe che rappresenta una persona con i suoi dati anagrafici.
 * Essa contiene alcune costati per tenere la posizione dell'anno, del mese e del giorno in una data sottoforma di stringa del tipo: "AAAA-MM-GG"
 * @author Gheza Giovanni
 */
public class Persona {
	static final int INIZIO_NELLA_DATA_DELL_ANNO= 0, FINE_NELLA_DATA_DELL_ANNO = 4;
	static final int INIZIO_NELLA_DATA_DEL_MESE= 5, FINE_NELLA_DATA_DEL_MESE = 7;
	static final int INIZIO_NELLA_DATA_DEL_GIORNO= 8, FINE_NELLA_DATA_DEL_GIORNO = 10;

	private String nome,cognome,comune_nascita,data_nascita,sesso,codice_fiscale = "ASSENTE";
	private int codice_univoco,meseDiNascita,giornoDiNascita,annoDiNascita;

	public Persona(){

	}


	/**
	 * Costruttore di una Persona senza codice fiscale
	 * @param nome - la stringa che rappresenta il nome
	 * @param cognome - la stringa che rappresenta il cognome
	 * @param comunediNascita - la stringa che rappresenta il nome del comune di nascita
	 * @param dataDiNascita - la stringa che rappresenta la data di nascita in forma: "AAAA-MM-GG"
	 * @param sesso - la stringa che rappresenta il sesso della persona
	 * @param codiceUnivoco - l'id della persona
	 */
	public Persona(String nome, String cognome, String comunediNascita, String dataDiNascita, String sesso, int codiceUnivoco) {
		this.nome = nome;
		this.cognome = cognome;
		this.comune_nascita = comunediNascita;
		this.data_nascita = dataDiNascita;

		this.annoDiNascita = Integer.parseInt(dataDiNascita.substring(INIZIO_NELLA_DATA_DELL_ANNO, FINE_NELLA_DATA_DELL_ANNO));
		this.meseDiNascita = Integer.parseInt(dataDiNascita.substring(INIZIO_NELLA_DATA_DEL_MESE, FINE_NELLA_DATA_DEL_MESE));
		this.giornoDiNascita = Integer.parseInt(dataDiNascita.substring(INIZIO_NELLA_DATA_DEL_GIORNO, FINE_NELLA_DATA_DEL_GIORNO));

		this.sesso = sesso;
		this.codice_univoco = codiceUnivoco;

	}

	/**
	 * Costruttore di una Persona con un codice fiscale
	 * @param nome - la stringa che rappresenta il nome
	 * @param cognome - la stringa che rappresenta il cognome
	 * @param comunediNascita - la stringa che rappresenta il nome del comune di nascita
	 * @param dataDiNascita - la stringa che rappresenta la data di nascita in forma: "AAAA-MM-GG"
	 * @param sesso - la stringa che rappresenta il sesso della persona
	 * @param codiceUnivoco - l'id della persona sottoforma di intero
	 * @param codice_fiscale - il codice fiscale come stringa
	 */
	public Persona(String nome, String cognome, String comunediNascita, String dataDiNascita, String sesso, int codiceUnivoco, String codice_fiscale) {
		this.nome = nome;
		this.cognome = cognome;
		this.comune_nascita = comunediNascita;
		this.data_nascita = dataDiNascita;

		this.annoDiNascita = Integer.parseInt(dataDiNascita.substring(INIZIO_NELLA_DATA_DELL_ANNO, FINE_NELLA_DATA_DELL_ANNO));
		this.meseDiNascita = Integer.parseInt(dataDiNascita.substring(INIZIO_NELLA_DATA_DEL_MESE, FINE_NELLA_DATA_DEL_MESE));
		this.giornoDiNascita = Integer.parseInt(dataDiNascita.substring(INIZIO_NELLA_DATA_DEL_GIORNO, FINE_NELLA_DATA_DEL_GIORNO));

		this.sesso = sesso;
		this.codice_univoco = codiceUnivoco;
		this.codice_fiscale = codice_fiscale;
	}

	//GETTERS
	
	public int getCodiceUnivoco(){
		return codice_univoco;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getSesso(){
		return sesso;
	}
	public String getComune_nascita() {
		return comune_nascita;
	}

	public String getData_nascita() {
		return data_nascita;
	}

	public int getMese() {
		return meseDiNascita;
	}

	public int getGiorno() {
		return giornoDiNascita;
	}

	public int getAnno() {
		return annoDiNascita;
	}

	public String getCodiceFiscale(){
		return codice_fiscale;
	}

	
	//SETTERS
	
	public void setCodiceUnivoco(int codice_univoco){
		this.codice_univoco = codice_univoco;

	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setComune_nascita(String comune_nascita) {
		this.comune_nascita = comune_nascita;
	}

	public void setData_nascita(String data_nascita) {
		this.data_nascita = data_nascita;
		this.annoDiNascita = Integer.parseInt(data_nascita.substring(INIZIO_NELLA_DATA_DELL_ANNO, FINE_NELLA_DATA_DELL_ANNO));
		this.meseDiNascita = Integer.parseInt(data_nascita.substring(INIZIO_NELLA_DATA_DEL_MESE, FINE_NELLA_DATA_DEL_MESE));
		this.giornoDiNascita = Integer.parseInt(data_nascita.substring(INIZIO_NELLA_DATA_DEL_GIORNO, FINE_NELLA_DATA_DEL_GIORNO));

	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public void setMese(int mese) {
		this.meseDiNascita = mese;
	}

	public void setGiorno(int giorno) {
		this.giornoDiNascita = giorno;
	}

	public void setAnno(int anno) {
		this.annoDiNascita = anno;
	}

	public void setCodiceFiscale(String codice_fiscale){
		this.codice_fiscale = codice_fiscale;
	}

	@Override
	public String toString(){
		return "Persona "
				+ "[id = " + codice_univoco 
				+ ", nome = " + nome 
				+ ", cognome = " + cognome
				+ ", sesso = " + sesso
				+ ", comune di nascita = " + comune_nascita
				+ ", data di nascita = " + data_nascita
				+ "]";
	}
}