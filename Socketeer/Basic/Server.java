package Socketeer.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        final int portNumber = 8081;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            int i = 0;
            while (true) {
                System.out.println(i + " Server is waiting for client connection on port " + portNumber);
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientMessage = in.readLine();
                System.out.println("Client says: " + clientMessage);
                String clientMessage2 = in.readLine();
                System.out.println("Client says again: " + clientMessage2);

                clientSocket.close();
                in.close();

                i++;
            }
        }
    }
}
