package Logik.Auswertungen;

import PraesentationSchicht.BefehlsZeilenSchnittstelle;

public class Auswertungen {

    private String[] unterMenue = {"1.  Mitarbeiter", "2.  Weiterbildung", "3.  Budget / Ausgaben", "4.  Stammdaten", "99. Hauptmenü"};
    private String[] unterMenueMitarbeiter = {"1.  Kursübersicht fuer gewünschten Mitarbeiter ", "2.  Zertifikatsübersicht fuer gewünschten Mitarbeiter", "99. Zurück"};
    private String[] unterMenueWeiterbildung = {"1.  Weiterbildung aller Mitarbeiter", "2.  Zertifikate aller Mitarbeiter", "3.  Kurse aller Mitarbeiter", "4.  Prüfung ablaufende Zertifikate", "99. Zurueck"};
    private String[] unterMenueBudgetAusgaben = {"1.  Budget pro Kostenstelle / Jahr", "2.  Kurskosten alle Kostenstellen / Waehrung / Anbieter ","3.  Kurskosten fuer gewünschte Kostenstelle mit Anbieter / Währung / Zeitraum ", "99. Zurueck"};
    private String[] unterMenueStammdaten = {"1.  Anzeige alle Mitarbeiter", "2.  Anzeige alle Kostenstellen", "99. Zurück"};

    public Auswertungen() {
        untermenuAnzeigen();
    }

    public Auswertungen(String kontext) {
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anzeigen des Untermenues
     */
    private void untermenuAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue, "Logik/Auswertungen")) {
                case 1:
                    unterMenuMitarbeiterAnzeigen();
                    break;
                case 2:
                    unterMenueWeiterbildungAnzeigen();
                    break;
                case 3:
                    unterMenueBudgetAusgabenAnzeigen();
                    break;
                case 4:
                    unterMenueStammdatenAnzeigen();
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
    Methode zum Anzeigen des Untermenues fuer die Auswertungen der Mitarbeiter
     */
    private void unterMenuMitarbeiterAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueMitarbeiter,"Auswertungen Mitarbeiter")) {
                case 1:
                    new KursProMitarbeiter().auswertungAusgeben();
                    break;
                case 2:
                    new ZertifikatProMitarbeiter().auswertungAusgeben();
                    break;
                case 99:
                    System.out.println(unterMenueMitarbeiter[1]);
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
    Methode zum Anzeigen des Untermenues fuer die Auswertungen der Kurse
     */
    private void unterMenueWeiterbildungAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueWeiterbildung,"Auswertungen Weiterbildungen")) {
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
                    new ZertifikateAlleMitarbeiterGueltigkeit().auswertungAusgeben();
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
 Methode zum Anzeigen des Untermenues fuer die Auswertungen von Budget und Ausgaben
  */
    private void unterMenueBudgetAusgabenAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueBudgetAusgaben,"Auswertungen Budget & Ausgaben")) {
                case 1:
                    new BudgetAlleKostenstellen().auswertungAusgeben();
                    break;
                case 2:
                    new KurseProAnbieterKostenstellenZeitraum().auswertungAusgeben();
                    break;
                case 3:
                    new KurseProAnbieterSelektivKostenstelleZeitraum().auswertungAusgeben();
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
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  /*
 Methode zum Anzeigen des Untermenues fuer die Auswertungen von Budget und Ausgaben
  */
    private void unterMenueStammdatenAnzeigen() {
        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenueStammdaten,"Auswertungen Stammdaten")) {
                case 1:
                    new StammdatenAlleMitarbeiter().auswertungAusgeben();
                    break;
                case 2:
                    new KostenstellenAlle().auswertungAusgeben();
                    break;
                case 99:
                    System.out.println(unterMenueStammdaten[2]);
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        } while (!gueltigeEingabe);
    }
}
