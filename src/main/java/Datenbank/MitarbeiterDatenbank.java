package Datenbank;

import Mitarbeiter.Mitarbeiter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MitarbeiterDatenbank extends Datenbank{

    HashMap<Mitarbeiter,Integer> mitarbeiterHashMap = new HashMap<>();

public void mitarbeiterAnlegen(String query){
    datenInDbAnlegen(query);


}
public HashMap<Mitarbeiter,Integer> mitarbeiterSuchen(String query){
    mitarbeiterListeAusgeben(query);
    return mitarbeiterHashMap;
}

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen eines Hashmap von Objekten der Klasse Auswertungen.MitarbeiterAuswertung
     */

    public void mitarbeiterSuchen(ResultSet dbInhalt) throws SQLException {

        HashMap<Mitarbeiter,Integer> mitarbeiterHash = new HashMap<>();
        Mitarbeiter mitarbeiter;


        while (dbInhalt.next()) {

            String anrede = dbInhalt.getString("Anrede");
            int id = dbInhalt.getInt("ID");
            String jobTitel = dbInhalt.getString("Jobtitel");
            int organisationsId = dbInhalt.getInt("OrganisationsId");
            String vorname = dbInhalt.getString("Vorname");
            String nachname = dbInhalt.getString("Nachname");
            int personalNummer= dbInhalt.getInt("PersonalNr");
            String geburtstag = dbInhalt.getString("Geburtsdatum");
            String statusAnstellung = dbInhalt.getString("Statusmitarbeiter");
            mitarbeiter = new Mitarbeiter(id ,personalNummer, organisationsId, anrede, vorname, nachname ,jobTitel, geburtstag, statusAnstellung);

            mitarbeiterHash.put(mitarbeiter,id);
        }
        mitarbeiterHashMap = mitarbeiterHash;
    }


}
