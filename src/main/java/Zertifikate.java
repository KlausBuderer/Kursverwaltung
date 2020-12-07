public class Zertifikate {

    String [] unterMenue = {"Zertifikate", "1. Zertifikate anlegen", "2. Zertifikate auslesen", "3. Gültigkeitsdauer von Zertifikate verlängern", "4. Hauptmenü"};



    Zertifikate(){

        untermenueAnzeigen();

    }

    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    System.out.println("Zertifikate anlegen");
                    break;
                case 2:
                    System.out.println("Zertifikate auslesen");
                    break;
                case 3:
                    System.out.println("Gültigkeitsdauer von Zertifikat verlängern");
                    break;
                case 4:
                    //zurück ins Hauptmenü;
                    System.out.println("Hauptmenü");
                    System.out.println("Hauptmenü");
                    gueltigeEingabe = false;
                    break;
                default:
                    gueltigeEingabe = true;
            }
        }while(gueltigeEingabe);
    }

}
