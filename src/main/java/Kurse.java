public class Kurse {

    String [] unterMenue = {"Kurse", "1. Kurse anlegen", "2. Kurse auslesen", "3. Hauptmenue"};


    Kurse(){
    untermenueAnzeigen();

    }

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    System.out.println("Kurse anlegen");
                    break;
                case 2:
                    System.out.println("Kurse auslesen");
                    break;
                case 3:
                    //zurück ins Hauptmenü;
                    System.out.println("Hauptmenü");
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }


}
