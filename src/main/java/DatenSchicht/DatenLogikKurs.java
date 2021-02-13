package DatenSchicht;

import Logik.Kurse.Kurse;

public interface DatenLogikKurs extends DatenLogik {

    boolean datenAnlegen(Kurse kurs);

    void datenMutation(Kurse kurs);

    void kursLoeschen(int Id);


}
