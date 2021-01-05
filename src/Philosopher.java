import javax.swing.*;
import java.util.Random;

/**
 * Implement philosopher thread
 * and philosopher image
 */
public class Philosopher extends JPanel implements Runnable {
    private final String EAT_GIF = "eat.gif";
    private final String THINK_IMAGE = "think.png";
    private final int EAT_TIME_MS = 4000;
    private final int THINK_TIME_MS = 3000;
    private final Random rand;
    private final int num;
    // First chopstick to pick up
    private final Chopstick first;
    // Second chopstick to pick up
    private final Chopstick second;
    private JLabel icon;

    /**
     * Initialize the philosopher
     * @param num philosopher number
     * @param first first chopstick to pick up
     * @param second second chopstick to pick up
     */
    public Philosopher(int num, Chopstick first, Chopstick second) {
        rand = new Random();
        this.num = num;
        this.first = first;
        this.second = second;
        icon = new JLabel(new ImageIcon(THINK_IMAGE));
        add(icon);
    }

    /**
     * Set the philosopher as eating
     */
    private void eat() {
        icon.setIcon(new ImageIcon(EAT_GIF));

        try {
            Thread.sleep(rand.nextInt(EAT_TIME_MS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the philosopher as thinking
     */
    private void think() {
        icon.setIcon(new ImageIcon(THINK_IMAGE));

        try {
            Thread.sleep(rand.nextInt(THINK_TIME_MS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implement philosophers actions
     * Pick up two chopsticks, eat, put down chopsticks
     * and then think
     */
    @Override
    public void run() {

        while (true) {
            // Pick up chopsticks
            first.pickUp();
            second.pickUp();

            eat();

            // Put down chopsticks
            second.putDown();
            first.putDown();

            think();
        }
    }
}
