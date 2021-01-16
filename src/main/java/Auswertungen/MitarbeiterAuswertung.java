package Auswertungen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MitarbeiterAuswertung {

    private String vorname;
    private String nachname;
    private String abteilungsBezeichnung;
    private String kursCode;
    private String anrede;
    private int id;
    private int personalNr;
    private int idMitarbeiterBescheinigung;
    static int anzahlMitarbeiter;

    public MitarbeiterAuswertung(String vorname, String nachname, String abteilungsBezeichnung, String kursCode, int id, int personalNr, String anrede, int idMitarbeiterBescheinigung) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.abteilungsBezeichnung = abteilungsBezeichnung;
        this.kursCode = kursCode;
        this.id = id;
        this.personalNr = personalNr;
        this.anrede = anrede;
        this.idMitarbeiterBescheinigung = idMitarbeiterBescheinigung;
        anzahlMitarbeiter++;
    }

    public MitarbeiterAuswertung(){

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen einer Liste von Objekten der Klasse Auswertungen.MitarbeiterAuswertung
     */

    public List<MitarbeiterAuswertung> mitarbeiterAuswerten(ResultSet dbInhalt) throws SQLException {

        List<MitarbeiterAuswertung> mitarbeiterAuswertungsliste = new ArrayList<>();
        MitarbeiterAuswertung mitarbeiterAuswertung;


        while (dbInhalt.next()) {

            String anrede = dbInhalt.getString("Anrede");
            int id = dbInhalt.getInt("ID");
            String kursCode = dbInhalt.getString("KursCode");
            String vorname = dbInhalt.getString("Vorname");
            String nachname = dbInhalt.getString("Nachname");
            int personalNummer = dbInhalt.getInt("PersonalNr");
            String abteilungBezeichnung = dbInhalt.getString("Abteilungbezeichnung");
            int idMitarbeiterBescheinigung = dbInhalt.getInt("ID Mitarbeiterbescheinigung");
            mitarbeiterAuswertung = new MitarbeiterAuswertung(vorname,nachname ,abteilungBezeichnung , kursCode,id ,personalNummer,anrede,idMitarbeiterBescheinigung);

            mitarbeiterAuswertungsliste.add(mitarbeiterAuswertung);
        }
        return mitarbeiterAuswertungsliste;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    Methode zum Erstellen einer Liste von Objekter der Klasse Auswertungen.MitarbeiterAuswertung
     */
    public void ausgabeListe(List<?> list){

        System.out.println("MitarbeiterAuswertung");
        System.out.println();
        System.out.println("Anzahl Mitarbeiter: " + MitarbeiterAuswertung.anzahlMitarbeiter);


        for (int i = 0; i < list.size(); i++) {

            System.out.println(list.toString());
        }

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
    To String Methode
     */

    @Override
    public String toString() {
        return   "Anrede = " + anrede +
                ", Vorname = " + vorname +
                ", Nachname = " + nachname +
                ", AbteilungsBezeichnung = " + abteilungsBezeichnung +
                ", KursCode = " + kursCode +
                ", Id = " + id +
                ", Personal Nummer =" + personalNr +
                ", Id MitarbeiterBescheinigung =" + idMitarbeiterBescheinigung;
    }
}

