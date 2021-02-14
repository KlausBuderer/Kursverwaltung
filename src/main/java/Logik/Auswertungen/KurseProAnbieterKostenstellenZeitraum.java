
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class KurseProAnbieterKostenstellenZeitraum {

    private final String[] TITELZEILE = {"kostenstelleNr","kostenstellebezeichnung","kosten","waehrung","anbieter","selektionszeitraumVon","selektionszeitraumBis"};

    private int kostenstelleNr;
    private String kostenstellebezeichnung;
    private int kosten;
    private String waehrung;
    private String anbieter;
    private String selektionszeitraumVon;
    private String selektionszeitraumBis;


    public KurseProAnbieterKostenstellenZeitraum() {}

    public KurseProAnbieterKostenstellenZeitraum(int kostenstelleNr, String kostenstellebezeichnung, int kosten, String waehrung, String anbieter, String selektionszeitraumVon, String selektionszeitraumBis) {

        this.kostenstelleNr = kostenstelleNr;
        this.kostenstellebezeichnung = kostenstellebezeichnung;
        this.kosten = kosten;
        this.waehrung = waehrung;
        this.anbieter = anbieter;
        this.selektionszeitraumVon = selektionszeitraumVon;
        this.selektionszeitraumBis = selektionszeitraumBis;
    }

    public void auswertungAusgeben() {
        String datumVon;
        String datumBis;
        List<KurseProAnbieterKostenstellenZeitraum> ausgabeKurseProAnbieterKostenstellenZeitraum;
        // Abfrage Datum
        datumVon = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie das Startdatum des gewuenschten Auswertungszeitraum ein: ");
        datumBis = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben Sie das Enddatum des gewuenschten Auswertungszeitraum ein: ");

        //Aufruf Store Procedure SP_ANZEIGEN_KURSE_PRO_ANBIETER_KOSTENSTELLE_JAHR

        ausgabeKurseProAnbieterKostenstellenZeitraum = new AuswertungenDatenbank().storeproduceKurseProAnbieterKostenstellenZeitraum(datumVon, datumBis);

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (KurseProAnbieterKostenstellenZeitraum kpakz : ausgabeKurseProAnbieterKostenstellenZeitraum) {

            tabelle.zeileHinzufuegen(kpakz.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
         }

    private String[] attributenArrayBefuellen() {
        String[] attributenArray = {String.valueOf(kostenstelleNr),kostenstellebezeichnung,String.valueOf(kosten),waehrung,anbieter,selektionszeitraumVon,selektionszeitraumBis};

        return attributenArray;

    }
}
