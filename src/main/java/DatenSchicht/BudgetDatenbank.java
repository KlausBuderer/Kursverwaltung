package DatenSchicht;

import Logik.Administratives.Budget;
import Logik.Services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BudgetDatenbank extends Datenbank implements DatenLogik{


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenAnlegen(Services services){
        datenInDbAnlegen(anlegenQuery((Budget) services));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Services services) {
        datenBearbeiten(updateQuery((Budget) services));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
           /*
    Gibt eine Auswahlliste zurueck
     */
    public List<?> datenAuslesen(String tabelle){
        return datenAuslesenfuerAbfrage(tabelle);
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle einer Liste mit den jeweiligen Objekten und befuellen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rueckgabewert: Liste mit Objekten fuer jeden Tuple
     */
    List<Budget> budgetAusgeben(ResultSet dbInhalt) throws SQLException {

        List<Budget> budgetListe = new ArrayList<>();
        Budget budget;

        while (dbInhalt.next()) {

            Integer id = dbInhalt.getInt("ID");
            Integer budgetJahr = dbInhalt.getInt("Jahr");
            Integer kostenstelleID = dbInhalt.getInt("KostenstelleID");
            Integer betrag = dbInhalt.getInt("Betrag");
            String waehrung = dbInhalt.getString("Waehrung");
            budget = new Budget(id, kostenstelleID, budgetJahr, betrag, waehrung);

            budgetListe.add(budget);
        }
        return budgetListe;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys fuer ein Update eines Budgets
    Parameter: Objekt des Aufrufers
    Rueckgabe: query als String
     */
    private String updateQuery(Budget budget) {

        return "UPDATE `itwisse_kursverwaltung`.`tblBudgetPeriode` SET " +
                " `Jahr` = " + budget.getBudgetJahr() +
                ", `Betrag` = " + budget.getBudgetBetrag() +
                " WHERE `ID` = " + budget.getBudgetId() + ";";
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys fuer ein Anlegen eines neuen Budget
     Parameter: Objekt des Aufrufers
     Rueckgabe: query als String
     */
    private String anlegenQuery(Budget budget){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblBudgetPeriode` (`Jahr`, `Betrag`, `Waehrung`, `KostenstelleID`) VALUES" +
                " ('"  + budget.getBudgetJahr() +
                "', '" + budget.getBudgetBetrag() +
                "', '" + budget.getWaehrung() +
                "', '" + budget.getKostenstelleId() + "')";
    }


    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Wird beim Ausbau der Software implementiert
    @Override
    public List<?> suchen(String suchkriterium, String suchText) {
        return null;
    }

    //Wird beim Ausbau der Software implementiert
    @Override
    public void datenLoeschen(int ID) {}
}
