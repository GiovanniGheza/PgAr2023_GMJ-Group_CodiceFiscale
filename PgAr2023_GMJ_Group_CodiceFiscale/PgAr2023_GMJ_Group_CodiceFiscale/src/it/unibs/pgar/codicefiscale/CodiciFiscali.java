package it.unibs.pgar.codicefiscale;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CodiciFiscali {
    @SerializedName("invalidi")
    private ArrayList<String> listCodiciFiscaliSbagliati;
    @SerializedName("spaiati")
    private ArrayList<String> listCodiciFiscaliSpagliati;
    
    public CodiciFiscali(ArrayList<String> listCodiciFiscaliSbagliati,ArrayList<String> listCodiciFiscaliSpagliati){
        
        this.listCodiciFiscaliSbagliati = listCodiciFiscaliSbagliati;
        this.listCodiciFiscaliSpagliati = listCodiciFiscaliSpagliati;
    }
}