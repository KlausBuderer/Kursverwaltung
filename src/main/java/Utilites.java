/**
 * @author: Bruno Thurnherr <bruno.thurnherr@it-wissen.ch>
 * @version: 1.0
 */
import java.sql.*;

public class Utilites {

    /**
     * Ein Java MySQL SELECT statement Beispiel

     */

    public static void main(String[] args)
    {
        try
        {
            String db_host = "itwisse.mysql.db.hostpoint.ch";
            String db_port = "3306";
            String db_user = "itwisse_kursvw";
            String db_pass = "abbts2020-01";
            String db_base = "itwisse_kursverwaltung";
            String DRIVER = "com.mysql.cj.jdbc.Driver";
            String bedingung1 = "1";
            String bedingung2 = "'en'";
            String mySqlUrl = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_base;

            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(mySqlUrl, db_user, db_pass);

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"

            String query = "SELECT * FROM itwisse_kursverwaltung.Fehlermeldungen where FehlermeldungsNummer =" +bedingung1 +" and Sprache = "+bedingung2 ;

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {


                int FehlermeldungID = rs.getInt("FehlermeldungID");
                String Arbeitsgebiet = rs.getString("Arbeitsgebiet");
                int FehlermeldungsNummer = rs.getInt("FehlermeldungsNummer");
                String Sprache = rs.getString("Sprache");
                String FehlermeldungsText = rs.getString("FehlermeldungsText");

                // print the results
                // System.out.format("%s, %s, %s, %s, %s, \n", FehlermeldungID, Arbeitsgebiet, FehlermeldungsNummer, Sprache, FehlermeldungsText);

                System.out.println("Datensatz Nr.: " +FehlermeldungID );
                System.out.println("Aus Tabelle: Fehlermeldungen");
                System.out.println("Die Fehlermeldung lautet: " +FehlermeldungsText );


            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}


