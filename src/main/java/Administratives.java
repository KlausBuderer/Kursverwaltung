
import java.sql.SQLException;
import java.time.Year;
import java.util.*;

public class Administratives extends Datenbank{


    private String[] unterMenue = {"Menü für Administratives", "1. Kostenstelle anlegen", "2. Budget erfassen pro Kostenstelle", "3. Oranisation anlegen", "4. Kostenstelle bearbeiten", "5. Budget Bearbeiten", "6. Hauptmenü"};
    private String[] waehrungsArray = {"CHF","EUR"};

    private int kostenstelleId;
    private int budgetId;
    private int kostenstelle;
    private int budgetJahr;
    private int budgetBetrag;
    private int waehrung;
    private String abteilungsBezeichnung;
    private String bezeichnungKst = " ";
    private String kostenstelleVerantPerson;
    private String stellenbezeichnung;

    public int organisationsId;

    Scanner scan = new Scanner(System.in);

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor anzeigen Hauptmenü
    Administratives(){
    untermenueAnzeigen();

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor aufruf von anderen Klassen
    Administratives(String kontekt){

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Kostenstelle
    Administratives( int kostenstelleId, int konstenstelle, String bezeichnungKst, String kostenstelleVerantPerson){

        this.kostenstelleId =kostenstelleId;
        this.kostenstelle = konstenstelle;
        this.bezeichnungKst = bezeichnungKst;
        this.kostenstelleVerantPerson = kostenstelleVerantPerson;

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Organisation
    public Administratives( int organisationsID, int kostenstelleId, String abteilungsBezeichnung) {
        this.kostenstelleId = kostenstelleId;
        this.organisationsId = organisationsID;
        this.abteilungsBezeichnung = abteilungsBezeichnung;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Tabelle BudgetPeriode

    public Administratives(int budgetID, int kostenstelleId, int budgetJahr, int budgetBetrag) {
        this.kostenstelleId = kostenstelleId;
        this.budgetJahr = budgetJahr;
        this.budgetBetrag = budgetBetrag;
        this.budgetId = budgetID;
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
                    kostenstelleAnlegen();
                    break;
                case 2:
                    budgetAnlegen();
                    break;
                case 3:
                    organisationAnlegen();
                    break;
                case 4:
                    kostenstelleBearbeiten();
                    break;
                case 5:
                    budjetBearbeiten();
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

    void kostenstelleAnlegen(){

        boolean abschliessen = true;
        String vorname;
        String nachname;

        do {

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            System.out.print("Kostenstelle: ");
            kostenstelle = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
            System.out.print("Bezeichnung der Kostenstelle: ");
            bezeichnungKst = scan.next();
            System.out.println("Verantwortliche Person der Kostenstelle: ");
            System.out.print("Vorname: ");
            vorname = scan.next();
            System.out.print("Nachname: ");
            nachname = scan.next();
            kostenstelleVerantPerson = vorname + " " + nachname;

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
       /*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses
     */
    String kostenstelleAnlegenQuerry(){

        return "INSERT INTO `itwisse_kursverwaltung`.`Kostenstelle` (`Kostenstelle`, `BezeichnungKST`, `KostenstelleVerantPerson`) VALUES " +
                "('" + kostenstelle + "', '" + bezeichnungKst + "', '" + kostenstelleVerantPerson + "')";

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode budgetErfassen lässt den Benutzer ein neues Budget erfassen und einer Kostenstelle zuweisen
     */

    void budgetAnlegen(){

        boolean abschliessen = true;

        do{

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            System.out.println();


            int jahr = Year.now().getValue();

            //Gibt Jahre zu Auswahl
            for (int i = 1; i < 6; i++) {
                System.out.println(i + ". " + (jahr + (i-1)));
            }
            System.out.print("Jahr (1-5): ");
            budgetJahr = jahr + (BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(6) - 1);
            //Budget Betrag
            System.out.print("Budget Betrag: ");
            budgetBetrag = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
              //Gibt Währung zur Auswahl
            int i = 1;
            for (String waehrung: waehrungsArray) {
                System.out.println(i + ". " + waehrung);
                i++;
            }
            System.out.println("Wahrung (1-2): ");
            waehrung = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1;
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
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses
     */
    String budgetAnlegenQuerry(){

        return "INSERT INTO `itwisse_kursverwaltung`.`BudgetPeriode` (`Jahr`, `Betrag`, `Waehrung`, `KostenstelleID`) VALUES" +
                " ('" + budgetJahr + "', '" + budgetBetrag + "', '" + waehrungsArray[waehrung] + "', '" + kostenstelleId + "')";

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode organisationAnlegen lässt den Benutzer eine neue Organisation Anlegen
     */
    void organisationAnlegen(){

        boolean abschliessen = true;

        do{

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            System.out.print("Stellenbezeichnung: ");
            stellenbezeichnung = scan.next();
            //Gibt Kostenstelle als Liste aus
            auswahlListeKostenstelleAusgeben();

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Stellenbezeichnung: " + stellenbezeichnung + "\tBezeichnung der Kostenstelle: " + bezeichnungKst);
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
       /*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses
     */
    String organisationAnlegenQuerry(){

        return "Insert INTO `itwisse_kursverwaltung`.`Organisation` (`AbteilungsBezeichnung`,`KostenstelleID`) VALUES ('" + stellenbezeichnung + "', '" + kostenstelleId + "')";

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode gibt beim Aufruf des Objekts und der Übergabe des Parameters die Membervariablen aus
     */

    public String toString(String tabelle) {

        String ausgabe;

        switch (tabelle) {
            case "Kostenstelle":
                ausgabe = "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstelle), 10) +
                    "\t Verantwortliche Person: " + BefehlsZeilenSchnittstelle.textFormatieren(kostenstelleVerantPerson, 20) +
                    "\t Bezeichnung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(bezeichnungKst), 25);
                break;
            case "Organisation":
                ausgabe = "Bezeichnung der Abteilung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.abteilungsBezeichnung), 25) +
                        "\tKostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(bezeichnungKst, 25);
                break;
            case  "Budget":
                ausgabe = "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstelle), 10) +
                        "\t Budget Jahr: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetJahr), 20) +
                        "\t Budget Betrag: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetBetrag), 25);
                break;
            default:
                ausgabe = "";
                break;

        }

        return ausgabe;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     */

    public void auswahlListeKostenstelleAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank nach Kostenstellen
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
       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     */

    public void auswahlListeOrganisationAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank nach Kostenstellen
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
       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     */
    public void auswahlListeBudgetAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank nach Kostenstellen
        HashMap<Administratives, Integer> organisationMap = (HashMap<Administratives, Integer>) datenAuslesenfuerAbfrage("BudgetPeriode");


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

        budgetId = budgetArray[auswahl].budgetId;
        budgetBetrag = budgetArray[auswahl].budgetBetrag;
        budgetJahr = budgetArray[auswahl].budgetJahr;
        waehrung = budgetArray[auswahl].waehrung;
        abteilungsBezeichnung = budgetArray[auswahl].abteilungsBezeichnung;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung einer Kostenstelle

     */
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
                    System.out.print("Geben sie die neue Kostenstellennummer ein: ");
                    kostenstelle = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
                    break;
                case 2:
                    System.out.println("Aktuell: " + bezeichnungKst);
                    System.out.print("Geben sie die neue Bezeichnung ein: ");
                    bezeichnungKst = scan.next();
                    break;
                case 3:
                    System.out.println("Aktuell: " + kostenstelleVerantPerson);
                    System.out.println("Geben sie die neue verantwortliche Person ein: ");
                    System.out.print("Vorname: ");
                    vornamen = scan.next();
                    System.out.print("Nachname: ");
                    nachnamen = scan.next();
                    kostenstelleVerantPerson = vornamen + " " + nachnamen;
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
       /*
    Methode zur Erstellung eines Querrys für einen Update von Kostenstelle

     */

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
       /*
    Methode zur Bearbeitung eines Budget
     */
    void budjetBearbeiten() {

        String[] spaltenArray = {"Budget Jahr","Budget Betrag",};
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
                    System.out.print("Geben sie das neue Budget ein: ");
                    budgetBetrag = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
                    break;
                // TODO Checken ob Waehrung bei Budget wirklich notwendig
//            case 3:
//                System.out.println("Aktuell: " + waehrung);
//                int p = 1;
//                System.out.println("Geben sie die neue verantwortliche Person ein: ");
//                for (String waehrung: waehrungsArray) {
//                    System.out.println(p + ". " + waehrung);
//                    p++;
//                }
//                System.out.println("Wahrung (1-2): ");
//                waehrung = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1;
//                break;
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


    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für einen Update von Kostenstelle

     */

    String updateBudget(){

        String querry;

        querry = "UPDATE `itwisse_kursverwaltung`.`BudgetPeriode` SET " +
                " `Jahr` = " + budgetJahr +
                ", `Betrag` = " + budgetBetrag +
                " WHERE `ID` = " + budgetId + ";";
        return querry;
    }
}
