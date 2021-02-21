
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import DatenSchicht.DatenLogikAuswertungen;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;



public class BudgetAlleKostenstellen {

    private int kostenstellennummer;

    private String budgetjahr;
    private String budgetbetrag;
    private String waehrung;
    private String kostenstellenbezeichnung;
    private String kostenstellenverantwortlichePerson;

    private final String[] TITELZEILE = {"Budgetjahr","Budgetbetrag","Währung","Kostenstellennummer","Kostenstellenbezeichnung","KostenstellenverantwortlichePerson"};

    public BudgetAlleKostenstellen() {
    }

    public BudgetAlleKostenstellen(String budgetjahr, String budgetbetrag, String waehrung, int kostenstellennummer, String kostenstellenbezeichnung, String kostenstellenverantwortlichePerson) {
        this.budgetjahr = budgetjahr;
        this.budgetbetrag = budgetbetrag;
        this.waehrung = waehrung;
        this.kostenstellennummer = kostenstellennummer;
        this.kostenstellenbezeichnung = kostenstellenbezeichnung;
        this.kostenstellenverantwortlichePerson = kostenstellenverantwortlichePerson;
    }

    public void auswertungAusgeben() {

        List<BudgetAlleKostenstellen> AusgabeBudgetAlleKostenstellen;

        //Aufruf Store Procedure SP_ANZEIGEN_ALLE_KOSTENSTELLE_BUDGET

        DatenLogikAuswertungen auswertungen = new AuswertungenDatenbank();

        AusgabeBudgetAlleKostenstellen = auswertungen.storeproduceBudgetAlleKostenstellen();

        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TITELZEILE);
        tabelle.vertikaleLinieSetzen(true);
        for (BudgetAlleKostenstellen bak  : AusgabeBudgetAlleKostenstellen) {

            tabelle.zeileHinzufuegen(bak.attributenArrayBefuellen());

        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
    }

    private String[] attributenArrayBefuellen() {

        String[] attributenArray = {budgetjahr,budgetbetrag,waehrung,String.valueOf(kostenstellennummer),kostenstellenbezeichnung,kostenstellenverantwortlichePerson};

        return attributenArray;
    }
}
