package Datenbank;

import Zertifikate.Zertifikate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ZertifikatsDatenbank extends Datenbank{

    STORE_PROCEDURE_KONTEXT kontext;


    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank.Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank
    Rueckgabewert: Hashmap mit Objekten fuer jeden Tuple
     */
    HashMap<Zertifikate, Integer> zertifikateListeErstellen(ResultSet dbInhalt) throws SQLException {

        HashMap<Zertifikate, Integer> zertifikateHash = new HashMap<>();
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

            zertifikateHash.put(zertifikate, id);
        }
        return zertifikateHash;
    }

    public HashMap<?,Integer> zertifikatSuchen(String query){

        System.out.println(query);
        return datenListeAusgeben(query);
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
 Aufruf eines Store Procedure um ein Zertifikat zu loeschen
 Parameter: Id des Zertifikats
  */
    public void zertifikatLoeschen(int kursId){

        storeProcedureAufrufen("{ call SP_AENDERN_ZERT_LOESCHEN(?,?) }",kursId, kontext.ZERTIFIKAT_LOESCHEN);
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
       Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
       Parameter: Objekt des Aufrufers
       */
    public boolean datenAnlegen(Zertifikate zertifikat){
         datenInDbAnlegen(anlegenQuerry(zertifikat));
        return false;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Zertifikate zertifikat){
        datenBearbeiten(updateQuerry(zertifikat));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys fuer einen Update von Kostenstelle
     */
    String updateQuerry(Zertifikate zertifikat){

        return  "UPDATE `itwisse_kursverwaltung`.`tblZertifikate` SET " +
                " `Titel` = '"          + zertifikat.zertifikatsTitel +
                "', `Beschreibung` = '" + zertifikat.zertifikatsBeschreibung +
                "', `Anbieter` = '"     + zertifikat.anbieter +
                "', `Sprache` = '"      + zertifikat.sprache +
                "', `Kosten` = "        + zertifikat.kosten +
                ", `Waehrung` = '"      + zertifikat.waehrung +
                "' WHERE `ID` = "       + zertifikat.zertifikatsId + ";";
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys fuer ein Anlegen eines neuen Kurses
     */
    String anlegenQuerry(Zertifikate zerttifikat){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblZertifikate`" +
                " (`Titel`, `Beschreibung`, `Anbieter`, `Sprache`, `Kosten`, `Waehrung`)" +
                " VALUES ('"    + zerttifikat.zertifikatsTitel +
                "', '"          + zerttifikat.zertifikatsBeschreibung +
                "', '"          + zerttifikat.anbieter +
                "', '"          + zerttifikat.sprache +
                "', '"          + zerttifikat.kosten +
                "', '"          + zerttifikat.waehrung + "')";
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
     Methode die den query aus den Angaben des Bedieners zusammensetzt
    Rueckgabewert: query als String
      */
    public String queryFuerAnzahlAbfrage(String suchkriterium, String suchText){

        String query = "`tblZertifikate` where `";
        String suche =  suchkriterium + "` Like \"%" + suchText + "%\"";
        return query + suche;
    }
}
