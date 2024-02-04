import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class QueryClientPython {
    public static void main(String[] args) throws InterruptedException {
        final String serverAddress = "localhost";
        final int portNumber = 8081;

        try {
                Socket socket = new Socket(serverAddress, portNumber);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String option = args[0];
                out.println(Integer.parseInt(option));

                Integer.parseInt(in.readLine());

                String result = in.readLine();
                System.out.println(result);
                out.println("Bye");
                socket.close();
                out.close();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverAddress);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverAddress);
        }
    }
}
