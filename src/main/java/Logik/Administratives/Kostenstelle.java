package Logik.Administratives;

import DatenSchicht.DatenLogik;
import DatenSchicht.KostenstelleDatenbank;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

import java.util.List;

public class Kostenstelle extends ServicesAdmin {

    private int kostenstelleId;
    private int kostenstelleNr;
    private String bezeichnungKst = " ";
    private String kostenstelleVerantPerson;

    private final String[] KOPFZEILE = {"Nr.","Kostenstellen Nr.", "Verant. Person", "Bezeichnung"};
    private final String[] KOPFZEILE_ANZEIGE_EINGABE = {"Nr.","Kostenstellen Nr.", "Verant. Person", "Bezeichnung"};
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
    Methode zur Ausgabe einer Auswahlliste Kostenstelle fuer den Benutzerverwaltung
     */
    public void auswahlListeKostenstelleAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;
        String titelName = "Kostenstellenauswahlliste";

        DatenLogik kostenstelleDatenbank = new KostenstelleDatenbank();
        //  Datenbanka nach Kostenstellen
        List<Kostenstelle> kostenstelleList = (List<Kostenstelle>) kostenstelleDatenbank.datenAuslesen("tblKostenstelle");

        // Schreiben der Kostenstellen in ein Array
        Kostenstelle[] kostenstelleArray = new Kostenstelle[kostenstelleList.size() + 1];

        BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
        Tabelle tabelle = new Tabelle();
        tabelle.vertikaleLinieSetzen(true);
        tabelle.kopfzeileSetzen(KOPFZEILE);

        //Schleife durch ganze Liste fuer die Ausgabe in einer Tabelle
        for (Kostenstelle kostenstelle : kostenstelleList) {
            kostenstelleArray[i] = kostenstelle;
            //Temporaeres Array fuer die Ausgabe in der Tabelle
            String[] tempArray = kostenstelle.attributenArrayFuerTabelle();
            tempArray[0] = i + ".";
            // Ausgeben des Array
            tabelle.zeileHinzufuegen(tempArray);
            i++;
        }

        tabelle.ausgabe();

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
    Diese Methode laesst den Benutzer eine neue Kostenstelle anlegen
     */
    protected void datenAnlegen(){

        boolean abschliessen = true;
        String titelName = "Kostenstelle Anlegen";

        do {//Eingabe der Daten
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie folgende Daten ein");
            //Kostenstellennummer
            kostenstelleNr = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kostenstelle Nummer: ");
            //Bezeichnung Kostenstelle
            bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Bezeichnung der Kostenstelle: ");
            //Verantwortliche Person
            kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Verantwortliche Person der Kostenstelle: ");

            // Ausgabe der eingegebenen Daten
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE_ANZEIGE_EINGABE,attributenArrayFuerTabelle());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            // Abfrage ob Eingaben korrekt sind
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){
                case 1: //Speichern
                    DatenLogik kostenstelleDatenbank = new KostenstelleDatenbank();
                    ((KostenstelleDatenbank) kostenstelleDatenbank).datenAnlegen(this);
                    abschliessen = true;
                    break;
                case 2: //Neu beginnen
                    abschliessen = false;
                    break;
                case 3://Abbrechen
                    abschliessen = true;
                    break;
            }
        }while(!abschliessen);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung einer Kostenstelle
     */
    protected void datenMutieren(){

        String[] spaltenArray = {"Kostenstellennummer","Bezeichnung der Kostenstelle","Verantwortliche Person der Kostenstelle"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;
        String titelName = "Kostenstelle Mutieren";

        auswahlListeKostenstelleAusgeben();

        do {
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            int i = 1;
            for (String spalte : spaltenArray) {

                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Welchen Spalte möchten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + kostenstelleNr);
                    kostenstelleNr = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die neue Kostenstellennummer ein: ");
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + bezeichnungKst);
                    bezeichnungKst = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Geben sie die neue Bezeichnung ein: ");
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + kostenstelleVerantPerson);
                    kostenstelleVerantPerson = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Geben sie die neue verantwortliche Person ein: ");
                    break;
                default:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
            }

            //Anzeige der Eingaben
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE_ANZEIGE_EINGABE,attributenArrayFuerTabelle());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");
            //Eingabe bestaetigen, neu beginnen oder abbrechen
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {
                case 1:
                    DatenLogik kostenstelleDatenbank = new KostenstelleDatenbank();
                    kostenstelleDatenbank.datenMutation(this);
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
    Diese Methode packt die Membervariablen in ein Array fuer die Ausgabe in einer Tabelle
     */
    protected String[] attributenArrayFuerTabelle(){
        String[] attributenArray = {" ",String.valueOf(kostenstelleNr),kostenstelleVerantPerson,bezeichnungKst};
        return attributenArray;
    }

    @Override
    public String toString() {
        return "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.kostenstelleNr), 10) +
                "Verantwortliche Person: " + BefehlsZeilenSchnittstelle.textFormatieren(kostenstelleVerantPerson, 20) +
                "Bezeichnung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(bezeichnungKst), 25);
    }

    public int getKostenstelleId() {
        return kostenstelleId;
    }

    public int getKostenstelleNr() {
        return kostenstelleNr;
    }

    public String getBezeichnungKst() {
        return bezeichnungKst;
    }

    public String getKostenstelleVerantPerson() {
        return kostenstelleVerantPerson;
    }
}
