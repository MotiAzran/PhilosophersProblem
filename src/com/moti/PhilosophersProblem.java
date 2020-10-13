package com.moti;

import javax.swing.*;
import java.awt.*;

public class PhilosophersProblem extends JFrame {

    private final int PHILOSOPHERS_COUNT = 5;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final int TABLE_RADIUS = 300;
    private JPanel table;
    private Chopstick[] chopsticks;
    private Philosopher[] philosophers;

    public PhilosophersProblem() {
        super("Philosophers Problem");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init_philosophers();

        table = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                int x_center = getWidth() / 2;
                int y_center = getHeight() / 2;
                int min_coordinate = Math.min(x_center, y_center);
                int radius = 4 * min_coordinate / 5;

                g.fillOval(x_center - radius, y_center - radius, 2 * radius, 2 * radius);

                for (int i = 0; i < PHILOSOPHERS_COUNT; ++i) {
                    double place = 2 * Math.PI * i / PHILOSOPHERS_COUNT;
                    int x = (int) Math.round(x_center + radius * Math.cos(place));
                    int y = (int) Math.round(y_center + radius * Math.sin(place));
                    philosophers[i].setBounds(x - 50, y - 50, 100, 100);
                }

            }
        };

        add(table);

        for (int i = 0; i < PHILOSOPHERS_COUNT; ++i) {
            (new Thread(philosophers[i])).start();
        }
    }

    private void init_philosophers() {
        chopsticks = new Chopstick[PHILOSOPHERS_COUNT];
        philosophers = new Philosopher[PHILOSOPHERS_COUNT];

        // Initialize chopsticks
        for (int i = 0; i < chopsticks.length; ++i) {
            chopsticks[i] = new Chopstick(i % 2);
        }

        int x_center = getWidth() / 2;
        int y_center = getHeight() / 2;
        int min_coordinate = Math.min(x_center, y_center);
        int radius = 4 * min_coordinate / 5;

        // Initialize and run philosophers
        for (int i = 0; i < philosophers.length; ++i) {
            // Get first and second chopsticks for the philosopher
            Chopstick first = chopsticks[i];
            Chopstick second = chopsticks[(i + 1) % chopsticks.length];
            if (first.get_num() > second.get_num()) {
                first = chopsticks[(i + 1) % chopsticks.length];
                second = chopsticks[i];
            }

            // Run the philosopher
            philosophers[i] = new Philosopher(i, first, second);

            double place = 2 * Math.PI * i / PHILOSOPHERS_COUNT;
            int x = (int) Math.round(x_center + radius * Math.cos(place));
            int y = (int) Math.round(y_center + radius * Math.sin(place));
            philosophers[i].setBounds(x - 50, y - 50, 100, 100);
            add(philosophers[i]);
        }
    }
}
