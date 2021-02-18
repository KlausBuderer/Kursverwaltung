
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import DatenSchicht.DatenLogikAuswertungen;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;


public class KostenstellenAlle {

    private int kostenstellennummer;

    private String bezechnungkostenstelle;
    private String kostenstellenverantwortlichePerson;

    private final String[] TITELZEILE = {"Kostenstellennummer","Bezechnungkostenstelle","KostenstellenverantwortlichePerson"};

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

        DatenLogikAuswertungen auswertungen = new AuswertungenDatenbank();
        AusgabeKostenstellenAlle = auswertungen.storeproduceKostenstellenAlle();

        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TITELZEILE);
        tabelle.vertikaleLinieSetzen(true);
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
