/*GraficoTortaVisualeAffittiMensili.java*/

import java.util.*;
import javafx.collections.*;
import javafx.scene.chart.*;

public class GraficoTortaVisualeAffitti{
    private final ObservableList<PieChart.Data> listaOsservabileDati;
    private final PieChart graficoATorta; //(1)
 
    GraficoTortaVisualeAffitti(){ 
        listaOsservabileDati = FXCollections.observableArrayList();
        graficoATorta = new PieChart(listaOsservabileDati);
        graficoATorta.setTitle("Giorni di Affitto");
    }
    
    public void aggiorna(List<Affitto> listaAffitti){ //(2)
        TreeMap<String,Long> mappaGiorniMensili = new TreeMap<>();
        
        for(Affitto a: listaAffitti) {
            if(mappaGiorniMensili.containsKey(a.getMeseCheckout())){
                mappaGiorniMensili.replace(a.getMeseCheckout(), mappaGiorniMensili.get(a.getMeseCheckout()) + a.getNumeroNottiMeseCheckout());
            }else{
                mappaGiorniMensili.put(a.getMeseCheckout(), a.getNumeroNottiMeseCheckout());
            }
            
            if(mappaGiorniMensili.containsKey(a.getMeseCheckin())){
                mappaGiorniMensili.replace(a.getMeseCheckin(), mappaGiorniMensili.get(a.getMeseCheckin()) + a.getNumeroNottiMeseCheckin());
            }else{
                mappaGiorniMensili.put(a.getMeseCheckin(), a.getNumeroNottiMeseCheckin());
            }
        }
        
        listaOsservabileDati.clear();
        for(String mese : mappaGiorniMensili.keySet()) {
            listaOsservabileDati.add(new PieChart.Data(mese, mappaGiorniMensili.get(mese)));
        }
    }
    
    public PieChart getGraficoATorta(){ //(3) 
        return graficoATorta;
    }
}

/*
    1. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/PieChart.html

    2. Aggiorna e calcola i dati

    3. Restitusice la PieChart

                                                      Made by: Stefano Petrocchi
*/