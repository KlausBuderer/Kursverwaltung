package Kurse;

import Datenbank.KursDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.util.HashMap;
import java.util.Map;

public class KursSuchen {

    private String suchText;
    private String query;
    private final String[] TABELLENHEADER = {"Nr.","Kurs Code", "Anbieter", "Kursbeschreibung", "Kosten", "Waehrung", "Start-Datum", "End-Datum", "Durchführungsort"};
    private final String[] SUCHKRITERIEN = {"Kurs Code", "Anbieter", "Kursbeschreibung", "Kosten", "Waehrung", "Start-Datum", "End-Datum", "Durchführungsort"};
    private final String[] SPALTENBEZEICHNUNG = {"KursCode", "Anbieter", "Kursbeschreibung", "Kosten", "Waehrung", "DatumVon", "DatumBis", "Durchführungsort"};



    public Kurse kursSuchen(){

        boolean abbruchBedingung = false;
        HashMap<?, Integer> kursHash;

        do {
            //Suchkriterium Abfragen
            int suchkriterium = suchkriterienAbfragen();
            //Suchtext zum gewählten Kriterium Abfragen
            suchText = suchTextEinlesen(suchkriterium);
            //Datenbankquery zusammenstellen anhand den Angaben
            query = new KursDatenbank().queryFuerAnzahlAbfrage(SPALTENBEZEICHNUNG[suchkriterium - 1], suchText);
            //Hashmap mit allen Treffern erstellen
            kursHash = new KursDatenbank().kursSuchen(query);

            BefehlsZeilenSchnittstelle.bildReinigen();

            if (kursHash.size() > 20) {
                System.out.println("Zuviele Treffer!");
                System.out.println("Bitte geben sie genauere Angaben an");
                BefehlsZeilenSchnittstelle.verzoegerung(2000);
            } else if (kursHash.size() > 0) {
                // Mitarbeiterliste ausgeben und Auswahl Einlesen
                abbruchBedingung = true;
                return auswahlListeKurseAusgeben(kursHash);
            } else {
                System.out.println("Keine Treffer");
            }
        }while(!abbruchBedingung);
        return null;
    }



    /*--------------------------------------------------------------------------------------------------------------------------------------
       Methode die eine Auswahl an Suchkriterien Ausgibt und eine Auswahl vom Bediener liest
      +Rückgabewert: Auswahl als Integer
        */
    private int suchkriterienAbfragen() {

        String[] HEADER = {"Nr.","Suchkriterium"};

        BefehlsZeilenSchnittstelle.bildReinigen();
        System.out.println("Bitte wählen sie ein Suchkriterium: ");

        int auswahlNummer = 1;

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(HEADER);
        tabelle.setVertikaleLinie(true);

        for (String suchkriterium : SUCHKRITERIEN) {
            String[] tempArray = {"",""};
            tempArray[0] = auswahlNummer + ".";
            tempArray[1] = suchkriterium;
            tabelle.zeileHinzufuegen(tempArray);
           // System.out.println(auswahlNummer + ". " + suchkriterium);
            auswahlNummer++;
        }
        tabelle.ausgabe();
        System.out.print("Nach welchem Kriterium möchten sie suchen: ");
        return BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(SUCHKRITERIEN.length);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die die Eingabe des Suchtextes einliest
   Rückgabewert: Suchtext als String
     */
    private String suchTextEinlesen(int auswahl) {

        int suchZahl = 0;


        switch (auswahl) {
            case 1:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Geben sie den Kurs Code ein nach dem sie suchen: ");
                suchText = String.valueOf(suchZahl);
                break;
            case 2:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Anbieter des Kurses ein nach dem sie suchen möchten: ");
                break;
            case 3:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die Kursbeschreibung ein nach dem sie suchen möchten: ");
                break;
            case 4:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Gebn sie die Kosten des Kurses ein nach dem sie suchen möchten: ");
                break;
            case 5:
                suchText = BefehlsZeilenSchnittstelle.abfrageWaehrung();
                break;
            case 6:
                System.out.println("Geben sie das Startdatum des Kurses ein nach dem sie suchen möchten: ");
                suchText = BefehlsZeilenSchnittstelle.pruefeDatum();
                break;
            case 7:
                System.out.println("Geben sie das Enddatum des Kurses ein nach dem sie suchen möchten: ");
                suchText = BefehlsZeilenSchnittstelle.pruefeDatum();
                break;
            case 8:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Durchfuehrungsort ein nach dem sie suchen möchten: ");
                break;
            default:
                System.out.println("Falsche Eingabe!");
        }

        return suchText;

    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer
     */

    public Kurse auswahlListeKurseAusgeben(HashMap kursHash) {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank.Datenbank nach Kursen
        HashMap<Kurse, Integer> kursMap = kursHash;

        // Schreiben der Kostenstellen in ein Array
        Kurse[] kursArray = new Kurse[kursMap.size() + 1];
        //Tabelle erstellen

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TABELLENHEADER);
        tabelle.setVertikaleLinie(true);

        for (Map.Entry<Kurse, Integer> map : kursMap.entrySet()) {
            kursArray[i] = map.getKey();
            // Ausgeben des Array
            String[] tempArray = map.getKey().attributenString();
            tempArray[0] = i + ".";
            tabelle.zeileHinzufuegen(tempArray);
            i++;

        }
        tabelle.ausgabe();
        arrayLaenge = kursArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        System.out.print("Bitte wählen sie einen Kurs aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        return kursArray[auswahl];
    }
}