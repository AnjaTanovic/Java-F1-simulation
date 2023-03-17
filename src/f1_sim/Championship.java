package f1_sim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Anja Tanovic
 */
public class Championship {

    private ArrayList<Driver> drivers;
    private ArrayList<Venue> venues;
    private int numRaces;
    private int lapCounter;

    final int MINOR_MECHANICAL_FAULT = 5;
    final int MAJOR_MECHANICAL_FAULT = 3;
    final int UNRECOVERABLE_MECHANICAL_FAULT = 1;

    Championship(String drivers_txt, String venues_txt, int races) throws IOException {
        this.numRaces = races;
        this.lapCounter = 0;
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
        this.numRaces = races;
    }

    public int getRaces() {
        return numRaces;
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    public ArrayList<Venue> getVenues() {
        return venues;
    }

    String getVenuesName(int i) {
        Venue vn;
        vn = venues.get(i);
        return vn.getVenueName();
    }

    void prepareForTheRace() {
        Driver drv;
        for (int i = 0; i < drivers.size(); i++) {
            drv = drivers.get(i);
            drv.setAccumulatedTime(0);
            drv.setEligibleToRace(true);
        }
    }

    void driveAverageLapTime(int race) {
        Driver drv;
        Venue vn = venues.get(race);
        int averageLapTime = vn.getAverageLapTime();
        for (int i = 0; i < drivers.size(); i++) {
            drv = drivers.get(i);
            if (drv.isEligibleToRace()) {
                drv.setAccumulatedTime(drv.getAccumulatedTime() + averageLapTime);
            }
        }
    }

    void applySpecialSkills() {
        Driver drv;
        int reduced_time = 0;
        RNG rnd;
        lapCounter++;
        for (int i = 0; i < drivers.size(); i++) {
            drv = drivers.get(i);
            if (drv.isEligibleToRace()) {
                if (drv.getSpecialSkill().equals("Overtaking")) {
                    if (lapCounter % 3 == 0) {
                        rnd = new RNG(10, 20 + 1);
                        reduced_time = rnd.getRandomValue();
                    }
                } else if (drv.getSpecialSkill().equals("Cornering")
                        || drv.getSpecialSkill().equals("Braking")) {
                    rnd = new RNG(1, 8 + 1);
                    reduced_time = rnd.getRandomValue();
                }
            }
            drv.setAccumulatedTime(drv.getAccumulatedTime() - reduced_time);
        }
    }

    void checkMechanicalProblem() {
        System.out.println("Checking for mechanical problems.");
        Driver drv;
        RNG rnd;
        //1, 2-4, 5-9 numbers are problems
        int problemProbability = 0;
        for (int i = 0; i < drivers.size(); i++) {
            drv = drivers.get(i);
            if (drv.isEligibleToRace()) {
                rnd = new RNG(1, 100 + 1); //100 possible numbers -> probability
                problemProbability = rnd.getRandomValue();
                if (problemProbability == UNRECOVERABLE_MECHANICAL_FAULT) {
                    System.out.println("Driver " + drv.getName() + " has unrecoverable "
                            + "mechanical fault. He will no longer compete in this race.");
                    drv.setEligibleToRace(false);
                } else if ((problemProbability > UNRECOVERABLE_MECHANICAL_FAULT)
                        && (problemProbability <= (UNRECOVERABLE_MECHANICAL_FAULT + MAJOR_MECHANICAL_FAULT))) {
                    System.out.println("Driver " + drv.getName() + " had major mechanical fault.");
                    drv.setAccumulatedTime(drv.getAccumulatedTime() + 120);
                } else if ((problemProbability > (UNRECOVERABLE_MECHANICAL_FAULT + MAJOR_MECHANICAL_FAULT))
                        && (problemProbability <= (UNRECOVERABLE_MECHANICAL_FAULT + MAJOR_MECHANICAL_FAULT + MINOR_MECHANICAL_FAULT))) {
                    System.out.println("Driver " + drv.getName() + " had minor mechanical fault.");
                    drv.setAccumulatedTime(drv.getAccumulatedTime() + 20);
                }
            }
        }
    }

    void printLeader(int lap) {

    }

    void printWinnersAfterRace(String venueName) {
        System.out.println("After race on " + venueName + " winners are:");
        for (int i = 0; i < 4; i++) {
            System.out.println((i+1) + ") " + drivers.get(i).getName());
        }
    }

    void printChampion() {
        System.out.println("CHAMPION IS " + drivers.get(0).getName().toUpperCase() + "!!!");
    }

    int getNumberOfVenues() {
        return venues.size();
    }

    boolean isChosenRace(int i) {
        Venue vn;
        vn = venues.get(i);
        return vn.getChosenRace();
    }

    void setChosenRace(int chosenRace) {
        Venue vn;
        vn = venues.get(chosenRace);
        vn.setChosenRace(true);
    }

    int getNumberOfLaps(int venue) {
        Venue vn;
        vn = venues.get(venue);
        return vn.getNumberOfLaps();
    }

    void sortDriversByTime() {
        Collections.sort(drivers);

        Driver drv;
        for (int i = 0; i < 4; i++) {
            drv = drivers.get(i);
            drv.setRanking(i + 1);
            switch (i + 1) {
                case 1 ->
                    drv.setAccumulatedPoints(drv.getAccumulatedPoints() + 8);
                case 2 ->
                    drv.setAccumulatedPoints(drv.getAccumulatedPoints() + 5);
                case 3 ->
                    drv.setAccumulatedPoints(drv.getAccumulatedPoints() + 3);
                default ->
                    drv.setAccumulatedPoints(drv.getAccumulatedPoints() + 1);
            }
        }
        for (int i = 4; i < drivers.size(); i++) {
            drv = drivers.get(i);
            drv.setRanking(5);
        }

        System.out.println("Sorted drivers:");
        for (Driver driver : drivers) {
            System.out.println("Driver " + driver.getName() + ", "
                    + driver.getAccumulatedTime() + " seconds.");
        }
    }

    void sortDriversByPoints() {
        for (Driver driver : drivers) {
            driver.setAccumulatedTime(driver.getAccumulatedPoints());
        }
        Collections.sort(drivers);
        Collections.reverse(drivers);
        System.out.println("Sorted drivers:");
        for (Driver driver : drivers) {
            System.out.println("Driver " + driver.getName() + ", "
                    + driver.getAccumulatedPoints() + " points.");
        }
    }
}
