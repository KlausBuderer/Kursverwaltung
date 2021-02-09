package Einstellungen;

import Utilities.BefehlsZeilenSchnittstelle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;


        public class Einstellungen {

            private final String[] UNTERMENUE = {"1.  Datenbank Verbindungsdaten aendern", "99. Hauptmenue"};
            private final String[] KOPFZEILETABELLE = {"Url", "Benutzer", "Passwort"};
            private String[] angabenTabelle = {url, benutzer, passwort};

            //Standard Verbindungsdaten
            private final  String URLDEFAULT = "jdbc:mysql://itwisse.mysql.db.hostpoint.ch:3306/itwisse_kursverwaltung";
            private final  String BENUTZERDEFAULT = "itwisse_kursvw";
            private final  String PASSWORTDEFAULT = "abbts2020-01";

            //Benutzerdefinierte Benutzerdaten
            private   String urlBenutzerdefiniert;
            private   String benutzerBenutzerdefiniert;
            private   String passwortBenutzerdefiniert;

            //aktive Verbindungsdaten
            public static String url;
            public static String benutzer;
            public static String passwort;

            private boolean standardVerbindungsDaten = true;

            Scanner scan = new Scanner(System.in);

            //Konstruktor mit der Anzeige des Untermenues
            public Einstellungen() {
                unterMenueAnzeigen();
            }

            //Konsruktor fuer das einmalige Aufstarten der Software
            public Einstellungen(String start){
                verbindunsDatenBeiStartSetzen();
            }

           private void unterMenueAnzeigen() {

                boolean gueltigeEingabe = false;

                do {
                    switch (BefehlsZeilenSchnittstelle.unterMenue(UNTERMENUE,"Einstellungen")) {
                        case 1:
                            nachVerbindungFragen();
                            break;
                        case 99:
                            gueltigeEingabe = true;
                            break;
                        default:
                            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("???");
                    }
                } while (!gueltigeEingabe);
            }


            //Erstellt neue Datei und schreibt die Eingaben vom Benutzer in die Datei
            private void verbindungsdatenDateiErstellen(){

                try {
                    BefehlsZeilenSchnittstelle.bildReinigen("Verbindungsdaten Mutieren",2);
                    FileWriter fileWriter = new FileWriter("DatenbankVerbindungsdaten.txt");
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Geben sie die URL der Datenbank.Datenbank ein");
                    fileWriter.write(scan.next() + "\n");
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Geben sie den Benutzer der Datenbank.Datenbank ein");
                    fileWriter.write(scan.next() + "\n");
                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Geben sie das Passwort des Benutzers ein");
                    fileWriter.write(scan.next() + "\n");
                    fileWriter.close();

                }catch (Exception e){

                    BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Datei erzeugen nicht erfolgreich!");
                }

            }
            //Liest die Daten aus der Datein
           private void verbindungsdatenAusDateiLesen() throws FileNotFoundException {

                File file = new File("DatenbankVerbindungsdaten.txt");

                Scanner scanFile = null;
                try {
                    scanFile = new Scanner(file);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                urlBenutzerdefiniert = scanFile.nextLine();
                benutzerBenutzerdefiniert = scanFile.nextLine();
                passwortBenutzerdefiniert = scanFile.nextLine();
            }


            // Abfrage welche Verbindungsdaten verwendet werden sollen
            public void nachVerbindungFragen(){

                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Welche Verbindungsdaten moechten sie nutzen?");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("1. Standart Verbindungsdaten");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("2. Benutzerdefinierte Verbindungsdaten");

                switch (scan.nextInt()){
                    case 1:
                        standardVerbindungsDaten = true;
                        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Standard Verbindungsdaten aktiviert");
                        url = URLDEFAULT;
                        benutzer = BENUTZERDEFAULT;
                        passwort = PASSWORTDEFAULT;
                        break;
                    case 2:
                        standardVerbindungsDaten = false;
                        try {
                            verbindungsDatenKorrektAbfrage();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Benutzerdefinierte Verbindungsdaten aktiviert");
                        url = urlBenutzerdefiniert;
                        benutzer = benutzerBenutzerdefiniert;
                        passwort = passwortBenutzerdefiniert;
                        break;
                    default:
                        BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");

                }




            }

            void verbindungsDatenKorrektAbfrage() throws FileNotFoundException {

                boolean korrekteEingabe;

                verbindungsdatenAusDateiLesen();
                BefehlsZeilenSchnittstelle.bildReinigen("Verbindungsdaten Mutieren",2);
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Datenbank URL: " + urlBenutzerdefiniert + "\t" + "Benutzer: " + benutzerBenutzerdefiniert + "\t" + "Passwort: " + passwortBenutzerdefiniert);
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte ueberpruefen sie die Daten auf ihre Korrektheit");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Sind die Daten korrekt?");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("1. Verbindungsdaten verwenden");
                BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("2. Neue Verbindungsdaten eingeben");

                do {
                    switch (scan.nextInt()) {
                        case 1:
                            verbindungsdatenAusDateiLesen();
                            korrekteEingabe = true;
                            break;

                        case 2:
                            verbindungsdatenDateiErstellen();
                            korrekteEingabe = true;
                            break;
                        default:
                            BefehlsZeilenSchnittstelle.ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
                            korrekteEingabe = false;
                    }
                }while(!korrekteEingabe);
            }


            void verbindunsDatenBeiStartSetzen(){

                url = URLDEFAULT;
                benutzer = BENUTZERDEFAULT;
                passwort = PASSWORTDEFAULT;

            }

        }

