/**
 * Represent a chopstick
 * object for the philosophers to pick up
 */
public class Chopstick {
    private int num;
    private boolean isPickedUp;

    /**
     * Initialize chopstick
     * @param num chopstick assigned number
     */
    public Chopstick(int num) {
        this.num = num;
        isPickedUp = false;
    }

    /**
     * Get chopstick assigned number
     * @return chopstick assigned number
     */
    public int getNum() {
        return num;
    }

    /**
     * Pick up the chopstick when it freed
     */
    public synchronized void pickUp() {
        while (isPickedUp) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isPickedUp = true;
    }

    /**
     * Put down the chopstick
     */
    public synchronized void putDown() {
        isPickedUp = false;
        notify();
    }
}
