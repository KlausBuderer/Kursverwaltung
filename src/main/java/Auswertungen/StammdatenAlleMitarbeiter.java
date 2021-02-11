
package Auswertungen;

import Datenbank.AuswertungenDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.util.List;


public class StammdatenAlleMitarbeiter {

    private final String[] TITELZEILE = {"Personalnummer","Anrede","Nachname","Vorname","Jobtitel","Geburtsdatum","Statusmitarbeiter"};
    private int personalnummer;
    private String anrede;
    private String nachname;
    private String vorname;
    private String jobtitel;
    private String geburtsdatum;
    private String statusmitarbeiter;

    public StammdatenAlleMitarbeiter(int personalnummer, String anrede, String nachname, String vorname, String jobtitel, String geburtsdatum, String statusmitarbeiter) {
        this.personalnummer = personalnummer;
        this.anrede = anrede;
        this.nachname = nachname;
        this.vorname = vorname;
        this.jobtitel = jobtitel;
        this.geburtsdatum = geburtsdatum;
        this.statusmitarbeiter = statusmitarbeiter;
    }

    public StammdatenAlleMitarbeiter() {
    }

    public void auswertungAusgeben() {


        List<StammdatenAlleMitarbeiter> AusgabeStammdatenAlleMitarbeiter;


        //Aufruf Store Procedure SP_ANZEIGEN_ALLE_MA_STAMMDATEN

        AusgabeStammdatenAlleMitarbeiter = new AuswertungenDatenbank().storeproduceStammdatenAlleMitarbeiter();

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (StammdatenAlleMitarbeiter sam  : AusgabeStammdatenAlleMitarbeiter) {

            tabelle.zeileHinzufuegen(sam.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
    }

    private String[] attributenArrayBefuellen() {

        String[] attributenArray = {String.valueOf(personalnummer),anrede ,nachname, vorname, jobtitel, geburtsdatum, statusmitarbeiter};

        return attributenArray;
    }
}
