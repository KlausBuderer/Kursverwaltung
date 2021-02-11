package Auswertungen;

import Datenbank.AuswertungenDatenbank;
import Mitarbeiter.Mitarbeiter;
import Mitarbeiter.MitarbeiterSuche;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.util.HashMap;
import java.util.Map;


public class ZertifikatProMitarbeiter {


    // Feldliste in Datenbank

    // MitarbeiterID,Nachname,Vorname,ZertAblDatum,Zertifikatstitel,Zertifikatsbeschreibung

    private final String[] TITELZEILE = {"MitarbeiterID","Nachname","Vorname","ZertAblDatum","Zertifikatstitel","Zertifikatsbeschreibung"};
    private int mitarbeiterID;
    private String nachname;
    private String vorname;
    private String zertAblDatum;
    private String zertifikatstitel;
    private String zertifikatsbeschreibung;


    public ZertifikatProMitarbeiter(int mitarbeiterID, String nachname, String vorname, String zertAblDatum, String zertifikatstitel, String zertifikatsbeschreibung) {
        this.mitarbeiterID = mitarbeiterID;
        this.nachname = nachname;
        this.vorname = vorname;
        this.zertAblDatum = zertAblDatum;
        this.zertifikatstitel = zertifikatstitel;
        this.zertifikatsbeschreibung = zertifikatsbeschreibung;

    }

    public ZertifikatProMitarbeiter() {

    }

    public void auswertungAusgeben() {

        Mitarbeiter mitarbeiterIdentification;

        HashMap<ZertifikatProMitarbeiter, Integer> AusgabeZertifikatProMitarbeiter;

        mitarbeiterIdentification = new MitarbeiterSuche().mitarbeiterSuchen();


        //Aufruf Store Procedure SP_ANZEIGEN_MA_ZERT

        AusgabeZertifikatProMitarbeiter = new AuswertungenDatenbank().storeproduceZertifikatproMitarbeiter(mitarbeiterIdentification.mitarbeiterId);


        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);

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
