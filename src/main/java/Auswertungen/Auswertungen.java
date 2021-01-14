package Auswertungen;

import Datenbank.AuswertungenDatenbank;
import Utilities.BefehlsZeilenSchnittstelle;

public class Auswertungen{

    private String[] unterMenue = {"Auswertungen", "1. Mitarbeiter","2. Zertifikate","3. Kurse","4. Hauptmenue"};
    private final String[] viewBefehle = {"view_Bruno_TEST","view_kurse_auswertung"};

    public Auswertungen() {
        untermenuAnezeigen();
    }

    public Auswertungen(String kontext) {
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Anzeigen des Untermenüs
     */
    private void untermenuAnezeigen(){
        boolean gueltigeEingabe = false;

        do{
            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)){
                case 1:
                    new AuswertungenDatenbank().auswertungAusDbLesen(viewBefehle[0]);
                    break;
                case 2:
                    System.out.println("Zertifikat Auswertung");
                    break;
                case 3:
                    System.out.println("Kurse Auswertung");
                    new AuswertungenDatenbank().auswertungAusDbLesen(viewBefehle[1]);
                    break;
                case 4:
                    System.out.println("Kurse Auswertung");
                    gueltigeEingabe = true;
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    gueltigeEingabe = false;
            }
        }while(!gueltigeEingabe);
    }


}
