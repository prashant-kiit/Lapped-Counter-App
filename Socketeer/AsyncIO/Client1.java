package Socketeer.AsyncIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client1 {
    public static void main(String[] args) throws InterruptedException {
        final String serverAddress = "localhost";
        final int portNumber = 8080;

        try {
            int i = 0;

            while (true) {
                Thread.sleep(2500);
                Socket socket = new Socket(serverAddress, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("Hello from Client1! " + i);
                String serverResponse = in.readLine();
                System.out.println("Server says: " + serverResponse);

                socket.close();
                out.close();
                in.close();

                i++;                
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverAddress);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverAddress);
        }
    }
}
