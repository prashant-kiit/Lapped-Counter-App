import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Query extends Thread {

    private Database database = null;

    public Query(Database database) {
        this.database = database;
    }

    public void printDatabase(int i) {
        System.out.println(database.getDatabase().get(i).getSessionID());
        System.out.println(database.getDatabase().get(i).getSessionName());
        System.out.println(database.getDatabase().get(i).getDate());
        System.out.println(database.getDatabase().get(i).getTime());
        System.out.println(database.getDatabase().get(i).getCountPerSession());
        System.out.println(database.getDatabase().get(i).getCountPerLaps().toString());
        System.out.println("------------------------------------------------------------");
    }

    public void selectAll(Database database) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            printDatabase(i);
        }
    }

    public void filterBySessionId(Database database, Integer targetSessionId) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if (database.getDatabase().get(i).getSessionID() == targetSessionId) {
                printDatabase(i);
            }
        }
    }

    public void filterBySessionName(Database database, String targetSessionName) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if (database.getDatabase().get(i).getSessionName().contains(targetSessionName)) {
                printDatabase(i);
            }
        }
    }

    public void filterByDate(Database database, Date date1, Date date2) throws ParseException {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(database.getDatabase().get(i).getDate());
            if (date.after(date1) && date.before(date2)) {
                printDatabase(i);
            }
        }
    }

    public void filterByTime(Database database, Time time1, Time time2) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            Time time = Time.valueOf(database.getDatabase().get(i).getTime());
            int tmp1 = time.compareTo(time1);
            int tmp2 = time.compareTo(time2);
            if (tmp2 >= 0 && tmp1 <= 0) {
                printDatabase(i);
            }
        }
    }

    public void filterByCountPerSession(Database database, Integer countPerSession) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if (database.getDatabase().get(i).getCountPerSession() == countPerSession) {
                printDatabase(i);
            }
        }
    }

    public void run() {
        final int portNumber = 8081;
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                // System.out.println("Enter query options:
                // 1-select,2-sessid,3-sessname,4-bydate,5-bytime,6-count");
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String serverResponseInput = in.readLine();
                int input = Integer.parseInt(serverResponseInput);

                if (input == 100) {
                    selectAll(database);
                }

                if (input == 2) {
                    String serverResponseTargetSessionId = in.readLine();
                    Integer targetSessionId = Integer.parseInt(serverResponseTargetSessionId);
                    filterBySessionId(database, targetSessionId);
                }

                if (input == 3) {
                    String targetSessionName = in.readLine();
                    filterBySessionName(database, targetSessionName);
                }

                if (input == 4) {
                    String date1 = in.readLine();
                    Date date11 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
                    String date2 = in.readLine();
                    Date date22 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
                    filterByDate(database, date11, date22);
                }

                if (input == 5) {
                    String time1 = in.readLine();
                    Time time11 = Time.valueOf(time1);
                    String time2 = in.readLine();
                    Time time22 = Time.valueOf(time2);
                    filterByTime(database, time11, time22);
                }

                if (input == 6) {
                    String serverResponseCountPerSession = in.readLine();
                    Integer countPerSession = Integer.parseInt(serverResponseCountPerSession);
                    filterByCountPerSession(database, countPerSession);
                }

                socket.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
