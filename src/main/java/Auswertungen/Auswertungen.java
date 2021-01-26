package Auswertungen;

import Datenbank.AuswertungenDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

public class Auswertungen {

    private String[] unterMenue = {"Auswertungen", "1.  Mitarbeiter", "2.  Kurse Anzeigen", "3.  Budget", "4.  Ausgaben", "99. Hauptmenue"};
    private String[] unterMenueMitarbeiter = {"Mitarbeiter", "1.  Aktive Mitarbeiter", "2.  Auswertung Anzahl Mitarbeiter pro Kostenstelle", "3.  Abgelaufene Zertifikate", "99. Zurueck"};
    private String[] unterMenueKurse = {"Kurse", "1.  Kurse pro Mitarbeiter", "2.  dddd", "99.  Zurueck"};
    private String[] unterMenueBudget = {"Budget", "1.  Budgetuebersicht pro Kostenstelle", "2.  Budget Ist/Soll Vergleich", "99.  Zurueck"};
    private String[] unterMenueAusgaben = {"Ausgaben", "1.  Kurskosten pro Anbieter / Jahr / Waehrung alle Mitarbeiter", "2.  Kurskosten pro Jahr / Anbieter / Kostenstelle", "99.  Zurueck"};
    private final String[] viewBefehle = {"view_Bruno_TEST", "view_kurse_auswertung"};

    public Auswertungen() {
        untermenuAnzeigen();
    }

    public Auswertungen(String kontext) {
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anzeigen des Untermenüs
     */
    private void untermenuAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    unterMenuMitarbeiterAnzeigen();
                    break;
                case 2:
                    unterMenueKurseAnzeigen();
                    break;
                case 3:
                    unterMenueBudgetAnzeigen();
                    break;
                case 4:
                    unterMenueAusgabenAnzeigen();
                    break;
                case 99:
                    System.out.println("Hauptmenue");
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        } while (!gueltigeEingabe);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anzeigen des Untermenüs für die Auswertungen der Mitarbeiter
     */
    private void unterMenuMitarbeiterAnzeigen() {
        boolean gueltigeEingabe = false;
        MitarbeiterAuswertung mitarbeiterAuswertung = new MitarbeiterAuswertung();

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueMitarbeiter)) {
                case 1:
                    mitarbeiterAuswertung.ausgabeListe(new AuswertungenDatenbank().auswertungAusDbLesen(viewBefehle[0]));
                    break;
                case 2:
                    System.out.println(unterMenueMitarbeiter[1]);
                    break;
                case 3:
                    System.out.println(unterMenueMitarbeiter[2]);
                    break;
                case 99:
                    System.out.println(unterMenueMitarbeiter[3]);
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        } while (!gueltigeEingabe);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anzeigen des Untermenüs für die Auswertungen der Kurse
     */
    private void unterMenueKurseAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueKurse)) {
                case 1:
                    System.out.println(unterMenueKurse[0]);
                    break;
                case 2:
                    System.out.println(unterMenueKurse[1]);
                    break;
                case 99:
                    System.out.println(unterMenueKurse[2]);
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        } while (!gueltigeEingabe);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
 Methode zum Anzeigen des Untermenüs für die Auswertungen des Budget
  */
    private void unterMenueBudgetAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueBudget)) {
                case 1:
                    System.out.println(unterMenueBudget[0]);
                    break;
                case 2:
                    System.out.println(unterMenueBudget[1]);
                    break;
                case 99:
                    System.out.println(unterMenueBudget[2]);
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        } while (!gueltigeEingabe);
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
 Methode zum Anzeigen des Untermenüs für die Auswertungen der Ausgaben
  */
    private void unterMenueAusgabenAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueAusgaben)) {
                case 1:
                    System.out.println(unterMenueAusgaben[0]);
                    break;
                case 2:
                    System.out.println(unterMenueAusgaben[1]);
                    break;
                case 99:
                    System.out.println(unterMenueMitarbeiter[3]);
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        } while (!gueltigeEingabe);
    }
}
