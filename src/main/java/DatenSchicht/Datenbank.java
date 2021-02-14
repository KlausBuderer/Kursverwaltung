package DatenSchicht;

import Logik.Einstellungen.Einstellungen;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Datenbank {



    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode fuehrt einen Verbindungstest

    Rueckgabewert: Das Resultat des Verbindungstest als Boolean
     */
    public boolean verbindungTesten() {

        boolean verbindungErfolgreich;

        Connection connection = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);

            System.out.println("Verbindung zu Datenbank erfolgreich");
            BefehlsZeilenSchnittstelle.bildReinigen("",3);

            verbindungErfolgreich = true;

        } catch (SQLException | ClassNotFoundException sqlException) {

            System.out.println("Verbindung zur Datenbank nicht erfolgreich!");
            verbindungErfolgreich = false;
        }

        if (verbindungErfolgreich) {

            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return verbindungErfolgreich;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Datein in der DB anzulegen
     */
    public void datenInDbAnlegen(String query) {

        Connection connection = null;
        Statement statement = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            statement.execute(query);


        } catch (SQLException | ClassNotFoundException sqlException) {

            sqlException.printStackTrace();

        }

            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Abfrage aus Datenbank.Datenbank fuer Integer Werte -> Gibt einen HashMap zurueck Key = ID, Value = Inhalt

    Parameter: Tabellennamen der Datenbank.Datenbank als String

    Rueckgabewert: HashMap mit Objekt als Key und ID als Value
     */
    public HashMap<?, Integer> datenAuslesenfuerAbfrage(String tabelle) {

        HashMap<?, Integer> datenAuflistung = new HashMap<>();

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            ResultSet dbInhalt = statement.executeQuery("SELECT * FROM `itwisse_kursverwaltung`.`" + tabelle + "`");

            switch (tabelle) {
                case "tblKostenstelle":
                    datenAuflistung = new KostenstelleDatenbank().kostenstelleAusgeben(dbInhalt);
                    break;
                case "tblBudgetPeriode":
                    datenAuflistung = new BudgetDatenbank().budgetAusgeben(dbInhalt);
                    break;
                case "tblKurse":
                    datenAuflistung = new KursDatenbank().kursListeErstellen(dbInhalt);
                    break;
                case "tblZertifikate":
                    datenAuflistung = new ZertifikatsDatenbank().zertifikateListeErstellen(dbInhalt);
                    break;
                case "tblAuswertung":
                    break;
                default:
                    break;


            }
        } catch (SQLException | ClassNotFoundException sqlException) {

            sqlException.printStackTrace();

        }
        try {
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return datenAuflistung;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Methode zur Bearbeitung einer Utilities.Tabelle von der Datenbank.Datenbank

    Parameter: Der Update Querry der in der aufrufenden Klasse generiert wird

     */

    public boolean datenBearbeiten(String query) {

        boolean bearbeitungErfolgreich;

        Connection connection = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            PreparedStatement ps = connection.prepareStatement(query);

            ps.executeUpdate();

            bearbeitungErfolgreich = true;

        } catch (SQLException | ClassNotFoundException sqlException) {
            System.out.println(sqlException.getLocalizedMessage());
            bearbeitungErfolgreich = false;
        }

        if (bearbeitungErfolgreich) {

            try {
                connection.close();
            } catch (SQLException throwables) {

                throwables.printStackTrace();

            }
        }

        return bearbeitungErfolgreich;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Abfrage aus Datenbank.Datenbank fuer Integer Werte -> Gibt einen HashMap zurueck Key = ID, Value = Inhalt

    Parameter: Tabellennamen der Datenbank.Datenbank als String

    Rueckgabewert: HashMap mit Objekt als Key und ID als Value
     */
    public List<?> auswertungAusgeben(String view) {

        Connection connection = null;
        Statement statement = null;

        List<?> auswertungsListe = new ArrayList<>();
        ResultSet dbInhalt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            dbInhalt = statement.executeQuery("SELECT * FROM `itwisse_kursverwaltung`.`" + view + "`");

            switch (view) {
                case "view_Bruno_TEST":
                   // auswertungsListe = new AuswertungenDatenbank().(dbInhalt);
                    break;
                case "view_kurse_auswertung":
                    System.out.println("Kursauswertung");
                    break;
                default:
                    System.out.println("Utilities.Tabelle unbekannt");
            }


        } catch (SQLException | ClassNotFoundException sqlException) {

            sqlException.printStackTrace();
        }

            try {
                statement.close();
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }


        return auswertungsListe;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Abfrage aus Datenbank.Datenbank fuer Integer Werte -> Gibt einen HashMap zurueck Key = ID, Value = Inhalt

    Parameter: Tabellennamen der Datenbank.Datenbank als String

    Rueckgabewert: HashMap mit Objekt als Key und ID als Value
     */
    public HashMap<?, Integer> datenListeAusgeben(String query) {

        Connection connection = null;
        Statement statement = null;

        HashMap<?,Integer> rueckgabeHashMap = null;

        ResultSet dbInhalt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            dbInhalt = statement.executeQuery("SELECT * FROM `itwisse_kursverwaltung`." + query);

            if(query.contains("tblMitarbeiter")){
            rueckgabeHashMap = new MitarbeiterDatenbank().mitarbeiterListeErstellen(dbInhalt);

            }else if(query.contains("tblKurse")){
                rueckgabeHashMap = new KursDatenbank().kursListeErstellen(dbInhalt);
            }else if (query.contains("tblZertifikate")){
                rueckgabeHashMap = new ZertifikatsDatenbank().zertifikateListeErstellen(dbInhalt);
            }

        } catch (SQLException | ClassNotFoundException sqlException) {

            sqlException.printStackTrace();
        }

            try {
                statement.close();
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        return rueckgabeHashMap;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank.Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank
    Rueckgabewert: Hashmap mit Objekten fuer jeden Tuple

     */

    public HashMap<?,Integer> dbHashMap(String tabelle){
        return datenAuslesenfuerAbfrage(tabelle);
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode untermenueAnzeige zeigt das Untermenue und fuehrt anhand der Eingabe des Benutzers eine Aktion aus
     */
    public HashMap storeProcedureAufrufen(String query, int parameter, STORE_PROCEDURE_KONTEXT kontext) {

        boolean anlegenErfolgreich;

        Connection connection = null;
        CallableStatement statement = null;
        ResultSet dbInhalt = null;
        HashMap rueckgabeHash = null;
        String statusSP;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.prepareCall(query);



            System.out.println("Store Procedure erfolgreich");

            switch (kontext){
                case AUSWERTUNG_KURS_PRO_MITARBEITER:
                    statement.setInt(1,parameter);

                    dbInhalt = statement.executeQuery();
                    rueckgabeHash = new AuswertungenDatenbank().ausfuehrenKursProMitarbeiter(dbInhalt);
                    break;

                case AUSWERTUNG_ZERTIFIKAT_PRO_MITARBEITER:
                    statement.setInt(1,parameter);

                    dbInhalt = statement.executeQuery();
                    rueckgabeHash = new AuswertungenDatenbank().ausfuehrenZertifikatProMitarbeiter(dbInhalt);
                    break;

                case ZERTIFIKATE_PRO_MITARBEITER:
                    statement.setInt(1,parameter);

                    dbInhalt = statement.executeQuery();
                    rueckgabeHash = new MitarbeiterDatenbank().zertifikatVerlaengern(dbInhalt);
                    break;

                case MITARBEITER_LOESCHEN: case KURS_LOESCHEN: case ZERTIFIKAT_LOESCHEN:
                    statement.setInt(1,parameter);
                    statement.executeQuery();
                    statusSP = statement.getString(2);
                    System.out.println(statusSP);
                    BefehlsZeilenSchnittstelle.verzoegerung(3000);
                    break;
            }

        } catch (SQLException | ClassNotFoundException sqlException) {

            System.out.println("Hat geklappt");

            sqlException.printStackTrace();
        }

            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return rueckgabeHash;

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode untermenueAnzeige zeigt das Untermenue und fuehrt anhand der Eingabe des Benutzers eine Aktion aus
     */

    public List storeProcedureAufrufen(String query, String parameter1,String parameter2, STORE_PROCEDURE_KONTEXT kontext) {

        boolean anlegenErfolgreich;

        Connection connection = null;
        CallableStatement statement = null;
        ResultSet dbInhalt = null;
        List rueckgabeList = null;
        String statusSP;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.prepareCall(query);



            System.out.println("Store Procedure erfolgreich");


            switch (kontext) {
                case AUSWERTUNG_MITARBEITER:
                    System.out.println(parameter1 + parameter2);
                    statement.setString(1, parameter1);
                    statement.setString(2, parameter2);

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenWeiterbildungAlleMitarbeiterZeitraum(dbInhalt);
                    break;

                case AUSWERTUNG_ZERTIFIKATE:

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenZertifikateAlleMitarbeiter(dbInhalt);
                    break;

                case AUSWERTUNG_ZERTIFIKAT_PRO_MITARBEITER:

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenStammdatenAlleMitarbeiter(dbInhalt);
                    break;

                case AUSWERTUNG_STAMMDATEN_ALLE_MITARBEITER:

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenStammdatenAlleMitarbeiter(dbInhalt);
                    break;

                case AUSWERTUNG_ALLE_KURSE:

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenKurseAlleMitarbeiter(dbInhalt);
                    break;

                case AUSWERTUNG_BUDGET_ALLE_KOSTENSTELLE:

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenBudgetAlleKostenstellen(dbInhalt);
                    break;

                case AUSWERTUNG_ZERTIFIKATE_ALLE_MITARBEITER_GUELTIGKEIT:
                    System.out.println(parameter1);
                    statement.setString(1, parameter1);

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenZertifikateAlleMitarbeiterGueltigkeit(dbInhalt);
                    break;

               }

        } catch (SQLException | ClassNotFoundException sqlException) {

            System.out.println("Hat geklappt");

            sqlException.printStackTrace();
        }

        try {
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rueckgabeList;

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode ruft ein Store Procedure um die Existenz einer Mitarbeiternummer zu pruefen
     */
    public String storeProcedureAufrufen(String query, int parameter) {

        boolean anlegenErfolgreich;

        Connection connection = null;
        CallableStatement statement = null;
        ResultSet dbInhalt = null;
        String antwortDatenbank = "";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.prepareCall(query);


                    statement.setInt(1, parameter);
                    statement.executeQuery();
                    antwortDatenbank = statement.getString(2);


        } catch (SQLException | ClassNotFoundException sqlException) {

            System.out.println("Hat nicht geklappt");

            sqlException.printStackTrace();
        }

        try {
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return antwortDatenbank;
    }

}