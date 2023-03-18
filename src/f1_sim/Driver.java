package f1_sim;

/**
 *
 * @author Anja Tanovic
 */
public class Driver implements Comparable<Driver>{

    private String name;
    private int ranking;
    private String specialSkill;
    private boolean eligibleToRace;
    private int accumulatedTime;
    private int accumulatedPoints;
    private boolean wetWeatherPneumatics;

    public Driver() {
        this.name = "Driver";
        this.ranking = 0;
        this.specialSkill = "Cornering";
        this.eligibleToRace = true;
        this.accumulatedTime = 0;
        this.accumulatedPoints = 0;
        this.wetWeatherPneumatics = false;
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
        this.wetWeatherPneumatics = false;
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
    
    public boolean isWetWeatherPneumatics() {
        return wetWeatherPneumatics;
    }

    public void setWetWeatherPneumatics(boolean wetWeatherPneumatics) {
        this.wetWeatherPneumatics = wetWeatherPneumatics;
    }
    
    public void useSpecialSkill(RNG rng) {
        
    }

    @Override
    public int compareTo(Driver o) {
        int greater = 0;
        if (this.accumulatedTime > o.getAccumulatedTime())
            greater = 1;
        else if (this.accumulatedTime < o.getAccumulatedTime())
            greater = -1;
        return greater;
    }
}
