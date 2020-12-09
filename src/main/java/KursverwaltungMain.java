import java.io.File;

public class KursverwaltungMain {

    public static void main(String[] args) {

        //Start Prozedere

        // Verbindungsdaten setzen
        Einstellungen einstellungen = new Einstellungen("Start");

        boolean verbindungAufgebaut;
        Datenbank datenbank = new Datenbank();

        // Verbindung zur Datenbank testen
        do {
            verbindungAufgebaut = datenbank.verbindungTesten();

            if (!verbindungAufgebaut) {
                einstellungen.nachVerbindungFragen();
            }
            ;
        }while(!verbindungAufgebaut);


        while(true) {
            BefehlsZeilenSchnittstelle.hauptmenueAusgeben();

        }




    }
}
