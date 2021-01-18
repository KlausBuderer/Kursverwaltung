package Mitarbeiter;

import Datenbank.MitarbeiterDatenbank;
import Kurse.KursSuchen;
import Kurse.Kurse;

public class MitarbeiterBescheinigung {

    public int id;
    public String zertifikatsAblaufDatum = "0000-00-00";
    public int mitarbeiterId;
    public int zertifikatId;
    public int kurseId;

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
                    new MitarbeiterDatenbank().mitarbeiterBescheinigungAnlegen(this);
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