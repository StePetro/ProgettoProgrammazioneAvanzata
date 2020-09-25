/*TabellaVisualeAffitti.java*/

import javafx.application.*;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;

public class AvvioGAP extends Application {
    
    private final Configurazione parametri = (new GestioneParametriDiConfigurazione()).prelevaParametri();
    private final ArchivioAffitti archivio = new ArchivioAffitti(parametri);
    private final CacheInserimentiNonValidati cache = new CacheInserimentiNonValidati();
    private final AreaNotifiche areaNotifiche = new AreaNotifiche();
    private RegistroEStatisticheAffitti sezioneRegistroEStatistiche;
    private InserimentoAffitti sezioneInserimento;
    
    @Override
    public void start(Stage stage) { //(1) 
        DatiFormInserimento datiInserimento = cache.caricaDaCache();
        sezioneInserimento = new InserimentoAffitti(datiInserimento);
        sezioneRegistroEStatistiche = new RegistroEStatisticheAffitti(archivio.prelevaDistinti(parametri.getPreferenze().getCodiceUtenteDelLocatore()));
        
        new EventoDiNavigazioneGUI(parametri);
        inizializzaEventi();
        aggiornaStatistiche();
        
        Text titolo = new Text("       G.A.P.");
        Text sottotitolo = new Text("\n   Gestione Affitti Privati");
        titolo.setId("titolo");
        sottotitolo.setId("sottotitolo");
        TextFlow titoloESottotitolo = new TextFlow();
        titoloESottotitolo.getChildren().addAll(titolo, sottotitolo);
        titoloESottotitolo.setLayoutY(20);

        Scene scene = new Scene(new Pane(titoloESottotitolo, sezioneInserimento, areaNotifiche, sezioneRegistroEStatistiche),1920,1080);
        scene.getStylesheets().add("stile.css");
        stage.setTitle("G.A.P. - Gestione Affitti Privati");
        stage.getIcons().add(new Image("icona.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        new EventoDiNavigazioneGUI("Finestra","APERTURA", "Applicazione avviata con successo", parametri.getPreferenze().getIndirizzoIPClient()).invia();
        stage.setOnCloseRequest((WindowEvent ev) -> {
            cache.salvaInCache(sezioneInserimento.getLocale(), sezioneInserimento.getImportoInserito(), sezioneInserimento.getCheckin(), sezioneInserimento.getCheckout());
            new EventoDiNavigazioneGUI("Finestra","CHIUSURA", "Applicazione chiusa con successo", parametri.getPreferenze().getIndirizzoIPClient()).invia();
        });
    }
    
    private void aggiornaStatistiche(){ //(2) 
        sezioneRegistroEStatistiche.aggiornaStatistiche(archivio.caricaAffitto(sezioneRegistroEStatistiche.getLocale(), sezioneRegistroEStatistiche.getCheckin(), sezioneRegistroEStatistiche.getCheckout(), parametri.getPreferenze().getCodiceUtenteDelLocatore()));     
        for (final PieChart.Data data : sezioneRegistroEStatistiche.getGraficoAffittiMensili().getGraficoATorta().getData()) {
            data.getNode().setOnMouseClicked((MouseEvent e) -> {
                areaNotifiche.aggiungiMessaggio("\n" +data.getName()+": "+ Math.round(data.getPieValue())+" giorni", Color.VIOLET);
                new EventoDiNavigazioneGUI("Grafico a Torta","DETTAGLI STATISTICHE", "Notificati i dettagli", parametri.getPreferenze().getIndirizzoIPClient()).invia();
            });
        }
        for (XYChart.Series<String,Number> serie: sezioneRegistroEStatistiche.getIstogrammaRicavi().getIstogramma().getData()){
            for (XYChart.Data<String, Number> barra: serie.getData()){
                barra.getNode().setOnMouseClicked((MouseEvent e) -> {
                    areaNotifiche.aggiungiMessaggio("\n" + barra.getXValue() + ": " + barra.getYValue() + " \u20ac", Color.LIGHTBLUE);
                    new EventoDiNavigazioneGUI("Istogramma","DETTAGLI STATISTICHE", "Notificati i dettagli", parametri.getPreferenze().getIndirizzoIPClient()).invia();
                });
            }
        }
    }
    
    private void inserimentoAffitto(){//(3) 
        try{
            if(sezioneInserimento.tuttiCampiInseriti()){
                   if(archivio.inserisciAffitto(parametri.getPreferenze().getCodiceUtenteDelLocatore(), sezioneInserimento.getLocale(), sezioneInserimento.getCheckin(), sezioneInserimento.getCheckout(), sezioneInserimento.getImportoInserito())){
                    areaNotifiche.aggiungiMessaggio("\n" + "Affitto inserito con successo", Color.GREEN);
                    new EventoDiNavigazioneGUI("Bottone Inserimento Affitto","INSERISCI AFFITTO", "Affitto inserito con successo", parametri.getPreferenze().getIndirizzoIPClient()).invia();
                }else{
                    areaNotifiche.aggiungiMessaggio("\n" + "Data non valida o occupata", Color.RED);                   
                    new EventoDiNavigazioneGUI("Bottone Inserimento Affitto","INSERISCI AFFITTO", "Data occupata o non valida", parametri.getPreferenze().getIndirizzoIPClient()).invia();
                }
                aggiornaStatistiche();    
                sezioneRegistroEStatistiche.aggiornaComboBox(archivio.prelevaDistinti(parametri.getPreferenze().getCodiceUtenteDelLocatore()));
            }else{
                areaNotifiche.aggiungiMessaggio("\n" +"Inserire tutti i dati", Color.RED); 
                new EventoDiNavigazioneGUI("Bottone Inserimento Affitto","INSERISCI AFFITTO", "Alcuni dati non inseriti", parametri.getPreferenze().getIndirizzoIPClient()).invia();
            }
        }catch(NumberFormatException e){
            areaNotifiche.aggiungiMessaggio("\n" +"Affitto giornaliero non valido", Color.RED); 
            new EventoDiNavigazioneGUI("Bottone Inserimento Affitto","INSERISCI AFFITTO", "L'affitto giornaliero non e' un numero", parametri.getPreferenze().getIndirizzoIPClient()).invia();
        } 
    }
    
    private void inizializzaEventi(){//(4) 
        sezioneInserimento.getBottoneInserimentoAffitto().setOnAction((ActionEvent ev)->{
            inserimentoAffitto();
         });
        
        sezioneRegistroEStatistiche.getCheckinPicker().setOnAction((ActionEvent ev)->{
            aggiornaStatistiche();
            new EventoDiNavigazioneGUI("DataPicker Dal","AGGIORNA STATISTICHE", "Statistiche Aggiornate", parametri.getPreferenze().getIndirizzoIPClient()).invia();
        });
        
        sezioneRegistroEStatistiche.getCheckoutPicker().setOnAction((ActionEvent ev)->{
            aggiornaStatistiche();
            new EventoDiNavigazioneGUI("DataPicker Al","AGGIORNA STATISTICHE", "Statistiche Aggiornate", parametri.getPreferenze().getIndirizzoIPClient()).invia();
        });
        
        sezioneRegistroEStatistiche.getBoxSceltaLocale().setOnAction((Event ev)->{
            aggiornaStatistiche();
            new EventoDiNavigazioneGUI("ComboBox Scelta Locale","AGGIORNA STATISTICHE", "Statistiche Aggiornate", parametri.getPreferenze().getIndirizzoIPClient()).invia();
        });
        
        sezioneRegistroEStatistiche.getBottoneEliminaAffitto().setOnAction((ActionEvent ev)->{
           eliminaAffitto();
        });
        
        sezioneRegistroEStatistiche.getTabellaAffitti().getSelectionModel().selectedItemProperty().addListener((listaOssrvabile, vecchiaSelezione, nuovaSelezione) -> {
            if (nuovaSelezione != null) {
                 new EventoDiNavigazioneGUI("Tabella affitti","SELEZIONE RIGA", "Riga della tabella selezionata", parametri.getPreferenze().getIndirizzoIPClient()).invia();
            }
        });
    }
    
    private void eliminaAffitto(){ //(5) 
        try{
            Affitto selezionato = (Affitto) sezioneRegistroEStatistiche.getTabellaAffitti().getSelectionModel().getSelectedItem();
           if(archivio.cancellaPrenotazione(parametri.getPreferenze().getCodiceUtenteDelLocatore(), selezionato.getIdAffitto(), selezionato.getDataCheckin())){
                areaNotifiche.aggiungiMessaggio("\n" + "Affitto eliminato", Color.GREEN);
                new EventoDiNavigazioneGUI("\n" + "Prenotazione eliminata con successo","ELIMINA PRENOTAZIONE", "Affitto eliminato con successo", parametri.getPreferenze().getIndirizzoIPClient()).invia();
           }else{
                areaNotifiche.aggiungiMessaggio("\n" + "Slezionare una prenotazione", Color.RED);
                new EventoDiNavigazioneGUI("Bottone Elimina Prenotazione","ELIMINA PRENOTAZIONE", "Prenotazione non selezionata", parametri.getPreferenze().getIndirizzoIPClient()).invia();
           }
            aggiornaStatistiche();
        }catch(NullPointerException e){
            areaNotifiche.aggiungiMessaggio("\n" + "Selezionare la riga della prenotazione", Color.RED);
            new EventoDiNavigazioneGUI("Bottone Elimina Prenotazione","ELIMINA PRENOTAZIONE", "Riga non selezionata", parametri.getPreferenze().getIndirizzoIPClient()).invia();
        }
    }

}

/*
    1.  Inizializza gli elementi grafici, di back-end, gestisce le loro 
        interazioni e avvia l'applicazione

    2.  Aggiorna le statistiche e reimposta gli handler di queste in modo da 
        reagire alla nuova configurazione dei grafici

    3.  Gestisce il controllo e l'inserimento di un nuovo affitto facendo da 
        intermediario tra back-end e front-end

    4.  Gestisce gli handler relativi alla maggior parde degli eventi di 
        navigaione della GUI

    5.  Contolla la validità dell'operazione e nel caso elimina una prenotazione 
        dall'archivio facendo da intermediario tra back-end e front-end

                                                      Made by: Stefano Petrocchi
*/