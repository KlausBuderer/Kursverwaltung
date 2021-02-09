package Datenbank;

import Auswertungen.*;

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

    public List storeproduceZertifikateAlleMitarbeiter(){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_MA_ZERTITIFKATE()}"," ", " ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_ZERTIFIKATE);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceKurseAlleMitarbeiter(){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_MA_KURSE()}"," ", " ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_ALLE_KURSE);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceZertifikateAlleMitarbeiterGueltigkeit(String datumVon, String datumBis){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_NICHT_GUELTIG_ALLE_MA_ZERT()}",datumVon,datumBis,STORE_PROCEDURE_KONTEXT.ZERTIFIKAT_ALLE_MITARBEITER_GUELTIGKEIT);
    }


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
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
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    public List<ZertifikateAlleMitarbeiter> ausfuehrenZertifikateAlleMitarbeiter(ResultSet dbInhalt) throws SQLException {

        List<ZertifikateAlleMitarbeiter> zertifikateAlleMitarbeiterliste = new ArrayList<>();
        ZertifikateAlleMitarbeiter zertifikateAlleMitarbeiter;

        while (dbInhalt.next()) {

            int personalnummer = dbInhalt.getInt("personalnummer");
            String nachname = dbInhalt.getString("nachname");
            String vorname = dbInhalt.getString("vorname");
            String zertifikatstitel = dbInhalt.getString("zertifikatstitel");
            String zertifikatsbeschreibung = dbInhalt.getString("zertifikatsbeschreibung");
            String zertAblDatum = dbInhalt.getString("zertAblDatum");
            zertifikateAlleMitarbeiter = new ZertifikateAlleMitarbeiter(personalnummer,nachname,vorname,zertifikatstitel,zertifikatsbeschreibung,zertAblDatum);

            zertifikateAlleMitarbeiterliste.add(zertifikateAlleMitarbeiter);
        }
        return zertifikateAlleMitarbeiterliste;
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    public List<KurseAlleMitarbeiter> ausfuehrenKurseAlleMitarbeiter(ResultSet dbInhalt) throws SQLException {

        List<KurseAlleMitarbeiter> kurseAlleMitarbeiterliste = new ArrayList<>();
        KurseAlleMitarbeiter kurseAlleMitarbeiter;

        while (dbInhalt.next()) {

            int personalnummer = dbInhalt.getInt("personalnummer");
            String nachname = dbInhalt.getString("nachname");
            String vorname = dbInhalt.getString("vorname");
            String zertifikatstitel = dbInhalt.getString("kurscode");
            String kursbeschreibung = dbInhalt.getString("kursbeschreibung");
            String anbieter = dbInhalt.getString("anbieter");
            kurseAlleMitarbeiter = new KurseAlleMitarbeiter(personalnummer,nachname,vorname,zertifikatstitel,kursbeschreibung,anbieter);

            kurseAlleMitarbeiterliste.add(kurseAlleMitarbeiter);
        }
        return kurseAlleMitarbeiterliste;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    public List<ZertifikateAlleMitarbeiterGueltigkeit> ausfuehrenZertifikateAlleMitarbeiterGueltigkeit(ResultSet dbInhalt) throws SQLException {

        List<ZertifikateAlleMitarbeiterGueltigkeit> ZertifikateAlleMitarbeiterGueltigkeitliste = new ArrayList<>();
        ZertifikateAlleMitarbeiterGueltigkeit zertifikateAlleMitarbeiterGueltigkeit;

        while (dbInhalt.next()) {

            String nachname = dbInhalt.getString("nachname");
            String vorname = dbInhalt.getString("vorname");
            String zertAblDatum = dbInhalt.getString("zertAblDatum");
            String zertifikatstitel = dbInhalt.getString("zertifikatstitel");
            String zertifikatsbeschreibung = dbInhalt.getString("zertifikatsbeschreibung");
            zertifikateAlleMitarbeiterGueltigkeit = new ZertifikateAlleMitarbeiterGueltigkeit(nachname,vorname,zertAblDatum,zertifikatstitel,zertifikatsbeschreibung);


            ZertifikateAlleMitarbeiterGueltigkeitliste.add(zertifikateAlleMitarbeiterGueltigkeit);
        }
        return ZertifikateAlleMitarbeiterGueltigkeitliste;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    public List<?> auswertungAusDbLesen(String query){
        return  auswertungAusgeben(query);
    }


    void auswertungErstellen(ResultSet dbInhalt) throws SQLException {
        MitarbeiterAuswertung mitarbeiterAuswertung = new MitarbeiterAuswertung();
        mitarbeiterAuswertung.ausgabeListe(mitarbeiterAuswertung.mitarbeiterAuswerten(dbInhalt));
    }
}
