/*RegistroEStatisticheAffitti.java*/

import java.time.LocalDate;
import java.util.List;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;

public class RegistroEStatisticheAffitti extends TitledPane{
    
    private final Button bottoneEliminaPrenotazione = new Button();
    private final TabellaVisualeAffitti tabellaAffitti;
    private final DatePicker dataDal = new DatePicker();
    private final DatePicker dataAl = new DatePicker();
    private final ComboBox sceltaLocale = new ComboBox(); //(1) 
    private final GraficoTortaVisualeAffitti graficoAffittiMensili;
    private final IstogrammaVisualeRicavi istogrammaRicavi;
    private final ObservableList listaOsservabileLocali = FXCollections.observableArrayList();
  
    RegistroEStatisticheAffitti(List<String> listaLocali){ //(2) 
        super();
        
        tabellaAffitti = new TabellaVisualeAffitti();
        graficoAffittiMensili = new GraficoTortaVisualeAffitti();
        istogrammaRicavi = new IstogrammaVisualeRicavi();
        aggiornaComboBox(listaLocali);
        
        Label labelLocale = new Label("Locale:");
        Label labelDal = new Label("Dal:");
        Label labelAl = new Label("Al:");
        labelLocale.setLayoutX(15);
        labelLocale.setLayoutY(30);
        labelDal.setLayoutX(390);
        labelDal.setLayoutY(30);
        labelAl.setLayoutX(710);
        labelAl.setLayoutY(30);
        
        sceltaLocale.setItems(listaOsservabileLocali);
        sceltaLocale.setLayoutX(95);
        sceltaLocale.setLayoutY(20);
        sceltaLocale.setValue("Tutti");
        tabellaAffitti.setLayoutX(20);
        tabellaAffitti.setLayoutY(90);
        tabellaAffitti.setPrefSize(1410, 310);
        graficoAffittiMensili.getGraficoATorta().setLayoutX(15);
        graficoAffittiMensili.getGraficoATorta().setLayoutY(425);
        graficoAffittiMensili.getGraficoATorta().setPrefSize(720, 565);
        istogrammaRicavi.getIstogramma().setLayoutX(700);
        istogrammaRicavi.getIstogramma().setLayoutY(425);
        istogrammaRicavi.getIstogramma().setPrefSize(670, 565);
        dataDal.setLayoutX(450);
        dataDal.setLayoutY(20);
        dataDal.setPrefWidth(220);
        dataDal.setValue(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        dataAl.setLayoutX(750);
        dataAl.setLayoutY(20);
        dataAl.setPrefWidth(220);
        dataAl.setValue(LocalDate.of(LocalDate.now().getYear(), 12, LocalDate.now().lengthOfMonth()));
        bottoneEliminaPrenotazione.setLayoutX(1165);
        bottoneEliminaPrenotazione.setLayoutY(20);
        bottoneEliminaPrenotazione.setText("Elimina Prenotazione");
        
        this.setAlignment(Pos.CENTER);
        this.setText("Registo e Statistiche Affitti");
        this.setCollapsible(false);
        this.setLayoutX(465);
        this.setLayoutY(15);
        this.setPrefHeight(1052);
        this.setPrefWidth(1441);
        this.setContent(new Group(tabellaAffitti, dataDal, dataAl, labelLocale, labelDal, labelAl, sceltaLocale, bottoneEliminaPrenotazione, graficoAffittiMensili.getGraficoATorta(), istogrammaRicavi.getIstogramma()));
    }
    
    public void aggiornaComboBox(List<String> listaLocali){ //(3) 
        listaLocali.add(0, "Tutti");
        listaOsservabileLocali.setAll(listaLocali);
    }
    
    public void aggiornaStatistiche(List<Affitto> listaAffitti){ //(4) 
        tabellaAffitti.aggiorna(listaAffitti);
        graficoAffittiMensili.aggiorna(listaAffitti);
        istogrammaRicavi.aggiorna(listaAffitti);
    }
    
    public Button getBottoneEliminaAffitto(){return bottoneEliminaPrenotazione;}  
    public String getLocale(){ return sceltaLocale.getValue().toString();}
    public ComboBox getBoxSceltaLocale(){ return sceltaLocale;}
    public LocalDate getCheckin(){ return dataDal.getValue();}
    public LocalDate getCheckout(){ return dataAl.getValue();}
    public DatePicker getCheckinPicker(){return dataDal;}
    public DatePicker getCheckoutPicker(){return dataAl;}
    public IstogrammaVisualeRicavi getIstogrammaRicavi(){ return istogrammaRicavi;}
    public GraficoTortaVisualeAffitti getGraficoAffittiMensili(){ return graficoAffittiMensili;}
    public TabellaVisualeAffitti getTabellaAffitti() {return tabellaAffitti;}
}

/*
    1. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ComboBox.html

    2. Inizializza e posiziona gli elementi grafici 

    3. Aggiorna la lista dei locali all'interno della ComboBox

    4. Aggiorna le informazioni dei grafici e della tabella

                                                      Made by: Stefano Petrocchi
*/