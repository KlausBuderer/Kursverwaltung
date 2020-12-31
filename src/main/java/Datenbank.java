import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Datenbank {


    Datenbank() {

    }

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

            sqlException.printStackTrace();
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


    public boolean datenAnlegen(Administratives objekt, String kontext) {

        boolean anlegenErfolgreich;

        Connection connection = null;
        Statement statement = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();
            if (kontext.equals("Organisation")) {
                statement.execute("Insert INTO `itwisse_kursverwaltung`.`Organisation` (`AbteilungsBezeichnung`,`KostenstelleID`) VALUES ('" + objekt.stellenbezeichnung + "', '" + objekt.kostenstelleId + "')");
            } else if (kontext.equals("Kostenstelle")) {
                statement.execute("INSERT INTO `itwisse_kursverwaltung`.`Kostenstelle` (`Kostenstelle`, `BezeichnungKST`, `KostenstelleVerantPerson`) VALUES ('" + objekt.kostenstelle + "', '" + objekt.bezeichnungKst + "', '" + objekt.kostenstelleVerantPerson + "')");
            } else if (kontext.equals("Budget")) {
                statement.execute("INSERT INTO `itwisse_kursverwaltung`.`BudgetPeriode` (`Jahr`, `Betrag`, `Waehrung`, `KostenstelleID`) VALUES ('" + objekt.budgetJahr + "', '" + objekt.budgetBetrag + "', '" + objekt.waehrungsArray[objekt.waehrung] + "', '" + objekt.kostenstelleId + "')");
            } else {
                System.out.println("Falscher Übergabeparameter!");
            }
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

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Die Methode untermenueAnzeige zeigt das Untermenü und führt anhand der Eingabe des Benutzers eine Aktion aus

     */
    public boolean datenAnlegenAllgemein(String querry) {

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

    // Abfrage aus Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt
    HashMap<Administratives, Integer> datenAuslesenfuerAbfrageKostenstelle(String tabelle) {

        HashMap<Administratives, Integer> kostenstelleHash = new HashMap<>();

        Connection connection = null;
        Statement statement = null;
        Administratives kostenstelle;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            ResultSet dbInhalt = statement.executeQuery("SELECT * FROM `itwisse_kursverwaltung`.`" + tabelle + "`");

            while (dbInhalt.next()) {

                Integer id = dbInhalt.getInt("ID");
                Integer kostenstelleNummer = dbInhalt.getInt("Kostenstelle");
                String kostenstellenBezeichnung = dbInhalt.getString("BezeichnungKST");
                String verantwortlichePerson = dbInhalt.getNString("KostenstelleVerantPerson");
                kostenstelle = new Administratives(id, kostenstelleNummer, kostenstellenBezeichnung, verantwortlichePerson);

                kostenstelleHash.put(kostenstelle, id);
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


    // Abfrage aus Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt
    HashMap<Administratives, Integer> datenAuslesenfuerAbfrageOrganisation() {

        HashMap<Administratives, Integer> organisationHash = new HashMap<>();

        Connection connection = null;
        Statement statement = null;
        Administratives organisation;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.createStatement();

            ResultSet dbInhalt = statement.executeQuery("SELECT * FROM `itwisse_kursverwaltung`.`Organisation`");

            while (dbInhalt.next()) {

                Integer id = dbInhalt.getInt("ID");
                String abteilungsBezeichnung = dbInhalt.getString("AbteilungsBezeichnung");
                Integer kostenstelleID = dbInhalt.getInt("KostenstelleID");
                organisation = new Administratives(id, kostenstelleID, abteilungsBezeichnung);

                organisationHash.put(organisation, id);
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

        return organisationHash;
    }

    // Abfrage aus Datenbank für Integer Werte -> Gibt einen HashMap zurück Key = ID, Value = Inhalt
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
            budget = new Administratives(id, kostenstelleID, budgetJahr, betrag);

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
            kurs = new Kurse(id,kosten,waehrung, kursCode, anbieter, kursBeschreibung, datumVon, datumBis,durchfuehrungsOrt);

            kursHash.put(kurs, id);


        }
        return kursHash;
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

            sqlException.printStackTrace();
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
}