/*Affitto.java*/

import java.time.*;//(1)
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import javafx.beans.property.*;

public class Affitto {
    private final SimpleLongProperty idAffitto;
    private final SimpleStringProperty locale;
    private final SimpleStringProperty stato;
    private final SimpleStringProperty checkin;
    private final SimpleStringProperty checkout;
    private final SimpleStringProperty affittoGiornaliero;
    private final SimpleStringProperty incassoTotale;
    private final SimpleStringProperty tasse;
    private final SimpleStringProperty guadagnoNetto;
    private double valoreIncassoTotale;
    private final double valoreTasse;
    private final double valoreGuadagnoNetto;
    private final LocalDate dataCheckout;
    private final LocalDate dataCheckin;
    private final long numeroNotti;
    private final long numeroNottiMeseCheckin;
    private final long numeroNottiMeseCheckout;
    private final String formattazione;
                                                               
    Affitto(long idAffitto, String locale, LocalDate checkin, LocalDate checkout, double affittoGiornaliero, double percentualeTasse, String formattazione){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy"); //(2)

        this.idAffitto = new SimpleLongProperty(idAffitto);
        this.locale = new SimpleStringProperty(locale);
        dataCheckout = checkout;
        this.checkin = new SimpleStringProperty(checkin.format(formatter));
        dataCheckin = checkin;
        this.checkout = new SimpleStringProperty(checkout.format(formatter));
        this.affittoGiornaliero = new SimpleStringProperty(Double.toString(affittoGiornaliero)+" \u20ac");
    
        LocalDate ora = LocalDate.now(); //(3)
        numeroNotti = DAYS.between(checkin, checkout);
        numeroNottiMeseCheckin = DAYS.between(checkin, LocalDate.of(checkout.getYear(), checkout.getMonth(), 1));
        numeroNottiMeseCheckout = numeroNotti - numeroNottiMeseCheckin;
        valoreIncassoTotale = (affittoGiornaliero*numeroNotti);
        this.incassoTotale = new SimpleStringProperty(Double.toString(valoreIncassoTotale)+" \u20ac");
        
        if(ora.isAfter(checkout)){ //(4)
            this.stato = new SimpleStringProperty("Passato");
        }else{
            if(ora.isAfter(checkin) && checkout.isAfter(ora)){
                this.stato = new SimpleStringProperty("In Corso");
                valoreIncassoTotale = 0;
            }else{
                this.stato = new SimpleStringProperty("Prenotato");
                valoreIncassoTotale = 0;
            }
        }
        
        if(this.stato.get().equals("Passato")){ //(5)
            valoreTasse = valoreIncassoTotale*percentualeTasse;
            valoreGuadagnoNetto = valoreIncassoTotale - valoreTasse;
            this.tasse = new SimpleStringProperty(Double.toString(valoreTasse)+" \u20ac");
            this.guadagnoNetto = new SimpleStringProperty(Double.toString(valoreGuadagnoNetto)+" \u20ac");
        }else{
            valoreTasse = 0;
            valoreGuadagnoNetto = 0;
            this.tasse = new SimpleStringProperty("-");
            this.guadagnoNetto = new SimpleStringProperty("-");
        }
        this.formattazione = formattazione;//(6)
    }
    
    public String getLocale(){return locale.get();} 
    public long getIdAffitto(){return idAffitto.get();} 
    public String getStato(){return stato.get();} 
    public String getCheckin(){return checkin.get();} 
    public String getCheckout(){return checkout.get();} 
    public String getAffittoGiornaliero(){return affittoGiornaliero.get();} 
    public String getIncassoTotale(){return incassoTotale.get();} 
    public String getTasse(){return tasse.get();} 
    public String getGuadagnoNetto(){return guadagnoNetto.get();}
    public double getValoreIncassoTotale() {return valoreIncassoTotale;}
    public double getValoreTasse() {return valoreTasse;}
    public double getValoreGuadagnoNetto() {return valoreGuadagnoNetto;}
    public String getMeseCheckout() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formattazione); 
        return (dataCheckout.format(formatter));
    }
    public String getMeseCheckin() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formattazione);
        return (dataCheckin.format(formatter));
    }
    public long getNumeroNotti() {return numeroNotti;}
    public long getNumeroNottiMeseCheckin() {return numeroNottiMeseCheckin;}
    public long getNumeroNottiMeseCheckout() {return numeroNottiMeseCheckout;}
    public LocalDate getDataCheckin() {return dataCheckin;}

}

/*
    1. https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html

    2. Formatta la data nel formato indicato

    3. Data di oggi, calcolo del numero di notti e dell'incasso totale

    4. Calcolo se il periodo d'affitto è passato, in corso o prenotato

    5. Calcolo dei valori finanziari  

    6. Serve a formattare i valori dei grafici

                                                      Made by: Stefano Petrocchi
*/
