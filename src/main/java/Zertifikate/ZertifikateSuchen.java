package Zertifikate;

import Datenbank.ZertifikatsDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;
import Utilities.Tabelle;

import java.util.HashMap;
import java.util.Map;

public class ZertifikateSuchen {

    private String suchText;
    private String query;
    private String[] SUCHKRITERIEN = {"Titel", "Beschreibung","Anbieter","Sprache","Kosten","Waehrung"};
    private String[] TABELLENHEADER = {"Nr.", "Titel", "Beschreibung","Anbieter","Sprache","Kosten","Waehrung"};


    public Zertifikate zertifikatSuchen(){

        boolean abbruchBedingung = false;
        HashMap<?, Integer> zertifikatsHash;
        String titelName = "Zertifikat Suchen";

        do {
            //Suchkriterium Abfragen
            int suchkriterium = suchkriterienAbfragen();
            //Suchtext zum gewaehlten Kriterium Abfragen
            suchText = suchTextEinlesen(suchkriterium);
            //Datenbankquery zusammenstellen anhand den Angaben
            query = new ZertifikatsDatenbank().queryFuerAnzahlAbfrage(SUCHKRITERIEN[suchkriterium - 1], suchText);
            //Hashmap mit allen Treffern erstellen
            zertifikatsHash = new ZertifikatsDatenbank().zertifikatSuchen(query);

            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);

            if (zertifikatsHash.size() > 20) {
                System.out.println("Zuviele Treffer!");
                System.out.println("Bitte geben sie genauere Angaben an");
                BefehlsZeilenSchnittstelle.verzoegerung(2000);
            } else if (zertifikatsHash.size() > 0) {
                // Mitarbeiterliste ausgeben und Auswahl Einlesen
                abbruchBedingung = true;
                return auswahlListeZertifikateAusgeben(zertifikatsHash);
            } else {
                System.out.println("Keine Treffer");
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

        BefehlsZeilenSchnittstelle.bildReinigen("Suchkriterien fuer die Mitarbeitersuche",2);
        System.out.println("Bitte waehlen sie ein Suchkriterium: ");

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
        System.out.print("Nach welchem Kriterium moechten sie suchen: ");
        return BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(SUCHKRITERIEN.length);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
   Methode die die Eingabe des Suchtextes einliest
  Rueckgabewert: Suchtext als String
    */
    private String suchTextEinlesen(int auswahl) {

        int suchZahl = 0;


        switch (auswahl) {
            case 1:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Geben sie den Titel des Zertifikats ein nach dem sie suchen: ");
                break;
            case 2:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Geben sie die Zertifikatsbezeichnung ein nach dem sie suchen moechten: ");
                break;
            case 3:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Anbieter des Zertifikats ein nach dem sie suchen moechten: ");
                break;
            case 4:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die Sprache des Zertifikats ein nach dem sie suchen moechten: ");
                break;
            case 5:
                suchZahl = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Gebn sie die Kosten des Zertifikats ein nach dem sie suchen moechten: ");
                suchText = String.valueOf(suchZahl);
                break;
            case 6:
                suchText = BefehlsZeilenSchnittstelle.abfrageWaehrung();
                break;
            default:
                System.out.println("Falsche Eingabe!");
        }

        return suchText;

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle fuer den Benutzer
     */
    public Zertifikate auswahlListeZertifikateAusgeben(HashMap zertifikateHash) {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        ZertifikatsDatenbank zertifikatsDatenbank = new ZertifikatsDatenbank();

        // Abfrage Datenbank.Datenbank nach Zertifikaten
        HashMap<Zertifikate, Integer> zertifikatMap = zertifikateHash;

        //Erstellt eine Tabelle
        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TABELLENHEADER);
        tabelle.setVertikaleLinie(true);

        // Schreiben der Kostenstellen in ein Array
        Zertifikate[] zertifikatArray = new Zertifikate[zertifikatMap.size() + 1];

        for (Map.Entry<Zertifikate, Integer> map : zertifikatMap.entrySet()) {
            zertifikatArray[i] = map.getKey();
            String[] tempArray = map.getKey().attributenArrayFuerTabelle();
            tempArray[0] = i + ".";

            // Ausgeben des Array
            //System.out.println(i + ". " + map.getKey().toString());
            tabelle.zeileHinzufuegen(tempArray);
            i++;
        }

        tabelle.ausgabe();
        arrayLaenge = zertifikatArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        System.out.print("Bitte waehlen sie ein Zertifikat aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

       return zertifikatArray[auswahl];
    }
}
