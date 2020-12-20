import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Datenbank {




    Datenbank(){

    }

    public boolean verbindungTesten(){

        boolean verbindungErfolgreich;

        Connection connection = null;
        Statement statement = null;

        try{

           Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url,Einstellungen.benutzer,Einstellungen.passwort);

            System.out.println("Verbindung zu Datenbank erfolgreich");
            BefehlsZeilenSchnittstelle.verzoegerung(2000);

            verbindungErfolgreich = true;

        }catch(SQLException | ClassNotFoundException sqlException){

            sqlException.printStackTrace();
            verbindungErfolgreich = false;
        }

        if(verbindungErfolgreich){

            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return verbindungErfolgreich;
    }



    public boolean datenAnlegen(Administratives objekt, String kontext){

        boolean anlegenErfolgreich;

        Connection connection = null;
        Statement statement = null;

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url,Einstellungen.benutzer,Einstellungen.passwort);
            statement = connection.createStatement();
            if (kontext.equals("Organisation")) {
                statement.execute("Insert INTO `itwisse_kursverwaltung`.`Organisation` (`Stellenbezeichnung`,`Kostenstelle_ID`) VALUES ('" + objekt.stellenbezeichnung + "', '" + objekt.kostenstelleId + "')");
                }else if(kontext.equals("Kostenstelle")) {
                statement.execute("INSERT INTO `itwisse_kursverwaltung`.`Kostenstelle` (`Kostenstelle`, `Bezeichnung KST`, `KostenstelleVerantPerson`) VALUES ('" + objekt.kostenstelle + "', '" + objekt.bezeichnungKst + "', '" + objekt.kostenstelleVerantPerson + "')");
                    }else if(kontext.equals("Budget")){
                statement.execute("INSERT INTO `itwisse_kursverwaltung`.`BudgetPeriode` (`Jahr`, `Betrag`, `Waehrung`, `Kostenstelle_ID`) VALUES ('" + objekt.budgetJahr + "', '" + objekt.budgetBetrag + "', '" + objekt.waehrungsArray[objekt.waehrung] +  "', '" + objekt.kostenstelleId + "')");
                    }else {
                            System.out.println("Falscher Übergabeparameter!");
            }
            anlegenErfolgreich = true;

        }catch(SQLException | ClassNotFoundException sqlException){

            sqlException.printStackTrace();
            anlegenErfolgreich = false;
        }

        if(anlegenErfolgreich){

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
    HashMap<Integer, Integer> datenAuslesenfuerAbfrageInt(String tabelle, String spalte){

        HashMap<Integer,Integer> kostenstelleHash = new HashMap<>();

        Connection connection = null;
        Statement statement = null;

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url,Einstellungen.benutzer,Einstellungen.passwort);
            statement = connection.createStatement();

                ResultSet dbInhalt = statement.executeQuery("SELECT "+ spalte +", ID FROM `itwisse_kursverwaltung`.`" + tabelle + "`");

                while(dbInhalt.next()){
                    if (!kostenstelleHash.containsValue(dbInhalt.getInt(spalte)))
                   kostenstelleHash.put(dbInhalt.getInt(spalte),dbInhalt.getInt("ID"));
                }


        }catch(SQLException | ClassNotFoundException sqlException){

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

}
