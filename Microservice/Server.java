package Microservice;

public class Server extends Thread{
    @Override
    public void run() {
        System.out.println("Server has started...");
        new Controller().listening();
    }
}

class Controller {
    int loopState = 1;
    public void listening() {
        System.out.println("Server is listening...");
        while (true) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (loopState == 1) {
                System.out.println("Server looping started...");
                System.out.println("Press Anything to start Client");
                loopState = 0;
            }

            if (Broker.retrieve().equals("")) {
                System.out.println("Server has received ON message. Processor will start...");
                Processor processor = new Processor();
                processor.start();
                Broker.feed("INACTIVE");
            }

            if (Broker.retrieve().equals(" ")) {
                System.out.println("Server has received OFF message. Termination will start...");
                Broker.terminate();
                Broker.feed("INACTIVE");
            }
        }
    }
}
