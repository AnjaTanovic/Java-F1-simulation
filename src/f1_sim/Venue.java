package f1_sim;

/**
 *
 * @author Anja Tanovic
 */
public class Venue {

    private String venueName;
    private int numberOfLaps;
    private int averageLapTime;
    private double chanceOfRain;
    static private int numberOfVenues;
    private boolean chosenRace;

    public Venue() {
        this.venueName = "Venue";
        this.numberOfLaps = 3;
        this.averageLapTime = 70;
        this.chanceOfRain = 0;
        numberOfVenues++;
        chosenRace = false;
    }

    public Venue(String venueName, int numberOfLaps, int averageLapTime, double chanceOfRain) {
        this.venueName = venueName;
        this.numberOfLaps = numberOfLaps;
        this.averageLapTime = averageLapTime;
        this.chanceOfRain = chanceOfRain;
        numberOfVenues++;
        chosenRace = false;
    }

    public String getVenueName() {
        return venueName;
    }

    public int getAverageLapTime() {
        return averageLapTime;
    }

    public double getChanceOfRain() {
        return chanceOfRain;
    }

    public int getNumberOfLaps() {
        return numberOfLaps;
    }
    
    public int getNumberOfVenues() {
        return numberOfVenues;
    }
    
    public boolean getChosenRace() {
        return chosenRace;
    }

    public void setChosenRace(boolean chosenRace) {
        this.chosenRace = chosenRace;
    }
    
    @Override
    protected void finalize() {
        numberOfVenues--;
    }
}
