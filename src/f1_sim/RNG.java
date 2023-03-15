package f1_sim;

import java.util.Random;

/**
 *
 * @author Anja Tanovic
 */
public class RNG {

    private int minimumValue;
    private int maximumValue;
    private Random rnd;

    public RNG() {
        rnd = new Random();
        this.minimumValue = 0;
        this.maximumValue = 100;
    }

    public RNG(int minimumValue, int maximumValue) {
        rnd = new Random();
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }

    public void setMinimumValue(int minimumValue) {
        this.minimumValue = minimumValue;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public int getRandomValue() {
        int rand_range = rnd.nextInt(this.maximumValue - this.minimumValue);
        return rand_range + this.minimumValue;
    }
}
