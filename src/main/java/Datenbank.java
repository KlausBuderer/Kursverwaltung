import java.sql.*;

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

        if(verbindungErfolgreich == true){

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
                statement.execute("Insert INTO 'itwisse_kursverwaltung'.'Organisation' ('Stellenbezeichnung','Kostenstelle_ID') VALUES ('" + objekt.stellenbezeichnung + "," + objekt.kostenstelleId + "')");
                }else if(kontext.equals("Kostenstelle")){
                        statement.execute("INSERT INTO `itwisse_kursverwaltung`.`Kostenstelle` (`Kostenstelle`, `Bezeichnung KST`, `KostenstelleVerantPerson`) VALUES ('" + objekt.kostenstelle + "', '" + objekt.bezeichnungKst + "', '" + objekt.kostenstelleVerantPerson + "')");
                    }else {
                            System.out.println("Falscher Übergabeparameter!");
            }
            anlegenErfolgreich = true;

        }catch(SQLException | ClassNotFoundException sqlException){

            sqlException.printStackTrace();
            anlegenErfolgreich = false;
        }

        if(anlegenErfolgreich == true){

            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return anlegenErfolgreich;
    }

    void datenAuslesen(){
        Connection connection = null;
        Statement statement = null;

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Einstellungen.url,Einstellungen.benutzer,Einstellungen.passwort);
            statement = connection.createStatement();

                ResultSet dbInhalt = statement.executeQuery("SELECT Kostenstelle FROM `itwisse_kursverwaltung`.`Kostenstelle`");

                System.out.println(dbInhalt);

        }catch(SQLException | ClassNotFoundException sqlException){

            sqlException.printStackTrace();

        }


            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

    }

}
