
package Auswertungen;

import Datenbank.AuswertungenDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.util.List;


public class KursProMitarbeiter {


    // Feldliste in Datenbank
    // MitarbeiterID, Nachname, Vorname, Kurscode, Kursbeschreibung, Anbieter, Kosten, Waehrung


    private final String[] TITELZEILE = {"MitarbeiterID","Nachname","Vorname","Kurscode","Kursbeschreibung","Anbieter","Kosten","Waehrung"};
    private int mitarbeiterID;
    private String nachname;
    private String vorname;
    private String kurscode;
    private String kursbeschreibung;
    private String anbieter;
    private int kosten;
    private String waehrung;


    public KursProMitarbeiter(int mitarbeiterID, String nachname, String vorname, String kurscode, String kursbeschreibung, String anbieter, int kosten, String waehrung) {
        this.mitarbeiterID = mitarbeiterID;
        this.nachname = nachname;
        this.vorname = vorname;
        this.kurscode = kurscode;
        this.kursbeschreibung = kursbeschreibung;
        this.kosten = kosten;
        this.waehrung = waehrung;

    }

    public KursProMitarbeiter() {

    }
    // Abfrage Datum

    public void auswertungAusgeben() {
        int mitarbeiterIdentification;

        List<KursProMitarbeiter> AusgabeKursProMitarbeiter;

        mitarbeiterIdentification = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Bitte MitarbeiterID eingeben");

        //Aufruf Store Procedure SP_ANZEIGEN_MA_KURSE

        AusgabeKursProMitarbeiter = new AuswertungenDatenbank().storeproduceKursProMitarbeiter("bitte Mitarbeiter ID angeben");

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (KursProMitarbeiter akpm : AusgabeKursProMitarbeiter) {

            tabelle.zeileHinzufuegen(akpm.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
         }
    private String[] attributenArrayBefuellen() {
        String[] attributenArray = {String.valueOf(mitarbeiterID), nachname, vorname, kurscode, kursbeschreibung, anbieter, String.valueOf(kosten), waehrung};

        return attributenArray;

    }
}
