package Administratives;

import Utilities.BefehlsZeilenSchnittstelle;

public class Administratives{


    private String[] unterMenue = {"Administratives", "1.  Kostenstelle Anlegen", "2.  Kostenstelle Mutation", "3.  Budget pro Kostenstelle erfassen", "4.  Budget Mutation", "99. Hauptmenü"};


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
                    new Kostenstelle().kostenstelleMutieren();
                    break;
                case 3:
                    new Budget().budgetAnlegen();
                    break;
                case 4:
                    new Budget().budgetMutieren();
                    break;
                case 99:
                    //zurück ins Hauptmenü;
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }


}
