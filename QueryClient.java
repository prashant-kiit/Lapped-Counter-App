import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class QueryClient {
    private static final String serverAddress = "localhost";
    private static final int portNumber = 8081;
    private static String queryIndex = new String();
    private static String metric = new String();
    private static Map<String, String> message = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        try {
            while (true) {
                Socket socket = new Socket(serverAddress, portNumber);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("\nEnter query options: 1-select,2-sessid,3-sessname,4-bydate,5-bytime,6-count");
                queryIndex = read.readLine();
                message.put("queryIndex", queryIndex);
                int i = 0;
                while (true) {
                    metric = read.readLine();
                    if(metric.equals("r")) {
                        break;
                    }
                    message.put("metric" + i, metric);
                    i++;
                }
                out.println(ProtocolHandler.jsonify(message));
                String result = in.readLine();
                
                System.out.println(result);
                out.println("Response Recevived from Query Server");
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
