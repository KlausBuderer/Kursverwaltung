public class Mitarbeiter {


    String [] unterMenue = {"Mitarbeitermenü", "1. Mitarbeiter anlegen", "2. Mitarbeiter anlegen Massenimport",
            "3. Kurs an Mitarbeiter zuordnen", "4. Mitarbeiter neue Zertifikate zuordnen", "5. Zertifikate von Mitarbeiter erneuern",
            "6. Mitarbeiter Informationen auslesen", "7. Mitarbeiter aendern", "8. Hauptmenue"};


    Mitarbeiter(){
        untermenueAnzeigen();
    }

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = false;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    System.out.println(unterMenue[1]);
                    break;
                case 2:
                    System.out.println(unterMenue[2]);
                    break;
                case 3:
                    System.out.println(unterMenue[3]);
                    break;
                case 4:
                    System.out.println(unterMenue[4]);
                    break;
                case 5:
                    System.out.println(unterMenue[5]);
                    break;
                case 6:
                    System.out.println(unterMenue[6]);
                    break;
                case 7:
                    System.out.println(unterMenue[7]);
                    break;
                case 8:
                    System.out.println(unterMenue[8]);
                    gueltigeEingabe = true;
                    break;
                default:
                    gueltigeEingabe = false;
            }
        }while(!gueltigeEingabe);
    }

}
