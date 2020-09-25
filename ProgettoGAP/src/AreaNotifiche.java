/*TabellaVisualeAffitti.java*/

import java.time.LocalTime;
import javafx.geometry.Pos;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class AreaNotifiche extends TitledPane{
    
    private final TextFlow messaggiAUtente = new TextFlow(); //(1) 
    
    AreaNotifiche(){ //(2) 
        super();
        
        setText("Area Notifiche");
        setContent(messaggiAUtente);
        setAlignment(Pos.CENTER);
        setLayoutX(14);
        setLayoutY(800);
        setPrefSize(435, 266);
        setCollapsible(false);
        
        if(LocalTime.now().isBefore(LocalTime.of(12, 0))){
            aggiungiMessaggio("\nBuongiorno!", Color.BLACK);
        }else{
            if(LocalTime.now().isBefore(LocalTime.of(17, 0))){
               aggiungiMessaggio("\nBuon pomeriggio!", Color.BLACK); 
            }else{
                aggiungiMessaggio("\nBuonasera!", Color.BLACK);
            }
        }
        
        messaggiAUtente.setTextAlignment(TextAlignment.CENTER);
        messaggiAUtente.setPrefSize(430, 266);
    }
    
    public void aggiungiMessaggio(String contenuto, Color colore){//(3) 
        Text messaggio = new Text(contenuto);
        messaggio.setFill(colore);
        if(messaggiAUtente.getChildren().size() > 4){
            messaggiAUtente.getChildren().remove(messaggiAUtente.getChildren().size() - 1 );
        }
        messaggiAUtente.getChildren().add(0, messaggio);
    }
}

/*
    1. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/text/TextFlow.html

    2. Inizializza gli elementi grafici e li posiziona + messaggio di benvenuto

    3. Aggiunge un messaggio a scorrimento nella TextFlow

                                                      Made by: Stefano Petrocchi
*/