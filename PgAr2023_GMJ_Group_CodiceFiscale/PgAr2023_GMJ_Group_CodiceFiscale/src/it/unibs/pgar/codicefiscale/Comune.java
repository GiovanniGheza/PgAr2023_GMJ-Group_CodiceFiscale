package it.unibs.pgar.codicefiscale;

/**
 * una classe che rappresenta un comune
 * @author Gheza Giovanni
 */
public class Comune {
    private String nome,codice;
   
    /**
     * costruttore vuoto
     */
    public Comune() {
        nome = "";
        codice = "";
    }
    
    /**
     * costruttore con in input nome e codice del comune
     */
    public Comune(String nome, String codice) {
        this.nome = nome;
        this.codice = codice;
     
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }
    
    @Override
    public String toString(){
        return "Comune [nome = "+ nome +", codice = "+codice+"]";
    
    }
    
    
}
