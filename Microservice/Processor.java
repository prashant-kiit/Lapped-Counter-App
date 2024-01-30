package Microservice;

public class Processor extends Thread {
    @Override
    public void run() {
        while(true) {
            if (Broker.getIsTerminated() == 1) {
                System.out.println("Process terminated...");
                Broker.terminateNoteToClient();
                break;
            }

            System.out.println("\n------------\nFan is spinning...\n--------------\n");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
