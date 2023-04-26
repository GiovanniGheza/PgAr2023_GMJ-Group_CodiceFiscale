package it.unibs.pgar.codicefiscale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;



public class JsonFileWriter {
    private String configFile;
    
    public JsonFileWriter(String configFile){
        this.configFile = configFile;
    }
    
    public void writeFile(List<Persona> listaPersone,ArrayList<String> listCodiciFiscaliSbagliati,ArrayList<String> listCodiciFiscaliSpagliati){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try{
            FileWriter writer = new FileWriter(configFile);
            
            CodiciFiscali codiciFiscaliSbagliatiSpagliati = new CodiciFiscali(listCodiciFiscaliSbagliati,listCodiciFiscaliSpagliati);
            MainCodiceFiscale m = new MainCodiceFiscale(listaPersone,codiciFiscaliSbagliatiSpagliati);
            gson.toJson(m, writer);
            writer.close();
           
        } catch(Exception e){
            System.out.println("Error -> "+e.getMessage());
        }
    
    }
    
    
}
