
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;

public class ZertifikateAlleMitarbeiterGueltigkeit {

    private final String[] TITELZEILE = {"nachname","vorname","zertAblDatum","zertifikatstitel","zertifikatsbeschreibung"};

    private String nachname;
    private String vorname;
    private String zertifikatsablaufdatum;
    private String zertifikatstitel;
    private String zertifikatsbeschreibung;

    public ZertifikateAlleMitarbeiterGueltigkeit() {}

    public ZertifikateAlleMitarbeiterGueltigkeit(String nachname, String vorname, String zertifikatsablaufdatum, String zertifikatstitel, String zertifikatsbeschreibung) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.zertifikatsablaufdatum = zertifikatsablaufdatum;
        this.zertifikatstitel = zertifikatstitel;
        this.zertifikatsbeschreibung = zertifikatsbeschreibung;
    }

    public void auswertungAusgeben() {

        String datumBis;

        List<ZertifikateAlleMitarbeiterGueltigkeit> AusgabeZertifikateAlleMitarbeiterGueltigkeit;

        // Abfrage Datum
        datumBis = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie ein Datum ein bis wann Sie die Gültigkeit der Zertifikate in Zukunft prüfen wollen: ");


        //Aufruf Store Procedure SP_ANZEIGEN_NICHT_GUELTIG_ALLE_MA_ZERT

        AusgabeZertifikateAlleMitarbeiterGueltigkeit = new AuswertungenDatenbank().storeproduceZertifikateAlleMitarbeiterGueltigkeit(datumBis);

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (ZertifikateAlleMitarbeiterGueltigkeit zamg : AusgabeZertifikateAlleMitarbeiterGueltigkeit) {

            tabelle.zeileHinzufuegen(zamg.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
         }

    private String[] attributenArrayBefuellen() {
        String[] attributenArray = {nachname,vorname,zertifikatsablaufdatum,zertifikatstitel,zertifikatsbeschreibung};

        return attributenArray;

    }
}
