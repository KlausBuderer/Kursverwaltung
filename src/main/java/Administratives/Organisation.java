package Administratives;

import Datenbank.OrganisationsDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

import java.util.HashMap;
import java.util.Map;

public class Organisation {

    public String abteilungsBezeichnung;
    public int kostenstelleId;
    public int organisationsId;

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Organisation
    public Organisation( int organisationsID, int kostenstelleId, String abteilungsBezeichnung) {
        this.kostenstelleId = kostenstelleId;
        this.organisationsId = organisationsID;
        this.abteilungsBezeichnung = abteilungsBezeichnung;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Konstruktor erstellen eines Objekts mit Angaben der Organisation
    public Organisation() {

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode organisationAnlegen lässt den Benutzer eine neue Organisation Anlegen
     */
    void organisationAnlegen(){

        boolean abschliessen = true;
        do{

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Bitte geben sie folgende Daten ein");
            //Abteilungsbezeichnung
            abteilungsBezeichnung = BefehlsZeilenSchnittstelle.abfrageMitEingabeString("Abteilungsbezeichnung: ");
            //Gibt Kostenstelle als Liste aus
            Kostenstelle kostenstelle = new Kostenstelle();
            kostenstelle.auswahlListeKostenstelleAusgeben();

            BefehlsZeilenSchnittstelle.bildReinigen();
            System.out.println("Stellenbezeichnung: " + abteilungsBezeichnung + "\tBezeichnung der Kostenstelle: " +kostenstelleId);
            System.out.println();
            System.out.println("Bitte überprüfen sie die Korrektheit der Erfassten Daten");



            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()){

                case 1:
                    OrganisationsDatenbank organisationsDatenbank = new OrganisationsDatenbank();
                    organisationsDatenbank.datenAnlegen(this);
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
    Methode zur Ausgabe einer Auswahlliste Kostenstelle für den Benutzer

     */

    public void auswahlListeOrganisationAusgeben() {

        int i = 1;
        int arrayLaenge;
        int auswahl;

        // Abfrage Datenbank.Datenbank nach Kostenstellen
        OrganisationsDatenbank organisationsDatenbank = new OrganisationsDatenbank();
        HashMap<Organisation, Integer> organisationMap = (HashMap<Organisation, Integer>) organisationsDatenbank.datenAuslesenfuerAbfrage("Organisation");


        // Schreiben der Kostenstellen in ein Array
        Organisation[] organisationArray = new Organisation[organisationMap.size() + 1];

        for (Map.Entry<Organisation, Integer> map : organisationMap.entrySet()) {
            organisationArray[i] = map.getKey();
            // Ausgeben des Array
            System.out.println(i + ". " + map.getKey().toString());
            i++;
        }

        arrayLaenge = organisationArray.length;

        System.out.print("Bitte wählen sie eine Organisation aus der Liste (1-" + (arrayLaenge-1) + ")");
        auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(arrayLaenge);

        kostenstelleId = organisationArray[auswahl].kostenstelleId;
        organisationsId = organisationArray[auswahl].organisationsId;
        abteilungsBezeichnung = organisationArray[auswahl].abteilungsBezeichnung;

    }

    @Override
    public String toString() {
        return  "Bezeichnung der Abteilung: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(this.abteilungsBezeichnung), 25) +
                "Kostenstelle: " + BefehlsZeilenSchnittstelle.textFormatieren(String.valueOf(kostenstelleId), 25);

    }
}
