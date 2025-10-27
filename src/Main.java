//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {

            VehicleService vehicleService = new VehicleService();
            Program program = new Program(vehicleService);
            program.run();


        } catch (Exception e) {
            System.err.println("Noe gikk galt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}