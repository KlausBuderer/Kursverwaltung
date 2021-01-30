package Benutzerverwaltung;

import Utilities.BefehlsZeilenSchnittstelle;

public class Benutzerverwaltung {

    String [] unterMenue = {"Benutzerverwaltung", "1.  Benutzer Anlegen", "2.  Benutzer Loeschen", "3.  Benutzer Passwort mutieren", "99. Hauptmenü"};

    public Benutzerverwaltung(){

        untermenueAnzeigen();

    }

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    new Benutzer().benutzerAnlegen();
                    break;
                case 2:
                    new Benutzer().benutzerLoeschen();
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
