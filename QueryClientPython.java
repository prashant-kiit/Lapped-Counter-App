import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class QueryClientPython {
    private static final String serverAddress = "localhost";
    private static final int portNumber = 8081;
    private static String queryIndex = new String();
    private static Map<String, String> message = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {

        try {
                Socket socket = new Socket(serverAddress, portNumber);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String queryIndex = args[0];
                message.put("queryIndex", queryIndex);
                out.println(ProtocolHandler.jsonify(message));
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
