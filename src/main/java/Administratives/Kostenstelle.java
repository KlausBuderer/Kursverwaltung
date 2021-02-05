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
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie folgende Daten ein");
            //Kostenstellennummer
            kostenstelleNr = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kostenstelle Nummer: ");
            //Bezeichnung Kostenstelle
            bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Bezeichnung der Kostenstelle: ");
            //Verantwortliche Person
            kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Verantwortliche Person der Kostenstelle: ");

            BefehlsZeilenSchnittstelle.bildReinigen();
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Kostenstelle: " + kostenstelleNr + "\tBezeichnung der Kostenstelle: " + bezeichnungKst + "\tVerantwortliche Person der Kostenstelle: " + kostenstelleVerantPerson);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

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
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ". " + map.getKey().toString());
            i++;
        }

        arrayLaenge = kostenstelleArray.length;

        BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Bitte wählen sie eine Kostenstelle aus der Liste (1-" + (arrayLaenge - 1) + ")");
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
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;


        auswahlListeKostenstelleAusgeben();

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            int i = 1;
            for (String spalte : spaltenArray) {

                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Welchen Spalte moechten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + kostenstelleNr);
                    kostenstelleNr = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die neue Kostenstellennummer ein: ");
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + bezeichnungKst);
                    bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die neue Bezeichnung ein: ");
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + kostenstelleVerantPerson);
                    kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie die neue verantwortliche Person ein: ");
                    break;
                default:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
            }

            BefehlsZeilenSchnittstelle.bildReinigen();
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Kostenstellen Bezeichnung: " + bezeichnungKst + "\tKostenstellen Nummer: " + kostenstelleNr + "\tVerantwortliche Person: " + kostenstelleVerantPerson);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

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
