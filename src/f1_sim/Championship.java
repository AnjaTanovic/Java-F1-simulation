package f1_sim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Anja Tanovic
 */
public class Championship {

    private ArrayList<Driver> drivers;
    private ArrayList<Venue> venues;
    private int num_races;

    final int MINOR_MECHANICAL_FAULT = 5;
    final int MAJOR_MECHANICAL_FAULT = 3;
    final int UNRECOVERABLE_MECHANICAL_FAULT = 1;

    Championship(String drivers_txt, String venues_txt, int races) throws IOException {
        this.num_races = races;
        drivers = new ArrayList<>();
        venues = new ArrayList<>();

        String readString;
        //Read driver info
        BufferedReader drivers_in = new BufferedReader(new FileReader(drivers_txt));
        while ((readString = drivers_in.readLine()) != null) {
            String[] name_rank_skill = readString.split(",");
            Driver drv = new Driver(name_rank_skill[0], Integer.parseInt(name_rank_skill[1]), name_rank_skill[2]);
            drivers.add(drv);
        }
        //Read venue info
        BufferedReader venues_in = new BufferedReader(new FileReader(venues_txt));
        while ((readString = venues_in.readLine()) != null) {
            String[] name_laps_time_rain = readString.split(",");
            Venue vn = new Venue(name_laps_time_rain[0], Integer.parseInt(name_laps_time_rain[1]),
                    Integer.parseInt(name_laps_time_rain[2]), Double.parseDouble(name_laps_time_rain[3]));
            venues.add(vn);
        }
    }

    public void setRaces(int races) {
        this.num_races = races;
    }

    public int getRaces() {
        return num_races;
    }

    void prepareForTheRace() {
        
    }
    
    void driveAverageLapTime() {
        
    }

    void applySpecialSkills() {
        
    }
    
    void checkMechanicalProblem() {
        
    }
    
    void printLeader(int lap) {
        
    }
    
    void printWinnersAfterRace(String venueName) {
        
    }
    
    void printChampion(int numOfRaces) {
        
    }
}
