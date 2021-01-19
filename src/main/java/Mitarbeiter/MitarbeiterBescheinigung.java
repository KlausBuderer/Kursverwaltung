package Mitarbeiter;

import Datenbank.MitarbeiterDatenbank;
import Kurse.KursSuchen;
import Kurse.*;
import Utilities.BefehlsZeilenSchnittstelle;
import Zertifikate.*;

public class MitarbeiterBescheinigung {

    public int id;
    public String zertifikatsAblaufDatum = "0000-00-00";
    public int mitarbeiterId;
    public int zertifikatId;
    public int kurseId;
    public enum kontextAnlegen  {KURS,ZERTIFIKAT};



    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um einen Kurs einem Mitarbeiter zuzuweisen
     */
    public void kursZuweisen() {

        Mitarbeiter mitarbeiter;
        Kurse kurs;
        boolean abschliessen = false;

        do {
            //Mitarbeiter auswählen
            System.out.println("Welchem Mitarbeiter moechten sie einen Kurs zuweisen?");
            mitarbeiter = new MitarbeiterSuche().mitarbeiterSuchen();
            this.mitarbeiterId = mitarbeiter.mitarbeiterId;

            //Kurs auswählen
            System.out.println("Welchen Kurs moechten sie dem Mitarbeiter zuweisen?");
            kurs = new KursSuchen().kursSuchen();
            this.kurseId = kurs.kurseId;


            Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte bestätigen sie die richtige Eingabe");
            System.out.println();
            System.out.println("Mitarbeiter: " + mitarbeiter.vorname + " " + mitarbeiter.nachname);
            System.out.println("Kurs: " + kurs.anbieter + " " + kurs.kursBeschreibung + " " + kurs.datumVon);

            switch (Utilities.BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    new MitarbeiterDatenbank().mitarbeiterBescheinigungAnlegen(this, kontextAnlegen.KURS);
                    abschliessen = true;
                    break;
                case 2:
                    abschliessen = false;
                    break;
                case 3:
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

        do {
            //Mitarbeiter auswählen
            System.out.println("Welchem Mitarbeiter moechten sie ein Zertifikat zuweisen?");
            mitarbeiter = new MitarbeiterSuche().mitarbeiterSuchen();
            this.mitarbeiterId = mitarbeiter.mitarbeiterId;

            //Zertifikat auswählen
            System.out.println("Welches Zertifikat moechten sie dem Mitarbeiter zuweisen?");
            zertifikat = new ZertifikateSuchen().zertifikatSuchen();
            this.zertifikatId = zertifikat.zertifikatsId;

            //
            System.out.println("Geben sie das Ablaufdatum des Zertifikats ein");
            this.zertifikatsAblaufDatum = BefehlsZeilenSchnittstelle.pruefeDatum();


            Utilities.BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte bestätigen sie die richtige Eingabe");
            System.out.println();
            System.out.println("Mitarbeiter: " + mitarbeiter.vorname + " " + mitarbeiter.nachname);
            System.out.println();
            System.out.println("Zertifikat: " + zertifikat.anbieter + " " + zertifikat.zertifikatsBeschreibung + " " + zertifikatsAblaufDatum);

            switch (Utilities.BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1:
                    new MitarbeiterDatenbank().mitarbeiterBescheinigungAnlegen(this, kontextAnlegen.ZERTIFIKAT);
                    abschliessen = true;
                    break;
                case 2:
                    abschliessen = false;
                    break;
                case 3:
                    abschliessen = true;
                    break;
            }

        } while (!abschliessen) ;
    }
}