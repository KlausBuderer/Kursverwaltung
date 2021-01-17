package Kurse;

import Datenbank.KursDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Kurse{

    private String [] unterMenue = {"Kurse", "1.  Kurse Anlegen", "2.  Kurse Mutation", "99. Hauptmenue"};
    public int kurseId;
    public int kosten = 1;
    public String waehrung;
    public String kursCode;
    public String anbieter;
    public String kursBeschreibung;
    public String datumVon;
    public String datumBis;
    public String durchfuehrungsOrt;


    Scanner scan = new Scanner(System.in);

    public Kurse(){
    untermenueAnzeigen();

    }


    public Kurse(int kurseId, int kosten, String waehrung, String kursCode, String anbieter, String kursBeschreibung, String datumVon, String datumBis, String durchfuerungsOrt) {
        this.kurseId = kurseId;
        this.kosten = kosten;
        this.waehrung = waehrung;
        this.kursCode = kursCode;
        this.anbieter = anbieter;
        this.kursBeschreibung = kursBeschreibung;
        this.datumVon = datumVon;
        this.datumBis = datumBis;
        this.durchfuehrungsOrt = durchfuerungsOrt;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anzeigen des Untermenüs
     */
    public void untermenueAnzeigen(){



        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    kurseAnlegen();
                    break;
                case 2:
                    kurseBearbeiten();
                    break;
                case 99:
                    //zurück ins Hauptmenü;
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
    Methode zum Anlegen von Kursen

     */


    void kurseAnlegen() {

        boolean abschliessen = true;

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Kurs Code
            kursCode = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Kurs Code: ");
            //Anbieter
            anbieter = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Anbieter: ");
            //Kursbeschreibung
            kursBeschreibung = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Kursbeschreibung: ");
            //Kosten
            kosten = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kosten: ");
            //Waehrung
            waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
            //Datum von
            datumVon = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Start Datum: ");
            //Datum bis
            datumBis = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("End-Datum: ");
            //Durchfuehrungsort
            durchfuehrungsOrt = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Durchfuehrungsort: ");

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    KursDatenbank kursDatenbank = new KursDatenbank();
                        kursDatenbank.datenAnlegen(this);
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
    Methode zur Bearbeitung eines Budget
     */
    void kurseBearbeiten() {

        String[] spaltenArray = {"Kurs Code","Anbieter","Kursbeschreibung", "Kosten", "Waehrung", "Start Datum", "End Datum", "Durchfuehrungs Ort"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;

        auswahlListeKurseAusgeben();

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            int i = 1;
            for (String spalte : spaltenArray) {

                System.out.println(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            System.out.print("Welchen Spalte möchten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + kursCode);
                    System.out.println("Geben sie einen neuen Code ein: ");
                    kursCode = scan.next();
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + anbieter);
                    System.out.print("Geben sie einen neuen Anbieter an: ");
                    anbieter = scan.next();
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + kursBeschreibung);
                    System.out.print("Geben sie eine neue Kursbeschreibung ein: ");
                    kursBeschreibung = scan.next();
                    break;
                case 4:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + kosten);
                    System.out.print("Geben sie die neuen Kosten ein: ");
                    kosten = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
                    break;
                case 5:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + waehrung);
                    waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
                    break;
                case 6:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + datumVon);
                    System.out.print("Geben sie das neue Start Datum ein: ");
                    datumVon = BefehlsZeilenSchnittstelle.pruefeDatum();
                    break;
                case 7:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + datumBis);
                    System.out.print("Geben sie das neue End Datum ein: ");
                    datumBis = BefehlsZeilenSchnittstelle.pruefeDatum();
                    break;
                case 8:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + durchfuehrungsOrt);
                    System.out.print("Geben sie den neuen Durchfuehrungsort ein: ");
                    durchfuehrungsOrt = scan.next();
                    break;
                default:
                    System.out.println("Falsche Eingabe!");
                    break;
            }

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1:
                    KursDatenbank kursDatenbank = new KursDatenbank();
                    kursDatenbank.datenMutation(this);
                    abschliessen = true;
                    break;
                case 2: abschliessen = false;
                    break;
                case 3: abschliessen = true;
                    break;
            }

        }while(!abschliessen);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     */

    public void auswahlListeKurseAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        KursDatenbank kursDatenbank = new KursDatenbank();

        // Abfrage Datenbank.Datenbank nach Kursen
        HashMap<Kurse, Integer> kursMap = (HashMap<Kurse, Integer>) kursDatenbank.dbHashMap("Kurse");


        // Schreiben der Kostenstellen in ein Array
        Kurse[] kursArray = new Kurse[kursMap.size() + 1];

        for (Map.Entry<Kurse, Integer> map : kursMap.entrySet()) {
            kursArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString());
            i++;
        }

        arrayLaenge = kursArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        System.out.print("Bitte wählen sie einen Kurs aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        // Die Daten des gewählten Objekts werden in das sich vorhandene Objekt geschrieben
        kurseId = kursArray[auswahl].kurseId;
        kosten = kursArray[auswahl].kosten;
        waehrung = kursArray[auswahl].waehrung;
        kursCode = kursArray[auswahl].kursCode;
        anbieter = kursArray[auswahl].anbieter;
        kursBeschreibung = kursArray[auswahl].kursBeschreibung;
        datumVon = kursArray[auswahl].datumVon;
        datumBis = kursArray[auswahl].datumBis;
        durchfuehrungsOrt = kursArray[auswahl].durchfuehrungsOrt;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    ToString Methode

     */

    @Override
    public String toString() {
        return

                " KursCode: " + kursCode +
                " Anbieter: " + anbieter +
                        " Kosten: " + kosten +
                " KursBeschreibung: " + kursBeschreibung +
                " DatumVon: " + datumVon +
                " DatumBis: " + datumBis +
                " DurchfuerungsOrt: " + durchfuehrungsOrt +
                " Waehrung: " + waehrung;
    }
}
