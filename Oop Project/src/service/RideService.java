package service;

import model.Ride;
import java.io.*;
import java.util.ArrayList;

public class RideService {

    private static final String RIDE_FILE = "rides.txt";

    public static void saveRide(Ride ride) {
        try (FileWriter fw = new FileWriter(RIDE_FILE, true)) {
            fw.write(ride.getDetails() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> loadRides() {
        ArrayList<String> rides = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RIDE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                rides.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rides;
    }
}