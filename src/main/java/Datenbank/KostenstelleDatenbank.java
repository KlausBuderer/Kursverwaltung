package Datenbank;

import Administratives.Kostenstelle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class KostenstelleDatenbank extends Datenbank {


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
            Integer kostenstelleNummer = dbInhalt.getInt("KostenstelleNr");
            String kostenstellenBezeichnung = dbInhalt.getString("BezeichnungKST");
            String verantwortlichePerson = dbInhalt.getNString("KostenstelleVerantPerson");
            kostenstelle = new Kostenstelle(id, kostenstelleNummer, kostenstellenBezeichnung, verantwortlichePerson);

            kostenstelleHash.put(kostenstelle, id);
        }
        return kostenstelleHash;
    }
    /*
    Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
    */
    public boolean datenAnlegen(Kostenstelle kostenstelle){
        datenInDbAnlegen(anlegenQuery(kostenstelle));
        return false;
    }
    /*
   Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
   Parameter: Objekt des Aufrufers
   */
    public String kostenstelleBezichnungAusgeben(int kostenstellenId){
        return  storeProcedureAufrufen("{ call SP_ANZEIGEN_KOSTENSTELLE_MIT_ID(?,?) }",
                kostenstellenId);
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Kostenstelle kostenstelle){
        datenBearbeiten(updatequery(kostenstelle));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys für ein Update einer Kostenstelle
    Parameter: Objekt des Aufrufers
    Rückgabe: query als String
     */
    String updatequery(Kostenstelle kostenstelle){

        return "UPDATE `itwisse_kursverwaltung`.`tblKostenstelle` SET " +
                " `Kostenstelle` = "                     + kostenstelle.kostenstelleNr +
                ", `BezeichnungKST` = \""                + kostenstelle.bezeichnungKst +
                "\", `KostenstelleVerantPerson` = \""    + kostenstelle.kostenstelleVerantPerson +
                "\" WHERE `ID` = "                       + kostenstelle.kostenstelleId + ";";

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys für ein Anlegen einer Kostenstelle
     Parameter: Objekt des Aufrufers
     Rückgabe: query als String
     */
    String anlegenQuery(Kostenstelle kostenstelle){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblKostenstelle` (`KostenstelleNr`, `BezeichnungKST`, `KostenstelleVerantPerson`) VALUES " +
                "('"    + kostenstelle.kostenstelleNr +
                "', '"  + kostenstelle.bezeichnungKst +
                "', '"  + kostenstelle.kostenstelleVerantPerson + "')";

    }


}
