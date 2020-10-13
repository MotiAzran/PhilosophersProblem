package com.moti;

import javax.swing.*;
import java.util.Random;

public class Philosopher extends JPanel implements Runnable {

    private final String EAT_GIF = "eat.gif";
    private final String THINK_IMAGE = "think.png";
    private final Random _rand;
    private final int _num;
    private final Chopstick _first;
    private final Chopstick _second;
    private JLabel _icon;

    public Philosopher(int num, Chopstick first, Chopstick second) {

        _rand = new Random();
        _num = num;
        _first = first;
        _second = second;
        _icon = new JLabel(new ImageIcon(THINK_IMAGE));
        add(_icon);
    }

    private void _eat() {

        _icon.setIcon(new ImageIcon(EAT_GIF));

        try {
            Thread.sleep(_rand.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void _think() {

        _icon.setIcon(new ImageIcon(THINK_IMAGE));

        try {
            Thread.sleep(_rand.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            // Pick up chopsticks
            _first.pick_up();
            _second.pick_up();

            _eat();

            // Put down chopsticks
            _second.put_down();
            _first.put_down();

            _think();
        }
    }
}
