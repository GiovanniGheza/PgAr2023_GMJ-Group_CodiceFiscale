package it.unibs.pgar.codicefiscale;

import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * una classe che permette di creare dei lettori di XML
 * @author Kumar Mega
 */
public class XMLReader {
    static final String PERSONA = "persona";
    static final String NOME = "nome";
    static final String COGNOME = "cognome";
    static final String SESSO = "sesso";
    static final String COMUNE_NASCITA = "comune_nascita";
    static final String DATA_NASCITA = "data_nascita";
    
    static final String COMUNE = "comune";
    static final String CODICE = "codice";
    
    public List<Persona> readConfigPersone(String configFile){
        List<Persona> persone = new ArrayList<>();
        try{
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Persona persona = null;
            
            while(eventReader.hasNext()){
                XMLEvent event =  eventReader.nextEvent();
                
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();
                    
                    switch(elementName){
                        case PERSONA:
                            persona = new Persona();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while(attributes.hasNext()){
                                Attribute attribute = attributes.next();
                                if(attribute.getName().toString().equals("id"))
                                    persona.setCodiceUnivoco(Integer.parseInt(attribute.getValue()));
                            }
                            break;
                        case NOME:
                            event = eventReader.nextEvent();
                            persona.setNome(event.asCharacters().getData());
                            break;
                        case COGNOME:
                            event = eventReader.nextEvent();
                            persona.setCognome(event.asCharacters().getData());
                            break;
                        case SESSO:
                            event = eventReader.nextEvent();
                            persona.setSesso(event.asCharacters().getData());
                            break;
                        case COMUNE_NASCITA:
                            event = eventReader.nextEvent();
                            persona.setComune_nascita(event.asCharacters().getData());
                            break;
                        case DATA_NASCITA:
                            event = eventReader.nextEvent();
                            persona.setData_nascita(event.asCharacters().getData());
                            break;
                        
                    }
                
                }
                if(event.isEndElement()){
                    EndElement endElement = event.asEndElement();
                    if(endElement.getName().getLocalPart().equals(PERSONA))
                        persone.add(persona);
                }
            }
        }catch (Exception e){
            System.out.println("Error -> "+e.getMessage());
        }
        return persone;
    }
    
    
    public List<Comune> readConfigComuni(String configFile){
        List<Comune> comuni = new ArrayList<>();
        try{
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Comune comune = null;
            
            while(eventReader.hasNext()){
                XMLEvent event =  eventReader.nextEvent();
                
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();
                    
                    switch(elementName){
                        case COMUNE:
                            comune = new Comune();
                            break;
                      
                        case NOME:
                            event = eventReader.nextEvent();
                            comune.setNome(event.asCharacters().getData());
                            break;
                        case CODICE:
                            event = eventReader.nextEvent();
                            comune.setCodice(event.asCharacters().getData());
                            break;
                    }
                }
                if(event.isEndElement()){
                    EndElement endElement = event.asEndElement();
                    if(endElement.getName().getLocalPart().equals(COMUNE))
                        comuni.add(comune);
                }
            }
        }catch (Exception e){
            System.out.println("Error -> "+e.getMessage());
        }
        return comuni;
    }
    
    public ArrayList<String> readConfigCodiciFiscali(String configFile){
        ArrayList<String> listCodiceFiscaliInput = new ArrayList<>();;
        try{
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
     
            while(eventReader.hasNext()){
                XMLEvent event =  eventReader.nextEvent();
                
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();
                    
                    switch(elementName){
                        case CODICE:
                            event = eventReader.nextEvent();
                            listCodiceFiscaliInput.add(event.asCharacters().getData());
                            break;
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error -> "+e.getMessage());
        }
        return listCodiceFiscaliInput;
    }
    
}
