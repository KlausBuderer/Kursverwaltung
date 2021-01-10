import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Auswertungen extends Datenbank{

    private String[] unterMenue = {"Auswertungen", "1. Mitarbeiter","2. Zertifikate","3. Kurse","4. Hauptmenue"};
    private final String[] viewBefehle = {"view_Bruno_TEST","view_kurse_auswertung"};

    public Auswertungen() {
        untermenuAnezeigen();
    }

    public Auswertungen(String kontext) {
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anzeigen des Untermenüs
     */
    private void untermenuAnezeigen(){
        boolean gueltigeEingabe = false;

        do{
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)){
                case 1:
                    System.out.println("Mitarbeiter Auswertung");
                     auswertungAusgeben(viewBefehle[0]);
                    break;
                case 2:
                    System.out.println("Zertifikat Auswertung");
                    break;
                case 3:
                    System.out.println("Kurse Auswertung");
                    auswertungAusgeben(viewBefehle[1]);
                    break;
                case 4:
                    System.out.println("Kurse Auswertung");
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        }while(!gueltigeEingabe);
    }


}
