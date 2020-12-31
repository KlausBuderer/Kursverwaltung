import java.util.Scanner;

public class Zertifikate extends Datenbank{

    String [] unterMenue = {"Zertifikate", "1. Zertifikate anlegen", "2. Zertifikate auslesen", "3. Gültigkeitsdauer von Zertifikate verlängern", "4. Hauptmenü"};

    int zertifikatsId;
    int kosten;
    int waehrung = 1;
    String zertifikatsTitel;
    String zertifikatsBeschreibung;
    String anbieter;
    String sprache;
    String [] waehrungsArray = {"CHF", "EUR"};


    Scanner scan = new Scanner(System.in);


    Zertifikate(){

        untermenueAnzeigen();

    }

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    zertifikatAnlegen();
                    break;
                case 2:
                    System.out.println("Zertifikate auslesen");
                    break;
                case 3:
                    System.out.println("Gültigkeitsdauer von Zertifikat verlängern");
                    break;
                case 4:
                    //zurück ins Hauptmenü;
                    System.out.println("Hauptmenü");
                    System.out.println("Hauptmenü");
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anlegen von Zertifikaten

     */


    void zertifikatAnlegen() {

        boolean abschliessen = true;

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Zertifikats Titel
            System.out.print("Zertifikats Titel: ");
            zertifikatsTitel = scan.next();
            //Anbieter
            System.out.print("Anbieter: ");
            anbieter = scan.next();
            //Beschreibung
            System.out.print("Beschreibung: ");
            zertifikatsBeschreibung = scan.next();
            //Kosten
            System.out.print("Kosten: ");
            kosten = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
            //Waehrung
            int i = 1;

            for (String waehrung: waehrungsArray) {
                System.out.println(i + ". " + waehrung);
                i++;
            }
            System.out.println("Wahrung (1-2): ");
            waehrung = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1;
            //Sprache
            System.out.print("Sprache: ");
            sprache = scan.next();


            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabebestaetigen()) {

                case 1:
                    datenAnlegenAllgemein(anlegenQuerry());
                    abschliessen = true;
                    break;
                case 2:
                    abschliessen = false;
                    break;
                case 3:
                    abschliessen = true;
                    break;

            }
        }while (!abschliessen) ;



    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für einen Update von Kostenstelle

     */

    String updateQuerry(){

        String querry;

        querry = "UPDATE `itwisse_kursverwaltung`.`Zertifikate` SET " +
                " `Titel` = " + zertifikatsTitel +
                ", `Beschreibung` = " + zertifikatsBeschreibung +
                ", `Anbieter` = " + anbieter +
                ", `Sprache` = " + sprache +
                ", `Kosten` = " + kosten +
                ", `Waehrung` = " + waehrungsArray[waehrung] +
                " WHERE `ID` = " + zertifikatsId + ";";
        return querry;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses

     */

    String anlegenQuerry(){

        return "INSERT INTO `itwisse_kursverwaltung`.`Zertifikate`" +
                " (`Titel`, `Beschreibung`, `Anbieter`, `Sprache`, `Kosten`, `Waehrung`)" +
                " VALUES ('" + zertifikatsTitel + "', '" + zertifikatsBeschreibung + "', '" + anbieter + "', '" + sprache + "', '" + kosten + "', '" +
                waehrungsArray[waehrung] + "')";

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    ToString Methode

     */

    @Override
    public String toString() {
        return  "Titel: " + BefehlsZeilenSchnittstelle.textFormatieren(zertifikatsTitel, 20 )+
                "zertifikatsBeschreibung: " + BefehlsZeilenSchnittstelle.textFormatieren(zertifikatsBeschreibung, 50 ) +
                "anbieter: " + BefehlsZeilenSchnittstelle.textFormatieren(anbieter, 15 ) +
                "sprache: " + BefehlsZeilenSchnittstelle.textFormatieren(sprache, 10 ) +
                "kosten: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(kosten), 10 ) +
                "waehrung: " + BefehlsZeilenSchnittstelle.textFormatieren(waehrungsArray[waehrung], 30 ) ;





    }
}
