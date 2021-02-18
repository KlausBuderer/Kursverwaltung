package PraesentationSchicht;

import Logik.Administratives.Administratives;
import Logik.Auswertungen.Auswertungen;
import Logik.Benutzerverwaltung.Benutzerverwaltung;
import Logik.Einstellungen.Einstellungen;
import Logik.Kurse.*;
import Logik.Mitarbeiter.Mitarbeiter;
import Logik.Zertifikate.Zertifikate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

// Utility Klasse fuer Ausgaben und Eingaben in der Konsole
public final class BefehlsZeilenSchnittstelle {

   private static final String[] HAUPTMENU_ADMIN =  {"1.  Mitarbeiter", "2.  Kurse", "3.  Zertifikate","4.  Auswertungen", "5.  Administratives",
            "6.  Benutzerverwaltung", "7.  Einstellungen","90. Abmelden","99. Programm Beenden"};

    private static final String[] HAUPTMENU_BENUTZER =  {"1.  Mitarbeiter", "2.  Kurse", "3.  Zertifikate","4.  Auswertungen", "5.  Administratives", "90. Abmelden",
            "99. Programm Beenden"};

    // Konsolenfarben
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_SCHWARZ = "\u001B[30m";
    public static final String ANSI_ROT = "\u001B[31m";
    public static final String ANSI_GRUEN = "\u001B[32m";
    public static final String ANSI_GELB = "\u001B[33m";
    public static final String ANSI_BLAU = "\u001B[34m";
    public static final String ANSI_VIOLETT = "\u001B[35m";
    public static final String ANSI_TUERKIS = "\u001B[36m";
    public static final String ANSI_WEISS = "\u001B[37m";
    public static final String ANSI_USICHTBARER_TEXT = "\u001B[8m";
    public static String schriftfarbe = "";


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
        farbschemaWaehlen(FARBSCHEMA.WEISS);

