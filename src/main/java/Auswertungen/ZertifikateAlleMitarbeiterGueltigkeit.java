
package Auswertungen;

import Datenbank.AuswertungenDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.util.List;


public class ZertifikateAlleMitarbeiterGueltigkeit {

    private final String[] TITELZEILE = {"nachname","vorname","zertAblDatum","zertifikatstitel","zertifikatsbeschreibung"};
    private String nachname;
    private String vorname;
    private String zertifikatsablaufdatum;
    private String zertifikatstitel;
    private String zertifikatsbeschreibung;

    public ZertifikateAlleMitarbeiterGueltigkeit(String nachname, String vorname, String zertifikatsablaufdatum, String zertifikatstitel, String zertifikatsbeschreibung) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.zertifikatsablaufdatum = zertifikatsablaufdatum;
        this.zertifikatstitel = zertifikatstitel;
        this.zertifikatsbeschreibung = zertifikatsbeschreibung;

    }

    public ZertifikateAlleMitarbeiterGueltigkeit() {

    }
    // Abfrage Datum

    public void auswertungAusgeben() {

        String datumVon;
        String datumBis;

        List<ZertifikateAlleMitarbeiterGueltigkeit> AusgabeZertifikateAlleMitarbeiterGueltigkeit;

        datumVon = " ";
        datumBis = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie ein Datum ein bis wann Sie die Gueltigkeit der Zertifikate in Zukunft pruefen wollen: ");


        //Aufruf Store Procedure SP_ANZEIGEN_NICHT_GUELTIG_ALLE_MA_ZERT

        AusgabeZertifikateAlleMitarbeiterGueltigkeit = new AuswertungenDatenbank().storeproduceZertifikateAlleMitarbeiterGueltigkeit(datumVon,datumBis);

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (ZertifikateAlleMitarbeiterGueltigkeit zamg : AusgabeZertifikateAlleMitarbeiterGueltigkeit) {

            tabelle.zeileHinzufuegen(zamg.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
         }
    private String[] attributenArrayBefuellen() {
        String[] attributenArray = {nachname,vorname,zertifikatsablaufdatum,zertifikatstitel,zertifikatsbeschreibung};

        return attributenArray;

    }
}
