package Mitarbeiter;

import Administratives.Kostenstelle;
import Datenbank.MitarbeiterDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.HashMap;
import java.util.Map;

public class MitarbeiterSuche{


    private String suchText;
    private String query;
    private final String[] SUCHKRITERIEN = {"Personalnummer","Nachname","Vorname","Geburtsdatum","Abteilung","Jobtitel","Status", "Anrede"};
    private final String[] MYSQLSPALTENNAMEN = {"PersonalNr","Nachname","Vorname","Geburtsdatum","KostenstelleID","Jobtitel","Statusmitarbeiter","Anrede"};
    private String[] statusArray = {"angestellt", "ausgetreten"};
    private String[] anredeArray = {"Frau", "Herr", "Andere"};

    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die eine Auswahl an Suchkriterien Ausgibt und eine Auswahl vom Bediener liest
    Rückgabewert: Objekt von Mitarbeiter oder null
     */
    public Mitarbeiter mitarbeiterSuchen(){
        int arrayLaenge;
        int auswahl;
        boolean abbruchBedingung = false;
        HashMap<?, Integer> mitarbeiterHash;

        do {
            //Suchkriterium Abfragen
            int suchkriterium = suchkriterienAbfragen();
            //Suchtext zum gewählten Kriterium Abfragen
            suchText = suchTextEinlesen(suchkriterium);
            //Datenbankquery zusammenstellen anhand den Angaben
            query = new MitarbeiterDatenbank().queryFuerAnzahlAbfrage(MYSQLSPALTENNAMEN[suchkriterium - 1], suchText);
            //Hashmap mit allen Treffern erstellen
            mitarbeiterHash = new MitarbeiterDatenbank().mitarbeiterSuchen(query);

            BefehlsZeilenSchnittstelle.bildReinigen();

            if (mitarbeiterHash.size() > 20) {
                System.out.println("Zuviele Treffer!");
                System.out.println("Bitte geben sie genauere Angaben an");
                BefehlsZeilenSchnittstelle.verzoegerung(2000);
            } else if (mitarbeiterHash.size() > 0) {
                // Mitarbeiterliste ausgeben und Auswahl Einlesen
                abbruchBedingung = true;
                return mitarbeiterListeAusgeben(mitarbeiterHash);
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
    private int suchkriterienAbfragen(){

        BefehlsZeilenSchnittstelle.bildReinigen();
        System.out.println("Bitte wählen sie ein Suchkriterium: ");

        int auswahlNummer = 1;

        for (String suchkriterium:SUCHKRITERIEN) {

            System.out.println(auswahlNummer + ". " + suchkriterium);
            auswahlNummer++;
        }


        return BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(SUCHKRITERIEN.length);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die die Eingabe des Suchtextes einliest
   Rückgabewert: Suchtext als String
     */
    private String suchTextEinlesen(int auswahl){

        int suchZahl = 0;


        switch (auswahl){
            case 1: suchZahl = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die Personalnummer ein nach der sie suchen möchten: ");
                suchText = String.valueOf(suchZahl);
            break;
            case 2: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Nachnamen ein nach dem sie suchen möchten: ");
            break;
            case 3: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Vornamen nach dem sie suchen möchten: ");
            break;
            case 4: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Gebn sie das Geburtsdatum ein nach dem sie suchen möchten: ");
            break;
            case 5:
                Kostenstelle kostenstelle = new Kostenstelle();
                kostenstelle.auswahlListeKostenstelleAusgeben();
                suchZahl = kostenstelle.kostenstelleId;
                suchText = String.valueOf(suchZahl);
                break;
            case 6: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Jobtitel an nach dem sie suchen möchten: ");
            break;
            case 7:
                BefehlsZeilenSchnittstelle.bildReinigen();
                System.out.println("Mitarbeiterstatus: ");
                int p = 1;

                for (String status : statusArray) {
                    System.out.println(p + ". " + status);
                    p++;
                }
                System.out.println("Status (1-2): ");
                suchText = statusArray[Utilities.BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1];
                break;
            case 8:
                BefehlsZeilenSchnittstelle.bildReinigen();
                System.out.println("Anrede: ");
                int i = 1;

                for (String waehrung : anredeArray) {
                    System.out.println(i + ". " + waehrung);
                    i++;
                }
                System.out.println("Anrede (1-3): ");
                suchText = anredeArray[BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(3) - 1];
                break;
            default:
                System.out.println("Falsche Eingabe!");
        }

        return suchText;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen einer Liste von Objekten der Klasse Mitarbeiter.Mitarbeiter zur Auswahl für den Bediener
    Parameter: Hashmap mit den Mitarbeiter.Mitarbeiter
    Rückgabewert: objekt des Typ Mitarbeiter.Mitarbeiter
     */
    private Mitarbeiter mitarbeiterListeAusgeben(HashMap mitarbeiterHash) {

        int arrayLaenge;
        int auswahl;

        HashMap<Mitarbeiter,Integer> mitarbeiterHashmap = mitarbeiterHash;
        Mitarbeiter[] mitarbeiterArray = new Mitarbeiter[mitarbeiterHash.size() + 1];

        int i = 1;
        for (Map.Entry<Mitarbeiter, Integer> map : mitarbeiterHashmap.entrySet()) {
            mitarbeiterArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString());
            i++;
        }

        arrayLaenge = mitarbeiterArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        System.out.print("Bitte wählen sie einen Mitarbeiter aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        return mitarbeiterArray[auswahl];
    }

}
