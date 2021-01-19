package Mitarbeiter;

import Administratives.Kostenstelle;
import Datenbank.MitarbeiterDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

public class Mitarbeiter{


    private String[] unterMenue = {"Mitarbeitermenue", "1.  Mitarbeiter Anlegen","2.  Mitarbeiter Kurszuordnung", "3.  Mitarbeiter Zertifikatszuordnung", "4.  Zertifikat Verlängern",
            "5.  Mitarbeiter Mutation", "6.  Mitarbeiter Massenimport", "99. Hauptmenue"};
    private String[] anredeArray = {"Frau", "Herr", "Andere"};
    private String[] statusArray = {"angestellt", "ausgetreten"};

    public int mitarbeiterId;
    public int personalNummer;
    public int kostenstelleId;
    public String anrede;
    public String vorname;
    public String nachname;
    public String jobTitel;
    public String geburtstag;
    public String mitarbeiterStatus;

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
    }

    private void untermenueAnzeigen() {

        boolean gueltigeEingabe = false;

        do {
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    mitarbeiterAnlegen();
                    break;
                case 2:
                    new MitarbeiterBescheinigung().kursZuweisen();
                    break;
                case 3:
                    new MitarbeiterBescheinigung().zertifikatZuweisen();
                    break;
                case 4:
                    System.out.println(unterMenue[4]);
                    break;
                case 5:
                    mitarbeiterMutieren();
                    System.out.println(unterMenue[5]);
                    break;
                case 6:
                    System.out.println(unterMenue[6]);
                    break;
                case 99:
                    System.out.println(unterMenue[7]);
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
    private void mitarbeiterAnlegen() {

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
            geburtstag = BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geburtsdatum (dd.MM.yyyy): ");
            //Personal Nummer
            personalNummer = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Personalnummer: ");
            //Jobtitel
            jobTitel = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Jobtitel: ");
            //Status beim Anlegen automatisch true
            mitarbeiterStatus = statusArray[0];
            //kostenstelle (organisation)

            Kostenstelle kostenstelle = new Kostenstelle();
            kostenstelle.auswahlListeKostenstelleAusgeben();
            kostenstelleId = kostenstelle.kostenstelleId;

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    MitarbeiterDatenbank mitarbeiter = new MitarbeiterDatenbank();
                    mitarbeiter.mitarbeiterAnlegen(this);
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
    private void mitarbeiterMutieren() {

        String[] spaltenArray = {"Personalnummer","Anrede","Nachname","Vorname","Geburtsdatum","Abteilung","Jobtitel","Status"};
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

        System.out.println(mitarbeiter.toString());
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
                    //Personalnummer
                    System.out.println("Aktuell: " + personalNummer);
                    personalNummer = BefehlsZeilenSchnittstelle.abfrageMitEingabeInt("Geben sie die Personalnummer ein: ");
                    break;
                case 2:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    //Anrede
                    System.out.println("Aktuell: " + anrede);
                    System.out.println("Anrede: ");
                    int j = 1;

                    for (String anrede : anredeArray) {
                        System.out.println(j + ". " + anrede);
                        j++;
                    }
                    System.out.println("Anrede (1-3): ");
                    anrede = anredeArray[Utilities.BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(3) - 1];
                    break;
                case 3:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + nachname);
                    nachname = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Nachnamen ein: ");
                    break;
                case 4:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + vorname);
                    vorname = Utilities.BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Vornamen ein: ");
                    break;
                case 5:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + geburtstag);
                    geburtstag = Utilities.BefehlsZeilenSchnittstelle.abfrageMitEingabeDatum("Geben sie das Geburtsdatum ein: ");
                    break;
                case 6:
                    Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + kostenstelleId);
                    Kostenstelle kostenstelle = new Kostenstelle();
                    kostenstelle.auswahlListeKostenstelleAusgeben();
                    kostenstelleId = kostenstelle.kostenstelleId;
                    break;
                case 7:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + jobTitel);
                    jobTitel = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Geben sie den Jobtitel ein: ");
                case 8:
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Aktuell: " + mitarbeiterStatus);
                    System.out.println("Mitarbeiterstatus: ");
                    int p = 1;

                    for (String anrede : statusArray) {
                        System.out.println(p + ". " + anrede);
                        p++;
                    }
                    System.out.println("Anrede (1-2): ");
                    mitarbeiterStatus = statusArray[Utilities.BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1];
                default:
                    System.out.println("Falsche Eingabe!");
                    break;
            }

            Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (Utilities.BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1:
                    new MitarbeiterDatenbank().datenMutation(this);
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
    Die Methode übergibt ein Objekt in das jetztige
    Parameter: Objekt welches übergeben werden soll
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
    Diese Methode packt die Membervariablen in ein Array für die Ausgabe in einer Tabelle
     */
    String[] attributenArrayFuerTabelle(){
        String[] attributenArray = {"",String.valueOf(personalNummer),nachname,vorname, geburtstag, String.valueOf(kostenstelleId), jobTitel, mitarbeiterStatus, anrede};
        return attributenArray;
    }
}