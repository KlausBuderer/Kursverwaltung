package Logik.Mitarbeiter;


import Logik.Administratives.Kostenstelle;
import DatenSchicht.*;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.HashMap;
import java.util.Map;

public class MitarbeiterSuche{


    private String suchText;
    private String query;
    private final String[] TABELLENHEADER = {"Nr.","Personalnummer","Nachname","Vorname","Geburtsdatum","Abteilung","Jobtitel","Status", "Anrede"};
    private final String[] SUCHKRITERIEN = {"Personalnummer","Nachname","Vorname","Geburtsdatum","Abteilung","Jobtitel","Status", "Anrede"};
    private final String[] MYSQLSPALTENNAMEN = {"PersonalNr","Nachname","Vorname","Geburtsdatum","KostenstelleID","Jobtitel","Statusmitarbeiter","Anrede"};
    private String[] statusArray = {"angestellt", "ausgetreten"};
    private String[] anredeArray = {"Frau", "Herr", "Andere"};


    public MitarbeiterSuche() {
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
        Methode die eine Auswahl an Suchkriterien Ausgibt und eine Auswahl vom Bediener liest
        Rueckgabewert: Objekt von Mitarbeiter oder null
         */
    public Mitarbeiter mitarbeiterSuchen(){
        int arrayLaenge;
        int auswahl;
        boolean abbruchBedingung = false;
        HashMap<?, Integer> mitarbeiterHash;

        do {
            //Suchkriterium Abfragen
            int suchkriterium = suchkriterienAbfragen();
            //Suchtext zum gewaehlten Kriterium Abfragen
            suchText = suchTextEinlesen(suchkriterium);
            //Tabelle aller passenden Mitarbeiter aus Datenbank anfordern
            DatenLogik mitarbeiterSuchen = new MitarbeiterDatenbank();
            mitarbeiterHash = mitarbeiterSuchen.suchen(MYSQLSPALTENNAMEN[suchkriterium - 1], suchText);


            BefehlsZeilenSchnittstelle.bildReinigen("Mitarbeitersuche",2);

            if (mitarbeiterHash.size() > 20) {
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Zuviele Treffer!");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie genauere Angaben an");
                BefehlsZeilenSchnittstelle.verzoegerung(2000);
            } else if (mitarbeiterHash.size() > 0) {
                // Mitarbeiterliste ausgeben und Auswahl Einlesen
                abbruchBedingung = true;
                return mitarbeiterListeAusgeben(mitarbeiterHash);
            } else {
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Keine Treffer");
            }
        }while(!abbruchBedingung);
        return null;
    }
    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die eine Auswahl an Suchkriterien Ausgibt und eine Auswahl vom Bediener liest
   +Rueckgabewert: Auswahl als Integer
     */
    private int suchkriterienAbfragen(){

        BefehlsZeilenSchnittstelle.bildReinigen("Suchkriterien fuer die Mitarbeitersuche", 2);
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte waehlen sie ein Suchkriterium: ");

        int auswahlNummer = 1;

        for (String suchkriterium:SUCHKRITERIEN) {

            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(auswahlNummer + ". " + suchkriterium);
            auswahlNummer++;
        }


        return BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(SUCHKRITERIEN.length);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die die Eingabe des Suchtextes einliest
   Rueckgabewert: Suchtext als String
     */
    private String suchTextEinlesen(int auswahl){

        int suchZahl = 0;
BefehlsZeilenSchnittstelle.bildReinigen("Mitarbeitersuche",2);

        switch (auswahl){
            case 1: suchZahl = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die Personalnummer ein nach der sie suchen moechten: ");
                suchText = String.valueOf(suchZahl);
            break;
            case 2: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Nachnamen ein nach dem sie suchen moechten: ");
            break;
            case 3: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Vornamen nach dem sie suchen moechten: ");
            break;
            case 4: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Gebn sie das Geburtsdatum ein nach dem sie suchen moechten: ");
            break;
            case 5:
                Kostenstelle kostenstelle = new Kostenstelle();
                kostenstelle.auswahlListeKostenstelleAusgeben();
                suchZahl = kostenstelle.kostenstelleId;
                suchText = String.valueOf(suchZahl);
                break;
            case 6: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Jobtitel an nach dem sie suchen moechten: ");
            break;
            case 7:
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Mitarbeiterstatus: ");
                int p = 1;

                for (String status : statusArray) {
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(p + ". " + status);
                    p++;
                }
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Status (1-2): ");
                suchText = statusArray[PraesentationSchicht.BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1];
                break;
            case 8:
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Anrede: ");
                int i = 1;

                for (String waehrung : anredeArray) {
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ". " + waehrung);
                    i++;
                }
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Anrede (1-3): ");
                suchText = anredeArray[BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(3) - 1];
                break;
            default:
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
        }

        return suchText;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen einer Liste von Objekten der Klasse Mitarbeiter.Mitarbeiter zur Auswahl fuer den Bediener
    Parameter: Hashmap mit den Mitarbeiter.Mitarbeiter
    Rueckgabewert: objekt des Typ Mitarbeiter.Mitarbeiter
     */
    private Mitarbeiter mitarbeiterListeAusgeben(HashMap mitarbeiterHash) {

        int arrayLaenge;
        int auswahl;

        HashMap<Mitarbeiter,Integer> mitarbeiterHashmap = mitarbeiterHash;
        Mitarbeiter[] mitarbeiterArray = new Mitarbeiter[mitarbeiterHash.size() + 1];
       // Erstellen der Tabelle
        Tabelle tabelle = new Tabelle();
        tabelle.setHeaders(TABELLENHEADER);
        tabelle.setVertikaleLinie(true);

        int i = 1;
        for (Map.Entry<Mitarbeiter, Integer> map : mitarbeiterHashmap.entrySet()) {
            mitarbeiterArray[i] = map.getKey();
            // Ausgeben des Array
            String[] tempArray = map.getKey().attributenArrayFuerTabelle();
            tempArray[0] = i + ".";
            tabelle.zeileHinzufuegen(tempArray);
            i++;
        }

        tabelle.ausgabe();
        arrayLaenge = mitarbeiterArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Bitte waehlen sie einen Mitarbeiter aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        return mitarbeiterArray[auswahl];
    }

}
