package Datenbank;

import Mitarbeiter.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static Mitarbeiter.MitarbeiterBescheinigung.kontextAnlegen.KURS;
import static Mitarbeiter.MitarbeiterBescheinigung.kontextAnlegen.ZERTIFIKAT;

public class MitarbeiterDatenbank extends Datenbank{

    HashMap<Mitarbeiter,Integer> mitarbeiterHashMap = new HashMap<>();


    public void mitarbeiterAnlegen(Mitarbeiter mitarbeiter){

    datenInDbAnlegen(anlegenQuerry(mitarbeiter));


    }
    public HashMap<?, Integer> mitarbeiterSuchen(String query){

        System.out.println(query);
    return datenListeAusgeben(query);
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void mitarbeiterBescheinigungAnlegen(MitarbeiterBescheinigung mitarbeiterBescheinigung,  MitarbeiterBescheinigung.kontextAnlegen kontext){

        if(kontext == KURS) {
            datenInDbAnlegen(kursZuweisungQuerry(mitarbeiterBescheinigung));
        }else if (kontext == ZERTIFIKAT) {
            datenInDbAnlegen(zertifikatZuweisungQuerry(mitarbeiterBescheinigung));
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
 Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
 Parameter: Objekt des Aufrufers
  */
    public HashMap zertifikatVerlaengernListe(int mitarbeiterId){

       return storeProcedureAufrufen("{ call SP_SHOW_MA_ZERT(?) }",mitarbeiterId);
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
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Mitarbeiter mitarbeiter){
        datenBearbeiten(updateQuerry(mitarbeiter));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum erstellen des Sql Querry
     */

    String anlegenQuerry(Mitarbeiter mitarbeiter){

        return "INSERT INTO `itwisse_kursverwaltung`.`Mitarbeiter`" +
                " (`PersonalNr`, `Anrede`, `Vorname`, `Nachname`, `Jobtitel`, `Geburtsdatum`, `Statusmitarbeiter`, `KostenstelleID`)" +
                " VALUES ('" + mitarbeiter.personalNummer +
                "', '" + mitarbeiter.anrede +
                "', '" + mitarbeiter.vorname +
                "', '" + mitarbeiter.nachname +
                "', '" + mitarbeiter.jobTitel +
                "', '" + mitarbeiter.geburtstag +
                "', '" + mitarbeiter.mitarbeiterStatus +
                "', '" + mitarbeiter.kostenstelleId +"')";

    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen eines Hashmap von Objekten der Klasse Auswertungen.MitarbeiterAuswertung
     */

    public HashMap mitarbeiterListeErstellen(ResultSet dbInhalt) throws SQLException {

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

    /*--------------------------------------------------------------------------------------------------------------------------------------
      Methode die den query aus den Angaben des Bedieners zusammensetzt
     Rückgabewert: query als String
       */
    public String queryFuerAnzahlAbfrage(String suchkriterium, String suchText){

        String query = "`Mitarbeiter` where `";
        String suche =  suchkriterium + "` Like \"" + suchText + "%\"";
        System.out.println(query + suche);
        return query + suche;
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen eines Hashmap von Objekten der Klasse Auswertungen.MitarbeiterAuswertung
     */

    public HashMap zertifikatVerlaengern(ResultSet dbInhalt) throws SQLException {

        HashMap<MitarbeiterBescheinigung,Integer> mitarbeiterBescheinigungHashMap = new HashMap<>();
        MitarbeiterBescheinigung mitarbeiterBescheinigung;


        while (dbInhalt.next()) {

            int mitarbeiterID = dbInhalt.getInt("MitarbeiterID");
            String vorname = dbInhalt.getString("Vorname");
            String nachname = dbInhalt.getString("Nachname");
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
    Methode zur Erstellung eines Querrys für einen Update
     */
    private String updateQuerry(Mitarbeiter mitarbeiter){

        return "Update `itwisse_kursverwaltung`.`Mitarbeiter` SET" +
                "`PersonalNr` = '"            + mitarbeiter.personalNummer +
                "', `Anrede` = '"           + mitarbeiter.anrede +
                "', `Vorname` = '"           + mitarbeiter.vorname +
                "', `Nachname` = '"          + mitarbeiter.nachname +
                "', `Jobtitel` = '"          + mitarbeiter.jobTitel +
                "', `Geburtsdatum` = '"      + mitarbeiter.geburtstag +
                "', `Statusmitarbeiter` = '" + mitarbeiter.mitarbeiterStatus +
                "', `KostenstelleID` = '"    + mitarbeiter.kostenstelleId +
                "' WHERE `ID` = "              + mitarbeiter.mitarbeiterId +";";
    }

    String kursZuweisungQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung){

        return "INSERT INTO `itwisse_kursverwaltung`.`MitarbeiterBescheinigung`" +
                " (`ZertifikatAblaufDatum`, `MitarbeiterID`, `KurseID`)" +
                " VALUES ('" + mitarbeiterBescheinigung.zertifikatsAblaufDatum +
                "', '" + mitarbeiterBescheinigung.mitarbeiterId +
                "', '" + mitarbeiterBescheinigung.kurseId +
                "')";

    }

    String zertifikatZuweisungQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung){

        return "INSERT INTO `itwisse_kursverwaltung`.`MitarbeiterBescheinigung`" +
                " (`ZertifikatAblaufDatum`, `MitarbeiterID`, `ZertifikatID`)" +
                " VALUES ('" + mitarbeiterBescheinigung.zertifikatsAblaufDatum +
                "', '" + mitarbeiterBescheinigung.mitarbeiterId +
                "', '" + mitarbeiterBescheinigung.zertifikatId +
                "')";

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querrys für einen Update
     */
    private String updateQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung){

        return "Update `itwisse_kursverwaltung`.`MitarbeiterBescheinigung` SET" +
                " `ZertifikatAblaufDatum` = '"           + mitarbeiterBescheinigung.zertifikatsAblaufDatum +
                "' Where `ID` = " + mitarbeiterBescheinigung.id + ";";
    }
}