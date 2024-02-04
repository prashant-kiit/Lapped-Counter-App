package Socketeer.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        final String serverAddress = "localhost";
        final int portNumber = 8081;

        try {
            @SuppressWarnings("unused")
            int i = 0;

            while (true) {
                Thread.sleep(2500);
                Socket socket = new Socket(serverAddress, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
                String message = read.readLine();
                out.println(message);
                String message2 = read.readLine();
                out.println(message2);

                socket.close();
                out.close();

                i++;                
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverAddress);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverAddress);
        }
    }
}
