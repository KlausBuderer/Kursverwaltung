package Benutzerverwaltung;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Benutzer {

    private String benutzer;
    private String passwort;
    private BENUTZER_STATUS benutzerStatus;
    private BENUTZERGRUPPEN benutzergruppen;
    private List<String> alleBenutzerString;
    private String[] benutzerArray;
    private List<Benutzer> alleBenutzer = new ArrayList<>();

    public static String angemeldeterBenutzer;
    public static BENUTZERGRUPPEN angemeldeteGruppe;

    public Benutzer(String benutzer, String passwort, BENUTZER_STATUS benutzerStatus, BENUTZERGRUPPEN benutzergruppen) {
        this.benutzer = benutzer;
        this.passwort = passwort;
        this.benutzerStatus = benutzerStatus;
        this.benutzergruppen = benutzergruppen;
    }

    public Benutzer() {
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Benutzer Anmelden
     */
    public boolean benutzerAnmelden(String benutzerEingabe, String passwortEingabe){
        String benutzername = benutzerEingabe;
        String passwort = passwortEingabe;

        // Ausgabe, dass sich angemeldet werden muss

        List benutzerListe = benutzerAusDateiLesen();

        if (benutzerListe.isEmpty()){
            System.out.println("Es sind keine Benutzer auffindbar");
            return false;
        }

        // Prüfen ob der Benutzer existiert
        Benutzer b;

            b = benutzerSuchen(benutzerListe,benutzername);

        if(b.benutzer.isEmpty()){
            System.out.println("Dieser Benutzer ist nicht vorhanden");
            return false;
        }


        // Prüfen ob das Passwort korrekt ist
        if (b.passwort.equals(passwort)){
            // Bei korrekter Eingabe Benutzer anmelden

            angemeldeterBenutzer = b.benutzer;
            angemeldeteGruppe = b.benutzergruppen;
            return true;
        }else{
            System.out.println("Falsches Passwort");

        }
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Benutzer hinzuzufügen
     */
    public void benutzerHinzufuegen(){
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Benutzer zu sperren
     */
    public void benutzerStatusMutieren() {
    }
        //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Benutzer zu suchen
     */
        public List benutzerAusDateiLesen(){

            int benutzerIndex;

            try {
                //Benutzerverwaltung File einlesen
                Stream<String> zeilen = Files.lines(Paths.get("Benutzerverwaltung"));
                //Zeile für Zeile in Liste schreiben
                alleBenutzerString = zeilen.collect(Collectors.toList());

                zeilen.close();

                for (String benutzer: alleBenutzerString) {
                    //Zeile durch Delimiter "," trennen in vier Strings
                    benutzerArray = benutzer.split(",");
                    // Überprüfen ob die Anzahl Wörter korrekt ist
                    if (benutzerArray.length == 4) {
                       if(benutzerArray[2].equals(BENUTZER_STATUS.AKTIV.toString())  || benutzerArray[2].equals(BENUTZER_STATUS.DEAKTIVIERT.toString()));
                            if(benutzerArray[3].equals(BENUTZERGRUPPEN.ADMINISTRATOR.toString()) || benutzerArray[3].equals(BENUTZERGRUPPEN.BENUTZER.toString()))
                                    //Liste mit Benutzer befüllen und die Benutzer initialisieren
                                     alleBenutzer.add(new Benutzer(benutzerArray[0], benutzerArray[1], BENUTZER_STATUS.valueOf(benutzerArray[2]), BENUTZERGRUPPEN.valueOf(benutzerArray[3])));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                }

            for (int i = 0; i < alleBenutzer.size(); i++) {
                System.out.println(alleBenutzer.get(i).toString());
            }

            return alleBenutzer;
        }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Benutzer zu suchen
     */

    public Benutzer benutzerSuchen(List<Benutzer> benutzerListe, String benutzername){

        Benutzer benutzer = new Benutzer();

        for (Benutzer b: benutzerListe) {
            if(b.benutzer.equals(benutzername)){
                return b;
            }
        }
        return benutzer;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "benutzer='" + benutzer + '\'' +
                ", passwort='" + passwort + '\'' +
                ", benutzerStatus=" + benutzerStatus +
                ", benutzergruppen=" + benutzergruppen +
                '}';
    }
}
