package Logik.Zertifikate;

import DatenSchicht.*;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.HashMap;
import java.util.Map;

public class ZertifikateSuchen {

    private String suchText;
    private String[] SUCHKRITERIEN = {"Titel", "Beschreibung","Anbieter","Sprache","Kosten","Waehrung"};
    private String[] TABELLENHEADER = {"Nr.", "Titel", "Beschreibung","Anbieter","Sprache","Kosten","Waehrung"};

//-----------------------------------------------------------------------------------------------------------------------------------------

    public Zertifikate zertifikatSuchen(){

        boolean abbruchBedingung = false;
        HashMap<?, Integer> zertifikatsHash;
        String titelName = "Zertifikat Suchen";

        do {
            //Suchkriterium Abfragen
            int suchkriterium = suchkriterienAbfragen();
            //Suchtext zum gewaehlten Kriterium Abfragen
            suchText = suchTextEinlesen(suchkriterium);
            //Hashmap mit allen Treffern erstellen
            DatenLogik zertifikatSuchen = new ZertifikatsDatenbank();
            zertifikatsHash = zertifikatSuchen.suchen(SUCHKRITERIEN[suchkriterium - 1], suchText);

            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);

            if (zertifikatsHash.size() > 20) {
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Zuviele Treffer!");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie genauere Angaben an");
                BefehlsZeilenSchnittstelle.verzoegerung(2000);
            } else if (zertifikatsHash.size() > 0) {
                // Mitarbeiterliste ausgeben und Auswahl Einlesen
                abbruchBedingung = true;
                return auswahlListeZertifikateAusgeben(zertifikatsHash);
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

        BefehlsZeilenSchnittstelle.bildReinigen("Suchkriterien fuer die Zertifikatssuche",2);
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte waehlen sie ein Suchkriterium: ");

        int auswahlNummer = 1;

        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(HEADER);
        tabelle.vertikaleLinieSetzen(true);

        //Packen der Suchkriterien in eine Tabelle
        for (String suchkriterium : SUCHKRITERIEN) {
            String[] tempArray = {"",""};
            tempArray[0] = auswahlNummer + ".";
            tempArray[1] = suchkriterium;
            tabelle.zeileHinzufuegen(tempArray);

            auswahlNummer++;
        }
        //Abfrage welche Tabelle geändert werden soll
        tabelle.ausgabe();
        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Nach welchem Kriterium moechten sie suchen: ");
        return BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(SUCHKRITERIEN.length);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
   Methode die die Eingabe des Suchtextes einliest
  Rueckgabewert: Suchtext als String
    */
    private String suchTextEinlesen(int auswahl) {

        int suchZahl = 0;

        //Eingabe des Suchtextes
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
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
        }

        return suchText;

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Ausgabe einer Auswahlliste Kostenstelle fuer den Benutzerverwaltung
     */
    private Zertifikate auswahlListeZertifikateAusgeben(HashMap zertifikateHash) {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        ZertifikatsDatenbank zertifikatsDatenbank = new ZertifikatsDatenbank();

        // Abfrage Datenbank.Datenbank nach Zertifikaten
        HashMap<Zertifikate, Integer> zertifikatMap = zertifikateHash;

        //Erstellt eine Tabelle
        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TABELLENHEADER);
        tabelle.vertikaleLinieSetzen(true);

        // Schreiben der Kostenstellen in ein Array
        Zertifikate[] zertifikatArray = new Zertifikate[zertifikatMap.size() + 1];

        for (Map.Entry<Zertifikate, Integer> map : zertifikatMap.entrySet()) {
            zertifikatArray[i] = map.getKey();
            String[] tempArray = map.getKey().attributenArrayFuerTabelle();
            tempArray[0] = i + ".";

            // Ausgeben des Array
            //BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ". " + map.getKey().toString());
            tabelle.zeileHinzufuegen(tempArray);
            i++;
        }

        tabelle.ausgabe();
        arrayLaenge = zertifikatArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Bitte waehlen sie ein Zertifikat aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

       return zertifikatArray[auswahl];
    }
}
