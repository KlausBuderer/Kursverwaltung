package Kurse;

import Datenbank.KursDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.util.HashMap;
import java.util.Map;

public class KursSuchen {

    private String suchText;
    private String query;
    private final String[] TABELLENHEADER = {"Nr.","Kurs Code", "Anbieter", "Kursbeschreibung",  "Kosten", "Waehrung", "Start-Datum", "End-Datum", "Durchfuehrungsort"};
    private final String[] SUCHKRITERIEN = {"Kurs Code", "Anbieter", "Kursbeschreibung", "Durchfuehrungsort"};
    private final String[] SPALTENBEZEICHNUNG = {"KursCode", "Anbieter", "Kursbeschreibung", "Durchfuehrungsort"};



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

            if (kursHash.size() > 30) {
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Zuviele Treffer!");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie genauere Angaben an");
                BefehlsZeilenSchnittstelle.verzoegerung(2000);
            } else if (kursHash.size() > 0) {
                // Mitarbeiterliste ausgeben und Auswahl Einlesen
                abbruchBedingung = true;
                return auswahlListeKurseAusgeben(kursHash);
            } else {
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Keine Treffer");
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
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte wählen sie ein Suchkriterium: ");

        int auswahlNummer = 1;

        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(HEADER);
        tabelle.setVertikaleLinie(true);

        for (String suchkriterium : SUCHKRITERIEN) {
            String[] tempArray = {"",""};
            tempArray[0] = auswahlNummer + ".";
            tempArray[1] = suchkriterium;
            tabelle.zeileHinzufuegen(tempArray);
           // BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(auswahlNummer + ". " + suchkriterium);
            auswahlNummer++;
        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Nach welchem Kriterium moechten sie suchen: ");
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
                break;
            case 2:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Anbieter des Kurses ein nach dem sie suchen moechten: ");
                break;
            case 3:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die Kursbeschreibung ein nach dem sie suchen moechten: ");
                break;
            case 4:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Durchfuehrungsort ein nach dem sie suchen moechten: ");
                break;
            default:
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
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
        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Bitte wählen sie einen Kurs aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        return kursArray[auswahl];
    }
}