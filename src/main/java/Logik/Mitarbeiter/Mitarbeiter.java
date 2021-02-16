package Logik.Mitarbeiter;

import DatenSchicht.*;
import Logik.Administratives.Kostenstelle;
import Logik.Services;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;

public class Mitarbeiter extends Services {


    private String[] unterMenue = {"1.  Mitarbeiter Anlegen","2.  Mitarbeiter Kurszuordnung", "3.  Mitarbeiter Zertifikatszuordnung", "4.  Zertifikat Verlaengern",
            "5.  Mitarbeiter Mutation", "6.  Mitarbeiter loeschen", "99. Hauptmenue"};
    private String[] anredeArray = {"Frau", "Herr", "Neutral"};
    private String[] statusArray = {"angestellt", "ausgetreten"};
    private String[] KOPFZEILE = {" ","Personal Nummer","Nachname","Vorname", "Geburtsdatum", "Kostenstelle", "Jobtitel", "Anstellungsstatus", "Anrede"};

    public int mitarbeiterId;
    public int personalNummer;
    public int kostenstelleId;
    public String anrede;
    public String vorname;
    public String nachname;
    public String jobTitel;
    public String geburtstag;
    public String mitarbeiterStatus;
    private String kostenstellenBezeichnung;

    public Mitarbeiter() {
        untermenueAnzeigen();
    }

    public Mitarbeiter(int mitarbeiterId, int personalNummer, int kostenstelleId, String anrede, String vorname, String nachname, String jobTitel, String geburtstag, String mitarbeiterStatusString) {
        this.mitarbeiterId = mitarbeiterId;
        this.personalNummer = personalNummer;
        this.kostenstelleId = kostenstelleId;
        this.anrede = anrede;
        this.vorname = vorname;
        this.nachname = nachname;
        this.jobTitel = jobTitel;
        this.geburtstag = geburtstag;
        this.mitarbeiterStatus = mitarbeiterStatusString;
        DatenLogikKostenstelle datenLogikKostenstelle =  new KostenstelleDatenbank();
        this.kostenstellenBezeichnung = datenLogikKostenstelle.kostenstellenBezeichnungAusgeben(kostenstelleId);
    }

