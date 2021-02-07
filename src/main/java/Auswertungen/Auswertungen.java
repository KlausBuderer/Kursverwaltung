package Auswertungen;

import Utilities.BefehlsZeilenSchnittstelle;

public class Auswertungen {

    private String[] unterMenue = {"Auswertungen", "1.  Mitarbeiter", "2.  Weiterbildung", "3.  Budget / Ausgaben", "99. Hauptmenue"};
    private String[] unterMenueMitarbeiter = {"Mitarbeiter", "1. Kursuebersicht fuer gewuenschten Mitarbeiter ", "2. Zertifikatsuebersicht fuer gewuenschten Mitarbeiter", "99. Zurueck"};
    private String[] unterMenueWeiterbildung = {"Weiterbildung", "1.  Weiterbildung aller Mitarbeiter", "2.  Zertifikate aller Mitarbeiter", "3. Kurse aller Mitarbeiter", "4.  Pruefung ablaufende Zertifikate", "99.  Zurueck"};
    private String[] unterMenueBudgetAusgaben = {"Budget / Ausgaben", "1. 99Budget pro Kostenstelle / Jahr", "2. Kurskosten fuer gewuenschte Kostenstelle mit Anbieter / Waehrung / Zeitraum ","3. Kurskosten alle Kostenstellen / Waehrung / Anbieter ", "99.  Zurueck"};

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
                    unterMenueWeiterbildungAnzeigen();
                    break;
                case 3:
                    unterMenueBudgetAusgabenAnzeigen();
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


        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueMitarbeiter)) {
                case 1:
                    new WeiterbildungAlleMitarbeiterZeitraum().auswertungAusgeben();
                    break;
                case 2:
                    System.out.println(unterMenueMitarbeiter[1]);
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
    private void unterMenueWeiterbildungAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueWeiterbildung)) {
                case 1:
                    new WeiterbildungAlleMitarbeiterZeitraum().auswertungAusgeben();
                    break;
                case 2:
                    new ZertifikateAlleMitarbeiter().auswertungAusgeben();
                    break;
                case 3:
                    new KurseAlleMitarbeiter().auswertungAusgeben();
                    break;
                case 4:
                    System.out.println(unterMenueWeiterbildung[1]);
                    break;
                case 99:
                    System.out.println(unterMenueWeiterbildung[2]);
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
 Methode zum Anzeigen des Untermenüs für die Auswertungen von Budget und Ausgaben
  */
    private void unterMenueBudgetAusgabenAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueBudgetAusgaben)) {
                case 1:
                    System.out.println(unterMenueBudgetAusgaben[0]);
                    break;
                case 2:
                    System.out.println(unterMenueBudgetAusgaben[1]);
                    break;
                case 99:
                    System.out.println(unterMenueBudgetAusgaben[2]);
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        } while (!gueltigeEingabe);
    }
}
