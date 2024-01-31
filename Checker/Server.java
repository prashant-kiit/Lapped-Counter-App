package Checker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Server
 */
public class Server {
    static int fan = 0;
    static int bulb = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("Server Started!!!");
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.start();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = read.readLine();
            if (input.equals("f")) {
                Server.fan = 1;
                // System.out.println(Server.state);
                System.out.println("Fan On Message Send");
                input = "";
            }
            if (input.equals("fo")) {
                Server.fan = -1;
                // System.out.println(Server.state);
                System.out.println("Fan off Message Send");
                input = "";
            }
            if (input.equals("b")) {
                Server.bulb = 1;
                // System.out.println(Server.state);
                System.out.println("Bulb on Message Send");
                input = "";
            }
            if (input.equals("bo")) {
                Server.bulb = -1;
                // System.out.println(Server.state);
                System.out.println("Bulb off Message Send");
                input = "";
            }
        }
    }
}

class Dispatcher extends Thread {
    @Override
    public void run() {
        System.out.println("Server started!!!");    
        System.out.println("Give Input!!!");
        Fan fanobj = null;
        Bulb bulb = null;
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Server.fan == 1) {
                fanobj = new Fan();
                fanobj.start();
                Server.fan = 0;
            }
            if (Server.fan == -1) {
                fanobj.stopperFan = 1;
                Server.fan = 0;
            }
            if (Server.bulb == 1) {
                bulb = new Bulb();
                bulb.start();
                Server.bulb = 0;
            }
            if (Server.bulb == -1) {
                bulb.stopperBulb = 1;
                Server.bulb = 0;
            }
        }
    }
}

class Fan extends Thread {
    public int stopperFan = 0;

    @Override
    public void run() {
        System.out.println("Fan Started!!");
        while (true) {
            System.out.println("Fan Running!!!");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stopperFan == 1) {
                System.out.println("Fan Stopped!!!");
                break;
            }
        }
    }
}

class Bulb extends Thread {
    public int stopperBulb = 0;

    @Override
    public void run() {
        System.out.println("Bulb Started!!");
        while (true) {
            System.out.println("Bulb Shinning!!!");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stopperBulb == 1) {
                System.out.println("Bulb Stopped!!!");
                break;
            }
        }
    }
}