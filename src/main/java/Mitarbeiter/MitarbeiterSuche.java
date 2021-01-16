package Mitarbeiter;

import Datenbank.MitarbeiterDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.HashMap;
import java.util.Map;

public class MitarbeiterSuche extends Mitarbeiter {


    private String suchText;
    private final String[] SUCHKRITERIEN = {"Personalnummer","Nachname","Vorname","Geburtstag","Abteilung","Jobtitel","Status"};

    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die eine Auswahl an Suchkriterien Ausgibt und eine Auswahl vom Bediener liest
    Rückgabewert: Objekt von Mitarbeiter.Mitarbeiter oder null
     */
    public Mitarbeiter mitarbeiterSuchen(){//
        Mitarbeiter mitarbeiter = null;
        int arrayLaenge;
        int auswahl;

        HashMap<Mitarbeiter,Integer> mitarbeiterHash = new HashMap<>();

        //Suchkriterium Abfragen
        int suchkriterium = suchkriterienAbfragen();
        //Suchtext zum gewählten Kriterium Abfragen
        String suchtext = suchTextEinlesen(suchkriterium);
        //Datenbankquery zusammenstellen anhand den Angaben
        String query = queryFuerAnzahlAbfrage(suchkriterium, suchText);
        //Hashmap mit allen Treffern erstellen
        mitarbeiterHash = new MitarbeiterDatenbank().mitarbeiterSuchen(query);

        BefehlsZeilenSchnittstelle.bildReinigen();

        if (mitarbeiterHash.size() > 20){
            System.out.println("Zuviele Treffer!");
            System.out.println("Bitte geben sie genauere Angaben an");

        }else if(mitarbeiterHash.size() > 0){
          // Mitarbeiterliste ausgeben und Auswahl Einlesen
            mitarbeiter = mitarbeiterListeAusgeben(mitarbeiterHash);
        }else{
            System.out.println("Keine Treffer");
        }

        return mitarbeiter;
    }
    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die eine Auswahl an Suchkriterien Ausgibt und eine Auswahl vom Bediener liest
   Rückgabewert: Auswahl als Integer
     */
    private int suchkriterienAbfragen(){

        int eingabe = 0;

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
    Methode die den query aus den Angaben des Bedieners zusammensetzt
   Rückgabewert: query als String
     */
    private String queryFuerAnzahlAbfrage(int auswahl, String suchText){

        String query = "Mitarbeiter.Mitarbeiter where ";
        String suchkriterium = SUCHKRITERIEN[auswahl] + " = " + suchText + "%";
        return query + suchkriterium;
        }
    /*--------------------------------------------------------------------------------------------------------------------------------------
    Methode die die Eingabe des Suchtextes einliest
   Rückgabewert: Suchtext als String
     */
    private String suchTextEinlesen(int auswahl){

        int suchZahl = 0;

        switch (auswahl){
            case 1: suchZahl = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die Personalnummer ein nach der sie suchen möchten: ");break;
            case 2: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Nachnamen ein nach dem sie suchen möchten: ");break;
            case 3: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Vornamen nach dem sie suchen möchten: ");break;
            case 4: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Gebn sie das Geburtstag ein nach dem sie suchen möchten: ");break;
            case 5: break;
            case 6: suchText = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Jobtitel an nach dem sie suchen möchten: ");break;
            case 7: break;
        }
        suchText = String.valueOf(suchZahl);
        return suchText;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen einer Liste von Objekten der Klasse Mitarbeiter.Mitarbeiter zur Auswahl für den Bediener
    Parameter: Hashmap mit den Mitarbeiter.Mitarbeiter
    Rückgabewert: objekt des Typ Mitarbeiter.Mitarbeiter
     */
    private Mitarbeiter mitarbeiterListeAusgeben(HashMap<Mitarbeiter,Integer> mitarbeiterHash) {

        int arrayLaenge;
        int auswahl;

        Mitarbeiter[] mitarbeiterArray = new Mitarbeiter[mitarbeiterHash.size()];

        int i =1;
        for (Map.Entry<Mitarbeiter, Integer> map : mitarbeiterHash.entrySet()) {
            mitarbeiterArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString());
            i++;
        }

        arrayLaenge = mitarbeiterArray.length;

        //Der Bediener wird zu Auswahl einer der Objekte aufgefordert
        System.out.print("Bitte wählen sie ein Zertifikat aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        return mitarbeiterArray[auswahl];
    }

}
