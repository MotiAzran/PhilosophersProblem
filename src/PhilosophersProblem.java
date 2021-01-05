import javax.swing.*;
import java.awt.*;

/**
 * Implement philosophers problem window
 */
public class PhilosophersProblem extends JFrame {
    private final int PHILOSOPHERS_COUNT = 5;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final int PICTURE_EDGE_SIZE = 100;
    private final Color TABLE_COLOR = new Color(102, 51, 0);
    private JPanel table;
    private Chopstick[] chopsticks;
    private Philosopher[] philosophers;

    /**
     * Initialize window
     */
    public PhilosophersProblem() {
        super("Philosophers Problem");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize philosophers
        initPhilosophers();

        // Create table panel
        table = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(TABLE_COLOR);

                int xCenter = PhilosophersProblem.this.getWidth() / 2;
                int yCenter = PhilosophersProblem.this.getHeight() / 2;
                int radius = getTableRadius();

                g.fillOval(xCenter - radius, yCenter - radius, 2 * radius, 2 * radius);

                // Put philosophers around the table
                for (int i = 0; i < PHILOSOPHERS_COUNT; ++i) {
                    Point philosopherPoint = getPhilosopherCoordinate(i);
                    philosophers[i].setBounds(philosopherPoint.x, philosopherPoint.y, PICTURE_EDGE_SIZE, PICTURE_EDGE_SIZE);
                }

            }
        };

        add(table);

        // start philosophers
        for (int i = 0; i < PHILOSOPHERS_COUNT; ++i) {
            (new Thread(philosophers[i])).start();
        }
    }

    /**
     * Get table radius by frame size
     * @return table radius
     */
    private int getTableRadius() {
        int minCoordinate = Math.min(getWidth(), getHeight()) / 2;

        return 4 * minCoordinate / 6;
    }

    /**
     * Get philosopher coordinate, the
     * function creates an angle with the
     * philosopher index and put the philosopher
     * around the circle by this angle
     * @param philosopherIndex philosopher index
     * @return philosopher calculated coordinate
     */
    private Point getPhilosopherCoordinate(int philosopherIndex) {
        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;
        int radius = getTableRadius();
        double angle = 2 * Math.PI * philosopherIndex / PHILOSOPHERS_COUNT;

        // x = xCenter + r * cos(angle)
        int x = (int) Math.round(xCenter + radius * Math.cos(angle)) - (PICTURE_EDGE_SIZE / 2);

        // y = yCenter + r * sin(angle)
        int y = (int) Math.round(yCenter + radius * Math.sin(angle)) - (PICTURE_EDGE_SIZE / 2);

        return new Point(x, y);
    }

    /**
     * Initialize philosophers with their chopsticks
     */
    private void initPhilosophers() {
        chopsticks = new Chopstick[PHILOSOPHERS_COUNT];
        philosophers = new Philosopher[PHILOSOPHERS_COUNT];

        // Initialize chopsticks
        for (int i = 0; i < chopsticks.length; ++i) {
            chopsticks[i] = new Chopstick(i % 2);
        }

        // Initialize and run philosophers
        for (int i = 0; i < philosophers.length; ++i) {
            // Get first and second chopsticks for the philosopher
            Chopstick first = chopsticks[i];
            Chopstick second = chopsticks[(i + 1) % chopsticks.length];
            if (first.getNum() > second.getNum()) {
                first = chopsticks[(i + 1) % chopsticks.length];
                second = chopsticks[i];
            }

            // Run the philosopher
            philosophers[i] = new Philosopher(i, first, second);

            // Put philosopher around the table
            Point philosopherPoint = getPhilosopherCoordinate(i);
            int x = (int) philosopherPoint.getX();
            int y = (int) philosopherPoint.getY();
            philosophers[i].setBounds(x, y, PICTURE_EDGE_SIZE, PICTURE_EDGE_SIZE);
            add(philosophers[i]);
        }
    }
}
