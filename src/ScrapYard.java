public class ScrapYard {

    private int skraphandelID;
    private String navn;
    private String adresse;
    private String tlfNr;

    public ScrapYard(int skraphandelID, String adresse, String navn, String tlfNr) {
        this.skraphandelID = skraphandelID;
        this.adresse = adresse;
        this.navn = navn;
        this.tlfNr = tlfNr;
    }

    public int getSkraphandelID() {
        return skraphandelID;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTlfNr() {
        return tlfNr;
    }

    @Override
    public String toString() {
        return "scrapYard{" +
                "skraphandelID=" + skraphandelID +
                ", navn='" + navn + '\'' +
                ", adresse='" + adresse + '\'' +
                ", tlfNr=" + tlfNr +
                '}';
    }


}
