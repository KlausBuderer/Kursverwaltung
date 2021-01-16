package Administratives;

import Datenbank.KostenstelleDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.HashMap;
import java.util.Map;

public class Kostenstelle {

    public int kostenstelleId;
    public int kostenstelleNr;
    public String bezeichnungKst = " ";
    public String kostenstelleVerantPerson;

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Kostenstelle
    public Kostenstelle(int kostenstelleId, int konstenstelle, String bezeichnungKst, String kostenstelleVerantPerson){

        this.kostenstelleId =kostenstelleId;
        this.kostenstelleNr = konstenstelle;
        this.bezeichnungKst = bezeichnungKst;
        this.kostenstelleVerantPerson = kostenstelleVerantPerson;

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Kostenstelle() {
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Die Methode kostenstelleAnlegen lässt den Benutzer eine neue Kostenstelle anlegen
     */

    void kostenstelleAnlegen(){

        boolean abschliessen = true;

        do {

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Kostenstellennummer
            kostenstelleNr = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kostenstelle Nummer: ");
            //Bezeichnung Kostenstelle
            bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Bezeichnung der Kostenstelle: ");
            //Verantwortliche Person
            kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Verantwortliche Person der Kostenstelle: ");

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Kostenstelle: " + kostenstelleNr + "\tBezeichnung der Kostenstelle: " + bezeichnungKst + "\tVerantwortliche Person der Kostenstelle: " + kostenstelleVerantPerson);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: KostenstelleDatenbank kostenstelleDatenbank = new KostenstelleDatenbank();
                    kostenstelleDatenbank.datenAnlegen(this);
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

    public void auswahlListeKostenstelleAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

       KostenstelleDatenbank kostenstelleDatenbank = new KostenstelleDatenbank();
        // Abfrage Datenbank.Datenbank nach Kostenstellen
        HashMap<Kostenstelle, Integer> kostenstelleMap = (HashMap<Kostenstelle, Integer>) kostenstelleDatenbank.datenAuslesenfuerAbfrage("Kostenstelle");


        // Schreiben der Kostenstellen in ein Array
        Kostenstelle[] kostenstelleArray = new Kostenstelle[kostenstelleMap.size() + 1];

        BefehlsZeilenSchnittstelle.bildReinigen();
        for (Map.Entry<Kostenstelle, Integer> map : kostenstelleMap.entrySet()) {
            kostenstelleArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString());
            i++;
        }

        arrayLaenge = kostenstelleArray.length;

        System.out.print("Bitte wählen sie eine Kostenstelle aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        kostenstelleId = kostenstelleArray[auswahl].kostenstelleId;
        kostenstelleNr = kostenstelleArray[auswahl].kostenstelleNr;
        bezeichnungKst = kostenstelleArray[auswahl].bezeichnungKst;
        kostenstelleVerantPerson = kostenstelleArray[auswahl].kostenstelleVerantPerson;

    }



    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung einer Kostenstelle

     */
    void kostenstelleMutieren(){

        String[] spaltenArray = {"Kostenstellennummer","Bezeichnung der Kostenstelle","Verantwortliche Person der Kostenstelle"};
        String vornamen;
        String nachnamen;
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;


        auswahlListeKostenstelleAusgeben();

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
                    System.out.println("Aktuell: " + kostenstelleNr);
                    kostenstelleNr = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die neue Kostenstellennummer ein: ");
                    break;
                case 2:
                    System.out.println("Aktuell: " + bezeichnungKst);
                    bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die neue Bezeichnung ein: ");
                    break;
                case 3:
                    System.out.println("Aktuell: " + kostenstelleVerantPerson);
                    kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die neue verantwortliche Person ein: ");
                    break;
                default:
                    System.out.println("Falsche Eingabe!");
            }

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Kostenstellen Bezeichnung: " + bezeichnungKst + "\tKostenstellen Nummer: " + kostenstelleNr + "\tVerantwortliche Person: " + kostenstelleVerantPerson);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    KostenstelleDatenbank kostenstelleDatenbank = new KostenstelleDatenbank();
                    kostenstelleDatenbank.datenMutation(this);
                    ;
                    abschliessen = true;
                    break;
                case 2:
                    abschliessen = false;
                    break;
                case 3:
                    abschliessen = true;
                    break;
            }
        }while(!abschliessen);
    }

    @Override
    public String toString() {
        return "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstelleNr), 10) +
                "Verantwortliche Person: " + BefehlsZeilenSchnittstelle.textFormatieren(kostenstelleVerantPerson, 20) +
                "Bezeichnung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(bezeichnungKst), 25);
    }
}
