package DatenSchicht;

import Logik.Services;

import java.util.List;

public interface DatenLogik {

    List<?> suchen(String suchkriterium, String suchText);

    void datenAnlegen(Services services);

    void datenMutation(Services services);

    List<?> datenAuslesen(String tabelle);

    public void datenLoeschen(int ID);

}
