/*CacheInserimentiNonValidati.java*/

import java.io.*;
import java.time.LocalDate;

public class CacheInserimentiNonValidati{
    
    public boolean salvaInCache(String locale, double affittoGiornaliero, LocalDate dal, LocalDate al){ //(1) 
        try( 
                FileOutputStream streamInUscita = new FileOutputStream("./cacheInserimenti.bin");
                ObjectOutputStream oggettoInUscita = new ObjectOutputStream(streamInUscita)
           ){
            oggettoInUscita.writeObject(new DatiFormInserimento(locale, affittoGiornaliero, dal, al));
            System.out.println("Cache salvata");
        }catch(IOException e){
                e.printStackTrace();
                return false;
        }
        return true;
    }
    
    public DatiFormInserimento caricaDaCache(){ //(2) 
        DatiFormInserimento datiForm = null;
        try( 
                FileInputStream streamInIngresso = new FileInputStream("./cacheInserimenti.bin");
                ObjectInputStream oggettoInIngresso = new ObjectInputStream(streamInIngresso)
           ){
            datiForm = (DatiFormInserimento) oggettoInIngresso.readObject();
            System.out.println("Prelevata cache");
        }catch(Exception e){
                e.printStackTrace();
        }
        if(datiForm == null){
            datiForm = new DatiFormInserimento("",0,LocalDate.now(),LocalDate.now());
        }
        return datiForm;
    }
}

/*
    1. Salva i valori passati nella cache 

    2. Preleva i dati dalla cache e restituisce un elemento di tipo "datiForm"

                                                      Made by: Stefano Petrocchi
*/