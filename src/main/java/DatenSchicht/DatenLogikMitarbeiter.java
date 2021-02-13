package DatenSchicht;

import Logik.Mitarbeiter.Mitarbeiter;
import Logik.Mitarbeiter.MitarbeiterBescheinigung;

import java.util.HashMap;

public interface DatenLogikMitarbeiter extends DatenLogik {

    void mitarbeiterAnlegen(Mitarbeiter mitarbeiter);

    void datenMutation(Mitarbeiter mitarbeiter);

    void mitarbeiterLoeschen(int Id);

    void mitarbeiterBescheinigungAnlegen(MitarbeiterBescheinigung mitarbeiterBescheinigung, MitarbeiterBescheinigung.kontextAnlegen kontext);

    void zertifikatVerlaengernSpeichern(MitarbeiterBescheinigung mitarbeiterBescheinigung);

    HashMap zertifikatVerlaengernListe(int id);
}
