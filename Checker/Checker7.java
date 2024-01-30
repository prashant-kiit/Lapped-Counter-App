package Checker;

public class Checker7 {
    public static void main(String[] args) {
        Sample.getInstance().start();
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        Sample.getInstance().start();
    }
}

class Sample extends Thread {
    private Sample() {}
    public static Sample getInstance() {
        // if (sample == null) {
        //     sample = new Sample();
        // }
        // return sample;
        return new Sample();
    }
    public void run() {
        System.out.println(1 + 2);
    }
}
