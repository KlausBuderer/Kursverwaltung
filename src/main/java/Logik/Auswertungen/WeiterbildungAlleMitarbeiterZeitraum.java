
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import DatenSchicht.DatenLogikAuswertungen;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class WeiterbildungAlleMitarbeiterZeitraum {

    private int kosten;

    private String anrede;
    private String nachname;
    private String vorname;
    private String kennung;
    private String kursbeschreibung;
    private String waehrung;
    private String zertifikatsablaufdatum;

    private final String[] TITELZEILE = {"Anrede", "Nachname", "Vorname", "Kennung", "Kursbeschreibung", "KOSTEN", "Währung", "Zertifikatsablaufdatum"};

    public WeiterbildungAlleMitarbeiterZeitraum() {}

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

    public void auswertungAusgeben() {
        String datumVon;
        String datumBis;
        String kostenstelle;

        List<WeiterbildungAlleMitarbeiterZeitraum> AusgabeWeiterbildungAlleMitarbeiterZeitraum;
        // Abfrage Datum
        datumVon = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie das Startdatum des gewünschten Auswertungszeitraum ein: ");
        datumBis = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie das Enddatum des gewünschten Auswertungszeitraum ein: ");

        //Aufruf Store Procedure SP_ANZEIGEN_ALLE_MA_WEITERBILDUNG_DATUM
        DatenLogikAuswertungen auswertungen = new AuswertungenDatenbank();
        AusgabeWeiterbildungAlleMitarbeiterZeitraum = auswertungen.storeproduceWeiterbildungAlleMitarbeiterZeitraum(datumVon, datumBis);

        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TITELZEILE);
        tabelle.vertikaleLinieSetzen(true);
        for (WeiterbildungAlleMitarbeiterZeitraum wbamz : AusgabeWeiterbildungAlleMitarbeiterZeitraum) {

            tabelle.zeileHinzufuegen(wbamz.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
         }

    private String[] attributenArrayBefuellen() {
        String[] attributenArray = {anrede,nachname,vorname,kennung,kursbeschreibung,String.valueOf(kosten),waehrung,zertifikatsablaufdatum};

        return attributenArray;

    }
}
