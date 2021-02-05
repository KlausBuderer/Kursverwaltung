import Benutzerverwaltung.Benutzer;
import Datenbank.Datenbank;
import Einstellungen.Einstellungen;
import Utilities.BefehlsZeilenSchnittstelle;

public class KursverwaltungMain {

    public static void main(String[] args) {

        //Start Prozedere

       /* try {
            Animationen.kaffee();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        // Verbindungsdaten setzen
        Einstellungen einstellungen = new Einstellungen("Start");

        boolean verbindungAufgebaut;
        Datenbank datenbank = new Datenbank();

        // Verbindung zur Datenbank.Datenbank testen
        do {
            verbindungAufgebaut = datenbank.verbindungTesten();

            if (!verbindungAufgebaut) {
                einstellungen.nachVerbindungFragen();
            }

            new Benutzer().benutzerAusDateiLesen();

        }while(!verbindungAufgebaut);

        while(true) {
            if(Benutzer.angemeldeterBenutzer.equals("")) {
                BefehlsZeilenSchnittstelle.anmeldeFensterAusgeben();
            }
            Benutzer benutzer = new Benutzer();
            BefehlsZeilenSchnittstelle.hauptmenuAufruf();

        }




    }
}
