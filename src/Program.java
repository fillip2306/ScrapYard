import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    private final VehicleService vehicleService;

    public Program(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    public void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 5) {
            System.out.println("--- Meny ---");
            System.out.println("0: Importer data fra fil (advarsel: gir feil hvis data finnes fra før)");
            System.out.println("1: Vis informasjon om alle kjøretøy.");
            System.out.println("2: Vis informasjon om hvor mye drivstoff som befinner seg i fossilbilene totalt.");
            System.out.println("3: Vis informasjon om alle kjøretøy som er kjørbare.");
            System.out.println("4: Vis kjøretøy fra et valgt år (2010 eller nyere).");
            System.out.println("5: Avslutt program");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> importerData();
                case 1 -> alleKjoretoy();
                case 2 -> totalDrivstoffMengde();
                case 3 -> kjorbareKjoretoy();
                case 4 -> kjoretoyFraArstall(scanner);
                case 5 -> System.out.println("Avslutter program");
                default -> System.out.println("Ugyldig valg. Prøv igjen.");
            }
        }
    }

    private void importerData() {
        try {
            Scanner scanner = new Scanner(new File("vehicles.txt"));
            vehicleService.addScrapYard(scanner);
            vehicleService.addVehicles(scanner);
            scanner.close();
            System.out.println("Import fullført.");
        } catch (Exception e) {
            System.out.println("Import feilet: " + e.getMessage());
        }
    }

    private void alleKjoretoy() throws Exception{
        List<Vehicles> alleKjoretoy = new ArrayList<>();
        alleKjoretoy.addAll(vehicleService.retrieveFossilCars());
        alleKjoretoy.addAll(vehicleService.retrieveElectricCars());
        alleKjoretoy.addAll(vehicleService.retrieveMotorCycles());

        for (Vehicles v : alleKjoretoy) {
            System.out.println(v.toString());
        }
    }

    private void totalDrivstoffMengde() {
        try {
            int total = vehicleService.retrieveTotalFuelAmount();
            System.out.println("Totalt drivstoff i alle fossilbiler: " + total + " liter");
        } catch (Exception e) {
            System.out.println("Klarte ikke å hente total drivstoffmengde: " + e.getMessage());
        }
    }

    private void kjorbareKjoretoy() {
        try {
            List<Vehicles> driveableVehicles = vehicleService.retrieveDriveableVehicles();
            if (driveableVehicles.isEmpty()) {
                System.out.println("Ingen kjørbare kjøretøy funnet.");
            } else {
                System.out.println("Kjørbare kjøretøy:");
                for (Vehicles v : driveableVehicles) {
                    System.out.println(v.toString()); // forutsetter at toString() er bra laget
                }
            }
        } catch (Exception e) {
            System.out.println("Feil ved henting av kjørbare kjøretøy: " + e.getMessage());
        }
    }

    private void kjoretoyFraArstall(Scanner scanner) throws Exception {
        try {
            System.out.print("Oppgi et årstall: ");
            int year = scanner.nextInt();

            List<Vehicles> alleVehicles = vehicleService.retrieveVehiclesFromYear(year);

            if (alleVehicles.isEmpty()) {
                System.out.println("Ingen kjørbare kjøretøy funnet.");
            } else {
                System.out.println("Kjørbare kjøretøy:");
                for (Vehicles v : alleVehicles) {
                    System.out.println(v.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Feil ved henting av kjøretøy:" + e.getMessage());
        }
    }


}
