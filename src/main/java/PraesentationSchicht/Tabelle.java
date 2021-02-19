package PraesentationSchicht;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Formatieren einer Tabelle und einordnen der auszugebenden Daten
public class Tabelle {

        private final String HORIZONTALE_LINIE = "-";

        private String vertikaleLinie;
        private String kreuzungLinie;
        private String[] kopfzeile;

        private boolean rechtsBuendig;

        private List<String[]> zeile = new ArrayList<>();

        public Tabelle() {
            vertikaleLinieSetzen(false);
        }

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------
        // Zeilen rechtsbuendig ausgeben falls parameter True ist
        public void rechtsBuendigSetzen(boolean rechtsBuendig) {
            this.rechtsBuendig = rechtsBuendig;
        }
         //-----------------------------------------------------------------------------------------------------------------------------------------------------------
         // Vertikale Linie ausgeben falls parameter True ist
        public void vertikaleLinieSetzen(boolean zeigeVertikaleLinie) {
            vertikaleLinie = zeigeVertikaleLinie ? "|" : "";
            kreuzungLinie = zeigeVertikaleLinie ? "+" : " ";
        }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    // Zeilen rechtsbuendig ausgeben falls parameter True ist
        public void kopfzeileSetzen(String[] kopfzeile) {
            this.kopfzeile = kopfzeile;
        }
        //-----------------------------------------------------------------------------------------------------------------------------------------------------------
        // Neuer StringArray in Arraylist uebergeben
        public void zeileHinzufuegen(String[] zellen) {
            zeile.add(zellen);
        }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    // Ausgabe der Tabelle
        public void ausgabe() {

            // Eruiert die Anzahl der Zellen falls eine Kopfzeile vorhanden ist
            int[] maximaleBreite = kopfzeile != null ?
                    Arrays.stream(kopfzeile).mapToInt(String::length).toArray() : null;

            for (String[] zellen : zeile) {
                if (maximaleBreite == null) {
                    maximaleBreite = new int[zellen.length];
                }
                //Vergleich ob die Zellenlänge identisch ist, sonst wird eine Exception geworfen
                if (zellen.length != maximaleBreite.length) {
                    throw new IllegalArgumentException
                            ("Die Anzahl Zelle der Zeilen und des Headers muessen gleich sein");
                }

                for (int i = 0; i < zellen.length; i++) {
                    maximaleBreite[i] = Math.max(maximaleBreite[i], zellen[i].length());
                }
            }


            if(zeile.isEmpty()){
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Keine Treffer!");
            }else {

                //Kopfzeile schreiben
                if (kopfzeile != null) {
                    schreibeLinie(maximaleBreite);
                    schreibeZeile(kopfzeile, maximaleBreite);
                    schreibeLinie(maximaleBreite);
                }
                //Ausgabe falls keine Treffer

                //Tabelleninhalt schreiben
                for (String[] zeile : zeile) {
                    schreibeZeile(zeile, maximaleBreite);
                }

                if (kopfzeile != null) {
                    schreibeLinie(maximaleBreite);
                }
            }
        }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    // Zusammensetzen und Ausgabe der Horizontalen Linie
        private void schreibeLinie(int[] spaltenBreite) {
            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("");
            //Setzt die Horizontale Linie für jede Zelle zusammen
            for (int i = 0; i < spaltenBreite.length; i++) {
                String linie = String.join("", Collections.nCopies(spaltenBreite[i] +
                        vertikaleLinie.length() + 1, HORIZONTALE_LINIE));
                //Gibt die Linie mit dem Kreuz aus
                System.out.print(BefehlsZeilenSchnittstelle.schriftfarbe
                        + kreuzungLinie + linie +
                        (i == spaltenBreite.length - 1 ? kreuzungLinie : "") +
                        BefehlsZeilenSchnittstelle.ANSI_RESET);
            }
            System.out.println();
        }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //Schreibt die Werte in die Zellen und setzt jeweils eine vertikale Linie dazwischen
        private void schreibeZeile(String[] zellen, int[] maximaleBreite) {
            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("");
            // Schreibt jede Zelle
            for (int i = 0; i < zellen.length; i++) {
                String s = zellen[i];
                String verStrTemp = i == zellen.length - 1 ? vertikaleLinie : "";

                //Bei leerer Liste augeben

                if (rechtsBuendig) {
                    System.out.printf(BefehlsZeilenSchnittstelle.schriftfarbe +"%s %" + maximaleBreite[i] +
                            "s %s", vertikaleLinie, s, verStrTemp + BefehlsZeilenSchnittstelle.ANSI_RESET);
                } else {
                    System.out.printf(BefehlsZeilenSchnittstelle.schriftfarbe + "%s %-" + maximaleBreite[i]
                            + "s %s", vertikaleLinie, s, verStrTemp + BefehlsZeilenSchnittstelle.ANSI_RESET);
                }
            }
            System.out.println();
        }



}
