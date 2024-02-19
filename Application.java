import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Application extends Thread {
    private int sessionSwitch = 0;
    private int lapSwitch = 0;
    private Database database = null;

    public Application(Database database) {
        this.database = database;
    }

    public int count(int lap) throws IOException {
        int countPerLap = 0;

        while (true) {
            System.out.println("Enter continue/end Lap " + lap + "\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Integer input = Integer.parseInt(reader.readLine());

            // To change Lap
            if (input == 0) {
                System.out.println("\nLap " + lap + " ends");
                break;
            }

            // Redo Counting
            if (input == -1) {
                System.out.println("\nRedoing the count " + countPerLap);
                countPerLap -= 2;
            }

            // Redo Lap
            if (input == -2) {
                lapSwitch = 1;
                break;
            }

            // To save/change/exit Session
            if (input == -3) {
                sessionSwitch = 1;
                break;
            }

            countPerLap++;
            System.out.println("\nCount " + countPerLap + "\n");
        }

        return countPerLap;
    }

    public void run() {

        while (true) {
            System.out.println("Session starts\n");
            System.out.println("Enter the name of the Session\n");
            SessionData sessionData = new SessionData();
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            try {
                sessionData.setSessionName(read.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            sessionData.setSessionID(date, time, sessionData.getSessionName());
            sessionData.setDate(date);
            sessionData.setTime(time);

            Integer lap = 1;
            ArrayList<Integer> countPerLaps = new ArrayList<Integer>();
            Integer countPerSession = 0;

            while (true) {
                if (sessionSwitch == 1) {
                    System.out.println("\nSession ends/exited\n");
                    
                    System.out.println("Message Queued");
                    sessionSwitch = 0;
                    break;
                }
                System.err.println("\nLap " + lap + " starts\n");

                int countPerLap = 0;
                try {
                    countPerLap = count(lap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (lapSwitch == 1) {
                    System.out.println("\nRedoing the Lap " + lap);
                    lapSwitch = 0;
                } else {
                    countPerLaps.add(countPerLap);
                    countPerSession += countPerLap;
                    System.out.println("\nCount in Lap " + lap + " - " + countPerLap);
                    System.out.println("\nCount Per Session " + countPerSession + "\n");
                    sessionData.setCountPerLaps(countPerLaps);
                    sessionData.setCountPerSession(countPerSession);
                    database.save(sessionData);
                    System.err.println("Saved Session " + sessionData.getSessionName());
                    lap++;
                }
            }
        }
    }
}