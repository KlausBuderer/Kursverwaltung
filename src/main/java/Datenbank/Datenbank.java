package Datenbank;

import Einstellungen.Einstellungen;
import Utilities.BefehlsZeilenSchnittstelle;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Datenbank {


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode führt einen Verbindungstest

    Rückgabewert: Das Resultat des Verbindungstest als Boolean
     */
    public boolean verbindungTesten() {

        boolean verbindungErfolgreich;

        Connection connection = null;
        Statement statement = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);

            System.out.println("Verbindung zu Datenbank.Datenbank erfolgreich");
            BefehlsZeilenSchnittstelle.verzoegerung(2000);

            verbindungErfolgreich = true;

        } catch (SQLException | ClassNotFoundException sqlException) {

            System.out.println("Verbindung zur Datenbank.Datenbank nicht erfolgreich!");
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
    Die Methode untermenueAnzeige zeigt das Untermenü und führt anhand der Eingabe des Benutzers eine Aktion aus
     */
    public boolean datenInDbAnlegen(String query) {

        boolean anlegenErfolgreich;

        Connection connection = null;
        Statement statement = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            System.out.println(query);

            statement.execute(query);

            anlegenErfolgreich = true;

        } catch (SQLException | ClassNotFoundException sqlException) {

            sqlException.printStackTrace();
            anlegenErfolgreich = false;
        }

        if (anlegenErfolgreich) {

            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return anlegenErfolgreich;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Abfrage aus Datenbank.Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt

    Parameter: Tabellennamen der Datenbank.Datenbank als String

    Rückgabewert: HashMap mit Objekt als Key und ID als Value
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
                case "Kostenstelle":
                    datenAuflistung = new KostenstelleDatenbank().kostenstelleAusgeben(dbInhalt);
                    break;
                case "BudgetPeriode":
                    datenAuflistung = new BudgetDatenbank().budgetAusgeben(dbInhalt);
                    break;
                case "Kurse":
                    datenAuflistung = new KursDatenbank().kursListeErstellen(dbInhalt);
                    break;
                case "Zertifikate":
                    datenAuflistung = new ZertifikatsDatenbank().zertifikateListeErstellen(dbInhalt);
                    break;
                case "Auswertung":
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

    public boolean datenBearbeiten(String querry) {

        boolean bearbeitungErfolgreich;

        Connection connection = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            PreparedStatement ps = connection.prepareStatement(querry);

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
    Abfrage aus Datenbank.Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt

    Parameter: Tabellennamen der Datenbank.Datenbank als String

    Rückgabewert: HashMap mit Objekt als Key und ID als Value
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
                   auswertungsListe = new AuswertungenDatenbank().mitarbeiterAuswerten(dbInhalt);
                    break;
                case "view_kurse_auswertung":
                    System.out.println("Kursauswertung");
                    break;
                default:
                    System.out.println("Utilities.Tabelle unbekannt");
            }


        } catch (SQLException | ClassNotFoundException sqlException) {

            sqlException.printStackTrace();
        }finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        }
        return auswertungsListe;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Abfrage aus Datenbank.Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt

    Parameter: Tabellennamen der Datenbank.Datenbank als String

    Rückgabewert: HashMap mit Objekt als Key und ID als Value
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

            if(query.contains("Mitarbeiter")){
            rueckgabeHashMap = new MitarbeiterDatenbank().mitarbeiterListeErstellen(dbInhalt);

            }else if(query.contains("Kurse")){
                rueckgabeHashMap = new KursDatenbank().kursListeErstellen(dbInhalt);
            }else if (query.contains("Zertifikate")){
                rueckgabeHashMap = new ZertifikatsDatenbank().zertifikateListeErstellen(dbInhalt);
            }

        } catch (SQLException | ClassNotFoundException sqlException) {

            sqlException.printStackTrace();
        }finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return rueckgabeHashMap;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank.Datenbank
    Parameter: Inhalt der Utilities.Tabelle der Datenbank.Datenbank
    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */

    public HashMap<?,Integer> dbHashMap(String tabelle){
        return datenAuslesenfuerAbfrage(tabelle);
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode untermenueAnzeige zeigt das Untermenü und führt anhand der Eingabe des Benutzers eine Aktion aus
     */
    public HashMap storeProcedureAufrufen(String query, int parameter) {

        boolean anlegenErfolgreich;

        Connection connection = null;
        CallableStatement statement = null;
        ResultSet dbInhalt = null;
        HashMap rueckgabeHash = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.prepareCall(query);


            statement.setInt(1,parameter);

            System.out.println("Store Procedure erfolgreich");

            dbInhalt = statement.executeQuery();

           rueckgabeHash = new MitarbeiterDatenbank().zertifikatVerlaengern(dbInhalt);

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
}