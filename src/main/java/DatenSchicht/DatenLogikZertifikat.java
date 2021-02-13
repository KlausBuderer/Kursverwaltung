package DatenSchicht;

import Logik.Zertifikate.Zertifikate;

public interface DatenLogikZertifikat extends DatenLogik{

    boolean datenAnlegen(Zertifikate zertifikat);

    void zertifikatLoeschen(int id);

    void datenMutation(Zertifikate zertifikat);



}
