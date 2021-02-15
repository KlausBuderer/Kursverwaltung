
package Logik.Auswertungen;

import DatenSchicht.AuswertungenDatenbank;
import DatenSchicht.DatenLogikAuswertungen;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;



public class BudgetAlleKostenstellen {

    private final String[] TITELZEILE = {"Budgetjahr","Budgetbetrag","Waehrung","Kostenstellennummer","Kostenstellenbezeichnung","KostenstellenverantwortlichePerson"};

    private String budgetjahr;
    private String budgetbetrag;
    private String waehrung;
    private int kostenstellennummer;
    private String kostenstellenbezeichnung;
    private String kostenstellenverantwortlichePerson;


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
        tabelle.setHeaders(TITELZEILE);
        tabelle.setVertikaleLinie(true);
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
