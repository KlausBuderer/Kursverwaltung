package DatenSchicht;

import Logik.Administratives.Budget;

import java.util.HashMap;

public interface DatenLogikBudget {

    boolean datenAnlegen(Budget budget);

    void datenMutation(Budget budget);

    HashMap<?,Integer> datenAuslesen(String tabelle);
}
