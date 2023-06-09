package Logik.Administratives;


import DatenSchicht.BudgetDatenbank;
import DatenSchicht.DatenLogik;
import DatenSchicht.DatenLogikKostenstelle;
import DatenSchicht.KostenstelleDatenbank;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.time.Year;
import java.util.List;

public class Budget extends ServicesAdmin {

    private int budgetId;
    private int budgetJahr;
    private int budgetBetrag;
    private int kostenstelleId;

    private String waehrung;
    private String kostenstellenBezeichnung;

    private final String[] KOPFZEILE = {" ","Kostenstelle", "Jahr", "Betrag", "Währung"};

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Utilities.Tabelle BudgetPeriode

    public Budget(int budgetID, int kostenstelleId, int budgetJahr, int budgetBetrag, String waehrung) {
        this.kostenstelleId = kostenstelleId;
        this.budgetJahr = budgetJahr;
        this.budgetBetrag = budgetBetrag;
        this.budgetId = budgetID;
        this.waehrung = waehrung;

        //Liest anhand der kostenstellenId die Bezeichnung der Kostenstelle aus der Datenbank
        DatenLogikKostenstelle datenLogikKostenstelle =  new KostenstelleDatenbank();
        this.kostenstellenBezeichnung = datenLogikKostenstelle.kostenstellenBezeichnungAusgeben(kostenstelleId);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Utilities.Tabelle BudgetPeriode
    public Budget() {
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
 Methode zur Ausgabe einer Auswahlliste Kostenstelle fuer den Benutzerverwaltung
  */
    public void auswahlListeBudgetAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;
        String titelName = "Budget Waehlen";

        // Datenbank nach Liste Fragen
        DatenLogik budgetDatenbank = new BudgetDatenbank();

        // Abfrage Datenbank.Datenbank nach Kostenstellen
        List<Budget> budgetList = (List<Budget>) budgetDatenbank.datenAuslesen("tblBudgetPeriode");


        // Schreiben der Kostenstellen in ein budgetArray
        Budget[] budgetArray = new Budget[budgetList.size() + 1];

        // Erstellt eine Tabelle für die Ausgabe
        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(KOPFZEILE);
        tabelle.vertikaleLinieSetzen(true);

        BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);

        for (Budget budget : budgetList) {
            budgetArray[i] = budget;
            // Nummer hinzufügen für die Auswahlliste
            String[] tempArray = budgetArray[i].attributenArrayFuerTabelle();
            tempArray[0] = i + ". ";

            tabelle.zeileHinzufuegen(tempArray);

            i++;
        }

        tabelle.ausgabe();

        arrayLaenge = budgetArray.length;

        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Bitte wählen sie ein Budget aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        //Schreiben der Attributen der ausgewaehlten Daten in die Membervariablen
        budgetId = budgetArray[auswahl].budgetId;
        budgetBetrag = budgetArray[auswahl].budgetBetrag;
        budgetJahr = budgetArray[auswahl].budgetJahr;
        waehrung = budgetArray[auswahl].waehrung;
        kostenstellenBezeichnung = budgetArray[auswahl].kostenstellenBezeichnung;

    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Methode zum ein neues Budget anzulegen
    protected void datenAnlegen(){

        boolean abschliessen = true;
        String titelName = "Budget Anlegen";

        do{

            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie folgende Daten ein");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");

            //Ermittelt aktuelles Jahr
            int jahr = Year.now().getValue();

            //Gibt Jahre zu Auswahl => Aktuelles Jahr + 4 weitere Jahre
            for (int i = 1; i < 6; i++) {
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ". " + (jahr + (i-1)));
            }
            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Jahr (1-5): ");
            budgetJahr = jahr + (BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(6) - 1);

            //Budget Betrag
            budgetBetrag = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Budget Betrag: ");

            //Gibt Waehrung zur Auswahl
            waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();

            //Gibt Kostenstellen zur Auswahl aus
            Kostenstelle kostenstelle = new Kostenstelle();
            kostenstelle.auswahlListeKostenstelleAusgeben();
            kostenstelleId = kostenstelle.getKostenstelleId();
            DatenLogikKostenstelle kostenstellenBez = new KostenstelleDatenbank();
            kostenstellenBezeichnung = kostenstellenBez.kostenstellenBezeichnungAusgeben(kostenstelleId);

            //Eingaben anzeigen
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE,attributenArrayFuerTabelle());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");

            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            // Eingaben bestaetigen, neu beginnen oder abbrechen
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){
                case 1:
                    new BudgetDatenbank().datenAnlegen(this);
                    abschliessen = true;
                    break;
                case 2: abschliessen = false;
                    break;
                case 3: abschliessen = true;
                    break;
            }

        }while(!abschliessen);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung eines Budget
     */
    protected void datenMutieren() {

        String[] spaltenArray = {"Budget Jahr","Budget Betrag","Waehrung"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = false;
        String titelName = "Budget Mutieren";
        auswahlListeBudgetAusgeben();

        // Gibt eine Auswahl von Attributen die geändert werden können
        do {
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            int i = 1;
            for (String spalte : spaltenArray) {

                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Welchen Spalte möchten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + budgetJahr);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Wählen sie das neue Jahr aus: ");
                    int jahr = Year.now().getValue();

                    //Gibt Jahre zu Auswahl
                    for (int j = 1; j < 6; j++) {
                        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(j + ". " + (jahr + (j - 1)));
                    }

                    BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Jahr (1-5): ");
                    budgetJahr = jahr + (BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(6) - 1);
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + budgetBetrag);
                    budgetBetrag = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie das neue Budget ein: ");
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + waehrung);
                    waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
                    break;
                default:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
            }

            //Eingaben anzeigen
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE,attributenArrayFuerTabelle());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            //Eingaben bestaetigen, neu beginnen oder abbrechen
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){
                case 1:
                    new BudgetDatenbank().datenMutation(this);
                    abschliessen = true;
                    break;
                case 2: abschliessen = false;
                    break;
                case 3: abschliessen = true;
                    break;
            }
        }while(!abschliessen);
    }

        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Diese Methode packt die Membervariablen in ein Array fuer die Ausgabe in einer Tabelle
     */
    protected String[] attributenArrayFuerTabelle(){
        String[] attributenArray = {" ",kostenstellenBezeichnung, String.valueOf(budgetJahr), String.valueOf(budgetBetrag),waehrung};
        return attributenArray;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return  "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstellenBezeichnung), 10) +
                "Budget Jahr: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetJahr), 20) +
                "Budget Betrag: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetBetrag), 25) +
                "Waehrung: " + waehrung;
    }

    public int getBudgetJahr() {
        return budgetJahr;
    }

    public int getBudgetBetrag() {
        return budgetBetrag;
    }

    public int getKostenstelleId() {
        return kostenstelleId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public String getWaehrung() {
        return waehrung;
    }

}
