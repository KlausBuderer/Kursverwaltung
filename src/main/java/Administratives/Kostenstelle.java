package Administratives;

import Datenbank.AdministrativesDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.HashMap;
import java.util.Map;

public class Kostenstelle {

    private int kostenstelleId;
    private int kostenstelle;
    private String bezeichnungKst = " ";
    private String kostenstelleVerantPerson;

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Kostenstelle
    public Kostenstelle(int kostenstelleId, int konstenstelle, String bezeichnungKst, String kostenstelleVerantPerson){

        this.kostenstelleId =kostenstelleId;
        this.kostenstelle = konstenstelle;
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
            kostenstelle = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kostenstelle Nummer: ");
            //Bezeichnung Kostenstelle
            bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Bezeichnung der Kostenstelle: ");
            //Verantwortliche Person
            kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Verantwortliche Person der Kostenstelle: ");

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Kostenstelle: " + kostenstelle + "\tBezeichnung der Kostenstelle: " + bezeichnungKst + "\tVerantwortliche Person der Kostenstelle: " + kostenstelleVerantPerson);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: AdministrativesDatenbank administrativesDatenbank = new AdministrativesDatenbank();
                    administrativesDatenbank.datenAnlegen(kostenstelleAnlegenQuerry());
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

        AdministrativesDatenbank administrativesDatenbank = new AdministrativesDatenbank();
        // Abfrage Datenbank.Datenbank nach Kostenstellen
        HashMap<Kostenstelle, Integer> kostenstelleMap = (HashMap<Kostenstelle, Integer>) administrativesDatenbank.datenAuslesenfuerAbfrage("Kostenstelle");


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
        kostenstelle = kostenstelleArray[auswahl].kostenstelle;
        bezeichnungKst = kostenstelleArray[auswahl].bezeichnungKst;
        kostenstelleVerantPerson = kostenstelleArray[auswahl].kostenstelleVerantPerson;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für ein Anlegen eines neuen Kurses
     */
    String kostenstelleAnlegenQuerry(){

        return "INSERT INTO `itwisse_kursverwaltung`.`Kostenstelle` (`Kostenstelle`, `BezeichnungKST`, `KostenstelleVerantPerson`) VALUES " +
                "('" + kostenstelle + "', '" + bezeichnungKst + "', '" + kostenstelleVerantPerson + "')";

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung einer Kostenstelle

     */
    void kostenstelleBearbeiten(){

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
                    System.out.println("Aktuell: " + kostenstelle);
                    kostenstelle = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die neue Kostenstellennummer ein: ");
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
            System.out.println("Kostenstellen Bezeichnung: " + bezeichnungKst + "\tKostenstellen Nummer: " + kostenstelle + "\tVerantwortliche Person: " + kostenstelleVerantPerson);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    AdministrativesDatenbank administrativesDatenbank = new AdministrativesDatenbank();
                    administrativesDatenbank.datenBearbeiten(updateKostenstelle());
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
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für einen Update von Kostenstelle

     */

    String updateKostenstelle(){

        String querry;

        querry = "UPDATE `itwisse_kursverwaltung`.`Kostenstelle` SET " +
                " `Kostenstelle` = " + kostenstelle +
                ", `BezeichnungKST` = \"" + bezeichnungKst +
                "\", `KostenstelleVerantPerson` = \"" + kostenstelleVerantPerson +
                "\" WHERE `ID` = " + kostenstelleId + ";";
        return querry;
    }


    @Override
    public String toString() {
        return "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstelle), 10) +
                "Verantwortliche Person: " + BefehlsZeilenSchnittstelle.textFormatieren(kostenstelleVerantPerson, 20) +
                "Bezeichnung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(bezeichnungKst), 25);
    }
}
