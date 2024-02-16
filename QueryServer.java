import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryServer extends Thread {
    private QueryRegistry queryRegistry = null;
    private ArrayList<String> result = new ArrayList<>();
    private Map<String, String> message = new HashMap<>();
    private String queryIndex = new String();
    private ArrayList<String> paramaters = new ArrayList<>();
    public QueryServer(Database database) {
        this.queryRegistry = new QueryRegistry(database);
    }

    public void run() {
        final int portNumber = 8081;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                System.out.println("Database is listening...");
                result.clear();
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String messageString = in.readLine();

                message = ProtocolHandler.mapify(messageString);
                queryIndex = message.get("queryIndex");
                int i = 0;
                for(@SuppressWarnings("unused") String key : message.keySet()) {
                    paramaters.add(message.get("metric" + i));
                    i++;
                }
                Query query = queryRegistry.getQueryIndex().get(queryIndex);
                result = query.operate(paramaters);
                System.out.println("Result\n" + result.toString());

                System.out.println("Response sent to Client");
                out.println(result.toString());
                in.readLine();
                socket.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
