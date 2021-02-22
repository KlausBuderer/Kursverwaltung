package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import DatenSchicht.DatenLogikAuswertungen;
import Logik.Mitarbeiter.Mitarbeiter;
import Logik.Mitarbeiter.MitarbeiterSuche;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class KursProMitarbeiter {

    private int mitarbeiterID;
    private int kosten;

    private String nachname;
    private String vorname;
    private String kurscode;
    private String kursbeschreibung;
    private String anbieter;
    private String waehrung;

    private final String[] TITELZEILE = {"MitarbeiterID", "Nachname", "Vorname", "Kurscode", "Kursbeschreibung", "Anbieter", "Kosten", "Währung"};

    public KursProMitarbeiter() {}

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

    public void auswertungAusgeben() {
        Mitarbeiter mitarbeiterIdentification;

        List<KursProMitarbeiter> AusgabeKursProMitarbeiter;

        mitarbeiterIdentification = new MitarbeiterSuche().mitarbeiterSuchen();


        //Aufruf Store Procedure SP_ANZEIGEN_MA_KURSE
        DatenLogikAuswertungen auswertungen = new AuswertungenDatenbank();
        AusgabeKursProMitarbeiter = auswertungen.storeproduceKursproMitarbeiter(mitarbeiterIdentification.getMitarbeiterId());


        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TITELZEILE);
        tabelle.vertikaleLinieSetzen(true);

        for (KursProMitarbeiter kursProMitarbeiter : AusgabeKursProMitarbeiter) {

            tabelle.zeileHinzufuegen(kursProMitarbeiter.attributenArrayBefuellen());
        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
    }

    private String[] attributenArrayBefuellen () {
            String[] attributenArray = {String.valueOf(mitarbeiterID), nachname, vorname, kurscode, kursbeschreibung, anbieter, String.valueOf(kosten), waehrung};

            return attributenArray;

        }
    }
