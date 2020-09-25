/*TabellaVisualeAffitti.java*/

import java.util.List;
import javafx.collections.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabellaVisualeAffitti extends TableView{
    
        private final ObservableList<Affitto> listaOsservabileAffitti;
        
    TabellaVisualeAffitti(){ //(1) 
       
        TableColumn colonnaLocale = new TableColumn("Locale");
        colonnaLocale.setCellValueFactory(new PropertyValueFactory<>("locale"));
        
        TableColumn colonnaStato = new TableColumn("Stato");
        colonnaStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        
        TableColumn colonnaCheckin = new TableColumn("Check-in");
        colonnaCheckin.setCellValueFactory(new PropertyValueFactory<>("checkin"));
        
        TableColumn colonnaCheckout = new TableColumn("Check-out");
        colonnaCheckout.setCellValueFactory(new PropertyValueFactory<>("checkout"));
        
        TableColumn colonnaAffittoGiornaliero = new TableColumn("Affitto Giornaliero");
        colonnaAffittoGiornaliero.setCellValueFactory(new PropertyValueFactory<>("affittoGiornaliero"));
        
        TableColumn colonnaIncassoTotale = new TableColumn("Incasso Totale");
        colonnaIncassoTotale.setCellValueFactory(new PropertyValueFactory<>("incassoTotale"));
        
        TableColumn colonnaTasse = new TableColumn("Tasse");
        colonnaTasse.setCellValueFactory(new PropertyValueFactory<>("tasse"));
        
        TableColumn colonnaGuadagnoNetto = new TableColumn("Guadagno Netto");
        colonnaGuadagnoNetto.setCellValueFactory(new PropertyValueFactory<>("guadagnoNetto"));
        
        listaOsservabileAffitti = FXCollections.observableArrayList();
        
        this.setItems(listaOsservabileAffitti);
        this.getColumns().addAll(colonnaLocale, colonnaStato, colonnaCheckin, colonnaCheckout, colonnaAffittoGiornaliero, colonnaIncassoTotale, colonnaTasse, colonnaGuadagnoNetto);  
    }
    
    public void aggiorna(List<Affitto> listaAffitti){ //(2) 
        listaOsservabileAffitti.setAll(listaAffitti);
    }
}

/*
    1. Inizializza la tabella

    2. Aggiorna i dati della tabella 

                                                      Made by: Stefano Petrocchi
*/