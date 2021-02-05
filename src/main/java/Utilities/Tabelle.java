package Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Formatieren einer Utilities.Tabelle und einordnen der auszugebenden Daten
public class Tabelle {

        private static final String HORIZONTALE_LINIE = "-";
        private String vertikaleLinie;
        private String kreuzungLinie;
        private String[] kopfzeile;
        private List<String[]> zeile = new ArrayList<>();
        private boolean rechtsBuendig;

        public Tabelle() {
            setVertikaleLinie(false);
        }

        public void setRightAlign(boolean rightAlign) {
            this.rechtsBuendig = rightAlign;
        }

        public void setVertikaleLinie(boolean zeigeVertikaleLinie) {
            vertikaleLinie = zeigeVertikaleLinie ? "|" : "";
            kreuzungLinie = zeigeVertikaleLinie ? "+" : " ";
        }

        public void setHeaders(String... headers) {
            this.kopfzeile = headers;
        }
        // Neuer String in Arraylist übergeben
        public void zeileHinzufuegen(String... zellen) {
            zeile.add(zellen);
        }
        //
        public void ausgabe() {

            int[] maximaleBreite = kopfzeile != null ?
                    Arrays.stream(kopfzeile).mapToInt(String::length).toArray() : null;

            for (String[] zellen : zeile) {
                if (maximaleBreite == null) {
                    maximaleBreite = new int[zellen.length];
                }
                if (zellen.length != maximaleBreite.length) {
                    throw new IllegalArgumentException("Die Anazahl Zelle der Zeilen und des Headers müssen gleich sein");
                }
                for (int i = 0; i < zellen.length; i++) {
                    maximaleBreite[i] = Math.max(maximaleBreite[i], zellen[i].length());
                }
            }

            if (kopfzeile != null) {
                schreibeLinie(maximaleBreite);
                schreibeZeile(kopfzeile, maximaleBreite);
                schreibeLinie(maximaleBreite);
            }
            for (String[] zeile : zeile) {
                schreibeZeile(zeile, maximaleBreite);
            }
            if (kopfzeile != null) {
                schreibeLinie(maximaleBreite);
            }
        }

        private void schreibeLinie(int[] spaltenBreite) {
            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("");
            for (int i = 0; i < spaltenBreite.length; i++) {
                String linie = String.join("", Collections.nCopies(spaltenBreite[i] +
                        vertikaleLinie.length() + 1, HORIZONTALE_LINIE));
                System.out.print(kreuzungLinie + linie + (i == spaltenBreite.length - 1 ? kreuzungLinie : ""));
            }
            System.out.println();
        }

        private void schreibeZeile(String[] zellen, int[] maximaleBreite) {
            BefehlsZeilenSchnittstelle.ausgabeOhneAbsatz("");
            for (int i = 0; i < zellen.length; i++) {
                String s = zellen[i];
                String verStrTemp = i == zellen.length - 1 ? vertikaleLinie : "";
                if (rechtsBuendig) {
                    System.out.printf("%s %" + maximaleBreite[i] + "s %s", vertikaleLinie, s, verStrTemp);
                } else {
                    System.out.printf("%s %-" + maximaleBreite[i] + "s %s", vertikaleLinie, s, verStrTemp);
                }
            }
            System.out.println();
        }



}
