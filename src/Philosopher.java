package com.moti;

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
    private final Random _rand;
    private final int _num;
    // First chopstick to pick up
    private final Chopstick _first;
    // Second chopstick to pick up
    private final Chopstick _second;
    private JLabel _icon;

    /**
     * Initialize the philosopher
     * @param num philosopher number
     * @param first first chopstick to pick up
     * @param second second chopstick to pick up
     */
    public Philosopher(int num, Chopstick first, Chopstick second) {
        _rand = new Random();
        _num = num;
        _first = first;
        _second = second;
        _icon = new JLabel(new ImageIcon(THINK_IMAGE));
        add(_icon);
    }

    /**
     * Set the philosopher as eating
     */
    private void _eat() {
        _icon.setIcon(new ImageIcon(EAT_GIF));

        try {
            Thread.sleep(_rand.nextInt(EAT_TIME_MS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the philosopher as thinking
     */
    private void _think() {
        _icon.setIcon(new ImageIcon(THINK_IMAGE));

        try {
            Thread.sleep(_rand.nextInt(THINK_TIME_MS));
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
            _first.pickUp();
            _second.pickUp();

            _eat();

            // Put down chopsticks
            _second.putDown();
            _first.putDown();

            _think();
        }
    }
}
