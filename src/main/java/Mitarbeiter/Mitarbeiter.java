package Mitarbeiter;

import Administratives.*;
import Datenbank.MitarbeiterDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.Scanner;

public class Mitarbeiter{


    private String[] unterMenue = {"Mitarbeitermenü", "1. Mitarbeiter anlegen", "2. Mitarbeiter anlegen Massenimport",
            "3. Kurs an Mitarbeiter zuordnen", "4. Mitarbeiter neue Zertifikate.Zertifikate zuordnen", "5. Zertifikate von Mitarbeiter erneuern",
            "6. Mitarbeiter Informationen auslesen", "7. Mitarbeiter bearbeiten", "8. Hauptmenue"};
    private String[] anredeArray = {"Frau", "Herr", "Andere"};

    private boolean mitarbeiterStatus;
    private int mitarbeiterId;
    private int personalNummer;
    private int organisationsId;
    private String anrede;
    private String vorname;
    private String nachname;
    private String jobTitel;
    private String geburtstag;
    private String mitarbeiterStatusString;

    Scanner scan = new Scanner(System.in);

    public Mitarbeiter() {
        untermenueAnzeigen();
    }

    public Mitarbeiter(int mitarbeiterId, int personalNummer, int organisationsId, String anrede, String vorname, String nachname, String jobTitel, String geburtstag, String mitarbeiterStatusString) {
        this.mitarbeiterId = mitarbeiterId;
        this.personalNummer = personalNummer;
        this.organisationsId = organisationsId;
        this.anrede = anrede;
        this.vorname = vorname;
        this.nachname = nachname;
        this.jobTitel = jobTitel;
        this.geburtstag = geburtstag;
        this.mitarbeiterStatusString = mitarbeiterStatusString;
    }

