package Utilities;

import Administratives.Administratives;
import Auswertungen.Auswertungen;
import Benutzerverwaltung.Benutzerverwaltung;
import Einstellungen.Einstellungen;
import Kurse.Kurse;
import Mitarbeiter.Mitarbeiter;
import Zertifikate.Zertifikate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// Utility Klasse für Ausgaben und Eingaben in der Konsole
public final class BefehlsZeilenSchnittstelle {

   private static final String[] HAUPTMENU_ADMIN =  {"Hauptmenue","", "1. Mitarbeiter", "2. Kurse", "3. Zertifikate","4. Auswertungen", "5. Administratives",
            "6. Benutzerverwaltung", "7. Einstellungen","8. Programm Beenden", "Mit welchen Menüpunkt wollen sie weiterfahren?"};

    // Privater Konstruktor um keine Instanzierung zu erlauben
    private BefehlsZeilenSchnittstelle(){

    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     Anzeige des Hauptmenüs für Benutzer mit Adminrecht
     */
    public static void hauptmenueAusgeben() {

        int auswahlInt = 0;
        boolean gueltigeEingabe;
        String auswahlString;

        Scanner scan = new Scanner(System.in);

        //Ausgabe des Hauptmenüs und Einlesen der Auswahl
        bildReinigen();
        System.out.println();
        System.out.println();

        do {
            // Ausgabe des Menü Arrays
            for (String menue : HAUPTMENU_ADMIN) {
                System.out.println(menue);
            }

            System.out.println();

            System.out.print("Wählen sie das gewünschte (1-8): ");


            auswahlString = scan.next();

            // Überprüft ob die Eingabe eine Ganzzahl [1-(länge des Untermenüs)] ist
            if(!auswahlString.matches("[1-" + (HAUPTMENU_ADMIN.length - 3) + "]")){

                System.out.println("Bitte geben sie einen gültigen Wert ein!");
                verzoegerung(1500);
                gueltigeEingabe = false;

            }else {
                auswahlInt = Integer.parseInt(auswahlString);
                gueltigeEingabe = true;
            }

        }while(!gueltigeEingabe);

        switch (auswahlInt) {
            case 1:
                Mitarbeiter mitarbeiter = new Mitarbeiter();
                break;
            case 2:
                Kurse kurse = new Kurse();
                break;
            case 3:
                Zertifikate zertifikate = new Zertifikate();
                break;
            case 4:
                Auswertungen auswertungen = new Auswertungen();
                break;
            case 5:
                Administratives administratives = new Administratives();
                break;
            case 6:
                Benutzerverwaltung benutzerverwaltung = new Benutzerverwaltung();
                break;
            case 7:
                Einstellungen einstellungen = new Einstellungen();
                break;
            case 8:
                programmBeenden();
                break;
            default:
                System.out.println("Bitte geben sie einen gültigen Wert ein!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
      Anzeige der Untermenüs => Untermenü wird als String Array als Parameter übergeben,
      das Menü wird ausgegeben und die Auswahl wird als Integer zurückgegeben

    Parameter: Array von Strings mit dem Auswahltext der angezeigt werden soll
    Rückgabe: Die Auswahl des Bedieners als Integer Wert
   */
    public static int unterMenue(String[] unterMenue) {

        int auswahlInt = 0;
        String auswahlString;
        boolean gueltigeEingabe;

        Scanner scan = new Scanner(System.in);
        BefehlsZeilenSchnittstelle.bildReinigen();

        System.out.println();
        System.out.println();

        do {
            for (String untermenue : unterMenue) {
                System.out.println(untermenue);
            }
            System.out.println();
            System.out.print("Wählen sie das gewünschte Untermenü (1-" + (unterMenue.length - 2) + ") oder (99 Menue verlassen): ");

            //Liest Eingabe als String ein um zu überpfüfen ob die Eingabe gültig ist
            auswahlString = scan.next();

            // Überprüft ob die Eingabe eine Ganzzahl [1-(länge des Untermenüs)] oder 99 ist
            if(!auswahlString.matches("[1-" + (unterMenue.length - 2) + "]") & !auswahlString.equals("99")){

                BefehlsZeilenSchnittstelle.bildReinigen();
                System.out.println("Bitte geben sie einen gültigen Wert ein!");
                System.out.println();

                verzoegerung(1500);
                gueltigeEingabe = false;

            }else {
                auswahlInt = Integer.parseInt(auswahlString);
                gueltigeEingabe = true;
            }
        } while (!gueltigeEingabe);

        return auswahlInt;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode bildReinigen reinigt das die Konsole
     */
    public static void bildReinigen() {

        // Reinigt die Konsole in Windows
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ex) {
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode verzoegerung generiert eine Verzögerung von einer vordefinierten Dauer, um zum Beispiel eine Meldung aus-
    zugeben.

    Parameter: Verzögerungsdauer in ms
     */
    public static void verzoegerung(int dauer){
        try {
            Thread.sleep(dauer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Gibt Daten in einer Utilities.Tabelle aus
         */
    public static void tabelleAusgeben(String[] kopfzeile,String[] angaben){

        BefehlsZeilenSchnittstelle.bildReinigen();
        Tabelle tabelle = new Tabelle();
        tabelle.setVertikaleLinie(true);
        tabelle.setHeaders(kopfzeile);
        tabelle.zeileHinzufuegen(angaben);
        tabelle.ausgabe();
        System.out.println();
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode korrekteEingabeBestätigen fragt den Benutzer ab ob die soeben Eingegebene Eingabe korrekt ist. Der Benutzer
    hat die Möglichkeit die Eingabe nochmals zu wiederholen oder die Eingabe abzubrechen, falls die Eingabe korrekt ist,
    kann die Eingabe gespeichert werden.
     */

    public static int korrekteEingabeBestaetigen() {

        int auswahl = 0;

        System.out.println("Sind sie sicher ob sie alles richtig eingegeben haben?");
        System.out.println("1. Ja");
        System.out.println("2. Nein, neu beginnen");
        System.out.println("3. Abbrechen");

        auswahl = eingabeMitWertpruefung(4);

        return (auswahl);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Die Methode eingabeMitWertpruefung prüft ob der eingegebene Wert des Users eine Ganzzahl ist und sich
    im Wertebereich des Arrays befindet.

    Parameter: Länge des gültigen Eingabebereiches -> Von 1-99 möglich
    Rückgabe: Die gültige Eingabe als Integer Wert
    */
    public static int eingabeMitWertpruefung(int arrayLaenge){

        boolean gueltigeEingabe = false;

        String eingabeString;
        int eingabeInt = 0;

        Scanner scan = new Scanner(System.in);

        do {
            //Liest Eingabe als String ein um zu überpfüfen ob die Eingabe gültig ist
            eingabeString = scan.next();

            // Überprüft ob die Eingabe eine Ganzzahl [1-99] ist
            if (eingabeString.matches("^([1-9][0-9]?)$")) {
                // Überprüft ob die Eingabe in der Range des Arrays ist
                if ((Integer.parseInt(eingabeString) <= (arrayLaenge)) && Integer.parseInt(eingabeString) > 0) {

                    eingabeInt = Integer.parseInt(eingabeString);
                    gueltigeEingabe = true;
                } else {
                    // Gibt eine Fehlermeldung bei nicht korrekter Eingabe und lässt die Abfrage nochmals beginnen
                    BefehlsZeilenSchnittstelle.bildReinigen();
                    System.out.println("Bitte geben sie einen gültigen Wert ein!");
                    System.out.println();

                    verzoegerung(1500);
                    gueltigeEingabe = false;
                }
            } else {
                // Gibt eine Fehlermeldung bei nicht korrekter Eingabe und lässt die Abfrage nochmals beginnen
                BefehlsZeilenSchnittstelle.bildReinigen();
                System.out.println("Bitte geben sie einen gültigen Wert ein!");
                System.out.println();

                verzoegerung(1500);
                gueltigeEingabe = false;
            }
        }while (!gueltigeEingabe) ;

            return eingabeInt;
        }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Methode zur Formatierung einer Ausgabe in Tabellenform

    Parameter
    spaltenInhalt: Text der in der Spalte ausgegeben werden soll
    spaltenBreite: Anzahl Zeichen pro Spalte

     */

    public static String textFormatieren(String spaltenInhalt, int spaltenBreite){

        StringBuffer leerzeichen = new StringBuffer("");// Stringbuilder für die Lehrzeichen nach dem Text

        int anzahlLeerzeichen = (spaltenBreite - spaltenInhalt.length()); // Berechnen der fehlenden Leerzeichen

        if (anzahlLeerzeichen < 5){ //Mindestabstand von 5 Leerzeichen
            anzahlLeerzeichen = 5;
        }

        for (int i = 0; i != anzahlLeerzeichen; i++) { // Hinzufügen von Leerzeichen
            char lehrzeichenA = ' ';
            leerzeichen.append(lehrzeichenA);
        }

        return spaltenInhalt + leerzeichen;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Diese Methode prüft ob die Eingage nur Zahlen verwendet werden

     */

    public static int eingabeAufIntegerPruefen() {

        int korrekterWert = 0;
        String eingabe;
        boolean korrekteEingabe = false;

        Scanner scan = new Scanner(System.in);

        //Prüft ob die Eingabe eine Zahl ist und keine Sonderzeichen enthält
        while (!korrekteEingabe) {
            eingabe = scan.next();
            korrekteEingabe = true;
            if (eingabeIntPruefen(eingabe)) {
                return korrekterWert = Integer.parseInt(eingabe);
            }else {
                korrekteEingabe = false;
                System.out.println("Bitte geben sie einen gültigen Wert ein");
            }
        }
            return korrekterWert;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Diese Methode prüft ob die Eingage nur Zahlen verwendet werden
     */
    public static boolean eingabeIntPruefen(String eingabe){

        boolean korrekteIntEingabe = false;
        int zahl;

            try {
                zahl = Integer.parseInt(eingabe);
                korrekteIntEingabe = true;
            } catch (Exception exception) {
                korrekteIntEingabe = false;
            }

        return korrekteIntEingabe;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Eingabe auf korrektes Datum und korrektes Format prüfen

    Rückgabe = Korrektes Datum als String

     */

    public static String pruefeDatum() {

        Scanner scan = new Scanner(System.in);

        String sDatumFormat= "dd.MM.yyyy";
        boolean korrekteEingabe = false;
        String datum = "";
        Date date;

        do {
            try {
                datum = scan.next();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sDatumFormat);
                simpleDateFormat.setLenient(false);
                date = simpleDateFormat.parse(datum);
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                datum = simpleDateFormat.format(date);
                korrekteEingabe = true;

            } catch (ParseException e) {
                System.out.println("Ungültiges Datum oder falsches Format");
                System.out.println("Bitte verwenden sie folgendes Format: dd.MM.yyyy");
                korrekteEingabe = false;

            } catch (IllegalArgumentException e) {
                System.out.println("Fehler");
                korrekteEingabe = false;
            }
        }while (!korrekteEingabe);
        return datum;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Ausgabe eines Textes mit Erwartung einer Eingabe die keine Sonderzeichne oder Zahlen enthält (z.B Namen)

        Parameter = Ausgabe
        Rückgabe = Korrektes Eingabe als String

     */
    public static String abfrageMitEingabeString(String abfrage) {
        Scanner scan = new Scanner(System.in);
        boolean korrekteEingabe = false;
        String eingabe = "";
        do {
            System.out.print(abfrage);

            try {
                eingabe = scan.nextLine();

                for (Character zeichen:eingabe.toCharArray()) {
                    if(Character.isAlphabetic(zeichen) || Character.isSpaceChar(zeichen)){

                    }else {
                        korrekteEingabe = false;
                        System.out.println("Diese Eingabe darf keine Zahlen enthalten!");
                        break;
                    }
                    korrekteEingabe = true;
                }

            } catch (Exception e) {
                System.out.println("Fehler bei der Verarbeitung der Eingabe");
                korrekteEingabe = false;
            }
        } while (!korrekteEingabe);
        return eingabe;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Ausgabe eines Textes mit Erwartung einer Eingabe die keine Sonderzeichnen oder Buchstaben enthält

        Parameter = Ausgabe
        Rückgabe = Korrektes Eingabe als int

     */

    public static int abfrageMitEingabeInt(String ausgabe){

       int eingabe = -1;
       Scanner scan = new Scanner(System.in);

        System.out.print(ausgabe);
        eingabe = eingabeAufIntegerPruefen();

        return eingabe;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Ausgabe eines Textes mit Erwartung einer Eingabe eines Datum im richtigen Format

        Parameter = Ausgabe
        Rückgabe = Korrektes Eingabe als int

     */

    public static String abfrageMitEingabeDatum(String ausgabe){

        String eingabe = "";
        Scanner scan = new Scanner(System.in);

        System.out.print(ausgabe);
        eingabe = pruefeDatum();

        return eingabe;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Ausgabe eines Textes mit Erwartung einer Eingabe eines Datum im richtigen Format

        Parameter = Ausgabe
        Rückgabe = Korrektes Eingabe als int

     */

    public static String abfrageMitEingabeFrei(String ausgabe){

        Scanner scan = new Scanner(System.in);

        System.out.print(ausgabe);

        return scan.nextLine();
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Ausgabe einer Auswahl von Währungen mit Erwartung einer korrekten Eingabe

        Parameter = Ausgabe
        Rückgabe = Auswahl als String

     */

    public static String abfrageWaehrung(){

        String[] waehrungsArray = {"CHF","EUR","USD"};
        Scanner scan = new Scanner(System.in);

        int i = 1;

        for (String waehrung: waehrungsArray) {
            System.out.println(i + ". " + waehrung);
            i++;
        }
        System.out.println("Waehrung (1-" + waehrungsArray.length + "): ");

        return waehrungsArray[eingabeMitWertpruefung(waehrungsArray.length)-1];
    }
  private static void programmBeenden(){

      Scanner scan = new Scanner(System.in);
      boolean korrekteEingabe;
      int auswahlInt = 0;
      do {



          bildReinigen();
          System.out.println("Sind sie sicher, dass sie das Programm Beenden möchten?");
          System.out.println("1. Ja \n2. Nein");
          String auswahl = scan.next();


          // Überprüft ob die Eingabe eine Ganzzahl [1-2]
          if(!auswahl.matches("[1-2]")){

             bildReinigen();
              System.out.println("Bitte geben sie einen gültigen Wert ein!");
              System.out.println();

              verzoegerung(1500);
              korrekteEingabe = false;

          }else {
              auswahlInt = Integer.parseInt(auswahl);
              korrekteEingabe = true;
          }

          if(auswahlInt == 1){
              System.out.println("Das Programm wird beendet");
              Runtime.getRuntime().exit(0);
          }

      } while (!korrekteEingabe);
        }
}





