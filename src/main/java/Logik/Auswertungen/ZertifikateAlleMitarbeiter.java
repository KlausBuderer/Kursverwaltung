
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import DatenSchicht.DatenLogikAuswertungen;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class ZertifikateAlleMitarbeiter {

    private int personalnummer;

    private String nachname;
    private String vorname;
    private String zertifikatstitel;
    private String zertifikatsbeschreibung;
    private String zertifikatsablaufdatum;

    private final String[] TITELZEILE = {"Personalnummer", "Nachname", "Vorname", "Zertifikatstitel", "Zertifikatsbeschreibung", "ZertAblDatum"};

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
        DatenLogikAuswertungen auswertungen = new AuswertungenDatenbank();
        AusgabeZertifikateAlleMitarbeiter = auswertungen.storeproduceZertifikateAlleMitarbeiter();

        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TITELZEILE);
        tabelle.vertikaleLinieSetzen(true);
        for (ZertifikateAlleMitarbeiter zama  : AusgabeZertifikateAlleMitarbeiter) {

            tabelle.zeileHinzufuegen(zama.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
    }

    private String[] attributenArrayBefuellen() {

        String[] attributenArray = {String.valueOf(personalnummer),nachname,vorname,zertifikatstitel,zertifikatsbeschreibung,zertifikatsablaufdatum};

        return attributenArray;
    }
}
