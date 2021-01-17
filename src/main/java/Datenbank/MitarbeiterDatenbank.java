package Datenbank;

import Mitarbeiter.Mitarbeiter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MitarbeiterDatenbank extends Datenbank{

    HashMap<Mitarbeiter,Integer> mitarbeiterHashMap = new HashMap<>();


    public void mitarbeiterAnlegen(Mitarbeiter mitarbeiter){

    datenInDbAnlegen(anlegenQuerry(mitarbeiter));


    }
    public HashMap<Mitarbeiter,Integer> mitarbeiterSuchen(String query){

        System.out.println(query);
    return mitarbeiterListeAusgeben(query);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Mitarbeiter mitarbeiter){
        datenBearbeiten(updateQuerry(mitarbeiter));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum erstellen des Sql Querry
     */

    String anlegenQuerry(Mitarbeiter mitarbeiter){

        return "INSERT INTO `itwisse_kursverwaltung`.`Mitarbeiter`" +
                " (`PersonalNr`, `Anrede`, `Vorname`, `Nachname`, `Jobtitel`, `Geburtsdatum`, `Statusmitarbeiter`, `KostenstelleID`)" +
                " VALUES ('" + mitarbeiter.personalNummer +
                "', '" + mitarbeiter.anrede +
                "', '" + mitarbeiter.vorname +
                "', '" + mitarbeiter.nachname +
                "', '" + mitarbeiter.jobTitel +
                "', '" + mitarbeiter.geburtstag +
                "', '" + mitarbeiter.mitarbeiterStatus +
                "', '" + mitarbeiter.kostenstelleId +"')";

    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen eines Hashmap von Objekten der Klasse Auswertungen.MitarbeiterAuswertung
     */

    public HashMap mitarbeiterListeErstellen(ResultSet dbInhalt) throws SQLException {

        HashMap<Mitarbeiter,Integer> mitarbeiterHash = new HashMap<>();
        Mitarbeiter mitarbeiter;


        while (dbInhalt.next()) {

            int id = dbInhalt.getInt("ID");
            int personalNummer= dbInhalt.getInt("PersonalNr");
            String anrede = dbInhalt.getString("Anrede");
            String vorname = dbInhalt.getString("Vorname");
            String nachname = dbInhalt.getString("Nachname");
            String jobTitel = dbInhalt.getString("Jobtitel");
            String geburtstag = dbInhalt.getString("Geburtsdatum");
            String statusAnstellung = dbInhalt.getString("Statusmitarbeiter");
            int kostenstelleId = dbInhalt.getInt("KostenstelleId");

            mitarbeiter = new Mitarbeiter(id ,personalNummer, kostenstelleId, anrede, vorname, nachname ,jobTitel, geburtstag, statusAnstellung);

            mitarbeiterHash.put(mitarbeiter,id);
        }
        return mitarbeiterHash;
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
      Methode die den query aus den Angaben des Bedieners zusammensetzt
     Rückgabewert: query als String
       */
    public String queryFuerAnzahlAbfrage(String suchkriterium, String suchText){

        String query = " where `";
        String suche =  suchkriterium + "` Like \"" + suchText + "%\"";
        System.out.println(query + suche);
        return query + suche;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für einen Update von Kostenstelle
     */
    private String updateQuerry(Mitarbeiter mitarbeiter){

        return "Update `itwisse_kursverwaltung`.`Mitarbeiter` SET" +
                "`PersonalNr` = '"            + mitarbeiter.personalNummer +
                "', `Anrede` = '"           + mitarbeiter.anrede +
                "', `Vorname` = '"           + mitarbeiter.vorname +
                "', `Nachname` = '"          + mitarbeiter.nachname +
                "', `Jobtitel` = '"          + mitarbeiter.jobTitel +
                "', `Geburtsdatum` = '"      + mitarbeiter.geburtstag +
                "', `Statusmitarbeiter` = '" + mitarbeiter.mitarbeiterStatus +
                "', `KostenstelleID` = '"    + mitarbeiter.kostenstelleId +
                "' WHERE `ID` = "              + mitarbeiter.mitarbeiterId +";";
    }

}
