/*LogDiNavigazioneGUI.java*/

import java.io.*;
import java.net.*;
import java.nio.file.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LogDiNavigazioneGUI{
    private static String evento;

    public static void appendiEvento(){ //(1)  
        if(!valida()){
           return; 
        }
        try{
            Files.write(Paths.get("log.xml"), ("\n" + evento + "\n").getBytes(), StandardOpenOption.APPEND);
        }catch(IOException e){
            e.printStackTrace();
        }        
    }
    
    public static boolean valida() { //(2) 
        try {  
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Document documento = db.parse(new StringBufferInputStream(evento));
                Schema schema = schemaFactory.newSchema(new StreamSource(new File("ValidazioneEventoLog.xsd"))); 
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

    public static boolean riceviEvento(){ //(3) 
        try(
           ServerSocket serverSocket = new ServerSocket(8080);
           Socket socket = serverSocket.accept();     
           DataInputStream dis = new DataInputStream(socket.getInputStream());
        ){
           evento = dis.readUTF();        
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args){ //(4) 
        System.out.println("Sono il server log");
        while(riceviEvento()){
            System.out.println(evento + "\n");
            appendiEvento();
        }
    }
}

/*
    1. Appende l'evento in fondo al file di log 

    2. Valida l'evento secondo lo schema "ValidazioneEventoLog.xsd"

    3. Riceve l'evento inviatogli dal client

    4. Quando riceve un evento lo appende al file di log

                                                      Made by: Stefano Petrocchi
*/