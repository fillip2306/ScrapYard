public class ElectricCar extends Vehicles {

    private int batteriKapasitet;
    private int ladeNiva;

    public ElectricCar(int kjoretoyID, int skraphandelID, String merke, String modell, String regNr, String arsmodell, String chassisNr, Boolean kjorbart, int salgbareHjul, int batteriKapasitet, int ladeNiva) {
        super(kjoretoyID, skraphandelID, merke, modell, regNr, arsmodell, chassisNr, kjorbart, salgbareHjul);
        this.batteriKapasitet = batteriKapasitet;
        this.ladeNiva = ladeNiva;
    }

    public int getBatteriKapasitet() {
        return batteriKapasitet;
    }

    public int getLadeNiva() {
        return ladeNiva;
    }

    @Override
    public String toString() {
        return "electricCar{" +
                "batteriKapasitet=" + batteriKapasitet +
                ", ladeNiva=" + ladeNiva +
                "} " + super.toString();
    }
}
