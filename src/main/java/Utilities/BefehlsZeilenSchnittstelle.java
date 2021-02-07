package Utilities;

import Administratives.Administratives;
import Auswertungen.Auswertungen;
import Benutzerverwaltung.*;
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
            "6. Benutzerverwaltung", "7. Einstellungen","90. Abmelden","99. Programm Beenden"};

    private static final String[] HAUPTMENU_BENUTZER =  {"Hauptmenue","", "1. Mitarbeiter", "2. Kurse", "3. Zertifikate","4. Auswertungen", "5. Administratives", "90. Abmelden",
            "99. Programm Beenden"};

    // Privater Konstruktor um keine Instanzierung zu erlauben
    private BefehlsZeilenSchnittstelle(){

    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     Anmeldefenster ausgeben
     */

    public static void anmeldeFensterAusgeben(){

        boolean benutzerAngemeldet = false;
        String benutzerEingabe;
        String passwortEingabe;

        do{

            ausgabeMitAbsatz("Bitte melden sie sich an");
                System.out.println();
                   benutzerEingabe = abfrageMitEingabeFrei("Benutzername: ");
                   passwortEingabe = abfrageMitEingabeFrei("Passwort: ");
                     benutzerAngemeldet = new Benutzer().benutzerAnmelden(benutzerEingabe,passwortEingabe);

        }while (!benutzerAngemeldet);


    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     Anmeldefenster ausgeben
     */

    public static void hauptmenuAufruf(){

        if(Benutzer.angemeldeteGruppe.equals("ADMINISTRATOR")){
            hauptmenueAusgeben(HAUPTMENU_ADMIN);
        }else{
         hauptmenueAusgeben(HAUPTMENU_BENUTZER);
        }


    }


    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     Anzeige des Hauptmenüs für Benutzer mit Adminrecht
     */
    public static void hauptmenueAusgeben(String[] menu) {

        int auswahlInt = 0;
        boolean gueltigeEingabe;
        String auswahlString;
        String[] hauptmenu = menu;

        Scanner scan = new Scanner(System.in);



        //Ausgabe des Hauptmenüs und Einlesen der Auswahl
        bildReinigen();
        System.out.println();
        System.out.println();

        do {
            // Ausgabe des Menü Arrays
            for (String menue : hauptmenu) {
                ausgabeMitAbsatz(menue);
            }

            System.out.println();

            ausgabeOhneAbsatz("Waehlen sie das gewuenschte Menue (1-7 oder 90 / 99): ");
            auswahlString = scan.next();

            // Überprüft ob die Eingabe eine Ganzzahl [1-(länge des Untermenüs)] ist
            if(!auswahlString.matches("[1-" + (hauptmenu.length - 2) + "]") & (!auswahlString.matches("90") & !auswahlString.matches("99"))){

                ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
                verzoegerung(1500);
                gueltigeEingabe = false;

            }else {
                auswahlInt = Integer.parseInt(auswahlString);
                gueltigeEingabe = true;
            }

        }while(!gueltigeEingabe);

        switch (auswahlInt) {
            case 1:
                new Mitarbeiter();
                break;
            case 2:
                new Kurse();
                break;
            case 3:
                new Zertifikate();
                break;
            case 4:
                new Auswertungen();
                break;
            case 5:
                new Administratives();
                break;
            case 6:
                new Benutzerverwaltung();
                break;
            case 7:
                new Einstellungen();
                break;
            case 90:
                Benutzer.angemeldeterBenutzer = "";
                Benutzer.angemeldeteGruppe = null;
                anmeldeFensterAusgeben();
                break;
            case 99:
                programmBeenden();
                break;
            default:
                ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
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
                ausgabeMitAbsatz(untermenue);
            }
            System.out.println();
            ausgabeOhneAbsatz("Waehlen sie das gewuenschte Untermenue (1-" + (unterMenue.length - 2) + ") oder (99 Menue verlassen): ");

            //Liest Eingabe als String ein um zu überpfüfen ob die Eingabe gültig ist
            auswahlString = scan.next();

            // Überprüft ob die Eingabe eine Ganzzahl [1-(länge des Untermenüs)] oder 99 ist
            if(!auswahlString.matches("[1-" + (unterMenue.length - 2) + "]") & !auswahlString.equals("99")){

                BefehlsZeilenSchnittstelle.bildReinigen();
                ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
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
    Die Methode korrekteEingabeBestätigen fragt den Benutzer ab ob die soeben Eingegebene Eingabe korrekt ist. Der Benutzer
    hat die Möglichkeit die Eingabe nochmals zu wiederholen oder die Eingabe abzubrechen, falls die Eingabe korrekt ist,
    kann die Eingabe gespeichert werden.
     */

    public static int korrekteEingabeBestaetigen() {

        int auswahl = 0;

        ausgabeMitAbsatz("Sind sie sicher ob sie alles richtig eingegeben haben?");
        ausgabeMitAbsatz("1. Ja");
        ausgabeMitAbsatz("2. Nein, neu beginnen");
        ausgabeMitAbsatz("3. Abbrechen");

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
            ausgabeOhneAbsatz("");
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
                    ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
                    System.out.println();

                    verzoegerung(1500);
                    gueltigeEingabe = false;
                }
            } else {
                // Gibt eine Fehlermeldung bei nicht korrekter Eingabe und lässt die Abfrage nochmals beginnen
                BefehlsZeilenSchnittstelle.bildReinigen();
                ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
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
                ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein");
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
                ausgabeMitAbsatz("Ungültiges Datum oder falsches Format");
                ausgabeMitAbsatz("Bitte verwenden sie folgendes Format: dd.MM.yyyy");
                ausgabeOhneAbsatz("");
                korrekteEingabe = false;

            } catch (IllegalArgumentException e) {
                ausgabeMitAbsatz("Fehler");
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
            ausgabeOhneAbsatz(abfrage);

            try {
                eingabe = scan.nextLine();

                for (Character zeichen:eingabe.toCharArray()) {
                    if(Character.isAlphabetic(zeichen) || Character.isSpaceChar(zeichen)){

                    }else {
                        korrekteEingabe = false;
                        ausgabeMitAbsatz("Diese Eingabe darf keine Zahlen enthalten!");
                        break;
                    }
                    korrekteEingabe = true;
                }

            } catch (Exception e) {
                ausgabeMitAbsatz("Fehler bei der Verarbeitung der Eingabe");
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

        ausgabeOhneAbsatz(ausgabe);
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

        ausgabeOhneAbsatz(ausgabe);
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

        ausgabeOhneAbsatz(ausgabe);

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
            ausgabeMitAbsatz(i + ". " + waehrung);
            i++;
        }
        ausgabeOhneAbsatz("Waehrung (1-" + waehrungsArray.length + "): ");

        return waehrungsArray[eingabeMitWertpruefung(waehrungsArray.length)-1];
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
       Methode um das Programm zu beenden
     */


  private static void programmBeenden(){

      Scanner scan = new Scanner(System.in);
      boolean korrekteEingabe;
      int auswahlInt = 0;
      do {



          bildReinigen();
          ausgabeMitAbsatz("Sind sie sicher, dass sie das Programm Beenden möchten?");
          ausgabeMitAbsatz("1. Ja");
          ausgabeMitAbsatz("2. Nein");
          String auswahl = scan.next();


          // Überprüft ob die Eingabe eine Ganzzahl [1-2]
          if(!auswahl.matches("[1-2]")){

             bildReinigen();
              ausgabeMitAbsatz("Bitte geben sie einen gültigen Wert ein!");
              System.out.println();

              verzoegerung(1500);
              korrekteEingabe = false;

          }else {
              auswahlInt = Integer.parseInt(auswahl);
              korrekteEingabe = true;
          }

          if(auswahlInt == 1){
              ausgabeMitAbsatz("Das Programm wird beendet");
              Runtime.getRuntime().exit(0);
          }

      } while (!korrekteEingabe);
        }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
        Ausgabe einer Auswahl von Währungen mit Erwartung einer korrekten Eingabe mit Absatz
        Parameter = Ausgabe
     */
        public static void ausgabeMitAbsatz(String ausgabe){

            System.out.println(seitenAbstandgenerieren(5) + ausgabe);


        }  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
        Ausgabe einer Auswahl von Währungen mit Erwartung einer korrekten Eingabe
        Parameter = Ausgabe
     */
    public static void ausgabeOhneAbsatz(String ausgabe){

        System.out.print(seitenAbstandgenerieren(5) + ausgabe);


    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
        Erstellen eines Abstandes von der Linkenseite bis zum ausgegebenen Text
        Parameter = Anzahl der Leerschlaege die eingefuegt werden muessen
        Ausgabe = String mit den Leerschlaegen
     */
        public static String seitenAbstandgenerieren(int anzahlLeerschlaege){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < anzahlLeerschlaege; i++) {
             stringBuilder.append(" ");
            }
            return stringBuilder.toString();
        }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

}





