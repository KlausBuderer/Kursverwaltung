import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.sql.SQLOutput;
import java.util.Scanner;


        public class Einstellungen {


            String[] unterMenue = {"Einstellungen", "1. Datenbank Verbindungsdaten ändern", "2. Hauptmenü"};
            String[] kopfzeileTabelle = {"Url", "Benutzer", "Passwort"};
            String[] angabenTabelle = {url, benutzer, passwort};


            //Standard Verbindungsdaten
            public final  String urlDefault = "jdbc:mysql://itwisse.mysql.db.hostpoint.ch:3306/itwisse_kursverwaltung";
            public final  String benutzerDefault = "itwisse_kursvw";
            public final  String passwortDefault = "abbts2020-01";

            //Benutzerdefinierte Benutzerdaten
            public  String urlBenutzerdefiniert;
            public  String benutzerBenutzerdefiniert;
            public  String passwortBenutzerdefiniert;

            //aktive Verbindungsdaten
            public static String url;
            public static String benutzer;
            public static String passwort;

            public boolean standartVerbindungsDaten = true;

            Scanner scan = new Scanner(System.in);

            //Konstruktor mit der Anzeige des Untermenüs
            Einstellungen() {

                unterMenueAnzeigen();

            }

            //Konsruktor für das einmalige Aufstarten der Software
            Einstellungen(String start){
                verbindunsDatenBeiStartSetzen();
            }

            void unterMenueAnzeigen() {

                boolean gueltigeEingabe = false;

                do {
                    switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                        case 1:
                            nachVerbindungFragen();
                            break;
                        case 2:
                            gueltigeEingabe = true;
                            break;
                        default:
                            System.out.println("Bitte geben sie einen gültigen Wert ein!");
                    }
                } while (!gueltigeEingabe);

            }


            //Erstellt neue Datei und schreibt die Eingaben vom Benutzer in die Datei
            void verbindungsDatenDateiErstellen(){

                try {

                    FileWriter fileWriter = new FileWriter("DatenbankVerbindungsdaten.txt");
                    System.out.println("Geben sie die URL der Datenbank ein");
                    fileWriter.write(scan.next() + "\n");
                    System.out.println("Geben sie den Benutzer der Datenbank ein");
                    fileWriter.write(scan.next() + "\n");
                    System.out.println("Geben sie das Passwort des Benutzers ein");
                    fileWriter.write(scan.next() + "\n");
                    fileWriter.close();

                }catch (Exception e){

                    System.out.println("Datei erzeugen nicht erfolgreich!");
                }

            }
            //Liest die Daten aus der Datein
            void verbindungsdatenAusDateiLesen() throws FileNotFoundException {

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
            void nachVerbindungFragen(){

                System.out.println("Welche Verbindungsdaten möchten sie nutzen?");
                System.out.println("1. Standart Verbindungsdaten");
                System.out.println("2. Benutzerdefinierte Verbindungsdaten");

                switch (scan.nextInt()){
                    case 1:
                        standartVerbindungsDaten = true;
                        System.out.println("Standart Verbindungsdaten aktiviert");
                        url = urlDefault;
                        benutzer = benutzerDefault;
                        passwort = passwortDefault;
                        break;
                    case 2:
                        standartVerbindungsDaten = false;
                        try {
                            verbindungsDatenKorrektAbfrage();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        System.out.println("Benutzerdefinierte Verbindungsdaten aktiviert");
                        url = urlBenutzerdefiniert;
                        benutzer = benutzerBenutzerdefiniert;
                        passwort = passwortBenutzerdefiniert;
                        break;
                    default:
                        System.out.println("Bitte geben sie einen gültigen Wert ein!");

                }




            }

            void verbindungsDatenKorrektAbfrage() throws FileNotFoundException {

                boolean korrekteEingabe;

                verbindungsdatenAusDateiLesen();
                BefehlsZeilenSchnittstelle.bildReinigen();
                System.out.println();
                System.out.println("Datenbank URL: " + urlBenutzerdefiniert + "\t" + "Benutzer: " + benutzerBenutzerdefiniert + "\t" + "Passwort: " + passwortBenutzerdefiniert);
                System.out.println();
                System.out.println("Bitte überprüfen sie die Daten auf ihre Korrektheit");
                System.out.println("Sind die Daten korrekt?");
                System.out.println("1. Verbindungsdaten verwenden");
                System.out.println("2. Neue Verbindungsdaten eingeben");

                do {
                    switch (scan.nextInt()) {
                        case 1:
                            verbindungsdatenAusDateiLesen();
                            korrekteEingabe = true;
                            break;

                        case 2:
                            verbindungsDatenDateiErstellen();
                            korrekteEingabe = true;
                            break;
                        default:
                            System.out.println("Bitte geben sie einen gültigen Wert ein!");
                            korrekteEingabe = false;
                    }
                }while(!korrekteEingabe);
            }


            void verbindunsDatenBeiStartSetzen(){

                url = urlDefault;
                benutzer = benutzerDefault;
                passwort = passwortDefault;

            }

        }

