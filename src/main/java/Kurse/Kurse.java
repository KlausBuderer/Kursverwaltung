package Kurse;

import Datenbank.KursDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.Scanner;

public class Kurse{

    private String [] unterMenue = {"1.  Kurse Anlegen", "2.  Kurse Mutation","3.  Kurs loeschen", "99. Hauptmenue"};
    public int kurseId;
    public int kosten = 1;
    public String waehrung;
    public String kursCode;
    public String anbieter;
    public String kursBeschreibung;
    public String datumVon;
    public String datumBis;
    public String durchfuehrungsOrt;


    Scanner scan = new Scanner(System.in);

    public Kurse(){
    untermenueAnzeigen();

    }


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
    Methode zum Anzeigen des Untermenüs
     */
    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue,"Kurse")) {
                case 1:
                    kurseAnlegen();
                    break;
                case 2:
                    kurseMutieren();
                    break;
                case 3:
                    kursLoeschen();
                    break;
                case 99:
                    //zurück ins Hauptmenü;
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Hauptmenü");
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
    void kurseAnlegen() {

        boolean abschliessen = true;
        String titelName = "Kurse Anlegen";

        do {
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

            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(toString());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    KursDatenbank kursDatenbank = new KursDatenbank();
                        kursDatenbank.datenAnlegen(this);
                    abschliessen = true;
                    break;
                case 2:
                    abschliessen = false;
                    break;
                case 3:
                    abschliessen = true;
                    break;
            }
        }while (!abschliessen) ;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung eines Budget
     */
    void kurseMutieren() {

        String[] spaltenArray = {"Kurs Code","Anbieter","Kursbeschreibung", "Kosten", "Waehrung", "Start Datum", "End Datum", "Durchfuehrungs Ort"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;
        String titelName = "Kurse Mutieren";
        Kurse kurs;

        KursSuchen kursSuchen = new KursSuchen();

        try {
            kurs = kursSuchen.kursSuchen();
            objektUebergeben(kurs);
        }catch (NullPointerException exception0){
            return;
        }
        do {
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
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

            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(toString());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1:
                    KursDatenbank kursDatenbank = new KursDatenbank();
                    kursDatenbank.datenMutation(this);
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
    Methode zum Loeschen eines Kurses
     */

    private void kursLoeschen(){
        boolean abschliessen = false;
        String titelName = "Kurse loeschen";
        Kurse kurs;

        do {
            //Abfrage welchen Kurs gelöscht werden soll
            //Aufrufen von MitarbeiterSuchen()
            kurs = new KursSuchen().kursSuchen();
            //Ausgabe der Daten des ausgewählten Kurses
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(kurs.toString());
            //Abfrage ob der Kurs wirklich gelöscht werden soll
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: //1.Ja-> MitarbeiterLoeschenQuery aufrufen und Methode beenden
                    new KursDatenbank().kursLoeschen(kurs.kurseId);
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
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum übergeben eines Objekt in dieses Objekt

     */
    public void objektUebergeben(Kurse kurs){

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
       /*
    Diese Methode packt die Membervariablen in ein Array für die Ausgabe in einer Tabelle
     */
    public String[] attributenString() {

        String[] attributenString = {"",kursCode,anbieter, kursBeschreibung,String.valueOf(kosten), waehrung, datumVon,datumBis,durchfuehrungsOrt};

        return attributenString;
    }
}
