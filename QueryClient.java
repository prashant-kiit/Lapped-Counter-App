import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class QueryClient {
    public static void main(String[] args) throws InterruptedException {
        final String serverAddress = "localhost";
        final int portNumber = 8081;

        // edit name of session
        try {
            while (true) {
                Socket socket = new Socket(serverAddress, portNumber);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("\nEnter query options: 1-select,2-sessid,3-sessname,4-bydate,5-bytime,6-count");
                String option = read.readLine();
                out.println(Integer.parseInt(option));

                int inputReceived = Integer.parseInt(in.readLine());

                if (inputReceived == 2 || inputReceived == 3 || inputReceived == 6) {
                    System.out.println("One Parameters");
                    String metric1 = read.readLine();
                    out.println(metric1);
                }

                if (inputReceived == 4 || inputReceived == 5) {
                    System.out.println("Two Parameters");
                    String metric1 = read.readLine();
                    out.println(metric1);
                    String metric2 = read.readLine();
                    out.println(metric2);
                }
                String result = in.readLine();
                System.out.println(result);
                out.println("Bye");
                socket.close();
                out.close();
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverAddress);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverAddress);
        }
    }
}
