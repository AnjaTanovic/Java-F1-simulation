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

    public Venue() {
        this.venueName = "Venue";
        this.numberOfLaps = 3;
        this.averageLapTime = 70;
        this.chanceOfRain = 0;
    }

    public Venue(String venueName, int numberOfLaps, int averageLapTime, double chanceOfRain) {
        this.venueName = venueName;
        this.numberOfLaps = numberOfLaps;
        this.averageLapTime = averageLapTime;
        this.chanceOfRain = chanceOfRain;
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

}
