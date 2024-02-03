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
                System.out.println("Enter query options: 1-select,2-sessid,3-sessname,4-bydate,5-bytime,6-count");
                String message = read.readLine();
                out.println(message);

                int inputReceived = Integer.parseInt(in.readLine());

                if (inputReceived == 2 || inputReceived == 3 || inputReceived == 6) {
                    System.out.println("One Parameters");
                    String message2 = read.readLine();
                    out.println(message2);
                }

                if (inputReceived == 4 || inputReceived == 5) {
                    System.out.println("Two Parameters");
                    String message2 = read.readLine();
                    out.println(message2);
                    String message3 = read.readLine();
                    out.println(message3);
                }

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
