/*ArchivioAffittiESpese.java*/

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ArchivioAffitti {
    
    private static Connection connessioneArchivio;
    private static PreparedStatement prelevaAffitti;
    private static PreparedStatement prelevaAffittiTutti;
    private static PreparedStatement inserimentoAffitto;
    private static PreparedStatement eliminazionePrenotazione;
    private static PreparedStatement prelevaAffittiDistinti;
    private static String formattazione;
    private static String gestisciSoloPrenotazioni;
    private static Double percentualeTasse;
    
    ArchivioAffitti(Configurazione parametri){ //(1) 
        try { 
                connessioneArchivio = DriverManager.getConnection("jdbc:mysql://"+parametri.getDBMS().getIndirizzoIP()+":"+parametri.getDBMS().getPorta()+"/archivio_gap", parametri.getDBMS().getUsername(),parametri.getDBMS().getPassword());
                prelevaAffitti = connessioneArchivio.prepareStatement("SELECT* FROM archivioaffitti WHERE codiceUtente = ? AND locale = ? AND checkout > ? AND checkin < ? ORDER BY checkin LIMIT " + parametri.getPreferenze().getNumeroDiRigheVisibiliNellaTabella()); //considero tutti gli affitti che si svolgono anche solo in parte nel periodo selezionato
                prelevaAffittiTutti = connessioneArchivio.prepareStatement("SELECT* FROM archivioaffitti WHERE codiceUtente = ? AND checkout > ? AND checkin < ? ORDER BY checkin LIMIT " + parametri.getPreferenze().getNumeroDiRigheVisibiliNellaTabella());
                inserimentoAffitto = connessioneArchivio.prepareStatement("INSERT INTO archivioaffitti VALUE (NULL,?,?,?,?,?,?)");
                eliminazionePrenotazione = connessioneArchivio.prepareStatement("DELETE FROM archivioaffitti WHERE codiceUtente = ? AND idAffitto = ?");
                prelevaAffittiDistinti = connessioneArchivio.prepareStatement("SELECT DISTINCT locale FROM archivioaffitti WHERE codiceUtente = ?");            
        } catch (SQLException e) {System.err.println(e.getMessage());} 
        formattazione = parametri.getPreferenze().getFormattazione(); //(2) 
        gestisciSoloPrenotazioni = parametri.getPreferenze().soloPrenotazioni();
        percentualeTasse = parametri.getPreferenze().getPercentualeTasse();
    }
    
    public List<Affitto> caricaAffitto(String locale, LocalDate da, LocalDate a, long codiceUtente){ //(3) 
        List<Affitto> listaAffitti = new ArrayList<>();
        try{
            ResultSet rs = null;
            if(locale.compareTo("Tutti") == 0){
                prelevaAffittiTutti.setLong(1, codiceUtente);
                prelevaAffittiTutti.setDate(2, java.sql.Date.valueOf(da));
                prelevaAffittiTutti.setDate(3, java.sql.Date.valueOf(a));
                rs = prelevaAffittiTutti.executeQuery();
            }else{
                prelevaAffitti.setLong(1, codiceUtente);
                prelevaAffitti.setString(2, locale);
                prelevaAffitti.setDate(3, java.sql.Date.valueOf(da));
                prelevaAffitti.setDate(4, java.sql.Date.valueOf(a)); 
                rs = prelevaAffitti.executeQuery();
            }
            
            while(rs.next()){
                listaAffitti.add(new Affitto(rs.getLong("idAffitto"),rs.getString("locale"), rs.getDate("checkin").toLocalDate(), rs.getDate("checkout").toLocalDate(), rs.getDouble("affittoGiornaliero"), rs.getDouble("percentualeTasse"), formattazione));
            }
            System.out.println("Prelevati affitti");    
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return listaAffitti;
    }
    
    public List<String> prelevaDistinti(long codiceUtente){ //(4) 
        List<String> listaLocali= new ArrayList<String>();
        try{
            ResultSet rs = null;
            prelevaAffittiDistinti.setLong(1, codiceUtente);
            rs = prelevaAffittiDistinti.executeQuery();           
            while(rs.next()){
                listaLocali.add(rs.getString("locale"));
            }
            System.out.println("Prelevata lista locali");    
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return listaLocali;
    }
    
    public boolean inserisciAffitto(long codiceUtente, String locale, LocalDate dal, LocalDate al, double affittoGiornaliero){ //(5) 
        boolean successo = true;
        try{
            if(dal.isBefore(al) && prenotazione(dal) && periodoLibero(codiceUtente, locale, dal, al)){
                inserimentoAffitto.setLong(1, codiceUtente);
                inserimentoAffitto.setString(2, locale);
                inserimentoAffitto.setDate(3, java.sql.Date.valueOf(dal));
                inserimentoAffitto.setDate(4, java.sql.Date.valueOf(al)); 
                inserimentoAffitto.setDouble(5, affittoGiornaliero);
                inserimentoAffitto.setDouble(6, percentualeTasse);
                System.out.println(inserimentoAffitto.executeUpdate() + " riga inserita");
            }else{
                successo = false;
            }           
        }catch(SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return successo;
    }
    
    public boolean cancellaPrenotazione(long codiceUtente, long idAffitto, LocalDate checkin){ //(6) 
        boolean successo = true;
        try{
            if(prenotazione(checkin)){
                eliminazionePrenotazione.setLong(1, codiceUtente);
                eliminazionePrenotazione.setLong(2, idAffitto);
                System.out.println(eliminazionePrenotazione.executeUpdate() + " riga eliminata");
            }else{
                successo = false;
            }           
        }catch(SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return successo;
    }
    
    private boolean periodoLibero(long codiceUtente, String locale, LocalDate dal, LocalDate al){ //(7) 
        
        boolean successo = true;
        try{
            ResultSet rs = null;
            prelevaAffitti.setLong(1, codiceUtente);
            prelevaAffitti.setString(2, locale);
            prelevaAffitti.setDate(3, java.sql.Date.valueOf(dal));
            prelevaAffitti.setDate(4, java.sql.Date.valueOf(al)); 
            rs = prelevaAffitti.executeQuery();
            if(rs.next()){
                successo = false;
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return successo;
    }
    
    private boolean prenotazione(LocalDate checkin){ //(8) 
        if(!gestisciSoloPrenotazioni.equals("si")){
            return true;
        }
        
        LocalDate ora = LocalDate.now();
        return ora.isBefore(checkin);
    }
}

/*
    1.  Creazione della connessione e inizializzazione degli statement che 
        vengono riutilizzati ad ogni interrogazione

    2.  Inizializzazione dei parametri

    3.  Preleva i dati degli affitti dal db

    4.  Preleva la lista dei locali per il combobox della sezione statistiche

    5.  Inserisce un nuovo affitto nell'archivio

    6.  Cancella una prenotazione dall'archivio

    7.  Controlla se il periodo selezionato per quel locale è libero

    8.  Controlla che l'affitto che sta per essere eliminato sia una prenotazione

                                                      Made by: Stefano Petrocchi
*/