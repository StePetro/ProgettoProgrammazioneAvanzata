/*IstogrammaVisualeRicavi.java*/

import java.util.*;
import javafx.collections.*;
import javafx.scene.chart.*;
 
public class IstogrammaVisualeRicavi{
    final private CategoryAxis asseX = new CategoryAxis();
    final private NumberAxis asseY = new NumberAxis();
    final private BarChart<String,Number> istogramma = new BarChart<>(asseX,asseY); //(1) 
    private final ObservableList<String> listaOsservabileAsseX = FXCollections.observableArrayList();
    
    IstogrammaVisualeRicavi(){
        istogramma.setTitle("Istogramma dei Ricavi");
        asseX.setLabel("Mese");       
        asseY.setLabel("Valore (\u20ac)");  
    }
    
    public void aggiorna(List<Affitto> listaAffitti){ //(2) 
         istogramma.setData(ricavaDati(listaAffitti));
         asseX.setCategories(listaOsservabileAsseX);
    }
    
    public BarChart<String,Number> getIstogramma(){ //(3) 
        return istogramma;
    }
    
    private ObservableList<XYChart.Series<String, Number>> ricavaDati(List<Affitto> listaAffitti) { //(4) 
        ObservableList<XYChart.Series<String, Number>> listaOsservabileRicaviMensili = FXCollections.observableArrayList();
        
        XYChart.Series serieAffitti = new XYChart.Series();
        serieAffitti.setName("Affitti");
        
        XYChart.Series serieTasse = new XYChart.Series();
        serieTasse.setName("Tasse");

        XYChart.Series serieGuadagnoNetto = new XYChart.Series();
        serieGuadagnoNetto.setName("Guadagno Netto"); 
        
        TreeMap<String,Double> mappaIncassoTotale = new TreeMap<>();
        TreeMap<String,Double> mappaTasse = new TreeMap<>();
        TreeMap<String,Double> mappaGuadagnoNetto = new TreeMap<>();
        ArrayList<String> listaAsseX = new ArrayList();
        
        for(Affitto a: listaAffitti) {
            if(mappaIncassoTotale.containsKey(a.getMeseCheckout())){
                mappaIncassoTotale.replace(a.getMeseCheckout(), mappaIncassoTotale.get(a.getMeseCheckout()) + a.getValoreIncassoTotale());
                mappaTasse.replace(a.getMeseCheckout(), mappaTasse.get(a.getMeseCheckout()) + a.getValoreTasse());
                mappaGuadagnoNetto.replace(a.getMeseCheckout(), mappaGuadagnoNetto.get(a.getMeseCheckout()) + a.getValoreGuadagnoNetto());
            }else{
                mappaIncassoTotale.put(a.getMeseCheckout(), a.getValoreIncassoTotale());
                mappaTasse.put(a.getMeseCheckout(), a.getValoreTasse());
                mappaGuadagnoNetto.put(a.getMeseCheckout(), a.getValoreGuadagnoNetto());
                listaAsseX.add(a.getMeseCheckout());
            }
        }
        
        for(String mese : mappaIncassoTotale.keySet()) {
            serieAffitti.getData().add(new XYChart.Data(mese,mappaIncassoTotale.get(mese)));
            serieTasse.getData().add(new XYChart.Data(mese,mappaTasse.get(mese)));
            serieGuadagnoNetto.getData().add(new XYChart.Data(mese,mappaGuadagnoNetto.get(mese)));
        }
        
        listaOsservabileAsseX.clear(); //(5) 
        listaOsservabileAsseX.addAll(listaAsseX);
        
        listaOsservabileRicaviMensili.addAll(serieAffitti, serieTasse, serieGuadagnoNetto);
        return listaOsservabileRicaviMensili;
    }

}

/*
    1. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/BarChart.html

    2. Aggiorna i dati

    3. Restituisce il BarChart 

    4. Calcola i dati a partire da elementi da una lista di elementi di tipo Affitto

    5. I valori relativi all'asse x devono essere aggiornati

                                                      Made by: Stefano Petrocchi
*/