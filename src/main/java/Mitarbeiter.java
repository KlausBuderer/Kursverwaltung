import java.util.Scanner;

public class Mitarbeiter extends Datenbank {


    String[] unterMenue = {"Mitarbeitermenü", "1. Mitarbeiter anlegen", "2. Mitarbeiter anlegen Massenimport",
            "3. Kurs an Mitarbeiter zuordnen", "4. Mitarbeiter neue Zertifikate zuordnen", "5. Zertifikate von Mitarbeiter erneuern",
            "6. Mitarbeiter Informationen auslesen", "7. Mitarbeiter bearbeiten", "8. Hauptmenue"};
    String[] anredeArray = {"Frau", "Herr", "Neutral"};
    boolean mitarbeiterStatus;
    int mitarbeiterId;
    int personalNummer;
    int organisationsId;
    String anrede;
    String vorname;
    String nachname;
    String jobTitel;
    String geburtstag;
    String mitarbeiterStatusString;

    Scanner scan = new Scanner(System.in);

    Mitarbeiter() {
        untermenueAnzeigen();
    }

    public void untermenueAnzeigen() {

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
    Methode zum Mitarbeiter anlegen
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
            System.out.print("Vorname: ");
            vorname = scan.next();
            //Nachname
            System.out.print("Nachname: ");
            nachname = scan.next();
            //Geburtsdatum
            System.out.print("Geburtsdatum (yyyy-MM-dd): ");
            geburtstag = BefehlsZeilenSchnittstelle.pruefeDatum();
            //Personal Nummer
            System.out.print("Personalnummer: ");
            personalNummer = BefehlsZeilenSchnittstelle.eingabeAufIntegerPruefen();
            //Jobtitel
            System.out.print("Jobtitel: ");
            jobTitel = scan.next();

            //Status beim Anlegen automatisch true
            mitarbeiterStatus = true;
            //kostenstelle (organisation)

            Administratives administratives = new Administratives("test");
            administratives.auswahlListeOrganisationAusgeben();
            organisationsId = administratives.organisationsID;


            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println(toString());
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");

            switch (BefehlsZeilenSchnittstelle.korrekteEingabebestaetigen()) {

                case 1:
                    datenAnlegenAllgemein(anlegenQuerry());
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

    @Override
    public String toString() {
        return "Anrede " + BefehlsZeilenSchnittstelle.textFormatieren(anrede, 15)
                + "Vorname " + BefehlsZeilenSchnittstelle.textFormatieren(vorname, 15) +
                "Nachname " + BefehlsZeilenSchnittstelle.textFormatieren(nachname, 15) +
                "PersonalNummer " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(personalNummer), 15) +
                "Geburtstag " + BefehlsZeilenSchnittstelle.textFormatieren(geburtstag, 15) +
                "JobTitel " + BefehlsZeilenSchnittstelle.textFormatieren(jobTitel, 25);

    }


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
}