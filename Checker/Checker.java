package Checker;

public class Checker {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Check.mess);

        System.out.println(Check.mess);
        Thread.sleep(2000);
        Checker2.mainer();
        System.out.println("Final " + Check.mess);
    }
}
