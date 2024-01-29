package Checker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Checker4 {
    public static void main(String[] args) {
        Switcher switcher = new Switcher();
        Lopper lopper = new Lopper(switcher);
        lopper.start();
        Terminator terminator = new Terminator(switcher);
        terminator.start();
    }
}

class Switcher {
    private volatile int button = 1;

    public int getButton() {
        return button;
    }

    public void pressOff() {
        button = 0;
    }
}

class Lopper extends Thread {
    private Switcher switcher;

    public Lopper(Switcher switcher) {
        this.switcher = switcher;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 50000) {
            System.out.println("Loop goes on..... " + i);
            if (switcher.getButton() == 0) {
                break;
            }
            i++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Loop says Bye " + i);
    }
}

class Terminator extends Thread {
    private Switcher switcher;

    public Terminator(Switcher switcher) {
        this.switcher = switcher;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Press button to switch off Loop");
            reader.readLine();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }

        switcher.pressOff();
    }
}