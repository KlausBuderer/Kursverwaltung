import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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


}
