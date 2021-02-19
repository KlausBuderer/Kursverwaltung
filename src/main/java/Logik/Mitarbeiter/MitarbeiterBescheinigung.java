package Logik.Mitarbeiter;

import DatenSchicht.*;
import Logik.Kurse.*;
import PraesentationSchicht.*;
import Logik.Zertifikate.*;


import java.util.HashMap;
import java.util.Map;

public class MitarbeiterBescheinigung {

   private int id;
   private int mitarbeiterId;
   private int zertifikatId;
   private int kurseId;

   private String zertifikatsAblaufDatum = "0000-00-00";
   private String vorname;
   private String nachname;
   private String zertifikatsTitel;
   private String zertifikatsBeschreibung;

    public enum kontextAnlegen  {KURS,ZERTIFIKAT};
    private String[] TABELLENHEADER = {"Nr.","Vorname","Nachname","Zertifikat","Ablaufdatum","Zertifikatsbeschreibung"};

    //----------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor
    public MitarbeiterBescheinigung() {
    }
    //Konstruktor um eine neue MitarbeiterBescheinigung zu erstellen
    public MitarbeiterBescheinigung(int id, String zertifikatsAblaufDatum, int mitarbeiterId,String vorname, String nachname, String zertifikatsTitel, String zertifikatsBeschreibung) {
        this.id = id;
        this.zertifikatsAblaufDatum = zertifikatsAblaufDatum;
        this.mitarbeiterId = mitarbeiterId;
        this.zertifikatId = zertifikatId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.zertifikatsTitel = zertifikatsTitel;
        this.zertifikatsBeschreibung = zertifikatsBeschreibung;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um einen Kurs einem Mitarbeiter zuzuweisen
     */
    public void kursZuweisen() {

        Mitarbeiter mitarbeiter;
        Kurse kurs;
        boolean abschliessen = false;
        String titelName = "Mitarbeiter Kurs zuweisen";

        do {
            //Mitarbeiter auswaehlen
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Welchem Mitarbeiter moechten sie einen Kurs zuweisen?");
            mitarbeiter = new MitarbeiterSuche().mitarbeiterSuchen();
            this.mitarbeiterId = mitarbeiter.getMitarbeiterId();

            //Kurs auswaehlen
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Welchen Kurs moechten sie dem Mitarbeiter zuweisen?");
            kurs = new KursSuchen().kursSuchen();
            this.kurseId = kurs.getKurseId();

            // Ausgabe der neuen MitarbeiterBescheinigung
            PraesentationSchicht.BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte bestaetigen sie die richtige Eingabe");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Mitarbeiter: " + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Kurs: " + kurs.getAnbieter() + " " + kurs.getKursBeschreibung() + " " + kurs.getDatumVon());

            //Bestätigung der MitarbeiterBescheinigung
            switch (PraesentationSchicht.BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {
                case 1://Speichern
                    DatenLogik mitarbeiterKursZuweisen = new MitarbeiterDatenbank();
                    ((MitarbeiterDatenbank) mitarbeiterKursZuweisen).mitarbeiterBescheinigungAnlegen(this, kontextAnlegen.KURS);
                    abschliessen = true;
                    break;
                case 2://Neu beginnen
                    abschliessen = false;
                    break;
                case 3://Abbrechen
                    abschliessen = true;
                    break;
            }
        } while (!abschliessen) ;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um eine Zertifikat einem Mitarbeiter zuzuweisen
     */

    public void zertifikatZuweisen() {

        Mitarbeiter mitarbeiter;
        Zertifikate zertifikat;
        boolean abschliessen = false;
        String titelName = "Mitarbeiter Zertifikat zuweisen";

        do {
            //Mitarbeiter auswaehlen
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Welchem Mitarbeiter moechten sie ein Zertifikat zuweisen?");
            mitarbeiter = new MitarbeiterSuche().mitarbeiterSuchen();
            this.mitarbeiterId = mitarbeiter.getMitarbeiterId();

            //Zertifikat auswaehlen
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Welches Zertifikat moechten sie dem Mitarbeiter zuweisen?");
            zertifikat = new ZertifikateSuchen().zertifikatSuchen();
            this.zertifikatId = zertifikat.getZertifikatsId();

            //Ablaufdatum angeben
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Geben sie das Ablaufdatum des Zertifikats ein (TT.MM.JJJJ): ");
            this.zertifikatsAblaufDatum = BefehlsZeilenSchnittstelle.pruefeDatum();

            // Ausgabe der neuen MitarbeiterBescheinigung
            PraesentationSchicht.BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte bestaetigen sie die richtige Eingabe");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Mitarbeiter: " + mitarbeiter.getVorname() + " " + mitarbeiter.getNachname());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Zertifikat: " + zertifikat.getAnbieter());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Zertifikatsbeschreibung: " + zertifikat.getZertifikatsBeschreibung());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Ablaufdatum: " + zertifikatsAblaufDatum);

            switch (PraesentationSchicht.BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {
                case 1://Speichern
                    DatenLogik mitarbeiterZertifikatZuweisen = new MitarbeiterDatenbank();
                    ((MitarbeiterDatenbank) mitarbeiterZertifikatZuweisen).mitarbeiterBescheinigungAnlegen(this, kontextAnlegen.ZERTIFIKAT);
                    abschliessen = true;
                    break;
                case 2://Neu beginnen
                    abschliessen = false;
                    break;
                case 3://Abbrechen
                    abschliessen = true;
                    break;
            }

        } while (!abschliessen) ;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um eine Zertifikat zu verlaengern
     */
    public void zertifikatVerlaengern(){

        Mitarbeiter mitarbeiter;
        int mitarbeiterId;
        boolean abschliessen = false;
        String titelName = "Zertifikat Verlaengern";

        do {
            //Abfrage von welchem Mitarbeiter das Zertifikat verlaengert werden soll
            MitarbeiterSuche mitarbeiterSuche = new MitarbeiterSuche();
                mitarbeiter = mitarbeiterSuche.mitarbeiterSuchen();
                    mitarbeiterId = mitarbeiter.getMitarbeiterId();

                    //Store Procedure aufrufen und Zertifikate als Liste ausgeben
            MitarbeiterBescheinigung mitarbeiterBescheinigung;

            //Pruefung ob bereits ein Zertifikat zugewiesen ist
            //Falls dem Mitarbeiter kein Zertifikat ist, wird die Methode beendet
            try{
                mitarbeiterBescheinigung = auswahlListeZertifikateAusgeben(mitarbeiterId);
                objektUebergeben(mitarbeiterBescheinigung);
            }catch (NullPointerException nullPointerException){
                return;
            }



            //Das aktuelle Datum zeigen
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            Tabelle tabelleAlt = new Tabelle();
            tabelleAlt.kopfzeileSetzen(TABELLENHEADER);
            tabelleAlt.zeileHinzufuegen(this.attributenArrayErstellen());
            tabelleAlt.ausgabe();

            //Eingabe eines neuen Datum anfordern
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie das neue Datum ein (TT.MM.JJJJ): ");
            zertifikatsAblaufDatum = BefehlsZeilenSchnittstelle.pruefeDatum();

            //Eingabe zeigen und bestaetigung verlangen
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            Tabelle tabelleNeu = new Tabelle();
            tabelleNeu.kopfzeileSetzen(TABELLENHEADER);
            tabelleNeu.zeileHinzufuegen(this.attributenArrayErstellen());
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            tabelleNeu.ausgabe();


            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte ueberpruefen sie die Korrektheit der Erfassten Daten");

            switch (PraesentationSchicht.BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1://Speichern
                    DatenLogik zertifikatVerlaengern = new MitarbeiterDatenbank();
                    ((MitarbeiterDatenbank) zertifikatVerlaengern).zertifikatVerlaengernSpeichern(this);
                    abschliessen = true;
                    break;
                case 2://Neu beginnen
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
    Methode zur Ausgabe einer Auswahlliste Mitarbeiterbescheinigung fuer den Benutzerverwaltung
     */

    private MitarbeiterBescheinigung auswahlListeZertifikateAusgeben(int mitarbeiterId) {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        DatenLogik mitarbeiterDatenbank = new MitarbeiterDatenbank();
        // Abfrage Datenbank nach Kostenstellen
        HashMap<MitarbeiterBescheinigung, Integer> mitarbeiterBescheinigungsMap = (HashMap<MitarbeiterBescheinigung, Integer>) ((MitarbeiterDatenbank) mitarbeiterDatenbank).zertifikatVerlaengernListe(mitarbeiterId);

        // Schreiben der Kostenstellen in ein Array
        MitarbeiterBescheinigung[] mitarbeiterBescheinigungsArray = new MitarbeiterBescheinigung[mitarbeiterBescheinigungsMap.size() + 1];
        arrayLaenge = mitarbeiterBescheinigungsArray.length;

        BefehlsZeilenSchnittstelle.bildReinigen("Zertifikatsauswahlsliste", 2);
        if(arrayLaenge <= 1) {
            //Tabelle fuer die Ausgaben erstellen
            Tabelle tabelle = new Tabelle();
            tabelle.kopfzeileSetzen(TABELLENHEADER);
            tabelle.vertikaleLinieSetzen(true);


            for (Map.Entry<MitarbeiterBescheinigung, Integer> map : mitarbeiterBescheinigungsMap.entrySet()) {
                mitarbeiterBescheinigungsArray[i] = map.getKey();

                // Ausgeben des Array
                String[] tempArray = map.getKey().attributenArrayErstellen();
                tempArray[0] = i + ".";
                tabelle.zeileHinzufuegen(tempArray);
                i++;
            }

            tabelle.ausgabe();
        }else{
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Diesem Mitarbeiter sind keine Zertifikate zugewiesen!");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.beliebigeTasteDrueckenAnzeigen();
            return null;
        }

        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte waehlen sie ein Zertifikat aus der Liste (1-" + (arrayLaenge - 1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        return mitarbeiterBescheinigungsArray[auswahl];
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode uebergibt ein Objekt in das jetztige
    Parameter: Objekt welches uebergeben werden soll
     */
    private void objektUebergeben(MitarbeiterBescheinigung mitarbeiterBescheinigung){

        this.id =                       mitarbeiterBescheinigung.id;
        this.vorname =                  mitarbeiterBescheinigung.vorname;
        this.nachname =                 mitarbeiterBescheinigung.nachname;
        this.mitarbeiterId =            mitarbeiterBescheinigung.mitarbeiterId;
        this.kurseId =                  mitarbeiterBescheinigung.kurseId;
        this.zertifikatId =             mitarbeiterBescheinigung.zertifikatId;
        this.zertifikatsAblaufDatum =   mitarbeiterBescheinigung.zertifikatsAblaufDatum;
        this.zertifikatsBeschreibung =  mitarbeiterBescheinigung.zertifikatsBeschreibung;
        this.zertifikatsTitel =         mitarbeiterBescheinigung.zertifikatsTitel;

    }

    // Erstellen eines Arrays fuer die Ausgabe in einer Tabelle
    private String[] attributenArrayErstellen(){
        String[] attributenArray = {"",vorname, nachname, zertifikatsTitel, zertifikatsAblaufDatum, zertifikatsBeschreibung};
        return attributenArray;
    }

    public int getId() {
        return id;
    }

    public String getZertifikatsAblaufDatum() {
        return zertifikatsAblaufDatum;
    }

    public int getMitarbeiterId() {
        return mitarbeiterId;
    }

    public int getZertifikatId() {
        return zertifikatId;
    }

    public int getKurseId() {
        return kurseId;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

}