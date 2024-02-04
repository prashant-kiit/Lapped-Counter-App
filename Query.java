import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Query extends Thread {

    private Database database = null;
    private ArrayList<String> result = new ArrayList<>();

    public Query(Database database) {
        this.database = database;
    }

    public String getJsonstring(PrintWriter out, int i) {
        return "{\"SessionID\" : " + database.getDatabase().get(i).getSessionID() + ", \"SessionName\" : \""
                + database.getDatabase().get(i).getSessionName() + "\", \"Date\" : \""
                + database.getDatabase().get(i).getDate() + "\", \"Time\" : \""
                + database.getDatabase().get(i).getTime()
                + "\", \"CountPerSession\" : " + database.getDatabase().get(i).getCountPerSession()
                + ", \"CountPerLaps\" : " + database.getDatabase().get(i).getCountPerLaps().toString() + "}";
    }

    public void selectAll(PrintWriter out, Database database) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            result.add(getJsonstring(out, i));
        }
    }

    public void filterBySessionId(PrintWriter out, Database database, Integer targetSessionId) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if (database.getDatabase().get(i).getSessionID() == targetSessionId) {
                System.out.println("true");
                result.add(getJsonstring(out, i));
            }
        }
    }
    
    public void filterBySessionName(PrintWriter out, Database database, String targetSessionName) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if (database.getDatabase().get(i).getSessionName().contains(targetSessionName)) {
                System.out.println("true");
                result.add(getJsonstring(out, i));
            }
        }
    }
    
    public void filterByDate(PrintWriter out, Database database, Date date1, Date date2) throws ParseException {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(database.getDatabase().get(i).getDate());
            if (date.after(date1) && date.before(date2)) {
                result.add(getJsonstring(out, i));
            }
        }
    }

    public void filterByTime(PrintWriter out, Database database, Time time1, Time time2) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            Time time = Time.valueOf(database.getDatabase().get(i).getTime());
            int tmp1 = time.compareTo(time1);
            int tmp2 = time.compareTo(time2);
            if (tmp2 >= 0 && tmp1 <= 0) {
                result.add(getJsonstring(out, i));
            }
        }
    }

    public void filterByCountPerSession(PrintWriter out, Database database, Integer countPerSession) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if (database.getDatabase().get(i).getCountPerSession() == countPerSession) {
                System.out.println("true");
                result.add(getJsonstring(out, i));
            }
        }
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
                String serverResponseInput = in.readLine();
                Integer input = Integer.parseInt(serverResponseInput);

                if (input == 1) {
                    System.out.println("Caught Input/Option");
                    out.println(input);
                    selectAll(out, database);
                }
                
                if (input == 2) {
                    System.out.println("Caught Input/Option");
                    out.println(input);
                    String serverResponseTargetSessionId = in.readLine();
                    Integer targetSessionId = Integer.parseInt(serverResponseTargetSessionId);
                    filterBySessionId(out, database, targetSessionId);
                }
                
                if (input == 3) {
                    System.out.println("Caught Input/Option");
                    out.println(input);
                    String targetSessionName = in.readLine();
                    filterBySessionName(out, database, targetSessionName);
                }
                
                if (input == 4) {
                    System.out.println("Caught Input/Option");
                    String date1 = in.readLine();
                    Date date11 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
                    String date2 = in.readLine();
                    Date date22 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
                    filterByDate(out, database, date11, date22);
                }
                
                if (input == 5) {
                    System.out.println("Caught Input/Option");
                    out.println(input);
                    String time1 = in.readLine();
                    Time time11 = Time.valueOf(time1);
                    String time2 = in.readLine();
                    Time time22 = Time.valueOf(time2);
                    filterByTime(out, database, time11, time22);
                }
                
                if (input == 6) {
                    System.out.println("Caught Input/Option");
                    out.println(input);
                    String serverResponseCountPerSession = in.readLine();
                    Integer countPerSession = Integer.parseInt(serverResponseCountPerSession);
                    filterByCountPerSession(out, database, countPerSession);
                }
                System.out.println("Result\n" + result.toString());
                String temp = result.toString();
                out.println(temp);
                in.readLine();
                socket.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
