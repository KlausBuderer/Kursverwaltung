package DatenSchicht;

import Logik.Services;
import Logik.Zertifikate.Zertifikate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZertifikatsDatenbank extends Datenbank implements DatenLogik {

    private STORE_PROCEDURE_KONTEXT kontext;

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
       Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
       Parameter: Objekt des Aufrufers
       */
    public void datenAnlegen(Services services){
         datenInDbAnlegen(anlegenQuerry((Zertifikate) services));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Services services){
        datenBearbeiten(updateQuerry((Zertifikate) services));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Spezfisches Zertifikat in der Datenbank suchen
    public List<?> suchen(String suchkriterium, String suchText){
        return datenInDbSuchen(queryFuerAnzahlAbfrage(suchkriterium,suchText));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Aufruf eines Store Procedure um ein Zertifikat zu loeschen
    Parameter: Id des Zertifikats
    */
    public void datenLoeschen(int kursId){

        storeProcedureAufrufen("{ call SP_AENDERN_ZERT_LOESCHEN(?,?) }",kursId, kontext.ZERTIFIKAT_LOESCHEN);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank.Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    protected List<Zertifikate> zertifikateListeErstellen(ResultSet dbInhalt) throws SQLException {

        List<Zertifikate> zertifikateListe = new ArrayList<>();
        Zertifikate zertifikate;

        while (dbInhalt.next()) {

            Integer id = dbInhalt.getInt("ID");
            String zertifikatstitel = dbInhalt.getString("Titel");
            String zertifikatsBeschreibung = dbInhalt.getString("Beschreibung");
            String anbieter = dbInhalt.getString("Anbieter");
            String sprache = dbInhalt.getString("Sprache");
            Integer kosten = dbInhalt.getInt("Kosten");
            String waehrung = dbInhalt.getString("Waehrung");
            zertifikate = new Zertifikate(id, kosten, zertifikatstitel, zertifikatsBeschreibung, anbieter, sprache, waehrung);

            zertifikateListe.add(zertifikate);
        }
        return zertifikateListe;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys fuer einen Update von Kostenstelle
     */
    private String updateQuerry(Zertifikate zertifikat){

        return  "UPDATE `itwisse_kursverwaltung`.`tblZertifikate` SET " +
                " `Titel` = '"          + zertifikat.getZertifikatsTitel() +
                "', `Beschreibung` = '" + zertifikat.getZertifikatsBeschreibung() +
                "', `Anbieter` = '"     + zertifikat.getAnbieter() +
                "', `Sprache` = '"      + zertifikat.getSprache() +
                "', `Kosten` = "        + zertifikat.getKosten() +
                ", `Waehrung` = '"      + zertifikat.getWaehrung() +
                "' WHERE `ID` = "       + zertifikat.getZertifikatsId() + ";";
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys fuer ein Anlegen eines neuen Kurses
     */
    private String anlegenQuerry(Zertifikate zerttifikat){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblZertifikate`" +
                " (`Titel`, `Beschreibung`, `Anbieter`, `Sprache`, `Kosten`, `Waehrung`)" +
                " VALUES ('"    + zerttifikat.getZertifikatsTitel() +
                "', '"          + zerttifikat.getZertifikatsBeschreibung() +
                "', '"          + zerttifikat.getAnbieter() +
                "', '"          + zerttifikat.getSprache() +
                "', '"          + zerttifikat.getKosten() +
                "', '"          + zerttifikat.getWaehrung() + "')";
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
     Methode die den query aus den Angaben des Bedieners zusammensetzt
    Rueckgabewert: query als String
      */
    private String queryFuerAnzahlAbfrage(String suchkriterium, String suchText){

        String query = "`tblZertifikate` where `";
        String suche =  suchkriterium + "` Like \"%" + suchText + "%\"";
        return query + suche;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Wird beim Ausbau der Software implementiert
    @Override
    public List<?> datenAuslesen(String tabelle) {
        return null;
    }

}
