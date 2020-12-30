import java.util.Arrays;
import java.util.Scanner;

public class Kurse extends Datenbank {

    String [] unterMenue = {"Kurse", "1. Kurse anlegen", "2. Kurse auslesen", "3. Hauptmenue"};

    int kurseId;
    int kosten;
    int waehrung;
    String kursCode;
    String anbieter;
    String kursBeschreibung;
    String datumVon;
    String datumBis;
    String durchfuerungsOrt;
    String[] waehrungsArray = {"CHF","EUR"};

    Scanner scan = new Scanner(System.in);

    Kurse(){
    untermenueAnzeigen();

    }

    public void untermenueAnzeigen(){



        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    kurseAnlegen();
                    break;
                case 2:
                    System.out.println("Kurse auslesen");
                    break;
                case 3:
                    //zurück ins Hauptmenü;
                    System.out.println("Hauptmenü");
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }


    void kurseAnlegen() {

        boolean abschliessen = true;

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Kurs Code
            System.out.print("Kurs Code: ");
            kursCode = scan.next();
            //Anbieter
            System.out.print("Anbieter: ");
            anbieter = scan.next();
            //Kursbeschreibung
            System.out.print("Kursbeschreibung: ");
            kursBeschreibung = scan.next();
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
            //Datum von
            System.out.print("Start Datum: ");
            datumVon = BefehlsZeilenSchnittstelle.pruefeDatum();

            //Datum bis
            System.out.print("End-Datum: ");
            datumBis = BefehlsZeilenSchnittstelle.pruefeDatum();
            //kostenstelle (organisation)
            System.out.print("Durchfuehrungsort: ");
            durchfuerungsOrt = scan.next();

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

        querry = "UPDATE `itwisse_kursverwaltung`.`Kurse` SET " +
                " `KursCode` = " + kursCode +
                ", `Anbieter` = " + anbieter +
                ", `Kursbeschreibung` = " + kursBeschreibung +
                ", `Kosten` = " + kosten +
                ", `Waehrung` = " + waehrung +
                ", `DatumVon` = " + datumVon +
                ", `DatumBis` = " + datumBis +
                ", `Durchfuehrungsort` = " + durchfuerungsOrt +
                " WHERE `ID` = " + kurseId + ";";
        return querry;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses

     */

    String anlegenQuerry(){

        return "INSERT INTO `itwisse_kursverwaltung`.`Kurse`" +
                " (`KursCode`, `Anbieter`, `Kursbeschreibung`, `Kosten`, `Waehrung`, `DatumVon`, `DatumBis`, `Durchfuehrungsort`)" +
                " VALUES ('" + kursCode + "', '" + anbieter + "', '" + kursBeschreibung + "', '" + kosten + "', '" + waehrung + "', '" +
                datumVon + "', '" + datumBis + "', '" + durchfuerungsOrt +"')";

    }

    @Override
    public String toString() {
        return

                " kursCode: " + kursCode +
                ", anbieter: " + anbieter +
                        ", kosten: " + kosten +
                ", kursBeschreibung: " + kursBeschreibung +
                ", datumVon: " + datumVon +
                ", datumBis: " + datumBis +
                ", durchfuerungsOrt: '" + durchfuerungsOrt +
                ", waehrungsArray: " + waehrungsArray[waehrung];
    }
}