        do{

            bildReinigen("Anmeldefenster",2);
                System.out.println();
                   benutzerEingabe = abfrageMitEingabeFrei45("                                                 Benutzername: ");
                   passwortEingabe = abfrageMitEingabeFrei45("                                                 Passwort: ");

                     benutzerAngemeldet = new Benutzerverwaltung().benutzerAnmelden(benutzerEingabe,passwortEingabe);
        }while (!benutzerAngemeldet);


    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     Aufruf von Hauptmenue abhängig von der Benutzergruppe
     */
    public static void hauptmenuAufruf(){

        if(Benutzerverwaltung.angemeldeteGruppe.equals("ADMINISTRATOR")){
            hauptmenueAusgeben(HAUPTMENU_ADMIN);
        }else{
         hauptmenueAusgeben(HAUPTMENU_BENUTZER);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     Anzeige des Hauptmenues
     */
    private static void hauptmenueAusgeben(String[] menu) {

        int auswahlInt = 0;
        boolean gueltigeEingabe;
        String auswahlString;
        String[] hauptmenu = menu;

        Scanner scan = new Scanner(System.in);

        //Schriftfarbe bestimmen
        farbschemaWaehlen(FARBSCHEMA.BLAU);

        //Ausgabe des Hauptmenues und Einlesen der Auswahl
        bildReinigen("Hauptmenue", 1);
        do {
            // Ausgabe des Menue Arrays
            for (String menue : hauptmenu) {
                ausgabeMitAbsatz(schriftfarbe + menue + ANSI_RESET);
            }

            System.out.println();

            ausgabeOhneAbsatz(schriftfarbe + "Waehlen sie das gewuenschte Menue (1-7 oder 90 / 99): " + ANSI_RESET);
            auswahlString = scan.next();

            // ueberprueft ob die Eingabe eine Ganzzahl [1-(laenge des Untermenues)] ist
            if(!auswahlString.matches("[1-" + (hauptmenu.length) + "]") &
                    (!auswahlString.matches("90") & !auswahlString.matches("99"))){

                ausgabeMitAbsatz(schriftfarbe + "Bitte geben sie einen gueltigen Wert ein!" + ANSI_RESET);
                verzoegerung(1500);
                gueltigeEingabe = false;
            }else {
                auswahlInt = Integer.parseInt(auswahlString);
                gueltigeEingabe = true;
            }

        }while(!gueltigeEingabe);

        switch (auswahlInt) {
            case 1:
                farbschemaWaehlen(FARBSCHEMA.ROT);
                new Mitarbeiter();
                break;
            case 2:
                farbschemaWaehlen(FARBSCHEMA.GELB);
                new Kurse();
                break;
            case 3:
                farbschemaWaehlen(FARBSCHEMA.VIOLETT);
                new Zertifikate();
                break;
            case 4:
                farbschemaWaehlen(FARBSCHEMA.GRUEN);
                new Auswertungen();
                break;
            case 5:
                farbschemaWaehlen(FARBSCHEMA.TUERKIS);
                new Administratives();
                break;
            case 6:
                farbschemaWaehlen(FARBSCHEMA.VIOLETT);
                new Benutzerverwaltung().untermenueAnzeigen();
                break;
            case 7:
                farbschemaWaehlen(FARBSCHEMA.WEISS);
                new Einstellungen();
                break;
            case 90:
                Benutzerverwaltung.angemeldeterBenutzer = "";
                Benutzerverwaltung.angemeldeteGruppe = null;
                anmeldeFensterAusgeben();
                break;
            case 99:
                programmBeenden();
                break;
            default:
                ausgabeMitAbsatz(schriftfarbe + "Bitte geben sie einen gueltigen Wert ein!" + ANSI_RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
      Anzeige der Untermenues => Untermenue wird als String Array als Parameter uebergeben,
      das Menue wird ausgegeben und die Auswahl wird als Integer zurueckgegeben

    Parameter: Array von Strings mit dem Auswahltext der angezeigt werden soll
    Rueckgabe: Die Auswahl des Bedieners als Integer Wert
   */
    public static int unterMenue(String[] unterMenue, String menueTitel) {

        int auswahlInt = 0;
        String auswahlString;
        boolean gueltigeEingabe;

        Scanner scan = new Scanner(System.in);
        BefehlsZeilenSchnittstelle.bildReinigen(menueTitel,2);

        do {//Ausgabe des Untermenues
            for (String untermenue : unterMenue) {
                ausgabeMitAbsatz(schriftfarbe + untermenue + ANSI_RESET);
            }
            System.out.println();
            ausgabeOhneAbsatz(schriftfarbe + "Waehlen sie das gewuenschte Untermenue (1-" + (unterMenue.length) + ") oder (99 Menue verlassen): " + ANSI_RESET);

            //Liest Eingabe als String ein um zu ueberpfuefen ob die Eingabe gueltig ist
            auswahlString = scan.next();

            // ueberprueft ob die Eingabe eine Ganzzahl [1-(laenge des Untermenues)] oder 99 ist
            if(!auswahlString.matches("[1-" + (unterMenue.length) + "]") & !auswahlString.equals("99")){

                BefehlsZeilenSchnittstelle.bildReinigen(menueTitel,1);
                ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
                System.out.println();

                verzoegerung(1500);
                gueltigeEingabe = false;

            }else {// Bei gueltiger Eingabe wird die Auswahl als Integer zurückgegeben
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
    public static void bildReinigen(String menue, int kontext) {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // Reinigt die Konsole in Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Reinigt die Konsole in Linux und Mac Os
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ex) {
        }
        if (kontext < 3) {
            titelZeile(menue, kontext);
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Die Methode verzoegerung generiert eine Verzoegerung von einer vordefinierten Dauer, um zum Beispiel eine Meldung aus-
    zugeben.

    Parameter: Verzoegerungsdauer in ms
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
    Die Methode korrekteEingabeBestaetigen fragt den Benutzer ab ob die soeben Eingegebene Eingabe korrekt ist. Der Benutzer
    hat die Moeglichkeit die Eingabe nochmals zu wiederholen oder die Eingabe abzubrechen, falls die Eingabe korrekt ist,
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
    Die Methode eingabeMitWertpruefung prueft ob der eingegebene Wert des Users eine Ganzzahl ist und sich
    im Wertebereich des Arrays befindet.

    Parameter: Laenge des gueltigen Eingabebereiches -> Von 1-99 moeglich
    Rueckgabe: Die gueltige Eingabe als Integer Wert
    */
    public static int eingabeMitWertpruefung(int arrayLaenge){

        boolean gueltigeEingabe = false;

        String eingabeString;
        int eingabeInt = 0;

        Scanner scan = new Scanner(System.in);

        do {
            //Liest Eingabe als String ein um zu ueberpfuefen ob die Eingabe gueltig ist
            ausgabeOhneAbsatz("");
            eingabeString = scan.next();

            // ueberprueft ob die Eingabe eine Ganzzahl [1-99] ist
            if (eingabeString.matches("^([1-9][0-9]?)$")) {
                // ueberprueft ob die Eingabe in der Range des Arrays ist
                if ((Integer.parseInt(eingabeString) <= (arrayLaenge)) && Integer.parseInt(eingabeString) > 0) {

                    eingabeInt = Integer.parseInt(eingabeString);
                    gueltigeEingabe = true;
                } else {
                    // Gibt eine Fehlermeldung bei nicht korrekter Eingabe und laesst die Abfrage nochmals beginnen
                    ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
                    System.out.println();

                    verzoegerung(1500);
                    gueltigeEingabe = false;
                }
            } else {
                // Gibt eine Fehlermeldung bei nicht korrekter Eingabe und laesst die Abfrage nochmals beginnen
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

        StringBuffer leerzeichen = new StringBuffer("");// Stringbuilder fuer die Lehrzeichen nach dem Text

        int anzahlLeerzeichen = (spaltenBreite - spaltenInhalt.length()); // Berechnen der fehlenden Leerzeichen

        if (anzahlLeerzeichen < 5){ //Mindestabstand von 5 Leerzeichen
            anzahlLeerzeichen = 5;
        }

        for (int i = 0; i != anzahlLeerzeichen; i++) { // Hinzufuegen von Leerzeichen
            char lehrzeichenA = ' ';
            leerzeichen.append(lehrzeichenA);
        }

        return spaltenInhalt + leerzeichen;
    }

     //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
        Diese Methode prueft ob die Eingage nur Zahlen verwendet werden
     */
    public static int eingabeAufIntegerPruefen() {

        int korrekterWert = 0;
        String eingabe;
        boolean korrekteEingabe = false;

        Scanner scan = new Scanner(System.in);

        //Prueft ob die Eingabe eine Zahl ist und keine Sonderzeichen enthaelt
        //Die Zahl darf maximal 11 stellig sein
        do {
            eingabe = scan.next();
            if(!(eingabe.length() > 11 || eingabe.isEmpty())) {
                korrekteEingabe = true;
                if (eingabeIntPruefen(eingabe)) {
                    return korrekterWert = Integer.parseInt(eingabe);
                } else {
                    korrekteEingabe = false;
                    ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein");
                    ausgabeOhneAbsatz("");
                }
            }else{
                ausgabeMitAbsatz("Ungueltige Eingabe");
                ausgabeMitAbsatz("Zuviele oder keine Zeichen!");
                ausgabeOhneAbsatz("");
            }
        }while (!korrekteEingabe);
            return korrekterWert;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Diese Methode prueft ob die Eingage nur Zahlen verwendet werden
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
    Eingabe auf korrektes Datum und korrektes Format pruefen
    Rueckgabe = Korrektes Datum als String
     */
    public static String pruefeDatum() {

        Scanner scan = new Scanner(System.in);

        String sDatumFormat= "dd.MM.yyyy";
        boolean korrekteEingabe = false;
        String datum = "";
        Date date;

        do {
            try {
                //Einlesen des Datums
                datum = scan.next();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sDatumFormat);
                simpleDateFormat.setLenient(false);
                //Pruefen ob das korrekte Format und ein reales Datum eingegeben wurde
                date = simpleDateFormat.parse(datum);
                //Umformatieren des Datum für die Datenbank
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                datum = simpleDateFormat.format(date);
                korrekteEingabe = true;

            } catch (ParseException e) {
                //Bei falscher Eingabe wird die Schleife wiederholt
                ausgabeMitAbsatz("Ungueltiges Datum oder falsches Format");
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
        Ausgabe eines Textes mit Erwartung einer Eingabe die keine Sonderzeichnen oder Zahlen enthaelt (z.B Namen), ebenfalls wird
        die maximale und minimale Laenge des String geprueft

        Parameter = Ausgabe
        Rueckgabe = Korrektes Eingabe als String
     */
    public static String abfrageMitEingabeString(String abfrage) {
        Scanner scan = new Scanner(System.in);
        boolean korrekteEingabe = false;
        String eingabe = "";
        do {
            ausgabeOhneAbsatz(abfrage);// Ausgabe der Frage

            try {
                eingabe = scan.nextLine();
                // Prüfung ob die Eingabe grösser als 45 Zeichen oder keine Zeichen eingegeben
                if(!(eingabe.length() > 45 || eingabe.isEmpty())) {
                    // Pruefen ob nur Buchstaben eingegeben wurden
                    for (Character zeichen : eingabe.toCharArray()) {
                        if (Character.isAlphabetic(zeichen) || Character.isSpaceChar(zeichen)) {

                        } else {
                            korrekteEingabe = false;
                            ausgabeMitAbsatz("Diese Eingabe darf keine Zahlen und keine Sonderzeichen enthalten!");
                            break;
                        }
                        korrekteEingabe = true;
                    }
                }else{
                    ausgabeMitAbsatz("Ungueltige Eingabe");
                    ausgabeMitAbsatz("Zuviele oder keine Zeichen!");
                    korrekteEingabe = false;
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
        Ausgabe eines Textes mit Erwartung einer Eingabe die keine Sonderzeichnen oder Buchstaben enthaelt

        Parameter = Ausgabe
        Rueckgabe = Korrektes Eingabe als int
     */
    public static int abfrageMitEingabeInt(String ausgabe){

       int eingabe = -1;

        ausgabeOhneAbsatz(ausgabe);
        eingabe = eingabeAufIntegerPruefen();

        return eingabe;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
        Ausgabe eines Textes mit Erwartung einer Eingabe eines Datum im richtigen Format

        Parameter = Ausgabe
        Rueckgabe = Korrektes Eingabe als int
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
        Ausgabe eines Textes mit Erwartung einer Eingabe mit maximal 45 und mindestens 1 Zeichen

        Parameter = Ausgabe -> Die Ausgabe die fuer die Fragestellung ausgegeben wird
        Rueckgabe = Korrektes Eingabe als String
     */
    public static String abfrageMitEingabeFrei45(String ausgabe){

        Scanner scan = new Scanner(System.in);
        String eingabe;
        boolean korrekteEingabe = false;

        do{
            ausgabeOhneAbsatz(ausgabe);
            eingabe = scan.nextLine();

            if(eingabe.length() > 45 || eingabe.isEmpty()){
                ausgabeMitAbsatz("Ungueltige Eingabe");
                ausgabeMitAbsatz("Zuviele oder keine Zeichen!");
            }else{
                korrekteEingabe = true;
            }

        }while(!korrekteEingabe);

       return eingabe;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
        Ausgabe eines Textes mit Erwartung einer Eingabe mit maximal 255 und mindestens 1 Zeichen

        Parameter = Ausgabe -> Die Ausgabe die fuer die Fragestellung ausgegeben wird
        Rueckgabe = Korrektes Eingabe als String
     */
    public static String abfrageMitEingabeFrei255(String ausgabe){

        Scanner scan = new Scanner(System.in);
        String eingabe;
        boolean korrekteEingabe = false;

        do{
            ausgabeOhneAbsatz(ausgabe);
            eingabe = scan.nextLine();

            if(eingabe.length() > 255 || eingabe.isEmpty()){
                ausgabeMitAbsatz("Ungueltige Eingabe");
                ausgabeMitAbsatz("Zuviele oder keine Zeichen!");
            }else{
                korrekteEingabe = true;
            }

        }while(!korrekteEingabe);


        return eingabe;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
        Ausgabe einer Auswahl von Waehrungen mit Erwartung einer korrekten Eingabe

        Parameter = Ausgabe
        Rueckgabe = Auswahl als String
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
          //Bestaetigung ob wirklich beendet werde soll
          bildReinigen("Programm Beenden",2);
          ausgabeMitAbsatz("Sind sie sicher, dass sie das Programm Beenden moechten?");
          ausgabeMitAbsatz("1. Ja");
          ausgabeMitAbsatz("2. Nein");
          String auswahl = scan.next();

          // ueberprueft ob die Eingabe eine Ganzzahl [1-2]
          if(!auswahl.matches("[1-2]")){

              ausgabeMitAbsatz("Bitte geben sie einen gueltigen Wert ein!");
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
        Ausgabe mit einem anschliessenden Absatz equivalent System.out.println
        Bei der Ausgabe wird noch einen abstand zum linken Seitenrand generiert
        Parameter = Ausgabe
     */
        public static void ausgabeMitAbsatz(String ausgabe){
            System.out.println(schriftfarbe + seitenAbstandgenerieren(5) + ausgabe + ANSI_RESET);
        }  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
      Ausgabe ohne einem anschliessenden Absatz equivalent System.out.print
        Bei der Ausgabe wird noch einen abstand zum linken Seitenrand generiert
        Parameter = Ausgabe
     */
     public static void ausgabeOhneAbsatz(String ausgabe){
        System.out.print(schriftfarbe + seitenAbstandgenerieren(5) + ausgabe + ANSI_RESET);
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
    /*
       Methode um das Programm zu Pausieren und mit beliebiger Taste weiterzufahren
     */

    public static void beliebigeTasteDrueckenAnzeigen(){

        ausgabeMitAbsatz("Betaetigen Sie eine beliebige Taste um weiterzufahren");
        new Scanner(System.in).nextLine();
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
       In dieser Methode wird die Titelzeile Gebildet
       Parameter: Text der in der Titelzeile ausgegeben werden soll, kontext -> Auswahl zwischen 1.Haupt- oder 2.Untermenue
     */

    public static void titelZeile(String text, int kontext){

            int vorEinmitten = (120 - text.length()) / 2; // Berechnung der Leerzeichen vor dem Text
            int nachEinmitten = ((120 - text.length()) % 2) + vorEinmitten;// Berechnung Leer zeichen nach dem Text


            StringBuilder vor = new StringBuilder("");// Stringbuilder fuer die Lehrzeichen vor dem Text
            StringBuilder nach = new StringBuilder("");// Stringbuilder fuer die Lehrzeichen nach dem Text

            // Hinzufuegen von Lehrzeichen zum Einmitten des Textes -> vor + (Anzahl benoetigten Leerzeichen * char (' '))
            for (int i = 0; i != vorEinmitten; i++) {
                char lehrzeichenA = ' ';
                vor.append(lehrzeichenA);
            }
            // Hinzufuegen von Lehrzeichen zum Einmitten des Textes -> nach + (Anzahl benoetigten Leerzeichen * char (' '))
            for (int i = 0; i != nachEinmitten; i++) {
                char lehrzeichenB = ' ';
                nach.append(lehrzeichenB);
            }
            //Datum
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("" +"MMM dd HH:mm");

            //Ausgabe der Titelzeile
            if (kontext == 1) {
                text.toString();
                System.out.println(schriftfarbe +"\n" + "                                                                           Angemeldeter Benutzer: " + Benutzerverwaltung.angemeldeterBenutzer +"\t" +dtf.format(LocalDateTime.now()) + ANSI_RESET);
                //Hauptmenue
                System.out.println(schriftfarbe + "<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>" + ANSI_RESET);
                System.out.println(schriftfarbe + "<  " + vor + text + nach + "  >" + ANSI_RESET);
                System.out.println(schriftfarbe + "<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>" + ANSI_RESET);
            }  else if(kontext == 2){
                //Untermenue
                System.out.println(schriftfarbe + "--------------------------------------------------------------------------------------------------------------------------" + ANSI_RESET);
                System.out.println(schriftfarbe + "|" + vor+ text + nach + "|" + ANSI_RESET);
                System.out.println(schriftfarbe + "--------------------------------------------------------------------------------------------------------------------------" + ANSI_RESET);
            }

        }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
       In dieser Methode wird das Farbschema gewählt
       Parameter: FARBSCHEMA Wahl der Schriftfarbe
       Rueckgabewert: FARBSCHEMA
     */
    public static void farbschemaWaehlen(FARBSCHEMA farbschema){

        switch (farbschema){
            case ROT: schriftfarbe = ANSI_ROT; break;
            case BLAU: schriftfarbe = ANSI_BLAU; break;
            case VIOLETT: schriftfarbe = ANSI_VIOLETT;  break;
            case GELB:schriftfarbe = ANSI_GELB;  break;
            case TUERKIS: schriftfarbe = ANSI_TUERKIS;  break;
            case SCHWARZ: schriftfarbe = ANSI_SCHWARZ; break;
            case WEISS: schriftfarbe = ANSI_WEISS;  break;
            case GRUEN: schriftfarbe = ANSI_GRUEN; break;
        }

    }
}








