package Util.Audio;

import java.awt.*;

public class Beep implements Runnable {

    private int WAIT = 900;
    private int COUNT = 1;

    public Beep() {
        WAIT = 900;
        COUNT = 1;
    }

    public Beep(int wait, int count) {
        WAIT = wait;
        COUNT = count;
    }

    private void beep()  {
        try {
            Toolkit.getDefaultToolkit().beep();
        } catch (Exception e) { }
    }

    public void run() {
        try {
            for (int i = 0; i < COUNT; i++) {
                beep();
                Thread.sleep(WAIT);
            }
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }

}
