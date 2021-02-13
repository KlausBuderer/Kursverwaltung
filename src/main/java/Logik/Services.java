package Logik;

import PraesentationSchicht.BefehlsZeilenSchnittstelle;
import PraesentationSchicht.Tabelle;

public abstract class Services {

    public void objectInTabelleAusgeben(String[] kopfzeile, String[] attributenArray) {
       try {
           Tabelle tabelle = new Tabelle();
           tabelle.setHeaders(kopfzeile);
           tabelle.setVertikaleLinie(true);
           tabelle.zeileHinzufuegen(attributenArray);
           tabelle.ausgabe();
       }catch (Exception exception){
           BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Fehler bei der Ausgabe!");
       }

    }

     protected abstract void datenAnlegen();

    protected abstract void datenMutieren();

    protected abstract void datenLoeschen();

    protected abstract String[] attributenArrayFuerTabelle();

}