    public void untermenueAnzeigen() {

        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue, "Logik/Mitarbeiter")) {
                case 1:
                    datenAnlegen();
                    break;
                case 2:
                    new MitarbeiterBescheinigung().kursZuweisen();
                    break;
                case 3:
                    new MitarbeiterBescheinigung().zertifikatZuweisen();
                    break;
                case 4:
                    new MitarbeiterBescheinigung().zertifikatVerlaengern();
                    break;
                case 5:
                    datenMutieren();
                    break;
                case 6:
                    datenLoeschen();
                    break;
                case 99:
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
    protected void datenAnlegen() {

        boolean abschliessen = true;
        String titelName = "Mitarbeiter Anlegen";

        do {
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie folgende Daten ein");
            //Anrede
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Anrede: ");
            int i = 1;

            for (String waehrung : anredeArray) {
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ". " + waehrung);
                i++;
            }
            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("Anrede (1-3): ");
            anrede = anredeArray[BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(3) - 1];
            //Vorname
            vorname = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Vorname: ");
            //Nachname
            nachname = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Nachname: ");
            //Geburtsdatum
            geburtstag = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geburtsdatum (dd.MM.yyyy): ");
            //Personal Nummer
            int zaehler = 0;
            do {
                if(zaehler > 0){BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Personalnummer bereits vergeben!");};
                personalNummer = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Personalnummer: ");
                zaehler++;
            }while(new MitarbeiterDatenbank().nummerAufExistenzPruefen(personalNummer).equals("EXISTIERT"));


            //Jobtitel
            jobTitel = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei255("Jobtitel: ");
            //Status beim Anlegen automatisch true
            mitarbeiterStatus = statusArray[0];
            //kostenstelle (organisation)

            Kostenstelle kostenstelle = new Kostenstelle();
            kostenstelle.auswahlListeKostenstelleAusgeben();
            kostenstelleId = kostenstelle.kostenstelleId;
            //kostenstellen Bezeichnung aus der Datenbank lesen
            DatenLogikKostenstelle datenLogikKostenstelle = new KostenstelleDatenbank();
            kostenstellenBezeichnung =  datenLogikKostenstelle.kostenstellenBezeichnungAusgeben(kostenstelleId);


            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE,attributenArrayFuerTabelle());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte ueberpruefen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    DatenLogik mitarbeiter = new MitarbeiterDatenbank();
                    mitarbeiter.datenAnlegen(this);
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
    Methode zur Bearbeitung eines Mitarbeiter
     */
    protected void datenMutieren() {

        String[] spaltenArray = {"Personalnummer","Anrede","Nachname","Vorname","Geburtsdatum","Abteilung","Jobtitel","Status"};
        String titelName = "Mitarbeiter Mutieren";
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

        do {
            BefehlsZeilenSchnittstelle.bildReinigen(titelName, 2);
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
                    //Personalnummer
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + personalNummer);
                    personalNummer = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die Personalnummer ein: ");
                    break;
                case 2:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName, 2);
                    //Anrede
                   BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + anrede);
                   BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Anrede: ");
                    int j = 1;

                    for (String anrede : anredeArray) {
                        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(j + ". " + anrede);
                        j++;
                    }
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Anrede (1-3): ");
                    anrede = anredeArray[BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(3) - 1];
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName, 2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + nachname);
                    nachname = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Nachnamen ein: ");
                    break;
                case 4:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + vorname);
                    vorname = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Vornamen ein: ");
                    break;
                case 5:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + geburtstag);
                    geburtstag = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben sie das Geburtsdatum ein: ");
                    break;
                case 6:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + kostenstelleId);
                    Kostenstelle kostenstelle = new Kostenstelle();
                    kostenstelle.auswahlListeKostenstelleAusgeben();
                    kostenstelleId = kostenstelle.kostenstelleId;
                    break;
                case 7:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + jobTitel);
                    jobTitel = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei255("Geben sie den Jobtitel ein: ");
                case 8:
                    BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Aktuell: " + mitarbeiterStatus);
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Mitarbeiterstatus: ");
                    int p = 1;

                    for (String anrede : statusArray) {
                        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(p + ". " + anrede);
                        p++;
                    }
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Anrede (1-2): ");
                    mitarbeiterStatus = statusArray[BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1];
                default:
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsche Eingabe!");
                    break;
            }

            //Eingaben ausgeben
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            objectInTabelleAusgeben(KOPFZEILE,attributenArrayFuerTabelle());
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte ueberpruefen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1:
                    DatenLogik mitarbeiterMutieren = new MitarbeiterDatenbank();
                    mitarbeiterMutieren.datenMutation(this);
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
    Methode zur Bearbeitung eines Mitarbeiter
     */
    protected void datenLoeschen(){
        boolean abschliessen = false;
        String titelName = "Mitarbeiter Loeschen";
        Mitarbeiter mitarbeiter;

        do {
            //Abfrage welchen Mitarbeiter geloescht werden soll
            //Aufrufen von MitarbeiterSuchen()
            mitarbeiter = new MitarbeiterSuche().mitarbeiterSuchen();
            //Ausgabe der Daten des ausgewaehlten Mitarbeiters
            BefehlsZeilenSchnittstelle.bildReinigen(titelName,2);
            mitarbeiter.objectInTabelleAusgeben(KOPFZEILE,mitarbeiter.attributenArrayFuerTabelle());
            //Abfrage ob der Mitarbeiter wirklich geloescht werden soll
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1: //1.Ja-> Mitarbeiter Loeschen Query aufrufen und Methode beenden
                    DatenLogikMitarbeiter mitarbeiterLoeschen =  new MitarbeiterDatenbank();
                    mitarbeiterLoeschen.datenLoeschen(mitarbeiter.mitarbeiterId);
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
    Die Methode uebergibt ein Objekt in das jetztige
    Parameter: Objekt welches uebergeben werden soll
     */
    void objektUebergeben(Mitarbeiter mitarbeiter){

        this.anrede = mitarbeiter.anrede;
        this.jobTitel = mitarbeiter.jobTitel;
        this.personalNummer = mitarbeiter.personalNummer;
        this.vorname = mitarbeiter.vorname;
        this.nachname = mitarbeiter.nachname;
        this.mitarbeiterStatus = mitarbeiter.mitarbeiterStatus;
        this.geburtstag = mitarbeiter.geburtstag;
        this.mitarbeiterId = mitarbeiter.mitarbeiterId;
        this.kostenstelleId = mitarbeiter.kostenstelleId;
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
    Diese Methode packt die Membervariablen in ein Array fuer die Ausgabe in einer Tabelle
     */
    protected String[] attributenArrayFuerTabelle(){
        String[] attributenArray = {" ",String.valueOf(personalNummer),nachname,vorname, geburtstag, kostenstellenBezeichnung, jobTitel, mitarbeiterStatus, anrede};
        return attributenArray;
    }
}