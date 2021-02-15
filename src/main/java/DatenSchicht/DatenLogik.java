package DatenSchicht;

import Logik.Services;

import java.util.HashMap;

public interface DatenLogik {

    HashMap<?,Integer> suchen(String suchkriterium, String suchText);

    void datenAnlegen(Services services);

    void datenMutation(Services services);

    HashMap<?,Integer> datenAuslesen(String tabelle);

    public void datenLoeschen(int ID);

}
