package Datenbank;

import Auswertungen.MitarbeiterAuswertung;
import Auswertungen.WeiterbildungAlleMitarbeiterZeitraum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuswertungenDatenbank extends Datenbank {

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceWeiterbildungAlleMitarbeiterZeitraum(String datumVon, String datumBis){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_MA_WEITERBILDUNG_DATUM(?,?)}",datumVon, datumBis,STORE_PROCEDURE_KONTEXT.AUSWERTUNG_MITARBEITER);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rückgabewert: Liste mit Objekten für jeden Tuple
     */
    public List<WeiterbildungAlleMitarbeiterZeitraum> ausfuehrenWeiterbildungAlleMitarbeiterZeitraum(ResultSet dbInhalt) throws SQLException {

        List<WeiterbildungAlleMitarbeiterZeitraum> weiterbildungAlleMitarbeiterliste = new ArrayList<>();
        WeiterbildungAlleMitarbeiterZeitraum weiterbildungAlleMitarbeiter;

        while (dbInhalt.next()) {

            String anrede = dbInhalt.getString("Anrede");
            String nachname = dbInhalt.getString("Nachname");
            String vorname = dbInhalt.getString("Vorname");
            String kennung = dbInhalt.getString("Kennung");
            String kursbeschreibung = dbInhalt.getString("Kursbeschreibung");
            int kosten = dbInhalt.getInt("KOSTEN");
            String waehrung = dbInhalt.getString("Waehrung");
            String zertifikatsablaufdatum = dbInhalt.getString("Zertifikatsablaufdatum");
            weiterbildungAlleMitarbeiter = new WeiterbildungAlleMitarbeiterZeitraum(anrede,nachname,vorname,kennung,kursbeschreibung,kosten, waehrung,zertifikatsablaufdatum);

            weiterbildungAlleMitarbeiterliste.add(weiterbildungAlleMitarbeiter);
        }
        return weiterbildungAlleMitarbeiterliste;
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
