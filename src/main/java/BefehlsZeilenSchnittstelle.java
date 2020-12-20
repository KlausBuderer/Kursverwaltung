import java.io.IOException;
import java.util.Scanner;

// Utility Klasse für Ausgaben und Eingaben in der Konsole
public final class BefehlsZeilenSchnittstelle {

   static String[] hauptmenuAdmin =  {"Hauptmenü","", "1. Mitarbeiter", "2. Kurse", "3. Zertifikate", "4. Administratives",
            "5. Benutzerverwaltung,", "6. Einstellungen", "Mit welchen Menüpunkt wollen sie weiterfahren?"};


    // Privater Konstruktor um keine Instanzierung zu erlauben
    private BefehlsZeilenSchnittstelle(){

    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
     Anzeige des Hauptmenüs für Benutzer mit Adminrecht

     */

    static void hauptmenueAusgeben() {

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
            for (String menue : hauptmenuAdmin) {
                System.out.println(menue);
            }

            System.out.println();

            System.out.print("Wählen sie das gewünschte (1-6): ");


            auswahlString = scan.next();

            // Überprüft ob die Eingabe eine Ganzzahl [1-(länge des Untermenüs)] ist
            if(!auswahlString.matches("[1-" + (hauptmenuAdmin.length - 1) + "]")){

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
                Administratives administratives = new Administratives();
                break;
            case 5:
                Benutzerverwaltung benutzerverwaltung = new Benutzerverwaltung();
                break;
            case 6:
                Einstellungen einstellungen = new Einstellungen();
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

    static int unterMenue(String[] unterMenue) {

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
            System.out.print("Wählen sie das gewünschte Untermenü (1-" + (unterMenue.length - 1) + "): ");

            //Liest Eingabe als String ein um zu überpfüfen ob die Eingabe gültig ist
            auswahlString = scan.next();

            // Überprüft ob die Eingabe eine Ganzzahl [1-(länge des Untermenüs)] ist
            if(!auswahlString.matches("[1-" + (unterMenue.length - 1) + "]")){

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
    static void bildReinigen() {

        // Reinigt die Konsole

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
    static void verzoegerung(int dauer){
        try {
            Thread.sleep(dauer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    Gibt Daten in einer Tabelle aus
         */
    static void datenAusgeben(String[] kopfzeile,String[] angaben){

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

    static int korrekteEingabebestaetigen() {

        int auswahl = 0;

        System.out.println("Sind sie sicher ob sie alles richtig eingegeben haben?");
        System.out.println("1. Ja, Daten speichern");
        System.out.println("2. Nein, neu beginnen");
        System.out.println("3. Abbrechen");

        auswahl = eingabeMitWertpruefung(4);

        return (auswahl);

    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
    Die Methode eingabeMitWertpruefung prüft ob der eingegebene Wert des Users eine Ganzzahl ist und ob die Eingabe sich
    im Wertebereich des Arrays befindet.

    Parameter: Länge des gültigen Eingabebereiches -> Von 1-99 möglich
    Rückgabe: Die gültige Eingabe als Integer Wert

    */
    static int eingabeMitWertpruefung(int arrayLaenge){

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
                if ((Integer.parseInt(eingabeString) <= (arrayLaenge -1)) && Integer.parseInt(eingabeString) > 0) {

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
}





