/*Configurazione.java*/

import java.io.Serializable;

 public class Configurazione implements Serializable{
    private Preferenze preferenze;
    private DBMS dbms;
    private ServerLog serverLog;

    public Preferenze getPreferenze(){return preferenze;}
    public DBMS getDBMS(){return dbms;}
    public ServerLog getServerLog(){return serverLog;}

    public class Preferenze{
        private String indirizzoIP;
        private double percentualeTasse;
        private int numeroDiRigheVisibiliNellaTabella;
        private long codiceUtenteDelLocatore;
        private String formattazioneGrafici;
        private String gestisciSoloPrenotazioni;

        public String getIndirizzoIPClient(){return indirizzoIP;}
        public double getPercentualeTasse(){return percentualeTasse;}
        public int getNumeroDiRigheVisibiliNellaTabella(){return numeroDiRigheVisibiliNellaTabella;}
        public long getCodiceUtenteDelLocatore(){return codiceUtenteDelLocatore;}
        public String getFormattazione(){return formattazioneGrafici;}
        public String soloPrenotazioni(){return gestisciSoloPrenotazioni;}
    }

    public class DBMS{
        private int porta;
        private String username;
        private String password;
        private String indirizzoIP;

        public int getPorta(){return porta;}
        public String getUsername(){return username;}
        public String getPassword(){return password;}
        public String getIndirizzoIP(){return indirizzoIP;}
    }

    public class ServerLog{
        private int porta;
        private String indirizzoIP;

        public int getPorta(){return porta;}
        public String getIndirizzoIP(){return indirizzoIP;}
    }
}

/*  
    serve a rendere disponibile i dati deserializzati, prelevati dal file di 
    configurazione, alle altre classi

                                                      Made by: Stefano Petrocchi
*/