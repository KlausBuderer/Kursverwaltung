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
    private final List<Benutzer> ALLEBENUTZER = new ArrayList<>();
    private final String[] BENUTZERGRUPPEN = {"ADMINISTRATOR", "BENUTZER"};

    public static String angemeldeterBenutzer = "";
    public static String angemeldeteGruppe;

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Konstruktor: Anlegen von Benutzer
     */
    public Benutzer(String benutzer, String passwort, String benutzergruppe) {
        this.benutzer = benutzer;
        this.passwort = passwort;
        this.benutzergruppe = benutzergruppe;
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Konstruktor
     */
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
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Es sind keine Benutzer auffindbar");
            return false;
        }
        // Prüfen ob der Benutzer existiert
        Benutzer b;

            b = benutzerSuchen(benutzerListe,benutzername);

        if(b.benutzer == null){
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Dieser Benutzer ist nicht vorhanden");
            return false;
        }
        // Prüfen ob das Passwort korrekt ist
        if (b.passwort.equals(passwort)){
            // Bei korrekter Eingabe Benutzer anmelden

            angemeldeterBenutzer = b.benutzer;
            angemeldeteGruppe = b.benutzergruppe;
            return true;
        }else{
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Falsches Passwort");

        }
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
   Methode um neuen Benutzer anzulegen
     */

    public void benutzerAnlegen(){
        boolean bereitsVorhanden = true;
        boolean abschliessen = false;

        do {
            do {
                //Ausgabe Benutzer Anlegen
                BefehlsZeilenSchnittstelle.bildReinigen();
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Benutzer Anlegen");

                //Ausgabe Benutzername und einlesen der Eingabe
                benutzer = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Benutzername: ");

                //Prüfen ob Benutzer bereits vorhanden
                bereitsVorhanden = benutzerBereitsVorhandenPruefen(benutzerAusDateiLesen(), benutzer);
                if(bereitsVorhanden){
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Benutzername bereits vergeben!");
                    BefehlsZeilenSchnittstelle.verzoegerung(2000);
                }
            } while (bereitsVorhanden);

            //Ausgabe Passwort und einlesen der Eingabe
            passwort = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Passwort: ");

            //Ausgabe der Auswahl an Benutzergruppen
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Benutzergruppen: \n1. Administrator\n2. Benutzer");
            int auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(2) - 1;

            //Einlesen der Auswahl der Benutzergruppe
            benutzergruppe = BENUTZERGRUPPEN[auswahl];

            //Zusammen setzen des String
            String zusammengesetzt = benutzer + "," + passwort + "," + benutzergruppe;

            //Aufezeigen der Eingabe und abfrgage ob in Ordnung
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(toString());
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
    Methode um neuen Benutzer in die Datei zu schreiben
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
    Methode um Benutzer aus der Datei zu loeschen
     */
    public void benutzerLoeschen() {

        //Ausgabe Benutzer loeschen
        BefehlsZeilenSchnittstelle.bildReinigen();
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Benutzer Loeschen");

        //Auswahlliste von Benutzern ausgeben
        List<Benutzer> benutzerListe = benutzerAusDateiLesen();

        int i = 0;

        for (Benutzer benutzer:benutzerListe) {
            i++;
            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(i + ". " + benutzer.toString());
        }
        //Ausgabe welchen Benutzer moechten sie loeschen
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Welchen Benutzer moechten sie Loeschen?");

        //Auswahl einlesen
        int auswahl = BefehlsZeilenSchnittstelle.eingabeMitWertpruefung(benutzerListe.size())-1;
        this.benutzer = benutzerListe.get(auswahl).benutzer;
        this.passwort = benutzerListe.get(auswahl).passwort;
        this.benutzergruppe = benutzerListe.get(auswahl).benutzergruppe;

        //Ausgabe sind sie sicher das sie den Benutzer loeschen moechten
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz(toString());
        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Sind sie sicher?\n 1. Ja\n 2. Nein");

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
    Methode um das Passwort des Benutzers zu aendern
     */

    public void passwortAendern(){

        //Suche nach dem momentan angemeldeten Benutzer in der Datei
        Benutzer benutzer;

        benutzer = benutzerSuchen(benutzerAusDateiLesen(),angemeldeterBenutzer);
        this.benutzer = benutzer.benutzer;
        this.passwort = benutzer.passwort;
        this.benutzergruppe = benutzer.benutzergruppe;

        //Ausgabe: Geben sie das aktuelle Passwort ein
        //Einlesen der Eingabe und vergleichen mit dem Aktuellen

        BefehlsZeilenSchnittstelle.bildReinigen();

        String eingegebenesPasswort = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Geben sie das aktuelle Passwort ein: ");
        if (!this.passwort.equals(eingegebenesPasswort)){
            //Drei Wiederholungen , falls alle falsch wird die Methode beendet
            for (int i = 0; i < 3; i++) {
                 eingegebenesPasswort = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Falsches Passwort! \n Geben sie das aktuelle Passwort ein: ");
                // Falls das Passwort uebereinstimmt wird die Schleife abgebrochen
                 if (this.passwort.equals(eingegebenesPasswort)){
                     break;
                 }else if(i == 2){
                     BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Zuviele Versuche!");
                     BefehlsZeilenSchnittstelle.verzoegerung(5000);
                     return;
                }
            }
        }
        // Ausgabe: Geben sie das neue Passwort ein
        String erstesPasswort = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Geben sie ein neues Passwort ein: ");
        String zweitesPasswort = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Wiederholen sie das Passwort ein: ");//Ausgabe: Wiederholen sie das Passwort
        //Drei Wiederholungen , falls alle falsch wird die Methode beendet
        if (!erstesPasswort.equals(zweitesPasswort)){
            for (int i = 0; i < 3; i++) {
                erstesPasswort = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Passwort stimmt nicht ueberein, versuchen sie es erneut: ");
                zweitesPasswort = BefehlsZeilenSchnittstelle.abfrageMitEingabeFrei("Wiederholen sie das Passwort ein: ");//Ausgabe: Wiederholen sie das Passwort
                // Falls das Passwort uebereinstimmt wird die Schleife abgebrochen
                if (erstesPasswort.equals(zweitesPasswort)){
                    break;
                }else if(i == 2){
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Zuviele Versuche!");
                    BefehlsZeilenSchnittstelle.verzoegerung(5000);
                    return;
                }
            }
        }

        //String für Text in Datei mit altem Passwort zusammensetzen
        String altesPasswort = benutzer.benutzerAngabenZusammensetzen();
        // String für Text in Datei mit neuem Passwort zusammensetzen
        this.passwort = zweitesPasswort;
        String neuesPasswort = benutzerAngabenZusammensetzen();
        //Ausgabe: Überschreiben des aktuell gespeicherten Eintrag mit neuen Daten
        textInDateiUeberschreiben(neuesPasswort,altesPasswort);

    }



        //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um Benutzer zu suchen -> Wird verwendet um zu pruefen ob der Benutzer bereits vorhanden ist
     */
        public List benutzerAusDateiLesen(){

            try {
                //Benutzerverwaltung File einlesen
                Stream<String> zeilen = Files.lines(Paths.get("Benutzerverwaltung"));
                //Zeile für Zeile in Liste schreiben
                List<String> alleBenutzerString = zeilen.collect(Collectors.toList());
                zeilen.close();


                for (String benutzer: alleBenutzerString) {
                    //Zeile durch Delimiter "," trennen in vier Strings
                    String[] benutzerArray = benutzer.split(",");
                    // Überprüfen ob die Anzahl Wörter korrekt ist
                    if (benutzerArray.length == 3) {
                        if(benutzerArray[2].equals("ADMINISTRATOR") || benutzerArray[2].equals("BENUTZER"))
                            //Liste mit Benutzer befüllen und die Benutzer initialisieren
                                ALLEBENUTZER.add(new Benutzer(benutzerArray[0], benutzerArray[1], benutzergruppe = benutzerArray[2]));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                }
            return ALLEBENUTZER;
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
        return "Benutzer" +
                "benutzer = " + benutzer + '\n' +
                ", passwort = " + passwort + '\n' +
                ", benutzergruppen = " + benutzergruppe + "\n";
    }

    private String benutzerAngabenZusammensetzen(){
        //Zusammen setzen des String
       return  benutzer + "," + passwort + "," + benutzergruppe;
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    Methode um ein Benutzer der Datei entweder zu loeschen oder das Passwort zu aendern
     */
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
