package Logik.Zertifikate;

import DatenSchicht.DatenLogik;
import DatenSchicht.ZertifikatsDatenbank;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;

public class ZertifikateSuchen {

    private String suchText;
    private String[] SUCHKRITERIEN = {"Titel", "Beschreibung","Anbieter","Sprache","Kosten","Waehrung"};
    private String[] TABELLENHEADER = {"Nr.", "Titel", "Beschreibung","Anbieter","Sprache","Kosten","Währung"};

//-----------------------------------------------------------------------------------------------------------------------------------------

    public Zertifikate zertifikatSuchen(){

        boolean abbruchBedingung = false;
        List<?> zertifikatsList;
        String titelName = "Zertifikat Suchen";

        do {
            //Suchkriterium Abfragen
            int suchkriterium = suchkriterienAbfragen();
            //Suchtext zum gewaehlten Kriterium Abfragen
            suchText = suchTextEinlesen(suchkriterium);
            //Liste mit allen Treffern erstellen
            DatenLogik zertifikatSuchen = new ZertifikatsDatenbank();
            zertifikatsList = zertifikatSuchen.suchen(SUCHKRITERIEN[suchkriterium - 1], suchText);

            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);

            if (zertifikatsList.size() > 20) {
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Zuviele Treffer!");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie genauere Angaben an");
                BefehlsZeilenSchnittstelle.verzoegerung(2000);
            } else if (zertifikatsList.size() > 0) {
                // Mitarbeiterliste ausgeben und Auswahl Einlesen
                abbruchBedingung = true;
                return auswahlListeZertifikateAusgeben(zertifikatsList);
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

        BefehlsZeilenSchnittstelle.bildReinigen("Suchkriterien für die Zertifikatssuche",2);
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte wählen sie ein Suchkriterium: ");

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
        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Nach welchem Kriterium möchten sie suchen: ");
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
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Geben sie die Zertifikatsbezeichnung ein nach dem sie suchen möchten: ");
                break;
            case 3:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Anbieter des Zertifikats ein nach dem sie suchen möchten: ");
                break;
            case 4:
                suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die Sprache des Zertifikats ein nach dem sie suchen möchten: ");
                break;
            case 5:
                suchZahl = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Gebn sie die Kosten des Zertifikats ein nach dem sie suchen möchten: ");
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
    private Zertifikate auswahlListeZertifikateAusgeben(List zertifikateList) {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank nach Zertifikaten
        List<Zertifikate> zertifikatListe = zertifikateList;

        //Erstellt eine Tabelle
        Tabelle tabelle = new Tabelle();
        tabelle.kopfzeileSetzen(TABELLENHEADER);
        tabelle.vertikaleLinieSetzen(true);

        // Schreiben der Zertifikate in ein Array
        Zertifikate[] zertifikatArray = new Zertifikate[zertifikatListe.size() + 1];

        for (Zertifikate zertifikate : zertifikatListe) {
            String[] tempArray = zertifikate.attributenArrayFuerTabelle();
            tempArray[0] = i + ".";

            // Ausgeben des Array
            tabelle.zeileHinzufuegen(tempArray);
            i++;
        }

        tabelle.ausgabe();
        arrayLaenge = zertifikatArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Bitte wählen sie ein Zertifikat aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

       return zertifikatListe.get(auswahl - 1);
    }
}
