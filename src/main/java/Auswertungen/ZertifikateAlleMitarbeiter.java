
package Auswertungen;

import Datenbank.AuswertungenDatenbank;
import Utilities.Tabelle;

import java.util.List;


public class ZertifikateAlleMitarbeiter {

    private final String[] TITELZEILE = {"Personalnummer", "Nachname", "Vorname", "Zertifikatstitel", "Zertifikatsbeschreibung", "ZertAblDatum"};
    private int personalnummer;
    private String nachname;
    private String vorname;
    private String zertifikatstitel;
    private String zertifikatsbeschreibung;
    private String zertifikatsablaufdatum;

    public ZertifikateAlleMitarbeiter(int personalnummer, String nachname, String vorname, String zertifikatstitel, String zertifikatsbeschreibung, String zertifikatsablaufdatum) {
        this.personalnummer = personalnummer;
        this.nachname = nachname;
        this.vorname = vorname;
        this.zertifikatstitel = zertifikatstitel;
        this.zertifikatsbeschreibung = zertifikatsbeschreibung;
        this.zertifikatsablaufdatum = zertifikatsablaufdatum;
    }

    public ZertifikateAlleMitarbeiter() {
    }

    public void auswertungAusgeben() {


        List<ZertifikateAlleMitarbeiter> AusgabeZertifikateAlleMitarbeiter;


        //Aufruf Store Procedure SP_ANZEIGEN_ALLE_MA_ZERTITIFKATE

        AusgabeZertifikateAlleMitarbeiter = new AuswertungenDatenbank().storeproduceZertifikateAlleMitarbeiter();

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (ZertifikateAlleMitarbeiter zama  : AusgabeZertifikateAlleMitarbeiter) {

            tabelle.zeileHinzufuegen(zama.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
    }

    private String[] attributenArrayBefuellen() {

        String[] attributenArray = {String.valueOf(personalnummer),nachname,vorname,zertifikatstitel,zertifikatsbeschreibung,zertifikatsablaufdatum};

        return attributenArray;
    }
}
