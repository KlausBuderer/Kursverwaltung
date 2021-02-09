package Zertifikate;

import Datenbank.ZertifikatsDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.Scanner;

public class Zertifikate{

    String [] unterMenue = {"1.  Zertifikate Anlegen", "2.  Zertifikate Mutation","3.  Zertifikat Loeschen", "99. Hauptmenue"};
    public int zertifikatsId;
    public int kosten;
    public String waehrung;
    public String zertifikatsTitel;
    public String zertifikatsBeschreibung;
    public String anbieter;
    public String sprache;

    Scanner scan = new Scanner(System.in);


    public Zertifikate(){

        untermenueAnzeigen();

    }

    public Zertifikate(int zertifikatsId, int kosten, String zertifikatsTitel, String zertifikatsBeschreibung, String anbieter, String sprache, String waehrung) {
        this.zertifikatsId = zertifikatsId;
        this.kosten = kosten;
        this.zertifikatsTitel = zertifikatsTitel;
        this.zertifikatsBeschreibung = zertifikatsBeschreibung;
        this.anbieter = anbieter;
        this.sprache = sprache;
        this.waehrung = waehrung;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anezigeb des Untermenues

     */
    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue,"Zertifikate")) {
                case 1:
                    zertifikatAnlegen();
                    break;
                case 2:
                    zertifikatMutieren();
                    break;
                case 3:
                    zertifikatLoeschen();
                    break;
                case 99:
                    //zurueck ins Hauptmenue;
                    System.out.println("Hauptmenue");
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anlegen von Zertifikaten

     */
    void zertifikatAnlegen() {

        boolean abschliessen = true;
        String titelName = "Zertifikat Anlegen";

        do {
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            System.out.println("Bitte geben sie folgende Daten ein");
            //Zertifikats Titel
            zertifikatsTitel = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Zertifikats Titel: ");
            //Anbieter
            anbieter = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Anbieter: ");
            //Beschreibung
            zertifikatsBeschreibung = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei45("Beschreibung: ");
            //Kosten
            kosten = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Kosten: ");
            //Waehrung
            waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
            //Sprache
            sprache = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Sprache: ");

            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte ueberpruefen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    ZertifikatsDatenbank zertifikatsDatenbank = new ZertifikatsDatenbank();
                    zertifikatsDatenbank.datenAnlegen(this);
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
    void zertifikatMutieren() {

        String[] spaltenArray = {"Zertifikatstitel","Anbieter","Zertifikatsbeschreibung", "Kosten", "Waehrung", "Sprache"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;
        String titelName = "Zertifikat Mutieren";

        ZertifikateSuchen zertifikateSuchen = new ZertifikateSuchen();
        Zertifikate zertifikat;

        try {
            zertifikat = zertifikateSuchen.zertifikatSuchen();
            objektUebergabe(zertifikat);
        }catch (NullPointerException exception){
            System.out.println("Kein Treffer");
            return;
        }

        do {
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            int i = 1;
            for (String spalte : spaltenArray) {

                System.out.println(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            System.out.print("Welchen Spalte moechten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    System.out.println("Aktuell: " + zertifikatsTitel);
                    System.out.println("Geben sie einen neuen Titel ein: ");
                    zertifikatsTitel = scan.next();
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    System.out.println("Aktuell: " + anbieter);
                    System.out.print("Geben sie einen neuen Anbieter an: ");
                    anbieter = scan.next();
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    System.out.println("Aktuell: " + zertifikatsBeschreibung);
                    System.out.print("Geben sie eine neue Kursbeschreibung ein: ");
                    zertifikatsBeschreibung = scan.next();
                    break;
                case 4:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    System.out.println("Aktuell: " + kosten);
                    System.out.print("Geben sie die neuen Kosten ein: ");
                    kosten = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
                    break;
                case 5:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    System.out.println("Aktuell: " + waehrung);
                    waehrung = BefehlsZeilenSchnittstelle.abfrageWaehrung();
                    break;
                case 6:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    System.out.println("Aktuell: " + sprache);
                    sprache = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie eine neue Sprache ein: ");
                    break;
                default:
                    System.out.println("Falsche Eingabe!");
                    break;
            }

            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte ueberpruefen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1:
                    ZertifikatsDatenbank zertifikatsDatenbank = new ZertifikatsDatenbank();
                    zertifikatsDatenbank.datenMutation(this);
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
    Methode zum Loeschen eines Zertifikats
     */

    private void zertifikatLoeschen(){
        boolean abschliessen = false;
        Zertifikate zertifikat;
        String titelName = "Zertifikat loeschen";

        do {
            //Abfrage welchen Kurs geloescht werden soll
            //Aufrufen von MitarbeiterSuchen()
            zertifikat = new ZertifikateSuchen().zertifikatSuchen();
            //Ausgabe der Daten des ausgewaehlten Kurses
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            System.out.println(zertifikat.toString());
            //Abfrage ob der Kurs wirklich geloescht werden soll
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: //1.Ja-> MitarbeiterLoeschenQuery aufrufen und Methode beenden
                    new ZertifikatsDatenbank().zertifikatLoeschen(zertifikat.zertifikatsId);
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
    Methode fuer die uebergabe von den Attributen eines Objekts
     */
    public void objektUebergabe(Zertifikate zertifikat) {

        // Die Daten des gewaehlten Objekts werden in das sich vorhandene Objekt geschrieben
        this.zertifikatsId =             zertifikat.zertifikatsId;
        this.kosten =                    zertifikat.kosten;
        this.waehrung =                  zertifikat.waehrung;
        this.zertifikatsTitel =          zertifikat.zertifikatsTitel;
        this.anbieter =                  zertifikat.anbieter;
        this.zertifikatsBeschreibung =   zertifikat.zertifikatsBeschreibung;
        this.sprache =                   zertifikat.sprache;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    ToString Methode
     */
    @Override
    public String toString() {
        return  "Titel: " + BefehlsZeilenSchnittstelle.textFormatieren(zertifikatsTitel, 30 )+
                "ZertifikatsBeschreibung: " + BefehlsZeilenSchnittstelle.textFormatieren(zertifikatsBeschreibung, 50 ) +
                "Anbieter: " + BefehlsZeilenSchnittstelle.textFormatieren(anbieter, 15 ) +
                "Sprache: " + BefehlsZeilenSchnittstelle.textFormatieren(sprache, 10 ) +
                "Kosten: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(kosten), 10 ) +
                "Waehrung: " + BefehlsZeilenSchnittstelle.textFormatieren(waehrung, 30 ) ;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    ToString Methode
     */
    public String[] attributenArrayFuerTabelle(){
        String[] attributenArray = {"" ,zertifikatsTitel,zertifikatsBeschreibung,anbieter,sprache, String.valueOf(kosten),waehrung};
     return attributenArray;
    }
}
