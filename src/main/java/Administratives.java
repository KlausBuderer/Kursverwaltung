import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Administratives extends Datenbank{


    String[] unterMenue = {"Menü für Administratives", "1. Kostenstelle anlegen", "2. Budget erfassen pro Kostenstelle", "3. Oranisation anlegen", "4. Organisation aendern", "5. Hauptmenü"};


    String stellenbezeichnung;
    int kostenstelleId;
    int kostenstelle;
    String bezeichnungKst;
    String kostenstelleVerantPerson;
    int budgetJahr;
    int budgetBetrag;
    String[] waehrungsArray = {"CHF","Eur"};
    int waehrung;

    Scanner scan = new Scanner(System.in);

    //Konstruktor
    Administratives(){
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
                    kostenstelleAnlegen();
                    break;
                case 2:
                    budgetErfassen();
                    break;
                case 3:
                    organisationAnlegen();
                    break;
                case 4:
                    System.out.println("organisationAendern");
                    break;
                case 5:
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

        do {

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            System.out.print("Kostenstelle: ");
            kostenstelle = scan.nextInt();
            System.out.print("Bezeichnung der Kostenstelle: ");
            bezeichnungKst = scan.next();
            System.out.println("Verantwortliche Person der Kostenstelle: ");
            kostenstelleVerantPerson = scan.nextLine();

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
            waehrung = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(3) - 1;


            // Abfrage Datenbank nach Kostenstellen
            HashMap<Integer,Integer> kostenstelleMap = datenAuslesenfuerAbfrageInt("Kostenstelle","Kostenstelle");

            int j = 1;

            // Schreiben der Kostenstellen in ein Array
            Integer[] kostenstelleArray = new Integer[kostenstelleMap.size() + 1];

            for (Map.Entry<Integer,Integer> map: kostenstelleMap.entrySet()){
                kostenstelleArray[j] = map.getKey();
                // Ausgeben des Array
                System.out.println(j + ". " + map.getKey());
                j++;

            }
            System.out.println("Bitte wählen sie die Kostenstelle (1-" + (kostenstelleArray.length - 1) + ")");
            kostenstelleId = kostenstelleMap.get(kostenstelleArray[BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(kostenstelleArray.length)]);



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

            // Abfrage Datenbank nach Kostenstellen
            HashMap<Integer,Integer> kostenstelleMap = datenAuslesenfuerAbfrageInt("Kostenstelle","Kostenstelle");

            int i = 1;

            // Schreiben der Kostenstellen in ein Array
            Integer[] kostenstelleArray = new Integer[kostenstelleMap.size() + 1];

            for (Map.Entry<Integer,Integer> map: kostenstelleMap.entrySet()){
                kostenstelleArray[i] = map.getKey();
                     // Ausgeben des Array
                        System.out.println(i + ". " + map.getKey());
                            i++;

            }
            System.out.println("Bitte wählen sie die Kostenstelle der Organisation (1-" + (kostenstelleArray.length - 1) + ")");
            kostenstelleId = kostenstelleMap.get(kostenstelleArray[BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(kostenstelleArray.length)]);

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Stellenbezeichnung: " + stellenbezeichnung + "\tBezeichnung der Kostenstelle: " + kostenstelleId);
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
}
