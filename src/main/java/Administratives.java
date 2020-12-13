import java.util.Scanner;

public class Administratives extends Datenbank{


    String[] unterMenue = {"Menü für Administratives", "1. Kostenstelle anlegen", "2. Budget erfassen pro Kostenstelle", "3. Oranisation anlegen", "4. Organisation aendern", "5. Hauptmenü"};

    String stellenbezeichnung;
    String kostenstelleId;
    int kostenstelle;
    String bezeichnungKst;
    String kostenstelleVerantPerson;

    Scanner scan = new Scanner(System.in);

    //Konstruktor
    Administratives(){
    untermenueAnzeigen();

    }


    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    kostenstelleAnlegen();
                    break;
                case 2:
                    System.out.println("budgetErfassen");
                    break;
                case 3:
                    System.out.println("organisationAnlegen");
                    datenAuslesen();
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

    void kostenstelleAnlegen(){

        boolean abschliessen = true;

        do {

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            System.out.print("Kostenstelle: ");
            kostenstelle = scan.nextInt();
            System.out.print("Bezeichnung der Kostenstelle: ");
            bezeichnungKst = scan.next();
            System.out.print("Verantwortliche Person der Kostenstelle: ");
            kostenstelleVerantPerson = scan.next();

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Kostenstelle: " + kostenstelle + "\tBezeichnung der Kostenstelle: " + bezeichnungKst + "\tVerantwortliche Person der Kostenstelle: " + kostenstelleVerantPerson);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabebestätigen()){

                case 1: datenAnlegen(this,"Kostenstelle");
                        break;
                case 2: abschliessen = false;
                        break;
                case 3: break;
            }
        }while(!abschliessen);

    }

}
