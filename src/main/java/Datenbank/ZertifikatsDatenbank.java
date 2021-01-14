package Datenbank;

import Zertifikate.Zertifikate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ZertifikatsDatenbank extends Datenbank{

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank.Datenbank

    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank

    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */

    HashMap<Zertifikate, Integer> zertifikateAusgeben(ResultSet dbInhalt) throws SQLException {

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

    public HashMap<?,Integer> dbHashMap(String tabelle){
        return datenAuslesenfuerAbfrage(tabelle);
    }

    public boolean datenInDbAnlegen(String querry){
        datenInDbAnlegen(querry);
        return false;
    }

    public void datenMutation(String query){
        datenBearbeiten(query);
    }

}
