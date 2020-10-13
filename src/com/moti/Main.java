package com.moti;


import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        PhilosophersProblem window = new PhilosophersProblem();

        window.setVisible(true);
    }

    public static void dine(int n) {
        Chopstick[] chopsticks = new Chopstick[n];
        Philosopher[] philosophers = new Philosopher[n];

        // Initialize chopsticks
        for (int i = 0; i < chopsticks.length; ++i) {
            chopsticks[i] = new Chopstick(i % 2);
        }

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
            (new Thread(philosophers[i])).start();
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
