package Checker;

public class Checker7 {
    public static void main(String[] args) {
        Sample.getInstance().start();
        Sample.getInstance().start();
    }
}

class Sample extends Thread {
    private Sample() {}
    public static Sample getInstance() {
        return new Sample();
    }
    public void run() {
        System.out.println(1 + 2);
    }
}
