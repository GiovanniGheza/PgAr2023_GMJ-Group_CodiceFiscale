package it.unibs.pgar.codicefiscale;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * una classe che permette di creare degli scrittori di XML
 * @author Kumar Mega
 */
public class XMLWriter {
    private String configFile;
    
    /**
     * setta il file su cui si vuole scrivere
     * @param configFile - percorso del file su cui la classe andr� a scrivere
     */
    public void setFile(String configFile){
        this.configFile = configFile;
    
    }
    
    /**
     * genera il file di output
     * @param listaPersone - la lista delle persone di cui si vogliono salvare i dati
     * @param listCodiciFiscaliInvalidi - la lista dei codici fiscali che presentano degli errori
     * @param listCodiciFiscaliSpagliati - la lista dei codici fiscali corretti ma senza una persona associata
     */
    public void saveConfig(List<Persona> listaPersone,ArrayList<String> listCodiciFiscaliInvalidi,ArrayList<String> listCodiciFiscaliSpagliati) throws FileNotFoundException, XMLStreamException{
        
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(configFile));
        
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);
        
        eventWriter.add(end);
        
        StartElement configStartElement = eventFactory.createStartElement("", "", "output");
        eventWriter.add(configStartElement);
        
        eventWriter.add(end);
        eventWriter.add(tab);
        
        eventWriter.add(eventFactory.createStartElement("", "", "persone"));
        eventWriter.add(eventFactory.createAttribute("numero",""+listaPersone.size()));
        
        for(Persona p: listaPersone){
            eventWriter.add(end);
            eventWriter.add(tab);
            eventWriter.add(tab);
            
            eventWriter.add(eventFactory.createStartElement("", "", "persona"));
            eventWriter.add(eventFactory.createAttribute("id",""+p.getCodiceUnivoco()));
            
            createNode(eventWriter,"nome",p.getNome());
            createNode(eventWriter,"cognome",p.getCognome());
            createNode(eventWriter,"sesso",p.getSesso());
            createNode(eventWriter,"comune_nascita",p.getComune_nascita());
            createNode(eventWriter,"data_nascita",p.getData_nascita());
            createNode(eventWriter,"codice_fiscale",p.getCodiceFiscale());
            
            eventWriter.add(end);
            eventWriter.add(tab);
            eventWriter.add(tab);
            eventWriter.add(eventFactory.createEndElement("", "", "persona"));
        }
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("","","persone"));
       
        eventWriter.add(end);
        eventWriter.add(tab);
        
        eventWriter.add(eventFactory.createStartElement("", "", "codici"));
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", "invalidi"));
        eventWriter.add(eventFactory.createAttribute("numero",""+listCodiciFiscaliInvalidi.size()));
        
        for(String s: listCodiciFiscaliInvalidi){
            eventWriter.add(tab);
            eventWriter.add(tab);
            createNode(eventWriter,"codice",s);
        }
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "invalidi"));
        
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", "spaiati"));
        eventWriter.add(eventFactory.createAttribute("numero",""+listCodiciFiscaliSpagliati.size()));
       
        for(String p: listCodiciFiscaliSpagliati){
            eventWriter.add(tab);
            eventWriter.add(tab);
           
            createNode(eventWriter,"codice",p);
        }
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "spaiati"));
        
        eventWriter.add(end);
        eventWriter.add(tab);
      
        eventWriter.add(eventFactory.createEndElement("","","codici"));
        
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    
    }
    
    /**
     * crea un nuovo nodo
     * @param eventWriter - il writer dell'event
     * @param name - nome dell'elemento figlio sottoforma di stringa
     * @param value - valore dell'elemento figlio sottoforma di stringa
     */
    private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", name));
       
        eventWriter.add(eventFactory.createCharacters(value));
        
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        
    }
}
