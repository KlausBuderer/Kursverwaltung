public class Administratives {


    String[] unterMenue = {"Menü für Administratives", "1. Kostenstelle anlegen", "2. Budget erfassen pro Kostenstelle", "3. Oranisation anlegen", "4. Organisation aendern", "5. Hauptmenü"};

    //Konstruktor
    Administratives(){
    untermenueAnzeigen();

    }


    public void untermenueAnzeigen(){

        boolean gueltigeEingabe = true;

        do {

            switch (BefehlsZeilenSchnittstelle.unterMenue(unterMenue)) {
                case 1:
                    System.out.println("kostenstelleAnlegen()");
                    break;
                case 2:
                    System.out.println("budgetErfassen");
                    break;
                case 3:
                    System.out.println("organisationAnlegen");
                    break;
                case 4:
                    System.out.println("organisationAendern");
                    break;
                case 5:
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
