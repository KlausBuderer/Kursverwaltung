package Benutzerverwaltung;

import Utilities.BefehlsZeilenSchnittstelle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Benutzer {

    private String benutzer;
    private String passwort;
    private String benutzergruppe;
    private BENUTZER_STATUS benutzerStatus;
    private BENUTZERGRUPPEN benutzergruppen;
    private List<String> alleBenutzerString;
    private String[] benutzerArray;
    private List<Benutzer> alleBenutzer = new ArrayList<>();
    private String[] BENUTZERGRUPPENa = {"ADMINISTRATOR", "BENUTZER"};

    public static String angemeldeterBenutzer = "";
    public static String angemeldeteGruppe;

    public Benutzer(String benutzer, String passwort, BENUTZER_STATUS benutzerStatus, String benutzergruppe) {
        this.benutzer = benutzer;
        this.passwort = passwort;
        this.benutzerStatus = benutzerStatus;
        this.benutzergruppe = benutzergruppe;
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
        List benutzerListe = benutzerAusDateiLesen();

        // Ausgabe, Bitte melden sie sich an
        if (benutzerListe.isEmpty()){
            System.out.println("Es sind keine Benutzer auffindbar");
            return false;
        }
        // Prüfen ob der Benutzer existiert
        Benutzer b;

            b = benutzerSuchen(benutzerListe,benutzername);

        if(b.benutzer == null){
            System.out.println("Dieser Benutzer ist nicht vorhanden");
            return false;
        }
        // Prüfen ob das Passwort korrekt ist
        if (b.passwort.equals(passwort)){
            // Bei korrekter Eingabe Benutzer anmelden

            angemeldeterBenutzer = b.benutzer;
            angemeldeteGruppe = b.benutzergruppe;
            return true;
        }else{
            System.out.println("Falsches Passwort");

        }
        return false;
    }

    public void benutzerAnlegen(){
        boolean bereitsVorhanden = true;
        boolean abschliessen = false;

        do {
            do {
                //Ausgabe Benutzer Anlegen
                BefehlsZeilenSchnittstelle.bildReinigen();
                BefehlsZeilenSchnittstelle.ausgabe("Benutzer Anlegen");

                //Ausgabe Benutzername und einlesen der Eingabe
                benutzer = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Benutzername: ");

                //Prüfen ob Benutzer bereits vorhanden
                bereitsVorhanden = benutzerBereitsVorhandenPruefen(benutzerAusDateiLesen(), benutzer);
                if(bereitsVorhanden){
                    BefehlsZeilenSchnittstelle.ausgabe("Benutzername bereits vergeben!");
                    BefehlsZeilenSchnittstelle.verzoegerung(2000);
                }
            } while (bereitsVorhanden);

            //Ausgabe Passwort und einlesen der Eingabe
            passwort = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Passwort: ");

            //Ausgabe der Auswahl an Benutzergruppen
            BefehlsZeilenSchnittstelle.ausgabe("Benutzergruppen: \n1. Administrator\n2. Benutzer");
            int auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1;

            //Einlesen der Auswahl der Benutzergruppe
            benutzergruppe = BENUTZERGRUPPENa[auswahl];

            //Status auf Aktiv setzen
            benutzerStatus = BENUTZER_STATUS.AKTIV;

            //Zusammen setzen des String
            String zusammengesetzt = benutzer + "," + passwort + "," + benutzerStatus + "," + benutzergruppe;

            //Aufezeigen der Eingabe und abfrgage ob in Ordnung
            BefehlsZeilenSchnittstelle.ausgabe(toString());
            switch (BefehlsZeilenSchnittstelle.korrekteEingabeBestaetigen()) {

                case 1: //1.Ja -> Benutzer speichern
                    //Schreiben der Daten in die Datei
                    neuenBenutzerSchreiben(zusammengesetzt);
                    abschliessen = true;
                    break;
                case 2: //2.Nein-> Springe zu Aufrufen MitarbeiterSuchen()
                    abschliessen = false;
                    break;
                case 3: //3.Abbrechen-> Methode Beenden
                    abschliessen = true;
                    break;
            }
        }while (!abschliessen);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Benutzer in die Datei zu schreiben
     */
    public void neuenBenutzerSchreiben(String benutzerDaten){
        try {
            FileWriter fileWriter = new FileWriter("Benutzerverwaltung",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Fügt neue Zeile hinzu
            bufferedWriter.newLine();
            //Schreibt den neuen Benutzer in die Liste
            bufferedWriter.write(benutzerDaten);
            //Schliesst das Dokument
            bufferedWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Benutzer zu sperren
     */
    public void benutzerLoeschen() {

        //Ausgabe Benutzer loeschen
        BefehlsZeilenSchnittstelle.bildReinigen();
        BefehlsZeilenSchnittstelle.ausgabe("Benutzer Loeschen");

        //Auswahlliste von Benutzern ausgeben
        List<Benutzer> benutzerListe = benutzerAusDateiLesen();

        int i = 0;

        for (Benutzer benutzer:benutzerListe) {
            i++;
            BefehlsZeilenSchnittstelle.ausgabe(i + ". " + benutzer.toString());
        }
        //Ausgabe welchen Benutzer moechten sie loeschen
        BefehlsZeilenSchnittstelle.ausgabe("Welchen Benutzer moechten sie Loeschen?");

        //Auswahl einlesen
        int auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(benutzerListe.size())-1;
        this.benutzer = benutzerListe.get(auswahl).benutzer;
        this.passwort = benutzerListe.get(auswahl).passwort;
        this.benutzerStatus = benutzerListe.get(auswahl).benutzerStatus;
        this.benutzergruppe = benutzerListe.get(auswahl).benutzergruppe;

        //Ausgabe sind sie sicher das sie den Benutzer loeschen moechten
        BefehlsZeilenSchnittstelle.ausgabe(toString());
        BefehlsZeilenSchnittstelle.ausgabe("Sind sie sicher?\n 1. Ja\n 2. Nein");

        //Eingabe einlesen
        if(BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) == 1) {
            String aktuelleAngaben = benutzerAngabenZusammensetzen();
            String angabenLoeschen = "";
        //Datei ueberschreiben
        textInDateiUeberschreiben(angabenLoeschen,aktuelleAngaben);
        }
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
                            if(benutzerArray[3].equals("ADMINISTRATOR") || benutzerArray[3].equals("BENUTZER"))
                                    //Liste mit Benutzer befüllen und die Benutzer initialisieren
                                     alleBenutzer.add(new Benutzer(benutzerArray[0], benutzerArray[1], BENUTZER_STATUS.valueOf(benutzerArray[2]), benutzergruppe = benutzerArray[3]));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
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

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um zu Prüfen ob Benutzer bereits vorhanden ist
     */
    public boolean benutzerBereitsVorhandenPruefen(List<Benutzer> benutzerListe, String benutzername){

        for (Benutzer b: benutzerListe) {
            if(b.benutzer.equals(benutzername)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "benutzer='" + benutzer + '\'' +
                ", passwort='" + passwort + '\'' +
                ", benutzerStatus=" + benutzerStatus +
                ", benutzergruppen=" + benutzergruppe +
                '}';
    }

    private String benutzerAngabenZusammensetzen(){
        //Zusammen setzen des String
       return  benutzer + "," + passwort + "," + benutzerStatus + "," + benutzergruppe;
    }

    private void textInDateiUeberschreiben(String neuerText, String aktuellerText){

        try {
            //Benutzerverwaltung File einlesen
            Scanner scan = new Scanner(new File("Benutzerverwaltung"));
            StringBuffer buffer = new StringBuffer();
            while (scan.hasNextLine()){
                buffer.append(scan.nextLine() + System.lineSeparator());

            }
            //Liste mit den neuen Angaben versehen
            String inhaltDerDatei = buffer.toString();
            String geaenderteListe = inhaltDerDatei.replaceAll(aktuellerText,neuerText);
            scan.close();
            //Geaenderte Liste in die Datei schreiben
            FileWriter fileWriter = new FileWriter("Benutzerverwaltung");
            fileWriter.append(geaenderteListe);
            fileWriter.flush();
            fileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
