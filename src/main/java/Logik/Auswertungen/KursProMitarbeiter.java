package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import Logik.Mitarbeiter.Mitarbeiter;
import Logik.Mitarbeiter.MitarbeiterSuche;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.HashMap;
import java.util.Map;


public class KursProMitarbeiter {


    // Feldliste in Datenbank
    // MitarbeiterID, Nachname, Vorname, Kurscode, Kursbeschreibung, Anbieter, Kosten, Waehrung


    private final String[] TITELZEILE = {"MitarbeiterID", "Nachname", "Vorname", "Kurscode", "Kursbeschreibung", "Anbieter", "Kosten", "Waehrung"};
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
        this.anbieter = anbieter;
        this.kosten = kosten;
        this.waehrung = waehrung;

    }

    public KursProMitarbeiter() {

    }
    // Abfrage Datum

    public void auswertungAusgeben() {
        Mitarbeiter mitarbeiterIdentification;

        HashMap<KursProMitarbeiter, Integer> AusgabeKursProMitarbeiter;

        mitarbeiterIdentification = new MitarbeiterSuche().mitarbeiterSuchen();


        //Aufruf Store Procedure SP_ANZEIGEN_MA_KURSE

        AusgabeKursProMitarbeiter = new AuswertungenDatenbank().storeproduceKursproMitarbeiter(mitarbeiterIdentification.mitarbeiterId);


        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);

        for (Map.Entry<KursProMitarbeiter, Integer> map : AusgabeKursProMitarbeiter.entrySet()) {

            tabelle.zeileHinzufuegen(map.getKey().attributenArrayBefuellen());
        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
    }

        private String[] attributenArrayBefuellen () {
            String[] attributenArray = {String.valueOf(mitarbeiterID), nachname, vorname, kurscode, kursbeschreibung, anbieter, String.valueOf(kosten), waehrung};

            return attributenArray;

        }
    }
