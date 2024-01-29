package Tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Application has started...");
        Server server = new Server();
        server.start();
        new BufferedReader(new InputStreamReader(System.in)).readLine();
        Client client = new Client();
        client.start();
    }
}
