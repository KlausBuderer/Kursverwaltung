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

        //Anmeldefenster Einblenden
        BefehlsZeilenSchnittstelle.anmeldeFensterAusgeben();


        // Verbindung zur Datenbank testen
        Datenbank datenbank = new Datenbank();
        boolean verbindungAufgebaut;

        do {
            verbindungAufgebaut = datenbank.verbindungTesten();

            if (!verbindungAufgebaut) {
                einstellungen.nachVerbindungFragen();
            }

        }while(!verbindungAufgebaut);

        while(true) {
            if(Benutzerverwaltung.angemeldeterBenutzer.equals("")) {
                BefehlsZeilenSchnittstelle.anmeldeFensterAusgeben();
            }
            BefehlsZeilenSchnittstelle.hauptmenuAufruf();
        }




    }
}
