package f1_sim;

/**
 *
 * @author Anja Tanovic
 */
public class Driver {

    private String name;
    private int ranking;
    private String specialSkill;
    private boolean eligibleToRace;
    private int accumulatedTime;
    private int accumulatedPoints;

    public Driver() {
        this.name = "Driver";
        this.ranking = 0;
        this.specialSkill = "Cornering";
        this.eligibleToRace = true;
        this.accumulatedTime = 0;
        this.accumulatedPoints = 0;
    }

    public Driver(String name, int ranking, String specialSkill) {
        this.name = name;
        this.ranking = ranking;
        if (!specialSkill.equals("Braking") && !specialSkill.equals("Cornering")
                && !specialSkill.equals("Overtaking")) {
            this.specialSkill = "Cornering";
            System.out.println("Special skill for driver " + name + " is not defined. "
                    + "Special skill will be set to cornering.");
        }
        else 
            this.specialSkill = specialSkill;
        this.specialSkill = specialSkill;
        this.eligibleToRace = true;
        this.accumulatedTime = 0;
        this.accumulatedPoints = 0;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setEligibleToRace(boolean eligibleToRace) {
        this.eligibleToRace = eligibleToRace;
    }

    public void setAccumulatedTime(int accumulatedTime) {
        this.accumulatedTime = accumulatedTime;
    }

    public void setAccumulatedPoints(int accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }

    public String getName() {
        return name;
    }

    public int getRanking() {
        return ranking;
    }

    public String getSpecialSkill() {
        return specialSkill;
    }

    public boolean isEligibleToRace() {
        return eligibleToRace;
    }

    public int getAccumulatedTime() {
        return accumulatedTime;
    }

    public int getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void useSpecialSkill(RNG rng) {
        
    }
}
