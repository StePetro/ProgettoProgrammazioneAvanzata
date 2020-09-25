/*GestioneParametriDiConfigurazione.java*/

import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class GestioneParametriDiConfigurazione{

    public static Configurazione prelevaParametri() { //(1) 
        Configurazione parametri = null;
        if(valida()){
            XStream xmlStream = new XStream(); 
            String xml = xmlStream.toXML(parametri); 
            try { 
                xml = new String(Files.readAllBytes(Paths.get("fileDiConfigurazione.txt")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            parametri = (Configurazione)xmlStream.fromXML(xml); 
            System.out.println("Parametri di configurazione prelevati");
        }
        return parametri;
    }
    
    public static boolean valida() { //(2) 
        try {  
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Document documento = db.parse(new File("fileDiConfigurazione.txt"));
                Schema schema = schemaFactory.newSchema(new StreamSource(new File("ValidazioneFileConfigurazione.xsd"))); 
                schema.newValidator().validate(new DOMSource(documento)); 
        } catch (ParserConfigurationException | SAXException | IOException e) {
            if (e instanceof SAXException) 
                System.out.println("Errore di validazione: " + e.getMessage());
            else
                System.out.println(e.getMessage());   
            return false;
        }
        return true;
    }
}

/*
    1.  Preleva i parametri di configurazione dal file di configurazione

    2.  Valida i file di configurazione secondo lo schema 
        "ValidazioneFileConfigurazione.xsd"

                                                      Made by: Stefano Petrocchi
*/