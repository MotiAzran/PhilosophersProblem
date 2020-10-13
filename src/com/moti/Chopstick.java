package com.moti;

public class Chopstick {

    private int _num;
    private boolean _is_picked_up;

    public Chopstick(int num) {
        _num = num;
        _is_picked_up = false;
    }

    public int get_num() {
        return _num;
    }

    public synchronized void pick_up() {
        while (_is_picked_up) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        _is_picked_up = true;
    }

    public synchronized void put_down() {
        _is_picked_up = false;
        notify();
    }
}
