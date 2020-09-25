/*EventoDiNavigazioneGUI.java*/

import java.io.*;
import com.thoughtworks.xstream.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventoDiNavigazioneGUI implements Serializable{
    
    private static Configurazione parametri;  
    private String timestamp;
    private String indirizzoIP;
    private String oggetto;
    private String evento;
    private String esito;
    
    EventoDiNavigazioneGUI(Configurazione parametri){
        EventoDiNavigazioneGUI.parametri = parametri;
    }
    
    EventoDiNavigazioneGUI(String oggetto, String evento, String esito, String indirizzoIP){
        this.oggetto = oggetto;
        this.evento = evento;
        this.esito = esito;
        this.indirizzoIP = indirizzoIP;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM YYYY HH:mm:ss.SS");
        this.timestamp = LocalDateTime.now().format(formatter);
    }
    
    public void invia() { //(1) 
        try(
            Socket socket = new Socket(parametri.getServerLog().getIndirizzoIP(),parametri.getServerLog().getPorta());
            DataOutputStream streamUscita = new DataOutputStream(socket.getOutputStream());
        ){
            streamUscita.writeUTF(this.toString());
            System.out.println("Log inviato");
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    public String toString(){ //(2) 
        XStream xmlStream = new XStream();
        return xmlStream.toXML(this);
    }
}

/*
    1. Invia se stesso al server di log remoto

    2. Serializza se stesso come xml

                                                      Made by: Stefano Petrocchi
*/