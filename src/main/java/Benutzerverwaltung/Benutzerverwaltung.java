package Benutzerverwaltung;

import Utilities.BefehlsZeilenSchnittstelle;

public class Benutzerverwaltung {

    String [] unterMenue = {"Benutzerverwaltung", "1.  Benutzer anlegen", "2.  Benutzer sperren", "3.  Benutzer ändern", "99. Hauptmenü"};

    public Benutzerverwaltung(){

        untermenueAnzeigen();

    }

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    System.out.println(unterMenue[1]);
                    break;
                case 2:
                    System.out.println(unterMenue[2]);
                    break;
                case 3:
                    System.out.println(unterMenue[3]);
                    break;
                case 99:
                    System.out.println(unterMenue[4]);
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }
}
