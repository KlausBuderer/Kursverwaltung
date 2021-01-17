package Zertifikate;

import Datenbank.ZertifikatsDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Zertifikate{

    String [] unterMenue = {"Zertifikate", "1.  Zertifikate Anlegen", "2.  Zertifikate Mutation", "99. Hauptmenue"};
    public int zertifikatsId;
    public int kosten;
    public String waehrung;
    public String zertifikatsTitel;
    public String zertifikatsBeschreibung;
    public String anbieter;
    public String sprache;

    Scanner scan = new Scanner(System.in);


    public Zertifikate(){

        untermenueAnzeigen();

    }

    public Zertifikate(int zertifikatsId, int kosten, String zertifikatsTitel, String zertifikatsBeschreibung, String anbieter, String sprache, String waehrung) {
        this.zertifikatsId = zertifikatsId;
        this.kosten = kosten;
        this.zertifikatsTitel = zertifikatsTitel;
        this.zertifikatsBeschreibung = zertifikatsBeschreibung;
        this.anbieter = anbieter;
        this.sprache = sprache;
        this.waehrung = waehrung;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anezigeb des Untermenues

     */
    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    zertifikatAnlegen();
                    break;
                case 2:
                    zertifikatBearbeiten();
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
    Methode zum Anlegen von Zertifikaten

     */
    void zertifikatAnlegen() {

        boolean abschliessen = true;

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Zertifikats Titel
            zertifikatsTitel = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Zertifikats Titel: ");
            //Anbieter
            anbieter = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Anbieter: ");
            //Beschreibung
            zertifikatsBeschreibung = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Beschreibung: ");
            //Kosten
            kosten = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kosten: ");
            //Waehrung
            waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
            //Sprache
            sprache = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Sprache: ");

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    ZertifikatsDatenbank zertifikatsDatenbank = new ZertifikatsDatenbank();
                    zertifikatsDatenbank.datenAnlegen(this);
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
    void zertifikatBearbeiten() {

        String[] spaltenArray = {"Zertifikatstitel","Anbieter","Zertifikatsbeschreibung", "Kosten", "Waehrung", "Sprache"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;

        auswahlListeZertifikateAusgeben();

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
                    System.out.println("Aktuell: " + zertifikatsTitel);
                    System.out.println("Geben sie einen neuen Titel ein: ");
                    zertifikatsTitel = scan.next();
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + anbieter);
                    System.out.print("Geben sie einen neuen Anbieter an: ");
                    anbieter = scan.next();
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + zertifikatsBeschreibung);
                    System.out.print("Geben sie eine neue Kursbeschreibung ein: ");
                    zertifikatsBeschreibung = scan.next();
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
                    System.out.println("Aktuell: " + sprache);
                    sprache = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie eine neue Sprache ein: ");
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
                    ZertifikatsDatenbank zertifikatsDatenbank = new ZertifikatsDatenbank();
                    zertifikatsDatenbank.datenMutation(this);
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
    public void auswahlListeZertifikateAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        ZertifikatsDatenbank zertifikatsDatenbank = new ZertifikatsDatenbank();

        // Abfrage Datenbank.Datenbank nach Zertifikaten
        HashMap<Zertifikate, Integer> zertifikatMap = (HashMap<Zertifikate, Integer>) zertifikatsDatenbank.dbHashMap("Zertifikate");


        // Schreiben der Kostenstellen in ein Array
        Zertifikate[] zertifikatArray = new Zertifikate[zertifikatMap.size() + 1];

        for (Map.Entry<Zertifikate, Integer> map : zertifikatMap.entrySet()) {
            zertifikatArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString());
            i++;
        }

        arrayLaenge = zertifikatArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        System.out.print("Bitte wählen sie ein Zertifikat aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        // Die Daten des gewählten Objekts werden in das sich vorhandene Objekt geschrieben
        zertifikatsId = zertifikatArray[auswahl].zertifikatsId;
        kosten = zertifikatArray[auswahl].kosten;
        waehrung = zertifikatArray[auswahl].waehrung;
        zertifikatsTitel = zertifikatArray[auswahl].zertifikatsTitel;
        anbieter = zertifikatArray[auswahl].anbieter;
        zertifikatsBeschreibung = zertifikatArray[auswahl].zertifikatsBeschreibung;
        sprache = zertifikatArray[auswahl].sprache;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    ToString Methode
     */
    @Override
    public String toString() {
        return  "Titel: " + BefehlsZeilenSchnittstelle.textFormatieren(zertifikatsTitel, 30 )+
                "ZertifikatsBeschreibung: " + BefehlsZeilenSchnittstelle.textFormatieren(zertifikatsBeschreibung, 50 ) +
                "Anbieter: " + BefehlsZeilenSchnittstelle.textFormatieren(anbieter, 15 ) +
                "Sprache: " + BefehlsZeilenSchnittstelle.textFormatieren(sprache, 10 ) +
                "Kosten: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(kosten), 10 ) +
                "Waehrung: " + BefehlsZeilenSchnittstelle.textFormatieren(waehrung, 30 ) ;
    }
}
