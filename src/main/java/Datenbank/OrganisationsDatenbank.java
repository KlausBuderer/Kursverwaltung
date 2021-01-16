package Datenbank;

import Administratives.Organisation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class OrganisationsDatenbank extends Datenbank{

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank.Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank
    Rückgabewert: Hashmap mit Objekten für jeden Tuple
     */
    HashMap<Organisation, Integer> organisationAusgeben(ResultSet dbInhalt) throws SQLException {

        HashMap<Organisation, Integer> organisationHash = new HashMap<>();
        Organisation organisation;

        while (dbInhalt.next()) {

            Integer id = dbInhalt.getInt("ID");
            String abteilungsBezeichnung = dbInhalt.getString("AbteilungsBezeichnung");
            Integer kostenstelleID = dbInhalt.getInt("KostenstelleID");
            organisation = new Organisation(id, kostenstelleID, abteilungsBezeichnung);

            organisationHash.put(organisation, id);
        }
        return organisationHash;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public boolean datenAnlegen(Organisation organisation){
        anlegenQuery(organisation);
        return false;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(String query){
        datenBearbeiten(query);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys für ein Anlegen einer Kostenstelle
     Parameter: Objekt des Aufrufers
     Rückgabe: query als String
     */
    String anlegenQuery(Organisation organisation){

        return "Insert INTO `itwisse_kursverwaltung`.`Organisation` (`AbteilungsBezeichnung`,`KostenstelleID`) VALUES " +
                "('"    + organisation.abteilungsBezeichnung +
                "', '"  + organisation.kostenstelleId + "')";

    }



}
