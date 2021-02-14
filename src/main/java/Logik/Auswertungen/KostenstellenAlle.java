
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class KostenstellenAlle {


    private final String[] TITELZEILE = {"Kostenstellennummer","Bezechnungkostenstelle","KostenstellenverantwortlichePerson"};

    private int kostenstellennummer;
    private String bezechnungkostenstelle;
    private String kostenstellenverantwortlichePerson;

    public KostenstellenAlle(int kostenstellennummer, String bezechnungkostenstelle, String kostenstellenverantwortlichePerson) {
        this.kostenstellennummer = kostenstellennummer;
        this.bezechnungkostenstelle = bezechnungkostenstelle;
        this.kostenstellenverantwortlichePerson = kostenstellenverantwortlichePerson;
    }

    public KostenstellenAlle() {
    }

    public void auswertungAusgeben() {


        List<KostenstellenAlle> AusgabeKostenstellenAlle;


        //Aufruf Store Procedure SP_ANZEIGEN_ALLE_KOSTENSTELLEN

        AusgabeKostenstellenAlle = new AuswertungenDatenbank().storeproduceKostenstellenAlle();

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
        for (KostenstellenAlle ksa  : AusgabeKostenstellenAlle) {

            tabelle.zeileHinzufuegen(ksa.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
    }

    private String[] attributenArrayBefuellen() {

        String[] attributenArray = {String.valueOf(kostenstellennummer),bezechnungkostenstelle,kostenstellenverantwortlichePerson};

        return attributenArray;
    }
}
