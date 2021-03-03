package DatenSchicht;

import Logik.Kurse.*;
import Logik.Services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KursDatenbank extends Datenbank implements DatenLogik {

    STORE_PROCEDURE_KONTEXT kontext;
    

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenAnlegen(Services services){
        datenInDbAnlegen(anlegenQuerry((Kurse) services));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Services services){
        datenBearbeiten(updateQuerry((Kurse) services));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Spezfischen Kurs in der Datenbank suchen
    public List<?> suchen(String suchkriterium, String suchText){
        return datenInDbSuchen(queryFuerSuchabfrage(suchkriterium,suchText));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Aufruf eines Store Procedure um einen Kurs zu loeschen
    Parameter: Id des Kurses
    */
    public void datenLoeschen(int kursId){
        storeProcedureAufrufen("{ call SP_AENDERN_KURS_LOESCHEN(?,?) }",kursId, kontext.KURS_LOESCHEN);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    protected List<Kurse> kursListeErstellen(ResultSet dbInhalt) throws SQLException {

       List<Kurse> kursList = new ArrayList<>();
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

            kursList.add(kurs);
        }
        return kursList;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys fuer ein Anlegen eines neuen Datensatzes in der Datenbank
     */
    private String anlegenQuerry(Kurse kurs){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblKurse`" +
                " (`KursCode`, `Anbieter`, `Kursbeschreibung`, `Kosten`, `Waehrung`, `DatumVon`, `DatumBis`, `Durchfuehrungsort`)" +
                " VALUES ('"    + kurs.getKursCode() +
                "', '"          + kurs.getAnbieter() +
                "', '"          + kurs.getKursBeschreibung() +
                "', '"          + kurs.getKosten() +
                "', '"          + kurs.getWaehrung() +
                "', '"          + kurs.getDatumVon() +
                "', '"          + kurs.getDatumBis() +
                "', '"          + kurs.getDurchfuehrungsOrt() +"')";
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys fuer einen Update von Kostenstelle
     */
    private String updateQuerry(Kurse kurs){

        return  "UPDATE `itwisse_kursverwaltung`.`tblKurse` SET " +
                " `KursCode` = '"            + kurs.getKursCode() +
                "', `Anbieter` = '"          + kurs.getAnbieter() +
                "', `Kursbeschreibung` = '" + kurs.getKursBeschreibung() +
                "', `Kosten` = "            + kurs.getKosten() +
                ", `Waehrung` = '"          + kurs.getWaehrung() +
                "', `DatumVon` = '"          + kurs.getDatumVon() +
                "', `DatumBis` = '"           + kurs.getDatumBis() +
                "', `Durchfuehrungsort` = '" + kurs.getDurchfuehrungsOrt() +
                "' WHERE `ID` = "           + kurs.getKurseId() + ";";
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
      Methode die den query aus für die Suche nach Kursen
     Rueckgabewert: query als String
       */
    private String queryFuerSuchabfrage(String suchkriterium, String suchText){

        String query = "`tblKurse` where `";
        String suche;

        if(suchkriterium.equals("KursCode")){
            suche = suchkriterium + "` Like \"" + suchText + "\"";
        }else {
            suche = suchkriterium + "` Like \"%" + suchText + "%\"";
        }
        return query + suche;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Wird beim Ausbau der Software implementiert
    @Override
    public List<?> datenAuslesen(String tabelle) {
        return null;
    }

}
