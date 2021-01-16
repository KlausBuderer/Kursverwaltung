package Datenbank;

import Kurse.Kurse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class KursDatenbank extends Datenbank {
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank
    Rückgabewert: Hashmap mit Objekten für jeden Tuple
     */
    public HashMap<Kurse, Integer> kurseAusgeben(ResultSet dbInhalt) throws SQLException {

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
    Methode zur Erstellung eines Querys für ein Anlegen eines neuen Datensatzes in der Datenbank
     */
    private String anlegenQuerry(Kurse kurs){

        return "INSERT INTO `itwisse_kursverwaltung`.`Kurse`" +
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
    Methode zur Erstellung eines Querrys für einen Update von Kostenstelle
     */
    private String updateQuerry(Kurse kurs){

        return  "UPDATE `itwisse_kursverwaltung`.`Kurse` SET " +
                " `KursCode` = "            + kurs.kursCode +
                ", `Anbieter` = '"          + kurs.anbieter +
                "', `Kursbeschreibung` = '" + kurs.kursBeschreibung +
                "', `Kosten` = "            + kurs.kosten +
                ", `Waehrung` = '"          + kurs.waehrung +
                "', `DatumVon` = "          + kurs.datumVon +
                ", `DatumBis` = "           + kurs.datumBis +
                ", `Durchfuehrungsort` = '" + kurs.durchfuehrungsOrt +
                "' WHERE `ID` = "           + kurs.kurseId + ";";
    }
}
