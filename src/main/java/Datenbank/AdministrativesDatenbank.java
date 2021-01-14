package Datenbank;

import Administratives.Kostenstelle;
import Administratives.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AdministrativesDatenbank extends Datenbank {


    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank.Datenbank

    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank

    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */

    public HashMap<Budget, Integer> budgetAusgeben(ResultSet dbInhalt) throws SQLException {

        HashMap<Budget, Integer> budgetHash = new HashMap<>();
        Budget budget;

        while (dbInhalt.next()) {

            Integer id = dbInhalt.getInt("ID");
            Integer budgetJahr = dbInhalt.getInt("Jahr");
            Integer kostenstelleID = dbInhalt.getInt("KostenstelleID");
            Integer betrag = dbInhalt.getInt("Betrag");
            String waehrung = dbInhalt.getString("Waehrung");
            budget = new Budget(id, kostenstelleID, budgetJahr, betrag, waehrung);

            budgetHash.put(budget, id);


        }
        return budgetHash;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank.Datenbank

    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank

    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */
    HashMap<Kostenstelle, Integer> kostenstelleAusgeben(ResultSet dbInhalt) throws SQLException {

        HashMap<Kostenstelle, Integer> kostenstelleHash = new HashMap<>();
        Kostenstelle kostenstelle;

        while (dbInhalt.next()) {
            Integer id = dbInhalt.getInt("ID");
            Integer kostenstelleNummer = dbInhalt.getInt("Kostenstelle");
            String kostenstellenBezeichnung = dbInhalt.getString("BezeichnungKST");
            String verantwortlichePerson = dbInhalt.getNString("KostenstelleVerantPerson");
            kostenstelle = new Kostenstelle(id, kostenstelleNummer, kostenstellenBezeichnung, verantwortlichePerson);

            kostenstelleHash.put(kostenstelle, id);
        }
        return kostenstelleHash;
    }
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
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank.Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank
    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */

    public HashMap<?,Integer> dbHashMap(String tabelle){
        return datenAuslesenfuerAbfrage(tabelle);
    }

    public boolean datenAnlegen(String query){
        datenInDbAnlegen(query);
        return false;
    }

    public void datenMutation(String query){
        datenBearbeiten(query);
    }
}
