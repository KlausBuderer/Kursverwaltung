package DatenSchicht;

import Logik.Administratives.Kostenstelle;
import Logik.Services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KostenstelleDatenbank extends Datenbank implements DatenLogik, DatenLogikKostenstelle{


    /*
      Aufruf Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
      Parameter: Objekt des Aufrufers
    */
    public void datenAnlegen(Services services){
        datenInDbAnlegen(anlegenQuery((Kostenstelle) services));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Services services){
        datenBearbeiten(updatequery((Kostenstelle) services));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
           /*
    Gibt eine Auswahlliste zurueck (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
     */
    public List<?> datenAuslesen(String tabelle){
      return datenAuslesenfuerAbfrage(tabelle);
    }

    /*
    Gibt die Bezeichnung der Kostenstelle zurück (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
    */
    public String kostenstellenBezeichnungAusgeben(int kostenstellenId){
        return  storeProcedureAufrufen("{ call SP_ANZEIGEN_KOSTENSTELLE_MIT_ID(?,?) }",
                kostenstellenId);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    List<Kostenstelle> kostenstelleAusgeben(ResultSet dbInhalt) throws SQLException {

        List<Kostenstelle> kostenstelleList = new ArrayList<>();
        Kostenstelle kostenstelle;

        while (dbInhalt.next()) {
            Integer id = dbInhalt.getInt("ID");
            Integer kostenstelleNummer = dbInhalt.getInt("KostenstelleNr");
            String kostenstellenBezeichnung = dbInhalt.getString("BezeichnungKST");
            String verantwortlichePerson = dbInhalt.getNString("KostenstelleVerantPerson");
            kostenstelle = new Kostenstelle(id, kostenstelleNummer, kostenstellenBezeichnung, verantwortlichePerson);

            kostenstelleList.add(kostenstelle);
        }
        return kostenstelleList;
    }

    /*
    Methode zur Erstellung eines Querys fuer ein Update einer Kostenstelle
    Parameter: Objekt des Aufrufers
    Rueckgabe: query als String
     */
    private String updatequery(Kostenstelle kostenstelle){

        return "UPDATE `itwisse_kursverwaltung`.`tblKostenstelle` SET " +
                " `KostenstelleNr` = "                     + kostenstelle.getKostenstelleNr() +
                ", `BezeichnungKST` = \""                + kostenstelle.getBezeichnungKst()+
                "\", `KostenstelleVerantPerson` = \""    + kostenstelle.getKostenstelleVerantPerson() +
                "\" WHERE `ID` = "                       + kostenstelle.getKostenstelleId() + ";";

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys fuer ein Anlegen einer Kostenstelle
     Parameter: Objekt des Aufrufers
     Rueckgabe: query als String
     */
    private String anlegenQuery(Kostenstelle kostenstelle){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblKostenstelle` (`KostenstelleNr`, `BezeichnungKST`, `KostenstelleVerantPerson`) VALUES " +
                "('"    + kostenstelle.getKostenstelleNr() +
                "', '"  + kostenstelle.getBezeichnungKst() +
                "', '"  + kostenstelle.getKostenstelleVerantPerson() + "')";

    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Wird beim Ausbau der Software implementiert
    @Override
    public List<?> suchen(String suchkriterium, String suchText) {
        return null;
    }

    // Wird beim Ausbau der Software implementiert
    @Override
    public void datenLoeschen(int ID) {
    }
}
