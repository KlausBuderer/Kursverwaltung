package Logik.Kurse;

import DatenSchicht.DatenLogik;
import DatenSchicht.KursDatenbank;
import Logik.Services;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;

public class Kurse extends Services {

    private int kurseId;
    private int kosten = 1;

    private String waehrung;
    private String kursCode;
    private String anbieter;
    private String kursBeschreibung;
    private String datumVon;
    private String datumBis;
    private String durchfuehrungsOrt;

    private final String [] UNTERMENUE = {"1.  Kurse Anlegen", "2.  Kurse Mutation","3.  Kurs loeschen", "99. Hauptmenue"};
    private final String[] KOPFZEILE = {" ","Kurs Code","Anbieter", "Kurs Beschreibung","Kosten", "Waerung", "Datum Von","Datum Bis","Durchfuehrungsort"};


   //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

   //Konstrukor Aufruf des Untermenüs
    public Kurse(){
    untermenueAnzeigen();
    }

    //Konstruktor um ein neues Objekt zu erstellen
    public Kurse(int kurseId, int kosten, String waehrung, String kursCode, String anbieter, String kursBeschreibung, String datumVon, String datumBis, String durchfuerungsOrt) {
        this.kurseId = kurseId;
        this.kosten = kosten;
        this.waehrung = waehrung;
        this.kursCode = kursCode;
        this.anbieter = anbieter;
        this.kursBeschreibung = kursBeschreibung;
        this.datumVon = datumVon;
        this.datumBis = datumBis;
        this.durchfuehrungsOrt = durchfuerungsOrt;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anzeigen des Untermenues
     */
    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(UNTERMENUE, "Logik")) {
                case 1:
                    datenAnlegen();
                    break;
                case 2:
                    datenMutieren();
                    break;
                case 3:
                    datenLoeschen();
                    break;
                case 99:
                    //zurueck ins Hauptmenue;
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Hauptmenue");
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anlegen von Kursen
     */
    protected void datenAnlegen() {

        boolean abschliessen = true;
        String titelName = "Kurse Anlegen";

        do {//Eingabe der Daten
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie folgende Daten ein");
            //Kurs Code
            kursCode = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Kurs Code: ");
            //Anbieter
            anbieter = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Anbieter: ");
            //Kursbeschreibung
            kursBeschreibung = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei255("Kursbeschreibung: ");
            //Kosten
            kosten = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kosten: ");
            //Waehrung
            waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
            //Datum von
            datumVon = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Start Datum: ");
            //Datum bis
            datumBis = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("End-Datum: ");
            //Durchfuehrungsort
            durchfuehrungsOrt = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Durchfuehrungsort: ");

            // Ausgabe der eingegebenen Daten
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE, attributenArrayFuerTabelle());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte ueberpruefen sie die Korrektheit der Erfassten Daten");

            //Bestätigung der Eingaben
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {
                case 1://Speichern
                    DatenLogik kursDatenbank = new KursDatenbank();
                        kursDatenbank.datenAnlegen(this);
                    abschliessen = true;
                    break;
                case 2://Neu beginnen
                    abschliessen = false;
                    break;
                case 3://Abbrechen
                    abschliessen = true;
                    break;
            }
        }while (!abschliessen) ;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung eines Budget
     */
    protected void datenMutieren() {

        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;
        String titelName = "Kurse Mutieren";
        String[] spaltenArray = {"Kurs Code","Anbieter","Kursbeschreibung", "Kosten", "Waehrung", "Start Datum", "End Datum", "Durchfuehrungs Ort"};
        Kurse kurs;

        KursSuchen kursSuchen = new KursSuchen();

        try {// Suchen des Kurses der geändert werden soll
            kurs = kursSuchen.kursSuchen();
            objektUebergeben(kurs);
        }catch (NullPointerException exception0){
            return;
        }
        do {//Auswahl welches Attribut geändert werden soll
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            int i = 1;
            for (String spalte : spaltenArray) {

                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Welchen Spalte moechten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            //Eingabe der Änderung
            switch (auswahl) {
                case 1:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + kursCode);
                    kursCode = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Geben sie einen neuen Code ein: ");
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + anbieter);
                    anbieter = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie einen neuen Anbieter an: ");
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + kursBeschreibung);
                    kursBeschreibung = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei255("Geben sie eine neue Kursbeschreibung ein: ");
                    break;
                case 4:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + kosten);
                    BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Geben sie die neuen Kosten ein: ");
                    kosten = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
                    break;
                case 5:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + waehrung);
                    waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
                    break;
                case 6:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + datumVon);
                    BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Geben sie das neue Start Datum ein: ");
                    datumVon = BefehlsZeilenSchnittstelle.pruefeDatum();
                    break;
                case 7:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + datumBis);
                    BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Geben sie das neue End Datum ein: ");
                    datumBis = BefehlsZeilenSchnittstelle.pruefeDatum();
                    break;
                case 8:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + durchfuehrungsOrt);
                    durchfuehrungsOrt = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den neuen Durchfuehrungsort ein: ");
                    break;
                default:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
                    break;
            }

            //Ausgabe der eingegebenen Daten
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE, attributenArrayFuerTabelle());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte ueberpruefen sie die Korrektheit der Erfassten Daten");

            //Bestätigung der Eingaben
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){
                case 1://Speichern
                    DatenLogik kursDatenbank = new KursDatenbank();
                    kursDatenbank.datenMutation(this);
                    abschliessen = true;
                    break;
                case 2: //Neu beginnen
                    abschliessen = false;
                    break;
                case 3: //Abbrechen
                    abschliessen = true;
                    break;
            }

        }while(!abschliessen);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Loeschen eines Kurses
     */
    protected void datenLoeschen(){
        boolean abschliessen = false;
        String titelName = "Kurse loeschen";
        Kurse kurs;

        do {
            //Abfrage welchen Kurs geloescht werden soll
            //Aufrufen von MitarbeiterSuchen()
            kurs = new KursSuchen().kursSuchen();
            //Ausgabe der Daten des ausgewaehlten Kurses
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE, attributenArrayFuerTabelle());

//            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(kurs.toString());
            //Abfrage ob der Kurs wirklich geloescht werden soll
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: //1.Ja-> KursLoeschen aufrufen und Methode beenden
                    DatenLogik kursLoeschen = new KursDatenbank();
                    kursLoeschen.datenLoeschen(kurs.kurseId);
                    abschliessen = true;
                    break;
                case 2: //2.Nein-> Springe zu Aufrufen MitarbeiterSuchen()
                    abschliessen = false;
                    break;
                case 3: //3.Abbrechen-> Methode Beenden
                    abschliessen = true;
                    break;
            }

        }while(!abschliessen);
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Diese Methode packt die Membervariablen in ein Array fuer die Ausgabe in einer Tabelle
    */
    protected String[] attributenArrayFuerTabelle() {
        String[] attributenString = {" ",kursCode,anbieter, kursBeschreibung,String.valueOf(kosten), waehrung, datumVon,datumBis,durchfuehrungsOrt};
        return attributenString;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum uebergeben eines Objekt in dieses Objekt
     */
    private void objektUebergeben(Kurse kurs){

        this.kurseId =            kurs.kurseId;
        this.kosten =             kurs.kosten;
        this.waehrung =           kurs.waehrung;
        this.kursCode =           kurs.kursCode;
        this.anbieter =           kurs.anbieter;
        this.kursBeschreibung =   kurs.kursBeschreibung;
        this.datumVon =           kurs.datumVon;
        this.datumBis =           kurs.datumBis;
        this.durchfuehrungsOrt =  kurs.durchfuehrungsOrt;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    ToString Methode
     */
    @Override
    public String toString() {
        return

                " KursCode: " + kursCode +
                " Anbieter: " + anbieter +
                " Kosten: " + kosten +
                " KursBeschreibung: " + kursBeschreibung +
                " DatumVon: " + datumVon +
                " DatumBis: " + datumBis +
                " DurchfuerungsOrt: " + durchfuehrungsOrt +
                " Waehrung: " + waehrung;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    public int getKurseId() {
        return kurseId;
    }

    public int getKosten() {
        return kosten;
    }

    public String getWaehrung() {
        return waehrung;
    }

    public String getKursCode() {
        return kursCode;
    }

    public String getAnbieter() {
        return anbieter;
    }

    public String getKursBeschreibung() {
        return kursBeschreibung;
    }

    public String getDatumVon() {
        return datumVon;
    }

    public String getDatumBis() {
        return datumBis;
    }

    public String getDurchfuehrungsOrt() {
        return durchfuehrungsOrt;
    }
}
