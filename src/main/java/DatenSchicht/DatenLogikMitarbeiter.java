package DatenSchicht;

import Logik.Mitarbeiter.MitarbeiterBescheinigung;

import java.util.HashMap;

public interface DatenLogikMitarbeiter extends DatenLogik {

    public void mitarbeiterBescheinigungAnlegen(MitarbeiterBescheinigung mitarbeiterBescheinigung, MitarbeiterBescheinigung.kontextAnlegen kontext);

    public HashMap zertifikatVerlaengernListe(int mitarbeiterId);

    public String nummerAufExistenzPruefen(int mitarbeiterId);

    public void zertifikatVerlaengernSpeichern(MitarbeiterBescheinigung mitarbeiterBescheinigung);

    public String kursZuweisungQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung);

    public String zertifikatZuweisungQuerry(MitarbeiterBescheinigung mitarbeiterBescheinigung);
}
