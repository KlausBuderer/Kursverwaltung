package Datenbank;

import Kurse.Kurse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class KursDatenbank extends Datenbank {


    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank.Datenbank

    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank

    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */

    HashMap<Kurse, Integer> kurseAusgeben(ResultSet dbInhalt) throws SQLException {

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
