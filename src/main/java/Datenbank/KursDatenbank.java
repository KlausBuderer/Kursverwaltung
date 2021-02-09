package Datenbank;

import Kurse.Kurse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class KursDatenbank extends Datenbank {

    STORE_PROCEDURE_KONTEXT kontext;


    public HashMap<?,Integer> kursSuchen(String query){
        System.out.println(query);
        return datenListeAusgeben(query);
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank
    Rueckgabewert: Hashmap mit Objekten fuer jeden Tuple
     */
    public HashMap<Kurse, Integer> kursListeErstellen(ResultSet dbInhalt) throws SQLException {

        HashMap<Kurse, Integer> kursHash = new HashMap<>();
        Kurse kurs;

        while (dbInhalt.next()) {

            Integer id = dbInhalt.getInt("ID");
            String kursCode = dbInhalt.getString("KursCode");
            String anbieter = dbInhalt.getString("Anbieter");
            String kursBeschreibung = dbInhalt.getString("Kursbeschreibung");
            Integer kosten = dbInhalt.getInt("Kosten");
            String waehrung = dbInhalt.getString("Waehrung");
            String datumVon = dbInhalt.getString("DatumVon");
            String datumBis = dbInhalt.getString("DatumBis");
            String durchfuehrungsOrt = dbInhalt.getString("Durchfuehrungsort");
            kurs = new Kurse(id, kosten, waehrung, kursCode, anbieter, kursBeschreibung, datumVon, datumBis, durchfuehrungsOrt);

            kursHash.put(kurs, id);
        }
        return kursHash;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public boolean datenAnlegen(Kurse kurs){
        datenInDbAnlegen(anlegenQuerry(kurs));
        return false;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Kurse kurs){
        datenBearbeiten(updateQuerry(kurs));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
 Aufruf eines Store Procedure um einen Kurs zu loeschen
 Parameter: Id des Kurses
  */
    public void kursLoeschen(int kursId){

        storeProcedureAufrufen("{ call SP_AENDERN_KURS_LOESCHEN(?,?) }",kursId, kontext.KURS_LOESCHEN);
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys fuer ein Anlegen eines neuen Datensatzes in der Datenbank
     */
    private String anlegenQuerry(Kurse kurs){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblKurse`" +
                " (`KursCode`, `Anbieter`, `Kursbeschreibung`, `Kosten`, `Waehrung`, `DatumVon`, `DatumBis`, `Durchfuehrungsort`)" +
                " VALUES ('"    + kurs.kursCode +
                "', '"          + kurs.anbieter +
                "', '"          + kurs.kursBeschreibung +
                "', '"          + kurs.kosten +
                "', '"          + kurs.waehrung +
                "', '"          + kurs.datumVon +
                "', '"          + kurs.datumBis +
                "', '"          + kurs.durchfuehrungsOrt +"')";
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys fuer einen Update von Kostenstelle
     */
    private String updateQuerry(Kurse kurs){

        return  "UPDATE `itwisse_kursverwaltung`.`tblKurse` SET " +
                " `KursCode` = '"            + kurs.kursCode +
                "', `Anbieter` = '"          + kurs.anbieter +
                "', `Kursbeschreibung` = '" + kurs.kursBeschreibung +
                "', `Kosten` = "            + kurs.kosten +
                ", `Waehrung` = '"          + kurs.waehrung +
                "', `DatumVon` = '"          + kurs.datumVon +
                "', `DatumBis` = '"           + kurs.datumBis +
                "', `Durchfuehrungsort` = '" + kurs.durchfuehrungsOrt +
                "' WHERE `ID` = "           + kurs.kurseId + ";";
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
      Methode die den query aus den Angaben des Bedieners zusammensetzt
     Rueckgabewert: query als String
       */
    public String queryFuerAnzahlAbfrage(String suchkriterium, String suchText){

        String query = "`tblKurse` where `";
        String suche =  suchkriterium + "` Like \"%" + suchText + "%\"";
        return query + suche;
    }
}
