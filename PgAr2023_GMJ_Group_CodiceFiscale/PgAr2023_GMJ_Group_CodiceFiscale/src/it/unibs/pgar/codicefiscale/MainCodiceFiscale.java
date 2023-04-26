package it.unibs.pgar.codicefiscale;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class MainCodiceFiscale {
	
	private static final String PERCORSO_DI_INPUT_PERSONE = "PgAr2023_GMJ_Group_CodiceFiscale/src/data/InputPersone.xml";
	private static final String PERCORSO_DI_COMUNI = "PgAr2023_GMJ_Group_CodiceFiscale/src/data/Comuni.xml";
	private static final String PERCORSO_DI_CODICIFISCALI = "PgAr2023_GMJ_Group_CodiceFiscale/src/data/CodiciFiscali.xml";
	private static final String PERCORSO_FILE_DI_OUTPUT_IN_XML = "PgAr2023_GMJ_Group_CodiceFiscale/src/data/codiciPersone.xml";
	private static final String PERCORSO_FILE_DI_OUTPUT_IN_JSON = "PgAr2023_GMJ_Group_CodiceFiscale/src/data/codiciPersone.json";
	
	@SerializedName("persone")
    private List<Persona> listaPersone;
    @SerializedName("codici")
    private CodiciFiscali codiciFiscaliSbagliatiSpagliati;

    public MainCodiceFiscale(List<Persona> listaPersone, CodiciFiscali codiciFiscaliSbagliatiSpagliati){
        this.listaPersone = listaPersone;
        this.codiciFiscaliSbagliatiSpagliati = codiciFiscaliSbagliatiSpagliati;
    }
    
	public static void main(String []args){
		//creatore del reader di XML
		XMLReader myLettoreDiXML = new XMLReader();
		
		//lettura dati delle persone
		List<Persona> listaDellePersone = myLettoreDiXML.readConfigPersone(PERCORSO_DI_INPUT_PERSONE);
		//lettura dati dei comuni
		List<Comune> listaDeiComuni = myLettoreDiXML.readConfigComuni(PERCORSO_DI_COMUNI);
		//lettura dei codici fiscali
		ArrayList<String> listaCodiciFiscaliInput = myLettoreDiXML.readConfigCodiciFiscali(PERCORSO_DI_CODICIFISCALI);


		//generazione dei codici fiscali
		ArrayList<String> listaCodiciFiscaliGenerati = new ArrayList<>();
		CreatoreControlloreCodiciFiscali myCreatoreCodiceFiscale = new CreatoreControlloreCodiciFiscali((ArrayList<Comune>) listaDeiComuni);
		for(Persona p: listaDellePersone){
			listaCodiciFiscaliGenerati.add(myCreatoreCodiceFiscale.generaCodiceFiscale(p));
		}

		//controllo dei codici fiscali e la loro suddivisione a seconda della loro correttezza o presenza
		ArrayList<String> listCodiciFiscaliInvalidi = new ArrayList<>();
		ArrayList<String> listCodiciFiscaliSpagliati = new ArrayList<>();
		ArrayList<String> listCodiciFiscaliCorretti = new ArrayList<>();

		for(String codiceFiscaleInput : listaCodiciFiscaliInput){
			if(myCreatoreCodiceFiscale.controlloCorrettezzaCodiceFiscale(codiceFiscaleInput)){
				boolean codiceAssociatoTrovato = false;
				int i=0;
				for(String codiceFiscaleGenerato: listaCodiciFiscaliGenerati){
					if(codiceFiscaleGenerato.equals(codiceFiscaleInput.substring(0, 15))){
						codiceAssociatoTrovato = true;
						listCodiciFiscaliCorretti.add(codiceFiscaleInput);
						listaDellePersone.get(i).setCodiceFiscale(codiceFiscaleInput);
						break;
					}
					i++;
				}
				if(!codiceAssociatoTrovato)
					listCodiciFiscaliSpagliati.add(codiceFiscaleInput);
			} else {
				listCodiciFiscaliInvalidi.add(codiceFiscaleInput);
			}
		}

		//generazione del file di output in XML
		XMLWriter myScrittoreDiXML = new XMLWriter();
		myScrittoreDiXML.setFile(PERCORSO_FILE_DI_OUTPUT_IN_XML);
		try{
			myScrittoreDiXML.saveConfig(listaDellePersone,listCodiciFiscaliInvalidi,listCodiciFiscaliSpagliati);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		//generazione del file di output in XML
		JsonFileWriter writtenFile = new JsonFileWriter(PERCORSO_FILE_DI_OUTPUT_IN_JSON);
        writtenFile.writeFile(listaDellePersone, listCodiciFiscaliInvalidi, listCodiciFiscaliSpagliati);
	}
}