package PraesentationSchicht;

public class Animation {

    public Animation(){
        kursverwaltungAnimation();
    }

    void startProzedere(){




    }

    void kursverwaltungAnimation(){
        int dauer = 800;

        BefehlsZeilenSchnittstelle.bildReinigen("", 3);

        System.out.println("****************************************************************************************************************");
        BefehlsZeilenSchnittstelle.verzoegerung(dauer);
        System.out.println(" ##  ## ##  ## ####### ####### ### ### ####### ####### #     #    #    ##      ######## ##  ## ###  ## ######## ");
        BefehlsZeilenSchnittstelle.verzoegerung(dauer);
        System.out.println(" #####  ##  ## ###  ## ###     ### ### ###     ###  ## #  #  #  #####  ##         ##    ##  ## ###  ## ###      ");
        BefehlsZeilenSchnittstelle.verzoegerung(dauer);
        System.out.println(" ###    ##  ## #####   ####### ### ### ####### #####   #  #  # ##   ## ##         ##    ##  ## ####### ### #### ");
        BefehlsZeilenSchnittstelle.verzoegerung(dauer);
        System.out.println(" #####  ##  ## ##  ###    ####  #####  ###     ##  ### # ### # ####### ##         ##    ##  ## #  #### ###  ### ");
        BefehlsZeilenSchnittstelle.verzoegerung(dauer);
        System.out.println(" ##  ## ###### ##   ## #######   ###   ####### ##   ## ####### ##   ## #######    ##    ###### #  #### ######## ");
        BefehlsZeilenSchnittstelle.verzoegerung(dauer);
        System.out.println("****************************************************************************************************************");
        BefehlsZeilenSchnittstelle.verzoegerung(dauer);
        System.out.println("                                                                              By Bruno Thurnherr & Klaus Buderer");







        ;
    }


}
