package Logik.Kurse;

import DatenSchicht.*;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.HashMap;
import java.util.Map;

public class KursSuchen {

    private String suchText;
    private String query;
    private final String[] TABELLENHEADER = {"Nr.","Kurs Code", "Anbieter", "Kursbeschreibung",  "Kosten", "Währung", "Start-Datum", "End-Datum", "Durchführungsort"};
    private final String[] SUCHKRITERIEN = {"Kurs Code", "Anbieter", "Kursbeschreibung", "Durchfuehrungsort"};
    private final String[] SPALTENBEZEICHNUNG = {"KursCode", "Anbieter", "Kursbeschreibung", "Durchfuehrungsort"};

    public Kurse kursSuchen(){

        boolean abbruchBedingung = false;
        String titelName = "Kurse Suchen";
        HashMap<?, Integer> kursHash;

        do {
            //Suchkriterium Abfragen
            int suchkriterium = suchkriterienAbfragen();
            //Suchtext zum gewaehlten Kriterium Abfragen
            suchText = suchTextEinlesen(suchkriterium);
            //Hashmap mit allen Treffern erstellen
            DatenLogik kursSuchen = new KursDatenbank();
            kursHash = kursSuchen.suchen(SPALTENBEZEICHNUNG[suchkriterium - 1], suchText);
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);

            //Begrenzen der Anzahl der Ausgabe
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
                BefehlsZeilenSchnittstelle.verzoegerung(2000);
            }
        }while(!abbruchBedingung);
        return null;
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
       Methode die eine Auswahl an Suchkriterien Ausgibt und eine Auswahl vom Bediener liest
      +Rueckgabewert: Auswahl als Integer
        */
    private int suchkriterienAbfragen() {

        String[] HEADER = {"Nr.","Suchkriterium"};

        BefehlsZeilenSchnittstelle.bildReinigen("Suchkriterien für die Kurssuche",2);
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte wählen sie ein Suchkriterium: ");

        int auswahlNummer = 1;

        //Ausgabe der Suchkriterien
        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(HEADER);
        tabelle.vertikaleLinieSetzen(true);

        for (String suchkriterium : SUCHKRITERIEN) {
            String[] tempArray = {"",""};
            tempArray[0] = auswahlNummer + ".";
            tempArray[1] = suchkriterium;
            tabelle.zeileHinzufuegen(tempArray);
           // BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(auswahlNummer + ". " + suchkriterium);
            auswahlNummer++;
        }
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Nach welchem Kriterium möchten sie suchen: ");
        return BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(SUCHKRITERIEN.length);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die die Eingabe des Suchtextes einliest
   Rueckgabewert: Suchtext als String
     */
    private String suchTextEinlesen(int auswahl) {

        int suchZahl = 0;

            //Eingabe der Änderung die vorgenommen werden soll
        switch (auswahl) {
            case 1:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Geben sie den Kurs Code ein nach dem sie suchen: ");
                break;
            case 2:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Anbieter des Kurses ein nach dem sie suchen möchten: ");
                break;
            case 3:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die Kursbeschreibung ein nach dem sie suchen möchten: ");
                break;
            case 4:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Durchführungsort ein nach dem sie suchen möchten: ");
                break;
            default:
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
        }
        return suchText;
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle fuer den Benutzerverwaltung
     */
    private Kurse auswahlListeKurseAusgeben(HashMap kursHash) {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank nach Kursen
        HashMap<Kurse, Integer> kursMap = kursHash;

        // Schreiben des Kurses in ein Array
        Kurse[] kursArray = new Kurse[kursMap.size() + 1];
        //Tabelle erstellen

        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TABELLENHEADER);
        tabelle.vertikaleLinieSetzen(true);

        for (Map.Entry<Kurse, Integer> map : kursMap.entrySet()) {
            kursArray[i] = map.getKey();
            // Ausgeben des Array
            String[] tempArray = map.getKey().attributenArrayFuerTabelle();
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