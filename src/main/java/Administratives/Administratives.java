package Administratives;

import Utilities.BefehlsZeilenSchnittstelle;

public class Administratives{


    private String[] unterMenue = {"1.  Kostenstelle Anlegen", "2.  Kostenstelle Mutation", "3.  Budget pro Kostenstelle erfassen", "4.  Budget Mutation", "99. Hauptmenue"};


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor anzeigen Hauptmenue
    public Administratives() {
        untermenueAnzeigen();

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Die Methode untermenueAnzeige zeigt das Untermenue und fuehrt anhand der Eingabe des Benutzers eine Aktion aus
     */

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue,"Administratives")) {
                case 1:
                    new Kostenstelle().kostenstelleAnlegen();
                    break;
                case 2:
                    new Kostenstelle().kostenstelleMutieren();
                    break;
                case 3:
                    new Budget().budgetAnlegen();
                    break;
                case 4:
                    new Budget().budgetMutieren();
                    break;
                case 99:
                    //zurueck ins Hauptmenue;
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }


}
