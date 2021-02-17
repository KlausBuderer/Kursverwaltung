import DatenSchicht.Datenbank;
import Logik.Benutzerverwaltung.Benutzerverwaltung;
import Logik.Einstellungen.Einstellungen;
import PraesentationSchicht.Animation;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;

public class KursverwaltungMain {

    public static void main(String[] args) {

        //Start Prozedere
        new Animation();

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

            new Benutzerverwaltung().benutzerAusDateiLesen();

        }while(!verbindungAufgebaut);

        while(true) {
            if(Benutzerverwaltung.angemeldeterBenutzer.equals("")) {
                BefehlsZeilenSchnittstelle.anmeldeFensterAusgeben();
            }
            new Benutzerverwaltung();
            BefehlsZeilenSchnittstelle.hauptmenuAufruf();

        }




    }
}
