package Logik.Administratives;

import PraesentationSchicht.BefehlsZeilenSchnittstelle;

public class Administratives{


    private final String[] UNTERMENUE = {"1.  Kostenstelle Anlegen", "2.  Kostenstelle Mutation", "3.  Budget pro Kostenstelle erfassen", "4.  Budget Mutation", "99. Hauptmenue"};

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
            switch (BefehlsZeilenSchnittstelle.unterMenue(UNTERMENUE, "Logik/Administratives")) {
                case 1:
                    new Kostenstelle().datenAnlegen();
                    break;
                case 2:
                    new Kostenstelle().datenMutieren();
                    break;
                case 3:
                    new Budget().datenAnlegen();
                    break;
                case 4:
                    new Budget().datenMutieren();
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
