package DatenSchicht;

import Logik.Administratives.Kostenstelle;
import Logik.Services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class KostenstelleDatenbank extends Datenbank implements DatenLogik{

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank.Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank
    Rueckgabewert: Hashmap mit Objekten fuer jeden Tuple
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
    public void datenAnlegen(Services services){
        datenInDbAnlegen(anlegenQuery((Kostenstelle) services));
    }
    /*
   Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
   Parameter: Objekt des Aufrufers
   */
    public String kostenstelleBezeichnungAusgeben(int kostenstellenId){
        return  storeProcedureAufrufen("{ call SP_ANZEIGEN_KOSTENSTELLE_MIT_ID(?,?) }",
                kostenstellenId);
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Services services){
        datenBearbeiten(updatequery((Kostenstelle) services));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
           /*
    Gibt eine Auswahlliste zurueck
     */
    public HashMap<?,Integer> datenAuslesen(String tabelle){
      return datenAuslesenfuerAbfrage(tabelle);
    }


    // Wird beim Ausbau der Software implementiert
    @Override
    public void datenLoeschen(int ID) {
    }

    /*
    Methode zur Erstellung eines Querys fuer ein Update einer Kostenstelle
    Parameter: Objekt des Aufrufers
    Rueckgabe: query als String
     */
    String updatequery(Kostenstelle kostenstelle){

        return "UPDATE `itwisse_kursverwaltung`.`tblKostenstelle` SET " +
                " `KostenstelleNr` = "                     + kostenstelle.kostenstelleNr +
                ", `BezeichnungKST` = \""                + kostenstelle.bezeichnungKst +
                "\", `KostenstelleVerantPerson` = \""    + kostenstelle.kostenstelleVerantPerson +
                "\" WHERE `ID` = "                       + kostenstelle.kostenstelleId + ";";

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys fuer ein Anlegen einer Kostenstelle
     Parameter: Objekt des Aufrufers
     Rueckgabe: query als String
     */
    String anlegenQuery(Kostenstelle kostenstelle){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblKostenstelle` (`KostenstelleNr`, `BezeichnungKST`, `KostenstelleVerantPerson`) VALUES " +
                "('"    + kostenstelle.kostenstelleNr +
                "', '"  + kostenstelle.bezeichnungKst +
                "', '"  + kostenstelle.kostenstelleVerantPerson + "')";

    }


    @Override
    public HashMap<?, Integer> suchen(String suchkriterium, String suchText) {
        return null;
    }

}
