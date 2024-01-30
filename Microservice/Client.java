package Microservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client extends Thread {

    @Override
    public void run() {
        System.out.println("Client has started...");
        String button = "INACTIVE";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Press Enter to ON or Press Space to OFF");

            try {
                button = reader.readLine();
                if (!button.equals("")) {
                    throw new Exception("Wrong Input. Should have pressed Enter to ON");
                } else {
                    Broker.feed(button);

                    while (true) {
                        try {
                            button = reader.readLine();
                            if (!button.equals(" ")) {
                                throw new Exception("Wrong Input. Should have pressed Space to OFF");
                            } else {
                                Broker.feed(button);
                                System.out.println("Client is listening to Processor");
                                listenTerminateNoteFromProcessor();
                                System.out.println("Client has listened to Processor");
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void listenTerminateNoteFromProcessor() {
        while (true) {
            System.out.print("");
            if (Broker.getTerminateNoteToClient() == 1) {
                System.out.println("Termination Done Note Received by Client");
                Broker.resetTerminateNoteToClient();
                break;
            }
        }
    }
}
