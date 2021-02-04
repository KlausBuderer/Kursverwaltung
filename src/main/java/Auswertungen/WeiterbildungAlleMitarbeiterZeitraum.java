/**
 * @author: Bruno Thurnherr <bruno.thurnherr@it-wissen.ch>
 * @version: 1.0
 */
package Auswertungen;

import Datenbank.AuswertungenDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.util.List;


public class WeiterbildungAlleMitarbeiterZeitraum {

    private final String[] TITELZEILE = {"Anrede", "Nachname", "Vorname", "Kennung", "Kursbeschreibung", "KOSTEN", "Waehrung", "Zertifikatsablaufdatum"};
    private String anrede;
    private String nachname;
    private String vorname;
    private String kennung;
    private String kursbeschreibung;
    private int kosten;
    private String waehrung;
    private String zertifikatsablaufdatum;

    public WeiterbildungAlleMitarbeiterZeitraum(String anrede, String nachname, String vorname, String kennung, String kursbeschreibung, int kosten, String waehrung, String zertifikatsablaufdatum) {
        this.anrede = anrede;
        this.nachname = nachname;
        this.vorname = vorname;
        this.kennung = kennung;
        this.kursbeschreibung = kursbeschreibung;
        this.kosten = kosten;
        this.waehrung = waehrung;
        this.zertifikatsablaufdatum = zertifikatsablaufdatum;

    }

    public WeiterbildungAlleMitarbeiterZeitraum() {

    }
    // Abfrage Datum

    public void auswertungAusgeben() {
        String datumVon;
        String datumBis;
        List<WeiterbildungAlleMitarbeiterZeitraum> AusgabeWeiterbildungAlleMitarbeiterZeitraum;

        datumVon = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie das Startdatum des gewuenschten Auswertungszeitraum ein: ");
        datumBis = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie das Enddatum des gewuenschten Auswertungszeitraum ein: ");

        //Aufruf Store Procedure SP_ANZEIGEN_ALLE_MA_WEITERBILDUNG_DATUM

        AusgabeWeiterbildungAlleMitarbeiterZeitraum = new AuswertungenDatenbank().storeproduceWeiterbildungAlleMitarbeiterZeitraum(datumVon, datumBis);

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (WeiterbildungAlleMitarbeiterZeitraum wbamz : AusgabeWeiterbildungAlleMitarbeiterZeitraum) {

            tabelle.zeileHinzufuegen(wbamz.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
         }
    private String[] attributenArrayBefuellen() {
        String[] attributenArray = {anrede,nachname,vorname,kennung,kursbeschreibung,String.valueOf(kosten),waehrung,zertifikatsablaufdatum};

        return attributenArray;

    }
}
