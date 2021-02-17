
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import Logik.Administratives.Kostenstelle;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class KurseProAnbieterSelektivKostenstelleZeitraum {



    private final String[] TITELZEILE = {"Kostenstellennummer","Kosten","Währung","Anbieter","SelektionszeitraumVon","SelektionszeitraumBis"};

    private int kostenstelleNr;
    private int kosten;
    private String waehrung;
    private String anbieter;
    private String selektionszeitraumVon;
    private String selektionszeitraumBis;

    public KurseProAnbieterSelektivKostenstelleZeitraum() {}

    public KurseProAnbieterSelektivKostenstelleZeitraum(int kostenstelleNr, int kosten, String waehrung, String anbieter, String selektionszeitraumVon, String selektionszeitraumBis) {
        this.kostenstelleNr = kostenstelleNr;
        this.kosten = kosten;
        this.waehrung = waehrung;
        this.anbieter = anbieter;
        this.selektionszeitraumVon = selektionszeitraumVon;
        this.selektionszeitraumBis = selektionszeitraumBis;
    }

    public void auswertungAusgeben() {
        String datumVon;
        String datumBis;
        String eingabeKostenstelle;

        List<KurseProAnbieterSelektivKostenstelleZeitraum> AusgabeKurseProAnbieterSelektivKostenstelleZeitraum;
        // Abfrage Datum
        datumVon = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie das Startdatum des gewuenschten Auswertungszeitraum ein: ");
        datumBis = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie das Enddatum des gewuenschten Auswertungszeitraum ein: ");
        Kostenstelle kostenstelle = new Kostenstelle();
        kostenstelle.auswahlListeKostenstelleAusgeben();
        eingabeKostenstelle = String.valueOf(kostenstelle.kostenstelleNr);

        //Aufruf Store Procedure SP_ANZEIGEN_KURSE_PRO_ANBIETER_SELEKTIV_KOSTENSTELLE_JAHR

        AusgabeKurseProAnbieterSelektivKostenstelleZeitraum = new AuswertungenDatenbank().storeproduceProAnbieterSelektivKostenstelleZeitraum(datumVon, datumBis, eingabeKostenstelle);

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (KurseProAnbieterSelektivKostenstelleZeitraum kpaskz : AusgabeKurseProAnbieterSelektivKostenstelleZeitraum) {

            tabelle.zeileHinzufuegen(kpaskz.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
         }

    private String[] attributenArrayBefuellen() {

        String[] attributenArray = {String.valueOf(kostenstelleNr),String.valueOf(kosten),waehrung,waehrung,selektionszeitraumVon,selektionszeitraumBis};


        return attributenArray;

    }
}
