package DatenSchicht;

import Logik.Einstellungen.Einstellungen;
import PraesentationSchicht.BefehlsZeilenSchnittstelle;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class Datenbank {

    private Connection connection = null;
    private Statement statement = null;

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode fuehrt einen Verbindungstest durch, die Methode wird einmalig beim Start der Software ausgeführt
    Rueckgabewert: Das Resultat des Verbindungstest als Boolean
     */
    public boolean verbindungTesten() {

        boolean verbindungErfolgreich;

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
    Abfrage aus Datenbank fuer Integer Werte -> Gibt einen HashMap von Objekten zurueck

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

            //Schreibt den Inhalt der Tabelle in die Variable dbInhalt
            ResultSet dbInhalt = statement.executeQuery("SELECT * FROM `itwisse_kursverwaltung`.`" + tabelle + "`");

            // Befüllt einen Hashmap mit dem Inhalt der Datenbank und übergibt sie der Variable datenAuflistung
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
    Methode zur Bearbeitung einer Tabelle von der Datenbank
     */

    public boolean datenBearbeiten(String query) {

        boolean bearbeitungErfolgreich;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            PreparedStatement ps = connection.prepareStatement(query);
            // Ausführen des Querys
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
    Methode für die Suche von einem spezifischen Objekts in der Datenbank
    Rueckgabewert: HashMap mit Objekten
     */
    public HashMap<?, Integer> datenInDbSuchen(String query) {

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
    Methode zum Aufrufen von StoreProcedures
     */
    public HashMap storeProcedureAufrufen(String query, int parameter, STORE_PROCEDURE_KONTEXT kontext) {

        CallableStatement statement = null;
        ResultSet dbInhalt = null;
        HashMap rueckgabeHash = null;
        String statusSP;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url, Einstellungen.benutzer, Einstellungen.passwort);
            statement = connection.prepareCall(query);

            //Store Proceddures aufrufen
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
                    statement.getString(2);
                    BefehlsZeilenSchnittstelle.verzoegerung(3000);
                    break;
            }

        } catch (SQLException | ClassNotFoundException sqlException) {
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Fehler beim Ausführen aufgetreten!");
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
   Methode zum Aufrufen von StoreProcedures
     */
    public List storeProcedureAufrufen(String query, String parameter1, String parameter2, String parameter3, STORE_PROCEDURE_KONTEXT kontext) {

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

            //Store Proceddures aufrufen
            switch (kontext) {
                case AUSWERTUNG_WEITERILDUNG_ALLE_MITARBEITER_ZEITRAUM:
                    System.out.println(parameter1 + parameter2);
                    statement.setString(1, parameter1);
                    statement.setString(2, parameter2);

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenWeiterbildungAlleMitarbeiterZeitraum(dbInhalt);
                    break;

                case AUSWERTUNG_KURSE_PRO_ANBIETER_SELEKTIV_KOSTENSTELLE_ZEITRAUM:
                    System.out.println(parameter1 + parameter2 + parameter3);
                    statement.setString(1, parameter1);
                    statement.setString(2, parameter2);
                    statement.setString(3, parameter3);

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenProAnbieterSelektivKostenstelleZeitraum(dbInhalt);
                    break;

                case AUSWERTUNG_KURSE_PRO_ANBIETER_KOSTENSTELLEN_ZEITRAUM:
                    System.out.println(parameter1 + parameter2);
                    statement.setString(1, parameter1);
                    statement.setString(2, parameter2);

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenKurseProAnbieterKostenstellenZeitraums(dbInhalt);
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

                case AUSWERTUNG_KOSTENSTELLEN_ALLE:

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenKostenstellenAlle(dbInhalt);
                    break;

                case AUSWERTUNG_ZERTIFIKATE_ALLE_MITARBEITER_GUELTIGKEIT:
                    System.out.println(parameter1);
                    statement.setString(1, parameter1);

                    dbInhalt = statement.executeQuery();
                    rueckgabeList = new AuswertungenDatenbank().ausfuehrenZertifikateAlleMitarbeiterGueltigkeit(dbInhalt);
                    break;
            }

        } catch (SQLException | ClassNotFoundException sqlException) {
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Fehler beim Ausführen aufgetreten!");
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