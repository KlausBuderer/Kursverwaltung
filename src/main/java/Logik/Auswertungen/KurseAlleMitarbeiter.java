
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class KurseAlleMitarbeiter {

    private final String[] TITELZEILE = {"MitarbeiterID","Nachname","Vorname","Kurscode","Kursbeschreibung","Anbieter"};
    private int personalnummer;
    private String nachname;
    private String vorname;
    private String kurscode;
    private String kursbeschreibung;
    private String anbieter;

    public KurseAlleMitarbeiter(int personalnummer, String nachname, String vorname, String kurscode, String kursbeschreibung,String anbieter) {
        this.personalnummer = personalnummer;
        this.nachname = nachname;
        this.vorname = vorname;
        this.kurscode = kurscode;
        this.kursbeschreibung = kursbeschreibung;
        this.anbieter = anbieter;
    }

    public KurseAlleMitarbeiter() {
    }

    public void auswertungAusgeben() {


        List<KurseAlleMitarbeiter> AusgabeKurseAlleMitarbeiter;


        //Aufruf Store Procedure SP_ANZEIGEN_ALLE_MA_ZERTITIFKATE

        AusgabeKurseAlleMitarbeiter = new AuswertungenDatenbank().storeproduceKurseAlleMitarbeiter();

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (KurseAlleMitarbeiter kama  : AusgabeKurseAlleMitarbeiter) {

            tabelle.zeileHinzufuegen(kama.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
    }

    private String[] attributenArrayBefuellen() {

        String[] attributenArray = {String.valueOf(personalnummer),nachname,vorname,kurscode,kursbeschreibung,anbieter};

        return attributenArray;
    }
}
