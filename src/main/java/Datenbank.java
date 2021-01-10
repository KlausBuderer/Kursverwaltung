import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            System.out.println("Verbindung zu Datenbank erfolgreich");
            BefehlsZeilenSchnittstelle.verzoegerung(2000);

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
    Die Methode untermenueAnzeige zeigt das Untermenü und führt anhand der Eingabe des Benutzers eine Aktion aus
     */
    public boolean datenAnlegen(String querry) {

        boolean anlegenErfolgreich;

        Connection connection = null;
        Statement statement = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            statement.execute(querry);

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

    // Abfrage aus Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt
    HashMap<Integer, Integer> datenAuslesenfuerAbfrageInt(String tabelle, String spalte) {

        HashMap<Integer, Integer> kostenstelleHash = new HashMap<>();

        Connection connection = null;
        Statement statement = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            ResultSet dbInhalt = statement.executeQuery("SELECT " + spalte + ", ID FROM `itwisse_kursverwaltung`.`" + tabelle + "`");

            while (dbInhalt.next()) {
                if (!kostenstelleHash.containsValue(dbInhalt.getInt(spalte)))
                    kostenstelleHash.put(dbInhalt.getInt(spalte), dbInhalt.getInt("ID"));
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

        return kostenstelleHash;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Abfrage aus Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt

    Parameter: Tabellennamen der Datenbank als String

    Rückgabewert: HashMap mit Objekt als Key und ID als Value
     */
    HashMap<?, Integer> datenAuslesenfuerAbfrage(String tabelle) {

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
                    datenAuflistung = kostenstelleAusgeben(dbInhalt);
                    break;
                case "Organisation":
                    datenAuflistung = organisationAusgeben(dbInhalt);
                    break;
                case "BudgetPeriode":
                    datenAuflistung = budgetAusgeben(dbInhalt);
                    break;
                case "Kurse":
                    datenAuflistung = kurseAusgeben(dbInhalt);
                    break;
                case "Zertifikate":
                    datenAuflistung = zertifikateAusgeben(dbInhalt);
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
    Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank

    Parameter: Inhalt der Tabelle der Datenbank

    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */
    HashMap<Administratives, Integer> kostenstelleAusgeben(ResultSet dbInhalt) throws SQLException {

        HashMap<Administratives, Integer> kostenstelleHash = new HashMap<>();
        Administratives kostenstelle;

        while (dbInhalt.next()) {
            Integer id = dbInhalt.getInt("ID");
            Integer kostenstelleNummer = dbInhalt.getInt("Kostenstelle");
            String kostenstellenBezeichnung = dbInhalt.getString("BezeichnungKST");
            String verantwortlichePerson = dbInhalt.getNString("KostenstelleVerantPerson");
            kostenstelle = new Administratives(id, kostenstelleNummer, kostenstellenBezeichnung, verantwortlichePerson);

            kostenstelleHash.put(kostenstelle, id);


        }
        return kostenstelleHash;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank

    Parameter: Inhalt der Tabelle der Datenbank

    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */

    HashMap<Administratives, Integer> organisationAusgeben(ResultSet dbInhalt) throws SQLException {

        HashMap<Administratives, Integer> organisationHash = new HashMap<>();
        Administratives organisation;

        while (dbInhalt.next()) {

            Integer id = dbInhalt.getInt("ID");
            String abteilungsBezeichnung = dbInhalt.getString("AbteilungsBezeichnung");
            Integer kostenstelleID = dbInhalt.getInt("KostenstelleID");
            organisation = new Administratives(id, kostenstelleID, abteilungsBezeichnung);

            organisationHash.put(organisation, id);


        }
        return organisationHash;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank

    Parameter: Inhalt der Tabelle der Datenbank

    Rückgabewert: Hashmap mit Objekten für jeden Tuple

     */

    HashMap<Administratives, Integer> budgetAusgeben(ResultSet dbInhalt) throws SQLException {

        HashMap<Administratives, Integer> budgetHash = new HashMap<>();
        Administratives budget;

        while (dbInhalt.next()) {

            Integer id = dbInhalt.getInt("ID");
            Integer budgetJahr = dbInhalt.getInt("Jahr");
            Integer kostenstelleID = dbInhalt.getInt("KostenstelleID");
            Integer betrag = dbInhalt.getInt("Betrag");
            String waehrung = dbInhalt.getString("Waehrung");
            budget = new Administratives(id, kostenstelleID, budgetJahr, betrag, waehrung);

            budgetHash.put(budget, id);


        }
        return budgetHash;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank

    Parameter: Inhalt der Tabelle der Datenbank

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

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank

    Parameter: Inhalt der Tabelle der Datenbank

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

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Methode zur Bearbeitung einer Tabelle von der Datenbank

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
    Abfrage aus Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt

    Parameter: Tabellennamen der Datenbank als String

    Rückgabewert: HashMap mit Objekt als Key und ID als Value
     */
    public ResultSet auswertungAusgeben(String view) {

        Connection connection = null;
        Statement statement = null;

        ResultSet dbInhalt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            dbInhalt = statement.executeQuery("SELECT * FROM `itwisse_kursverwaltung`.`" + view + "`");

            switch(view) {
                case "view_Bruno_TEST":     MitarbeiterAuswertung mitarbeiterAuswertung = new MitarbeiterAuswertung();
                                            mitarbeiterAuswertung.ausgabeListe(mitarbeiterAuswertung.mitarbeiterAuswerten(dbInhalt));
                                            break;
                case "view_kurse_auswertung":
                    System.out.println("Kursauswertung");
                    break;
                default:
                    System.out.println("Tabelle unbekannt");
            }

            connection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException sqlException) {

            sqlException.printStackTrace();
        }
        return dbInhalt;
    }
}