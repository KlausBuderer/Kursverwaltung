package Benutzerverwaltung;

import Utilities.BefehlsZeilenSchnittstelle;

public class Benutzerverwaltung {

    String [] unterMenue = {"1.  Benutzer Anlegen", "2.  Benutzer Loeschen", "3.  Benutzer Passwort mutieren", "99. Hauptmenue"};

    public Benutzerverwaltung(){

        untermenueAnzeigen();

    }

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue,"Benutzerverwaltung")) {
                case 1:
                    new Benutzer().benutzerAnlegen();
                    break;
                case 2:
                    new Benutzer().benutzerLoeschen();
                    break;
                case 3:
                    new Benutzer().passwortAendern();
                    break;
                case 99:
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }
}
