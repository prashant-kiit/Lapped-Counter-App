package Socketeer.AsyncIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        final int portNumber = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            Runnable servlet = new Servlet(serverSocket, portNumber, i);
            executorService.submit(servlet);
        }
        executorService.shutdown();
        
    }
}

class Servlet implements Runnable {
    private int portNumber = 0;
    private ServerSocket serverSocket = null;
    private int servletId = 0;

    public Servlet(ServerSocket serverSocket, int portNumber, int servletId) {
        this.portNumber = portNumber;
        this.serverSocket = serverSocket;
        this.servletId = servletId;
    }

    @Override
    public void run() {
        try {
            int i = 0;

            while (true) {
                System.out.println(i + " Server is waiting for client connection on port " + portNumber + " Servlet " + servletId);
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected from: " + clientSocket.getInetAddress());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientMessage = in.readLine();
                System.out.println("Client says: " + clientMessage);

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Hello from Server! " + i);

                clientSocket.close();
                out.close();
                in.close();

                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
