package Checker;

public class Checker1 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Checker0.mess);

        System.out.println(Checker0.mess);
        Thread.sleep(2000);
        Checker2.mainer();
        System.out.println("Final " + Checker0.mess);
    }
}
