public class MotorCycle extends Vehicles {

    private Boolean sidevogn;
    private int motorkapasitet;
    private Boolean modifisert;
    private int antHjul;

    public MotorCycle(int kjoretoyID, int skraphandelID, String merke, String modell, String regNr, String arsmodell, String chassisNr, Boolean kjorbart, int salgbareHjul, Boolean sidevogn, int motorkapasitet, Boolean modifisert, int antHjul) {
        super(kjoretoyID, skraphandelID, merke, modell, regNr, arsmodell, chassisNr, kjorbart, salgbareHjul);
        this.sidevogn = sidevogn;
        this.motorkapasitet = motorkapasitet;
        this.modifisert = modifisert;
        this.antHjul = antHjul;
    }

    public Boolean getSidevogn() {
        return sidevogn;
    }

    public int getMotorkapasitet() {
        return motorkapasitet;
    }

    public Boolean getModifisert() {
        return modifisert;
    }

    public int getAntHjul() {
        return antHjul;
    }

    @Override
    public String toString() {
        return "motorCycle{" +
                "sidevogn=" + sidevogn +
                ", motorkapasitet=" + motorkapasitet +
                ", modifisert=" + modifisert +
                ", antHjul=" + antHjul +
                "} " + super.toString();
    }
}
