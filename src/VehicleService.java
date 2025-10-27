import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VehicleService {
    //INSERT statements
    private static final String INSERT_SCRAPYARD_SQL = "INSERT INTO Scrapyard (ScrapyardID, Name, Address, PhoneNumber ) VALUES (?, ?, ?, ?)";
    private static final String INSERT_FOSSIL_SQL = "INSERT INTO FossilCar  (VehicleID, ScrapyardID , Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, FuelType, FuelAmount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ELECTRIC_SQL = "INSERT INTO ElectricCar  (VehicleID, ScrapyardID , Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, BatteryCapacity, ChargeLevel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_MOTORCYCLE_SQL = "INSERT INTO Motorcycle  (VehicleID, ScrapyardID , Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, HasSidecar, EngineCapacity, IsModified, NumberOfWheels) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    // SELECT statements
    private static final String SELECT_SCRAPYARD_SQL = "SELECT ScrapyardID, Name, Address, PhoneNumber FROM Scrapyard";
    private static final String SELECT_FOSSIL_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, FuelType, FuelAmount FROM FossilCar";
    private static final String SELECT_ELECTRIC_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, BatteryCapacity, ChargeLevel FROM ElectricCar";
    private static final String SELECT_MOTORCYCLE_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, HasSidecar, EngineCapacity, IsModified, NumberOfWheels FROM Motorcycle";
    // SUM statement for hvor mye drivstoff som befinner seg i fossilbilene totalt
    private static final String SUM_FUEL_FOSSILCAR_SQL = "SELECT SUM(FuelAmount) AS total FROM FossilCar";
    // SELECT statement for å finne alle kjøretøy som er kjørbare
    private static final String SELECT_DRIVEABLE_FOSSIL_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, FuelType, FuelAmount FROM FossilCar WHERE Driveable = 1";
    private static final String SELECT_DRIVEABLE_ELECTRIC_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, BatteryCapacity, ChargeLevel FROM ElectricCar WHERE Driveable = 1";
    private static final String SELECT_DRIVEABLE_MOTORCYCLE_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, HasSidecar, EngineCapacity, IsModified, NumberOfWheels FROM Motorcycle WHERE Driveable = 1";
    // SELECT statement for å finne alle kjøretøy fra årstall ...
    private static final String SELECT_FOSSILCARS_FROM_YEAR_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, FuelType, FuelAmount FROM FossilCar WHERE YearModel = ?";
    private static final String SELECT_ELECTRICCARS_FROM_YEAR_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, BatteryCapacity, ChargeLevel FROM ElectricCar WHERE YearModel = ?";
    private static final String SELECT_MOTORCYCLES_FROM_YEAR_SQL = "SELECT VehicleID, ScrapyardID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, HasSidecar, EngineCapacity, IsModified, NumberOfWheels FROM Motorcycle WHERE YearModel = ?";


    private final MysqlDataSource carsDB;

    private final File file = new File("vehicles.txt");
    Connection conn = null;

    public VehicleService() {
        carsDB = new MysqlDataSource();
        carsDB.setServerName(PropertiesProvider.PROPS.getProperty("host"));
        carsDB.setPortNumber(Integer.parseInt(PropertiesProvider.PROPS.getProperty("port")));
        carsDB.setDatabaseName(PropertiesProvider.PROPS.getProperty("db_name"));
        carsDB.setUser(PropertiesProvider.PROPS.getProperty("uname"));
        carsDB.setPassword(PropertiesProvider.PROPS.getProperty("pwd"));
    }

    public void hoved() throws Exception {
        Scanner scanner = new Scanner(file);
        addScrapYard(scanner);
        addVehicles(scanner);
        scanner.close();
    }

    public List<ScrapYard> retrieveScrapYard() throws Exception {

        List<ScrapYard> allScrapYards = new ArrayList<>();

        try {

            conn = carsDB.getConnection();

            try(PreparedStatement stmt = conn.prepareStatement(SELECT_SCRAPYARD_SQL);
                ResultSet rs = stmt.executeQuery()
            ) {


                while (rs.next()) {

                    int skraphandelID = rs.getInt("ScrapyardID");
                    String navn = rs.getString("Name");
                    String adresse = rs.getString("Address");
                    String tlfNr = rs.getString("PhoneNumber");

                    ScrapYard scrapYard = new ScrapYard(skraphandelID, navn, adresse, tlfNr);

                    allScrapYards.add(scrapYard);

                }
            }
        } catch (SQLException sqle) {
            System.out.println("Exception caught. Rolling back");
            throw sqle;
        }

        return allScrapYards;

    }

    public List<FossilCar> retrieveFossilCars() throws Exception {

        List<FossilCar> allFossilCars = new ArrayList<>();

        try {

            conn = carsDB.getConnection();

            try(PreparedStatement stmt = conn.prepareStatement(SELECT_FOSSIL_SQL);
                ResultSet rs = stmt.executeQuery()
            ) {


                while (rs.next()) {
                    int kjoretoyID = rs.getInt("VehicleID");
                    int skraphandelID = rs.getInt("ScrapyardID");
                    String merke = rs.getString("Brand");
                    String modell = rs.getString("Model");
                    String arsmodell = rs.getString("YearModel");
                    String regNr = rs.getString("RegistrationNumber");
                    String chassisNr = rs.getString("ChassisNumber");
                    Boolean kjorbart = rs.getBoolean("Driveable");
                    int salgbareHjul = rs.getInt("NumberOfSellableWheels");
                    String typeDrivstoff = rs.getString("FuelType");
                    int drivstoffMengde = rs.getInt("FuelAmount");

                    FossilCar fossilCar = new FossilCar(kjoretoyID, skraphandelID, merke, modell, arsmodell, regNr, chassisNr, kjorbart, salgbareHjul, typeDrivstoff, drivstoffMengde);

                    allFossilCars.add(fossilCar);

                }
            }
        } catch (SQLException sqle) {
            System.out.println("Exception caught. Rolling back");
            throw sqle;
        }

        return allFossilCars;


    }

    public List<ElectricCar> retrieveElectricCars() throws Exception {

        List<ElectricCar> allElectricCars = new ArrayList<>();

        try {

            conn = carsDB.getConnection();

            try(PreparedStatement stmt = conn.prepareStatement(SELECT_ELECTRIC_SQL);
                ResultSet rs = stmt.executeQuery()
            ) {


                while (rs.next()) {
                    int kjoretoyID = rs.getInt("VehicleID");
                    int skraphandelID = rs.getInt("ScrapyardID");
                    String merke = rs.getString("Brand");
                    String modell = rs.getString("Model");
                    String arsmodell = rs.getString("YearModel");
                    String regNr = rs.getString("RegistrationNumber");
                    String chassisNr = rs.getString("ChassisNumber");
                    Boolean kjorbart = rs.getBoolean("Driveable");
                    int salgbareHjul = rs.getInt("NumberOfSellableWheels");

                    int batteriKapasitet = rs.getInt("BatteryCapacity");
                    int ladeNiva = rs.getInt("ChargeLevel");

                    ElectricCar electricCar = new ElectricCar(kjoretoyID, skraphandelID, merke, modell, arsmodell, regNr, chassisNr, kjorbart, salgbareHjul, batteriKapasitet, ladeNiva);

                    allElectricCars.add(electricCar);

                }
            }
        } catch (SQLException sqle) {
            System.out.println("Exception caught. Rolling back");
            throw sqle;
        }

        return allElectricCars;

    }

    public List<MotorCycle> retrieveMotorCycles() throws Exception {

        List<MotorCycle> allMotorCycles = new ArrayList<>();

        try {

            conn = carsDB.getConnection();

            try(PreparedStatement stmt = conn.prepareStatement(SELECT_MOTORCYCLE_SQL);
                ResultSet rs = stmt.executeQuery()
            ) {


                while (rs.next()) {
                    int kjoretoyID = rs.getInt("VehicleID");
                    int skraphandelID = rs.getInt("ScrapyardID");
                    String merke = rs.getString("Brand");
                    String modell = rs.getString("Model");
                    String arsmodell = rs.getString("YearModel");
                    String regNr = rs.getString("RegistrationNumber");
                    String chassisNr = rs.getString("ChassisNumber");
                    Boolean kjorbart = rs.getBoolean("Driveable");
                    int salgbareHjul = rs.getInt("NumberOfSellableWheels");

                    Boolean sidevogn = rs.getBoolean("HasSidecar");
                    int motorkapasitet = rs.getInt("EngineCapacity");
                    Boolean modifisert = rs.getBoolean("IsModified");
                    int antHjul = rs.getInt("NumberOfWheels");

                    MotorCycle motorCycles = new MotorCycle(kjoretoyID, skraphandelID, merke, modell, arsmodell, regNr, chassisNr, kjorbart, salgbareHjul, sidevogn, motorkapasitet, modifisert, antHjul);

                    allMotorCycles.add(motorCycles);

                }
            }
        } catch (SQLException sqle) {
            System.out.println("Exception caught. Rolling back");
            throw sqle;
        }

        return allMotorCycles;
    }


    public void addScrapYard(Scanner scanner) throws Exception{
        try {

            conn = carsDB.getConnection();
            conn.setAutoCommit(false);

            try(PreparedStatement stmt = conn.prepareStatement(INSERT_SCRAPYARD_SQL)) {
                int antScrapYards = Integer.parseInt(scanner.nextLine().trim());
                for (int i = 0; i < antScrapYards; i++) {
                    int scrapyardID = Integer.parseInt(scanner.nextLine());
                    String navn = scanner.nextLine();
                    String adresse = scanner.nextLine();
                    String tlfnr = scanner.nextLine();
                    scanner.nextLine(); // hopper over "-------"

                    stmt.setInt(1, scrapyardID);
                    stmt.setString(2, navn);
                    stmt.setString(3, adresse);
                    stmt.setString(4, tlfnr);
                    stmt.executeUpdate();
                }
                conn.commit();

            }

        } catch (SQLException sqle) {
            System.out.println("Exception caught. Rolling back");
            conn.rollback();
            throw sqle;
        }
    }

    public void addVehicles(Scanner scanner) throws Exception{
        try {
            conn = carsDB.getConnection();
            conn.setAutoCommit(false);

            try (
                    PreparedStatement stmtFossil = conn.prepareStatement(INSERT_FOSSIL_SQL);
                    PreparedStatement stmtElectric = conn.prepareStatement(INSERT_ELECTRIC_SQL);
                    PreparedStatement stmtMotorCycle = conn.prepareStatement(INSERT_MOTORCYCLE_SQL);
            ) {
                int antVehicles = Integer.parseInt(scanner.nextLine().trim());

                for (int i = 0; i < antVehicles; i++) {
                    int kjoretoyID = Integer.parseInt(scanner.nextLine());
                    int skraphandelID = Integer.parseInt(scanner.nextLine());
                    String typeKjoretoy = scanner.nextLine();
                    String merke = scanner.nextLine();
                    String modell = scanner.nextLine();
                    String arsmodell = scanner.nextLine();
                    String regNr = scanner.nextLine();
                    String chassisNr = scanner.nextLine();
                    Boolean kjorbart = Boolean.parseBoolean(scanner.nextLine());
                    int salgbareHjul = Integer.parseInt(scanner.nextLine());

                    switch (typeKjoretoy) {
                        case "FossilCar" -> {
                            String typeDrivstoff = scanner.nextLine();
                            int drivstoffMengde = Integer.parseInt(scanner.nextLine());
                            scanner.nextLine(); // hopper over "-------"

                            Vehicles fossilCar = new FossilCar(kjoretoyID, skraphandelID, merke, modell, arsmodell, regNr, chassisNr, kjorbart, salgbareHjul, typeDrivstoff, drivstoffMengde);

                            stmtFossil.setInt(1, kjoretoyID);
                            stmtFossil.setInt(2, skraphandelID);
                            stmtFossil.setString(3, merke);
                            stmtFossil.setString(4, modell);
                            stmtFossil.setString(5, arsmodell);
                            stmtFossil.setString(6, regNr);
                            stmtFossil.setString(7, chassisNr);
                            stmtFossil.setBoolean(8, kjorbart);
                            stmtFossil.setInt(9, salgbareHjul);
                            stmtFossil.setString(10, typeKjoretoy);
                            stmtFossil.setInt(11, drivstoffMengde);
                            stmtFossil.executeUpdate();
                        }
                        case "ElectricCar" -> {
                            int batteriKapasitet = Integer.parseInt(scanner.nextLine());
                            int ladeNiva = Integer.parseInt(scanner.nextLine());
                            scanner.nextLine(); // hopper over "-------"

                            Vehicles electricCar = new ElectricCar(kjoretoyID, skraphandelID, merke, modell, arsmodell, regNr, chassisNr, kjorbart, salgbareHjul, batteriKapasitet, ladeNiva);

                            stmtElectric.setInt(1, kjoretoyID);
                            stmtElectric.setInt(2, skraphandelID);
                            stmtElectric.setString(3, merke);
                            stmtElectric.setString(4, modell);
                            stmtElectric.setString(5, arsmodell);
                            stmtElectric.setString(6, regNr);
                            stmtElectric.setString(7, chassisNr);
                            stmtElectric.setBoolean(8, kjorbart);
                            stmtElectric.setInt(9, salgbareHjul);
                            stmtElectric.setInt(10, batteriKapasitet);
                            stmtElectric.setInt(11, ladeNiva);
                            stmtElectric.executeUpdate();

                        }
                        case "Motorcycle" -> {
                            Boolean sidevogn = Boolean.parseBoolean(scanner.nextLine());
                            int motorkapasitet = Integer.parseInt(scanner.nextLine());
                            Boolean modifisert = Boolean.parseBoolean(scanner.nextLine());
                            int antHjul = Integer.parseInt(scanner.nextLine());
                            scanner.nextLine(); // hopper over "-------"

                            Vehicles motorCycle = new MotorCycle(kjoretoyID, skraphandelID, merke, modell, arsmodell, regNr, chassisNr, kjorbart, salgbareHjul, sidevogn, motorkapasitet, modifisert, antHjul);

                            stmtMotorCycle.setInt(1, kjoretoyID);
                            stmtMotorCycle.setInt(2, skraphandelID);
                            stmtMotorCycle.setString(3, merke);
                            stmtMotorCycle.setString(4, modell);
                            stmtMotorCycle.setString(5, arsmodell);
                            stmtMotorCycle.setString(6, regNr);
                            stmtMotorCycle.setString(7, chassisNr);
                            stmtMotorCycle.setBoolean(8, kjorbart);
                            stmtMotorCycle.setInt(9, salgbareHjul);

                            stmtMotorCycle.setBoolean(10, sidevogn);
                            stmtMotorCycle.setInt(11, motorkapasitet);
                            stmtMotorCycle.setBoolean(12, modifisert);
                            stmtMotorCycle.setInt(13, antHjul);

                            stmtMotorCycle.executeUpdate();
                        }
                    }
                }

                conn.commit();

            }

        } catch (SQLException sqle) {
            System.out.println("Exception caught. Rolling back");
            conn.rollback();
            throw sqle;
        }
    }

    public List<Vehicles> retrieveDriveableVehicles() throws Exception {

        List<Vehicles> driveableVehicles = new ArrayList<>();

        try (Connection conn = carsDB.getConnection()) {

            // FossilCar
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_DRIVEABLE_FOSSIL_SQL);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicles fossilCar = new FossilCar(
                            rs.getInt("VehicleID"),
                            rs.getInt("ScrapyardID"),
                            rs.getString("Brand"),
                            rs.getString("Model"),
                            rs.getString("RegistrationNumber"),
                            rs.getString("YearModel"),
                            rs.getString("ChassisNumber"),
                            rs.getBoolean("Driveable"),
                            rs.getInt("NumberOfSellableWheels"),
                            rs.getString("FuelType"),
                            rs.getInt("FuelAmount")
                    );
                    driveableVehicles.add(fossilCar);
                }
            }

            // ElectricCar
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_DRIVEABLE_ELECTRIC_SQL);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicles electricCar = new ElectricCar(
                            rs.getInt("VehicleID"),
                            rs.getInt("ScrapyardID"),
                            rs.getString("Brand"),
                            rs.getString("Model"),
                            rs.getString("RegistrationNumber"),
                            rs.getString("YearModel"),
                            rs.getString("ChassisNumber"),
                            rs.getBoolean("Driveable"),
                            rs.getInt("NumberOfSellableWheels"),
                            rs.getInt("BatteryCapacity"),
                            rs.getInt("ChargeLevel")
                    );
                    driveableVehicles.add(electricCar);
                }
            }

            // Motorcycle
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_DRIVEABLE_MOTORCYCLE_SQL);
                ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicles motorCycle = new MotorCycle(
                            rs.getInt("VehicleID"),
                            rs.getInt("ScrapyardID"),
                            rs.getString("Brand"),
                            rs.getString("Model"),
                            rs.getString("RegistrationNumber"),
                            rs.getString("YearModel"),
                            rs.getString("ChassisNumber"),
                            rs.getBoolean("Driveable"),
                            rs.getInt("NumberOfSellableWheels"),
                            rs.getBoolean("HasSidecar"),
                            rs.getInt("EngineCapacity"),
                            rs.getBoolean("IsModified"),
                            rs.getInt("NumberOfWheels")
                    );
                    driveableVehicles.add(motorCycle);
                }
            }

        } catch (SQLException sqle) {
            System.out.println("Exception caught. Rolling back");
            throw sqle;
        }
        return driveableVehicles;
    }


    public int retrieveTotalFuelAmount() throws Exception {
        int total = 0;
        try (Connection conn = carsDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SUM_FUEL_FOSSILCAR_SQL);
             ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) {
                total = rs.getInt("total");
            }
        }
        return total;
    }

    public List<Vehicles> retrieveVehiclesFromYear(int year) throws Exception {
        List<Vehicles> vehicles = new ArrayList<>();

        try (Connection conn = carsDB.getConnection()) {
            // FossilCar
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_FOSSILCARS_FROM_YEAR_SQL)) {
                stmt.setInt(1, year);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Vehicles fossilCar = new FossilCar(
                                rs.getInt("VehicleID"),
                                rs.getInt("ScrapyardID"),
                                rs.getString("Brand"),
                                rs.getString("Model"),
                                rs.getString("RegistrationNumber"),
                                rs.getString("YearModel"),
                                rs.getString("ChassisNumber"),
                                rs.getBoolean("Driveable"),
                                rs.getInt("NumberOfSellableWheels"),
                                rs.getString("FuelType"),
                                rs.getInt("FuelAmount")
                        );
                        vehicles.add(fossilCar);
                    }
                }
            }
            // ElectricCar
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_ELECTRICCARS_FROM_YEAR_SQL)) {
                stmt.setInt(1, year);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Vehicles electricCar = new ElectricCar(
                                rs.getInt("VehicleID"),
                                rs.getInt("ScrapyardID"),
                                rs.getString("Brand"),
                                rs.getString("Model"),
                                rs.getString("RegistrationNumber"),
                                rs.getString("YearModel"),
                                rs.getString("ChassisNumber"),
                                rs.getBoolean("Driveable"),
                                rs.getInt("NumberOfSellableWheels"),
                                rs.getInt("BatteryCapacity"),
                                rs.getInt("ChargeLevel")
                        );
                        vehicles.add(electricCar);
                    }
                }
            }
            // Motorcycle
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_MOTORCYCLES_FROM_YEAR_SQL)) {
                stmt.setInt(1, year);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Vehicles motorCycle = new MotorCycle(
                                rs.getInt("VehicleID"),
                                rs.getInt("ScrapyardID"),
                                rs.getString("Brand"),
                                rs.getString("Model"),
                                rs.getString("RegistrationNumber"),
                                rs.getString("YearModel"),
                                rs.getString("ChassisNumber"),
                                rs.getBoolean("Driveable"),
                                rs.getInt("NumberOfSellableWheels"),
                                rs.getBoolean("HasSidecar"),
                                rs.getInt("EngineCapacity"),
                                rs.getBoolean("IsModified"),
                                rs.getInt("NumberOfWheels")
                        );
                        vehicles.add(motorCycle);
                    }
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Exception caught. Rolling back");
            throw sqle;
        }
        return vehicles;
    }



}
