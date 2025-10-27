public abstract class Vehicles {

    private int kjoretoyID;
    private int skraphandelID;
    private String merke;
    private String modell;
    private String arsmodell;
    private String regNr;
    private String chassisNr;
    private Boolean kjorbart;
    private int salgbareHjul;

    public Vehicles(int kjoretoyID, int skraphandelID, String merke, String modell, String regNr, String arsmodell, String chassisNr, Boolean kjorbart, int salgbareHjul) {
        this.kjoretoyID = kjoretoyID;
        this.skraphandelID = skraphandelID;
        this.merke = merke;
        this.modell = modell;
        this.regNr = regNr;
        this.arsmodell = arsmodell;
        this.chassisNr = chassisNr;
        this.kjorbart = kjorbart;
        this.salgbareHjul = salgbareHjul;
    }

    public int getKjoretoyID() {
        return kjoretoyID;
    }

    public int getSkraphandelID() {
        return skraphandelID;
    }

    public String getMerke() {
        return merke;
    }

    public String getModell() {
        return modell;
    }

    public String getArsmodell() {
        return arsmodell;
    }

    public String getRegNr() {
        return regNr;
    }

    public String getChassisNr() {
        return chassisNr;
    }

    public Boolean getKjorbart() {
        return kjorbart;
    }

    public int getSalgbareHjul() {
        return salgbareHjul;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "kjoretoyID=" + kjoretoyID +
                ", skraphandelID=" + skraphandelID +
                ", merke='" + merke + '\'' +
                ", modell='" + modell + '\'' +
                ", arsmodell='" + arsmodell + '\'' +
                ", regNr='" + regNr + '\'' +
                ", chassisNr='" + chassisNr + '\'' +
                ", kjorbart=" + kjorbart +
                ", salgbareHjul=" + salgbareHjul +
                '}';
    }
}