    private void untermenueAnzeigen() {

        boolean gueltigeEingabe = false;


        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    mitarbeiterAnlegen();
                    break;
                case 2:
                    System.out.println(unterMenue[2]);
                    break;
                case 3:
                    System.out.println(unterMenue[3]);
                    break;
                case 4:
                    System.out.println(unterMenue[4]);
                    break;
                case 5:
                    System.out.println(unterMenue[5]);
                    break;
                case 6:
                    System.out.println(unterMenue[6]);
                    break;
                case 7:
                    System.out.println(unterMenue[7]);
                    break;
                case 8:
                    System.out.println(unterMenue[8]);
                    gueltigeEingabe = true;
                    break;
                default:
                    gueltigeEingabe = false;
            }
        } while (!gueltigeEingabe);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Mitarbeiter.Mitarbeiter anlegen
     */


    void mitarbeiterAnlegen() {


        boolean abschliessen = true;

        do {
            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Anrede
            System.out.println("Anrede: ");
            int i = 1;

            for (String waehrung : anredeArray) {
                System.out.println(i + ". " + waehrung);
                i++;
            }
            System.out.println("Anrede (1-3): ");
            anrede = anredeArray[BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(3) - 1];
            //Vorname
            vorname = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Vorname: ");
            //Nachname
            nachname = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Nachname: ");
            //Geburtsdatum
            geburtstag = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geburtsdatumg (dd.MM.yyyy): ");
            //Personal Nummer
            personalNummer = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Personalnummer: ");
            //Jobtitel
            jobTitel = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Jobtitel: ");
            //Status beim Anlegen automatisch true
            mitarbeiterStatus = true;
            //kostenstelle (organisation)

            Organisation organisation = new Organisation();
            organisation.auswahlListeOrganisationAusgeben();
            organisationsId = organisation.organisationsId;


            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    MitarbeiterDatenbank mitarbeiter = new MitarbeiterDatenbank();
                    mitarbeiter.mitarbeiterAnlegen(anlegenQuerry());
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
    To String Methode
     */
    @Override
    public String toString() {
        return "Anrede " + BefehlsZeilenSchnittstelle.textFormatieren(anrede, 15)
                + "Vorname " + BefehlsZeilenSchnittstelle.textFormatieren(vorname, 15) +
                "Nachname " + BefehlsZeilenSchnittstelle.textFormatieren(nachname, 15) +
                "PersonalNummer " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(personalNummer), 15) +
                "Geburtstag " + BefehlsZeilenSchnittstelle.textFormatieren(geburtstag, 15) +
                "JobTitel " + BefehlsZeilenSchnittstelle.textFormatieren(jobTitel, 25);

    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum erstellen des Sql Querry
     */

    String anlegenQuerry(){
        if (mitarbeiterStatus) {

            mitarbeiterStatusString = "angestellt";

        }else{
            mitarbeiterStatusString = "nicht angestellt";
        }

        return "INSERT INTO `itwisse_kursverwaltung`.`Mitarbeiter`" +
                " (`PersonalNr`, `Anrede`, `Vorname`, `Nachname`, `Jobtitel`, `Geburtsdatum`, `Statusmitarbeiter`, `OrganisationID`)" +
                " VALUES ('" + personalNummer + "', '" + anrede + "', '" + vorname + "', '" + nachname + "', '" + jobTitel + "', '" +
                geburtstag + "', '" + mitarbeiterStatusString + "', '" + organisationsId +"')";

    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Bearbeitung eines Budget
     */
    private void mitarbeiterBearbeiten() {

        String[] spaltenArray = {"Personalnummer","Nachname","Vorname","Geburtstag","Abteilung","Jobtitel","Status"};
        int arrayLaenge;
        int auswahl;
        boolean abschliessen = true;
        Mitarbeiter mitarbeiter;

        MitarbeiterSuche mitarbeiterSuche = new MitarbeiterSuche();
        try {
            mitarbeiter = mitarbeiterSuche.mitarbeiterSuchen();
            objektUebergeben(mitarbeiter);
        }catch (NullPointerException exception){
            return;
        }

      /*  do {
            Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
            int i = 1;
            for (String spalte : spaltenArray) {

                System.out.println(i + ": " + spalte);
                i++;
            }
            arrayLaenge = spaltenArray.length;

            System.out.print("Welchen Spalte möchten sie Bearbeiten? (1-" + (arrayLaenge) + "):");
            auswahl = Utilities.BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

            switch (auswahl) {

                case 1:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    //Anrede
                    System.out.println("Aktuell: " + mitarbeiter.anrede);
                    System.out.println("Anrede: ");
                    int j = 1;

                    for (String waehrung : anredeArray) {
                        System.out.println(j + ". " + waehrung);
                        j++;
                    }
                    System.out.println("Anrede (1-3): ");
                    anrede = anredeArray[Utilities.BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(3) - 1];
                    break;
                case 2:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + anbieter);
                    System.out.print("Geben sie einen neuen Anbieter an: ");
                    anbieter = scan.next();
                    break;
                case 3:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + zertifikatsBeschreibung);
                    System.out.print("Geben sie eine neue Kursbeschreibung ein: ");
                    zertifikatsBeschreibung = scan.next();
                    break;
                case 4:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + kosten);
                    System.out.print("Geben sie die neuen Kosten ein: ");
                    kosten = Utilities.BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
                    break;
                case 5:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + waehrung);
                    waehrung = Utilities.BefehlsZeilenSchnittstelle.abfrageWaehrung();
                    break;
                case 6:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + sprache);
                    System.out.print("Geben sie eine neue Sprache ein: ");
                    sprache = Utilities.BefehlsZeilenSchnittstelle.pruefeDatum();
                    break;
                default:
                    System.out.println("Falsche Eingabe!");
                    break;
            }

            Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (Utilities.BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: datenBearbeiten(updateQuerry());
                    abschliessen = true;
                    break;
                case 2: abschliessen = false;
                    break;
                case 3: abschliessen = true;
                    break;
            }

        }while(!abschliessen);*/
    }


    /*

    Die Methode übergibt ein Objekt in das jetztige

    Parameter: Objekt welches übergeben werden soll

     */

    void objektUebergeben(Mitarbeiter mitarbeiter){

        this.anrede = mitarbeiter.anrede;
        this.jobTitel = mitarbeiter.jobTitel;
        this.personalNummer = mitarbeiter.personalNummer;
        this.vorname = mitarbeiter.vorname;
        this.nachname = mitarbeiter.nachname;
        this.mitarbeiterStatusString = mitarbeiter.mitarbeiterStatusString;
        this.geburtstag = mitarbeiter.geburtstag;
        this.mitarbeiterId = mitarbeiter.mitarbeiterId;
        this.organisationsId = mitarbeiter.organisationsId;
    }

}