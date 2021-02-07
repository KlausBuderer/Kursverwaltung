package Administratives;


import Datenbank.BudgetDatenbank;
import Datenbank.KostenstelleDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class Budget{

    public int kostenstelleId;
    public int budgetId;
    public int budgetJahr;
    public int budgetBetrag;
    public String waehrung;
    public String kostenstellenBezeichnung;

    private String[] KOPFZEILE = {" ","Kostenstelle", "Jahr", "Betrag", "Waehrung"};

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Utilities.Tabelle BudgetPeriode

    public Budget(int budgetID, int kostenstelleId, int budgetJahr, int budgetBetrag, String waehrung) {
        this.kostenstelleId = kostenstelleId;
        this.budgetJahr = budgetJahr;
        this.budgetBetrag = budgetBetrag;
        this.budgetId = budgetID;
        this.waehrung = waehrung;
        this.kostenstellenBezeichnung = new KostenstelleDatenbank().kostenstelleBezichnungAusgeben(kostenstelleId);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Utilities.Tabelle BudgetPeriode

    public Budget() {
    }

    void budgetAnlegen(){

        boolean abschliessen = true;

        do{

            BefehlsZeilenSchnittstelle.bildReinigen();
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
            //Gibt Währung zur Auswahl
            waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
            //Gibt Kostenstellen zur Auswahl aus
            Kostenstelle kostenstelle = new Kostenstelle();
            kostenstelle.auswahlListeKostenstelleAusgeben();
            kostenstelleId = kostenstelle.kostenstelleId;
            kostenstellenBezeichnung = new KostenstelleDatenbank().kostenstelleBezichnungAusgeben(kostenstelleId);


            BefehlsZeilenSchnittstelle.bildReinigen();

            Tabelle tabelle = new Tabelle();
            tabelle.setHeaders(KOPFZEILE);
            tabelle.zeileHinzufuegen(attributenArrayFuerTabelle());
            tabelle.ausgabe();
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");

            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

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
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     */
    public void auswahlListeBudgetAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Datenbank nach Liste Fragen
        BudgetDatenbank budgetDatenbank = new BudgetDatenbank();

        // Abfrage Datenbank.Datenbank nach Kostenstellen
        HashMap<Budget, Integer> budgetMap = (HashMap<Budget, Integer>) budgetDatenbank.datenAuslesenfuerAbfrage("tblBudgetPeriode");


        // Schreiben der Kostenstellen in ein budgetArray
        Budget[] budgetArray = new Budget[budgetMap.size() + 1];

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(KOPFZEILE);
        tabelle.setVertikaleLinie(true);

        for (Map.Entry<Budget, Integer> map : budgetMap.entrySet()) {
            budgetArray[i] = map.getKey();
            String[] tempArray = map.getKey().attributenArrayFuerTabelle();
            tempArray[0] = i + ". ";

            tabelle.zeileHinzufuegen(tempArray);

            i++;
        }

        tabelle.ausgabe();


        arrayLaenge = budgetArray.length;

        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Bitte wählen sie ein Budget aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        //Schreiben der Attributen der ausgewählten Daten in die Membervariablen
        budgetId = budgetArray[auswahl].budgetId;
        budgetBetrag = budgetArray[auswahl].budgetBetrag;
        budgetJahr = budgetArray[auswahl].budgetJahr;
        waehrung = budgetArray[auswahl].waehrung;
        kostenstellenBezeichnung = budgetArray[auswahl].kostenstellenBezeichnung;

    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung eines Budget
     */
    void budgetMutieren() {

        String[] spaltenArray = {"Budget Jahr","Budget Betrag","Waehrung"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;

        auswahlListeBudgetAusgeben();

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            int i = 1;
            for (String spalte : spaltenArray) {

                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Welchen Spalte moechten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + budgetJahr);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Waehlen sie das neue Jahr aus: ");
                    int jahr = Year.now().getValue();

                    //Gibt Jahre zu Auswahl
                    for (int j = 1; j < 6; j++) {
                        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(j + ". " + (jahr + (j - 1)));
                    }

                    BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Jahr (1-5): ");
                    budgetJahr = jahr + (BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(6) - 1);
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + budgetBetrag);
                    budgetBetrag = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie das neue Budget ein: ");
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + waehrung);
                    waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
                    break;
                default:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
            }

            BefehlsZeilenSchnittstelle.bildReinigen();

            Tabelle tabelle = new Tabelle();
            tabelle.setHeaders(KOPFZEILE);
            tabelle.zeileHinzufuegen(attributenArrayFuerTabelle());
            tabelle.ausgabe();

            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

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

    @Override
    public String toString() {
        return  "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstellenBezeichnung), 10) +
                "Budget Jahr: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetJahr), 20) +
                "Budget Betrag: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetBetrag), 25) +
                "Waehrung: " + waehrung;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Diese Methode packt die Membervariablen in ein Array für die Ausgabe in einer Tabelle
     */
    String[] attributenArrayFuerTabelle(){
        String[] attributenArray = {" ",kostenstellenBezeichnung, String.valueOf(budgetJahr), String.valueOf(budgetBetrag),waehrung};
        return attributenArray;
    }
}
