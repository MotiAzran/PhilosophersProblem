package com.moti;

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
    private JPanel _table;
    private Chopstick[] _chopsticks;
    private Philosopher[] _philosophers;

    /**
     * Initialize window
     */
    public PhilosophersProblem() {
        super("Philosophers Problem");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize philosophers
        _initPhilosophers();

        // Create table panel
        _table = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(TABLE_COLOR);

                int xCenter = PhilosophersProblem.this.getWidth() / 2;
                int yCenter = PhilosophersProblem.this.getHeight() / 2;
                int radius = _getTableRadius();

                g.fillOval(xCenter - radius, yCenter - radius, 2 * radius, 2 * radius);

                // Put philosophers around the table
                for (int i = 0; i < PHILOSOPHERS_COUNT; ++i) {
                    Point philosopherPoint = _getPhilosopherCoordinate(i);
                    _philosophers[i].setBounds(philosopherPoint.x, philosopherPoint.y, PICTURE_EDGE_SIZE, PICTURE_EDGE_SIZE);
                }

            }
        };

        add(_table);

        // start philosophers
        for (int i = 0; i < PHILOSOPHERS_COUNT; ++i) {
            (new Thread(_philosophers[i])).start();
        }
    }

    /**
     * Get table radius by frame size
     * @return table radius
     */
    private int _getTableRadius() {
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
    private Point _getPhilosopherCoordinate(int philosopherIndex) {
        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;
        int radius = _getTableRadius();
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
    private void _initPhilosophers() {
        _chopsticks = new Chopstick[PHILOSOPHERS_COUNT];
        _philosophers = new Philosopher[PHILOSOPHERS_COUNT];

        // Initialize chopsticks
        for (int i = 0; i < _chopsticks.length; ++i) {
            _chopsticks[i] = new Chopstick(i % 2);
        }

        // Initialize and run philosophers
        for (int i = 0; i < _philosophers.length; ++i) {
            // Get first and second chopsticks for the philosopher
            Chopstick first = _chopsticks[i];
            Chopstick second = _chopsticks[(i + 1) % _chopsticks.length];
            if (first.getNum() > second.getNum()) {
                first = _chopsticks[(i + 1) % _chopsticks.length];
                second = _chopsticks[i];
            }

            // Run the philosopher
            _philosophers[i] = new Philosopher(i, first, second);

            // Put philosopher around the table
            Point philosopherPoint = _getPhilosopherCoordinate(i);
            int x = (int) philosopherPoint.getX();
            int y = (int) philosopherPoint.getY();
            _philosophers[i].setBounds(x, y, PICTURE_EDGE_SIZE, PICTURE_EDGE_SIZE);
            add(_philosophers[i]);
        }
    }
}
