package DatenSchicht;

import Logik.Administratives.Kostenstelle;

import java.util.HashMap;

public interface DatenLogikKostenstelle {

    boolean datenAnlegen(Kostenstelle kostenstelle);

    void datenMutation(Kostenstelle kostenstelle);

    String kostenstelleBezichnungAusgeben(int kostenstellenId);

    HashMap<?,Integer> datenAuslesen(String tabelle);
}
