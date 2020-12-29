
import java.time.Year;
import java.util.*;

public class Administratives extends Datenbank{


    String[] unterMenue = {"Menü für Administratives", "1. Kostenstelle anlegen", "2. Budget erfassen pro Kostenstelle", "3. Oranisation anlegen", "4. Kostenstelle bearbeiten", "5. Budget Bearbeiten", "6. Hauptmenü"};



    int kostenstelleId;
    int kostenstelle;
    int budgetJahr;
    int budgetBetrag;
    int budgetID;
    int waehrung;
    int organisationsID;
    String[] waehrungsArray = {"CHF","EUR"};
    String abteilungsBezeichnung;
    String bezeichnungKst;
    String kostenstelleVerantPerson;
    String stellenbezeichnung;

    Scanner scan = new Scanner(System.in);
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor anzeigen Hauptmenü
    Administratives(){
    untermenueAnzeigen();

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
        this.organisationsID = organisationsID;
        this.abteilungsBezeichnung = abteilungsBezeichnung;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Tabelle BudgetPeriode

    public Administratives(int budgetID, int kostenstelleId, int budgetJahr, int budgetBetrag) {
        this.kostenstelleId = kostenstelleId;
        this.budgetJahr = budgetJahr;
        this.budgetBetrag = budgetBetrag;
        this.budgetID = budgetID;
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
                    budgetErfassen();
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
            kostenstelle = scan.nextInt();
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

            switch (BefehlsZeilenSchnittstelle.korrekteEingabebestaetigen()){

                case 1: datenAnlegen(this,"Kostenstelle");
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
    Die Methode budgetErfassen lässt den Benutzer ein neues Budget erfassen und einer Kostenstelle zuweisen
     */

    void budgetErfassen(){

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
            System.out.print("Budget Betrag: ");

            boolean korrekteEingabe = false;

            //Prüft ob die Eingabe eine Zahl ist und keine Sonderzeichen enthält
            while(!korrekteEingabe){

               String eingabe = scan.next();

               if(eingabe.matches("[^0-9]")){
                   System.out.println("Bitte geben sie einen gültigen Wert ein");
                   korrekteEingabe = false;
               }else{
                   budgetBetrag = Integer.parseInt(eingabe);
                   korrekteEingabe = true;
               }
            }

            int i = 1;

            for (String waehrung: waehrungsArray) {
                System.out.println(i + ". " + waehrung);
                i++;
            }
            System.out.println("Wahrung (1-2): ");
            waehrung = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1;

            auswahlListeKostenstelleAusgeben();

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(bezeichnungKst,20) +
                                "\tBudget: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetBetrag),10) +
                                "\tBudget Jahr: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(budgetJahr), 10));
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

        switch (BefehlsZeilenSchnittstelle.korrekteEingabebestaetigen()){

            case 1: datenAnlegen(this,"Budget");
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
    Die Methode organisationAnlegen lässt den Benutzer eine neue Organisation Anlegen
     */
    void organisationAnlegen(){

        boolean abschliessen = true;

        do{

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            System.out.print("Stellenbezeichnung: ");
            stellenbezeichnung = scan.next();

            auswahlListeKostenstelleAusgeben();

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Stellenbezeichnung: " + stellenbezeichnung + "\tBezeichnung der Kostenstelle: " + bezeichnungKst);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");



            switch (BefehlsZeilenSchnittstelle.korrekteEingabebestaetigen()){

            case 1: datenAnlegen(this,"Organisation");
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
                ausgabe = "Bezeichnung der Abteilung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.abteilungsBezeichnung), 10) +
                        "\tKostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(bezeichnungKst, 20);
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

    private void auswahlListeKostenstelleAusgeben() {

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

    private void auswahlListeOrganisationAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank nach Kostenstellen
        HashMap<Administratives, Integer> organisationMap = (HashMap<Administratives, Integer>) datenAuslesenfuerAbfrage("Organisation");


        // Schreiben der Kostenstellen in ein Array
        Administratives[] kostenstelleArray = new Administratives[organisationMap.size() + 1];

        for (Map.Entry<Administratives, Integer> map : organisationMap.entrySet()) {
            kostenstelleArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString("Organisation"));
            i++;
        }

        arrayLaenge = kostenstelleArray.length;

        System.out.print("Bitte wählen sie eine Organisation aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        kostenstelleId = kostenstelleArray[auswahl].kostenstelleId;
        organisationsID = kostenstelleArray[auswahl].organisationsID;
        abteilungsBezeichnung = kostenstelleArray[auswahl].abteilungsBezeichnung;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     */
    private void auswahlListeBudgetAusgeben() {

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

        budgetID = budgetArray[auswahl].budgetID;
        budgetBetrag = budgetArray[auswahl].budgetBetrag;
        budgetJahr = budgetArray[auswahl].budgetJahr;
        waehrung = budgetArray[auswahl].waehrung;
        abteilungsBezeichnung = budgetArray[auswahl].abteilungsBezeichnung;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung einer Kostenstelle

     */
    void kostenstelleBearbeiten() {

        String[] spaltenArray = {"Kostenstellennummer","Bezeichnung der Kostenstelle","Verantwortliche Person der Kostenstelle"};
        int arrayLaenge;
        int auswahl;
        String vornamen;
        String nachnamen;

        auswahlListeKostenstelleAusgeben();

        BefehlsZeilenSchnittstelle.bildReinigen();
        int i = 1;
        for (String spalte: spaltenArray) {

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

        datenBearbeiten(updateKostenstelle());

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

        auswahlListeBudgetAusgeben();

        BefehlsZeilenSchnittstelle.bildReinigen();
        int i = 1;
        for (String spalte: spaltenArray) {

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
                    System.out.println(j + ". " + (jahr + (j-1)));
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

        datenBearbeiten(updateBudget());

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
                " WHERE `ID` = " + budgetID + ";";
        return querry;
    }
}
