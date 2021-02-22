package DatenSchicht;

import Logik.Auswertungen.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DatenLogikAuswertungen {

    public List storeproduceKurseProAnbieterKostenstellenZeitraum(String datumVon, String datumBis);

    public List storeproduceWeiterbildungAlleMitarbeiterZeitraum(String datumVon, String datumBis);

    public List storeproduceZertifikateAlleMitarbeiter();

    public List storeproduceStammdatenAlleMitarbeiter();

    public List storeproduceKurseAlleMitarbeiter();

    public List storeproduceBudgetAlleKostenstellen();

    public List storeproduceKostenstellenAlle();

    public List storeproduceZertifikateAlleMitarbeiterGueltigkeit(String datumBis);

    public List<KursProMitarbeiter> storeproduceKursproMitarbeiter(int mitarbeiterID);

    public List<ZertifikatProMitarbeiter> storeproduceZertifikatproMitarbeiter(int mitarbeiterID);

    public List<WeiterbildungAlleMitarbeiterZeitraum> ausfuehrenWeiterbildungAlleMitarbeiterZeitraum(ResultSet dbInhalt) throws SQLException;

    public List<KurseProAnbieterKostenstellenZeitraum> ausfuehrenKurseProAnbieterKostenstellenZeitraums(ResultSet dbInhalt) throws SQLException;

    public List<ZertifikateAlleMitarbeiter> ausfuehrenZertifikateAlleMitarbeiter(ResultSet dbInhalt) throws SQLException;

    public List<StammdatenAlleMitarbeiter> ausfuehrenStammdatenAlleMitarbeiter(ResultSet dbInhalt) throws SQLException;

    public List<KurseAlleMitarbeiter> ausfuehrenKurseAlleMitarbeiter(ResultSet dbInhalt) throws SQLException;

    public List<BudgetAlleKostenstellen> ausfuehrenBudgetAlleKostenstellen(ResultSet dbInhalt) throws SQLException;

    public List<KostenstellenAlle> ausfuehrenKostenstellenAlle(ResultSet dbInhalt) throws SQLException;

    public List<ZertifikateAlleMitarbeiterGueltigkeit> ausfuehrenZertifikateAlleMitarbeiterGueltigkeit(ResultSet dbInhalt) throws SQLException;

    public List<KursProMitarbeiter> ausfuehrenKursProMitarbeiter(ResultSet dbInhalt) throws SQLException;

    public List<ZertifikatProMitarbeiter> ausfuehrenZertifikatProMitarbeiter(ResultSet dbInhalt) throws SQLException;

}
