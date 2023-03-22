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
            if (name_rank_skill.length != 3) {
                System.out.println("Text document for driver data is not in correct form!");
                System.exit(0);
            }
            Driver drv = new Driver(name_rank_skill[0], Integer.parseInt(name_rank_skill[1]), name_rank_skill[2]);
            drivers.add(drv);
        }
        //Read venue info
        BufferedReader venues_in = new BufferedReader(new FileReader(venues_txt));
        while ((readString = venues_in.readLine()) != null) {
            String[] name_laps_time_rain = readString.split(",");
            if (name_laps_time_rain.length != 4) {
                System.out.println("Text document for vanue data is not in correct form!");
                System.exit(0);
            }
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
        System.out.println("Preparing for race...");
        Driver drv;
        for (int i = 0; i < drivers.size(); i++) {
            drv = drivers.get(i);
            drv.setEligibleToRace(true);
            drv.setWetWeatherPneumatics(false);

            if (drv.getRanking() == 1) {
                drv.setAccumulatedTime(0);
            } else if (drv.getRanking() == 2) {
                drv.setAccumulatedTime(3);
            } else if (drv.getRanking() == 3) {
                drv.setAccumulatedTime(5);
            } else if (drv.getRanking() == 4) {
                drv.setAccumulatedTime(7);
            } else {
                drv.setAccumulatedTime(10);
            }
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
        System.out.println("Applying special skills...");
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
                        System.out.println("Driver " + drv.getName() + " reduced his lap time"
                                + " for " + reduced_time + " seconds due to overtaking skill.");
                    }
                } else if (drv.getSpecialSkill().equals("Cornering")
                        || drv.getSpecialSkill().equals("Braking")) {
                    rnd = new RNG(1, 8 + 1);
                    reduced_time = rnd.getRandomValue();
                    System.out.println("Driver " + drv.getName() + " reduced his lap time"
                            + " for " + reduced_time + " seconds due to "
                            + drv.getSpecialSkill().toLowerCase() + " skill.");
                }
            }
            drv.setAccumulatedTime(drv.getAccumulatedTime() - reduced_time);
        }
    }

    void checkMechanicalProblem() {
        System.out.println("Checking for mechanical problems...");
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
                    drv.setAccumulatedTime(1000);
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
        Driver drv;
        drv = drivers.get(0);
        System.out.println("* Leader in the lap number " + (lap + 1) + " is " + drv.getName() + ".");
    }

    void printWinnersAfterRace(String venueName) {
        System.out.println("* After race on " + venueName + " winners are:");
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ") " + drivers.get(i).getName());
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

        System.out.println("Drivers sorted by accumulated time:");
        for (Driver driver : drivers) {
            System.out.println("- Driver " + driver.getName() + ", "
                    + driver.getAccumulatedTime() + " seconds, "
                    + driver.getAccumulatedPoints() + " points.");
        }
    }

    void sortDriversByPoints() {
        for (Driver driver : drivers) {
            driver.setAccumulatedTime(driver.getAccumulatedPoints());
        }
        Collections.sort(drivers);
        Collections.reverse(drivers);
        System.out.println("Drivers sorted by accumulated points:");
        for (Driver driver : drivers) {
            System.out.println("- Driver " + driver.getName() + ", "
                    + driver.getAccumulatedPoints() + " points.");
        }
    }

    void updateAccumulatedPoints() {
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
    }

    void pneumaticsDecision() {
        RNG rnd;
        rnd = new RNG(1, 100 + 1);
        for (Driver driver : drivers) {
            if (rnd.getRandomValue() < 51) {
                driver.setWetWeatherPneumatics(true);
                driver.setAccumulatedTime(driver.getAccumulatedTime() + 10);
                System.out.println("Driver " + driver.getName() + " decided to use "
                        + "pneumatics for wet weather.");
            }
        }
    }

    void checkRainOccurence(int race) {
        Venue vn;
        vn = venues.get(race);
        double chanceOfRain = vn.getChanceOfRain();
        chanceOfRain = chanceOfRain * 100;
        RNG rnd = new RNG(1, 100 + 1);
        if (rnd.getRandomValue() <= chanceOfRain) {
            System.out.println("Rain occurred on venue " + vn.getVenueName() + ".");
            for (Driver driver : drivers) {
                if (!driver.isWetWeatherPneumatics()) {
                    driver.setAccumulatedTime(driver.getAccumulatedTime() + 5);
                    System.out.println("Driver " + driver.getName() + "did not use pneumatics"
                            + " for wet weather, so he had an extra 5 seconds on this lap.");
                }
            }
        }
    }
}
