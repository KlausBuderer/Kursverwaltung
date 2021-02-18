package DatenSchicht;

import Logik.Mitarbeiter.Mitarbeiter;
import Logik.Mitarbeiter.MitarbeiterBescheinigung;
import Logik.Services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static Logik.Mitarbeiter.MitarbeiterBescheinigung.kontextAnlegen.KURS;
import static Logik.Mitarbeiter.MitarbeiterBescheinigung.kontextAnlegen.ZERTIFIKAT;


public class MitarbeiterDatenbank extends Datenbank implements DatenLogikMitarbeiter {

    STORE_PROCEDURE_KONTEXT kontext;
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenAnlegen(Services services) {
        datenInDbAnlegen(anlegenQuerry((Mitarbeiter) services));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Services services){
        datenBearbeiten(updateQuerry((Mitarbeiter) services));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf Mitarbeiter suchen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
   Parameter: MySql Query
     */
    public HashMap<?, Integer> suchen(String suchkriterium, String suchText){
        return datenInDbSuchen(queryFuerAnzahlAbfrage(suchkriterium,suchText));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
 Aufruf eines Store Procedure der Datenbank umd eine Liste von Zertifikaten die einem Mitarbeiter zugewiesen sind auszugeben
 Parameter: Id des Mitarbeiters
  */
    public void datenLoeschen(int mitarbeiterId){

        storeProcedureAufrufen("{ call SP_AENDERN_MA_LOESCHEN(?,?) }",mitarbeiterId, kontext.MITARBEITER_LOESCHEN);
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf  Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void mitarbeiterBescheinigungAnlegen(MitarbeiterBescheinigung mitarbeiterBescheinigung, MitarbeiterBescheinigung.kontextAnlegen kontext){

        if(kontext == KURS) {
            datenInDbAnlegen(kursZuweisungQuerry(mitarbeiterBescheinigung));
        }else if (kontext == ZERTIFIKAT) {
            datenInDbAnlegen(zertifikatZuweisungQuerry(mitarbeiterBescheinigung));
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
 Aufruf eines Store Procedure der Datenbank umd eine Liste von Zertifikaten die einem Mitarbeiter zugewiesen sind auszugeben
 Parameter: Id des Mitarbeiters
  */
    public HashMap zertifikatVerlaengernListe(int mitarbeiterId){

       return storeProcedureAufrufen("{ call SP_ANZEIGEN_MA_ZERT(?) }",mitarbeiterId,kontext.ZERTIFIKATE_PRO_MITARBEITER);
    }

    /*
       //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
   Aufruf eines Store Procedure der Datenbank umd eine Liste von Zertifikaten die einem Mitarbeiter zugewiesen sind auszugeben
   Parameter: Id des Mitarbeiters
   */
    public String nummerAufExistenzPruefen(int mitarbeiterId){

      return  storeProcedureAufrufen("{ call SP_PRUEFEN_MA_NR(?,?) }",mitarbeiterId);
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
   Aufruf einer neuer KursWeisung an einen Mitarbeiter
   Parameter: Objekt des Aufrufers
   */
    public String kursZuweisungQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblMitarbeiterBescheinigung`" +
                " (`ZertifikatAblaufDatum`, `MitarbeiterID`, `KurseID`)" +
                " VALUES ('" + mitarbeiterBescheinigung.getZertifikatsAblaufDatum() +
                "', '" + mitarbeiterBescheinigung.getMitarbeiterId() +
                "', '" + mitarbeiterBescheinigung.getKurseId() +
                "')";

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
   Aufruf einer neuer Zertifikats Zuweisung an einen Mitarbeiter
   Parameter: Objekt des Aufrufers
   */
    public String zertifikatZuweisungQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblMitarbeiterBescheinigung`" +
                " (`ZertifikatAblaufDatum`, `MitarbeiterID`, `ZertifikatID`)" +
                " VALUES ('" + mitarbeiterBescheinigung.getZertifikatsAblaufDatum() +
                "', '" + mitarbeiterBescheinigung.getMitarbeiterId() +
                "', '" + mitarbeiterBescheinigung.getZertifikatId() +
                "')";

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
   Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
   Parameter: Objekt des Aufrufers
   */
    public void zertifikatVerlaengernSpeichern(MitarbeiterBescheinigung mitarbeiterBescheinigung){

        datenBearbeiten(updateQuerry(mitarbeiterBescheinigung));
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen eines Hashmap von Objekten der Klasse Auswertungen.MitarbeiterAuswertung
     */
    protected HashMap mitarbeiterListeErstellen(ResultSet dbInhalt) throws SQLException {

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

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen eines Hashmap von Objekten der Klasse MitarbeiterBescheinigung
     */
    protected HashMap zertifikatVerlaengern(ResultSet dbInhalt) throws SQLException {

        HashMap<MitarbeiterBescheinigung,Integer> mitarbeiterBescheinigungHashMap = new HashMap<>();
        MitarbeiterBescheinigung mitarbeiterBescheinigung;


        while (dbInhalt.next()) {

            int mitarbeiterID = dbInhalt.getInt("MitarbeiterID");
            String nachname = dbInhalt.getString("Nachname");
            String vorname = dbInhalt.getString("Vorname");
            int maBescheinigungId = dbInhalt.getInt("MABescheinigungID");
            String zertAblDatum = dbInhalt.getString("ZertAblDatum");
            String zertifikatsTitel = dbInhalt.getString("Zertifikatstitel");
            String zertifikatsbeschreibung = dbInhalt.getString("Zertifikatsbeschreibung");

            mitarbeiterBescheinigung = new MitarbeiterBescheinigung(maBescheinigungId ,zertAblDatum, mitarbeiterID, vorname, nachname, zertifikatsTitel,zertifikatsbeschreibung);

            mitarbeiterBescheinigungHashMap.put(mitarbeiterBescheinigung,maBescheinigungId);
        }
        return mitarbeiterBescheinigungHashMap;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstellen des Sql Querry
     */
    private String anlegenQuerry(Mitarbeiter mitarbeiter){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblMitarbeiter`" +
                " (`PersonalNr`, `Anrede`, `Vorname`, `Nachname`, `Jobtitel`, `Geburtsdatum`, `Statusmitarbeiter`, `KostenstelleID`)" +
                " VALUES ('" + mitarbeiter.getPersonalNummer() +
                "', '" + mitarbeiter.getAnrede() +
                "', '" + mitarbeiter.getVorname() +
                "', '" + mitarbeiter.getNachname() +
                "', '" + mitarbeiter.getJobTitel() +
                "', '" + mitarbeiter.getGeburtstag() +
                "', '" + mitarbeiter.getMitarbeiterStatus() +
                "', '" + mitarbeiter.getKostenstelleId() +"')";

    }

    /*--------------------------------------------------------------------------------------------------------------------------------------
      Methode die den query aus den Angaben des Bedieners zusammensetzt
     Rueckgabewert: query als String
       */
    private String queryFuerAnzahlAbfrage(String suchkriterium, String suchText){

        String query = "`tblMitarbeiter` where `";
        String suche =  suchkriterium + "` Like \"" + suchText + "%\"";
        System.out.println(query + suche);
        return query + suche;
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys fuer einen Update
     */
    private String updateQuerry(Mitarbeiter mitarbeiter){

        return "Update `itwisse_kursverwaltung`.`tblMitarbeiter` SET" +
                "`PersonalNr` = '"            + mitarbeiter.getPersonalNummer() +
                "', `Anrede` = '"           + mitarbeiter.getAnrede() +
                "', `Vorname` = '"           + mitarbeiter.getVorname() +
                "', `Nachname` = '"          + mitarbeiter.getNachname() +
                "', `Jobtitel` = '"          + mitarbeiter.getJobTitel() +
                "', `Geburtsdatum` = '"      + mitarbeiter.getGeburtstag() +
                "', `Statusmitarbeiter` = '" + mitarbeiter.getMitarbeiterStatus() +
                "', `KostenstelleID` = '"    + mitarbeiter.getKostenstelleId() +
                "' WHERE `ID` = "              + mitarbeiter.getMitarbeiterId() +";";
    }

  /*
    Methode zur Erstellung eines Querrys fuer einen Update
     */
    private String updateQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung){

        return "Update `itwisse_kursverwaltung`.`tblMitarbeiterBescheinigung` SET" +
                " `ZertifikatAblaufDatum` = '"           + mitarbeiterBescheinigung.getZertifikatsAblaufDatum() +
                "' Where `ID` = " + mitarbeiterBescheinigung.getId() + ";";
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Wird beim Ausbau der Software implementiert

    @Override
    public HashMap<?, Integer> datenAuslesen(String tabelle) {
        return null;
    }
}
