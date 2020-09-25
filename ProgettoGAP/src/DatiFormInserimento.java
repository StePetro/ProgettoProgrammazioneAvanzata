/*DatiFormInserimento.java*/

import java.io.Serializable;
import java.time.LocalDate;

public class DatiFormInserimento implements Serializable{
    private final String locale;
    private final double affittoGiornaliero;
    private final LocalDate dal;
    private final LocalDate al;
        
    DatiFormInserimento(String locale, double affittoGiornaliero, LocalDate dal, LocalDate al){
        this.locale = locale;
        this.affittoGiornaliero = affittoGiornaliero;
        this.dal = dal;
        this.al = al;
    }
    
    public String getLocale(){return locale;}
    public double getAffittoGiornaliero(){return affittoGiornaliero;}
    public LocalDate getDal(){return dal;}
    public LocalDate getAl(){return al;}
}

/*
    Rende disponibile i dati prelevati dalla cache alle altre classi

                                                      Made by: Stefano Petrocchi
*/