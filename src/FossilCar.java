public class FossilCar extends Vehicles {

    private String typeDrivstoff;
    private int drivstoffMengde;

    public FossilCar(int kjoretoyID, int skraphandelID, String merke, String modell, String regNr, String arsmodell, String chassisNr, Boolean kjorbart, int salgbareHjul, String typeDrivstoff, int drivstoffMengde) {
        super(kjoretoyID, skraphandelID, merke, modell, regNr, arsmodell, chassisNr, kjorbart, salgbareHjul);
        this.typeDrivstoff = typeDrivstoff;
        this.drivstoffMengde = drivstoffMengde;
    }

    public String getTypeDrivstoff() {
        return typeDrivstoff;
    }

    public int getDrivstoffMengde() {
        return drivstoffMengde;
    }

    @Override
    public String toString() {
        return "fossilCar{" +
                "drivstoffMengde=" + drivstoffMengde +
                ", typeDrivstoff='" + typeDrivstoff + '\'' +
                "} " + super.toString();
    }
}
