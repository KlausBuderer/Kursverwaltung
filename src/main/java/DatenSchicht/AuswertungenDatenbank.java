package DatenSchicht;

import Logik.Auswertungen.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuswertungenDatenbank extends Datenbank {


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceKurseProAnbieterKostenstellenZeitraum(String datumVon, String datumBis){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_KURSE_PRO_ANBIETER_KOSTENSTELLE_JAHR(?,?)}",datumVon, datumBis," ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_KURSE_PRO_ANBIETER_KOSTENSTELLEN_ZEITRAUM);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceWeiterbildungAlleMitarbeiterZeitraum(String datumVon, String datumBis){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_MA_WEITERBILDUNG_DATUM(?,?)}",datumVon, datumBis," ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_WEITERILDUNG_ALLE_MITARBEITER_ZEITRAUM);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceProAnbieterSelektivKostenstelleZeitraum(String datumVon, String datumBis, String kostenstelle){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_KURSE_PRO_ANBIETER_SELEKTIV_KOSTENSTELLE_JAHR(?,?,?)}",datumVon, datumBis, kostenstelle, STORE_PROCEDURE_KONTEXT.AUSWERTUNG_KURSE_PRO_ANBIETER_SELEKTIV_KOSTENSTELLE_ZEITRAUM);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceZertifikateAlleMitarbeiter(){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_MA_ZERTITIFKATE()}"," "," ", " ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_ZERTIFIKATE);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceStammdatenAlleMitarbeiter(){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_MA_STAMMDATEN()}"," "," ", "",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_STAMMDATEN_ALLE_MITARBEITER);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceKurseAlleMitarbeiter(){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_MA_KURSE()}"," "," ", " ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_ALLE_KURSE);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceBudgetAlleKostenstellen(){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_KOSTENSTELLE_BUDGET()}"," "," ", " ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_BUDGET_ALLE_KOSTENSTELLE);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceKostenstellenAlle(){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_ALLE_KOSTENSTELLEN()}"," "," ", " ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_KOSTENSTELLEN_ALLE);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public List storeproduceZertifikateAlleMitarbeiterGueltigkeit(String datumBis){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_NICHT_GUELTIG_ALLE_MA_ZERT(?)}",datumBis," "," ",STORE_PROCEDURE_KONTEXT.AUSWERTUNG_ZERTIFIKATE_ALLE_MITARBEITER_GUELTIGKEIT);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public HashMap<KursProMitarbeiter, Integer> storeproduceKursproMitarbeiter(int mitarbeiterID){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_MA_KURSE(?)}",mitarbeiterID,STORE_PROCEDURE_KONTEXT.AUSWERTUNG_KURS_PRO_MITARBEITER);
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public HashMap<ZertifikatProMitarbeiter, Integer> storeproduceZertifikatproMitarbeiter(int mitarbeiterID){
        return storeProcedureAufrufen("{call SP_ANZEIGEN_MA_ZERT(?)}",mitarbeiterID,STORE_PROCEDURE_KONTEXT.AUSWERTUNG_ZERTIFIKAT_PRO_MITARBEITER);
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
    public List<KurseProAnbieterSelektivKostenstelleZeitraum> ausfuehrenProAnbieterSelektivKostenstelleZeitraum(ResultSet dbInhalt) throws SQLException {

        List<KurseProAnbieterSelektivKostenstelleZeitraum> proAnbieterSelektivKostenstelleZeitraumliste = new ArrayList<>();
        KurseProAnbieterSelektivKostenstelleZeitraum proAnbieterSelektivKostenstelleZeitraum;

        while (dbInhalt.next()) {

            int kostenstelleNr = dbInhalt.getInt("KostenstelleNr");
            int kosten = dbInhalt.getInt("kosten");
            String waehrung = dbInhalt.getString("waehrung");
            String anbieter = dbInhalt.getString("anbieter");
            String selektionszeitraumVon = dbInhalt.getString("selektionszeitraumVon");
            String selektionszeitraumBis = dbInhalt.getString("selektionszeitraumBis");

            proAnbieterSelektivKostenstelleZeitraum = new KurseProAnbieterSelektivKostenstelleZeitraum(kostenstelleNr,kosten,waehrung,anbieter,selektionszeitraumVon,selektionszeitraumBis);

            proAnbieterSelektivKostenstelleZeitraumliste.add(proAnbieterSelektivKostenstelleZeitraum);
        }
        return proAnbieterSelektivKostenstelleZeitraumliste;
    }


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    public List<KurseProAnbieterKostenstellenZeitraum> ausfuehrenKurseProAnbieterKostenstellenZeitraums(ResultSet dbInhalt) throws SQLException {

        List<KurseProAnbieterKostenstellenZeitraum> kurseProAnbieterKostenstellenZeitraumliste = new ArrayList<>();
        KurseProAnbieterKostenstellenZeitraum kurseProAnbieterKostenstellenZeitraum;

        while (dbInhalt.next()) {

            int kostenstelleNr = dbInhalt.getInt("KostenstelleNr");
            String kostenstellebezeichnung = dbInhalt.getString("Kostenstellebezeichnung");
            int kosten = dbInhalt.getInt("KOSTEN");
            String waehrung = dbInhalt.getString("Waehrung");
            String anbieter = dbInhalt.getString("Anbieter");
            String selektionszeitraumVon = dbInhalt.getString("SelektionszeitraumVon");
            String selektionszeitraumBis = dbInhalt.getString("SelektionszeitraumBis");
            kurseProAnbieterKostenstellenZeitraum = new KurseProAnbieterKostenstellenZeitraum(kostenstelleNr,kostenstellebezeichnung,kosten,waehrung,anbieter,selektionszeitraumVon,selektionszeitraumBis);

            kurseProAnbieterKostenstellenZeitraumliste.add(kurseProAnbieterKostenstellenZeitraum);
        }
        return kurseProAnbieterKostenstellenZeitraumliste;
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
    public List<StammdatenAlleMitarbeiter> ausfuehrenStammdatenAlleMitarbeiter(ResultSet dbInhalt) throws SQLException {

        List<StammdatenAlleMitarbeiter> stammdatenAlleMitarbeiterliste = new ArrayList<>();
        StammdatenAlleMitarbeiter stammdatenAlleMitarbeiter;

        while (dbInhalt.next()) {

            int personalnummer = dbInhalt.getInt("personalnummer");
            String anrede = dbInhalt.getString("anrede");
            String nachname = dbInhalt.getString("nachname");
            String vorname = dbInhalt.getString("vorname");
            String jobtitel = dbInhalt.getString("jobtitel");
            String geburtsdatum = dbInhalt.getString("geburtsdatum");
            String statusmitarbeiter = dbInhalt.getString("statusmitarbeiter");
            stammdatenAlleMitarbeiter = new StammdatenAlleMitarbeiter(personalnummer,anrede, nachname,vorname,jobtitel,geburtsdatum,statusmitarbeiter);

            stammdatenAlleMitarbeiterliste.add(stammdatenAlleMitarbeiter);
        }
        return stammdatenAlleMitarbeiterliste;
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
    public List<BudgetAlleKostenstellen> ausfuehrenBudgetAlleKostenstellen(ResultSet dbInhalt) throws SQLException {

        List<BudgetAlleKostenstellen> budgetAlleKostenstellenliste = new ArrayList<>();
        BudgetAlleKostenstellen budgetAlleKostenstellen;

        while (dbInhalt.next()) {

            String budgetjahr = dbInhalt.getString("budgetjahr");
            String budgetbetrag = dbInhalt.getString("budgetbetrag");
            String waehrung = dbInhalt.getString("waehrung");
            int kostenstellennummer = dbInhalt.getInt("kostenstellennummer");
            String kostenstellenbezeichnung = dbInhalt.getString("kostenstellenbezeichnung");
            String kostenstellenverantwortlichePerson = dbInhalt.getString("kostenstellenverantwortlichePerson");

            budgetAlleKostenstellen = new BudgetAlleKostenstellen(budgetjahr,budgetbetrag,waehrung,kostenstellennummer,kostenstellenbezeichnung,kostenstellenverantwortlichePerson);

            budgetAlleKostenstellenliste.add(budgetAlleKostenstellen);
        }
        return budgetAlleKostenstellenliste;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    public List<KostenstellenAlle> ausfuehrenKostenstellenAlle(ResultSet dbInhalt) throws SQLException {

        List<KostenstellenAlle> kostenstellenAlleliste = new ArrayList<>();
        KostenstellenAlle kostenstellenAlle;

        while (dbInhalt.next()) {

            int kostenstellennummer = dbInhalt.getInt("kostenstellennummer");
            String bezechnungkostenstelle = dbInhalt.getString("bezechnungkostenstelle");
            String kostenstellenverantwortlichePerson = dbInhalt.getString("kostenstellenverantwortlichePerson");

            kostenstellenAlle = new KostenstellenAlle(kostenstellennummer,bezechnungkostenstelle,kostenstellenverantwortlichePerson);

            kostenstellenAlleliste.add(kostenstellenAlle);
        }
        return kostenstellenAlleliste;
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
    public HashMap<KursProMitarbeiter,Integer> ausfuehrenKursProMitarbeiter(ResultSet dbInhalt) throws SQLException {

        HashMap<KursProMitarbeiter, Integer> KursProMitarbeiterHashMap = new HashMap<KursProMitarbeiter, Integer>();
        KursProMitarbeiter kursProMitarbeiter;

        while (dbInhalt.next()) {

            int mitarbeiterID = dbInhalt.getInt("mitarbeiterID");
            String nachname = dbInhalt.getString("nachname");
            String vorname = dbInhalt.getString("vorname");
            String kurscode = dbInhalt.getString("kurscode");
            String kursbeschreibung = dbInhalt.getString("kursbeschreibung");
            String anbieter = dbInhalt.getString("anbieter");
            int kosten = dbInhalt.getInt("kosten");
            String waehrung = dbInhalt.getString("waehrung");

            kursProMitarbeiter = new KursProMitarbeiter(mitarbeiterID,nachname,vorname,kurscode,kursbeschreibung,anbieter,kosten,waehrung);


            KursProMitarbeiterHashMap.put(kursProMitarbeiter,mitarbeiterID);
        }
        return KursProMitarbeiterHashMap;
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    public HashMap<ZertifikatProMitarbeiter,Integer> ausfuehrenZertifikatProMitarbeiter(ResultSet dbInhalt) throws SQLException {

        HashMap<ZertifikatProMitarbeiter, Integer> ZertifikatProMitarbeiterHashMap = new HashMap<ZertifikatProMitarbeiter, Integer>();
        ZertifikatProMitarbeiter zertifikatProMitarbeiter;

        while (dbInhalt.next()) {

            int mitarbeiterID = dbInhalt.getInt("mitarbeiterID");
            String nachname = dbInhalt.getString("nachname");
            String vorname = dbInhalt.getString("vorname");
            String zertAblDatum = dbInhalt.getString("zertAblDatum");
            String zertifikatstitel = dbInhalt.getString("zertifikatstitel");
            String zertifikatsbeschreibung = dbInhalt.getString("zertifikatsbeschreibung");



            zertifikatProMitarbeiter = new ZertifikatProMitarbeiter(mitarbeiterID,nachname,vorname,zertAblDatum,zertifikatstitel,zertifikatsbeschreibung);


            ZertifikatProMitarbeiterHashMap.put(zertifikatProMitarbeiter,mitarbeiterID);
        }
        return ZertifikatProMitarbeiterHashMap;
    }

   }
