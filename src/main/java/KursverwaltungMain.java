import java.io.File;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class KursverwaltungMain {

    public static void main(String[] args) {

        //Start Prozedere

        // Verbindungsdaten setzen
        Einstellungen einstellungen = new Einstellungen("Start");

        boolean verbindungAufgebaut;
        Datenbank datenbank = new Datenbank();

        // Verbindung zur Datenbank testen
        do {
            verbindungAufgebaut = datenbank.verbindungTesten();

            if (!verbindungAufgebaut) {
                einstellungen.nachVerbindungFragen();
            }



            HashMap<Integer,Integer> kostenstelleMap =datenbank.datenAuslesenfuerAbfrageInt("Kostenstelle", "Kostenstelle");

            int i = 1;

            Integer[] kostenstelleArray = new Integer[kostenstelleMap.size() + 1];
            for (Map.Entry<Integer,Integer> map: kostenstelleMap.entrySet()){

                kostenstelleArray[i] = map.getKey();

                System.out.println(i + ". " + map.getKey());
                i++;
            }

            System.out.println(kostenstelleMap.get(kostenstelleArray[3]));

        }while(!verbindungAufgebaut);

        while(true) {
            BefehlsZeilenSchnittstelle.hauptmenueAusgeben();
        }




    }
}
