package Datenbank;

import Administratives.Budget;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BudgetDatenbank extends Datenbank {


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Methode zum Erstelle eines Hashmap mit den jeweiligen Objekten und befüllen der Membervariablen mit den Werten der Datenbank
    Parameter: Inhalt der Tabelle der Datenbank
    Rückgabewert: Hashmap mit Objekten für jeden Tuple
     */
    public HashMap<Budget, Integer> budgetAusgeben(ResultSet dbInhalt) throws SQLException {

        HashMap<Budget, Integer> budgetHash = new HashMap<>();
        Budget budget;

        while (dbInhalt.next()) {

            Integer id = dbInhalt.getInt("ID");
            Integer budgetJahr = dbInhalt.getInt("Jahr");
            Integer kostenstelleID = dbInhalt.getInt("KostenstelleID");
            Integer betrag = dbInhalt.getInt("Betrag");
            String waehrung = dbInhalt.getString("Waehrung");
            budget = new Budget(id, kostenstelleID, budgetJahr, betrag, waehrung);

            budgetHash.put(budget, id);
        }
        return budgetHash;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Anlegen (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public boolean datenAnlegen(Budget budget){
        datenInDbAnlegen(anlegenQuery(budget));
        return false;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Aufruf zum Daten Updaten (Schnittstelle von Logikpaketen zu den Datenbankpaketen)
    Parameter: Objekt des Aufrufers
     */
    public void datenMutation(Budget budget) {
        datenBearbeiten(updateQuery(budget));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys für ein Update eines Budgets
    Parameter: Objekt des Aufrufers
    Rückgabe: query als String
     */
    String updateQuery(Budget budget) {

        return "UPDATE `itwisse_kursverwaltung`.`tblBudgetPeriode` SET " +
                " `Jahr` = " + budget.budgetJahr +
                ", `Betrag` = " + budget.budgetBetrag +
                " WHERE `ID` = " + budget.budgetId + ";";
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zur Erstellung eines Querys für ein Anlegen eines neuen Budget
     Parameter: Objekt des Aufrufers
     Rückgabe: query als String
     */
    String anlegenQuery(Budget budget){

        return "INSERT INTO `itwisse_kursverwaltung`.`tblBudgetPeriode` (`Jahr`, `Betrag`, `Waehrung`, `KostenstelleID`) VALUES" +
                " ('"  + budget.budgetJahr +
                "', '" + budget.budgetBetrag +
                "', '" + budget.waehrung +
                "', '" + budget.kostenstelleId + "')";
    }

}
