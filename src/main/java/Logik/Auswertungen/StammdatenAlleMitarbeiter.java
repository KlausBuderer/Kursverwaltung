
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import DatenSchicht.DatenLogikAuswertungen;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class StammdatenAlleMitarbeiter {

    private int personalnummer;

    private String anrede;
    private String nachname;
    private String vorname;
    private String jobtitel;
    private String geburtsdatum;
    private String statusmitarbeiter;

    private final String[] TITELZEILE = {"Personalnummer","Anrede","Nachname","Vorname","Jobtitel","Geburtsdatum","Statusmitarbeiter"};

    public StammdatenAlleMitarbeiter() {}

    public StammdatenAlleMitarbeiter(int personalnummer, String anrede, String nachname, String vorname, String jobtitel, String geburtsdatum, String statusmitarbeiter) {
        this.personalnummer = personalnummer;
        this.anrede = anrede;
        this.nachname = nachname;
        this.vorname = vorname;
        this.jobtitel = jobtitel;
        this.geburtsdatum = geburtsdatum;
        this.statusmitarbeiter = statusmitarbeiter;
    }

    public void auswertungAusgeben() {

        //Performancetest Stammdaten der Mitarbeiter Start
       // long time = System.nanoTime();

        // 100 Testläufe
        //for (int i = 0; i < 100; i++) {

            List<StammdatenAlleMitarbeiter> AusgabeStammdatenAlleMitarbeiter;


            //Aufruf Store Procedure SP_ANZEIGEN_ALLE_MA_STAMMDATEN
            DatenLogikAuswertungen auswertungen = new AuswertungenDatenbank();
            AusgabeStammdatenAlleMitarbeiter = auswertungen.storeproduceStammdatenAlleMitarbeiter();

            Tabelle tabelle = new Tabelle();
            tabelle.kopfzeileSetzen(TITELZEILE);
            tabelle.vertikaleLinieSetzen(true);
            for (StammdatenAlleMitarbeiter sam : AusgabeStammdatenAlleMitarbeiter) {

                tabelle.zeileHinzufuegen(sam.attributenArrayBefuellen());
            }
            tabelle.ausgabe();

      //  }
            //Performancetest Stammdaten der Mitarbeiter Ende
            long duration = (System.nanoTime() - AuswertungenDatenbank.time) / 1000000; // stopp
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Performancemessung: " + duration + " Millisekunden");
            BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
        }

    private String[] attributenArrayBefuellen() {

        String[] attributenArray = {String.valueOf(personalnummer),anrede ,nachname, vorname, jobtitel, geburtsdatum, statusmitarbeiter};

        return attributenArray;
    }
}
