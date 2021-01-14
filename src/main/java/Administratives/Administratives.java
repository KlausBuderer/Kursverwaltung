package Administratives;

import Utilities.BefehlsZeilenSchnittstelle;

public class Administratives{


    private String[] unterMenue = {"Menü für Administratives", "1. Kostenstelle anlegen", "2. Budget erfassen pro Kostenstelle", "3. Oranisation anlegen", "4. Kostenstelle bearbeiten", "5. Budget Bearbeiten", "6. Hauptmenü"};


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor anzeigen Hauptmenü
    public Administratives() {
        untermenueAnzeigen();

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Die Methode untermenueAnzeige zeigt das Untermenü und führt anhand der Eingabe des Benutzers eine Aktion aus
     */

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    new Kostenstelle().kostenstelleAnlegen();
                    break;
                case 2:
                    new Budget().budgetAnlegen();
                    break;
                case 3:
                    new Organisation().organisationAnlegen();
                    break;
                case 4:
                    new Kostenstelle().kostenstelleBearbeiten();
                    break;
                case 5:
                    new Budget().budgetBearbeiten();
                    break;
                case 6:
                    //zurück ins Hauptmenü;
                    System.out.println("Hauptmenü");
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Die Methode kostenstelleAnlegen lässt den Benutzer eine neue Kostenstelle anlegen
     */
/*
    void kostenstelleAnlegen(){

        boolean abschliessen = true;

        do {

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Kostenstellennummer
            kostenstelle = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kostenstelle Nummer: ");
            //Bezeichnung Kostenstelle
            bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Bezeichnung der Kostenstelle: ");
            //Verantwortliche Person
            kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Verantwortliche Person der Kostenstelle: ");

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Kostenstelle: " + kostenstelle + "\tBezeichnung der Kostenstelle: " + bezeichnungKst + "\tVerantwortliche Person der Kostenstelle: " + kostenstelleVerantPerson);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: datenAnlegen(kostenstelleAnlegenQuerry());
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
       *//*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses
     *//*
    String kostenstelleAnlegenQuerry(){

        return "INSERT INTO `itwisse_kursverwaltung`.`Kostenstelle` (`Kostenstelle`, `BezeichnungKST`, `KostenstelleVerantPerson`) VALUES " +
                "('" + kostenstelle + "', '" + bezeichnungKst + "', '" + kostenstelleVerantPerson + "')";

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    *//*
    Die Methode budgetErfassen lässt den Benutzer ein neues Budget erfassen und einer Kostenstelle zuweisen
     *//*
*//*
    void budgetAnlegen(){

        boolean abschliessen = true;

        do{

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            System.out.println();

            //Ermittelt aktuelles Jahr
            int jahr = Year.now().getValue();

            //Gibt Jahre zu Auswahl => Aktuelles Jahr + 4 weitere Jahre
            for (int i = 1; i < 6; i++) {
                System.out.println(i + ". " + (jahr + (i-1)));
            }
            System.out.print("Jahr (1-5): ");
            budgetJahr = jahr + (BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(6) - 1);
            //Budget Betrag
            budgetBetrag = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Budget Betrag: ");
              //Gibt Währung zur Auswahl
            waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
            //Gibt Kostenstellen zur Auswahl aus
            auswahlListeKostenstelleAusgeben();

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(bezeichnungKst,20) +
                                "\tBudget: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetBetrag),10) +
                                "\tBudget Jahr: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetJahr), 10));
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

        switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

            case 1: datenAnlegen(budgetAnlegenQuerry());
                abschliessen = true;
                break;
            case 2: abschliessen = false;
                break;
            case 3: abschliessen = true;
                break;
        }

    }while(!abschliessen);
    }*//*
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       *//*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses
 /*
    String budgetAnlegenQuerry(){

        return "INSERT INTO `itwisse_kursverwaltung`.`BudgetPeriode` (`Jahr`, `Betrag`, `Waehrung`, `KostenstelleID`) VALUES" +
                " ('" + budgetJahr + "', '" + budgetBetrag + "', '" + waehrung + "', '" + kostenstelleId + "')";

    }
            *//*  //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    *//*
    Die Methode organisationAnlegen lässt den Benutzer eine neue Organisation Anlegen
     *//*
    void organisationAnlegen(){

        boolean abschliessen = true;

        do{

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Abteilungsbezeichnung
            abteilungsbezeichnung = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Abteilungsbezeichnung: ");
            //Gibt Kostenstelle als Liste aus
            auswahlListeKostenstelleAusgeben();

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Stellenbezeichnung: " + abteilungsbezeichnung + "\tBezeichnung der Kostenstelle: " + bezeichnungKst);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");



            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

            case 1: datenAnlegen(organisationAnlegenQuerry());
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
       *//*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses
     *//*
    String organisationAnlegenQuerry(){

        return "Insert INTO `itwisse_kursverwaltung`.`Organisation` (`AbteilungsBezeichnung`,`KostenstelleID`) VALUES ('" + abteilungsbezeichnung + "', '" + kostenstelleId + "')";

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    *//*
    Die Methode gibt beim Aufruf des Objekts und der Übergabe des Parameters die Membervariablen aus
     *//*

    public String toString(String tabelle) {

        String ausgabe;

        switch (tabelle) {
            case "Kostenstelle":
                ausgabe = "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstelle), 10) +
                    "Verantwortliche Person: " + BefehlsZeilenSchnittstelle.textFormatieren(kostenstelleVerantPerson, 20) +
                    "Bezeichnung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(bezeichnungKst), 25);
                break;
            case "Organisation":
                ausgabe = "Bezeichnung der Abteilung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.abteilungsBezeichnung), 25) +
                        "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(bezeichnungKst, 25);
                break;
          *//*  case  "Budget":
                ausgabe = "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstelleId), 10) +
                        "Budget Jahr: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetJahr), 20) +
                        "Budget Betrag: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetBetrag), 25) +
                        "Waehrung: " + waehrung;
                break;*//*
            default:
                ausgabe = "";
                break;

        }

        return ausgabe;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


       *//*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     *//*

    public void auswahlListeKostenstelleAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank.Datenbank nach Kostenstellen
        HashMap<Administratives, Integer> kostenstelleMap = (HashMap<Administratives, Integer>) datenAuslesenfuerAbfrage("Kostenstelle");


        // Schreiben der Kostenstellen in ein Array
        Administratives[] kostenstelleArray = new Administratives[kostenstelleMap.size() + 1];

        BefehlsZeilenSchnittstelle.bildReinigen();
        for (Map.Entry<Administratives, Integer> map : kostenstelleMap.entrySet()) {
            kostenstelleArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString("Kostenstelle"));
            i++;
        }

        arrayLaenge = kostenstelleArray.length;

        System.out.print("Bitte wählen sie eine Kostenstelle aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        kostenstelleId = kostenstelleArray[auswahl].kostenstelleId;
        kostenstelle = kostenstelleArray[auswahl].kostenstelle;
        bezeichnungKst = kostenstelleArray[auswahl].bezeichnungKst;
        kostenstelleVerantPerson = kostenstelleArray[auswahl].kostenstelleVerantPerson;

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       *//*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     *//*

    public void auswahlListeOrganisationAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank.Datenbank nach Kostenstellen
        HashMap<Administratives, Integer> organisationMap = (HashMap<Administratives, Integer>) datenAuslesenfuerAbfrage("Organisation");


        // Schreiben der Kostenstellen in ein Array
        Administratives[] organisationArray = new Administratives[organisationMap.size() + 1];

        for (Map.Entry<Administratives, Integer> map : organisationMap.entrySet()) {
            organisationArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString("Organisation"));
            i++;
        }

        arrayLaenge = organisationArray.length;

        System.out.print("Bitte wählen sie eine Organisation aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        kostenstelleId = organisationArray[auswahl].kostenstelleId;
        organisationsId = organisationArray[auswahl].organisationsId;
        abteilungsBezeichnung = organisationArray[auswahl].abteilungsBezeichnung;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       *//*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     *//*
   *//* public void auswahlListeBudgetAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Datenbank nach Liste Fragen
        AdministrativesDatenbank administrativesDatenbank = new AdministrativesDatenbank();

        // Abfrage Datenbank.Datenbank nach Kostenstellen
        HashMap<Administratives, Integer> organisationMap = (HashMap<Administratives, Integer>) administrativesDatenbank.datenAuslesenfuerAbfrage("BudgetPeriode");


        // Schreiben der Kostenstellen in ein budgetArray
        Administratives[] budgetArray = new Administratives[organisationMap.size() + 1];

        for (Map.Entry<Administratives, Integer> map : organisationMap.entrySet()) {
            budgetArray[i] = map.getKey();
            // Ausgeben des budgetArray
            System.out.println(i + ". " + map.getKey().toString("Budget"));
            i++;
        }

        arrayLaenge = budgetArray.length;

        System.out.print("Bitte wählen sie ein Budget aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        //Schreiben der Attributen der ausgewählten Daten in die Membervariablen
        budgetId = budgetArray[auswahl].budgetId;
        budgetBetrag = budgetArray[auswahl].budgetBetrag;
        budgetJahr = budgetArray[auswahl].budgetJahr;
        waehrung = budgetArray[auswahl].waehrung;
        abteilungsBezeichnung = budgetArray[auswahl].abteilungsBezeichnung;

    }
*//*
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       *//*
    Methode zur Bearbeitung einer Kostenstelle

     *//*
    void kostenstelleBearbeiten(){

        String[] spaltenArray = {"Kostenstellennummer","Bezeichnung der Kostenstelle","Verantwortliche Person der Kostenstelle"};
        String vornamen;
        String nachnamen;
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;


        auswahlListeKostenstelleAusgeben();

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            int i = 1;
            for (String spalte : spaltenArray) {

                System.out.println(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            System.out.print("Welchen Spalte möchten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    System.out.println("Aktuell: " + kostenstelle);
                    kostenstelle = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die neue Kostenstellennummer ein: ");
                    break;
                case 2:
                    System.out.println("Aktuell: " + bezeichnungKst);
                    bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die neue Bezeichnung ein: ");
                    break;
                case 3:
                    System.out.println("Aktuell: " + kostenstelleVerantPerson);
                    kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die neue verantwortliche Person ein: ");
                    break;
                default:
                    System.out.println("Falsche Eingabe!");
            }

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Kostenstellen Bezeichnung: " + bezeichnungKst + "\tKostenstellen Nummer: " + kostenstelle + "\tVerantwortliche Person: " + kostenstelleVerantPerson);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    datenBearbeiten(updateKostenstelle());
                    ;
                    abschliessen = true;
                    break;
                case 2:
                    abschliessen = false;
                    break;
                case 3:
                    abschliessen = true;
                    break;
            }

        }while(!abschliessen);


    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       *//*
    Methode zur Erstellung eines Querrys für einen Update von Kostenstelle

     *//*

    String updateKostenstelle(){

        String querry;

       querry = "UPDATE `itwisse_kursverwaltung`.`Kostenstelle` SET " +
               " `Kostenstelle` = " + kostenstelle +
               ", `BezeichnungKST` = \"" + bezeichnungKst +
               "\", `KostenstelleVerantPerson` = \"" + kostenstelleVerantPerson +
               "\" WHERE `ID` = " + kostenstelleId + ";";
        return querry;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       *//*
    Methode zur Bearbeitung eines Budget
     *//*
   *//* void budgetBearbeiten() {

        String[] spaltenArray = {"Budget Jahr","Budget Betrag","Waehrung"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;

        auswahlListeBudgetAusgeben();

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            int i = 1;
            for (String spalte : spaltenArray) {

                System.out.println(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            System.out.print("Welchen Spalte möchten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + budgetJahr);
                    System.out.println("Waehlen sie das neue Jahr aus: ");
                    int jahr = Year.now().getValue();

                    //Gibt Jahre zu Auswahl
                    for (int j = 1; j < 6; j++) {
                        System.out.println(j + ". " + (jahr + (j - 1)));
                    }

                    System.out.print("Jahr (1-5): ");
                    budgetJahr = jahr + (BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(6) - 1);
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + budgetBetrag);
                    budgetBetrag = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie das neue Budget ein: ");
                    break;
                case 3:
                   BefehlsZeilenSchnittstelle.bildReinigen();
                   System.out.println("Aktuell: " + waehrung);
                   waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
                   break;
                    default:
                        System.out.println("Falsche Eingabe!");
            }

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Budget Betrag: " + budgetBetrag + "\tBudget Jahr: " + budgetJahr + "\tKostenstelle: " + bezeichnungKst);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: datenBearbeiten(updateBudget());
                    abschliessen = true;
                    break;
                case 2: abschliessen = false;
                    break;
                case 3: abschliessen = true;
                    break;
            }




        }while(!abschliessen);


    }*//*
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       *//*
    Methode zur Erstellung eines Querrys für einen Update von Kostenstelle

     *//*
    *//*String updateBudget(){

        String querry;

        querry = "UPDATE `itwisse_kursverwaltung`.`BudgetPeriode` SET " +
                " `Jahr` = " + budgetJahr +
                ", `Betrag` = " + budgetBetrag +
                " WHERE `ID` = " + budgetId + ";";
        return querry;
    }*/
}
