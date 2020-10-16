package com.moti;

/**
 * Represent a chopstick
 * object for the philosophers to pick up
 */
public class Chopstick {
    private int _num;
    private boolean _isPickedUp;

    /**
     * Initialize chopstick
     * @param num chopstick assigned number
     */
    public Chopstick(int num) {
        _num = num;
        _isPickedUp = false;
    }

    /**
     * Get chopstick assigned number
     * @return chopstick assigned number
     */
    public int getNum() {
        return _num;
    }

    /**
     * Pick up the chopstick when it freed
     */
    public synchronized void pickUp() {
        while (_isPickedUp) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        _isPickedUp = true;
    }

    /**
     * Put down the chopstick
     */
    public synchronized void putDown() {
        _isPickedUp = false;
        notify();
    }
}
