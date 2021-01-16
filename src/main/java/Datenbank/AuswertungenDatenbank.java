package Datenbank;

import Auswertungen.MitarbeiterAuswertung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuswertungenDatenbank extends Datenbank {


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rückgabewert: Liste mit Objekten für jeden Tuple
     */
    public List<MitarbeiterAuswertung> mitarbeiterAuswerten(ResultSet dbInhalt) throws SQLException {

        List<MitarbeiterAuswertung> mitarbeiterAuswertungsliste = new ArrayList<>();
        MitarbeiterAuswertung mitarbeiterAuswertung;

        while (dbInhalt.next()) {

            String anrede = dbInhalt.getString("Anrede");
            int id = dbInhalt.getInt("ID");
            String kursCode = dbInhalt.getString("KursCode");
            String vorname = dbInhalt.getString("Vorname");
            String nachname = dbInhalt.getString("Nachname");
            int personalNummer = dbInhalt.getInt("PersonalNr");
            String abteilungBezeichnung = dbInhalt.getString("Abteilungbezeichnung");
            int idMitarbeiterBescheinigung = dbInhalt.getInt("ID Mitarbeiterbescheinigung");
            mitarbeiterAuswertung = new MitarbeiterAuswertung(vorname,nachname ,abteilungBezeichnung , kursCode,id ,personalNummer,anrede,idMitarbeiterBescheinigung);

            mitarbeiterAuswertungsliste.add(mitarbeiterAuswertung);
        }
        return mitarbeiterAuswertungsliste;
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rückgabewert: Liste mit Objekten für jeden Tuple
     */
    public List<?> auswertungAusDbLesen(String query){
        return  auswertungAusgeben(query);
    }


    void auswertungErstellen(ResultSet dbInhalt) throws SQLException {
        MitarbeiterAuswertung mitarbeiterAuswertung = new MitarbeiterAuswertung();
        mitarbeiterAuswertung.ausgabeListe(mitarbeiterAuswertung.mitarbeiterAuswerten(dbInhalt));
    }

}
