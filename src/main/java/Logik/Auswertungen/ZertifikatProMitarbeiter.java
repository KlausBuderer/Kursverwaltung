package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import DatenSchicht.DatenLogikAuswertungen;
import Logik.Mitarbeiter.Mitarbeiter;
import Logik.Mitarbeiter.MitarbeiterSuche;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.HashMap;
import java.util.Map;


public class ZertifikatProMitarbeiter {

    private int mitarbeiterID;

    private String nachname;
    private String vorname;
    private String zertAblDatum;
    private String zertifikatstitel;
    private String zertifikatsbeschreibung;
    // Feldliste in Datenbank
    // MitarbeiterID,Nachname,Vorname,ZertAblDatum,Zertifikatstitel,Zertifikatsbeschreibung
    private final String[] TITELZEILE = {"MitarbeiterID","Nachname","Vorname","ZertAblDatum","Zertifikatstitel","Zertifikatsbeschreibung"};

    public ZertifikatProMitarbeiter() {

    }

    public ZertifikatProMitarbeiter(int mitarbeiterID, String nachname, String vorname, String zertAblDatum, String zertifikatstitel, String zertifikatsbeschreibung) {
        this.mitarbeiterID = mitarbeiterID;
        this.nachname = nachname;
        this.vorname = vorname;
        this.zertAblDatum = zertAblDatum;
        this.zertifikatstitel = zertifikatstitel;
        this.zertifikatsbeschreibung = zertifikatsbeschreibung;

    }

    public void auswertungAusgeben() {

        Mitarbeiter mitarbeiterIdentification;

        HashMap<ZertifikatProMitarbeiter, Integer> AusgabeZertifikatProMitarbeiter;

        mitarbeiterIdentification = new MitarbeiterSuche().mitarbeiterSuchen();


        //Aufruf Store Procedure SP_ANZEIGEN_MA_ZERT
        DatenLogikAuswertungen auswertungen = new AuswertungenDatenbank();
        AusgabeZertifikatProMitarbeiter = auswertungen.storeproduceZertifikatproMitarbeiter(mitarbeiterIdentification.getMitarbeiterId());


        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TITELZEILE);
        tabelle.vertikaleLinieSetzen(true);

        for (Map.Entry<ZertifikatProMitarbeiter, Integer> map : AusgabeZertifikatProMitarbeiter.entrySet()) {

            tabelle.zeileHinzufuegen(map.getKey().attributenArrayBefuellen());
        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
    }

    private String[] attributenArrayBefuellen () {
            String[] attributenArray = {String.valueOf(mitarbeiterID), nachname, vorname, zertAblDatum, zertifikatstitel, zertifikatsbeschreibung};

            return attributenArray;

        }
    }
