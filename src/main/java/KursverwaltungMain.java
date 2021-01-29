import Benutzerverwaltung.Benutzer;
import Datenbank.Datenbank;
import Einstellungen.Einstellungen;
import Utilities.BefehlsZeilenSchnittstelle;

public class KursverwaltungMain {

    public static void main(String[] args) {

        //Start Prozedere

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
            BefehlsZeilenSchnittstelle.anmeldeFensterAusgeben();
            BefehlsZeilenSchnittstelle.hauptmenuAufruf();
        }




    }
}
