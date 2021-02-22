package DatenSchicht;

import Logik.Mitarbeiter.MitarbeiterBescheinigung;

import java.util.List;

public interface DatenLogikMitarbeiter extends DatenLogik {

   void mitarbeiterBescheinigungAnlegen(MitarbeiterBescheinigung mitarbeiterBescheinigung, MitarbeiterBescheinigung.kontextAnlegen kontext);

   List zertifikatVerlaengernListe(int mitarbeiterId);

   String nummerAufExistenzPruefen(int mitarbeiterId);

   void zertifikatVerlaengernSpeichern(MitarbeiterBescheinigung mitarbeiterBescheinigung);

   String kursZuweisungQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung);

   String zertifikatZuweisungQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung);
}
